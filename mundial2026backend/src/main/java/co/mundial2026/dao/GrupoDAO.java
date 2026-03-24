package co.mundial2026.dao;

import co.mundial2026.model.Grupo;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    // Método para agregar un grupo
    public void agregarGrupo(Grupo grupo) throws SQLException {
        String query = "INSERT INTO Grupo (nombre_grupo) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, grupo.getNombreGrupo());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los grupos
    public List<Grupo> obtenerGrupos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT * FROM Grupo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Grupo grupo = new Grupo(
                        rs.getInt("id_grupo"),
                        rs.getString("nombre_grupo")
                );
                grupos.add(grupo);
            }
        }
        return grupos;
    }
}