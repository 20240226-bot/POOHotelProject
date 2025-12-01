package com.mycompany.mavenproject9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class LoginFrame extends JFrame {

    private SistemaHotel sistema;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblLogo;
    private Image backgroundImage;

    public LoginFrame(SistemaHotel sistema) {
        this.sistema = sistema;

        // Cargar fondo
        URL bgURL = getClass().getResource("/resources/fondo.png");
        if (bgURL != null) {
            backgroundImage = new ImageIcon(bgURL).getImage();
        } else {
            System.out.println("ERROR: No se encontró fondo.png");
        }

        initComponents();
    }

    private void initComponents() {

        setTitle("Login - Hotel");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel del fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelFondo.setLayout(new GridBagLayout());  // Centrar el panel del login
        add(panelFondo, BorderLayout.CENTER);

        // Panel translúcido al centro
        JPanel panelLogin = new JPanel();
        panelLogin.setPreferredSize(new Dimension(600, 450));
        panelLogin.setBackground(new Color(255, 255, 255, 180)); // Blanco con transparencia
        panelLogin.setLayout(null); // Layout libre para colocar logo y campos
        panelLogin.setOpaque(true);

        // ========== LOGO ==========
        lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setBounds(150, 20, 300, 120);

        // Cargar logo
        URL logoURL = getClass().getResource("/resources/logo.png");
        if (logoURL != null) {
            Image img = new ImageIcon(logoURL).getImage();
            Image scaled = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaled));
        } else {
            System.out.println("ERROR: No se encontró logo.png");
        }

        panelLogin.add(lblLogo);

        // ========== LABEL USUARIO ==========
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(100, 160, 200, 30);
        panelLogin.add(lblUsuario);

        // Campo Usuario
        txtUsuario = new JTextField();
        txtUsuario.setBounds(100, 190, 400, 35);
        panelLogin.add(txtUsuario);

        // ========== LABEL PASSWORD ==========
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(100, 240, 200, 30);
        panelLogin.add(lblPassword);

        // Campo Password
        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 270, 400, 35);
        panelLogin.add(txtPassword);

        // Botón Ingresar
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(150, 330, 300, 45);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 16));

        btnIngresar.addActionListener((ActionEvent e) -> ingresar());
        panelLogin.add(btnIngresar);

        // Agregar panelLogin centrado
        panelFondo.add(panelLogin);
    }

    private void ingresar() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        Empleado emp = sistema.login(usuario, password);
        if (emp == null) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Bienvenido " + emp.getNombreCompleto() +
                        " (" + emp.getRol() + ")");

        if (emp.getRol() == RolEmpleado.ADMINISTRADOR) {
            new VentanaAdmin(sistema, emp).setVisible(true);
        } else {
            new VentanaRecepcionista(sistema, emp).setVisible(true);
        }

        this.dispose();
    }
}
