package com.mycompany.mavenproject9;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Estadia implements Facturable {

    private Reserva reserva;
    private Habitacion habitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;

    private ConsumoServicio[] consumos;
    private int numConsumos;

    public Estadia(Reserva reserva, Habitacion habitacion,
                   LocalDate fechaCheckIn) {
        this.reserva = reserva;
        this.habitacion = habitacion;
        this.fechaCheckIn = fechaCheckIn;
        this.consumos = new ConsumoServicio[50];
        this.numConsumos = 0;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public LocalDate getFechaCheckIn() {
        return fechaCheckIn;
    }

    public LocalDate getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void registrarCheckOut(LocalDate fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }

    public void agregarConsumo(ConsumoServicio consumo) {
        if (numConsumos < consumos.length) {
            consumos[numConsumos] = consumo;
            numConsumos++;
        } else {
            System.out.println("No se pueden registrar más consumos para esta estadía.");
        }
    }

    public ConsumoServicio[] getConsumos() {
        ConsumoServicio[] resultado = new ConsumoServicio[numConsumos];
        for (int i = 0; i < numConsumos; i++) {
            resultado[i] = consumos[i];
        }
        return resultado;
    }

    @Override
    public double calcularTotal() {
        long noches;
        if (fechaCheckOut != null) {
            noches = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
        } else {
            noches = reserva.getNumeroNoches();
        }

        if (noches <= 0) {
            noches = 1;
        }

        double totalHabitacion = noches * habitacion.getPrecioPorNoche();

        double totalServicios = 0;
        for (int i = 0; i < numConsumos; i++) {
            totalServicios += consumos[i].calcularSubtotal();
        }

        return totalHabitacion + totalServicios;
    }
}
