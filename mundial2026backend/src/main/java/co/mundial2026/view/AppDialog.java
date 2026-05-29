package co.mundial2026.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppDialog extends JDialog {

    public static final int YES_OPTION = 0;
    public static final int NO_OPTION = 1;

    private int respuesta = NO_OPTION;

    private AppDialog(
            Window parent,
            String titulo,
            String mensaje,
            String tipo,
            boolean confirmacion
    ) {
        super(parent, titulo, ModalityType.APPLICATION_MODAL);

        setUndecorated(true);
        setSize(430, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel root = new RoundedPanel(28, AppTheme.NEGRO_PANEL);
        root.setLayout(new BorderLayout());
        root.setBorder(new EmptyBorder(24, 26, 24, 26));

        root.add(crearHeader(titulo, tipo), BorderLayout.NORTH);
        root.add(crearMensaje(mensaje), BorderLayout.CENTER);
        root.add(crearBotones(confirmacion), BorderLayout.SOUTH);

        setContentPane(root);
    }

    public static void mostrarInfo(Component parent, String titulo, String mensaje) {
        mostrarMensaje(parent, titulo, mensaje, "INFO");
    }

    public static void mostrarError(Component parent, String titulo, String mensaje) {
        mostrarMensaje(parent, titulo, mensaje, "ERROR");
    }

    public static void mostrarAdvertencia(Component parent, String titulo, String mensaje) {
        mostrarMensaje(parent, titulo, mensaje, "ADVERTENCIA");
    }

    private static void mostrarMensaje(Component parent, String titulo, String mensaje, String tipo) {
        Window ventana = SwingUtilities.getWindowAncestor(parent);
        AppDialog dialog = new AppDialog(ventana, titulo, mensaje, tipo, false);
        dialog.setVisible(true);
    }

    public static int confirmar(Component parent, String titulo, String mensaje) {
        Window ventana = SwingUtilities.getWindowAncestor(parent);
        AppDialog dialog = new AppDialog(ventana, titulo, mensaje, "CONFIRMAR", true);
        dialog.setVisible(true);
        return dialog.respuesta;
    }

    private JPanel crearHeader(String titulo, String tipo) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel icono = new JLabel(obtenerIcono(tipo), SwingConstants.CENTER);
        icono.setPreferredSize(new Dimension(46, 46));
        icono.setForeground(AppTheme.NEGRO_FONDO);
        icono.setFont(new Font("SansSerif", Font.BOLD, 22));
        icono.setOpaque(false);

        RoundedPanel circulo = new RoundedPanel(40, obtenerColorTipo(tipo));
        circulo.setLayout(new BorderLayout());
        circulo.setPreferredSize(new Dimension(46, 46));
        circulo.add(icono, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel lblTipo = new JLabel(tipo);
        lblTipo.setForeground(AppTheme.DORADO);
        lblTipo.setFont(new Font("SansSerif", Font.BOLD, 11));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(lblTipo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(lblTitulo);

        panel.add(circulo, BorderLayout.WEST);
        panel.add(textos, BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane crearMensaje(String mensaje) {
        JTextArea area = new JTextArea(mensaje);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setForeground(AppTheme.GRIS_TEXTO);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setBorder(new EmptyBorder(6, 0, 6, 0));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        return scroll;
    }

    private JPanel crearBotones(boolean confirmacion) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(18, 0, 0, 0));

        if (confirmacion) {
            RoundedButton btnNo = new RoundedButton("Cancelar", new Color(38, 47, 66), AppTheme.BLANCO);
            RoundedButton btnSi = new RoundedButton("Confirmar", AppTheme.DORADO, AppTheme.NEGRO_FONDO);

            btnNo.addActionListener(e -> {
                respuesta = NO_OPTION;
                dispose();
            });

            btnSi.addActionListener(e -> {
                respuesta = YES_OPTION;
                dispose();
            });

            panel.add(btnNo);
            panel.add(btnSi);
        } else {
            RoundedButton btnAceptar = new RoundedButton("Aceptar", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
            btnAceptar.addActionListener(e -> dispose());
            panel.add(btnAceptar);
        }

        return panel;
    }

    private String obtenerIcono(String tipo) {
        return switch (tipo) {
            case "ERROR" -> "!";
            case "ADVERTENCIA" -> "!";
            case "CONFIRMAR" -> "?";
            default -> "✓";
        };
    }

    private Color obtenerColorTipo(String tipo) {
        return switch (tipo) {
            case "ERROR" -> new Color(160, 18, 28);
            case "ADVERTENCIA" -> AppTheme.DORADO;
            case "CONFIRMAR" -> AppTheme.DORADO;
            default -> new Color(46, 180, 120);
        };
    }

    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillRoundRect(4, 5, getWidth() - 8, getHeight() - 8, radius, radius);

            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class RoundedButton extends JButton {
        private final Color bgColor;
        private final Color fgColor;

        public RoundedButton(String text, Color bgColor, Color fgColor) {
            super(text);
            this.bgColor = bgColor;
            this.fgColor = fgColor;

            setForeground(fgColor);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setBorder(new EmptyBorder(11, 18, 11, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color color = getModel().isRollover() ? bgColor.brighter() : bgColor;
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 26, 26);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}