package ar.edu.ub.p3.conexion;

import java.util.Collection;

public class EstadoConexionAlServerDeChat {
	private boolean estoyConectado;
	private boolean estoyEsperandoRespuestaConexion;
	private boolean deboContinuar;	
	private boolean estoyEsperandoRespuestaDesconexion;
	private boolean estoyEsperandoRespuestaGetUsuarios;
	
	private Collection<String> usuarios;
	
	public EstadoConexionAlServerDeChat() {
		this.setEstoyConectado( false );
		this.setEstoyEsperandoRespuestaConexion( false );
		this.setDeboContinuar(true);
		this.setEstoyEsperandoRespuestaDesconexion(false);
		this.setEstoyEsperandoRespuestaGetUsuarios(false);
		this.setUsuarios(null);
	}

	public boolean isEstoyConectado() {
		return estoyConectado;
	}

	public void setEstoyConectado(boolean estoyConectado) {
		this.estoyConectado = estoyConectado;
	}

	public boolean isEstoyEsperandoRespuestaConexion() {
		return estoyEsperandoRespuestaConexion;
	}

	public void setEstoyEsperandoRespuestaConexion(boolean estoyEsperandoRespuestaConexion) {
		this.estoyEsperandoRespuestaConexion = estoyEsperandoRespuestaConexion;
	}

	public boolean isDeboContinuar() {
		return deboContinuar;
	}

	public void setDeboContinuar(boolean deboContinuar) {
		this.deboContinuar = deboContinuar;
	}

	public boolean isEstoyEsperandoRespuestaDesconexion() {
		return estoyEsperandoRespuestaDesconexion;
	}

	public void setEstoyEsperandoRespuestaDesconexion(boolean estoyEsperandoRespuestaDesconexion) {
		this.estoyEsperandoRespuestaDesconexion = estoyEsperandoRespuestaDesconexion;
	}

	public boolean isEstoyEsperandoRespuestaGetUsuarios() {
		return estoyEsperandoRespuestaGetUsuarios;
	}

	public void setEstoyEsperandoRespuestaGetUsuarios(boolean estoyEsperandoRespuestaGetUsuarios) {
		this.estoyEsperandoRespuestaGetUsuarios = estoyEsperandoRespuestaGetUsuarios;
	}

	public Collection<String> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Collection<String> usuarios) {
		this.usuarios = usuarios;
	}
	
}
