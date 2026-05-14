package co.mundial2026.view;

import co.mundial2026.dao.EquipoDAO;
import co.mundial2026.model.Equipo;

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

public class EquipoPanel extends JPanel {

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtBuscar;

    private JLabel lblTotalEquipos;
    private JLabel lblValorPromedio;
    private JLabel lblConfederaciones;

    private final EquipoDAO equipoDAO;

    public EquipoPanel() {
        this.equipoDAO = new EquipoDAO();

        setLayout(new BorderLayout(0, 18));
        setBackground(AppTheme.NEGRO_PANEL);

        initComponents();
        cargarEquipos();
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
        header.setPreferredSize(new Dimension(0, 155));
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel lblEtiqueta = new JLabel("TEAM CENTER");
        lblEtiqueta.setForeground(AppTheme.DORADO);
        lblEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Gestión de equipos");
        lblTitulo.setForeground(AppTheme.BLANCO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("Administra selecciones, países, valor total y confederación.");
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

        JLabel lblBuscar = new JLabel("Buscar equipo");
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
                filtrarEquipos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarEquipos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarEquipos();
            }
        });

        panel.add(panelBusqueda, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTarjetasResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 14, 0));
        panel.setOpaque(false);

        lblTotalEquipos = new JLabel("0");
        lblValorPromedio = new JLabel("$0");
        lblConfederaciones = new JLabel("6");

        panel.add(crearTarjetaResumen("Total equipos", lblTotalEquipos));
        panel.add(crearTarjetaResumen("Valor promedio", lblValorPromedio));
        panel.add(crearTarjetaResumen("Confederaciones", lblConfederaciones));

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
        modeloTabla.addColumn("País");
        modeloTabla.addColumn("Valor total");
        modeloTabla.addColumn("Confederación");
        modeloTabla.addColumn("ID Confederación");

        tablaEquipos = new JTable(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        tablaEquipos.setRowSorter(sorter);

        tablaEquipos.getColumnModel().getColumn(5).setMinWidth(0);
        tablaEquipos.getColumnModel().getColumn(5).setMaxWidth(0);
        tablaEquipos.getColumnModel().getColumn(5).setWidth(0);

        tablaEquipos.setRowHeight(34);
        tablaEquipos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaEquipos.setForeground(AppTheme.BLANCO);
        tablaEquipos.setBackground(new Color(18, 22, 34));
        tablaEquipos.setSelectionBackground(AppTheme.DORADO);
        tablaEquipos.setSelectionForeground(AppTheme.NEGRO_FONDO);
        tablaEquipos.setGridColor(new Color(45, 50, 62));
        tablaEquipos.setShowVerticalLines(false);

        JTableHeader header = tablaEquipos.getTableHeader();
        header.setBackground(new Color(28, 33, 45));
        header.setForeground(AppTheme.BLANCO);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 38));

        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
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

        RoundedButton btnNuevo = new RoundedButton("+ Nuevo equipo", AppTheme.DORADO, AppTheme.NEGRO_FONDO);
        RoundedButton btnEditar = new RoundedButton("Editar", new Color(38, 47, 66), AppTheme.BLANCO);
        RoundedButton btnEliminar = new RoundedButton("Eliminar", new Color(90, 20, 28), AppTheme.BLANCO);
        RoundedButton btnActualizar = new RoundedButton("Actualizar", AppTheme.DORADO, AppTheme.NEGRO_FONDO);

        btnNuevo.addActionListener(e -> abrirFormularioNuevoEquipo());

        btnEditar.addActionListener(e -> editarEquipoSeleccionado());

        btnEliminar.addActionListener(e -> eliminarEquipoSeleccionado());

        btnActualizar.addActionListener(e -> cargarEquipos());

        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);

        return panel;
    }

    private void cargarEquipos() {
        modeloTabla.setRowCount(0);

        try {
            List<Equipo> equipos = equipoDAO.obtenerEquipos();

            double sumaValores = 0;

            for (Equipo equipo : equipos) {
                Object[] fila = {
                        equipo.getIdEquipo(),
                        equipo.getNombre(),
                        equipo.getPais(),
                        formatearValor(equipo.getValorTotalEquipo()),
                        obtenerNombreConfederacion(equipo.getIdConfederacion()),
                        equipo.getIdConfederacion()
                };

                modeloTabla.addRow(fila);
                sumaValores += equipo.getValorTotalEquipo();
            }

            lblTotalEquipos.setText(String.valueOf(equipos.size()));

            if (!equipos.isEmpty()) {
                double promedio = sumaValores / equipos.size();
                lblValorPromedio.setText(formatearValor(promedio));
            } else {
                lblValorPromedio.setText("$0");
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

    private void eliminarEquipoSeleccionado() {
        int filaSeleccionada = tablaEquipos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un equipo para eliminar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int filaModelo = tablaEquipos.convertRowIndexToModel(filaSeleccionada);
        int idEquipo = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
        String nombreEquipo = modeloTabla.getValueAt(filaModelo, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el equipo " + nombreEquipo + "?\n\n" +
                        "Si tiene jugadores asociados, la base de datos no permitirá eliminarlo.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                equipoDAO.eliminarEquipo(idEquipo);

                JOptionPane.showMessageDialog(
                        this,
                        "Equipo eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );

                cargarEquipos();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el equipo.\n\n" +
                                "Es posible que tenga jugadores, partidos o registros asociados.\n\n" +
                                "Detalle: " + e.getMessage(),
                        "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void abrirFormularioNuevoEquipo() {
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        EquipoFormDialog dialog = new EquipoFormDialog(ventanaPadre);
        dialog.setVisible(true);

        if (dialog.isGuardado()) {
            cargarEquipos();
        }
    }

    private void editarEquipoSeleccionado() {
        int filaSeleccionada = tablaEquipos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un equipo para editar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int filaModelo = tablaEquipos.convertRowIndexToModel(filaSeleccionada);

            int idEquipo = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 0).toString());
            String nombre = modeloTabla.getValueAt(filaModelo, 1).toString();
            String pais = modeloTabla.getValueAt(filaModelo, 2).toString();

            String valorTexto = modeloTabla.getValueAt(filaModelo, 3).toString()
                    .replace("$", "")
                    .replace(",", "")
                    .replace(".", "")
                    .trim();

            double valorTotal = Double.parseDouble(valorTexto);
            int idConfederacion = Integer.parseInt(modeloTabla.getValueAt(filaModelo, 5).toString());

            Equipo equipo = new Equipo(
                    idEquipo,
                    nombre,
                    pais,
                    valorTotal,
                    idConfederacion
            );

            JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

            EquipoFormDialog dialog = new EquipoFormDialog(ventanaPadre, equipo);
            dialog.setVisible(true);

            if (dialog.isGuardado()) {
                cargarEquipos();
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

    private void filtrarEquipos() {
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

    private String obtenerNombreConfederacion(int idConfederacion) {
        switch (idConfederacion) {
            case 1:
                return "UEFA";
            case 2:
                return "CONMEBOL";
            case 3:
                return "CONCACAF";
            case 4:
                return "CAF";
            case 5:
                return "AFC";
            case 6:
                return "OFC";
            default:
                return "Confederación " + idConfederacion;
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