
package com.mycompany.mavenproject9;

public class Huesped extends Persona {

    public Huesped(String dni, String nombres, String apellidos,
                   String telefono, String email) {
        super(dni, nombres, apellidos, telefono, email);
    }

    @Override
    public String toString() {
        return "Huesped{" +
                "dni='" + getDni() + '\'' +
                ", nombre='" + getNombreCompleto() + '\'' +
                '}';
    }
}
