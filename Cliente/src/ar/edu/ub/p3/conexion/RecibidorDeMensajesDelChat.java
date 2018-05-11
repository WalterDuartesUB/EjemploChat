package ar.edu.ub.p3.conexion;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.handler.Handler;
import ar.edu.ub.p3.conexion.handlers.AutenticarACKHandler;
import ar.edu.ub.p3.conexion.handlers.AutenticarRechazadoHandler;
import ar.edu.ub.p3.conexion.handlers.MensajeDeChatHandler;

public class RecibidorDeMensajesDelChat implements Runnable {
    private Socket socket;
    private EstadoConexionAlServerDeChat estadoCnx;
    private Map< Mensaje.Tipo, Handler<EstadoConexionAlServerDeChat>>  handlers;
    
    public RecibidorDeMensajesDelChat(EstadoConexionAlServerDeChat estadoCnx, Socket clientSocket) {
        this.setSocket(clientSocket);
        this.setEstadoCnx(estadoCnx);
        
        this.setHandlers( new HashMap<Mensaje.Tipo, Handler<EstadoConexionAlServerDeChat>>() );
        this.loadHandlers();
    }

    private void loadHandlers() {		
		this.getHandlers().put( Mensaje.Tipo.MENSAJE_DE_CHAT, new MensajeDeChatHandler() );
		this.getHandlers().put( Mensaje.Tipo.AUTENTICAR_ACK, new AutenticarACKHandler() );
		this.getHandlers().put( Mensaje.Tipo.AUTENTICAR_RECHAZADO, new AutenticarRechazadoHandler());
		this.getHandlers().put( Mensaje.Tipo.DESCONECTAR_ACK, new DesconectarACKHandler() );
		this.getHandlers().put( Mensaje.Tipo.LISTADO_USUARIOS, new ListadoDeUsuariosHandler() );
	}

	public void run() {
        try (ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream())) {
        	Mensaje mensaje = null;        	
        	
            while ( this.getEstadoCnx().isDeboContinuar() ) {
                try {
                    mensaje = (Mensaje) in.readObject();
                    
                    this.getHandlers().get( mensaje.getTipo() ).accept( mensaje, this.getEstadoCnx() );
/*                    
                    if( mensaje.getMessage().compareTo("Connected") == 0 )
                    {
                    	System.out.println("conected");
                    	this.getEstadoCnx().setEstoyConectado( true );
                    	this.getEstadoCnx().setEstoyEsperandoRespuestaConexion( false );
                    }
                    else if( mensaje.getMessage().compareTo("Authentication failed") == 0 )
                    {
                    	System.out.println("no conected");
                    	this.getEstadoCnx().setEstoyConectado( false );
                    	this.getEstadoCnx().setEstoyEsperandoRespuestaConexion( false );
                    	deboContinuar = false;
                    }
                    
                    System.out.println(mensaje);
*/                    
                }
                catch (EOFException e) {
                    break;
                }
            }
        }
        catch (SocketException e) {
            System.out.println("Disconnected");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

	public Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket clientSocket) {
		this.socket = clientSocket;
	}

	private EstadoConexionAlServerDeChat getEstadoCnx() {
		return estadoCnx;
	}

	private void setEstadoCnx(EstadoConexionAlServerDeChat estadoCnx) {
		this.estadoCnx = estadoCnx;
	}

	public Map< Mensaje.Tipo, Handler<EstadoConexionAlServerDeChat>> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map< Mensaje.Tipo, Handler<EstadoConexionAlServerDeChat>> handlers) {
		this.handlers = handlers;
	}
}