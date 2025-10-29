package dao;

import java.sql.*;
import java.util.*;

import entidades.Pelicula;

public class DAOPelicula {
	
	// Obtener todas las películas
    public List<Pelicula> findAll() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT * FROM pelicula ORDER BY titulo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar película por género
    public List<Pelicula> findByGenero(String genero) {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT * FROM pelicula WHERE genero = ? ORDER BY puntuacion_media DESC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, genero);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar mejor película de un género
    public Optional<Pelicula> findBestByGenero(String genero) {
        String sql = "SELECT * FROM pelicula WHERE genero = ? ORDER BY puntuacion_media DESC LIMIT 1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, genero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Top películas
    public List<Pelicula> findTopRated(int limit) {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT * FROM pelicula ORDER BY puntuacion_media DESC LIMIT ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método auxiliar mapeo de fila del ResultSet
    private Pelicula mapRow(ResultSet rs) throws SQLException {
        Pelicula p = new Pelicula();
        p.setId(rs.getInt("id"));
        p.setTitulo(rs.getString("titulo"));
        p.setGenero(rs.getString("genero"));
        p.setPuntuacionMedia(rs.getDouble("puntuacion_media"));
        return p;
    }
}
