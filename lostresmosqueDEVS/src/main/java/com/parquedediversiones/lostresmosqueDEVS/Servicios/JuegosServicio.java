package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Service
public class JuegosServicio {

    @Autowired
    private JuegosRepositorio juegosRepositorio;

    public void crearJuego(String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego) throws MiException {

       validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);

        Juegos juego = new Juegos();
        
        juego.setNombreDelJuego(nombreDelJuego);
        juego.setFechaDeAlta(new Date());
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);
        
        juegosRepositorio.save(juego);


    }

    @Transactional
    public void modificarJuego(String id, String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego) throws MiException {

        validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);

        Optional<Juegos> respuesta = juegosRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Juegos juego = respuesta.get();

        juego.setNombreDelJuego(nombreDelJuego);
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);
        
        juegosRepositorio.save(juego);

        }
    }

    public Juegos getOne(String id) {
        return juegosRepositorio.getOne(id);
    }
    

    public List<Juegos> listarJuegos() {

        List<Juegos> comprador = new ArrayList();

        comprador = juegosRepositorio.findAll();

        return comprador;
    }

    public void eliminarJuego(String id) throws MiException {

        Optional<Juegos> respuesta = juegosRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Juegos juego = respuesta.get();

            juegosRepositorio.delete(juego);

        }
    }

    private void validarJuego(String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego) throws MiException {

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
    }
}
