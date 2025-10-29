package entidades;

public class Pelicula {
	
	private int id;
	private String titulo;
	private String genero;
	private double puntuacionMedia;
	
	
	public Pelicula() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public double getPuntuacionMedia() {
		return puntuacionMedia;
	}
	public void setPuntuacionMedia(double puntuacionMedia) {
		this.puntuacionMedia = puntuacionMedia;
	}
	
	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", titulo=" + titulo + ", genero=" + genero + ", puntuacionMedia="
				+ puntuacionMedia + "]";
	}
	
	
	
}
