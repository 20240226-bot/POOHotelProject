package com.mycompany.mavenproject9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private SistemaHotel sistema;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public LoginFrame(SistemaHotel sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {

        setTitle("Login - Hotel");

        // Tamaño igual al fondo
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ============================
        //   PANEL CON FONDO
        // ============================
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(
                        getClass().getResource("/resources/fondo.png")
                );
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout()); // Centrado
        add(panelFondo, BorderLayout.CENTER);

        // ============================
        //   PANEL BLANCO CENTRAL
        // ============================
        JPanel panelCentro = new JPanel();
        panelCentro.setPreferredSize(new Dimension(450, 350));
        panelCentro.setBackground(new Color(255, 255, 255, 220)); // Blanco semi-transparente
        panelCentro.setLayout(new GridLayout(5, 1, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Labels y campos
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblPassword = new JLabel("Password:");

        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 16));
        btnIngresar.addActionListener((ActionEvent e) -> ingresar());

        // Agregar elementos
        panelCentro.add(lblUsuario);
        panelCentro.add(txtUsuario);
        panelCentro.add(lblPassword);
        panelCentro.add(txtPassword);
        panelCentro.add(btnIngresar);

        // CENTRAR EL PANEL
        panelFondo.add(panelCentro);

    }

    private void ingresar() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        Empleado emp = sistema.login(usuario, password);

        if (emp == null) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Bienvenido " + emp.getNombreCompleto() + " (" + emp.getRol() + ")");

        if (emp.getRol() == RolEmpleado.ADMINISTRADOR) {
            new VentanaAdmin(sistema, emp).setVisible(true);
        } else {
            new VentanaRecepcionista(sistema, emp).setVisible(true);
        }

        this.dispose();
    }
}
