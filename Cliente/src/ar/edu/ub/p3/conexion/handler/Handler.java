package ar.edu.ub.p3.conexion.handler;

import ar.edu.ub.p3.common.Mensaje;
import ar.edu.ub.p3.conexion.EstadoConexionAlServerDeChat;

public interface Handler {
	public void accept(Mensaje mensaje, EstadoConexionAlServerDeChat estadoConexionAlServerDeChat);
}
