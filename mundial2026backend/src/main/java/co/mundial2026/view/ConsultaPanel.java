package co.mundial2026.view;

import co.mundial2026.dao.ConsultaDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.ListCellRenderer;

public class ConsultaPanel extends JPanel {

    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblTituloConsulta;
    private JLabel lblDescripcionConsulta;

    private JComboBox<EstadioItem> cbEstadios;

    private final ConsultaDAO consultaDAO;

    public ConsultaPanel() {
        this.consultaDAO = new ConsultaDAO();

        setLayout(new BorderLayout(0, 18));
        setBackground(AppTheme.NEGRO_PANEL);

        initComponents();
        cargarConsultaJugadorCostoso();
    }

    private void initComponents() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel header = crearHeader();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel opciones = crearPanelOpciones();
        opciones.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel resultados = crearPanelResultados();
        resultados.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultados.setPreferredSize(new Dimension(0, 560));

        contenido.add(header);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(opciones);
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

        JLabel lblEtiqueta = new JLabel("QUERY CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Consultas del sistema");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel(
                "Visualiza información estratégica del Mundial 2026 a partir de los datos registrados.");
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
        panel.setLayout(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel botones = new JPanel(new GridLayout(2, 2, 12, 12));
        botones.setOpaque(false);

        RoundedButton btnJugadorCostoso = new RoundedButton("Jugador más costoso", AppTheme.DORADO,
                AppTheme.NEGRO_FONDO);
        RoundedButton btnPartidosEstadio = new RoundedButton("Partidos por estadio", AppTheme.DORADO,
                AppTheme.NEGRO_FONDO);
        RoundedButton btnEquipoCostoso = new RoundedButton("Equipo más costoso por país", AppTheme.DORADO,
                AppTheme.NEGRO_FONDO);
        RoundedButton btnMenores21 = new RoundedButton("Cantidad sub-21", AppTheme.DORADO, AppTheme.NEGRO_FONDO);

        btnJugadorCostoso.addActionListener(e -> cargarConsultaJugadorCostoso());
        btnPartidosEstadio.addActionListener(e -> cargarConsultaPartidosPorEstadio());
        btnEquipoCostoso.addActionListener(e -> cargarConsultaEquipoCostoso());
        btnMenores21.addActionListener(e -> cargarConsultaMenores21());

        botones.add(btnJugadorCostoso);
        botones.add(btnPartidosEstadio);
        botones.add(btnEquipoCostoso);
        botones.add(btnMenores21);

        JPanel filtros = new JPanel(new BorderLayout(12, 0));
        filtros.setOpaque(false);

        JLabel lblEstadio = new JLabel("Estadio:");
        lblEstadio.setForeground(AppTheme.BLANCO);
        lblEstadio.setFont(new Font("SansSerif", Font.BOLD, 13));

        cbEstadios = new JComboBox<>();
        cbEstadios.setFont(AppTheme.FUENTE_NORMAL);
        cbEstadios.setForeground(AppTheme.BLANCO);
        cbEstadios.setBackground(new Color(28, 33, 45));
        cbEstadios.setPreferredSize(new Dimension(260, 42));
        cbEstadios.setBorder(BorderFactory.createLineBorder(new Color(55, 60, 72), 1));
        cbEstadios.setFocusable(false);
        cbEstadios.setOpaque(true);
        cbEstadios.setEditable(false);
        cbEstadios.setUI(new DarkComboBoxUI());

        cbEstadios.setRenderer(new DefaultListCellRenderer() {
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
        cargarEstadiosCombo();
        cbEstadios.addActionListener(e -> {
            if (lblTituloConsulta != null
                    && lblTituloConsulta.getText().equals("Partidos por estadio seleccionado")) {
                cargarConsultaPartidosPorEstadio();
            }
        });

        filtros.add(lblEstadio, BorderLayout.WEST);
        filtros.add(cbEstadios, BorderLayout.CENTER);

        panel.add(botones, BorderLayout.CENTER);
        panel.add(filtros, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarEstadiosCombo() {
        try {
            cbEstadios.removeAllItems();

            List<Object[]> estadios = consultaDAO.obtenerEstadios();

            for (Object[] estadio : estadios) {
                int idEstadio = Integer.parseInt(estadio[0].toString());
                String nombre = estadio[1].toString();

                cbEstadios.addItem(new EstadioItem(idEstadio, nombre));
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);

        RoundedPanel descripcion = new RoundedPanel(24, new Color(18, 22, 34));
        descripcion.setLayout(new BoxLayout(descripcion, BoxLayout.Y_AXIS));
        descripcion.setBorder(new EmptyBorder(18, 20, 18, 20));

        lblTituloConsulta = new JLabel("Consulta");
        lblTituloConsulta.setForeground(AppTheme.BLANCO);
        lblTituloConsulta.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTituloConsulta.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblDescripcionConsulta = new JLabel("Resultado de la consulta seleccionada.");
        lblDescripcionConsulta.setForeground(AppTheme.GRIS_TEXTO);
        lblDescripcionConsulta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescripcionConsulta.setAlignmentX(Component.LEFT_ALIGNMENT);

        descripcion.add(lblTituloConsulta);
        descripcion.add(Box.createVerticalStrut(6));
        descripcion.add(lblDescripcionConsulta);

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
        scrollPane.setPreferredSize(new Dimension(0, 420));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(18, 22, 34));
        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        contenedor.add(scrollPane, BorderLayout.CENTER);

        return contenedor;
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

    private void cargarConsultaJugadorCostoso() {
        lblTituloConsulta.setText("Jugador más costoso por confederación");
        lblDescripcionConsulta.setText("Muestra el jugador con mayor valor de mercado dentro de cada confederación.");

        limpiarTabla("Confederación", "Nombre confederación", "Jugador", "Equipo", "Posición", "Edad", "Valor mercado");

        try {
            List<Object[]> datos = consultaDAO.jugadorMasCostosoPorConfederacion();

            for (Object[] fila : datos) {
                modeloTabla.addRow(new Object[] {
                        fila[0],
                        fila[1],
                        fila[2],
                        fila[3],
                        fila[4],
                        fila[5],
                        formatearValor(Double.parseDouble(fila[6].toString()))
                });
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void cargarConsultaPartidosPorEstadio() {
        lblTituloConsulta.setText("Partidos por estadio seleccionado");
        lblDescripcionConsulta
                .setText("Lista los partidos que se llevarán a cabo en el estadio elegido por el usuario.");

        limpiarTabla("Estadio", "Fecha y hora", "Local", "Visitante", "Grupo", "Marcador");

        EstadioItem estadioSeleccionado = (EstadioItem) cbEstadios.getSelectedItem();

        if (estadioSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un estadio.",
                    "Sin estadio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Object[]> datos = consultaDAO.partidosPorEstadio(estadioSeleccionado.getIdEstadio());

            for (Object[] fila : datos) {
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void cargarConsultaEquipoCostoso() {
        lblTituloConsulta.setText("Equipo más costoso por país anfitrión");
        lblDescripcionConsulta.setText("Determina el equipo más costoso de los que jugarán en cada país anfitrión.");

        limpiarTabla("País anfitrión", "Equipo", "País equipo", "Valor total", "Jugadores");

        try {
            List<Object[]> datos = consultaDAO.equipoMasCostosoPorPaisAnfitrion();

            for (Object[] fila : datos) {
                modeloTabla.addRow(new Object[] {
                        fila[0],
                        fila[1],
                        fila[2],
                        formatearValor(Double.parseDouble(fila[3].toString())),
                        fila[4]
                });
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void cargarConsultaMenores21() {
        lblTituloConsulta.setText("Cantidad de jugadores menores de 21 por equipo");
        lblDescripcionConsulta.setText("Muestra la cantidad de jugadores sub-21 registrados en cada selección.");

        limpiarTabla("Equipo", "País", "Cantidad sub-21");

        try {
            List<Object[]> datos = consultaDAO.cantidadJugadoresMenores21PorEquipo();

            for (Object[] fila : datos) {
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    private void mostrarError(Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Error al ejecutar consulta:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
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
                false
        );

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
                true
        );
    }

    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {
            @Override
            protected JScrollPane createScroller() {
                JScrollPane scroller = new JScrollPane(
                        list,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                );

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