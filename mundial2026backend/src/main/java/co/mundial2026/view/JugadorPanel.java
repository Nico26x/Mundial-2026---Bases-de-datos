package co.mundial2026.view;

import co.mundial2026.dao.JugadorDAO;
import co.mundial2026.model.Jugador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.regex.Pattern;

public class JugadorPanel extends JPanel {

    private JTable tablaJugadores;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtBuscar;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;

    private JLabel lblTotalJugadores;
    private JLabel lblValorPromedio;
    private JLabel lblSub21;
    private JLabel lblTotalEquipos;

    private final JugadorDAO jugadorDAO;

    public JugadorPanel() {
        this.jugadorDAO = new JugadorDAO();

        setLayout(new BorderLayout(0, 18));
        setBackground(AppTheme.NEGRO_PANEL);
        setBorder(new EmptyBorder(0, 0, 0, 0));

        initComponents();
        cargarJugadores();
    }

    private void initComponents() {
        add(crearHeader(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setOpaque(false);

        centro.add(crearBarraAcciones(), BorderLayout.NORTH);

        JPanel zonaTabla = new JPanel(new BorderLayout(0, 14));
        zonaTabla.setOpaque(false);

        zonaTabla.add(crearTarjetasResumen(), BorderLayout.NORTH);
        zonaTabla.add(crearTabla(), BorderLayout.CENTER);
        zonaTabla.add(crearBotonesInferiores(), BorderLayout.SOUTH);

        centro.add(zonaTabla, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        HeaderPanel header = new HeaderPanel();
        header.setPreferredSize(new Dimension(0, 160));
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel lblEtiqueta = new JLabel("PLAYER CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Gestión de jugadores");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Administra la información deportiva, física y económica de los jugadores registrados.");
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

        JLabel lblBuscar = new JLabel("Buscar jugador");
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
                filtrarJugadores();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarJugadores();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarJugadores();
            }
        });

        panel.add(panelBusqueda, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTarjetasResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 14, 0));
        panel.setOpaque(false);

        lblTotalJugadores = new JLabel("0");
        lblValorPromedio = new JLabel("$0");
        lblSub21 = new JLabel("0");
        lblTotalEquipos = new JLabel("0");

        panel.add(crearTarjetaResumen("Total jugadores", lblTotalJugadores));
        panel.add(crearTarjetaResumen("Valor promedio", lblValorPromedio));
        panel.add(crearTarjetaResumen("Jugadores sub-21", lblSub21));
        panel.add(crearTarjetaResumen("Equipos registrados", lblTotalEquipos));

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
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Fecha nacimiento");
        modeloTabla.addColumn("Edad");
        modeloTabla.addColumn("Posición");
        modeloTabla.addColumn("Peso");
        modeloTabla.addColumn("Estatura");
        modeloTabla.addColumn("Valor mercado");
        modeloTabla.addColumn("Equipo");
        modeloTabla.addColumn("ID Equipo");

