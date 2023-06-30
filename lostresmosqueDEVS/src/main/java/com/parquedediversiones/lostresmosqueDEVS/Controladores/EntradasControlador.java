package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Entradas;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.CompradoresServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EntradasServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.JuegosServicio;
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
 * @author Tonna/SA FR34K
 */
/**/
// Controladora para las entradas y sus acciones
@Controller
@RequestMapping("/entradas")
public class EntradasControlador {

    @Autowired
    private JuegosServicio juegoServicio;
    @Autowired
    private EmpleadosServicio empleadoServicio;
    @Autowired
    private CompradoresServicio compradorServicio;
    @Autowired
    private EntradasServicio entradaServicio;
    // Vista para registrar una entrada 

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        List<Juegos> juegos = juegoServicio.listarJuegos();
        List<Compradores> compradores = compradorServicio.listarCompradores();

        modelo.addAttribute("juegos", juegos);
        modelo.addAttribute("compradores", compradores);
        modelo.addAttribute("empleado", empleados);

        return "registro_entradas_form.html";

    }

    // Luego de pasar los datos por parametro llamamos al servicio entrada y lo utilizamos  para registrar una entrada
    @PostMapping("/registro")
    public String registro(@RequestParam Integer numeroTicket, @RequestParam Date fechaTicket, @RequestParam Integer cantidadDePersonas, @RequestParam Integer precioJuego,
            @RequestParam Integer precioTotal, @RequestParam Long legajoDni, @RequestParam String idJuego, @RequestParam String idComprador, ModelMap modelo) throws MiException {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            entradaServicio.crearEntrada(numeroTicket, fechaTicket, cantidadDePersonas, precioJuego, precioTotal, legajoDni, idJuego, idComprador);
            modelo.put("Exito", "La entrada se obtuvo exitosamente");

        } catch (MiException ex) {

            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Juegos> juegos = juegoServicio.listarJuegos();
            List<Compradores> compradores = compradorServicio.listarCompradores();

            modelo.addAttribute("juegos", juegos);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleado", empleados);

            modelo.put("Error", ex.getMessage());

            return "registro_entradas_form.html";

        }

        return "index.html";

    }

    //Llamamos al servicio entrada para listar las entradas
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Entradas> entradas = entradaServicio.listarEntradas();
        modelo.put("entradas", entradas);

        return "listar_entradas.html";
    }

    // Luego de pasar los datos por parametro llamamos al servicio entrada para pasar los datos al PostMapping y hacer uso del metodo modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam Integer numeroTicket, @RequestParam Date fechaTicket, @RequestParam Integer cantidadDePersonas, @RequestParam Integer precioJuego,
            @RequestParam Integer precioTotal, @RequestParam String idEmpleado, @RequestParam String idJuego, @RequestParam String idComprador, ModelMap modelo) {

        modelo.put("entradas", entradaServicio.getOne(id));

        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        List<Juegos> juegos = juegoServicio.listarJuegos();
        List<Compradores> compradores = compradorServicio.listarCompradores();

        modelo.addAttribute("juegos", juegos);
        modelo.addAttribute("compradores", compradores);
        modelo.addAttribute("empleados", empleados);

        return "entradas_modificar.html";
    }

    // Luego de pasar los datos por parametro llamamos al servicio entrada y lo utilizamos para modificar una entrada
    @PostMapping("/modificar/{id}")
    public String modificarEntrada(@PathVariable String id, @RequestParam Integer numeroTicket, @RequestParam Date fechaTicket, @RequestParam Integer cantidadDePersonas, @RequestParam Integer precioJuego,
            @RequestParam Integer precioTotal, @RequestParam Long legajoDni, @RequestParam String idJuego, @RequestParam String idComprador, ModelMap modelo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Juegos> juegos = juegoServicio.listarJuegos();
            List<Compradores> compradores = compradorServicio.listarCompradores();

            modelo.addAttribute("juegos", juegos);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleados", empleados);

            entradaServicio.crearEntrada(numeroTicket, fechaTicket, cantidadDePersonas, precioJuego, precioTotal, legajoDni, idJuego, idComprador);

            return "redirect:../listar";

        } catch (MiException ex) {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            List<Juegos> juegos = juegoServicio.listarJuegos();
            List<Compradores> compradores = compradorServicio.listarCompradores();

            modelo.addAttribute("juegos", juegos);
            modelo.addAttribute("compradores", compradores);
            modelo.addAttribute("empleados", empleados);

            modelo.put("error", ex.getMessage());

            return "entradas_modificar.html";
        }

    }

 
//Llamamos al servicio entrada con los datos del GetMapping para eliminar efectivamente una entrada 

     @GetMapping("/eliminar/{id}")
    public String eliminarEntrada(@PathVariable String id, ModelMap modelo,RedirectAttributes redirectAttributes)throws MiException {
            modelo.put("entradas", entradaServicio.getOne(id));
        try {
            entradaServicio.eliminarEntrada(id);
            modelo.put("exito", "Se elimino la entrada exitosamente");
            redirectAttributes.addFlashAttribute("success", "Entrada eliminada exitosamente");
            return "redirect:../listar";
        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("success", "La entrada no puede ser eliminada");
            return "redirect:../listar";
        }

    }
   
}
