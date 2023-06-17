package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Turno;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EmpleadosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadosServicio {
    //Servicio para comtrolar el AMBL de empleado
    @Autowired
    private JuegosRepositorio juegosRepositorio;
     @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
     //Creamos un metodo para crear un empleado pasandole los parametros necesarios para eso 
    public void crearEmpleado(Long legajoDni, String nombreUsuario, String email, String password, Rol roles, Integer edad, Boolean activo, Date fechaDeAlta,
            Turno turnos, String idJuego) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarEmpleado(legajoDni, nombreUsuario, email, password, roles, edad, activo, fechaDeAlta, turnos, idJuego);
        //Usamos el juego repositorio para buscar que haya uno presente por la relacion entre estos
        Juegos juego = juegosRepositorio.findById(idJuego).get();
        Empleados empleado = new Empleados();

        empleado.setActivo(true);
        
        empleado.setEdad(edad);
        empleado.setEmail(email);
        empleado.setFechaDeAlta(new Date());
        empleado.setLegajoDni(legajoDni);
        empleado.setNombreUsuario(nombreUsuario);
        empleado.setPassword(password);
        empleado.setRoles(roles);
        empleado.setTurnos(turnos);
        empleado.setJuego(juego);
        //Usamos el repositorio empleado para guardar el comprador y registrarlo correctamente
       empleadoRepositorio.save(empleado);

    }
    //Creamos un metodo para modificar un empleado pasandole los parametros necesarios para eso 
    @Transactional
    public void modificarEmpleado(Long legajoDni, String nombreUsuario, String email, String password, Rol roles,  Integer edad, Boolean activo, Date fechaDeAlta,
            Turno turnos, String idJuego) throws MiException {
         //LLamamos al metodo validar para evitar errores
        validarEmpleado(legajoDni, nombreUsuario, email, password, roles, edad, activo, fechaDeAlta, turnos, idJuego);
        //Usamos un optional para asegurarnos que el empleado este presente 
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(legajoDni);
         //Usamos un optional para asegurarnos que el juego este presente 
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(idJuego);
        Juegos juego = new Juegos();
        if (respuestaJuego.isPresent()) {
            //Asignamos el juego encontrado al juego para luego guardarlo
            juego = juegosRepositorio.findById(idJuego).get();
        }

        if (respuestaEmpleado.isPresent()) {

            Empleados empleado = respuestaEmpleado.get();

            empleado.setActivo(activo);
            
            empleado.setEdad(edad);
            empleado.setEmail(email);
            empleado.setFechaDeAlta(fechaDeAlta);
            empleado.setLegajoDni(legajoDni);
            empleado.setNombreUsuario(nombreUsuario);
            empleado.setPassword(password);
            empleado.setPassword(password);
            empleado.setRoles(roles);
            empleado.setTurnos(turnos);
            empleado.setJuego(juego);
            //Usamos el repositorio empleado para guardar el empleado y registrarlo correctamente
            empleadoRepositorio.save(empleado);

        }
    }
    //Usamos el repositorio empleado para buscar uno
    public Empleados getOne(Long legajoDni) {
        return empleadoRepositorio.getOne(legajoDni);
    }
    //Usamos el repositorio empleado para buscar los registros y hacer una lista
    public List<Empleados> listarEmpleados() {

        List<Empleados> empleado = new ArrayList();

        empleado = empleadoRepositorio.findAll();

        return empleado;
    }
    //Usamos el repositorio empleado para eliminar uno luego de buscarlo 
    public void eliminarEmpleado(Long legajoDni) throws MiException {
         //Optional para asegurarnos que el empleado buscado esta presente
        Optional<Empleados> respuesta = empleadoRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {

            Empleados empleado = respuesta.get();

            empleadoRepositorio.delete(empleado);

        }
    }
    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    public void validarEmpleado(Long legajoDni, String nombreUsuario, String email, String password, Rol roles, Integer edad, Boolean activo, Date fechaDeAlta, Turno turnos, String idJuego) throws MiException {

        if (legajoDni!=0||  legajoDni == null) {
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
     
        if (edad == null || edad < 18) {
            throw new MiException("El empleado debe tener una edad ingresada correctamente y no puede ser menor a 18");

        }
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


