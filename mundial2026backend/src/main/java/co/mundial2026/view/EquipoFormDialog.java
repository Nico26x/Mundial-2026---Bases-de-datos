package co.mundial2026.view;

import co.mundial2026.dao.EquipoDAO;
import co.mundial2026.model.Equipo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class EquipoFormDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtPais;
    private JTextField txtValorTotal;
    private JComboBox<ConfederacionItem> cbConfederacion;

    private final EquipoDAO equipoDAO;

    private Equipo equipoEditar;
    private boolean modoEdicion;
    private boolean guardado;

    public EquipoFormDialog(JFrame parent) {
        super(parent, "Nuevo equipo", true);

        this.equipoDAO = new EquipoDAO();
        this.equipoEditar = null;
        this.modoEdicion = false;
        this.guardado = false;

        setSize(540, 620);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarConfederaciones();
    }

    public EquipoFormDialog(JFrame parent, Equipo equipoEditar) {
        super(parent, "Editar equipo", true);

        this.equipoDAO = new EquipoDAO();
        this.equipoEditar = equipoEditar;
        this.modoEdicion = true;
        this.guardado = false;

        setSize(520, 560);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarConfederaciones();
        cargarDatosEquipo();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.NEGRO_PANEL);
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearFormularioConScroll(), BorderLayout.CENTER);        root.add(crearBotones(), BorderLayout.SOUTH);

        add(root);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel lblEtiqueta = new JLabel("TEAM CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(modoEdicion ? "Editar equipo" : "Nuevo equipo");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Registra la información general de la selección.");
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

    private JScrollPane crearFormularioConScroll() {
        JPanel formulario = crearFormulario();

        JScrollPane scrollPane = new JScrollPane(formulario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.NEGRO_PANEL);
        scrollPane.setBackground(AppTheme.NEGRO_PANEL);

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());

        return scrollPane;
    }

    private JPanel crearFormulario() {
        RoundedPanel panel = new RoundedPanel(28, new Color(18, 22, 34));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        txtNombre = crearCampoTexto();
        txtPais = crearCampoTexto();
        txtValorTotal = crearCampoTexto();
        cbConfederacion = crearComboBoxConfederacion();

        txtValorTotal.setToolTipText("Ejemplo: 850000000");

        agregarCampo(panel, gbc, "Nombre del equipo", txtNombre);
        agregarCampo(panel, gbc, "País", txtPais);
        agregarCampo(panel, gbc, "Valor total equipo", txtValorTotal);
        agregarCampo(panel, gbc, "Confederación", cbConfederacion);

        return panel;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(22, 0, 0, 0));

        RoundedButton btnCancelar = new RoundedButton("Cancelar", new Color(38, 47, 66), AppTheme.BLANCO);
        RoundedButton btnGuardar = new RoundedButton(
                modoEdicion ? "Guardar cambios" : "Guardar equipo",
                AppTheme.DORADO,
                AppTheme.NEGRO_FONDO
        );

        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarEquipo());

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
        campo.setPreferredSize(new Dimension(0, 42));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 60, 72), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return campo;
    }

    private JComboBox<ConfederacionItem> crearComboBoxConfederacion() {
        JComboBox<ConfederacionItem> combo = new JComboBox<>();
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 42));
        return combo;
    }

    private void cargarConfederaciones() {
        cbConfederacion.removeAllItems();

        cbConfederacion.addItem(new ConfederacionItem(1, "UEFA"));
        cbConfederacion.addItem(new ConfederacionItem(2, "CONMEBOL"));
        cbConfederacion.addItem(new ConfederacionItem(3, "CONCACAF"));
        cbConfederacion.addItem(new ConfederacionItem(4, "CAF"));
        cbConfederacion.addItem(new ConfederacionItem(5, "AFC"));
        cbConfederacion.addItem(new ConfederacionItem(6, "OFC"));
    }

    private void cargarDatosEquipo() {
        if (equipoEditar == null) {
            return;
        }

        txtNombre.setText(equipoEditar.getNombre());
        txtPais.setText(equipoEditar.getPais());
        txtValorTotal.setText(String.valueOf(equipoEditar.getValorTotalEquipo()));

        for (int i = 0; i < cbConfederacion.getItemCount(); i++) {
            ConfederacionItem item = cbConfederacion.getItemAt(i);

            if (item.getIdConfederacion() == equipoEditar.getIdConfederacion()) {
                cbConfederacion.setSelectedIndex(i);
                break;
            }
        }
    }

    private void guardarEquipo() {
        try {
            String nombre = txtNombre.getText().trim();
            String pais = txtPais.getText().trim();
            String valorTexto = txtValorTotal.getText().trim();
            ConfederacionItem confederacion = (ConfederacionItem) cbConfederacion.getSelectedItem();

            if (nombre.isEmpty() || pais.isEmpty() || valorTexto.isEmpty() || confederacion == null) {
                mostrarAdvertencia("Todos los campos son obligatorios.");
                return;
            }

            double valorTotal = Double.parseDouble(valorTexto);

            if (valorTotal < 0) {
                mostrarAdvertencia("El valor total no puede ser negativo.");
                return;
            }

            int idEquipo = modoEdicion ? equipoEditar.getIdEquipo() : 0;

            Equipo equipo = new Equipo(
                    idEquipo,
                    nombre,
                    pais,
                    valorTotal,
                    confederacion.getIdConfederacion()
            );

            if (modoEdicion) {
                equipoDAO.actualizarEquipo(equipo);
            } else {
                equipoDAO.agregarEquipo(equipo);
            }

            guardado = true;

            JOptionPane.showMessageDialog(
                    this,
                    modoEdicion ? "Equipo actualizado correctamente." : "Equipo registrado correctamente.",
                    "Operación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Verifica los datos ingresados.\n\n" +
                            "Valor total: ejemplo 850000000\n\n" +
                            "Detalle: " + e.getMessage(),
                    "Datos inválidos",
                    JOptionPane.ERROR_MESSAGE
            );
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

    private static class ConfederacionItem {
        private final int idConfederacion;
        private final String nombre;

        public ConfederacionItem(int idConfederacion, String nombre) {
            this.idConfederacion = idConfederacion;
            this.nombre = nombre;
        }

        public int getIdConfederacion() {
            return idConfederacion;
        }

        @Override
        public String toString() {
            return nombre;
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

    private static class DarkScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = AppTheme.DORADO;
            this.trackColor = AppTheme.NEGRO_PANEL;
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
            g2.setColor(AppTheme.NEGRO_PANEL);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }
}