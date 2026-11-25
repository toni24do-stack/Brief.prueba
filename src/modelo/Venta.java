package modelo;

import java.util.ArrayList;

public class Venta {
    private Cliente cliente;
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Integer> cantidades = new ArrayList<>();

    public Venta(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("Seleccione un cliente.");
        this.cliente = cliente;
    }

    public void agregar(Producto p, int cantidad) {
        if (p == null) throw new IllegalArgumentException("Seleccione un producto.");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        int i = productos.indexOf(p);
        int total = cantidad + (i >= 0 ? cantidades.get(i) : 0);
        if (!p.hayStock(total))
            throw new IllegalArgumentException("No hay stock suficiente de " + p.getNombre()
                    + ". Disponible: " + p.getStockTexto());
        if (i >= 0) cantidades.set(i, total);
        else { productos.add(p); cantidades.add(cantidad); }
    }

    private double subtotal(int i) { return productos.get(i).getPrecio() * cantidades.get(i); }
    private double descuento(int i) { return cliente.isSocio() ? subtotal(i) * productos.get(i).getDescuentoSocio() : 0; }
    private double iva(int i) { return (subtotal(i) - descuento(i)) * productos.get(i).getIva(); }

    public double getTotal() {
        double t = 0;
        for (int i = 0; i < productos.size(); i++) t += subtotal(i) - descuento(i) + iva(i);
        return t;
    }

    public String getDetalle() {
        String s = "Cliente: " + cliente + "\n\n";
        double sub = 0, desc = 0, iva = 0;
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            s += String.format("%d x %s ($%.2f c/u)%n   Subtotal $%.2f | Desc. -$%.2f | IVA +$%.2f%n",
                    cantidades.get(i), p.getNombre(), p.getPrecio(), subtotal(i), descuento(i), iva(i));
            sub += subtotal(i); desc += descuento(i); iva += iva(i);
        }
        s += String.format("%nSubtotal:         $%.2f%nDescuento socio: -$%.2f%nIVA:             +$%.2f%nTOTAL A PAGAR:    $%.2f",
                sub, desc, iva, sub - desc + iva);
        return s;
    }

    public void finalizar() {
        if (productos.isEmpty()) throw new IllegalArgumentException("La venta no tiene productos.");
        for (int i = 0; i < productos.size(); i++)
            if (!productos.get(i).hayStock(cantidades.get(i)))
                throw new IllegalArgumentException("Ya no hay stock suficiente de " + productos.get(i).getNombre() + ".");
        for (int i = 0; i < productos.size(); i++)
            productos.get(i).descontarStock(cantidades.get(i));
    }
}
