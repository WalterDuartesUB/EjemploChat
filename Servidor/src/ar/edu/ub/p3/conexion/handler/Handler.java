package ar.edu.ub.p3.conexion.handler;

import ar.edu.ub.p3.common.Mensaje;

public interface Handler<T> {
	public void accept(Mensaje mensaje, T estadoConexionAlServerDeChat);
}
