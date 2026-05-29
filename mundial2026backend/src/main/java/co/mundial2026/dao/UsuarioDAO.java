package co.mundial2026.dao;

import co.mundial2026.model.Usuario;
import co.mundial2026.security.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void agregarUsuario(Usuario usuario) throws SQLException {
        String sql = """
                INSERT INTO Usuario (nombre_usuario, contrasena_hash, tipo_usuario)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasenaHash());
            stmt.setString(3, usuario.getTipoUsuario());

            stmt.executeUpdate();
        }
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT id_usuario, nombre_usuario, contrasena_hash, tipo_usuario, fecha_creacion
                FROM Usuario
                ORDER BY id_usuario ASC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDateTime fechaCreacion = null;

                if (rs.getTimestamp("fecha_creacion") != null) {
                    fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();
                }

                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena_hash"),
                        rs.getString("tipo_usuario"),
                        fechaCreacion
                );

                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) throws SQLException {
        String sql = """
                SELECT id_usuario, nombre_usuario, contrasena_hash, tipo_usuario, fecha_creacion
                FROM Usuario
                WHERE nombre_usuario = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDateTime fechaCreacion = null;

                    if (rs.getTimestamp("fecha_creacion") != null) {
                        fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();
                    }

                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre_usuario"),
                            rs.getString("contrasena_hash"),
                            rs.getString("tipo_usuario"),
                            fechaCreacion
                    );
                }
            }
        }

        return null;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = """
                UPDATE Usuario
                SET nombre_usuario = ?,
                    contrasena_hash = ?,
                    tipo_usuario = ?
                WHERE id_usuario = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasenaHash());
            stmt.setString(3, usuario.getTipoUsuario());
            stmt.setInt(4, usuario.getIdUsuario());

            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }
}