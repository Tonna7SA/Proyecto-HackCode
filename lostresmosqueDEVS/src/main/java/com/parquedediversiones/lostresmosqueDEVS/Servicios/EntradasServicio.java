package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Entradas;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.CompradoresRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EmpleadosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EntradasRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;
import org.springframework.stereotype.Service;
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
public class EntradasServicio {

    @Autowired
    private JuegosRepositorio juegosRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    @Autowired
    private CompradoresRepositorio compradorRepositorio;
    @Autowired
    private EntradasRepositorio entradaRepositorio;

    public void crearEntrada(Integer numeroTicket, Date fechaTicket, Integer cantidadDePersonas, Integer precioJuego, Integer precioTotal, String idEmpleado, String idJuego, String idComprador) throws MiException {

        validarEntrada(numeroTicket, fechaTicket, cantidadDePersonas, precioJuego, precioTotal, idEmpleado, idJuego, idComprador);
        Juegos juego = juegosRepositorio.findById(idJuego).get();
        Compradores comprador = compradorRepositorio.findById(idJuego).get();
        Empleados empleado = empleadoRepositorio.findById(idJuego).get();
        Entradas entrada = new Entradas();
        entrada.setCantidadDePersonas(cantidadDePersonas);
        entrada.setFechaTicket(fechaTicket);
        entrada.setNumeroTicket(numeroTicket);
        entrada.setPrecioJuego(precioJuego);
        entrada.setPrecioTotal(precioTotal);
        entrada.setJuego(juego);
        entrada.setComprador(comprador);
        entrada.setEmpleado(empleado);

        entradaRepositorio.save(entrada);

    }

    @Transactional
    public void modificarEmpleado(String id, Integer numeroTicket, Date fechaTicket, Integer cantidadDePersonas, Integer precioJuego, Integer precioTotal, String idEmpleado, String idJuego, String idComprador) throws MiException {

        validarEntrada(numeroTicket, fechaTicket, cantidadDePersonas, precioJuego, precioTotal, idEmpleado, idJuego, idComprador);
        Optional<Entradas> respuestaEntrada = entradaRepositorio.findById(id);
        Optional<Compradores> respuestaComprador = compradorRepositorio.findById(idComprador);
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(idEmpleado);
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(idJuego);
        Juegos juego = new Juegos();
        Compradores comprador = new Compradores();
        Empleados empleado = new Empleados();
        if (respuestaEmpleado.isPresent()) {

            empleado = empleadoRepositorio.findById(idEmpleado).get();
        }

        if (respuestaJuego.isPresent()) {

            juego = juegosRepositorio.findById(idJuego).get();
        }

        if (respuestaComprador.isPresent()) {

            comprador = compradorRepositorio.findById(idComprador).get();
        }

        if (respuestaEntrada.isPresent()) {

            Entradas entrada = respuestaEntrada.get();

            entrada.setCantidadDePersonas(cantidadDePersonas);
            entrada.setFechaTicket(fechaTicket);
            entrada.setNumeroTicket(numeroTicket);
            entrada.setPrecioJuego(precioJuego);
            entrada.setPrecioTotal(precioTotal);
            entrada.setJuego(juego);
            entrada.setComprador(comprador);
            entrada.setEmpleado(empleado);

            entradaRepositorio.save(entrada);

        }
    }

    public Entradas getOne(String id) {
        return entradaRepositorio.getOne(id);
    }

    public List<Entradas> listarEntradas() {

        List<Entradas> entrada = new ArrayList();

        entrada = entradaRepositorio.findAll();

        return entrada;
    }

    public void eliminarEntradas(String id) throws MiException {

        Optional<Entradas> respuesta = entradaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Entradas entrada = respuesta.get();

            entradaRepositorio.delete(entrada);

        }
    }

    public void validarEntrada(Integer numeroTicket, Date fechaTicket, Integer cantidadDePersonas, Integer precioJuego, Integer precioTotal, String idEmpleado, String idJuego, String idComprador) throws MiException {

        if (numeroTicket == null) {
            throw new MiException("Debe tener nro de Ticket");
        }
        if (fechaTicket == null) {
            throw new MiException("Debe ingresar una fecha del Ticket");
        }
        if (cantidadDePersonas == null) {
            throw new MiException("Debe ingresar la cantidad de personas");
        }
        if (precioJuego == null || precioJuego <= 0) {
            throw new MiException("Debe ingresar el precio del juego (debe ser mayor a 0)");
        }

        if (precioTotal == null || precioTotal <= 0) {
            throw new MiException("Debe ingresar el precio total (debe ser mayor a 0)");
        }

        if (idEmpleado.isEmpty() || idEmpleado == null) {
            throw new MiException("Debe tener un empleado asignado");
        }
        if (idJuego.isEmpty() || idJuego == null) {
            throw new MiException("Debe tener un juego asignado");
        }
        if (idComprador.isEmpty() || idComprador == null) {
            throw new MiException("Debe tener un comprador asignado");
        }
    }
}
