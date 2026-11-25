package modelo;

public class LibroDigital extends Producto {
    private String autor;
    private String formato;

    public LibroDigital(String codigo, String nombre, double precio, String autor, String formato) {
        super(codigo, nombre, precio);
        if (autor == null || autor.isBlank()) throw new IllegalArgumentException("El autor es obligatorio.");
        if (formato == null || !(formato.equalsIgnoreCase("PDF") || formato.equalsIgnoreCase("EPUB")))
            throw new IllegalArgumentException("El formato debe ser PDF o EPUB.");
        this.autor = autor.trim();
        this.formato = formato.toUpperCase();
    }

    public String getAutor() { return autor; }
    public String getFormato() { return formato; }

    public String getTipo() { return "Libro digital"; }
    public String getDetalle() { return "Autor: " + autor + " | " + formato; }
}
