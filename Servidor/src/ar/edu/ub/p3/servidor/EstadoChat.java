package ar.edu.ub.p3.servidor;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.p3.common.ChatMessage;
import ar.edu.ub.p3.conexion.Usuario;

public class EstadoChat {
	
	private List<Usuario> usuarios;
	private List<ChatMessage> ultimosMensajes;
	private int maximumMessages;

	public EstadoChat(int cantidadMaximaMensajes) {
		this.setUsuarios( new LinkedList<Usuario>());
		this.setUltimosMensajes( new LinkedList<ChatMessage>() );
		this.setMaximumMessages(cantidadMaximaMensajes);
	}

	public void agregarUsuario(Usuario usuarioNuevo) {	
		this.getUsuarios().add(usuarioNuevo);
	}

	private List<Usuario> getUsuarios() {
		return usuarios;
	}

	private void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



	public void agregarMensaje(ChatMessage message) {
		this.getUltimosMensajes().add(message);		
		
		//Si ya tengo mas de la cuota, elimino el primero
		if( this.getUltimosMensajes().size() > this.getMaximumMessages())
			this.getUltimosMensajes().remove(0);
	}

	private List<ChatMessage> getUltimosMensajes() {
		return ultimosMensajes;
	}
	
	public ChatMessage[] ObtenerUltimosMensajes() {
		return this.getUltimosMensajes().toArray( new ChatMessage[ this.getUltimosMensajes().size() ] );
	}

	private void setUltimosMensajes(List<ChatMessage> ultimosMensajes) {
		this.ultimosMensajes = ultimosMensajes;
	}

	public void quitarUsuario(Usuario usuario) {
		this.getUsuarios().remove( usuario );
		
	}
	
/*
 	public void broadcastMessage(Message message) {
		
		this.agregarMensaje(message);
		
		for( Usuario usuario : this.getUsuarios() )
			usuario.sendMessage( message );
		
	}
	
	public void sendLastMessages(Usuario usuario) {
		for( Message msg :this.getUltimosMensajes() )
			usuario.sendMessage(msg);		
	}

	public void broadcastUserList() {
		String listaUsuarios = "Usuarios Conectados: ";
		
		for( Usuario usr : this.getUsuarios() )
			listaUsuarios += usr.getUserName() + ", ";
		
		listaUsuarios = listaUsuarios.substring(0, listaUsuarios.length() - 2);
		
		Message message = new Message("Server", listaUsuarios );
		this.broadcastMessage(message);
		
	}
*/
	private int getMaximumMessages() {
		return maximumMessages;
	}

	private void setMaximumMessages(int maximumMessages) {
		this.maximumMessages = maximumMessages;
	}

	public Usuario[] ObtenerUsuarios() {
		return this.getUsuarios().toArray( new Usuario[ this.getUsuarios().size() ] );
	}

}
