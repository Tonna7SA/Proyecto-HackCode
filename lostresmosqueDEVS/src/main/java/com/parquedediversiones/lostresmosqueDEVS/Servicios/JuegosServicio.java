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

    @Autowired
    private JuegosRepositorio juegosRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;

    public void crearJuego(String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego, String idEmpleado) throws MiException {

       validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego, idEmpleado);
        Empleados empleado= empleadoRepositorio.findById(idEmpleado).get();
        Juegos juego = new Juegos();
        
        juego.setNombreDelJuego(nombreDelJuego);
        juego.setFechaDeAlta(new Date());
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);
        juego.setEmpleado(empleado);
        
        juegosRepositorio.save(juego);


    }

    @Transactional
    public void modificarJuego(String id, String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego, String idEmpleado) throws MiException {

        validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego, idEmpleado);

        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(id);
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(idEmpleado);
        Empleados empleado= new Empleados();
        if (respuestaEmpleado.isPresent()) {
            
            empleado=empleadoRepositorio.findById(idEmpleado).get();
        }
        
        
        if (respuestaJuego.isPresent()) {

            Juegos juego = respuestaJuego.get();

        juego.setNombreDelJuego(nombreDelJuego);
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);
        juego.setEmpleado(empleado);
        juegosRepositorio.save(juego);

        }
    }

    public Juegos getOne(String id) {
        return juegosRepositorio.getOne(id);
    }
    

    public List<Juegos> listarJuegos() {

        List<Juegos> juego = new ArrayList();

        juego = juegosRepositorio.findAll();

        return juego;
    }

    public void eliminarJuego(String id) throws MiException {

        Optional<Juegos> respuesta = juegosRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Juegos juego = respuesta.get();

            juegosRepositorio.delete(juego);

        }
    }

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
