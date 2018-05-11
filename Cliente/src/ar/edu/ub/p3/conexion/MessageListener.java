package ar.edu.ub.p3.conexion;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import ar.edu.ub.p3.common.Message;

public class MessageListener implements Runnable {
    private Socket socket;
    private EstadoConexionAlServerDeChat estadoCnx;
    
    public MessageListener(EstadoConexionAlServerDeChat estadoCnx, Socket clientSocket) {
        this.setSocket(clientSocket);
        this.setEstadoCnx(estadoCnx);
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream())) {
        	Message message = null;
        	boolean deboContinuar = true;
        	
            while ( deboContinuar ) {
                try {
                    message = (Message) in.readObject();
                    
                    if( message.getMessage().compareTo("Connected") == 0 )
                    {
                    	System.out.println("conected");
                    	this.getEstadoCnx().setEstoyConectado( true );
                    	this.getEstadoCnx().setEstoyEsperandoRespuestaConexion( false );
                    }
                    else if( message.getMessage().compareTo("Authentication failed") == 0 )
                    {
                    	System.out.println("no conected");
                    	this.getEstadoCnx().setEstoyConectado( false );
                    	this.getEstadoCnx().setEstoyEsperandoRespuestaConexion( false );
                    	deboContinuar = false;
                    }
                }
                catch (EOFException e) {
                    break;
                }
                
                System.out.println(message);
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
}