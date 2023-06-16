
package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.UsuariosServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
//Rol admin le damos sus visualizaciones necesarias para aplicar su rol con los distintos metodos
@Controller
@RequestMapping("admin")
public class AdminControlador {

    @Autowired
    UsuariosServicio usuariosServicio;
    //Panel de vista del Administrador
    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }
    // LLamamos al servicio usuario para poder listar usuarios
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Usuarios> usuarios = usuariosServicio.listarUsuarios();
        modelo.put("usuarios", usuarios);

        return "listar_usuario.html";
    }
    // LLamamos al servicio usuario para poder eliminar usuarios
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("usuario", usuariosServicio.getone(id));

        return "usuario_eliminar.html";
    }
    // LLamamos al servicio usuario para poder eliminar usuarios
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id,  RedirectAttributes toto, ModelMap modelo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuariosServicio.eliminarUsuario(id);
            
            toto.addFlashAttribute("exito", "Muy bien");

           return "redirect:../listar";
        } catch (MiException ex) {
            toto.addFlashAttribute("error", "Merd");
            return "redirect:../listar";
        }

    }
}
