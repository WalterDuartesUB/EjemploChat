package ar.edu.ub.p3.conexion;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.handler.Handler;

public class DesconectarACKHandler implements Handler<EstadoConexionAlServerDeChat> {

	@Override
	public void accept(Mensaje mensaje, EstadoConexionAlServerDeChat estadoConexionAlServerDeChat) {
		estadoConexionAlServerDeChat.setEstoyEsperandoRespuestaDesconexion(false);
		estadoConexionAlServerDeChat.setDeboContinuar(false);
		estadoConexionAlServerDeChat.setEstoyConectado(false);
	}

}
