/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Entradas;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Ventas;

import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;

import com.parquedediversiones.lostresmosqueDEVS.Servicios.CompradoresServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EntradasServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.VentasServicio;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Pergo
 */
@Controller
@RequestMapping("/ventas")
public class VentasControlador {
    
    @Autowired
    private EmpleadosServicio empleadoServicio;
    @Autowired
    private CompradoresServicio compradorServicio;
    @Autowired
    private EntradasServicio entradaServicio;
    @Autowired
    private VentasServicio ventaServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        List<Entradas> entradas = entradaServicio.listarEntradas();
        List<Compradores> compradores = compradorServicio.listarCompradores();
        
        modelo.addAttribute("entradas", entradas);
        modelo.addAttribute("compradores", compradores);
        modelo.addAttribute("empleados", empleados);
        
        return "registro_ventas_form.html";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam Integer totalVenta, @RequestParam Date fechaVenta, @RequestParam String idEmpleado,
            @RequestParam String idEntrada, @RequestParam String idComprador, ModelMap modelo) throws MiException {
        try {
            ventaServicio.crearVenta(totalVenta, fechaVenta, idEmpleado, idEntrada, idComprador);
            modelo.put("Exito", "La venta se registro correctamente");
            
        } catch (MiException ex) {
            
            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Entradas> entradas = entradaServicio.listarEntradas();
            List<Compradores> compradores = compradorServicio.listarCompradores();
            
            modelo.addAttribute("entradas", entradas);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleados", empleados);
            
            modelo.put("Error", ex.getMessage());
            
            return "registro_ventas_form.html";
            
        }
        
        return "index.html";
        
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Ventas> ventas = ventaServicio.listarVentas();
        modelo.put("ventas", ventas);
        
        return "listar_ventas.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam Integer totalVenta, @RequestParam Date fechaVenta, @RequestParam String idEmpleado,
            @RequestParam String idEntrada, @RequestParam String idComprador, ModelMap modelo) {
        
        modelo.put("ventas", ventaServicio.getOne(id));
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        List<Entradas> entradas = entradaServicio.listarEntradas();
        List<Compradores> compradores = compradorServicio.listarCompradores();
        
        modelo.addAttribute("entradas", entradas);
        modelo.addAttribute("compradores", compradores);
        modelo.addAttribute("empleados", empleados);
        
        return "ventas_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificarVenta(@PathVariable String id, @RequestParam Integer numeroTicket, @RequestParam Date fechaTicket, @RequestParam Integer cantidadDePersonas, @RequestParam Integer precioJuego,
            @RequestParam Integer precioTotal, @RequestParam String idEmpleado, @RequestParam String idJuego, @RequestParam String idComprador, ModelMap modelo) {
        try {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Entradas> entradas = entradaServicio.listarEntradas();
            List<Compradores> compradores = compradorServicio.listarCompradores();
            
            modelo.addAttribute("entradas", entradas);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleados", empleados);
            
            ventaServicio.modificarVenta(id, precioTotal, fechaTicket, idEmpleado, idEmpleado, idComprador);
            
            return "redirect:../listar";
            
        } catch (MiException ex) {
             List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Entradas> entradas = entradaServicio.listarEntradas();
            List<Compradores> compradores = compradorServicio.listarCompradores();
            
            modelo.addAttribute("entradas", entradas);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleados", empleados);
            
            modelo.put("error", ex.getMessage());
            
             return "ventas_modificar.html";
        }
        
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("ventas", ventaServicio.getOne(id));

        return "ventas_eliminar.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable String id, ModelMap modelo) {
        try {
            ventaServicio.eliminarVenta(id);
            modelo.put("Exito", "Se elimino la venta exitosamente");

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:../listar";
        }

    }
    
}
