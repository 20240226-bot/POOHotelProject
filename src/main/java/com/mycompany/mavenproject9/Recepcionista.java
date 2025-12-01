
package com.mycompany.mavenproject9;

public class Recepcionista extends Empleado {

    public Recepcionista(String dni, String nombres, String apellidos,
                         String telefono, String email,
                         String usuario, String password) {
        super(dni, nombres, apellidos, telefono, email,
              usuario, password, RolEmpleado.RECEPCIONISTA);
    }
}