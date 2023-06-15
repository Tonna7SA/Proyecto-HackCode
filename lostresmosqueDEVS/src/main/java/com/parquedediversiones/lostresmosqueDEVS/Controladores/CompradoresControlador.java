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
@Controller
@RequestMapping("/compradores")
public class CompradoresControlador {

    @Autowired
    private CompradoresServicio compradorServicio;

    @GetMapping("/registrar")
    public String registrar() {

       
        return "registro_compradores_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer dni,@RequestParam Integer edad,
            @RequestParam Date fechaDeaALta, Boolean activo,@RequestParam String email, ModelMap modelo) throws MiException {
        try {
           compradorServicio.crearComprador(nombre, apellido, dni, edad, fechaDeaALta, activo, email);
            modelo.put("Exito", "El juego se registro exitosamente");

        } catch (MiException ex) {


            modelo.put("Error", ex.getMessage());

           return "registro_compradores_form.html";
        }

        return "index.html";

    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Compradores> compradores = compradorServicio.listarCompradores();
        modelo.put("compradores", compradores);

        return "listar_compradores.html";
    }
       @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer dni,@RequestParam Integer edad,
            @RequestParam Date fechaDeaALta, Boolean activo,@RequestParam String email, ModelMap modelo) {

        modelo.put("compradores", compradorServicio.getOne(id));

        return "compradores_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarComprador(@PathVariable String id, @RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer dni,@RequestParam Integer edad,
            @RequestParam Date fechaDeaALta, Boolean activo,@RequestParam String email, ModelMap modelo) {
        try {
           

            compradorServicio.modificarComprador(id, nombre, apellido, dni, edad, activo, email);

            return "redirect:../listar";

        } catch (MiException ex) {
           
            modelo.put("Error", ex.getMessage());

             return "compradores_modificar.html";
        }

    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("compradores", compradorServicio.getOne(id));

        return "compradores_eliminar.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarComprador(@PathVariable String id, ModelMap modelo) {
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
