package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.CompradoresRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.CompradoresServicio;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private CompradoresRepositorio compradorRepositorio;

    @GetMapping("/registrar")
    public String registrar() {

        return "comprador_form.html";

    }

    // Luego de pasar los datos por parametro llamamos al servicio comprador y lo utilizamos  para registrar el comprador
    @PostMapping("/registro")
    public String registro(@RequestParam String nombreApellido, @RequestParam Long dni, @RequestParam Integer edad,
            @RequestParam String email, ModelMap modelo) throws MiException {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            compradorServicio.crearComprador(nombreApellido, dni, edad, email);
            //modelo.put("Exito", "El comprador se registro exitosamente");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());

            return "comprador_form.html";
        }

        return "index.html";

    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo, @Param("keyword")Long keyword) {
        try{
        List<Compradores> compradores = new ArrayList<>();
        if(keyword==null){
            compradorRepositorio.findAll().forEach(compradores::add);
        }else{
            compradorRepositorio.DniDevuelveId(keyword).forEach(compradores::add);
            modelo.addAttribute("keyword", keyword);
        }
        modelo.addAttribute("compradores",compradores );
        }catch(Exception e){
        modelo.addAttribute("error", e.getMessage());
        }
        return "comprador_list.html";
    }

    // Luego de pasar los datos por parametro llamamos al servicio comprador para pasar los datos al PostMapping y hacer uso del metodo modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

       modelo.put("compradores", compradorServicio.getOne(id));

        return "comprador_modificar.html";
    }
        @GetMapping("/modificarEstado/{id}")
    public String cambiarEstado(@PathVariable String id, RedirectAttributes redirectAttributes) throws Exception {
        try {
            compradorServicio.cambiarEstado(id);
            redirectAttributes.addFlashAttribute("success", "El Comprador con Id=" + id + " ha sido modificado correctamente!");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:../listar";
    }
    // Luego de pasar los datos por parametro llamamos al servicio comprador y lo utilizamos  para modificar un comprador
    @PostMapping("/modificar/{id}")
    public String modificarComprador(@PathVariable String id, @RequestParam String nombreApellido, @RequestParam Long dni,
           @RequestParam String email, @RequestParam Integer edad, ModelMap modelo) throws MiException{
        // Metodo try and catch para asegurarnos de captar errores 
        try {

            compradorServicio.modificarComprador(id, nombreApellido, dni, email, edad);
            return "redirect:../listar";

        } catch (MiException ex) {
            System.out.println("prueba");
            modelo.put("Error", ex.getMessage());
            
            return "comprador_modificar.html";
        }

    }
    
    // LLamamos al servicio comprador para hacer uso de su metodo buscar uno y pasamos los datos al PostMapping
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo, RedirectAttributes redirectAttributes) throws MiException {
        modelo.put("compradores", compradorServicio.getOne(id));
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            compradorServicio.eliminarComprador(id);
            modelo.put("Exito", "Se elimino el comprador exitosamente");
            redirectAttributes.addFlashAttribute("success", "Comprador eliminado exitosamente");
            return "redirect:../listar";
        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("success", "El comprador no puede ser eliminado");
            return "redirect:../listar";
        }

    }

}
