package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.JuegosServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
// Controladora para los juegos y sus acciones
@Controller
@RequestMapping("/juegos")
public class JuegosControlador {

    @Autowired
    private JuegosServicio juegoServicio;
    @Autowired
    private EmpleadosServicio empleadoServicio;
 // Vista para registrar un juego 
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        modelo.addAttribute("empleados", empleados);

        return "juegos_form.html";

    }
    // Luego de pasar los datos por parametro llamamos al servicio juego y lo utilizamos  para registrar un juego
    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombreDelJuego, @RequestParam Integer capacidadMaxima, @RequestParam String tipoDeJuego,
            @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, ModelMap modelo) throws MiException {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            juegoServicio.crearJuego(archivo, nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);
            modelo.put("Exito", "El juego se registro exitosamente");

        } catch (MiException ex) {

            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            modelo.addAttribute("empleados", empleados);

            modelo.put("Error", ex.getMessage());

            return "juegos_form.html";

        }

        return "index.html";
    }
     //Llamamos al servicio juego para listar los juegos
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Juegos> juegos = juegoServicio.listarJuegos();
        modelo.put("juegos", juegos);

        return "juegos_list.html";
    }
     // Luego de pasar los datos por parametro llamamos al servicio juego para pasar los datos al PostMapping y hacer uso del metodo modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombreDelJuego, @RequestParam Integer capacidadMaxima, @RequestParam String tipoDeJuego,
            @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, @RequestParam String idEmpleado, ModelMap modelo) {

        modelo.put("juegos", juegoServicio.getOne(id));

        List<Empleados> empleados = empleadoServicio.listarEmpleados();

        modelo.addAttribute("empleados", empleados);

        return "juegos_modificar.html";
    }
     // Luego de pasar los datos por parametro llamamos al servicio juego y lo utilizamos  para modificar un juego
    @PostMapping("/modificar/{id}")
    public String modificarJuego(MultipartFile archivo, @PathVariable String id, @RequestParam String nombreDelJuego, @RequestParam Integer capacidadMaxima, @RequestParam String tipoDeJuego,
            @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, ModelMap modelo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();

            modelo.addAttribute("empleados", empleados);

            juegoServicio.crearJuego(archivo, nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);

            return "redirect:../listar";

        } catch (MiException ex) {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();

            modelo.addAttribute("empleados", empleados);
            modelo.put("Error", ex.getMessage());
            return "juegos_modificar.html";
        }

    }
     // LLamamos al servicio juego para hacer uso de su metodo buscar uno y pasamos los datos al PostMapping
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("usuario", juegoServicio.getOne(id));

        return "juegos_list.html";
    }
    //Llamamos al servicio juego con los datos del GetMapping para eliminar efectivamente un juego
    @PostMapping("/eliminar/{id}")
    public String eliminarJuego(@PathVariable String id, ModelMap modelo) {
       // Metodo try and catch para asegurarnos de captar errores 
        try {
            juegoServicio.eliminarJuego(id);

            modelo.put("Exito", "Se elimino el juego exitosamente");

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:../listar";
        }

    }
}
