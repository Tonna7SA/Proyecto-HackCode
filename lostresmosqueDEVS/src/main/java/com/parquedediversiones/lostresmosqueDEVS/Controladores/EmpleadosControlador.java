package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Turno;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
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

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Controller
@RequestMapping("/empleados")
public class EmpleadosControlador {

    @Autowired
    private JuegosServicio juegoServicio;
    @Autowired
    private EmpleadosServicio empleadoServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Juegos> juegos = juegoServicio.listarJuegos();
        modelo.addAttribute("juegos", juegos);
        return "registro_empleados_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String id, @RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password, @RequestParam Rol roles, @RequestParam String dni,
             @RequestParam Integer edad, @RequestParam Boolean activo, @RequestParam Date fechaDeAlta,
            Turno turnos, String idJuego, ModelMap modelo) throws MiException {
        try {
            empleadoServicio.crearEmpleado(id, nombreUsuario, email, password, roles, dni, edad, activo, fechaDeAlta, turnos, idJuego);
            modelo.put("Exito", "El juego se registro exitosamente");

        } catch (MiException ex) {

            List<Juegos> juegos = juegoServicio.listarJuegos();

            modelo.addAttribute("juegos", juegos);

            modelo.put("Error", ex.getMessage());

            return "registro_empleados_form.html";
        }

        return "index.html";

    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        modelo.put("empleados", empleados);

        return "listar_empleados.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password, @RequestParam Rol roles, @RequestParam String dni,
             @RequestParam Integer edad, @RequestParam Boolean activo, @RequestParam Date fechaDeAlta,
            Turno turnos, String idJuego, ModelMap modelo) {

        modelo.put("empleados", empleadoServicio.getOne(id));

        List<Juegos> juegos = juegoServicio.listarJuegos();

            modelo.addAttribute("juegos", juegos);

        return "empleados_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarEntrada(@PathVariable String id, @RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password, @RequestParam Rol roles, @RequestParam String dni,
             @RequestParam Integer edad, @RequestParam Boolean activo, @RequestParam Date fechaDeAlta,
            Turno turnos, String idJuego, ModelMap modelo) {
        try {
            List<Juegos> juegos = juegoServicio.listarJuegos();

            modelo.addAttribute("juegos", juegos);

            empleadoServicio.modificarEmpleado(id, nombreUsuario, email, password, roles, dni, edad, activo, fechaDeAlta, turnos, idJuego);

            return "redirect:../listar";

        } catch (MiException ex) {
             List<Juegos> juegos = juegoServicio.listarJuegos();

            modelo.addAttribute("juegos", juegos);
            modelo.put("error", ex.getMessage());

            return "empleados_modificar.html";
        }

    }
     @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("empleados", empleadoServicio.getOne(id));

        return "empleados_eliminar.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable String id, ModelMap modelo) {
        try {
            empleadoServicio.eliminarEmpleado(id);
            modelo.put("exito", "Se elimino el empleado exitosamente");

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:../listar";
        }

    }
}
