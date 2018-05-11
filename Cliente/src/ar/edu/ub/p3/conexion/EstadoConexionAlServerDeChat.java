package ar.edu.ub.p3.conexion;

public class EstadoConexionAlServerDeChat {
	private boolean estoyConectado;
	private boolean estoyEsperandoRespuestaConexion;
	private boolean deboContinuar;

	public EstadoConexionAlServerDeChat() {
		this.setEstoyConectado( false );
		this.setEstoyEsperandoRespuestaConexion( false );
		this.setDeboContinuar(true);
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
	
}
