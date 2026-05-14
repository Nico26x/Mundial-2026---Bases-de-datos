package co.mundial2026.view;

import co.mundial2026.security.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MainFrame extends JFrame {

    private final AuthService authService;

    private JPanel panelContenido;
    private JLabel lblTituloVista;
    private JLabel lblSubtituloVista;

    public MainFrame(AuthService authService) {
        this.authService = authService;

        setTitle("Mundial 2026 - Sistema de Gestión");
        setSize(1320, 780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        mostrarVista("Inicio", "Panel principal del sistema", crearLandingPanel());
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.NEGRO_FONDO);

        root.add(crearSidebar(), BorderLayout.WEST);
        root.add(crearZonaCentral(), BorderLayout.CENTER);

        add(root);
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBackground(new Color(7, 9, 15));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(22, 16, 22, 16));

        JLabel lblMarca = new JLabel("MUNDIAL 2026");
        lblMarca.setForeground(AppTheme.BLANCO);
        lblMarca.setFont(new Font("SansSerif", Font.BOLD, 19));
        lblMarca.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMini = new JLabel("Sistema de gestión");
        lblMini.setForeground(AppTheme.GRIS_TEXTO);
        lblMini.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMini.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel panelSesion = new RoundedPanel(20, new Color(18, 22, 34));
        panelSesion.setLayout(new BoxLayout(panelSesion, BoxLayout.Y_AXIS));
        panelSesion.setBorder(new EmptyBorder(14, 14, 14, 14));
        panelSesion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));
        panelSesion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSesion = new JLabel("Sesión iniciada");
        lblSesion.setForeground(AppTheme.GRIS_TEXTO);
        lblSesion.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel lblRol = new JLabel("Rol: " + obtenerRolActual());
        lblRol.setForeground(AppTheme.DORADO);
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 13));

        panelSesion.add(lblSesion);
        panelSesion.add(Box.createVerticalStrut(6));
        panelSesion.add(lblRol);

        JButton btnInicio = crearBotonSidebar("Inicio");
        JButton btnEquipos = crearBotonSidebar("Equipos");
        JButton btnJugadores = crearBotonSidebar("Jugadores");
        JButton btnPartidos = crearBotonSidebar("Partidos");
        JButton btnConsultas = crearBotonSidebar("Consultas");
        JButton btnReportes = crearBotonSidebar("Reportes");
        JButton btnUsuarios = crearBotonSidebar("Usuarios");
        JButton btnCerrarSesion = crearBotonCerrarSesion("Cerrar sesión");

        btnInicio.addActionListener(e ->
                mostrarVista("Inicio", "Panel principal del sistema", crearLandingPanel()));

        btnJugadores.addActionListener(e ->
                mostrarVista("Jugadores", "Gestión de jugadores", new JugadorPanel()));

        btnPartidos.addActionListener(e ->
                mostrarVista("Partidos", "Gestión de partidos", crearPlaceholderPanel("Partidos")));

        btnConsultas.addActionListener(e ->
                mostrarVista("Consultas", "Consultas del sistema", crearPlaceholderPanel("Consultas")));

        btnReportes.addActionListener(e ->
                mostrarVista("Reportes", "Reportes del sistema", crearPlaceholderPanel("Reportes")));

        btnUsuarios.addActionListener(e ->
                mostrarVista("Usuarios", "Administración de usuarios", crearPlaceholderPanel("Usuarios")));

        btnEquipos.addActionListener(e ->
                mostrarVista("Equipos", "Gestión de equipos", new EquipoPanel()));

        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        sidebar.add(lblMarca);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(lblMini);
        sidebar.add(Box.createVerticalStrut(18));
        sidebar.add(panelSesion);
        sidebar.add(Box.createVerticalStrut(24));

        sidebar.add(btnInicio);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnEquipos);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnJugadores);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnPartidos);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnConsultas);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnReportes);

        if (authService.esAdministrador()) {
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btnUsuarios);
        }

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnCerrarSesion);

        return sidebar;
    }

    private JPanel crearZonaCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.NEGRO_PANEL);

        panel.add(crearHeader(), BorderLayout.NORTH);

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(AppTheme.NEGRO_PANEL);
        panelContenido.setBorder(new EmptyBorder(16, 20, 20, 20));

        panel.add(panelContenido, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.NEGRO_PANEL);
        header.setBorder(new EmptyBorder(20, 22, 8, 22));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        lblTituloVista = new JLabel("Inicio");
        lblTituloVista.setForeground(AppTheme.BLANCO);
        lblTituloVista.setFont(new Font("SansSerif", Font.BOLD, 24));

        lblSubtituloVista = new JLabel("Panel principal del sistema");
        lblSubtituloVista.setForeground(AppTheme.GRIS_TEXTO);
        lblSubtituloVista.setFont(new Font("SansSerif", Font.PLAIN, 14));

        textos.add(lblTituloVista);
        textos.add(Box.createVerticalStrut(4));
        textos.add(lblSubtituloVista);

        RoundedPanel badge = new RoundedPanel(18, new Color(18, 22, 34));
        badge.setLayout(new FlowLayout(FlowLayout.CENTER, 14, 8));

        JLabel lblBadge = new JLabel("México · Estados Unidos · Canadá");
        lblBadge.setForeground(AppTheme.DORADO);
        lblBadge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.add(lblBadge);

        header.add(textos, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        return header;
    }

    private JPanel crearLandingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.NEGRO_PANEL);

        panel.add(crearHeroPrincipal(), BorderLayout.CENTER);
        panel.add(crearBarraInferior(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearHeroPrincipal() {
    HeroPanel hero = new HeroPanel();
    hero.setLayout(new BorderLayout());
    hero.setBorder(new EmptyBorder(22, 26, 22, 26));

    JPanel contenidoIzquierdo = new JPanel();
    contenidoIzquierdo.setOpaque(false);
    contenidoIzquierdo.setLayout(new BoxLayout(contenidoIzquierdo, BoxLayout.Y_AXIS));

    JLabel lblSmall = new JLabel("MUNDIAL 2026 · FIFA WORLD CUP");
    lblSmall.setForeground(new Color(220, 225, 235));
    lblSmall.setFont(new Font("SansSerif", Font.BOLD, 12));
    lblSmall.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel lblTitulo = new JLabel("<html>CONTROL<br>CENTER</html>");
    lblTitulo.setForeground(AppTheme.BLANCO);
    lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 52));
    lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel lblTexto = new JLabel(
            "<html>Gestiona jugadores, partidos, consultas y reportes<br>desde una sola plataforma con una experiencia visual moderna.</html>"
    );
    lblTexto.setForeground(new Color(220, 226, 235));
    lblTexto.setFont(new Font("SansSerif", Font.PLAIN, 15));
    lblTexto.setAlignmentX(Component.LEFT_ALIGNMENT);

    RoundedButton btnPrincipal = new RoundedButton("Explorar sistema", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
    btnPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);
    btnPrincipal.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Desde aquí seguimos conectando tus módulos."));

    JPanel puntos = new JPanel();
    puntos.setOpaque(false);
    puntos.setLayout(new BoxLayout(puntos, BoxLayout.Y_AXIS));

    puntos.add(crearPuntoIndicador(true));
    puntos.add(Box.createVerticalStrut(10));
    puntos.add(crearPuntoIndicador(false));
    puntos.add(Box.createVerticalStrut(10));
    puntos.add(crearPuntoIndicador(false));
    puntos.add(Box.createVerticalStrut(10));
    puntos.add(crearPuntoIndicador(false));

    JPanel centroTexto = new JPanel(new BorderLayout());
    centroTexto.setOpaque(false);
    centroTexto.add(puntos, BorderLayout.WEST);

    JPanel bloqueTexto = new JPanel();
    bloqueTexto.setOpaque(false);
    bloqueTexto.setLayout(new BoxLayout(bloqueTexto, BoxLayout.Y_AXIS));
    bloqueTexto.setBorder(new EmptyBorder(0, 18, 0, 0));

    bloqueTexto.add(lblSmall);
    bloqueTexto.add(Box.createVerticalStrut(14));
    bloqueTexto.add(lblTitulo);
    bloqueTexto.add(Box.createVerticalStrut(18));
    bloqueTexto.add(lblTexto);
    bloqueTexto.add(Box.createVerticalStrut(20));
    bloqueTexto.add(btnPrincipal);

    centroTexto.add(bloqueTexto, BorderLayout.CENTER);

    contenidoIzquierdo.add(Box.createVerticalGlue());
    contenidoIzquierdo.add(centroTexto);
    contenidoIzquierdo.add(Box.createVerticalGlue());

    JLabel lblJugador = crearImagenEscalada("/images/player_main.png", 360, 420);
    lblJugador.setHorizontalAlignment(SwingConstants.RIGHT);
    lblJugador.setVerticalAlignment(SwingConstants.BOTTOM);

    JPanel panelImagen = new JPanel(new BorderLayout());
    panelImagen.setOpaque(false);
    panelImagen.add(lblJugador, BorderLayout.CENTER);

    hero.add(contenidoIzquierdo, BorderLayout.CENTER);
    hero.add(panelImagen, BorderLayout.EAST);

    return hero;
}

    private JPanel crearBarraInferior() {
        RoundedPanel barra = new RoundedPanel(22, new Color(16, 20, 31));
        barra.setLayout(new GridLayout(1, 4, 12, 0));
        barra.setBorder(new EmptyBorder(18, 18, 18, 18));
        barra.setPreferredSize(new Dimension(0, 95));

        barra.add(crearInfoBloque("48", "Selecciones"));
        barra.add(crearInfoBloque("104", "Partidos"));
        barra.add(crearInfoBloque("16", "Ciudades"));
        barra.add(crearInfoBloque("3", "Países anfitriones"));

        return barra;
    }

    private JPanel crearInfoBloque(String numero, String texto) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblNumero = new JLabel(numero);
        lblNumero.setForeground(AppTheme.BLANCO);
        lblNumero.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblNumero.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setForeground(AppTheme.GRIS_TEXTO);
        lblTexto.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblNumero);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblTexto);

        return panel;
    }

    private JLabel crearMiniNavLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        return label;
    }

    private JPanel crearPuntoIndicador(boolean activo) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(activo ? AppTheme.DORADO : new Color(210, 210, 210));
                g2.fillOval(0, 0, 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(10, 10));
        panel.setMaximumSize(new Dimension(10, 10));
        return panel;
    }

    private JPanel crearPlaceholderPanel(String modulo) {
        RoundedPanel panel = new RoundedPanel(28, new Color(18, 22, 34));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(modulo);
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTexto = new JLabel("<html><div style='text-align:center;'>Este módulo está listo para conectarse con su vista real.</div></html>");
        lblTexto.setForeground(AppTheme.GRIS_TEXTO);
        lblTexto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton btnVolver = new RoundedButton("Volver al inicio", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e ->
                mostrarVista("Inicio", "Panel principal del sistema", crearLandingPanel()));

        contenido.add(lblTitulo);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(lblTexto);
        contenido.add(Box.createVerticalStrut(24));
        contenido.add(btnVolver);

        panel.add(contenido);
        return panel;
    }

    private JButton crearBotonSidebar(String texto) {
        RoundedButton btn = new RoundedButton(texto, new Color(18, 22, 34), AppTheme.BLANCO);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    private JButton crearBotonCerrarSesion(String texto) {
        RoundedButton btn = new RoundedButton(texto, new Color(80, 18, 24), AppTheme.BLANCO);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    private JLabel crearImagenEscalada(String ruta, int anchoMax, int altoMax) {
        JLabel label = new JLabel();
        label.setOpaque(false);

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
            Image img = icon.getImage();

            int originalW = img.getWidth(null);
            int originalH = img.getHeight(null);

            if (originalW > 0 && originalH > 0) {
                double escala = Math.min((double) anchoMax / originalW, (double) altoMax / originalH);
                int nuevoW = (int) (originalW * escala);
                int nuevoH = (int) (originalH * escala);

                Image escalada = img.getScaledInstance(nuevoW, nuevoH, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(escalada));
            }
        } catch (Exception e) {
            label.setText("Imagen no encontrada");
            label.setForeground(AppTheme.BLANCO);
        }

        return label;
    }

    private void mostrarVista(String titulo, String subtitulo, JPanel panel) {
        lblTituloVista.setText(titulo);
        lblSubtituloVista.setText(subtitulo);

        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private String obtenerRolActual() {
        if (authService.esAdministrador()) {
            return "Administrador";
        } else if (authService.esUsuarioTradicional()) {
            return "Tradicional";
        } else {
            return "Esporadico";
        }
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas cerrar la sesión actual?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
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
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
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
            setBorder(new EmptyBorder(12, 18, 12, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color colorPintar = getModel().isRollover() ? bgColor.brighter() : bgColor;
            g2.setColor(colorPintar);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class HeroPanel extends JPanel {
        public HeroPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 34;

            Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc);
            g2.setClip(clip);

            // fondo azul oscuro
            GradientPaint gpBlue = new GradientPaint(
                    0, 0, new Color(8, 20, 48),
                    getWidth(), getHeight(), new Color(4, 10, 24)
            );
            g2.setPaint(gpBlue);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // panel rojo diagonal
            Polygon poly = new Polygon();
            poly.addPoint((int) (getWidth() * 0.72), 0);
            poly.addPoint(getWidth(), 0);
            poly.addPoint(getWidth(), getHeight());
            poly.addPoint((int) (getWidth() * 0.58), getHeight());

            GradientPaint gpRed = new GradientPaint(
                    (int) (getWidth() * 0.60), 0,
                    new Color(220, 24, 34),
                    getWidth(), getHeight(),
                    new Color(150, 12, 22)
            );
            g2.setPaint(gpRed);
            g2.fillPolygon(poly);

            // capa sutil oscura izquierda
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRect(0, 0, (int) (getWidth() * 0.68), getHeight());

            g2.dispose();
            super.paintComponent(g);
        }
    }
}