
package com.mycompany.mavenproject9;

public class Administrador extends Empleado {

    public Administrador(String dni, String nombres, String apellidos,
                         String telefono, String email,
                         String usuario, String password) {
        super(dni, nombres, apellidos, telefono, email,
              usuario, password, RolEmpleado.ADMINISTRADOR);
    }
}
