package ar.edu.ub.p3.conexion.handlers;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.EstadoConexionAlServerDeChat;
import ar.edu.ub.p3.conexion.handler.Handler;

public class AutenticarACKHandler implements Handler {

	@Override
	public void accept(Mensaje mensaje, EstadoConexionAlServerDeChat estadoConexionAlServerDeChat) {		
		estadoConexionAlServerDeChat.setEstoyConectado( true );
		estadoConexionAlServerDeChat.setEstoyEsperandoRespuestaConexion( false );
	}

}
