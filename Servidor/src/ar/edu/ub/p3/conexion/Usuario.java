package ar.edu.ub.p3.conexion;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import ar.edu.ub.p3.common.Credentials;
import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.handler.Handler;
import ar.edu.ub.p3.conexion.handlers.AutenticarHandler;
import ar.edu.ub.p3.conexion.handlers.DesconectarHandler;
import ar.edu.ub.p3.conexion.handlers.MensajeDeChatHandler;
import ar.edu.ub.p3.conexion.handlers.ObtenerListadoUsuariosHandler;
import ar.edu.ub.p3.common.ChatMessage;
import ar.edu.ub.p3.servidor.ChatService;
import ar.edu.ub.p3.servidor.ChatRepository;

public class Usuario implements Runnable {

	private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private Credentials auth;
    private ChatService chatManager;
    
    private Map<Mensaje.Tipo, Handler<ChatService>> handlers;
    
	public Usuario(Socket socket, ChatRepository estadoChat) {
    	this.setSocket(socket);
    	this.setChatManager( new ChatService( estadoChat ) );
    	
    	this.setHandlers( new HashMap<Mensaje.Tipo, Handler<ChatService>>());
    	this.loadHandlers();
	}

	private void loadHandlers() {
		this.getHandlers().put( Mensaje.Tipo.MENSAJE_DE_CHAT, new MensajeDeChatHandler( this ) );		
		this.getHandlers().put( Mensaje.Tipo.AUTENTICAR, new AutenticarHandler( this ) );
		this.getHandlers().put( Mensaje.Tipo.DESCONECTAR, new DesconectarHandler( this ) );
		this.getHandlers().put( Mensaje.Tipo.OBTENER_LISTADO_USUARIOS, new ObtenerListadoUsuariosHandler( this ) );
	}

	public void run() {
		
        try {
            this.setObjectOutputStream(new ObjectOutputStream( this.getSocket().getOutputStream() ) );
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        try (ObjectInputStream messageObject = new ObjectInputStream(this.getSocket().getInputStream())) {
            
        	//Leo el pedido de Auth al chat
        	Mensaje pedidoAuth = (Mensaje) messageObject.readObject();
        	
        	this.setAuth( pedidoAuth.getCredenciales() );
        	
            if ( this.aceptarConexion( this.getAuth() ) ) {
            	
            	//Envio un mensaje de que acepto la conexion
            	this.sendMessage( Mensaje.crearMensajeAutenticarACK() );

                // Me envio todos los mensajes
                this.sendLastMessages();
                	                
                //Envio la lista de clientes conectados
                this.broadcastUserList();
                
                //Me quedo esperando los mensajes nuevos
                while (true) {
                	try {
                		Mensaje  mensajeDesdeElChat = (Mensaje) messageObject.readObject();
                		
                		//Tomo accion segun corresponda
                		this.getHandlers().get( mensajeDesdeElChat.getTipo() ).accept( mensajeDesdeElChat, this.getChatService() );
                    }
                    catch (EOFException e) {
                        break;
                    }
                }
            }
            else 
            {
            	//Envio un mensaje al cliente que no se puede conectar
            	this.sendMessage( Mensaje.crearMensajeAutenticarRechazado( "Authentication failed for user " + this.getAuth().getUserName() ) );
                
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
		this.getChatService().quitarUsuario( this );
	}
/*
	private void broadcastMessage(ChatMessage message) {
		this.getChatService().broadcastMessage( message );
	}
*/
	private void broadcastUserList() {
		this.getChatService().broadcastUserList();
	}

	private void sendLastMessages() {
		this.getChatService().sendLastMessages(this);
	}
	
	public void sendMessage(Mensaje msg) {
		try {
			this.getObjectOutputStream().writeObject( msg );
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	public void sendChatMessage(ChatMessage chatMessage) {
		this.sendMessage( Mensaje.crearMensajeMensajeDeChat( chatMessage ) );		
	}
	
    private boolean aceptarConexion(Credentials auth) {
    	return auth.getUserName().compareTo("walter") != 0;
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

	public String getUserName() {
		return this.getAuth().getUserName();
	}

	private ChatService getChatService() {
		return chatManager;
	}

	private void setChatManager(ChatService chatManager) {
		this.chatManager = chatManager;
	}

	public Map<Mensaje.Tipo, Handler<ChatService>> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<Mensaje.Tipo, Handler<ChatService>> handlers) {
		this.handlers = handlers;
	}

	public void desconectar() {
		this.getChatService().quitarUsuario( this );
		this.sendMessage( Mensaje.crearMensajeDesconectarACK() );
	}
	
	public void sendUserList() {
		this.sendMessage( Mensaje.crearMensajeListadoUsuarios( this.getChatService().getAllUsers() ) );		
	}
}