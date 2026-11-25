package modelo;

public class LibroFisico extends Producto {
    private String autor;
    private int stock;

    public LibroFisico(String codigo, String nombre, double precio, String autor, int stock) {
        super(codigo, nombre, precio);
        if (autor == null || autor.isBlank()) throw new IllegalArgumentException("El autor es obligatorio.");
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.autor = autor.trim();
        this.stock = stock;
    }

    public String getAutor() { return autor; }
    public int getStock() { return stock; }

    public String getTipo() { return "Libro de papel"; }
    public String getDetalle() { return "Autor: " + autor; }
    public String getStockTexto() { return String.valueOf(stock); }
    public boolean hayStock(int cantidad) { return cantidad <= stock; }

    public void descontarStock(int cantidad) {
        if (!hayStock(cantidad)) throw new IllegalArgumentException("No hay stock suficiente de " + getNombre() + ".");
        stock -= cantidad;
    }
}
