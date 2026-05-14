package co.mundial2026.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppTheme {

    public static final Color NEGRO_FONDO = new Color(10, 12, 18);
    public static final Color NEGRO_PANEL = new Color(18, 21, 30);
    public static final Color NEGRO_TARJETA = new Color(24, 28, 38);

    public static final Color DORADO = new Color(224, 182, 77);
    public static final Color BLANCO = new Color(245, 245, 245);
    public static final Color GRIS_TEXTO = new Color(170, 175, 185);
    public static final Color BORDE_SUAVE = new Color(55, 60, 72);

    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 34);
    public static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_LABEL = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 14);

    public static void aplicarTemaGeneral() {
        UIManager.put("Button.font", FUENTE_BOTON);
        UIManager.put("Label.font", FUENTE_NORMAL);
        UIManager.put("TextField.font", FUENTE_NORMAL);
        UIManager.put("PasswordField.font", FUENTE_NORMAL);
        UIManager.put("Menu.font", FUENTE_NORMAL);
        UIManager.put("MenuItem.font", FUENTE_NORMAL);
    }

    public static JButton crearBotonPrincipal(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(DORADO);
        boton.setForeground(NEGRO_FONDO);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(12, 18, 12, 18));
        return boton;
    }
}