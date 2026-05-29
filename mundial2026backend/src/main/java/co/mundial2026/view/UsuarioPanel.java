package co.mundial2026.view;

import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;
import co.mundial2026.security.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UsuarioPanel extends JPanel {

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtBuscar;

    private JLabel lblTotalUsuarios;
    private JLabel lblAdministradores;
    private JLabel lblTradicionales;
    private JLabel lblEsporadicos;

    private final UsuarioDAO usuarioDAO;

    public UsuarioPanel() {
        this.usuarioDAO = new UsuarioDAO();

        setLayout(new BorderLayout());
        setBackground(AppTheme.NEGRO_PANEL);

        initComponents();
        cargarUsuarios();
    }

    private void initComponents() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel header = crearHeader();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));

        JPanel barraAcciones = crearBarraAcciones();
        barraAcciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        barraAcciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel tarjetas = crearTarjetasResumen();
        tarjetas.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjetas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel tabla = crearTabla();
        tabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        tabla.setPreferredSize(new Dimension(0, 430));
        tabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 430));

        JPanel botones = crearBotonesInferiores();
        botones.setAlignmentX(Component.LEFT_ALIGNMENT);
        botones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        contenido.add(header);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(barraAcciones);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(tarjetas);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(tabla);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(botones);

        JScrollPane scrollPrincipal = new JScrollPane(contenido);
        scrollPrincipal.setBorder(BorderFactory.createEmptyBorder());
        scrollPrincipal.getViewport().setBackground(AppTheme.NEGRO_PANEL);
        scrollPrincipal.setBackground(AppTheme.NEGRO_PANEL);
        scrollPrincipal.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPrincipal.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
        scrollPrincipal.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPrincipal.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(scrollPrincipal, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        HeaderPanel header = new HeaderPanel();
        header.setPreferredSize(new Dimension(0, 155));
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel lblEtiqueta = new JLabel("USER CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Gestión de usuarios");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Administra usuarios, roles y accesos del sistema.");
        lblDescripcion.setForeground(AppTheme.GRIS_TEXTO);
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        textos.add(lblEtiqueta);
        textos.add(Box.createVerticalStrut(8));
        textos.add(lblTitulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(lblDescripcion);

        header.add(textos, BorderLayout.CENTER);

        return header;
    }

    private JPanel crearBarraAcciones() {
        RoundedPanel panel = new RoundedPanel(26, new Color(18, 22, 34));
        panel.setLayout(new BorderLayout(14, 0));
        panel.setBorder(new EmptyBorder(16, 18, 16, 18));

        txtBuscar = new JTextField();
        txtBuscar.setFont(AppTheme.FUENTE_NORMAL);
        txtBuscar.setForeground(AppTheme.BLANCO);
        txtBuscar.setBackground(new Color(28, 33, 45));
        txtBuscar.setCaretColor(AppTheme.BLANCO);
        txtBuscar.setBorder(new EmptyBorder(10, 14, 10, 14));

        JLabel lblBuscar = new JLabel("Buscar usuario");
        lblBuscar.setForeground(AppTheme.GRIS_TEXTO);
        lblBuscar.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel panelBusqueda = new RoundedPanel(22, new Color(28, 33, 45));
        panelBusqueda.setLayout(new BorderLayout());
        panelBusqueda.setBorder(new EmptyBorder(0, 14, 0, 14));
        panelBusqueda.add(lblBuscar, BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarUsuarios();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarUsuarios();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarUsuarios();
            }
        });

        panel.add(panelBusqueda, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTarjetasResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 14, 0));
        panel.setOpaque(false);

        lblTotalUsuarios = new JLabel("0");
        lblAdministradores = new JLabel("0");
        lblTradicionales = new JLabel("0");
        lblEsporadicos = new JLabel("0");

        panel.add(crearTarjetaResumen("Total usuarios", lblTotalUsuarios));
        panel.add(crearTarjetaResumen("Administradores", lblAdministradores));
        panel.add(crearTarjetaResumen("Tradicionales", lblTradicionales));
        panel.add(crearTarjetaResumen("Esporádicos", lblEsporadicos));

        return panel;
    }

    private JPanel crearTarjetaResumen(String titulo, JLabel valor) {
        RoundedPanel card = new RoundedPanel(24, new Color(18, 22, 34));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(AppTheme.GRIS_TEXTO);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        valor.setForeground(AppTheme.BLANCO);
        valor.setFont(new Font("SansSerif", Font.BOLD, 25));
        valor.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(8));
        card.add(valor);

        return card;
    }

    private JPanel crearTabla() {
        RoundedPanel contenedor = new RoundedPanel(26, new Color(18, 22, 34));
        contenedor.setLayout(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(18, 18, 18, 18));

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Fecha creación");
        modeloTabla.addColumn("Hash contraseña");

        tablaUsuarios = new JTable(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        tablaUsuarios.setRowSorter(sorter);

        ocultarColumna(4);

        tablaUsuarios.setRowHeight(34);
        tablaUsuarios.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaUsuarios.setForeground(AppTheme.BLANCO);
        tablaUsuarios.setBackground(new Color(18, 22, 34));
        tablaUsuarios.setSelectionBackground(AppTheme.DORADO);
        tablaUsuarios.setSelectionForeground(AppTheme.NEGRO_FONDO);
        tablaUsuarios.setGridColor(new Color(45, 50, 62));
        tablaUsuarios.setShowVerticalLines(false);

        JTableHeader header = tablaUsuarios.getTableHeader();
        header.setBackground(new Color(28, 33, 45));
        header.setForeground(AppTheme.BLANCO);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 38));

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(18, 22, 34));
        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        contenedor.add(scrollPane, BorderLayout.CENTER);

        return contenedor;
    }

    private void ocultarColumna(int indice) {
        if (tablaUsuarios.getColumnModel().getColumnCount() > indice) {
            tablaUsuarios.getColumnModel().getColumn(indice).setMinWidth(0);
            tablaUsuarios.getColumnModel().getColumn(indice).setMaxWidth(0);
            tablaUsuarios.getColumnModel().getColumn(indice).setWidth(0);
        }
    }

    private JPanel crearBotonesInferiores() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);

        RoundedButton btnNuevo = new RoundedButton("+ Nuevo usuario", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        RoundedButton btnEditar = new RoundedButton("Editar", new Color(38, 47, 66), AppTheme.BLANCO);
        RoundedButton btnEliminar = new RoundedButton("Eliminar", new Color(90, 20, 28), AppTheme.BLANCO);
        RoundedButton btnActualizar = new RoundedButton("Actualizar", AppTheme.DORADO, AppTheme.NEGRO_FONDO);

        btnNuevo.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Aquí conectaremos el formulario de nuevo usuario."));

        btnEditar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Aquí conectaremos editar usuario."));

        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());

        btnActualizar.addActionListener(e -> cargarUsuarios());

        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);

        return panel;
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);

        try {
            List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();

            int admins = 0;
            int tradicionales = 0;
            int esporadicos = 0;

            for (Usuario usuario : usuarios) {
                String rol = usuario.getTipoUsuario();

                if ("Administrador".equalsIgnoreCase(rol)) {
                    admins++;
                } else if ("Tradicional".equalsIgnoreCase(rol)) {
                    tradicionales++;
                } else if ("Esporadico".equalsIgnoreCase(rol) || "Esporádico".equalsIgnoreCase(rol)) {
                    esporadicos++;
                }

                modeloTabla.addRow(new Object[]{
                        usuario.getIdUsuario(),
                        usuario.getNombreUsuario(),
                        usuario.getTipoUsuario(),
                        usuario.getFechaCreacion(),
                        usuario.getContrasenaHash()
                });
            }

            lblTotalUsuarios.setText(String.valueOf(usuarios.size()));
            lblAdministradores.setText(String.valueOf(admins));
            lblTradicionales.setText(String.valueOf(tradicionales));
            lblEsporadicos.setText(String.valueOf(esporadicos));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar usuarios:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un usuario para eliminar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int filaModelo = tablaUsuarios.convertRowIndexToModel(filaSeleccionada);
        int idUsuario = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
        String nombreUsuario = modeloTabla.getValueAt(filaModelo, 1).toString();
        String rol = modeloTabla.getValueAt(filaModelo, 2).toString();

        Usuario usuarioActual = SessionManager.getInstance().getUsuarioActual();

        if (usuarioActual != null && usuarioActual.getIdUsuario() == idUsuario) {
            JOptionPane.showMessageDialog(
                    this,
                    "No puedes eliminar el usuario con el que tienes la sesión iniciada.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if ("Administrador".equalsIgnoreCase(rol)) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se recomienda eliminar el usuario administrador principal.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el usuario " + nombreUsuario + "?\n\n" +
                        "Si tiene registros de bitácora asociados, la base de datos no permitirá eliminarlo.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                usuarioDAO.eliminarUsuario(idUsuario);

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );

                cargarUsuarios();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el usuario.\n\n" +
                                "Es posible que tenga registros de bitácora asociados.\n\n" +
                                "Detalle: " + e.getMessage(),
                        "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void filtrarUsuarios() {
        String texto = txtBuscar.getText().trim();

        if (sorter == null) {
            return;
        }

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto)));
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

    private static class HeaderPanel extends JPanel {
        public HeaderPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 32;

            GradientPaint fondo = new GradientPaint(
                    0, 0, new Color(8, 20, 48),
                    getWidth(), getHeight(), new Color(18, 22, 34)
            );

            g2.setPaint(fondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            Polygon rojo = new Polygon();
            rojo.addPoint((int) (getWidth() * 0.78), 0);
            rojo.addPoint(getWidth(), 0);
            rojo.addPoint(getWidth(), getHeight());
            rojo.addPoint((int) (getWidth() * 0.68), getHeight());

            g2.setColor(new Color(160, 18, 28));
            g2.fillPolygon(rojo);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class DarkScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = AppTheme.DORADO;
            this.trackColor = new Color(18, 22, 34);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return crearBotonInvisible();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return crearBotonInvisible();
        }

        private JButton crearBotonInvisible() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(AppTheme.DORADO);
            g2.fillRoundRect(
                    thumbBounds.x,
                    thumbBounds.y,
                    thumbBounds.width,
                    thumbBounds.height,
                    10,
                    10
            );

            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(18, 22, 34));
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }
}