        tablaJugadores = new JTable(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        tablaJugadores.setRowSorter(sorter);
        if (tablaJugadores.getColumnModel().getColumnCount() > 9) {
            tablaJugadores.getColumnModel().getColumn(9).setMinWidth(0);
            tablaJugadores.getColumnModel().getColumn(9).setMaxWidth(0);
            tablaJugadores.getColumnModel().getColumn(9).setWidth(0);
        }
        tablaJugadores.setRowHeight(34);
        tablaJugadores.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaJugadores.setForeground(AppTheme.BLANCO);
        tablaJugadores.setBackground(new Color(18, 22, 34));
        tablaJugadores.setSelectionBackground(new Color(224, 182, 77));
        tablaJugadores.setSelectionForeground(AppTheme.NEGRO_FONDO);
        tablaJugadores.setGridColor(new Color(45, 50, 62));
        tablaJugadores.setShowVerticalLines(false);

        JTableHeader header = tablaJugadores.getTableHeader();
        header.setBackground(new Color(28, 33, 45));
        header.setForeground(AppTheme.BLANCO);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 38));

        JScrollPane scrollPane = new JScrollPane(tablaJugadores);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(18, 22, 34));

        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI());

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        contenedor.add(scrollPane, BorderLayout.CENTER);

        return contenedor;
    }

    private JPanel crearBotonesInferiores() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setOpaque(false);

        btnNuevo = new RoundedButton("+ Nuevo jugador", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        btnEditar = new RoundedButton("Editar", new Color(38, 47, 66), AppTheme.BLANCO);
        btnEliminar = new RoundedButton("Eliminar", new Color(90, 20, 28), AppTheme.BLANCO);
        btnActualizar = new RoundedButton("Actualizar", AppTheme.DORADO, AppTheme.NEGRO_FONDO);

        btnNuevo.addActionListener(e -> abrirFormularioNuevoJugador());

        btnEditar.addActionListener(e -> editarJugadorSeleccionado());

        btnEliminar.addActionListener(e -> eliminarJugadorSeleccionado());

        btnActualizar.addActionListener(e -> cargarJugadores());

        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);

        return panel;
    }

    private void cargarJugadores() {
        modeloTabla.setRowCount(0);

        try {
            List<Jugador> jugadores = jugadorDAO.obtenerJugadores();

            double sumaValores = 0;
            int sub21 = 0;

            for (Jugador jugador : jugadores) {
                int edad = calcularEdadSimple(jugador.getFechaNacimiento().toString());

                Object[] fila = {
                        jugador.getIdJugador(),
                        jugador.getNombre(),
                        jugador.getFechaNacimiento(),
                        edad,
                        jugador.getPosicion(),
                        jugador.getPeso(),
                        jugador.getEstatura(),
                        formatearValor(jugador.getValorMercado()),
                        obtenerNombreEquipo(jugador.getIdEquipo()),
                        jugador.getIdEquipo()
                };

                modeloTabla.addRow(fila);

                sumaValores += jugador.getValorMercado();

                if (edad < 21) {
                    sub21++;
                }
            }

            lblTotalJugadores.setText(String.valueOf(jugadores.size()));

            if (!jugadores.isEmpty()) {
                double promedio = sumaValores / jugadores.size();
                lblValorPromedio.setText("$" + String.format("%,.0f", promedio));
            } else {
                lblValorPromedio.setText("$0");
            }

            lblSub21.setText(String.valueOf(sub21));
            lblTotalEquipos.setText("—");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar jugadores:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void filtrarJugadores() {
        String texto = txtBuscar.getText().trim();

        if (sorter == null) {
            return;
        }

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 1));
        }
    }

    private void abrirFormularioNuevoJugador() {
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        JugadorFormDialog dialog = new JugadorFormDialog(ventanaPadre);
        dialog.setVisible(true);

        if (dialog.isGuardado()) {
            cargarJugadores();
        }
    }

    private void eliminarJugadorSeleccionado() {
        int filaSeleccionada = tablaJugadores.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un jugador para eliminar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int filaModelo = tablaJugadores.convertRowIndexToModel(filaSeleccionada);
        int idJugador = (int) modeloTabla.getValueAt(filaModelo, 0);
        String nombreJugador = modeloTabla.getValueAt(filaModelo, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar al jugador " + nombreJugador + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                jugadorDAO.eliminarJugador(idJugador);

                JOptionPane.showMessageDialog(
                        this,
                        "Jugador eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );

                cargarJugadores();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar jugador:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void editarJugadorSeleccionado() {
        int filaSeleccionada = tablaJugadores.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un jugador para editar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int filaModelo = tablaJugadores.convertRowIndexToModel(filaSeleccionada);

            int idJugador = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
            String nombre = modeloTabla.getValueAt(filaModelo, 1).toString();
            java.time.LocalDate fechaNacimiento = java.time.LocalDate.parse(modeloTabla.getValueAt(filaModelo, 2).toString());

            // Columna 3 es Edad, por eso se salta.
            String posicion = modeloTabla.getValueAt(filaModelo, 4).toString();
            double peso = Double.parseDouble(modeloTabla.getValueAt(filaModelo, 5).toString());
            double estatura = Double.parseDouble(modeloTabla.getValueAt(filaModelo, 6).toString());

            String valorTexto = modeloTabla.getValueAt(filaModelo, 7).toString()
                    .replace("$", "")
                    .replace(",", "")
                    .replace(".", "")
                    .trim();

            double valorMercado = Double.parseDouble(valorTexto);

            // Columna 8 es nombre del equipo.
            // Columna 9 es ID Equipo oculto.
            int idEquipo = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 9).toString());

            Jugador jugador = new Jugador(
                    idJugador,
                    nombre,
                    fechaNacimiento,
                    posicion,
                    peso,
                    estatura,
                    valorMercado,
                    idEquipo
            );

            JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

            JugadorFormDialog dialog = new JugadorFormDialog(ventanaPadre, jugador);
            dialog.setVisible(true);

            if (dialog.isGuardado()) {
                cargarJugadores();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo abrir el formulario de edición.\n\nDetalle: " + e.getMessage(),
                    "Error al editar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int calcularEdadSimple(String fechaNacimiento) {
        try {
            int anioNacimiento = Integer.parseInt(fechaNacimiento.substring(0, 4));
            return 2026 - anioNacimiento;
        } catch (Exception e) {
            return 0;
        }
    }

    private String obtenerNombreEquipo(int idEquipo) {
        switch (idEquipo) {
            case 1:
                return "Brasil";
            case 2:
                return "Argentina";
            case 3:
                return "Francia";
            case 4:
                return "España";
            case 5:
                return "México";
            case 6:
                return "Estados Unidos";
            case 7:
                return "Canadá";
            case 8:
                return "Japón";
            case 9:
                return "Portugal";
            default:
                return "Equipo " + idEquipo;
        }
    }

    private String formatearValor(double valor) {
        return "$" + String.format("%,.0f", valor);
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