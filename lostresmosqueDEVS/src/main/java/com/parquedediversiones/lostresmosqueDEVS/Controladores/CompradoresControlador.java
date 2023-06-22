package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.CompradoresServicio;
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
// Controladora para los compradores y sus acciones
@Controller
@RequestMapping("/compradores")
public class CompradoresControlador {
    // Vista para registrarte como comprador 
    @Autowired
    private CompradoresServicio compradorServicio;

    @GetMapping("/registrar")
    public String registrar() {

       
        return "comprador_form.html";

    }
    // Luego de pasar los datos por parametro llamamos al servicio comprador y lo utilizamos  para registrar el comprador
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer dni,@RequestParam Integer edad,
            @RequestParam Date fechaDeaALta, Boolean activo,@RequestParam String email, ModelMap modelo) throws MiException {
       // Metodo try and catch para asegurarnos de captar errores 
        try {
           compradorServicio.crearComprador(nombre, apellido, dni, edad, fechaDeaALta, activo, email);
            modelo.put("Exito", "El comprador se registro exitosamente");

        } catch (MiException ex) {


            modelo.put("Error", ex.getMessage());

           return "comprador_form.html";
        }

        return "index.html";

    }
    //Llamamos al servicio comprador para listar los compradores.
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Compradores> compradores = compradorServicio.listarCompradores();
        modelo.put("compradores", compradores);

        return "comprador_list.html";
    }
    // Luego de pasar los datos por parametro llamamos al servicio comprador para pasar los datos al PostMapping y hacer uso del metodo modificar
       @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer dni,@RequestParam Integer edad,
            @RequestParam Date fechaDeaALta, Boolean activo,@RequestParam String email, ModelMap modelo) {

        modelo.put("compradores", compradorServicio.getOne(id));

        return "comprador_modificar.html";
    }
    // Luego de pasar los datos por parametro llamamos al servicio comprador y lo utilizamos  para modificar un comprador
    @PostMapping("/modificar/{id}")
    public String modificarComprador(@PathVariable String id, @RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer dni,@RequestParam Integer edad,
            @RequestParam Date fechaDeaALta, Boolean activo,@RequestParam String email, ModelMap modelo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
           

            compradorServicio.modificarComprador(id, nombre, apellido, dni, edad, activo, email);

            return "redirect:../listar";

        } catch (MiException ex) {
           
            modelo.put("Error", ex.getMessage());

             return "comprador_modificar.html";
        }

    }
    // LLamamos al servicio comprador para hacer uso de su metodo buscar uno y pasamos los datos al PostMapping
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("compradores", compradorServicio.getOne(id));

        return "comprador_list.html";
    }
    //Llamamos al servicio comprador con los datos del GetMapping para eliminar efectivamente un comprador 
    @PostMapping("/eliminar/{id}")
    public String eliminarComprador(@PathVariable String id, ModelMap modelo) {
         // Metodo try and catch para asegurarnos de captar errores 
        try {
            compradorServicio.eliminarComprador(id);
            modelo.put("Exito", "Se elimino el comprador exitosamente");

            return "redirect:../listar";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:../listar";
        }

    }

}
