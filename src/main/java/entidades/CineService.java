package entidades;

import java.util.*;
import java.sql.Timestamp;

import dao.DAOPelicula;
import dao.UsuarioPeliculaDAO;

public class CineService {
	
	private DAOPelicula DAOPelicula;
    private UsuarioPeliculaDAO usuarioPeliculaDAO;

    public CineService() {
    	
    	DAOPelicula = new DAOPelicula();
        usuarioPeliculaDAO = new UsuarioPeliculaDAO();
        
    }
    
    // Registrar preferencia
    public void registrarPreferencia(String alias, String genero, double valoracion) {
        try {
            Optional<Pelicula> mejor = DAOPelicula.findBestByGenero(genero);
            if (mejor.isPresent()) {
                Pelicula peli = mejor.get();
                UsuarioPelicula up = new UsuarioPelicula();
                up.setAlias(alias);
                up.setPeliculaId(peli.getId());
                up.setValoracion(valoracion);
                up.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
                usuarioPeliculaDAO.insert(up);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    // Recomendar película
    public Pelicula recomendarPorGenero(String genero) {
        try {
            Optional<Pelicula> mejor = DAOPelicula.findBestByGenero(genero);
            return mejor.orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Consultar historial
    public List<UsuarioPelicula> consultarHistorial(String alias) {
        try {
            return usuarioPeliculaDAO.findByAlias(alias);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Borrar historial
    public int borrarHistorial(String alias) {
        try {
            return usuarioPeliculaDAO.deleteByAlias(alias);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Top películas
    public List<Pelicula> obtenerTopPeliculas() {
        try {
            return DAOPelicula.findTopRated(10);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
}
