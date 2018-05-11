package ar.edu.ub.p3.conexion.handlers;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.EstadoConexionAlServerDeChat;
import ar.edu.ub.p3.conexion.handler.Handler;

public class AutenticarRechazadoHandler implements Handler {

	@Override
	public void accept(Mensaje mensaje, EstadoConexionAlServerDeChat estadoConexionAlServerDeChat) {
		estadoConexionAlServerDeChat.setEstoyConectado( false );
		estadoConexionAlServerDeChat.setEstoyEsperandoRespuestaConexion( false );
		estadoConexionAlServerDeChat.setDeboContinuar( false );
	}

}
