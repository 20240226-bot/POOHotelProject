
package com.mycompany.mavenproject9;
public class ServicioAdicional {
    private String nombre;
    private double precioUnitario;

    public ServicioAdicional(String nombre, double precioUnitario) {
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    @Override
    public String toString() {
        return nombre + " (S/ " + precioUnitario + ")";
    }
}
