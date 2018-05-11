package ar.edu.ub.p3.common;

import java.io.Serializable;
import java.util.Collection;

public class Mensaje implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7841614880524595646L;


	public enum Tipo{
		AUTENTICAR,
		MENSAJE_DE_CHAT,
		AUTENTICAR_ACK,
		AUTENTICAR_RECHAZADO,
		DESCONECTAR, 
		DESCONECTAR_ACK, 
		OBTENER_LISTADO_USUARIOS, 
		LISTADO_USUARIOS
	}
	
	private Tipo        	    tipo;
	private Credentials 	    credenciales;
	private ChatMessage 	    chatMessage;
	private String      	    comentario;
	private Collection<String>	usuarios;
	
	private Mensaje(Tipo tipo, Credentials credenciales, ChatMessage chatMessage, String comentario, Collection<String> usuarios) {
		this.setTipo(tipo);
		this.setChatMessage(chatMessage);
		this.setCredenciales(credenciales);
		this.setComentario(comentario);
		this.setUsuarios(usuarios);
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
	
	public Collection<String> getUsuarios() {
		return usuarios;
	}

	private void setUsuarios(Collection<String> usuarios) {
		this.usuarios = usuarios;
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Fabrica de mensajes

	public static Mensaje crearMensajeAuthentication( Credentials credenciales ) {
		return new Mensaje( Tipo.AUTENTICAR, credenciales, null, null, null );
	}
	
	public static Mensaje crearMensajeMensajeDeChat( ChatMessage chatMessage ) {
		return new Mensaje( Tipo.MENSAJE_DE_CHAT, null, chatMessage, null, null );
	}
	
	public static Mensaje crearMensajeAutenticarACK( ) {
		return new Mensaje( Tipo.AUTENTICAR_ACK, null, null, null, null );
	}
	
	public static Mensaje crearMensajeAutenticarRechazado(  String comentario) {
		return new Mensaje( Tipo.AUTENTICAR_RECHAZADO, null, null, comentario, null );
	}

	public static Mensaje crearMensajeDesconectar( String alias) {
		return new Mensaje( Tipo.DESCONECTAR, null, null, alias, null);
	}

	public static Mensaje crearMensajeDesconectarACK() {
		return new Mensaje( Tipo.DESCONECTAR_ACK, null, null, null, null);
	}

	public static Mensaje crearMensajeGetUsuarios() {
		return new Mensaje( Tipo.OBTENER_LISTADO_USUARIOS, null, null, null, null);
	}
	
	public static Mensaje crearMensajeListadoUsuarios( Collection<String> usuarios) {
		return new Mensaje( Tipo.LISTADO_USUARIOS, null, null, null, usuarios);
	}

}
