package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    private Libreria libreria = new Libreria();
    private Venta venta;

    private JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Libro de papel", "Libro digital", "Papelería"});
    private JComboBox<String> cbFormato = new JComboBox<>(new String[]{"PDF", "EPUB"});
    private JTextField txtCodigo = new JTextField(), txtNombre = new JTextField(), txtPrecio = new JTextField(),
            txtAutor = new JTextField(), txtStock = new JTextField(), txtMarca = new JTextField(),
            txtBuscar = new JTextField(15);
    private DefaultTableModel modeloTabla = new DefaultTableModel(
            new String[]{"Código", "Tipo", "Nombre", "Precio", "Detalle", "Stock"}, 0);

    private JComboBox<Cliente> cbCliente = new JComboBox<>();
    private JComboBox<Producto> cbProducto = new JComboBox<>();
    private JSpinner spCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private JTextArea areaVenta = new JTextArea(14, 40);

    public VentanaPrincipal() {
        super("Librería Páginas Andinas");
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Productos", panelProductos());
        tabs.add("Ventas", panelVentas());
        add(tabs);
        cargarTabla(libreria.getProductos());
        setSize(950, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel panelProductos() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Tipo de producto:")); form.add(cbTipo);
        form.add(new JLabel("Código:")); form.add(txtCodigo);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Precio ($):")); form.add(txtPrecio);
        form.add(new JLabel("Autor (libros):")); form.add(txtAutor);
        form.add(new JLabel("Formato (libro digital):")); form.add(cbFormato);
        form.add(new JLabel("Stock (libro de papel y papelería):")); form.add(txtStock);
        form.add(new JLabel("Marca (papelería):")); form.add(txtMarca);
        JButton btnRegistrar = new JButton("Registrar producto");
        btnRegistrar.addActionListener(e -> registrarProducto());
        form.add(new JLabel()); form.add(btnRegistrar);

        JPanel busqueda = new JPanel();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnTodos = new JButton("Ver todos");
        btnBuscar.addActionListener(e -> {
            ArrayList<Producto> res = libreria.buscar(txtBuscar.getText());
            if (res.isEmpty()) JOptionPane.showMessageDialog(this, "No se encontraron productos.");
            cargarTabla(res);
        });
        btnTodos.addActionListener(e -> { txtBuscar.setText(""); cargarTabla(libreria.getProductos()); });
        busqueda.add(new JLabel("Buscar por nombre:"));
        busqueda.add(txtBuscar); busqueda.add(btnBuscar); busqueda.add(btnTodos);

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(26);
        tabla.setDefaultEditor(Object.class, null);
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.add(busqueda, BorderLayout.NORTH);
        abajo.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p.add(form, BorderLayout.NORTH);
        p.add(abajo, BorderLayout.CENTER);
        return p;
    }

    private JPanel panelVentas() {
        for (Cliente c : libreria.getClientes()) cbCliente.addItem(c);
        cargarProductosVenta();
        cbCliente.addActionListener(e -> nuevaVenta());

        JButton btnAgregar = new JButton("Agregar a la venta");
        JButton btnFinalizar = new JButton("Finalizar venta");
        JButton btnNueva = new JButton("Cancelar / nueva venta");
        btnAgregar.addActionListener(e -> agregarAVenta());
        btnFinalizar.addActionListener(e -> finalizarVenta());
        btnNueva.addActionListener(e -> nuevaVenta());

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Cliente:")); form.add(cbCliente);
        form.add(new JLabel("Producto:")); form.add(cbProducto);
        form.add(new JLabel("Cantidad:")); form.add(spCantidad);
        form.add(btnAgregar); form.add(btnFinalizar);
        form.add(btnNueva); form.add(new JLabel());

        areaVenta.setEditable(false);
        areaVenta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));

        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(areaVenta), BorderLayout.CENTER);
        nuevaVenta();
        return p;
    }

    private void registrarProducto() {
        try {
            String tipo = (String) cbTipo.getSelectedItem();
            double precio = leerDecimal(txtPrecio, "Precio");
            Producto p;
            if (tipo.equals("Libro de papel"))
                p = new LibroFisico(txtCodigo.getText(), txtNombre.getText(), precio, txtAutor.getText(), leerEntero(txtStock, "Stock"));
            else if (tipo.equals("Libro digital"))
                p = new LibroDigital(txtCodigo.getText(), txtNombre.getText(), precio, txtAutor.getText(), (String) cbFormato.getSelectedItem());
            else
                p = new Papeleria(txtCodigo.getText(), txtNombre.getText(), precio, txtMarca.getText(), leerEntero(txtStock, "Stock"));
            libreria.agregarProducto(p);
            cargarTabla(libreria.getProductos());
            cargarProductosVenta();
            JOptionPane.showMessageDialog(this, "Producto registrado correctamente.");
            for (JTextField t : new JTextField[]{txtCodigo, txtNombre, txtPrecio, txtAutor, txtStock, txtMarca}) t.setText("");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void agregarAVenta() {
        try {
            venta.agregar((Producto) cbProducto.getSelectedItem(), (Integer) spCantidad.getValue());
            areaVenta.setText(venta.getDetalle());
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void finalizarVenta() {
        try {
            venta.finalizar();
            JOptionPane.showMessageDialog(this, venta.getDetalle(), "Venta registrada", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(libreria.getProductos());
            nuevaVenta();
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void nuevaVenta() {
        venta = new Venta((Cliente) cbCliente.getSelectedItem());
        areaVenta.setText(venta.getDetalle());
    }

    private void cargarTabla(ArrayList<Producto> lista) {
        modeloTabla.setRowCount(0);
        for (Producto p : lista)
            modeloTabla.addRow(new Object[]{p.getCodigo(), p.getTipo(), p.getNombre(),
                    String.format("$%.2f", p.getPrecio()), p.getDetalle(), p.getStockTexto()});
    }

    private void cargarProductosVenta() {
        cbProducto.removeAllItems();
        for (Producto p : libreria.getProductos()) cbProducto.addItem(p);
    }

    private double leerDecimal(JTextField campo, String nombre) {
        try { return Double.parseDouble(campo.getText().trim().replace(",", ".")); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Escriba un número válido en " + nombre + "."); }
    }

    private int leerEntero(JTextField campo, String nombre) {
        try { return Integer.parseInt(campo.getText().trim()); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Escriba un número entero en " + nombre + "."); }
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
