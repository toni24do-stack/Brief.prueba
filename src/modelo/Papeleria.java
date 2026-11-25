package modelo;

public class Papeleria extends Producto {
    private String marca;
    private int stock;

    public Papeleria(String codigo, String nombre, double precio, String marca, int stock) {
        super(codigo, nombre, precio);
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("La marca es obligatoria.");
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.marca = marca.trim();
        this.stock = stock;
    }

    public String getMarca() { return marca; }
    public int getStock() { return stock; }

    public String getTipo() { return "Papelería"; }
    public String getDetalle() { return "Marca: " + marca; }
    public String getStockTexto() { return String.valueOf(stock); }
    public boolean hayStock(int cantidad) { return cantidad <= stock; }
    public double getIva() { return 0.15; }
    public double getDescuentoSocio() { return 0; }

    public void descontarStock(int cantidad) {
        if (!hayStock(cantidad)) throw new IllegalArgumentException("No hay stock suficiente de " + getNombre() + ".");
        stock -= cantidad;
    }
}
