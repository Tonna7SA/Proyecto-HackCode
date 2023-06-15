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

    @Autowired
    private EntradasRepositorio entradaRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    @Autowired
    private CompradoresRepositorio compradorRepositorio;
    @Autowired
    private VentasRepositorio ventaRepositorio;

    public void crearVenta(Integer totalVenta, Date fechaVenta, String idEmpleado, String idEntrada, String idComprador) throws MiException {

        validarVenta(totalVenta, fechaVenta, idEmpleado, idEntrada, idComprador);
        Compradores comprador = compradorRepositorio.findById(idComprador).get();
        Entradas entrada = entradaRepositorio.findById(idEntrada).get();
        Empleados empleado = empleadoRepositorio.findById(idEmpleado).get();
        Ventas venta = new Ventas();

        // chequear cantidad de personas 
        venta.setTotalVenta(totalVenta);
        venta.setFechaVenta(fechaVenta);
        venta.setComprador(comprador);
        venta.setEntrada((List<Entradas>) entrada);
        venta.setEmpleado(empleado);

        ventaRepositorio.save(venta);

    }

    @Transactional
    public void modificarVenta(String id, Integer totalVenta, Date fechaVenta, String idEmpleado, String idEntrada, String idComprador) throws MiException {

        validarVenta(totalVenta, fechaVenta, idEmpleado, idEntrada, idComprador);
        Optional<Ventas> respuestaVenta = ventaRepositorio.findById(id);
        Optional<Entradas> respuestaEntrada = entradaRepositorio.findById(idEntrada);
        Optional<Compradores> respuestaComprador = compradorRepositorio.findById(idComprador);
        Optional<Empleados> respuestaEmpleado = empleadoRepositorio.findById(idEmpleado);

        Entradas entrada = new Entradas();
        Compradores comprador = new Compradores();
        Empleados empleado = new Empleados();
        if (respuestaEmpleado.isPresent()) {

            empleado = empleadoRepositorio.findById(idEmpleado).get();
        }

        if (respuestaEntrada.isPresent()) {

            entrada = entradaRepositorio.findById(idEntrada).get();
        }

        if (respuestaComprador.isPresent()) {

            comprador = compradorRepositorio.findById(idComprador).get();
        }

        if (respuestaVenta.isPresent()) {

            Ventas venta = respuestaVenta.get();

            venta.setTotalVenta(totalVenta);
            venta.setFechaVenta(fechaVenta);
            venta.setComprador(comprador);
            venta.setEntrada((List<Entradas>) entrada);
            venta.setEmpleado(empleado);

            ventaRepositorio.save(venta);

        }
    }
     public Ventas getOne(String id) {
        return ventaRepositorio.getOne(id);
    }

    public List<Ventas> listarVentas() {

        List<Ventas> venta = new ArrayList();

        venta = ventaRepositorio.findAll();

        return venta;
    }

    public void eliminarVenta(String id) throws MiException {

        Optional<Ventas> respuesta = ventaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Ventas venta = respuesta.get();

            ventaRepositorio.delete(venta);

        }
    }

    public void validarVenta(Integer totalVenta, Date fechaVenta, String idEmpleado, String idEntrada, String idComprador) throws MiException {

        if (totalVenta == null || totalVenta <= 0) {
            throw new MiException("Debe tener un monto total de la venta y debe ser mayor a 0");
        }
        if (fechaVenta == null) {
            throw new MiException("Debe ingresar una fecha de la venta");
        }

        if (idEmpleado.isEmpty() || idEmpleado == null) {
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
