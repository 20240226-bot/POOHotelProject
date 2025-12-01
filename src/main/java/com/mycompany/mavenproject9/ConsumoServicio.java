
package com.mycompany.mavenproject9;

public class ConsumoServicio {

    private ServicioAdicional servicio;
    private int cantidad;

    public ConsumoServicio(ServicioAdicional servicio, int cantidad) {
        this.servicio = servicio;
        this.cantidad = cantidad;
    }

    public ServicioAdicional getServicio() {
        return servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return servicio.getPrecioUnitario() * cantidad;
    }
}
