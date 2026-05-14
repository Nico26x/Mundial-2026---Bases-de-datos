package co.mundial2026.view;

import co.mundial2026.security.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.security.MessageDigest;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    private AuthService authService;

    public LoginFrame() {
        AppTheme.aplicarTemaGeneral();
        authService = new AuthService();

        setTitle("Mundial 2026 - Acceso al Sistema");
        setSize(1020, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2));
        panelPrincipal.setBackground(AppTheme.NEGRO_FONDO);

        panelPrincipal.add(crearPanelIzquierdo());
        panelPrincipal.add(crearPanelDerecho());

        add(panelPrincipal);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(AppTheme.NEGRO_FONDO);
        contenedor.setBorder(new EmptyBorder(28, 28, 28, 28));

        ImagePanel panelImagen = new ImagePanel("/images/worldcup_login.jpg");
        panelImagen.setBackground(AppTheme.NEGRO_FONDO);

        JPanel panelTexto = new JPanel();
        panelTexto.setBackground(AppTheme.NEGRO_FONDO);
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBorder(new EmptyBorder(18, 8, 0, 8));

        JLabel lblTitulo = new JLabel("Mundial 2026");
        lblTitulo.setFont(AppTheme.FUENTE_TITULO);
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Sistema de gestión del campeonato");
        lblSubtitulo.setFont(AppTheme.FUENTE_SUBTITULO);
        lblSubtitulo.setForeground(AppTheme.GRIS_TEXTO);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPaises = new JLabel("México · Estados Unidos · Canadá");
        lblPaises.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPaises.setForeground(AppTheme.DORADO);
        lblPaises.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelTexto.add(lblTitulo);
        panelTexto.add(Box.createVerticalStrut(6));
        panelTexto.add(lblSubtitulo);
        panelTexto.add(Box.createVerticalStrut(10));
        panelTexto.add(lblPaises);

        contenedor.add(panelImagen, BorderLayout.CENTER);
        contenedor.add(panelTexto, BorderLayout.SOUTH);

        return contenedor;
    }

    private JPanel crearPanelDerecho() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(AppTheme.NEGRO_PANEL);
        fondo.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(AppTheme.NEGRO_TARJETA);
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDE_SUAVE, 1),
                new EmptyBorder(36, 36, 36, 36)
        ));
        tarjeta.setPreferredSize(new Dimension(360, 390));

        JLabel lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Accede con tus credenciales");
        lblSubtitulo.setFont(AppTheme.FUENTE_SUBTITULO);
        lblSubtitulo.setForeground(AppTheme.GRIS_TEXTO);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblUsuario = crearLabelCampo("Usuario");
        txtUsuario = crearCampoTexto();

        JLabel lblContrasena = crearLabelCampo("Contraseña");
        txtContrasena = crearCampoContrasena();

        btnIngresar = AppTheme.crearBotonPrincipal("Ingresar al sistema");
        btnIngresar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btnIngresar.addActionListener(e -> iniciarSesion());

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblSubtitulo);
        tarjeta.add(Box.createVerticalStrut(28));

        tarjeta.add(lblUsuario);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(txtUsuario);
        tarjeta.add(Box.createVerticalStrut(18));

        tarjeta.add(lblContrasena);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(txtContrasena);
        tarjeta.add(Box.createVerticalStrut(28));

        tarjeta.add(btnIngresar);

        fondo.add(tarjeta);

        return fondo;
    }

    private JLabel crearLabelCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(AppTheme.FUENTE_LABEL);
        label.setForeground(AppTheme.BLANCO);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(AppTheme.FUENTE_NORMAL);
        campo.setForeground(AppTheme.BLANCO);
        campo.setBackground(new Color(30, 34, 44));
        campo.setCaretColor(AppTheme.BLANCO);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campo.setPreferredSize(new Dimension(280, 44));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDE_SUAVE, 1),
                new EmptyBorder(10, 14, 10, 14)
        ));
        return campo;
    }

    private JPasswordField crearCampoContrasena() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(AppTheme.FUENTE_NORMAL);
        campo.setForeground(AppTheme.BLANCO);
        campo.setBackground(new Color(30, 34, 44));
        campo.setCaretColor(AppTheme.BLANCO);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campo.setPreferredSize(new Dimension(280, 44));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDE_SUAVE, 1),
                new EmptyBorder(10, 14, 10, 14)
        ));
        return campo;
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar usuario y contraseña.",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            String contrasenaHash = convertirSHA256(contrasena);
            boolean autenticado = authService.autenticar(usuario, contrasenaHash);

            if (autenticado) {
                MainFrame mainFrame = new MainFrame(authService);
                mainFrame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos.",
                        "Error de autenticación",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos:\n" + ex.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String convertirSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }
}