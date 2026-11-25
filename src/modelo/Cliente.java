package modelo;

public class Cliente {
    private String nombre;
    private boolean socio;

    public Cliente(String nombre, boolean socio) {
        this.nombre = nombre;
        this.socio = socio;
    }

    public String getNombre() { return nombre; }
    public boolean isSocio() { return socio; }

    public String toString() { return nombre + (socio ? " (socio)" : ""); }
}
