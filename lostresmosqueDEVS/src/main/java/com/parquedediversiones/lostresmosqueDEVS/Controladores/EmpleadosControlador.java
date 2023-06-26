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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
// Controladora para los empleados y sus acciones
@Controller
@RequestMapping("/empleados")
public class EmpleadosControlador {

    @Autowired
    private JuegosServicio juegoServicio;
    @Autowired
    private EmpleadosServicio empleadoServicio;

    // Vista para registrarte como empleado 
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Juegos> juego = juegoServicio.listarJuegos();
        modelo.addAttribute("juegos", juego);
        return "empleados_form.html";

    }

    // Luego de pasar los datos por parametro llamamos al servicio empleado y lo utilizamos  para registrar un empleado
    @PostMapping("/registro")
    public String registro(@RequestParam(required = true) Long legajoDni, @RequestParam(required = true) String nombreUsuario, 
            @RequestParam (required = true) String email, @RequestParam (required = true) String password, 
            @RequestParam (required = true) String password2, @RequestParam (required = true) Integer edad, 
            @RequestParam String idJuego, ModelMap modelo, MultipartFile archivo) throws MiException {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            empleadoServicio.crearEmpleado(archivo, legajoDni, nombreUsuario, email, password, password2, edad, idJuego);
          //  modelo.put("Exito", "El empleado se registro exitosamente");
        } catch (MiException ex) {

            List<Juegos> juegos = juegoServicio.listarJuegos();
            modelo.addAttribute("juegos", juegos);

            modelo.put("Error", ex.getMessage());
            return "empleados_form.html";
        }

        return "index.html";

    }
//Llamamos al servicio empleado para listar los empleados.

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Empleados> empleados = empleadoServicio.listarEmpleados();
        modelo.put("empleados", empleados);

        return "empleados_list.html";
    }
// Luego de pasar los datos por parametro llamamos al servicio empleado para pasar los datos al PostMapping y hacer uso del metodo modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long legajoDni, @RequestParam String nombreUsuario, 
            @RequestParam String email, @RequestParam String password, @RequestParam Rol roles,
            @RequestParam Integer edad, ModelMap modelo, MultipartFile archivo) {

        modelo.put("empleados", empleadoServicio.getOne(legajoDni));

        List<Juegos> juegos = juegoServicio.listarJuegos();

        modelo.addAttribute("juegos", juegos);

        return "empleados_modificar.html";
    }
// Luego de pasar los datos por parametro llamamos al servicio empleado y lo utilizamos  para modificar un empleado
    @PostMapping("/modificar/{id}")
    public String modificarEntrada(@PathVariable Long legajoDni, @RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password,
            @RequestParam Integer edad, @RequestParam String idJuego, ModelMap modelo, MultipartFile archivo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            List<Juegos> juegos = juegoServicio.listarJuegos();

            modelo.addAttribute("juegos", juegos);

           empleadoServicio.modificarEmpleado(archivo, legajoDni, nombreUsuario, email, password, password, edad, email);

            return "redirect:../listar";

        } catch (MiException ex) {
            List<Juegos> juegos = juegoServicio.listarJuegos();

            modelo.addAttribute("juegos", juegos);
            modelo.put("error", ex.getMessage());

            return "empleados_modificar.html";
        }

    }
     // LLamamos al servicio empleado para hacer uso de su metodo buscar uno y pasamos los datos al PostMapping
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long legajoDni, ModelMap modelo) {
        modelo.put("empleados", empleadoServicio.getOne(legajoDni));

        return "empleados_list.html";
    }
    //Llamamos al servicio empleado con los datos del GetMapping para eliminar efectivamente un empleado 
    @PostMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long legajoDni, ModelMap modelo) {
         // Metodo try and catch para asegurarnos de captar errores 
        try {
            empleadoServicio.eliminarEmpleado(legajoDni);
            modelo.put("exito", "Se elimino el empleado exitosamente");

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:../listar";
        }

    }
}
