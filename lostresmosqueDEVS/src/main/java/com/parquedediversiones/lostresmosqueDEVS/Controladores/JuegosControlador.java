package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.JuegosServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private JuegosRepositorio juegosRepositorio;
 
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
    public String listar(ModelMap modelo, @Param("keyword")String keyword) throws MiException{
        try{
        List<Juegos> juegos = new ArrayList<>();
        if(keyword==null){
            juegosRepositorio.findAll().forEach(juegos::add);
        }else{
            juegosRepositorio.findByNombreDelJuegoContainingIgnoreCase(keyword).forEach(juegos::add);
            modelo.addAttribute("keyword", keyword);
        }
        modelo.addAttribute("juegos", juegos);
        }catch(Exception e){
        modelo.addAttribute("error", e.getMessage());
        }
        return "juegos_list.html";
    }
     // Luego de pasar los datos por parametro llamamos al servicio juego para pasar los datos al PostMapping y hacer uso del metodo modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("juegos", juegoServicio.getOne(id));
        return "juegos_modificar.html";
    }

    // Luego de pasar los datos por parametro llamamos al servicio juego y lo utilizamos  para modificar un juego
    @PostMapping("/modificar/{id}")
    public String modificarJuego(@RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) String id, @RequestParam(required = false) String nombreDelJuego, @RequestParam Integer capacidadMaxima, 
            @RequestParam String tipoDeJuego, @RequestParam Integer cantEmpleados, @RequestParam Integer precioDelJuego, ModelMap modelo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
           
            juegoServicio.modificarJuego(archivo, id, nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);

            return "redirect:../listar";

        } catch (MiException ex) {
            
            modelo.put("Error", ex.getMessage());
            return "juegos_modificar.html";
        }

    }
      //Llamamos al servicio juego con los datos del GetMapping para eliminar efectivamente un juego
    @GetMapping("/eliminar/{id}")
    public String eliminarJuego(@PathVariable String id, ModelMap modelo, RedirectAttributes redirectAttributes)throws MiException {
       
        modelo.put("usuario", juegoServicio.getOne(id));
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            juegoServicio.eliminarJuego(id);
            
            modelo.put("Exito", "Se elimino el juego exitosamente");
            redirectAttributes.addFlashAttribute("success", "Juego eliminado exitosamente");
            return "redirect:../listar";
        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("success", "El juego no puede ser eliminado");
            
            return "redirect:../listar";
        }

    }
}
