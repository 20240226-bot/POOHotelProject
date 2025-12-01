package com.mycompany.mavenproject9;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private int id;
    private Huesped huesped;
    private TipoHabitacion tipoHabitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoReserva estado;

    public Reserva(int id, Huesped huesped, TipoHabitacion tipoHabitacion,
                   LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.huesped = huesped;
        this.tipoHabitacion = tipoHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoReserva.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    // Verifica si el intervalo [inicio, fin] se cruza con esta reserva
    public boolean seSuperponeCon(LocalDate inicio, LocalDate fin) {
        return !(fin.isBefore(this.fechaInicio) || inicio.isAfter(this.fechaFin));
    }

    public long getNumeroNoches() {
        long noches = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (noches <= 0) {
            noches = 1;
        }
        return noches;
    }
}
