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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Pergo
 */
// Controladora para las ventas y sus acciones
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
     // Vista para registrar una venta
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
     // Luego de pasar los datos por parametro llamamos al servicio ventas y lo utilizamos  para registrar una venta
    @PostMapping("/registro")
    public String registro(@RequestParam Integer totalVenta, @RequestParam Date fechaVenta, @RequestParam Long legajoDni,
            @RequestParam String idEntrada, @RequestParam String idComprador, ModelMap modelo) throws MiException {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            ventaServicio.crearVenta(totalVenta, fechaVenta, legajoDni, idEntrada, idComprador);
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
     //Llamamos al servicio venta para listar las ventas
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Ventas> ventas = ventaServicio.listarVentas();
        modelo.put("ventas", ventas);
        
        return "listar_ventas.html";
    }
    // Luego de pasar los datos por parametro llamamos al servicio ventas para pasar los datos al PostMapping y hacer uso del metodo modificar
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
     // Luego de pasar los datos por parametro llamamos al servicio ventas y lo utilizamos  para modificar una venta
    @PostMapping("/modificar/{id}")
    public String modificarVenta(@PathVariable String id, @RequestParam Integer numeroTicket, @RequestParam Date fechaTicket, @RequestParam Integer cantidadDePersonas, @RequestParam Integer precioJuego,
            @RequestParam Integer precioTotal, @RequestParam Long legajoDni, @RequestParam String idJuego, @RequestParam String idComprador, ModelMap modelo) {
       // Metodo try and catch para asegurarnos de captar errores 
        try {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Entradas> entradas = entradaServicio.listarEntradas();
            List<Compradores> compradores = compradorServicio.listarCompradores();
            
            modelo.addAttribute("entradas", entradas);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleados", empleados);
            
            ventaServicio.modificarVenta(id, precioTotal, fechaTicket, legajoDni, idJuego, idComprador);
            
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
 
    //Llamamos al servicio ventas con los datos del GetMapping para eliminar efectivamente una venta
    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable String id, ModelMap modelo, RedirectAttributes redirectAttributes)throws MiException {
         modelo.put("ventas", ventaServicio.getOne(id));
        // Metodo try and catch para asegurarnos de captar errores 
        
        try {
            ventaServicio.eliminarVenta(id);
            modelo.put("Exito", "Se elimino la venta exitosamente");
            redirectAttributes.addFlashAttribute("success", "Venta eliminada exitosamente");
            return "redirect:../listar";
        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("success", "La venta no puede ser eliminada");
            return "redirect:../listar";
        }

    }

    }

    

