package ar.edu.ub.p3.conexion;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ar.edu.ub.p3.common.Credentials;
import ar.edu.ub.p3.common.Message;
import ar.edu.ub.p3.servidor.ChatManager;
import ar.edu.ub.p3.servidor.EstadoChat;

public class Usuario implements Runnable {

	private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private Credentials auth;
//    private EstadoChat estadoChat;
    private ChatManager chatManager;
    
	public Usuario(Socket socket, EstadoChat estadoChat) {
    	this.setSocket(socket);
    	this.setChatManager( new ChatManager( estadoChat ) );
//    	this.setEstadoChat( estadoChat );
	}

	public void run() {
        try {
            this.setObjectOutputStream(new ObjectOutputStream( this.getSocket().getOutputStream() ) );
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        try (ObjectInputStream messageObject = new ObjectInputStream(this.getSocket().getInputStream())) {
            
        	this.setAuth( (Credentials) messageObject.readObject() );
            
            if ( this.aceptarConexion( this.getAuth() )) {
            	
            	//Envio un mensaje de que acepto la conexion
                this.sendMessage( new Message("Server", "Connected") );

                // Me envio todos los mensajes
                this.sendLastMessages();
                	                
                //Envio la lista de clientes conectados
                this.broadcastUserList();
                
                //Me quedo esperando los mensajes nuevos
                while (true) {
                	Message message;
                	
                    try {
                        message = (Message) messageObject.readObject();
                    }
                    catch (EOFException e) {
                        break;
                    }                   

                    //Hago broadcast del mensaje que recibi
                    this.broadcastMessage(message);
                }
            }
            else 
            {
            	//Envio un mensaje al cliente que no se puede conectar
                this.sendMessage( new Message("Server", "Authentication failed") );
                
                System.out.println("Access denied (" + this.getSocket().getRemoteSocketAddress() + ")");
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        finally {
            try {
                this.getObjectOutputStream().close();
            }
            catch (IOException e) {
                System.out.println("Cannot close connection " + e);
            }
            
            //Quito el usuario de la lista
            this.quitarUsuario();
            
            System.out.println("Client disconnected (" + this.getSocket().getRemoteSocketAddress() + ")");
        }
    }

	private void quitarUsuario() {
//		this.getEstadoChat().quitarUsuario( this );
		this.getChatManager().quitarUsuario( this );
	}

	private void broadcastMessage(Message message) {
//		this.getEstadoChat().broadcastMessage( message );
		this.getChatManager().broadcastMessage( message );
	}

	private void broadcastUserList() {
//		this.getEstadoChat().broadcastUserList();
		this.getChatManager().broadcastUserList();
	}

	private void sendLastMessages() {
//		this.getEstadoChat().sendLastMessages(this);
		this.getChatManager().sendLastMessages(this);
	}
	
    public void sendMessage(Message msg) {
        try {
            this.getObjectOutputStream().writeObject(msg);
        }
        catch (NullPointerException e) {
            System.out.println("Cannot send message. Message is empty " + e);
        } catch (IOException e) {
            System.out.println("Cannot send message " + e);
        }
    }

    private boolean aceptarConexion(Credentials auth) {
    	return true;
    }

    private Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	private ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	private void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	private Credentials getAuth() {
		return auth;
	}

	private void setAuth(Credentials auth) {
		this.auth = auth;
	}
/*
	private EstadoChat getEstadoChat() {
		return estadoChat;
	}

	private void setEstadoChat(EstadoChat estadoChat) {
		this.estadoChat = estadoChat;
	}
*/
	public String getUserName() {
		return this.getAuth().getUserName();
	}

	private ChatManager getChatManager() {
		return chatManager;
	}

	private void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}
}