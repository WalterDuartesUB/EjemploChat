package ar.edu.ub.p3.conexion;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.handler.Handler;

public class ListadoDeUsuariosHandler implements Handler<EstadoConexionAlServerDeChat> {

	@Override
	public void accept(Mensaje mensaje, EstadoConexionAlServerDeChat estadoConexionAlServerDeChat) {
		estadoConexionAlServerDeChat.setUsuarios( mensaje.getUsuarios() );
		estadoConexionAlServerDeChat.setEstoyEsperandoRespuestaGetUsuarios(false);
	}

}
