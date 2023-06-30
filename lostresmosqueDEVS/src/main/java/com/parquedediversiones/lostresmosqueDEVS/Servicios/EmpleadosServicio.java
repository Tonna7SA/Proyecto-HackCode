package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Imagen;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Turno;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EmpleadosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.UsuariosRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmpleadosServicio {

    //Servicio para comtrolar el AMBL de empleado
    @Autowired
    private JuegosRepositorio juegosRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    @Autowired
    private UsuariosRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    //Creamos un metodo para crear un empleado pasandole los parametros necesarios para eso 

    public void crearEmpleado(MultipartFile archivo, Long legajoDni, String nombreUsuario, String email, String password, 
            String password2, Integer edad, String idJuego) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarEmpleado(legajoDni, nombreUsuario, email, password, password2, edad, idJuego);
        //Usamos el juego repositorio para buscar que haya uno presente por la relacion entre estos
        Juegos juego = juegosRepositorio.findById(idJuego).get();
        Empleados empleado = new Empleados();
        empleado.setLegajoDni(legajoDni);
        empleado.setNombreUsuario(nombreUsuario);
        empleado.setEmail(email);
        empleado.setActivo(true);
        empleado.setEdad(edad);
        empleado.setFechaDeAlta(new Date());
        Imagen imagen = imagenServicio.guardar(archivo);
        empleado.setImagen(imagen);
        empleado.setPassword(new BCryptPasswordEncoder().encode(password));
        empleado.setRoles(Rol.EMP);
        empleado.setTurnos(Turno.DIURNO);
        empleado.setJuego(juego);
        //Usamos el repositorio empleado para guardar el comprador y registrarlo correctamente
        empleadoRepositorio.save(empleado);

    }

    //Creamos un metodo para modificar un empleado pasandole los parametros necesarios para eso 
    @Transactional
    public void modificarEmpleado(MultipartFile archivo, Long legajoDni, String nombreUsuario, String email, 
            String password, String password2, Integer edad, String idJuego) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarEmpleado1(nombreUsuario, email, password, password2, edad, email);
        //Usamos un optional para asegurarnos que el empleado este presente 
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(legajoDni);
        //Usamos un optional para asegurarnos que el juego este presente 
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(idJuego);
        Juegos juego = new Juegos();
        if (respuestaJuego.isPresent()) {
//            //Asignamos el juego encontrado al juego para luego guardarlo
            juego = juegosRepositorio.findById(idJuego).get();
        }

        if (respuestaEmpleado.isPresent()) {

            Empleados empleado = respuestaEmpleado.get();

            empleado.setActivo(true);

            empleado.setEdad(edad);
            empleado.setEmail(email);
            empleado.setFechaDeAlta(new Date());
            empleado.setLegajoDni(legajoDni);
            empleado.setNombreUsuario(nombreUsuario);
            empleado.setPassword(new BCryptPasswordEncoder().encode(password));
            empleado.setRoles(Rol.ADM);
            String idImagen = null;
             if(empleado.getImagen() !=null){
                   idImagen = empleado.getImagen().getId();
             }
             Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
             empleado.setImagen(imagen);
//            empleado.setTurnos(turnos);
//            empleado.setJuego(juego);
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

        List<Empleados> empleados = new ArrayList();

        empleados = empleadoRepositorio.findAll();

        return empleados;
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
        @Transactional
    public void cambiarRol(Long legajoDni) throws MiException {
        Optional<Empleados> respuesta = empleadoRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {
            Empleados empleado = respuesta.get();

            if (!empleado.getRoles().equals(Rol.ADM)) {
                if (empleado.getRoles().equals(Rol.EMP)) {

                    empleado.setRoles(Rol.SUP);
                    

                } else if (empleado.getRoles().equals(Rol.SUP)) {

                    empleado.setRoles(Rol.EMP);
                    
                }
            } 
        }else{
             throw new MiException("No se pudo cambiar el rol del empleado");
        }
    }

    @Transactional
    public void cambiarEstado(Long legajoDni) throws Exception {
        Optional<Empleados> respuesta = empleadoRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {
             Empleados empleado = respuesta.get();

            if (empleado.getRoles().equals(Rol.EMP)||empleado.getRoles().equals(Rol.SUP)) {
                if (empleado.getActivo().equals(Boolean.TRUE)) {
                    empleado.setActivo(Boolean.FALSE);
                } else if (empleado.getActivo().equals(Boolean.FALSE)) {
                    empleado.setActivo(Boolean.TRUE);
                }
            } else {
                throw new MiException("No se pudo cambiar el estado del empleado");
            }

        }
    }

    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    public void validarEmpleado(Long legajoDni, String nombreUsuario, String email, String password, String password2, 
            Integer edad, String idJuego) throws MiException {

        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("El password no puede ser nulo, estar vacío o tener menos de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (legajoDni == null) {
            throw new MiException("el legajo ingresadas deben ser iguales");
        }

//        if (turnos == null) {
//            throw new MiException("Debe tener un turno asignado");
//        }
        if (idJuego.isEmpty() || idJuego == null) {
            throw new MiException("Debe tener un juego asignado");
        }
    }
    
    public void validarEmpleado1(String nombreUsuario, String email, String password, String password2, 
            Integer edad, String idJuego) throws MiException {

        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("El password no puede ser nulo, estar vacío o tener menos de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        

//        if (turnos == null) {
//            throw new MiException("Debe tener un turno asignado");
//        }
        if (idJuego.isEmpty() || idJuego == null) {
            throw new MiException("Debe tener un juego asignado");
        }
    }
}
