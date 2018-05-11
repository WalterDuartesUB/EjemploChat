package ar.edu.ub.p3.common;

import java.io.Serializable;

public class Mensaje implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7841614880524595646L;


	public enum Tipo{
		AUTENTICAR,
		MENSAJE_DE_CHAT,
		AUTENTICAR_ACK,
		AUTENTICAR_RECHAZADO
	}
	
	private Tipo        tipo;
	private Credentials credenciales;
	private ChatMessage 	chatMessage;
	private String      comentario;
	
	private Mensaje(Tipo tipo, Credentials credenciales, ChatMessage chatMessage, String comentario) {
		this.setTipo(tipo);
		this.setChatMessage(chatMessage);
		this.setCredenciales(credenciales);
		this.setComentario(comentario);
	}

	public Tipo getTipo() {
		return tipo;
	}

	private void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Credentials getCredenciales() {
		return credenciales;
	}

	private void setCredenciales(Credentials credenciales) {
		this.credenciales = credenciales;
	}

	public ChatMessage getChatMessage() {
		return chatMessage;
	}

	private void setChatMessage(ChatMessage chatMessage) {
		this.chatMessage = chatMessage;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	private void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Fabrica de mensajes

	public static Mensaje crearMensajeAuthentication( Credentials credenciales ) {
		return new Mensaje( Tipo.AUTENTICAR, credenciales, null, null );
	}
	
	public static Mensaje crearMensajeMensajeDeChat( ChatMessage chatMessage ) {
		return new Mensaje( Tipo.MENSAJE_DE_CHAT, null, chatMessage, null );
	}
	
	public static Mensaje crearMensajeAutenticarACK( ) {
		return new Mensaje( Tipo.AUTENTICAR_ACK, null, null, null );
	}
	
	public static Mensaje crearMensajeAutenticarRechazado(  String comentario) {
		return new Mensaje( Tipo.AUTENTICAR_RECHAZADO, null, null, comentario );
	}

}
