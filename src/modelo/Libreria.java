package modelo;

import java.util.ArrayList;

public class Libreria {
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();

    public Libreria() {
        agregarProducto(new LibroFisico("L001", "Cien años de soledad", 18.50, "Gabriel García Márquez", 3));
        agregarProducto(new LibroFisico("L002", "Huasipungo", 12.00, "Jorge Icaza", 1));
        agregarProducto(new LibroDigital("D001", "El laberinto de la soledad", 9.99, "Octavio Paz", "PDF"));
        agregarProducto(new LibroDigital("D002", "Rayuela", 8.50, "Julio Cortázar", "EPUB"));
        agregarProducto(new Papeleria("P001", "Cuaderno universitario", 2.50, "Norma", 20));
        agregarProducto(new Papeleria("P002", "Esfero azul", 0.60, "BIC", 50));
        clientes.add(new Cliente("María López", true));
        clientes.add(new Cliente("Juan Pérez", false));
    }

    public void agregarProducto(Producto p) {
        for (Producto x : productos)
            if (x.getCodigo().equalsIgnoreCase(p.getCodigo()))
                throw new IllegalArgumentException("Ya existe un producto con el código " + p.getCodigo() + ".");
        productos.add(p);
    }

    public ArrayList<Producto> buscar(String texto) {
        ArrayList<Producto> res = new ArrayList<>();
        String t = texto.trim().toLowerCase();
        for (Producto p : productos)
            if (p.getNombre().toLowerCase().contains(t)) res.add(p);
        return res;
    }

    public ArrayList<Producto> getProductos() { return productos; }
    public ArrayList<Cliente> getClientes() { return clientes; }
}
