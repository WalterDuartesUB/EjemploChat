package ar.edu.ub.p3.servidor;
import ar.edu.ub.p3.common.ChatMessage;
import ar.edu.ub.p3.conexion.Usuario;

public class ChatManager {
	EstadoChat estadoChat;

	public ChatManager(EstadoChat estadoChat) {
		this.setEstadoChat(estadoChat);
	}

	private EstadoChat getEstadoChat() {
		return estadoChat;
	}

	private void setEstadoChat(EstadoChat estadoChat) {
		this.estadoChat = estadoChat;
	}

	public void quitarUsuario(Usuario usuario) {
		this.getEstadoChat().quitarUsuario(usuario);		
	}

	public void sendLastMessages(Usuario usuario) {
		for( ChatMessage msg :this.getUltimosMensajes() )
			usuario.sendMessage(msg);		
	}

	private ChatMessage[] getUltimosMensajes() {
		return this.getEstadoChat().ObtenerUltimosMensajes();
	}

	public void broadcastUserList() {
		String listaUsuarios = "Usuarios Conectados: ";
		
		for( Usuario usr : this.getUsuarios() )
			listaUsuarios += usr.getUserName() + ", ";
		
		listaUsuarios = listaUsuarios.substring(0, listaUsuarios.length() - 2);
		
		ChatMessage message = new ChatMessage("Server", listaUsuarios );
		this.broadcastMessage(message);
		
	}
	
 	public void broadcastMessage(ChatMessage message) {
		
		this.getEstadoChat().agregarMensaje(message);
		
		for( Usuario usuario : this.getUsuarios() )
			usuario.sendMessage( message );
		
	}

	private Usuario[] getUsuarios() {
		return this.getEstadoChat().ObtenerUsuarios();
	}	
}
