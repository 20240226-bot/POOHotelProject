package com.mycompany.mavenproject9;

import java.time.LocalDate;

public class SistemaHotel {

    private Empleado[] empleados;
    private int numEmpleados;

    private Habitacion[] habitaciones;
    private int numHabitaciones;

    private Huesped[] huespedes;
    private int numHuespedes;

    private ServicioAdicional[] servicios;
    private int numServicios;

    private Reserva[] reservas;
    private int numReservas;

    private Estadia[] estadias;
    private int numEstadias;

    private int siguienteIdReserva = 1;

    public SistemaHotel() {
        empleados = new Empleado[50];
        habitaciones = new Habitacion[100];
        huespedes = new Huesped[200];
        servicios = new ServicioAdicional[50];
        reservas = new Reserva[200];
        estadias = new Estadia[200];

        numEmpleados = 0;
        numHabitaciones = 0;
        numHuespedes = 0;
        numServicios = 0;
        numReservas = 0;
        numEstadias = 0;

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Admin por defecto
        Administrador admin = new Administrador(
                "11111111", "Admin", "Principal",
                "999999999", "admin@hotel.com",
                "admin", "admin"
        );
        empleados[numEmpleados++] = admin;

        // Recepcionista por defecto
        Recepcionista rec = new Recepcionista(
                "22222222", "Ana", "Recepcion",
                "988888888", "recep@hotel.com",
                "recep", "recep"
        );
        empleados[numEmpleados++] = rec;

        // Habitaciones iniciales
        registrarHabitacion(new Habitacion(101, 2, 150.0, TipoHabitacion.SIMPLE));
        registrarHabitacion(new Habitacion(102, 2, 150.0, TipoHabitacion.SIMPLE));
        registrarHabitacion(new Habitacion(201, 3, 250.0, TipoHabitacion.DOBLE));
        registrarHabitacion(new Habitacion(301, 4, 400.0, TipoHabitacion.SUITE));

        // Servicios iniciales
        registrarServicio(new ServicioAdicional("Lavandería", 20.0));
        registrarServicio(new ServicioAdicional("Servicio a la habitación", 30.0));
        registrarServicio(new ServicioAdicional("Frigobar", 15.0));
    }

    // ========== LOGIN ==========

    public Empleado login(String usuario, String password) {
        for (int i = 0; i < numEmpleados; i++) {
            if (empleados[i].validarCredenciales(usuario, password)) {
                return empleados[i];
            }
        }
        return null;
    }

    // ========== EMPLEADOS ==========

    public void registrarEmpleado(Empleado empleado) {
        if (numEmpleados < empleados.length) {
            empleados[numEmpleados++] = empleado;
        }
    }

    public Empleado[] getEmpleados() {
        Empleado[] res = new Empleado[numEmpleados];
        for (int i = 0; i < numEmpleados; i++) {
            res[i] = empleados[i];
        }
        return res;
    }

    /** Buscar empleado por DNI (para modificar / eliminar) */
    public Empleado buscarEmpleadoPorDni(String dni) {
        for (int i = 0; i < numEmpleados; i++) {
            if (empleados[i].getDni().equals(dni)) {
                return empleados[i];
            }
        }
        return null;
    }

    /** Actualizar info de empleado existente (mismo DNI) */
    public boolean actualizarEmpleado(Empleado empActualizado) {
        for (int i = 0; i < numEmpleados; i++) {
            if (empleados[i].getDni().equals(empActualizado.getDni())) {
                empleados[i] = empActualizado;
                return true;
            }
        }
        return false;
    }

    /** Eliminar empleado por DNI (corre el array hacia arriba) */
    public boolean eliminarEmpleado(String dni) {
        for (int i = 0; i < numEmpleados; i++) {
            if (empleados[i].getDni().equals(dni)) {
                // desplazar a la izquierda
                for (int j = i; j < numEmpleados - 1; j++) {
                    empleados[j] = empleados[j + 1];
                }
                empleados[numEmpleados - 1] = null;
                numEmpleados--;
                return true;
            }
        }
        return false;
    }

    // ========== HABITACIONES ==========

    public void registrarHabitacion(Habitacion habitacion) {
        if (numHabitaciones < habitaciones.length) {
            habitaciones[numHabitaciones++] = habitacion;
        }
    }

    public Habitacion buscarHabitacionPorNumero(int numero) {
        for (int i = 0; i < numHabitaciones; i++) {
            if (habitaciones[i].getNumero() == numero) {
                return habitaciones[i];
            }
        }
        return null;
    }

    public Habitacion[] getHabitaciones() {
        Habitacion[] res = new Habitacion[numHabitaciones];
        for (int i = 0; i < numHabitaciones; i++) {
            res[i] = habitaciones[i];
        }
        return res;
    }

    // ========== HUESPEDES ==========

    public void registrarHuesped(Huesped h) {
        if (numHuespedes < huespedes.length) {
            huespedes[numHuespedes++] = h;
        }
    }

    public Huesped buscarHuespedPorDni(String dni) {
        for (int i = 0; i < numHuespedes; i++) {
            if (huespedes[i].getDni().equals(dni)) {
                return huespedes[i];
            }
        }
        return null;
    }

    // ========== SERVICIOS ==========

    public void registrarServicio(ServicioAdicional s) {
        if (numServicios < servicios.length) {
            servicios[numServicios++] = s;
        }
    }

