package ar.edu.ub.p3.conexion.handlers;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.Usuario;
import ar.edu.ub.p3.conexion.handler.Handler;
import ar.edu.ub.p3.servidor.ChatService;

public class DesconectarHandler implements Handler<ChatService> {

	private Usuario usuario;
	public DesconectarHandler(Usuario usuario) {
		this.setUsuario(usuario);
	}

	@Override
	public void accept(Mensaje mensaje, ChatService estadoConexionAlServerDeChat) {
		System.out.println("Desconectar al usuario " + mensaje.getComentario() );

		this.getUsuario().desconectar();		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
