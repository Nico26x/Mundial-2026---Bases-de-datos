package co.mundial2026.view;

import co.mundial2026.dao.EquipoDAO;
import co.mundial2026.dao.PartidoDAO;
import co.mundial2026.model.Equipo;
import co.mundial2026.model.Partido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PartidoFormDialog extends JDialog {

    private JTextField txtFechaHora;
    private JComboBox<EquipoItem> cbEquipoLocal;
    private JComboBox<EquipoItem> cbEquipoVisitante;
    private JComboBox<EstadioItem> cbEstadio;
    private JComboBox<GrupoItem> cbGrupo;
    private JTextField txtGolesLocal;
    private JTextField txtGolesVisitante;

    private final PartidoDAO partidoDAO;
    private final EquipoDAO equipoDAO;

    private Partido partidoEditar;
    private boolean modoEdicion;
    private boolean guardado;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PartidoFormDialog(JFrame parent) {
        super(parent, "Nuevo partido", true);

        this.partidoDAO = new PartidoDAO();
        this.equipoDAO = new EquipoDAO();
        this.partidoEditar = null;
        this.modoEdicion = false;
        this.guardado = false;

        setSize(560, 700);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarCombos();
    }

    public PartidoFormDialog(JFrame parent, Partido partidoEditar) {
        super(parent, "Editar partido", true);

        this.partidoDAO = new PartidoDAO();
        this.equipoDAO = new EquipoDAO();
        this.partidoEditar = partidoEditar;
        this.modoEdicion = true;
        this.guardado = false;

        setSize(560, 700);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
        cargarCombos();
        cargarDatosPartido();
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

        JLabel lblEtiqueta = new JLabel("MATCH CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(modoEdicion ? "Editar partido" : "Nuevo partido");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Registra la información del encuentro y su marcador.");
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

        txtFechaHora = crearCampoTexto();
        cbEquipoLocal = crearComboBoxEquipo();
        cbEquipoVisitante = crearComboBoxEquipo();
        cbEstadio = crearComboBoxEstadio();
        cbGrupo = crearComboBoxGrupo();
        txtGolesLocal = crearCampoTexto();
        txtGolesVisitante = crearCampoTexto();

        txtFechaHora.setToolTipText("Formato: yyyy-MM-dd HH:mm:ss");
        txtGolesLocal.setText("0");
        txtGolesVisitante.setText("0");

        agregarCampo(panel, gbc, "Fecha y hora (yyyy-MM-dd HH:mm:ss)", txtFechaHora);
        agregarCampo(panel, gbc, "Equipo local", cbEquipoLocal);
        agregarCampo(panel, gbc, "Equipo visitante", cbEquipoVisitante);
        agregarCampo(panel, gbc, "Estadio", cbEstadio);
        agregarCampo(panel, gbc, "Grupo", cbGrupo);
        agregarCampo(panel, gbc, "Goles local", txtGolesLocal);
        agregarCampo(panel, gbc, "Goles visitante", txtGolesVisitante);

        return panel;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(22, 0, 0, 0));

        RoundedButton btnCancelar = new RoundedButton("Cancelar", new Color(38, 47, 66), AppTheme.BLANCO);
        RoundedButton btnGuardar = new RoundedButton(
                modoEdicion ? "Guardar cambios" : "Guardar partido",
                AppTheme.DORADO,
                AppTheme.NEGRO_FONDO
        );

        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarPartido());

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

    private JComboBox<EquipoItem> crearComboBoxEquipo() {
        JComboBox<EquipoItem> combo = new JComboBox<>();
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 42));
        return combo;
    }

    private JComboBox<EstadioItem> crearComboBoxEstadio() {
        JComboBox<EstadioItem> combo = new JComboBox<>();
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 42));
        return combo;
    }

    private JComboBox<GrupoItem> crearComboBoxGrupo() {
        JComboBox<GrupoItem> combo = new JComboBox<>();
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setPreferredSize(new Dimension(0, 42));
        return combo;
    }

    private void cargarCombos() {
        cargarEquipos();
        cargarEstadios();
        cargarGrupos();
    }

    private void cargarEquipos() {
        try {
            List<Equipo> equipos = equipoDAO.obtenerEquipos();

            cbEquipoLocal.removeAllItems();
            cbEquipoVisitante.removeAllItems();

            for (Equipo equipo : equipos) {
                EquipoItem itemLocal = new EquipoItem(equipo.getIdEquipo(), equipo.getNombre());
                EquipoItem itemVisitante = new EquipoItem(equipo.getIdEquipo(), equipo.getNombre());

                cbEquipoLocal.addItem(itemLocal);
                cbEquipoVisitante.addItem(itemVisitante);
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

    private void cargarEstadios() {
        cbEstadio.removeAllItems();

        cbEstadio.addItem(new EstadioItem(1, "Estadio Azteca"));
        cbEstadio.addItem(new EstadioItem(2, "Estadio Akron"));
        cbEstadio.addItem(new EstadioItem(3, "Estadio BBVA"));
        cbEstadio.addItem(new EstadioItem(4, "Rose Bowl"));
        cbEstadio.addItem(new EstadioItem(5, "MetLife Stadium"));
        cbEstadio.addItem(new EstadioItem(6, "Hard Rock Stadium"));
        cbEstadio.addItem(new EstadioItem(7, "BMO Field"));
        cbEstadio.addItem(new EstadioItem(8, "BC Place"));
        cbEstadio.addItem(new EstadioItem(9, "Stade Olympique"));
    }

    private void cargarGrupos() {
        cbGrupo.removeAllItems();

        cbGrupo.addItem(new GrupoItem(1, "Grupo A"));
        cbGrupo.addItem(new GrupoItem(2, "Grupo B"));
        cbGrupo.addItem(new GrupoItem(3, "Grupo C"));
        cbGrupo.addItem(new GrupoItem(4, "Grupo D"));
        cbGrupo.addItem(new GrupoItem(5, "Grupo E"));
        cbGrupo.addItem(new GrupoItem(6, "Grupo F"));
        cbGrupo.addItem(new GrupoItem(7, "Grupo G"));
        cbGrupo.addItem(new GrupoItem(8, "Grupo H"));
        cbGrupo.addItem(new GrupoItem(9, "Grupo I"));
        cbGrupo.addItem(new GrupoItem(10, "Grupo J"));
        cbGrupo.addItem(new GrupoItem(11, "Grupo K"));
        cbGrupo.addItem(new GrupoItem(12, "Grupo L"));
    }

    private void cargarDatosPartido() {
        if (partidoEditar == null) {
            return;
        }

        txtFechaHora.setText(partidoEditar.getFechaHora().format(formatter));
        txtGolesLocal.setText(String.valueOf(partidoEditar.getGolesLocal()));
        txtGolesVisitante.setText(String.valueOf(partidoEditar.getGolesVisitante()));

        seleccionarEquipo(cbEquipoLocal, partidoEditar.getIdEquipoLocal());
        seleccionarEquipo(cbEquipoVisitante, partidoEditar.getIdEquipoVisitante());
        seleccionarEstadio(partidoEditar.getIdEstadio());
        seleccionarGrupo(partidoEditar.getIdGrupo());
    }

    private void seleccionarEquipo(JComboBox<EquipoItem> combo, int idEquipo) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            EquipoItem item = combo.getItemAt(i);

            if (item.getIdEquipo() == idEquipo) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void seleccionarEstadio(int idEstadio) {
        for (int i = 0; i < cbEstadio.getItemCount(); i++) {
            EstadioItem item = cbEstadio.getItemAt(i);

            if (item.getIdEstadio() == idEstadio) {
                cbEstadio.setSelectedIndex(i);
                break;
            }
        }
    }

    private void seleccionarGrupo(int idGrupo) {
        for (int i = 0; i < cbGrupo.getItemCount(); i++) {
            GrupoItem item = cbGrupo.getItemAt(i);

            if (item.getIdGrupo() == idGrupo) {
                cbGrupo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void guardarPartido() {
        try {
            String fechaTexto = txtFechaHora.getText().trim();

            EquipoItem local = (EquipoItem) cbEquipoLocal.getSelectedItem();
            EquipoItem visitante = (EquipoItem) cbEquipoVisitante.getSelectedItem();
            EstadioItem estadio = (EstadioItem) cbEstadio.getSelectedItem();
            GrupoItem grupo = (GrupoItem) cbGrupo.getSelectedItem();

            String golesLocalTexto = txtGolesLocal.getText().trim();
            String golesVisitanteTexto = txtGolesVisitante.getText().trim();

            if (fechaTexto.isEmpty() || local == null || visitante == null || estadio == null || grupo == null
                    || golesLocalTexto.isEmpty() || golesVisitanteTexto.isEmpty()) {
                mostrarAdvertencia("Todos los campos son obligatorios.");
                return;
            }

            if (local.getIdEquipo() == visitante.getIdEquipo()) {
                mostrarAdvertencia("El equipo local no puede ser igual al equipo visitante.");
                return;
            }

            LocalDateTime fechaHora = LocalDateTime.parse(fechaTexto, formatter);
            int golesLocal = Integer.parseInt(golesLocalTexto);
            int golesVisitante = Integer.parseInt(golesVisitanteTexto);

            if (golesLocal < 0 || golesVisitante < 0) {
                mostrarAdvertencia("Los goles no pueden ser negativos.");
                return;
            }

            int idPartido = modoEdicion ? partidoEditar.getIdPartido() : 0;

            Partido partido = new Partido(
                    idPartido,
                    fechaHora,
                    estadio.getIdEstadio(),
                    grupo.getIdGrupo(),
                    local.getIdEquipo(),
                    visitante.getIdEquipo(),
                    golesLocal,
                    golesVisitante
            );

            if (modoEdicion) {
                partidoDAO.actualizarPartido(partido);
            } else {
                partidoDAO.agregarPartido(partido);
            }

            guardado = true;

            JOptionPane.showMessageDialog(
                    this,
                    modoEdicion ? "Partido actualizado correctamente." : "Partido registrado correctamente.",
                    "Operación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Verifica los datos ingresados.\n\n" +
                            "Fecha: yyyy-MM-dd HH:mm:ss\n" +
                            "Goles: números enteros no negativos\n\n" +
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

    private static class EstadioItem {
        private final int idEstadio;
        private final String nombre;

        public EstadioItem(int idEstadio, String nombre) {
            this.idEstadio = idEstadio;
            this.nombre = nombre;
        }

        public int getIdEstadio() {
            return idEstadio;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static class GrupoItem {
        private final int idGrupo;
        private final String nombre;

        public GrupoItem(int idGrupo, String nombre) {
            this.idGrupo = idGrupo;
            this.nombre = nombre;
        }

        public int getIdGrupo() {
            return idGrupo;
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