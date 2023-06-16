package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
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
public class JuegosServicio {
    //Servicio para comtrolar el AMBL de juegos
    @Autowired
    private JuegosRepositorio juegosRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    //Creamos un metodo para crear un juego pasandole los parametros necesarios para eso 
    public void crearJuego(String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego, String idEmpleado) throws MiException {
        //LLamamos al metodo validar para evitar errores
       validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego, idEmpleado);
        //Usamos el empleado repositorio para buscar que haya uno presente por la relacion entre estos
        Empleados empleado= empleadoRepositorio.findById(idEmpleado).get();
        Juegos juego = new Juegos();
        
        juego.setNombreDelJuego(nombreDelJuego);
        juego.setFechaDeAlta(new Date());
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);
        juego.setEmpleado((List<Empleados>) empleado);
         //Usamos el repositorio juego para guardar el juego y registrarlo correctamente
        juegosRepositorio.save(juego);


    }
     //Creamos un metodo para modificar un juego pasandole los parametros necesarios para eso 
    @Transactional
    public void modificarJuego(String id, String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego, String idEmpleado) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego, idEmpleado);
         //Usamos un optional para asegurarnos que el juego este presente 
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(id);
        //Usamos un optional para asegurarnos que el empleado este presente 
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(idEmpleado);
        Empleados empleado= new Empleados();
        if (respuestaEmpleado.isPresent()) {
            //Asignamos el empleado encontrado al empleado para luego guardarlo
            empleado=empleadoRepositorio.findById(idEmpleado).get();
        }
        
        
        if (respuestaJuego.isPresent()) {

            Juegos juego = respuestaJuego.get();

        juego.setNombreDelJuego(nombreDelJuego);
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);
        juego.setEmpleado((List<Empleados>) empleado);
        //Usamos el repositorio juego para guardar el juego y registrarlo correctamente
        juegosRepositorio.save(juego);

        }
    }
    //Usamos el repositorio juego para buscar uno
    public Juegos getOne(String id) {
        return juegosRepositorio.getOne(id);
    }
    
     //Usamos el repositorio juego para buscar los registros y hacer una lista
    public List<Juegos> listarJuegos() {

        List<Juegos> juego = new ArrayList();

        juego = juegosRepositorio.findAll();

        return juego;
    }
    //Usamos el repositorio juego para eliminar uno luego de buscarlo 
    public void eliminarJuego(String id) throws MiException {

        Optional<Juegos> respuesta = juegosRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Juegos juego = respuesta.get();

            juegosRepositorio.delete(juego);

        }
    }
    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    private void validarJuego(String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego, String idEmpleado) throws MiException {

        if (nombreDelJuego.isEmpty() || nombreDelJuego == null || nombreDelJuego.length() < 3) {
            throw new MiException("Debe ingresar un nombre");
        }
        if (capacidadMaxima == null || capacidadMaxima < 10) {
            throw new MiException("Debe ingresar una capacidad");
        }
        if (tipoDeJuego.isEmpty() || tipoDeJuego == null ) {
            throw new MiException("Debe ingresar el tipo de Juego");
        }
        if (cantEmpleados == null || cantEmpleados < 1) {
            throw new MiException("Debe ingresar la cantidad de empleados");
        }

        if (precioDelJuego == null || precioDelJuego < 1) {
            throw new MiException("Debe ingresar un email valido");
        }
        if (idEmpleado==null) {
            throw new MiException("El id del empleado no puede ser nulo");
            
        }
    }
}
