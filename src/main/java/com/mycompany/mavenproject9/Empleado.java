
package com.mycompany.mavenproject9;
public abstract class Empleado extends Persona {

    private String usuario;
    private String password;
    private RolEmpleado rol;

    public Empleado(String dni, String nombres, String apellidos,
                    String telefono, String email,
                    String usuario, String password, RolEmpleado rol) {
        super(dni, nombres, apellidos, telefono, email);
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }

    public boolean validarCredenciales(String usuario, String password) {
        return this.usuario.equals(usuario) && this.password.equals(password);
    }
}
