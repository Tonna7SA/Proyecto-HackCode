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

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Controller
@RequestMapping("/juegos")
public class JuegosControlador {

    @Autowired
    private JuegosServicio juegoServicio;
    @Autowired
    private EmpleadosServicio empleadoServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        modelo.addAttribute("empleados", empleados);

        return "registro_juegos_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreDelJuego, @RequestParam Integer capacidadMaxima, @RequestParam String tipoDeJuego,
            @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, @RequestParam String idEmpleado, ModelMap modelo) throws MiException {
        try {
            juegoServicio.crearJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego, idEmpleado);
            modelo.put("Exito", "El juego se registro exitosamente");

        } catch (MiException ex) {

            List<Empleados> empleados = empleadoServicio.listarEmpleados();
            modelo.addAttribute("empleados", empleados);

            modelo.put("Error", ex.getMessage());

            return "registro_juegos_form.html";

        }

        return "index.html";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Juegos> juegos = juegoServicio.listarJuegos();
        modelo.put("juegos", juegos);

        return "listar_juegos.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombreDelJuego, @RequestParam Integer capacidadMaxima, @RequestParam String tipoDeJuego,
            @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, @RequestParam String idEmpleado, ModelMap modelo) {

        modelo.put("juegos", juegoServicio.getOne(id));

        List<Empleados> empleados = empleadoServicio.listarEmpleados();

        modelo.addAttribute("empleados", empleados);

        return "juegos_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarJuego(@PathVariable String id, @RequestParam String nombreDelJuego, @RequestParam Integer capacidadMaxima, @RequestParam String tipoDeJuego,
            @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, @RequestParam String idEmpleado, ModelMap modelo) {
        try {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();

            modelo.addAttribute("empleados", empleados);

            juegoServicio.crearJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego, idEmpleado);

            return "redirect:../listar";

        } catch (MiException ex) {
            List<Empleados> empleados = empleadoServicio.listarEmpleados();

            modelo.addAttribute("empleados", empleados);
            modelo.put("Error", ex.getMessage());
            return "juegos_modificar.html";
        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("usuario", juegoServicio.getOne(id));

        return "juego_eliminar.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarJuego(@PathVariable String id, ModelMap modelo) {
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
