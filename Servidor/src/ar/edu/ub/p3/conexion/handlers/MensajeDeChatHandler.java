package ar.edu.ub.p3.conexion.handlers;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.Usuario;
import ar.edu.ub.p3.conexion.handler.Handler;
import ar.edu.ub.p3.servidor.ChatService;

public class MensajeDeChatHandler implements Handler<ChatService> {

	public MensajeDeChatHandler(Usuario usuario) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Mensaje mensaje, ChatService estadoConexionAlServerDeChat) {
		estadoConexionAlServerDeChat.broadcastMessage( mensaje.getChatMessage() );
	}

}
