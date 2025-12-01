package com.mycompany.mavenproject9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class VentanaRecepcionista extends JFrame {

    private SistemaHotel sistema;
    private Empleado recepcionista;
    private JTextArea txtArea;

    public VentanaRecepcionista(SistemaHotel sistema, Empleado recepcionista) {
        this.sistema = sistema;
        this.recepcionista = recepcionista;
        initComponents();
    }

    private void initComponents() {
        setTitle("Panel Recepcionista - " + recepcionista.getNombreCompleto());
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnRegistrarHuesped = new JButton("Registrar huésped");
        JButton btnCrearReserva = new JButton("Crear reserva");
        JButton btnCheckIn = new JButton("Check-in");
        JButton btnConsumo = new JButton("Registrar consumo");
        JButton btnCheckOut = new JButton("Check-out");
        JButton btnReporteIngresos = new JButton("Reporte ingresos");

        btnRegistrarHuesped.addActionListener((ActionEvent e) -> registrarHuesped());
        btnCrearReserva.addActionListener((ActionEvent e) -> crearReserva());
        btnCheckIn.addActionListener((ActionEvent e) -> checkIn());
        btnConsumo.addActionListener((ActionEvent e) -> registrarConsumo());
        btnCheckOut.addActionListener((ActionEvent e) -> checkOut());
        btnReporteIngresos.addActionListener((ActionEvent e) -> reporteIngresos());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrarHuesped);
        panelBotones.add(btnCrearReserva);
        panelBotones.add(btnCheckIn);
        panelBotones.add(btnConsumo);
        panelBotones.add(btnCheckOut);
        panelBotones.add(btnReporteIngresos);

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtArea);

        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void registrarHuesped() {
        String dni = JOptionPane.showInputDialog(this, "DNI:");
        if (dni == null) return;
        String nombres = JOptionPane.showInputDialog(this, "Nombres:");
        if (nombres == null) return;
        String apellidos = JOptionPane.showInputDialog(this, "Apellidos:");
        if (apellidos == null) return;
        String telefono = JOptionPane.showInputDialog(this, "Teléfono:");
        if (telefono == null) return;
        String email = JOptionPane.showInputDialog(this, "Email:");
        if (email == null) return;

        Huesped h = new Huesped(dni, nombres, apellidos, telefono, email);
        sistema.registrarHuesped(h);
        JOptionPane.showMessageDialog(this, "Huésped registrado.");
    }

    private void crearReserva() {
        String dni = JOptionPane.showInputDialog(this, "DNI del huésped:");
        if (dni == null) return;
        Huesped h = sistema.buscarHuespedPorDni(dni);
        if (h == null) {
            JOptionPane.showMessageDialog(this, "Huésped no encontrado.");
            return;
        }

        String[] tipos = {"SIMPLE", "DOBLE", "SUITE"};
        String tipoSel = (String) JOptionPane.showInputDialog(this,
                "Tipo de habitación:", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipoSel == null) return;
        TipoHabitacion tipo = TipoHabitacion.valueOf(tipoSel);

        String fIniStr = JOptionPane.showInputDialog(this, "Fecha inicio (YYYY-MM-DD):");
        String fFinStr = JOptionPane.showInputDialog(this, "Fecha fin (YYYY-MM-DD):");
        if (fIniStr == null || fFinStr == null) return;

        try {
            LocalDate inicio = LocalDate.parse(fIniStr);
            LocalDate fin = LocalDate.parse(fFinStr);

            Reserva r = sistema.crearReserva(h, tipo, inicio, fin);
            if (r == null) {
                JOptionPane.showMessageDialog(this, "No hay disponibilidad.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Reserva creada con ID: " + r.getId());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fechas inválidas.");
        }
    }

    private void checkIn() {
        String idStr = JOptionPane.showInputDialog(this, "ID de reserva:");
        if (idStr == null) return;
        try {
            int id = Integer.parseInt(idStr);
            Estadia e = sistema.realizarCheckIn(id);
            if (e == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo realizar el check-in.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Check-in realizado. Habitación: " +
                                e.getHabitacion().getNumero());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void registrarConsumo() {
        String numHabStr = JOptionPane.showInputDialog(this,
                "Número de habitación:");
        if (numHabStr == null) return;

        try {
            int numHab = Integer.parseInt(numHabStr);

            ServicioAdicional[] lista = sistema.getServicios();
            if (lista.length == 0) {
                JOptionPane.showMessageDialog(this, "No hay servicios registrados.");
                return;
            }

            String[] nombres = new String[lista.length];
            for (int i = 0; i < lista.length; i++) {
                nombres[i] = lista[i].getNombre();
            }

            String servSel = (String) JOptionPane.showInputDialog(this,
                    "Servicio:", "Servicio",
                    JOptionPane.QUESTION_MESSAGE,
                    null, nombres, nombres[0]);
            if (servSel == null) return;

            String cantStr = JOptionPane.showInputDialog(this, "Cantidad:");
            if (cantStr == null) return;
            int cant = Integer.parseInt(cantStr);

            sistema.registrarConsumoEnHabitacion(numHab, servSel, cant);
            JOptionPane.showMessageDialog(this, "Consumo registrado.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    private void checkOut() {
        String numHabStr = JOptionPane.showInputDialog(this,
                "Número de habitación:");
        if (numHabStr == null) return;

        String fechaStr = JOptionPane.showInputDialog(this,
                "Fecha salida (YYYY-MM-DD):");
        if (fechaStr == null) return;

        try {
            int numHab = Integer.parseInt(numHabStr);
            LocalDate fecha = LocalDate.parse(fechaStr);

            double total = sistema.realizarCheckOut(numHab, fecha);
            if (total == 0) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró estadía para esa habitación.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Check-out realizado. Total a pagar: S/ " + total);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    private void reporteIngresos() {
        String fIniStr = JOptionPane.showInputDialog(this,
                "Fecha inicio (YYYY-MM-DD):");
        String fFinStr = JOptionPane.showInputDialog(this,
                "Fecha fin (YYYY-MM-DD):");
        if (fIniStr == null || fFinStr == null) return;

        try {
            LocalDate inicio = LocalDate.parse(fIniStr);
            LocalDate fin = LocalDate.parse(fFinStr);
            String rep = sistema.reporteIngresos(inicio, fin);
            txtArea.setText(rep);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fechas inválidas.");
        }
    }
}
