package modelo;

public abstract class Producto {
    private String codigo;
    private String nombre;
    private double precio;

    public Producto(String codigo, String nombre, double precio) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("El código es obligatorio.");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public abstract String getTipo();
    public abstract String getDetalle();

    // Por defecto: sin límite de stock (libro digital)
    public boolean hayStock(int cantidad) { return true; }
    public void descontarStock(int cantidad) { }
    public String getStockTexto() { return "Ilimitado"; }

    // Por defecto: libros (sin IVA, 10% descuento socio)
    public double getIva() { return 0; }
    public double getDescuentoSocio() { return 0.10; }

    public String toString() { return codigo + " - " + nombre; }
}
