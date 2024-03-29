package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Ventas;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.VentasRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.CompradoresServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EntradasServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.VentasServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

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
    @Autowired
    private VentasRepositorio ventaRepositorio;
    
     // Vista para registrar una venta
    @GetMapping("/registrar/{id}")
    public String registrar(@PathVariable String id, ModelMap modelo, HttpSession session) {
        
        modelo.put("compradores", compradorServicio.getOne(id));
        Usuarios usuarios = (Usuarios) session.getAttribute("usuariosession");
        modelo.put("usuarios", usuarios);
        System.out.println("sale de get");
       
        return "registro_ventas_form.html";
    }
     
// Luego de pasar los datos por parametro llamamos al servicio ventas y lo utilizamos  para registrar una venta
    @PostMapping("/registro")
    public String registro(String nombreJuego, String tipoJuego, Integer precioJuego, String nombreComprador, String dniComprador, 
            String emailComprador, String nombreVendedor, Long legajoVendedor, Integer totalVenta, Date fechaVenta, ModelMap modelo) throws MiException {
        // Metodo try and catch para asegurarnos de captar errores 
        
         System.out.println("entrapost");
        try {
            ventaServicio.crearVenta(nombreJuego, tipoJuego, precioJuego, nombreComprador, 
                    dniComprador, emailComprador, nombreVendedor, legajoVendedor, totalVenta);
            System.out.println("hola");
        } catch (MiException ex) {
            
            modelo.put("Error", ex.getMessage());
            System.out.println("chau");
     
            return "registro_ventas_form.html";
            
        }
        System.out.println("prueba");
        return "index.html";
        
    }
     //Llamamos al servicio venta para listar las ventas
//    @GetMapping("/listar")
//    public String listar(ModelMap modelo) {
//        List<Ventas> ventas = ventaServicio.listarVentas();
//        modelo.put("ventas", ventas);
//        
//        return "ventas_list.html";
//    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo, @Param("keyword")String keyword) {
        try{
        List<Ventas> ventas = new ArrayList<>();
        if(keyword==null || keyword==""){
            ventaRepositorio.findAll().forEach(ventas::add);
        }else{
            ventaRepositorio.DniDevuelvePorFecha(keyword).forEach(ventas::add);
            modelo.addAttribute("keyword", keyword);
        }
        modelo.addAttribute("ventas", ventas );
        }catch(Exception e){
        modelo.addAttribute("error", e.getMessage());
        }
        return "ventas_list.html";
    }
    
//    // Luego de pasar los datos por parametro llamamos al servicio ventas para pasar los datos al PostMapping y hacer uso del metodo modificar
//    @GetMapping("/modificar/{id}")
//    public String modificar(@PathVariable String id, @RequestParam Integer totalVenta, @RequestParam Date fechaVenta, @RequestParam String idEmpleado,
//            @RequestParam String idEntrada, @RequestParam String idComprador, ModelMap modelo) {
//        
//        modelo.put("ventas", ventaServicio.getOne(id));
//        List<Empleados> empleados = empleadoServicio.listarEmpleados();
//        List<Entradas> entradas = entradaServicio.listarEntradas();
//        List<Compradores> compradores = compradorServicio.listarCompradores();
//        
//        modelo.addAttribute("entradas", entradas);
//        modelo.addAttribute("compradores", compradores);
//        modelo.addAttribute("empleados", empleados);
//        
//        return "ventas_modificar.html";
//    }
//     // Luego de pasar los datos por parametro llamamos al servicio ventas y lo utilizamos  para modificar una venta
//    @PostMapping("/modificar/{id}")
//    public String modificarVenta(@PathVariable String id, @RequestParam Integer numeroTicket, @RequestParam Date fechaTicket, @RequestParam Integer cantidadDePersonas, @RequestParam Integer precioJuego,
//            @RequestParam Integer precioTotal, @RequestParam Long legajoDni, @RequestParam String idJuego, @RequestParam String idComprador, ModelMap modelo) {
//       // Metodo try and catch para asegurarnos de captar errores 
//        try {
//            List<Empleados> empleados = empleadoServicio.listarEmpleados();
//            List<Entradas> entradas = entradaServicio.listarEntradas();
//            List<Compradores> compradores = compradorServicio.listarCompradores();
//            
//            modelo.addAttribute("entradas", entradas);
//            modelo.addAttribute("compradores", compradores);
//            modelo.addAttribute("empleados", empleados);
//            
//            ventaServicio.modificarVenta(id, precioTotal, fechaTicket, legajoDni, idJuego, idComprador);
//            
//            return "redirect:../listar";
//            
//        } catch (MiException ex) {
//             List<Empleados> empleados = empleadoServicio.listarEmpleados();
//            List<Entradas> entradas = entradaServicio.listarEntradas();
//            List<Compradores> compradores = compradorServicio.listarCompradores();
//            
//            modelo.addAttribute("entradas", entradas);
//            modelo.addAttribute("compradores", compradores);
//            modelo.addAttribute("empleados", empleados);
//            
//            modelo.put("error", ex.getMessage());
//            
//             return "ventas_modificar.html";
//        }
//        
//    }
// 
//    //Llamamos al servicio ventas con los datos del GetMapping para eliminar efectivamente una venta
//    @GetMapping("/eliminar/{id}")
//    public String eliminarVenta(@PathVariable String id, ModelMap modelo, RedirectAttributes redirectAttributes)throws MiException {
//         modelo.put("ventas", ventaServicio.getOne(id));
//        // Metodo try and catch para asegurarnos de captar errores 
//        
//        try {
//            ventaServicio.eliminarVenta(id);
//            modelo.put("Exito", "Se elimino la venta exitosamente");
//            redirectAttributes.addFlashAttribute("success", "Venta eliminada exitosamente");
//            return "redirect:../listar";
//        } catch (MiException ex) {
//            redirectAttributes.addFlashAttribute("success", "La venta no puede ser eliminada");
//            return "redirect:../listar";
//        }
//
//    }

    }

    

