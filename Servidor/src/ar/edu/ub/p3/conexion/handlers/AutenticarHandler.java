package ar.edu.ub.p3.conexion.handlers;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.Usuario;
import ar.edu.ub.p3.conexion.handler.Handler;
import ar.edu.ub.p3.servidor.ChatService;

public class AutenticarHandler implements Handler<ChatService> {

	public AutenticarHandler(Usuario usuario) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Mensaje mensaje, ChatService estadoConexionAlServerDeChat) {

	}

}
