package com.mycompany.mavenproject9;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaEmpleados extends JFrame {

    private SistemaHotel sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtDni;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> cboRol; // "ADMINISTRADOR" o "RECEPCIONISTA"

    public VentanaEmpleados(SistemaHotel sistema) {
        this.sistema = sistema;
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestión de Empleados");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ---------- TABLA ----------
        modelo = new DefaultTableModel(
                new Object[]{"DNI", "Nombres", "Apellidos", "Teléfono",
                        "Email", "Usuario", "Rol"},
                0
        );
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // ---------- FORMULARIO ----------
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 5, 5));

        txtDni = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtTelefono = new JTextField();
        txtEmail = new JTextField();
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        cboRol = new JComboBox<>(new String[]{"ADMINISTRADOR", "RECEPCIONISTA"});

        panelForm.add(new JLabel("DNI:"));
        panelForm.add(txtDni);
        panelForm.add(new JLabel("Nombres:"));
        panelForm.add(txtNombres);
        panelForm.add(new JLabel("Apellidos:"));
        panelForm.add(txtApellidos);
        panelForm.add(new JLabel("Teléfono:"));
        panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Email:"));
        panelForm.add(txtEmail);
        panelForm.add(new JLabel("Usuario:"));
        panelForm.add(txtUsuario);
        panelForm.add(new JLabel("Password:"));
        panelForm.add(txtPassword);
        panelForm.add(new JLabel("Rol:"));
        panelForm.add(cboRol);

        // ---------- BOTONES ----------
        JButton btnNuevo   = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar / Actualizar");
        JButton btnEliminar= new JButton("Eliminar");

        btnNuevo.addActionListener(this::nuevoEmpleado);
        btnGuardar.addActionListener(this::guardarEmpleado);
        btnEliminar.addActionListener(this::eliminarEmpleado);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        // ---------- LAYOUT PRINCIPAL ----------
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(panelForm, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

        // Click en tabla → llenar formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtDni.setText(modelo.getValueAt(fila, 0).toString());
                txtNombres.setText(modelo.getValueAt(fila, 1).toString());
                txtApellidos.setText(modelo.getValueAt(fila, 2).toString());
                txtTelefono.setText(modelo.getValueAt(fila, 3) != null ? modelo.getValueAt(fila, 3).toString() : "");
                txtEmail.setText(modelo.getValueAt(fila, 4) != null ? modelo.getValueAt(fila, 4).toString() : "");
                txtUsuario.setText(modelo.getValueAt(fila, 5).toString());
                cboRol.setSelectedItem(modelo.getValueAt(fila, 6).toString());
                txtDni.setEnabled(false); // no cambiar DNI al editar
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        Empleado[] lista = sistema.getEmpleados();
        for (Empleado e : lista) {
            modelo.addRow(new Object[]{
                    e.getDni(),
                    e.getNombres(),
                    e.getApellidos(),
                    e.getTelefono(),
                    e.getEmail(),
                    e.getUsuario(),
                    e.getRol()  // muestra el enum RolEmpleado
            });
        }
    }

    private void nuevoEmpleado(ActionEvent evt) {
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        cboRol.setSelectedIndex(0);
        txtDni.setEnabled(true);
        tabla.clearSelection();
    }

    private void guardarEmpleado(ActionEvent evt) {
        String dni       = txtDni.getText().trim();
        String nombres   = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono  = txtTelefono.getText().trim();
        String email     = txtEmail.getText().trim();
        String usuario   = txtUsuario.getText().trim();
        String password  = new String(txtPassword.getPassword());
        String rolStr    = (String) cboRol.getSelectedItem();

        if (dni.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()
                || usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "DNI, nombres, apellidos, usuario y password son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado existente = sistema.buscarEmpleadoPorDni(dni);

        // Usamos tus clases existentes
        Empleado nuevoOActualizado;
        if ("ADMINISTRADOR".equals(rolStr)) {
            nuevoOActualizado = new Administrador(
                    dni, nombres, apellidos, telefono, email, usuario, password
            );
        } else { // RECEPCIONISTA
            nuevoOActualizado = new Recepcionista(
                    dni, nombres, apellidos, telefono, email, usuario, password
            );
        }

        if (existente == null) {
            sistema.registrarEmpleado(nuevoOActualizado);
        } else {
            sistema.actualizarEmpleado(nuevoOActualizado);
        }

        cargarTabla();
        JOptionPane.showMessageDialog(this, "Empleado guardado correctamente");
    }

    private void eliminarEmpleado(ActionEvent evt) {
        String dni = txtDni.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese o seleccione un DNI",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar al empleado con DNI " + dni + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            boolean ok = sistema.eliminarEmpleado(dni);
            if (ok) {
                cargarTabla();
                nuevoEmpleado(null);
                JOptionPane.showMessageDialog(this, "Empleado eliminado");
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el empleado",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
