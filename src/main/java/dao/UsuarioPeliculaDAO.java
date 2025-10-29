package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import entidades.UsuarioPelicula;

public class UsuarioPeliculaDAO {
	
	public void insert(UsuarioPelicula up) throws SQLException {
		String sql = "INSERT INTO usuario_pelicula(alias,pelicula_id,valoracion) VALUES (?,?,?)";
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, up.getAlias());
			ps.setInt(2, up.getPeliculaId());
			ps.setDouble(3, up.getValoracion());
			ps.executeUpdate();
			
			try (ResultSet keys = ps.getGeneratedKeys()){
				if (keys.next()) up.setId(keys.getLong(1));
			}
		}
	}
	
	public List<UsuarioPelicula> findByAlias(String alias) throws SQLException {
		List<UsuarioPelicula> lista = new ArrayList<>();
		String sql = "SELECT id,alias,pelicula_id,valoracion,fecha_registro FROM usuario_pelicula WHERE alias = ? ORDER BY fecha_registro DESC";
		
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, alias);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()){
					UsuarioPelicula up = new UsuarioPelicula();
					up.setId(rs.getLong("id"));
					up.setAlias(rs.getString("alias"));
					up.setPeliculaId(rs.getInt("pelicula_id"));
					up.setValoracion(rs.getDouble("valoracion"));
					up.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
					lista.add(up);
				}
			}
		}
		return lista;
	}
	
	public int deleteByAlias(String alias) throws SQLException {
		String sql = "DELETE FROM usuario_pelicula WHERE alias = ?";
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, alias);
			return ps.executeUpdate();
		}
	}
	
}