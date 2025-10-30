package entidades;

import java.sql.Timestamp;

public class UsuarioPelicula {
	
	private long id;
	private String alias;
	private int peliculaId;
	private double valoracion;
	private Timestamp fechaRegistro;
	
	public UsuarioPelicula() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getPeliculaId() {
		return peliculaId;
	}

	public void setPeliculaId(int peliculaId) {
		this.peliculaId = peliculaId;
	}

	public double getValoracion() {
		return valoracion;
	}

	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "UsuarioPelicula [id=" + id + ", alias=" + alias + ", peliculaId=" + peliculaId + ", valoracion="
				+ valoracion + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
	
	
}
