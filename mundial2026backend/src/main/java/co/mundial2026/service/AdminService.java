package co.mundial2026.service;

import co.mundial2026.security.AuthService;

public class AdminService {

    private final AuthService authService;

    public AdminService() {
        authService = new AuthService();
    }

    // Método para realizar una acción restringida solo para administradores
    public String realizarAccionAdmin(String accion) {
        if (authService.tieneRol("Administrador")) {
            // Aquí puedes implementar acciones que solo un administrador puede ejecutar
            switch (accion) {
                case "crearUsuario":
                    return "Acción realizada: Crear nuevo usuario";
                case "editarUsuario":
                    return "Acción realizada: Editar usuario";
                case "borrarUsuario":
                    return "Acción realizada: Eliminar usuario";
                default:
                    return "Acción no reconocida";
            }
        } else {
            return "Acción no permitida: Solo un administrador puede acceder a esta funcionalidad.";
        }
    }

    // Método para obtener información de los administradores
    public String obtenerInfoAdmin() {
        if (authService.tieneRol("Administrador")) {
            return "Información restringida a administradores: Estadísticas, reportes, etc.";
        } else {
            return "Acción no permitida: Solo un administrador puede acceder a esta información.";
        }
    }
}