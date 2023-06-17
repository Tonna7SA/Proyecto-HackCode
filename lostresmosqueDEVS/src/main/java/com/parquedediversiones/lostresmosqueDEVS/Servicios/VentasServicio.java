/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Entradas;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Ventas;

import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.CompradoresRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EmpleadosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EntradasRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.VentasRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pergo
 */

@Service
public class VentasServicio {
    //Servicio para comtrolar el AMBL de ventas
    @Autowired
    private EntradasRepositorio entradaRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    @Autowired
    private CompradoresRepositorio compradorRepositorio;
    @Autowired
    private VentasRepositorio ventaRepositorio;
     //Creamos un metodo para crear un juego pasandole los parametros necesarios para eso 
    public void crearVenta(Integer totalVenta, Date fechaVenta, Long legajoDni, String idEntrada, String idComprador) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarVenta(totalVenta, fechaVenta, legajoDni, idEntrada, idComprador);
         //Usamos el compradores repositorio para buscar que haya uno presente por la relacion entre estos
        Compradores comprador = compradorRepositorio.findById(idComprador).get();
        //Usamos el repositorio entrada para para buscar que haya uno presente por la relacion entre estos
        Entradas entrada = entradaRepositorio.findById(idEntrada).get();
        //Usamos el empleado repositorio para buscar que haya uno presente por la relacion entre estos
        Empleados empleado = empleadoRepositorio.findById(legajoDni).get();
        
        Ventas venta = new Ventas();

        // chequear cantidad de personas 
        venta.setTotalVenta(totalVenta);
        venta.setFechaVenta(fechaVenta);
        venta.setComprador(comprador);
        venta.setEntrada((List<Entradas>) entrada);
        venta.setEmpleado(empleado);
          //Usamos el repositorio venta para guardar la entrada y registrarla correctamente
        ventaRepositorio.save(venta);

    }
    //Creamos un metodo para modificar una entrada pasandole los parametros necesarios para eso 
    @Transactional
    public void modificarVenta(String id, Integer totalVenta, Date fechaVenta, Long legajoDni, String idEntrada, String idComprador) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarVenta(totalVenta, fechaVenta, legajoDni, idEntrada, idComprador);
        
        Optional<Ventas> respuestaVenta = ventaRepositorio.findById(id);
         //Usamos un optional para asegurarnos que la entrada este presente 
        Optional<Entradas> respuestaEntrada = entradaRepositorio.findById(idEntrada);
         //Usamos un optional para asegurarnos que el comprador este presente 
        Optional<Compradores> respuestaComprador = compradorRepositorio.findById(idComprador);
         //Usamos un optional para asegurarnos que el juego este presente 
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(legajoDni);

        Entradas entrada = new Entradas();
        Compradores comprador = new Compradores();
        Empleados empleado = new Empleados();
        if (respuestaEmpleado.isPresent()) {
             //Asignamos el empleado encontrado al empleado para luego guardarlo
            empleado = empleadoRepositorio.findById(legajoDni).get();
        }

        if (respuestaEntrada.isPresent()) {
           //Asignamos la entrada encontrada a la entrada para luego guardarlo
            entrada = entradaRepositorio.findById(idEntrada).get();
        }

        if (respuestaComprador.isPresent()) {
               //Asignamos el comprador encontrado al comprador para luego guardarlo
            comprador = compradorRepositorio.findById(idComprador).get();
        }

        if (respuestaVenta.isPresent()) {

            Ventas venta = respuestaVenta.get();

            venta.setTotalVenta(totalVenta);
            venta.setFechaVenta(fechaVenta);
            venta.setComprador(comprador);
            venta.setEntrada((List<Entradas>) entrada);
            venta.setEmpleado(empleado);
            //Usamos el repositorio venta para guardar la entrada y registrarla correctamente
            ventaRepositorio.save(venta);

        }
    }
    //Usamos el repositorio venta para buscar uno
     public Ventas getOne(String id) {
        return ventaRepositorio.getOne(id);
    }
     //Usamos el repositorio venta para buscar los registros y hacer una lista
    public List<Ventas> listarVentas() {

        List<Ventas> venta = new ArrayList();

        venta = ventaRepositorio.findAll();

        return venta;
    }
    //Usamos el repositorio venta para eliminar uno luego de buscarlo 
    public void eliminarVenta(String id) throws MiException {

        Optional<Ventas> respuesta = ventaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Ventas venta = respuesta.get();

            ventaRepositorio.delete(venta);

        }
    }
    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    public void validarVenta(Integer totalVenta, Date fechaVenta, Long legajoDni, String idEntrada, String idComprador) throws MiException {

        if (totalVenta == null || totalVenta <= 0) {
            throw new MiException("Debe tener un monto total de la venta y debe ser mayor a 0");
        }
        if (fechaVenta == null) {
            throw new MiException("Debe ingresar una fecha de la venta");
        }

        if (legajoDni == null) {
            throw new MiException("Debe tener un empleado asignado");
        }
        if (idEntrada.isEmpty() || idEntrada == null) {
            throw new MiException("Debe tener una entrada asignado");
        }
        if (idComprador.isEmpty() || idComprador == null) {
            throw new MiException("Debe tener un comprador asignado");
        }
    }
}
