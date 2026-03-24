package co.mundial2026.dao;

import co.mundial2026.model.Usuario;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void agregarUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO Usuario (nombre_usuario, contrasena_hash, tipo_usuario, fecha_creacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasenaHash());
            stmt.setString(3, usuario.getTipoUsuario());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.getFechaCreacion()));
            stmt.executeUpdate();
        }
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena_hash"),
                        rs.getString("tipo_usuario"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime());
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) throws SQLException {
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena_hash"),
                        rs.getString("tipo_usuario"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime());
            }
        }
        return null; // Si no encuentra el usuario, retorna null
    }
}