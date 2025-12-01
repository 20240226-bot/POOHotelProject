package com.mycompany.mavenproject9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaAdmin extends JFrame {

    private SistemaHotel sistema;
    private Empleado admin;

    private JTextArea txtArea;

    public VentanaAdmin(SistemaHotel sistema, Empleado admin) {
        this.sistema = sistema;
        this.admin = admin;
        initComponents();
    }

    private void initComponents() {
        setTitle("Panel Administrador - " + admin.getNombreCompleto());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnVerHabitaciones   = new JButton("Ver habitaciones");
        JButton btnAgregarHabitacion = new JButton("Agregar habitación");
        JButton btnAgregarServicio   = new JButton("Agregar servicio");
        JButton btnVerEmpleados      = new JButton("Gestionar empleados");

        // ----- ACCIONES -----

        btnVerHabitaciones.addActionListener((ActionEvent e) -> {
            txtArea.setText(sistema.reporteOcupacion());
        });

        btnAgregarHabitacion.addActionListener((ActionEvent e) -> {
            agregarHabitacion();
        });

        btnAgregarServicio.addActionListener((ActionEvent e) -> {
            agregarServicio();
        });

        // aquí es donde cambia: abrimos VentanaEmpleados
        btnVerEmpleados.addActionListener((ActionEvent e) -> {
            VentanaEmpleados ve = new VentanaEmpleados(sistema);
            ve.setVisible(true);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVerHabitaciones);
        panelBotones.add(btnAgregarHabitacion);
        panelBotones.add(btnAgregarServicio);
        panelBotones.add(btnVerEmpleados);

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtArea);

        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void agregarHabitacion() {
        try {
            String numStr = JOptionPane.showInputDialog(this, "Número de habitación:");
            if (numStr == null) return;
            int num = Integer.parseInt(numStr);

            String capStr = JOptionPane.showInputDialog(this, "Capacidad máxima:");
            if (capStr == null) return;
            int cap = Integer.parseInt(capStr);

            String precioStr = JOptionPane.showInputDialog(this, "Precio por noche:");
            if (precioStr == null) return;
            double precio = Double.parseDouble(precioStr);

            String[] opciones = {"SIMPLE", "DOBLE", "SUITE"};
            String tipoSel = (String) JOptionPane.showInputDialog(this,
                    "Tipo de habitación:", "Tipo",
                    JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);
            if (tipoSel == null) return;

            TipoHabitacion tipo = TipoHabitacion.valueOf(tipoSel);

            Habitacion h = new Habitacion(num, cap, precio, tipo);
            sistema.registrarHabitacion(h);

            JOptionPane.showMessageDialog(this, "Habitación registrada.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos.");
        }
    }

    private void agregarServicio() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del servicio:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String precioStr = JOptionPane.showInputDialog(this, "Precio unitario:");
        if (precioStr == null) return;

        try {
            double precio = Double.parseDouble(precioStr);
            ServicioAdicional s = new ServicioAdicional(nombre, precio);
            sistema.registrarServicio(s);
            JOptionPane.showMessageDialog(this, "Servicio registrado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio inválido.");
        }
    }
}
