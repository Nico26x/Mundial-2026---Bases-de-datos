package co.mundial2026.view;

import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;

public class UsuarioFormDialog extends JDialog {

    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbTipoUsuario;

    private final UsuarioDAO usuarioDAO;

    private Usuario usuarioEditar;
    private boolean modoEdicion;
    private boolean guardado;

    public UsuarioFormDialog(JFrame parent) {
        super(parent, "Nuevo usuario", true);

        this.usuarioDAO = new UsuarioDAO();
        this.usuarioEditar = null;
        this.modoEdicion = false;
        this.guardado = false;

        setSize(520, 540);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
    }

    public UsuarioFormDialog(JFrame parent, Usuario usuarioEditar) {
        super(parent, "Editar usuario", true);

        this.usuarioDAO = new UsuarioDAO();
        this.usuarioEditar = usuarioEditar;
        this.modoEdicion = true;
        this.guardado = false;

        setSize(520, 540);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarDatosUsuario();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.NEGRO_PANEL);
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearFormulario(), BorderLayout.CENTER);
        root.add(crearBotones(), BorderLayout.SOUTH);

        add(root);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel lblEtiqueta = new JLabel("USER CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(modoEdicion ? "Editar usuario" : "Nuevo usuario");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel(
                modoEdicion
                        ? "Actualiza el usuario, su contraseña o su tipo de acceso."
                        : "Crea un nuevo usuario para acceder al sistema."
        );
        lblDescripcion.setForeground(AppTheme.GRIS_TEXTO);
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(lblEtiqueta);
        header.add(Box.createVerticalStrut(8));
        header.add(lblTitulo);
        header.add(Box.createVerticalStrut(6));
        header.add(lblDescripcion);

        return header;
    }

    private JPanel crearFormulario() {
        RoundedPanel panel = new RoundedPanel(28, new Color(18, 22, 34));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        txtNombreUsuario = crearCampoTexto();
        txtContrasena = crearCampoPassword();
        cbTipoUsuario = crearComboTipoUsuario();

        agregarCampo(panel, gbc, "Nombre de usuario", txtNombreUsuario);

        String etiquetaContrasena = modoEdicion
                ? "Nueva contraseña (opcional)"
                : "Contraseña";

        agregarCampo(panel, gbc, etiquetaContrasena, txtContrasena);
        agregarCampo(panel, gbc, "Tipo de usuario", cbTipoUsuario);

        return panel;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(22, 0, 0, 0));

        RoundedButton btnCancelar = new RoundedButton("Cancelar", new Color(38, 47, 66), AppTheme.BLANCO);
        RoundedButton btnGuardar = new RoundedButton(
                modoEdicion ? "Guardar cambios" : "Guardar usuario",
                AppTheme.DORADO,
                AppTheme.NEGRO_FONDO
        );

        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarUsuario());

        panel.add(btnCancelar);
        panel.add(btnGuardar);

        return panel;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String etiqueta, JComponent campo) {
        JLabel label = new JLabel(etiqueta);
        label.setForeground(AppTheme.BLANCO);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridy++;
        panel.add(campo, gbc);
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(AppTheme.FUENTE_NORMAL);
        campo.setForeground(AppTheme.BLANCO);
        campo.setBackground(new Color(28, 33, 45));
        campo.setCaretColor(AppTheme.BLANCO);
        campo.setPreferredSize(new Dimension(0, 44));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 60, 72), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return campo;
    }

    private JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(AppTheme.FUENTE_NORMAL);
        campo.setForeground(AppTheme.BLANCO);
        campo.setBackground(new Color(28, 33, 45));
        campo.setCaretColor(AppTheme.BLANCO);
        campo.setPreferredSize(new Dimension(0, 44));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 60, 72), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return campo;
    }

    private JComboBox<String> crearComboTipoUsuario() {
        JComboBox<String> combo = new JComboBox<>(new String[]{
                "Administrador",
                "Tradicional",
                "Esporadico"
        });

        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 44));
        combo.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 72), 1));
        combo.setFocusable(false);
        combo.setOpaque(true);
        combo.setEditable(false);
        combo.setUI(new DarkComboBoxUI());

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus
            ) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                label.setFont(AppTheme.FUENTE_NORMAL);
                label.setBorder(new EmptyBorder(8, 12, 8, 12));
                label.setOpaque(true);

                if (isSelected) {
                    label.setBackground(AppTheme.DORADO);
                    label.setForeground(AppTheme.NEGRO_FONDO);
                } else {
                    label.setBackground(new Color(28, 33, 45));
                    label.setForeground(AppTheme.BLANCO);
                }

                list.setBackground(new Color(28, 33, 45));
                list.setForeground(AppTheme.BLANCO);
                list.setSelectionBackground(AppTheme.DORADO);
                list.setSelectionForeground(AppTheme.NEGRO_FONDO);

                return label;
            }
        });

        return combo;
    }

    private void cargarDatosUsuario() {
        if (usuarioEditar == null) {
            return;
        }

        txtNombreUsuario.setText(usuarioEditar.getNombreUsuario());
        cbTipoUsuario.setSelectedItem(usuarioEditar.getTipoUsuario());
    }

    private void guardarUsuario() {
        try {
            String nombreUsuario = txtNombreUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword()).trim();
            String tipoUsuario = cbTipoUsuario.getSelectedItem().toString();

            if (nombreUsuario.isEmpty()) {
                mostrarAdvertencia("El nombre de usuario es obligatorio.");
                return;
            }

            if (!modoEdicion && contrasena.isEmpty()) {
                mostrarAdvertencia("La contraseña es obligatoria para crear un usuario.");
                return;
            }

            String contrasenaHash;

            if (modoEdicion && contrasena.isEmpty()) {
                contrasenaHash = usuarioEditar.getContrasenaHash();
            } else {
                contrasenaHash = convertirSHA256(contrasena);
            }

            int idUsuario = modoEdicion ? usuarioEditar.getIdUsuario() : 0;
            LocalDateTime fechaCreacion = modoEdicion ? usuarioEditar.getFechaCreacion() : null;

            Usuario usuario = new Usuario(
                    idUsuario,
                    nombreUsuario,
                    contrasenaHash,
                    tipoUsuario,
                    fechaCreacion
            );

            if (modoEdicion) {
                usuarioDAO.actualizarUsuario(usuario);
            } else {
                usuarioDAO.agregarUsuario(usuario);
            }

            guardado = true;

            JOptionPane.showMessageDialog(
                    this,
                    modoEdicion ? "Usuario actualizado correctamente." : "Usuario registrado correctamente.",
                    "Operación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el usuario.\n\nDetalle: " + e.getMessage(),
                    "Error",
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

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Validación",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public boolean isGuardado() {
        return guardado;
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

    private static class DarkComboBoxUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton("▼");
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setBackground(new Color(28, 33, 45));
            button.setForeground(AppTheme.DORADO);
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
            button.setOpaque(true);
            return button;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(28, 33, 45));
            g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2.dispose();
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox);
            popup.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 72), 1));
            return popup;
        }
    }
}