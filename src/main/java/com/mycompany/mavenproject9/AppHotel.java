package com.mycompany.mavenproject9;

public class AppHotel {

    public static void main(String[] args) {

        // Look & Feel del sistema operativo (opcional)
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ignorar
        }

        // Crear el sistema del hotel (carga empleados, habitaciones, servicios)
        SistemaHotel sistema = new SistemaHotel();

        // Abrir ventana de login
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame(sistema).setVisible(true);
        });
    }
}