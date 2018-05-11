package ar.edu.ub.p3.conexion;

public class EstadoConexionAlServerDeChat {
	private boolean estoyConectado;
	private boolean estoyEsperandoRespuestaConexion;

	public EstadoConexionAlServerDeChat() {
		this.setEstoyConectado( false );
		this.setEstoyEsperandoRespuestaConexion( false );
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
}
