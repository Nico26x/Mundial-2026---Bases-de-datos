package co.mundial2026;

import co.mundial2026.view.DialogTheme;
import co.mundial2026.view.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DialogTheme.aplicarTema();
            new LoginFrame().setVisible(true);
        });
    }
}