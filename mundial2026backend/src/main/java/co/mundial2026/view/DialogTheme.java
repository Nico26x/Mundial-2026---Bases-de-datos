package co.mundial2026.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogTheme {

    public static void aplicarTema() {
        UIManager.put("OptionPane.background", AppTheme.NEGRO_PANEL);
        UIManager.put("Panel.background", AppTheme.NEGRO_PANEL);

        UIManager.put("OptionPane.messageForeground", AppTheme.BLANCO);
        UIManager.put("OptionPane.foreground", AppTheme.BLANCO);

        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 13));

        UIManager.put("Button.background", AppTheme.DORADO);
        UIManager.put("Button.foreground", AppTheme.NEGRO_FONDO);
        UIManager.put("Button.focus", AppTheme.DORADO);
        UIManager.put("Button.select", AppTheme.DORADO.brighter());

        UIManager.put("Button.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.DORADO, 1),
                new EmptyBorder(8, 16, 8, 16)
        ));

        UIManager.put("OptionPane.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 60, 72), 1),
                new EmptyBorder(14, 14, 14, 14)
        ));
    }
}