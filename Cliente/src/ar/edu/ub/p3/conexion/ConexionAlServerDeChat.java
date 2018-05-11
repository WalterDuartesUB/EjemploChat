package ar.edu.ub.p3.conexion;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import ar.edu.ub.p3.common.Credentials;
import ar.edu.ub.p3.common.Message;

public class ConexionAlServerDeChat {

	private String ipServer;
	private int puerto;
	private Socket socket = null;
	private ObjectOutputStream outputStream = null;
	private String userName;
	private EstadoConexionAlServerDeChat estadoCnx;
	
	public ConexionAlServerDeChat(String ipServer, int puerto) {
		this.setIpServer(ipServer);
		this.setPuerto(puerto);
		this.setEstadoCnx( new EstadoConexionAlServerDeChat() );
	}

	public boolean conectar(String userName, String userPassword) {
		
		///////////////////////////////////////////////////////////////////
		//Marco que estoy esperando la conexion
		
		this.getEstadoCnx().setEstoyEsperandoRespuestaConexion( true );
		
		///////////////////////////////////////////////////////////////////
		//Intento hacer la conexion
		
		try 
		{
			//Creo el socket al servidor
			this.setSocket( new Socket(this.getIpServer(), this.getPuerto() ) );
			
        	//Creo un thread para poder escuchar los mensajes que llegan desde el servidor        	
            new Thread( new MessageListener( this.getEstadoCnx(), this.getSocket() ) ).start();
            
            // Creo el outputstream
            this.setOutputStream( new ObjectOutputStream( this.getSocket().getOutputStream() ) );
            
            // Envio mis credenciales
            this.setUserName(userName);
            this.getOutputStream().writeObject( new Credentials( this.getUserName(), userPassword) );
            
            ///////////////////////////////////////////////////////////////////
            // Espero la respuesta del servidor
            
            while( this.isEstoyEsperandoRespuestaConexion() )
            {            	
            	// Espero para buscar mi respuesta            	
    			Thread.sleep( 1000 );
            }
            
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
				
		return this.isEstoyConectado();
	}

	public void enviarMensaje(String message) {
		try {
			this.getOutputStream().writeObject( new Message( this.getUserName(), message) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getIpServer() {
		return ipServer;
	}

	private void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	private int getPuerto() {
		return puerto;
	}

	private void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	private Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}

	private ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	private void setOutputStream(ObjectOutputStream out) {
		this.outputStream = out;
	}

	private String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	private EstadoConexionAlServerDeChat getEstadoCnx() {
		return estadoCnx;
	}

	private void setEstadoCnx(EstadoConexionAlServerDeChat estadoCnx) {
		this.estadoCnx = estadoCnx;
	}
	
	public boolean isEstoyConectado() {
		return this.getEstadoCnx().isEstoyConectado();
	}

	private boolean isEstoyEsperandoRespuestaConexion() {
		return this.getEstadoCnx().isEstoyEsperandoRespuestaConexion();
	}
	

}
