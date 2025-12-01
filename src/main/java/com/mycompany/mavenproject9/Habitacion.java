
package com.mycompany.mavenproject9;

public class Habitacion {

    private int numero;
    private int capacidadMaxima;
    private double precioPorNoche;
    private TipoHabitacion tipo;
    private EstadoHabitacion estado;

    public Habitacion(int numero, int capacidadMaxima,
                      double precioPorNoche, TipoHabitacion tipo) {
        this.numero = numero;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.tipo = tipo;
        this.estado = EstadoHabitacion.LIMPIA; // Estado inicial
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public boolean estaDisponibleParaCheckIn() {
        return estado == EstadoHabitacion.LIBRE || estado == EstadoHabitacion.LIMPIA;
    }

    @Override
    public String toString() {
        return "Habitación " + numero +
               " - Tipo: " + tipo +
               " - Estado: " + estado;
    }
}
