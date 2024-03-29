package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.UsuariosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.UsuariosServicio;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

//Rol admin le damos sus visualizaciones necesarias para aplicar su rol con los distintos metodos
@Controller
@RequestMapping("admin")
public class AdminControlador {

    @Autowired
    UsuariosServicio usuariosServicio;
     @Autowired
    EmpleadosServicio empleadosServicio;
 @Autowired
 UsuariosRepositorio usuarioRepositorio;

//Panel de vista del Administrador
    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }
    // LLamamos al servicio usuario para poder listar usuarios
    @GetMapping("/listar")
    public String listar(ModelMap modelo, @Param("keyword")Long keyword) {
        try{
        List<Usuarios> usuarios = new ArrayList<>();
        if(keyword==null){
            usuarioRepositorio.buscarPorRol().forEach(usuarios::add);
        }else{
            usuarioRepositorio.DniDevuelveId(keyword).forEach(usuarios::add);
            modelo.addAttribute("keyword", keyword);
        }
        modelo.addAttribute("usuarios", usuarios );
        }catch(Exception e){
        modelo.addAttribute("error", e.getMessage());
        }
        return "usuarios_list.html";
    }
    // LLamamos al servicio usuario para poder eliminar usuarios
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long legajoDni, ModelMap modelo) {
        modelo.put("usuario", usuariosServicio.getone(legajoDni));

        return "usuario_eliminar.html";
    }
    // LLamamos al servicio usuario para poder eliminar usuarios
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long legajoDni,  RedirectAttributes toto, ModelMap modelo) {
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuariosServicio.eliminarUsuario(legajoDni);
            
            toto.addFlashAttribute("exito", "Muy bien");

           return "redirect:../listar";
        } catch (MiException ex) {
            toto.addFlashAttribute("error", "Merd");
            return "redirect:../listar";
        }

    }
  
}
