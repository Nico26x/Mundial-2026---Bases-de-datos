package co.mundial2026.view;

import co.mundial2026.dao.ReporteDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ReportePanel extends JPanel {

    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    private JLabel lblTituloReporte;
    private JLabel lblDescripcionReporte;

    private JPanel panelFiltros;

    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;

    private JTextField txtPesoMin;
    private JTextField txtPesoMax;
    private JTextField txtEstaturaMin;
    private JTextField txtEstaturaMax;

    private JComboBox<ItemCombo> cbEquipos;
    private JComboBox<ItemCombo> cbConfederaciones;

    private final ReporteDAO reporteDAO;

    public ReportePanel() {
        this.reporteDAO = new ReporteDAO();

        setLayout(new BorderLayout());
        setBackground(AppTheme.NEGRO_PANEL);

        initComponents();
        mostrarFiltrosBitacora();
    }

    private void initComponents() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel header = crearHeader();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));

        JPanel opciones = crearPanelOpciones();
        opciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        opciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));

        panelFiltros = new RoundedPanel(26, new Color(18, 22, 34));
        panelFiltros.setLayout(new BorderLayout());
        panelFiltros.setBorder(new EmptyBorder(18, 18, 18, 18));
        panelFiltros.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 185));

        JPanel resultados = crearPanelResultados();
        resultados.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultados.setPreferredSize(new Dimension(0, 500));
        resultados.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        contenido.add(header);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(opciones);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(panelFiltros);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(resultados);

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

        JLabel lblEtiqueta = new JLabel("REPORT CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Reportes del sistema");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Genera reportes filtrados con información clave del Mundial 2026.");
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

    private JPanel crearPanelOpciones() {
        RoundedPanel panel = new RoundedPanel(26, new Color(18, 22, 34));
        panel.setLayout(new GridLayout(2, 2, 12, 12));
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        RoundedButton btnBitacora = new RoundedButton("Bitácora usuarios", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        RoundedButton btnJugadores = new RoundedButton("Jugadores filtrados", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        RoundedButton btnConfederacion = new RoundedButton("Valor por confederación", AppTheme.DORADO,
                AppTheme.NEGRO_FONDO);
        RoundedButton btnPaises = new RoundedButton("Países por anfitrión", AppTheme.DORADO, AppTheme.NEGRO_FONDO);

        btnBitacora.addActionListener(e -> mostrarFiltrosBitacora());
        btnJugadores.addActionListener(e -> mostrarFiltrosJugadores());
        btnConfederacion.addActionListener(e -> mostrarFiltrosConfederacion());
        btnPaises.addActionListener(e -> cargarReportePaisesPorAnfitrion());

        panel.add(btnBitacora);
        panel.add(btnJugadores);
        panel.add(btnConfederacion);
        panel.add(btnPaises);

        return panel;
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        RoundedPanel descripcion = new RoundedPanel(24, new Color(18, 22, 34));
        descripcion.setLayout(new BoxLayout(descripcion, BoxLayout.Y_AXIS));
        descripcion.setBorder(new EmptyBorder(18, 20, 18, 20));

        lblTituloReporte = new JLabel("Reporte");
        lblTituloReporte.setForeground(AppTheme.BLANCO);
        lblTituloReporte.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTituloReporte.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblDescripcionReporte = new JLabel("Resultado del reporte seleccionado.");
        lblDescripcionReporte.setForeground(AppTheme.GRIS_TEXTO);
        lblDescripcionReporte.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescripcionReporte.setAlignmentX(Component.LEFT_ALIGNMENT);

        descripcion.add(lblTituloReporte);
        descripcion.add(Box.createVerticalStrut(6));
        descripcion.add(lblDescripcionReporte);

        panel.add(descripcion, BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTabla() {
        RoundedPanel contenedor = new RoundedPanel(26, new Color(18, 22, 34));
        contenedor.setLayout(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(18, 18, 18, 18));

        modeloTabla = new DefaultTableModel();
        tablaResultados = new JTable(modeloTabla);

        tablaResultados.setRowHeight(34);
        tablaResultados.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaResultados.setForeground(AppTheme.BLANCO);
        tablaResultados.setBackground(new Color(18, 22, 34));
        tablaResultados.setSelectionBackground(AppTheme.DORADO);
        tablaResultados.setSelectionForeground(AppTheme.NEGRO_FONDO);
        tablaResultados.setGridColor(new Color(45, 50, 62));
        tablaResultados.setShowVerticalLines(false);

        JTableHeader header = tablaResultados.getTableHeader();
        header.setBackground(new Color(28, 33, 45));
        header.setForeground(AppTheme.BLANCO);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 38));

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setPreferredSize(new Dimension(0, 360));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(18, 22, 34));
        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        contenedor.add(scrollPane, BorderLayout.CENTER);

        return contenedor;
    }

    private void mostrarFiltrosBitacora() {
        lblTituloReporte.setText("Bitácora de usuarios");
        lblDescripcionReporte.setText("Lista usuarios que ingresaron o salieron dentro de un rango de fecha y hora.");

        panelFiltros.removeAll();

        JPanel grid = new JPanel(new GridLayout(1, 3, 12, 0));
        grid.setOpaque(false);

        txtFechaInicio = crearCampoTexto("2026-01-01 00:00:00");
        txtFechaFin = crearCampoTexto("2026-12-31 23:59:59");

        grid.add(crearCampoConEtiqueta("Fecha inicio", txtFechaInicio));
        grid.add(crearCampoConEtiqueta("Fecha fin", txtFechaFin));

        RoundedButton btnGenerar = new RoundedButton("Generar reporte", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        btnGenerar.addActionListener(e -> cargarReporteBitacora());
        grid.add(btnGenerar);

        panelFiltros.add(grid, BorderLayout.CENTER);
        panelFiltros.revalidate();
        panelFiltros.repaint();

        limpiarTabla("Usuario", "Tipo usuario", "Ingreso", "Salida", "Tiempo sesión");
    }

    private void mostrarFiltrosJugadores() {
        lblTituloReporte.setText("Jugadores filtrados");
        lblDescripcionReporte.setText("Lista jugadores cuyo peso, estatura y equipo están dentro de lo solicitado.");

        panelFiltros.removeAll();

        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 14));
        grid.setOpaque(false);

        txtPesoMin = crearCampoTexto("0");
        txtPesoMax = crearCampoTexto("200");
        txtEstaturaMin = crearCampoTexto("0");
        txtEstaturaMax = crearCampoTexto("2.50");

        cbEquipos = crearCombo();
        cargarEquiposCombo();

        RoundedButton btnGenerar = new RoundedButton("Generar reporte", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        btnGenerar.addActionListener(e -> cargarReporteJugadoresFiltrados());

        grid.add(crearCampoConEtiqueta("Peso mínimo", txtPesoMin));
        grid.add(crearCampoConEtiqueta("Peso máximo", txtPesoMax));
        grid.add(crearCampoConEtiqueta("Equipo", cbEquipos));
        grid.add(crearCampoConEtiqueta("Estatura mínima", txtEstaturaMin));
        grid.add(crearCampoConEtiqueta("Estatura máxima", txtEstaturaMax));
        grid.add(btnGenerar);

        panelFiltros.add(grid, BorderLayout.CENTER);
        panelFiltros.revalidate();
        panelFiltros.repaint();

        limpiarTabla("Jugador", "Equipo", "Posición", "Peso", "Estatura", "Valor mercado", "Edad");
    }

    private void mostrarFiltrosConfederacion() {
        lblTituloReporte.setText("Valor total por equipo y confederación");
        lblDescripcionReporte
                .setText("Determina el valor total de jugadores por equipo dentro de una confederación específica.");

        panelFiltros.removeAll();

        JPanel grid = new JPanel(new GridLayout(1, 3, 12, 0));
        grid.setOpaque(false);

        cbConfederaciones = crearCombo();
        cargarConfederacionesCombo();

        RoundedButton btnGenerar = new RoundedButton("Generar reporte", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        btnGenerar.addActionListener(e -> cargarReporteValorPorConfederacion());

        grid.add(crearCampoConEtiqueta("Confederación", cbConfederaciones));
        grid.add(btnGenerar);

        JPanel espacio = new JPanel();
        espacio.setOpaque(false);
        grid.add(espacio);

        panelFiltros.add(grid, BorderLayout.CENTER);
        panelFiltros.revalidate();
        panelFiltros.repaint();

        limpiarTabla("Confederación", "Equipo", "Jugadores", "Valor total", "Valor promedio");
    }

    private void cargarReporteBitacora() {
        limpiarTabla("Usuario", "Tipo usuario", "Ingreso", "Salida", "Tiempo sesión");

        try {
            List<Object[]> datos = reporteDAO.reporteBitacoraUsuarios(
                    txtFechaInicio.getText().trim(),
                    txtFechaFin.getText().trim());

            for (Object[] fila : datos) {
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void cargarReporteJugadoresFiltrados() {
        limpiarTabla("Jugador", "Equipo", "Posición", "Peso", "Estatura", "Valor mercado", "Edad");

        try {
            double pesoMin = Double.parseDouble(txtPesoMin.getText().trim());
            double pesoMax = Double.parseDouble(txtPesoMax.getText().trim());
            double estaturaMin = Double.parseDouble(txtEstaturaMin.getText().trim());
            double estaturaMax = Double.parseDouble(txtEstaturaMax.getText().trim());

            ItemCombo equipo = (ItemCombo) cbEquipos.getSelectedItem();
            int idEquipo = equipo != null ? equipo.getId() : 0;

            List<Object[]> datos = reporteDAO.reporteJugadoresFiltrados(
                    pesoMin,
                    pesoMax,
                    estaturaMin,
                    estaturaMax,
                    idEquipo);

            for (Object[] fila : datos) {
                modeloTabla.addRow(new Object[] {
                        fila[0],
                        fila[1],
                        fila[2],
                        fila[3],
                        fila[4],
                        formatearValor(Double.parseDouble(fila[5].toString())),
                        fila[6]
                });
            }

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void cargarReporteValorPorConfederacion() {
        limpiarTabla("Confederación", "Equipo", "Jugadores", "Valor total", "Valor promedio");

        try {
            ItemCombo confederacion = (ItemCombo) cbConfederaciones.getSelectedItem();

            if (confederacion == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debes seleccionar una confederación.",
                        "Sin confederación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Object[]> datos = reporteDAO.reporteValorJugadoresPorConfederacion(confederacion.getId());

            for (Object[] fila : datos) {
                modeloTabla.addRow(new Object[] {
                        fila[0],
                        fila[1],
                        fila[2],
                        formatearValor(Double.parseDouble(fila[3].toString())),
                        formatearValor(Double.parseDouble(fila[4].toString()))
                });
            }

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void cargarReportePaisesPorAnfitrion() {
        lblTituloReporte.setText("Países que jugarán en cada país anfitrión");
        lblDescripcionReporte
                .setText("Lista los países/equipos que jugarán partidos en México, Estados Unidos o Canadá.");

        panelFiltros.removeAll();

        JLabel lbl = new JLabel("Este reporte no requiere filtros. Se genera automáticamente.");
        lbl.setForeground(AppTheme.GRIS_TEXTO);
        lbl.setFont(AppTheme.FUENTE_NORMAL);
        panelFiltros.add(lbl, BorderLayout.CENTER);
        panelFiltros.revalidate();
        panelFiltros.repaint();

        limpiarTabla("País anfitrión", "País equipo", "Equipo", "Partidos");

        try {
            List<Object[]> datos = reporteDAO.reportePaisesPorPaisAnfitrion();

            for (Object[] fila : datos) {
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private JPanel crearCampoConEtiqueta(String etiqueta, JComponent campo) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);

        JLabel label = new JLabel(etiqueta);
        label.setForeground(AppTheme.BLANCO);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);

        return panel;
    }

    private JTextField crearCampoTexto(String textoInicial) {
        JTextField campo = new JTextField(textoInicial);
        campo.setFont(AppTheme.FUENTE_NORMAL);
        campo.setForeground(AppTheme.BLANCO);
        campo.setBackground(new Color(28, 33, 45));
        campo.setCaretColor(AppTheme.BLANCO);
        campo.setPreferredSize(new Dimension(0, 46));
        campo.setMinimumSize(new Dimension(0, 46));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 60, 72), 1),
                new EmptyBorder(10, 14, 10, 14)));
        return campo;
    }

    private JComboBox<ItemCombo> crearCombo() {
        JComboBox<ItemCombo> combo = new JComboBox<>();
        combo.setFont(AppTheme.FUENTE_NORMAL);
        combo.setForeground(AppTheme.BLANCO);
        combo.setBackground(new Color(28, 33, 45));
        combo.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 72), 1));
        combo.setFocusable(false);
        combo.setOpaque(true);
        combo.setEditable(false);
        combo.setPreferredSize(new Dimension(0, 46));
        combo.setMinimumSize(new Dimension(0, 46));
        combo.setUI(new DarkComboBoxUI());

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus);

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

    private void cargarEquiposCombo() {
        try {
            cbEquipos.removeAllItems();
            cbEquipos.addItem(new ItemCombo(0, "Todos"));

            List<Object[]> equipos = reporteDAO.obtenerEquipos();

            for (Object[] equipo : equipos) {
                cbEquipos.addItem(new ItemCombo(
                        Integer.parseInt(equipo[0].toString()),
                        equipo[1].toString()));
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void cargarConfederacionesCombo() {
        try {
            cbConfederaciones.removeAllItems();

            List<Object[]> confederaciones = reporteDAO.obtenerConfederaciones();

            for (Object[] confederacion : confederaciones) {
                cbConfederaciones.addItem(new ItemCombo(
                        Integer.parseInt(confederacion[0].toString()),
                        confederacion[1].toString()));
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void limpiarTabla(String... columnas) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);

        for (String columna : columnas) {
            modeloTabla.addColumn(columna);
        }
    }

    private String formatearValor(double valor) {
        return "$" + String.format("%,.0f", valor);
    }

    private void mostrarError(Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Error al generar reporte:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private static class ItemCombo {
        private final int id;
        private final String nombre;

        public ItemCombo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
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
                    getWidth(), getHeight(), new Color(18, 22, 34));

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
                    10);

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
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            ListCellRenderer<Object> renderer = comboBox.getRenderer();
            Component c = renderer.getListCellRendererComponent(
                    listBox,
                    comboBox.getSelectedItem(),
                    -1,
                    false,
                    false);

            c.setBackground(new Color(28, 33, 45));
            c.setForeground(AppTheme.BLANCO);
            c.setFont(AppTheme.FUENTE_NORMAL);

            currentValuePane.paintComponent(
                    g,
                    c,
                    comboBox,
                    bounds.x,
                    bounds.y,
                    bounds.width,
                    bounds.height,
                    true);
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    JScrollPane scroller = new JScrollPane(
                            list,
                            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                    scroller.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 72), 1));
                    scroller.getViewport().setBackground(new Color(28, 33, 45));
                    scroller.setBackground(new Color(28, 33, 45));
                    scroller.getVerticalScrollBar().setUI(new DarkScrollBarUI());
                    scroller.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

                    return scroller;
                }
            };

            popup.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 72), 1));
            return popup;
        }
    }
}