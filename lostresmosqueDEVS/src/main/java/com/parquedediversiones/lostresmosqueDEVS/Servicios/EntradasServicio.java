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
    //Servicio para comtrolar el AMBL de entradas
    @Autowired
    private JuegosRepositorio juegosRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    @Autowired
    private CompradoresRepositorio compradorRepositorio;
    @Autowired
    private EntradasRepositorio entradaRepositorio;
    //Creamos un metodo para crear un juego pasandole los parametros necesarios para eso 
    public void crearEntrada(Integer numeroTicket, Date fechaTicket, Integer cantidadDePersonas, Integer precioJuego, Integer precioTotal, Long legajoDni, String idJuego, String idComprador) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarEntrada(numeroTicket, fechaTicket, cantidadDePersonas, precioJuego, precioTotal, legajoDni, idJuego, idComprador);
        //Usamos el juego repositorio para buscar que haya uno presente por la relacion entre estos
        Juegos juego = juegosRepositorio.findById(idJuego).get();
        //Usamos el compradores repositorio para buscar que haya uno presente por la relacion entre estos
        Compradores comprador = compradorRepositorio.findById(idComprador).get();
        //Usamos el empleado repositorio para buscar que haya uno presente por la relacion entre estos
        Empleados empleado = empleadoRepositorio.findById(legajoDni).get();
        Entradas entrada = new Entradas();
        // chequear cantidad de personas 
        entrada.setCantidadDePersonas(cantidadDePersonas);
        entrada.setFechaTicket(fechaTicket);
        entrada.setNumeroTicket(numeroTicket);
        entrada.setPrecioJuego(precioJuego);
        entrada.setPrecioTotal(precioTotal);
        entrada.setJuego(juego);
        entrada.setComprador(comprador);
        entrada.setEmpleado(empleado);
         //Usamos el repositorio entrada para guardar la entrada y registrarlo correctamente
        entradaRepositorio.save(entrada);

    }
    //Creamos un metodo para modificar una entrada pasandole los parametros necesarios para eso 
    @Transactional
    public void modificarEntrada(String id, Integer numeroTicket, Date fechaTicket, Integer cantidadDePersonas, Integer precioJuego, Integer precioTotal, Long legajoDni, String idJuego, String idComprador) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarEntrada(numeroTicket, fechaTicket, cantidadDePersonas, precioJuego, precioTotal, legajoDni, idJuego, idComprador);
        //Usamos un optional para asegurarnos que la entrada este presente 
        Optional<Entradas> respuestaEntrada = entradaRepositorio.findById(id);
        //Usamos un optional para asegurarnos que el comprador este presente 
        Optional<Compradores> respuestaComprador = compradorRepositorio.findById(idComprador);
        //Usamos un optional para asegurarnos que el empleado este presente 
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(legajoDni);
        //Usamos un optional para asegurarnos que el juego este presente 
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(idJuego);
        Juegos juego = new Juegos();
        Compradores comprador = new Compradores();
        Empleados empleado = new Empleados();
        if (respuestaEmpleado.isPresent()) {
            //Asignamos el empleado encontrado al empleado para luego guardarlo
            empleado = empleadoRepositorio.findById(legajoDni).get();
        }

        if (respuestaJuego.isPresent()) {
            //Asignamos el juego encontrado al juego para luego guardarlo
            juego = juegosRepositorio.findById(idJuego).get();
        }

        if (respuestaComprador.isPresent()) {
            //Asignamos el comprador encontrado al comprador para luego guardarlo
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
            //Usamos el repositorio entrada para guardar la entrada y registrarla correctamente
            entradaRepositorio.save(entrada);

        }
    }
    //Usamos el repositorio entrada para buscar uno
    public Entradas getOne(String id) {
        return entradaRepositorio.getOne(id);
    }
    //Usamos el repositorio entrada para buscar los registros y hacer una lista
    public List<Entradas> listarEntradas() {

        List<Entradas> entrada = new ArrayList();

        entrada = entradaRepositorio.findAll();

        return entrada;
    }
    //Usamos el repositorio entrada para eliminar uno luego de buscarlo 
    public void eliminarEntrada(String id) throws MiException {

        Optional<Entradas> respuesta = entradaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Entradas entrada = respuesta.get();

            entradaRepositorio.delete(entrada);

        }
    }
     //Metodo para validar que los datos necesarios sean correctos y esten presentes
    public void validarEntrada(Integer numeroTicket, Date fechaTicket, Integer cantidadDePersonas, Integer precioJuego, Integer precioTotal, Long legajoDni, String idJuego, String idComprador) throws MiException {

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

        if (legajoDni == null) {
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
