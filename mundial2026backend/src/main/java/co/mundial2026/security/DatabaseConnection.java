package co.mundial2026.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://127.0.0.1:3307/Mundial2026"; // Asegúrate de que la base de datos esté creada
    private static final String USER = "root"; // Cambia por el usuario de tu base de datos
    private static final String PASSWORD = "juan"; // Cambia por la contraseña de tu base de datos
    
    // Conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        try {
            // Registrar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer conexión
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
    }
}