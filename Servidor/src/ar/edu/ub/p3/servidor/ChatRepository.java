package ar.edu.ub.p3.servidor;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.p3.common.ChatMessage;
import ar.edu.ub.p3.conexion.Usuario;

public class ChatRepository {
	
	private List<Usuario> usuarios;
	private List<ChatMessage> ultimosMensajes;
	private int maximumMessages;

	public ChatRepository(int cantidadMaximaMensajes) {
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
