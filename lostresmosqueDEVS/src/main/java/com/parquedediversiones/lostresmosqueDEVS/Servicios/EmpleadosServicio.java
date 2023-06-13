package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Turno;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EmpleadosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadosServicio {

    @Autowired
    private JuegosRepositorio juegosRepositorio;
     @Autowired
    private EmpleadosRepositorio empleadoRepositorio;

    public void crearEmpleado(String id, String nombreUsuario, String email, String password, Rol roles, String dni, Integer edad, Boolean activo, Date fechaDeAlta,
            Turno turnos, String idJuego) throws MiException {

        validarEmpleado(id, nombreUsuario, email, password, roles, dni, edad, activo, fechaDeAlta, turnos, idJuego);
        Juegos juego = juegosRepositorio.findById(idJuego).get();
        Empleados empleado = new Empleados();

        empleado.setActivo(activo);
        empleado.setDni(dni);
        empleado.setEdad(edad);
        empleado.setEmail(email);
        empleado.setFechaDeAlta(fechaDeAlta);
        empleado.setId(id);
        empleado.setNombreUsuario(nombreUsuario);
        empleado.setPassword(password);
        empleado.setRoles(roles);
        empleado.setTurnos(turnos);
        empleado.setJuego(juego);

        juegosRepositorio.save(juego);

    }

    @Transactional
    public void modificarEmpleado(String id, String nombreUsuario, String email, String password, Rol roles, String dni, Integer edad, Boolean activo, Date fechaDeAlta,
            Turno turnos, String idJuego) throws MiException {

        validarEmpleado(id, nombreUsuario, email, password, roles, dni, edad, activo, fechaDeAlta, turnos, idJuego);
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(id);
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(idJuego);
        Juegos juego = new Juegos();
        if (respuestaJuego.isPresent()) {

            juego = juegosRepositorio.findById(idJuego).get();
        }

        if (respuestaEmpleado.isPresent()) {

            Empleados empleado = respuestaEmpleado.get();

            empleado.setActivo(activo);
            empleado.setDni(dni);
            empleado.setEdad(edad);
            empleado.setEmail(email);
            empleado.setFechaDeAlta(fechaDeAlta);
            empleado.setId(id);
            empleado.setNombreUsuario(nombreUsuario);
            empleado.setPassword(password);
            empleado.setPassword(password);
            empleado.setRoles(roles);
            empleado.setTurnos(turnos);
            empleado.setJuego(juego);
            empleadoRepositorio.save(empleado);

        }
    }

    public Empleados getOne(String id) {
        return empleadoRepositorio.getOne(id);
    }

    public List<Empleados> listarEmpleados() {

        List<Empleados> empleado = new ArrayList();

        empleado = empleadoRepositorio.findAll();

        return empleado;
    }

    public void eliminarEmpleado(String id) throws MiException {

        Optional<Empleados> respuesta = empleadoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Empleados empleado = respuesta.get();

            empleadoRepositorio.delete(empleado);

        }
    }

    public void validarEmpleado(String id, String nombreUsuario, String email, String password, Rol roles, String dni, Integer edad, Boolean activo, Date fechaDeAlta, Turno turnos, String idJuego) throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("Debe tener un id de usuario");
        }
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("Debe ingresar un nombre de usuario");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("Debe ingresar un email");
        }
        if (password == null || password.length() <= 7) {
            throw new MiException("Debe tener una contraseña mayor a 7 caracteres");
        }

        if (roles == null) {
            throw new MiException("Debe tener un rol valido");
        }
        if (dni.isEmpty() || dni == null) {
            throw new MiException("Debe tener dni ingresado");
        }
        if (edad == null || edad < 18) {
            throw new MiException("El empleado debe tener una edad ingresada correctamente y no puede ser menor a 18");

        }
//         a chequear como validar un booleano 
//        if (activo) {
//            throw new MiException("Debe tener dni ingresado");
//        }
        if (fechaDeAlta == null) {
            throw new MiException("Debe tener una fecha de alta");
        }
        if (turnos == null) {
            throw new MiException("Debe tener un turno asignado");
        }
        if (idJuego.isEmpty() || idJuego == null) {
            throw new MiException("Debe tener un juego asignado");
        }
    }
}


