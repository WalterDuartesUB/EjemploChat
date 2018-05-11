package ar.edu.ub.p3.conexion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import ar.edu.ub.p3.servidor.ChatRepository;

public class RecibidorDePedidosDeConexion implements Runnable {

    private ServerSocket serverSocket;
    private int serverPort;
    private ChatRepository estadoChat;
    
    public RecibidorDePedidosDeConexion(int serverPort, ChatRepository estadoChat) {
        this.setServerPort(serverPort);
        this.setEstadoChat(estadoChat);
    }

    public void run() {
        try {
           
        	this.setServerSocket( new ServerSocket( getServerPort() ) );
        	
            System.out.println("Listening on " + this.getServerSocket().getInetAddress() + ":" + this.getServerSocket().getLocalPort());

            // TODO cambiarlo mientras que el servidor esta vivo
            while (true) {
            	
            	//Espero un nuevo usuario
                Socket socket = this.getServerSocket().accept();
                System.out.println("User connected (" + socket.getRemoteSocketAddress() + ")");
                
                //Creo un agrupador con la info de la nueva conexion
                Usuario UsuarioNuevo = new Usuario( socket, estadoChat );
                
                // lo agrego a la lista de usuarios
                this.getEstadoChat().agregarUsuario( UsuarioNuevo );
                
                //Disparo un thread para atenderlo
                Thread tx = new Thread( UsuarioNuevo );
                tx.start();
                
            }
        }
        catch (SocketException e) {
            System.out.println("SocketException " + e);
        }
        catch (IOException e) {
            System.out.println("IOException " + e);
        }
        finally {
            try {
                getServerSocket().close();
            }
            catch (IOException e) {
                System.out.println("IOException " + e);
            }
        }

    }

    private ServerSocket getServerSocket() {
		return serverSocket;
	}

	private void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	private int getServerPort() {
		return serverPort;
	}

	private void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	private ChatRepository getEstadoChat() {
		return estadoChat;
	}

	private void setEstadoChat(ChatRepository estadoChat) {
		this.estadoChat = estadoChat;
	}

}