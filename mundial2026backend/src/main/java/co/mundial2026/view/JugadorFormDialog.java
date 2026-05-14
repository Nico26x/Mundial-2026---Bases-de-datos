package co.mundial2026.view;

import co.mundial2026.dao.EquipoDAO;
import co.mundial2026.dao.JugadorDAO;
import co.mundial2026.model.Equipo;
import co.mundial2026.model.Jugador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class JugadorFormDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JComboBox<String> cbPosicion;
    private JTextField txtPeso;
    private JTextField txtEstatura;
    private JTextField txtValorMercado;
    private JComboBox<EquipoItem> cbEquipo;

    private final JugadorDAO jugadorDAO;
    private final EquipoDAO equipoDAO;

    private Jugador jugadorEditar;
    private boolean modoEdicion;
    private boolean guardado;

    public JugadorFormDialog(JFrame parent) {
        super(parent, "Nuevo jugador", true);

        this.jugadorDAO = new JugadorDAO();
        this.equipoDAO = new EquipoDAO();
        this.jugadorEditar = null;
        this.modoEdicion = false;
        this.guardado = false;

        setSize(560, 680);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarEquipos();
    }

    public JugadorFormDialog(JFrame parent, Jugador jugadorEditar) {
        super(parent, "Editar jugador", true);

        this.jugadorDAO = new JugadorDAO();
        this.equipoDAO = new EquipoDAO();
        this.jugadorEditar = jugadorEditar;
        this.modoEdicion = true;
        this.guardado = false;

        setSize(560, 680);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarEquipos();
        cargarDatosJugador();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.NEGRO_PANEL);
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearFormularioConScroll(), BorderLayout.CENTER);
        root.add(crearBotones(), BorderLayout.SOUTH);

        add(root);
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

    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel lblEtiqueta = new JLabel("PLAYER CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(modoEdicion ? "Editar jugador" : "Nuevo jugador");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Registra la información básica del jugador.");
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
        panel.setBorder(new EmptyBorder(22, 22, 22, 22));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        txtNombre = crearCampoTexto();
        txtFechaNacimiento = crearCampoTexto();
        cbPosicion = crearComboBox(new String[]{
                "Portero",
                "Defensa",
                "Centrocampista",
                "Delantero"
        });
        txtPeso = crearCampoTexto();
        txtEstatura = crearCampoTexto();
        txtValorMercado = crearCampoTexto();
        cbEquipo = crearComboBoxEquipo();

        txtFechaNacimiento.setToolTipText("Formato: yyyy-MM-dd");
        txtPeso.setToolTipText("Ejemplo: 73.5");
        txtEstatura.setToolTipText("Ejemplo: 1.78");
        txtValorMercado.setToolTipText("Ejemplo: 150000000");

        agregarCampo(panel, gbc, "Nombre", txtNombre);
        agregarCampo(panel, gbc, "Fecha nacimiento (yyyy-MM-dd)", txtFechaNacimiento);
        agregarCampo(panel, gbc, "Posición", cbPosicion);
        agregarCampo(panel, gbc, "Peso (kg)", txtPeso);
        agregarCampo(panel, gbc, "Estatura (m)", txtEstatura);
        agregarCampo(panel, gbc, "Valor mercado", txtValorMercado);
        agregarCampo(panel, gbc, "Equipo", cbEquipo);

        return panel;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(22, 0, 0, 0));

        RoundedButton btnCancelar = new RoundedButton("Cancelar", new Color(38, 47, 66), AppTheme.BLANCO);
        RoundedButton btnGuardar = new RoundedButton(
                modoEdicion ? "Guardar cambios" : "Guardar jugador",
                AppTheme.DORADO,
                AppTheme.NEGRO_FONDO
        );
        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarJugador());

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

    private JComboBox<String> crearComboBox(String[] opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 42));
        return combo;
    }

    private JComboBox<EquipoItem> crearComboBoxEquipo() {
        JComboBox<EquipoItem> combo = new JComboBox<>();
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 42));
        return combo;
    }

    private void cargarEquipos() {
        try {
            List<Equipo> equipos = equipoDAO.obtenerEquipos();

            cbEquipo.removeAllItems();

            for (Equipo equipo : equipos) {
                cbEquipo.addItem(new EquipoItem(equipo.getIdEquipo(), equipo.getNombre()));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar equipos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cargarDatosJugador() {
        if (jugadorEditar == null) {
            return;
        }

        txtNombre.setText(jugadorEditar.getNombre());
        txtFechaNacimiento.setText(jugadorEditar.getFechaNacimiento().toString());
        cbPosicion.setSelectedItem(jugadorEditar.getPosicion());
        txtPeso.setText(String.valueOf(jugadorEditar.getPeso()));
        txtEstatura.setText(String.valueOf(jugadorEditar.getEstatura()));
        txtValorMercado.setText(String.valueOf(jugadorEditar.getValorMercado()));

        for (int i = 0; i < cbEquipo.getItemCount(); i++) {
            EquipoItem item = cbEquipo.getItemAt(i);

            if (item.getIdEquipo() == jugadorEditar.getIdEquipo()) {
                cbEquipo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void guardarJugador() {
        try {
            String nombre = txtNombre.getText().trim();
            String fechaTexto = txtFechaNacimiento.getText().trim();
            String posicion = cbPosicion.getSelectedItem().toString();
            String pesoTexto = txtPeso.getText().trim();
            String estaturaTexto = txtEstatura.getText().trim();
            String valorTexto = txtValorMercado.getText().trim();
            EquipoItem equipoSeleccionado = (EquipoItem) cbEquipo.getSelectedItem();

            if (nombre.isEmpty() || fechaTexto.isEmpty() || pesoTexto.isEmpty()
                    || estaturaTexto.isEmpty() || valorTexto.isEmpty() || equipoSeleccionado == null) {
                mostrarAdvertencia("Todos los campos son obligatorios.");
                return;
            }

            LocalDate fechaNacimiento = LocalDate.parse(fechaTexto);
            double peso = Double.parseDouble(pesoTexto);
            double estatura = Double.parseDouble(estaturaTexto);
            double valorMercado = Double.parseDouble(valorTexto);

            if (peso <= 0 || peso >= 200) {
                mostrarAdvertencia("El peso debe ser mayor que 0 y menor que 200.");
                return;
            }

            if (estatura <= 0 || estatura >= 2.50) {
                mostrarAdvertencia("La estatura debe ser mayor que 0 y menor que 2.50.");
                return;
            }

            if (valorMercado < 0) {
                mostrarAdvertencia("El valor de mercado no puede ser negativo.");
                return;
            }

            int idJugador = modoEdicion ? jugadorEditar.getIdJugador() : 0;

            Jugador jugador = new Jugador(
                    idJugador,
                    nombre,
                    fechaNacimiento,
                    posicion,
                    peso,
                    estatura,
                    valorMercado,
                    equipoSeleccionado.getIdEquipo()
            );

            if (modoEdicion) {
                jugadorDAO.actualizarJugador(jugador);
            } else {
                jugadorDAO.agregarJugador(jugador);
            }

            guardado = true;

            JOptionPane.showMessageDialog(
                    this,
                    modoEdicion ? "Jugador actualizado correctamente." : "Jugador registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Verifica los datos ingresados.\n\n" +
                            "Fecha: yyyy-MM-dd\n" +
                            "Peso: ejemplo 73.5\n" +
                            "Estatura: ejemplo 1.78\n" +
                            "Valor: ejemplo 150000000\n\n" +
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

    private static class EquipoItem {
        private final int idEquipo;
        private final String nombre;

        public EquipoItem(int idEquipo, String nombre) {
            this.idEquipo = idEquipo;
            this.nombre = nombre;
        }

        public int getIdEquipo() {
            return idEquipo;
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