    public ServicioAdicional[] getServicios() {
        ServicioAdicional[] res = new ServicioAdicional[numServicios];
        for (int i = 0; i < numServicios; i++) {
            res[i] = servicios[i];
        }
        return res;
    }

    public ServicioAdicional buscarServicioPorNombre(String nombre) {
        for (int i = 0; i < numServicios; i++) {
            if (servicios[i].getNombre().equalsIgnoreCase(nombre)) {
                return servicios[i];
            }
        }
        return null;
    }

    // ========== RESERVAS ==========

    public Reserva crearReserva(Huesped huesped,
                                TipoHabitacion tipo,
                                LocalDate inicio,
                                LocalDate fin) {

        if (!hayDisponibilidad(tipo, inicio, fin)) {
            return null;
        }

        Reserva r = new Reserva(siguienteIdReserva++, huesped, tipo, inicio, fin);
        if (numReservas < reservas.length) {
            reservas[numReservas++] = r;
            return r;
        }
        return null;
    }

    private boolean hayDisponibilidad(TipoHabitacion tipo,
                                      LocalDate inicio,
                                      LocalDate fin) {
        int totalTipo = 0;
        for (int i = 0; i < numHabitaciones; i++) {
            if (habitaciones[i].getTipo() == tipo) {
                totalTipo++;
            }
        }

        int reservadas = 0;
        for (int i = 0; i < numReservas; i++) {
            Reserva r = reservas[i];
            if (r.getTipoHabitacion() == tipo
                    && r.getEstado() != EstadoReserva.CANCELADA
                    && r.seSuperponeCon(inicio, fin)) {
                reservadas++;
            }
        }

        return reservadas < totalTipo;
    }

    public Reserva buscarReservaPorId(int id) {
        for (int i = 0; i < numReservas; i++) {
            if (reservas[i].getId() == id) {
                return reservas[i];
            }
        }
        return null;
    }

    public Reserva[] getReservas() {
        Reserva[] res = new Reserva[numReservas];
        for (int i = 0; i < numReservas; i++) {
            res[i] = reservas[i];
        }
        return res;
    }

    // ========== ESTADIAS (check-in / check-out) ==========

    public Estadia realizarCheckIn(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r == null) {
            return null;
        }

        Habitacion habAsignada = null;
        for (int i = 0; i < numHabitaciones; i++) {
            Habitacion h = habitaciones[i];
            if (h.getTipo() == r.getTipoHabitacion()
                    && (h.getEstado() == EstadoHabitacion.LIBRE
                    || h.getEstado() == EstadoHabitacion.LIMPIA)) {
                habAsignada = h;
                break;
            }
        }
        if (habAsignada == null) {
            return null;
        }

        habAsignada.setEstado(EstadoHabitacion.OCUPADA);
        r.setEstado(EstadoReserva.CONFIRMADA);

        Estadia e = new Estadia(r, habAsignada, r.getFechaInicio());
        if (numEstadias < estadias.length) {
            estadias[numEstadias++] = e;
            return e;
        }
        return null;
    }

    public Estadia buscarEstadiaPorHabitacion(int numHab) {
        for (int i = 0; i < numEstadias; i++) {
            Estadia e = estadias[i];
            if (e.getHabitacion().getNumero() == numHab) {
                return e;
            }
        }
        return null;
    }

    public double realizarCheckOut(int numHabitacion, LocalDate fechaSalida) {
        Estadia e = buscarEstadiaPorHabitacion(numHabitacion);
        if (e == null) {
            return 0;
        }
        e.registrarCheckOut(fechaSalida);
        Habitacion h = e.getHabitacion();
        h.setEstado(EstadoHabitacion.SUCIA);
        double total = e.calcularTotal();
        return total;
    }

    public void registrarConsumoEnHabitacion(int numHabitacion,
                                             String nombreServicio,
                                             int cantidad) {
        Estadia e = buscarEstadiaPorHabitacion(numHabitacion);
        if (e == null) {
            return;
        }
        ServicioAdicional s = buscarServicioPorNombre(nombreServicio);
        if (s == null) {
            return;
        }
        ConsumoServicio consumo = new ConsumoServicio(s, cantidad);
        e.agregarConsumo(consumo);
    }

    // ========== REPORTES ==========

    public String reporteOcupacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE OCUPACIÓN:\n");
        for (int i = 0; i < numHabitaciones; i++) {
            sb.append(habitaciones[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public String reporteIngresos(LocalDate inicio, LocalDate fin) {
        double totalHabitaciones = 0;
        double totalServicios = 0;

        for (int i = 0; i < numEstadias; i++) {
            Estadia e = estadias[i];
            Reserva r = e.getReserva();

            if (!r.getFechaInicio().isAfter(fin) &&
                !r.getFechaFin().isBefore(inicio)) {

                double total = e.calcularTotal();

                long noches = r.getNumeroNoches();
                if (noches <= 0) noches = 1;
                double hab = noches * e.getHabitacion().getPrecioPorNoche();
                double serv = total - hab;

                totalHabitaciones += hab;
                totalServicios += serv;
            }
        }

        return "REPORTE DE INGRESOS\n" +
                "Desde: " + inicio + " Hasta: " + fin + "\n" +
                "Total Habitaciones: S/ " + totalHabitaciones + "\n" +
                "Total Servicios: S/ " + totalServicios + "\n" +
                "TOTAL GENERAL: S/ " + (totalHabitaciones + totalServicios);
    }

}
