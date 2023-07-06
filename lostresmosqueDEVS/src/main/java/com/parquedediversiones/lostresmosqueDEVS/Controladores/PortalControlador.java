package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.UsuariosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.UsuariosServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

// Controladora para el portal y sus acciones
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuariosServicio usuarioServicio;
    
    @Autowired
    private UsuariosRepositorio usuarioRepositorio;

    @GetMapping("/")
    public String bienvenidos() {
        
        return "bienvenidos.html";
    }
    
    @GetMapping("/index")
    public String index() {

        return "index.html";
    }
    
    
    
    //llamamos al metodo registrar
    @GetMapping("/registrar")
    public String registrar() {
        return "usuarios_form.html";
    }

    // Luego de pasar los datos por parametro utilizamos el servicio usuario para registrar uno
    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam Long legajoDni, @RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password, String password2, ModelMap modelo, RedirectAttributes redirectAttributes) {
        // Metodo try and catch para asegurarnos de captar errores 

        // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuarioServicio.registrar(legajoDni, nombreUsuario, email, password, password2, archivo);

            redirectAttributes.addFlashAttribute("exito", "El usuario fue cargado correctamente!");

            return "index.html";
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);
            return "usuarios_form.html";
        }

    }

//       User user = userRepository.findByUsername(username);
//        
//        if (user != null && user.getPassword().equals(password)) {
//            // Verificar si es el primer inicio de sesión
//            if (user.getFirstLogin() == null) {
//                // Es el primer inicio de sesión, actualizar la fecha y hora actual
//                user.setFirstLogin(LocalDateTime.now());
//                userRepository.save(user);
//            }
//            
//            // Resto de la lógica de inicio de sesión...
//        }
//      User user = userRepository.findByUsername(username);
//        
//        if (user != null && user.getPassword().equals(password)) {
//            // Verificar si es el primer inicio de sesión
//            if (user.getLoginCount() == 0) {
//                // Es el primer inicio de sesión, incrementar el contador y guardar
//                user.setLoginCount(1);
//                userRepository.save(user);
//            }
//            else {
//                // No es el primer inicio de sesión, simplemente incrementar el contador
//                user.setLoginCount(user.getLoginCount() + 1);
//                userRepository.save(user);
//            }
    //Llamamos al login para poder ingresar como un usuario
   
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos");
        }

        return "login.html";
    }

    //Llevamos al usuario al inicio correspondiente en caso de ser admin o empleado
    
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuarios logueado = (Usuarios) session.getAttribute("usuariosession");

        if (logueado.getRoles().toString().equals("ADM")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }
  //Llamamos al servicio usuario para listar
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
    

//llevamos al usuario a la vista de modificacion en caso de ser admin o empleado
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuarios usuarios = (Usuarios) session.getAttribute("usuariosession");
        modelo.put("usuarios", usuarios);

        return "usuarios_modificar.html";
    }
    


    //Llevamos al usuario con los datos y lo traido del GetMapping a realizar la modificacion del get en caso de ser admin o empleado y tener la autorizacion

    @PostMapping("/perfil")
    public String modificarUsuario (@RequestParam Long legajoDni, @RequestParam (required = false) String nombreUsuario,
            @RequestParam String email, ModelMap modelo, RedirectAttributes redirectAttributes) {

        // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuarioServicio.actualizar(legajoDni, nombreUsuario, email);

            redirectAttributes.addFlashAttribute("exito", "El usuario fue actualizado correctamente!");

            return "index.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);

            return "usuarios_modificar.html";
        }
    }
    
    @GetMapping("/foto")
    public String foto(ModelMap modelo, HttpSession session) {

        Usuarios usuarios = (Usuarios) session.getAttribute("usuariosession");
        modelo.put("usuarios", usuarios);

        return "foto_modificar.html";
    }
    


    //Llevamos al usuario con los datos y lo traido del GetMapping a realizar la modificacion del get en caso de ser admin o empleado y tener la autorizacion

    @PostMapping("/foto")
    public String modificarfoto (MultipartFile archivo, @RequestParam Long legajoDni, ModelMap modelo, RedirectAttributes redirectAttributes) {

        // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuarioServicio.actualizarfoto(legajoDni, archivo);

            redirectAttributes.addFlashAttribute("exito", "El usuario fue actualizado correctamente!");

            return "redirect:/";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "foto_modificar.html";
        }
    }

    @GetMapping("/password")
    public String pass(ModelMap modelo,HttpSession session) {

        Usuarios usuarios = (Usuarios) session.getAttribute("usuariosession");
        modelo.put("usuarios", usuarios);

        return "pass_modificar.html";
    }
    
    @PostMapping("/password/clave")
    public String modificarpassword (@RequestParam String claveActual, @RequestParam Long legajoDni, @RequestParam String clave,
            @RequestParam String clave2, ModelMap model) {
        try {
            usuarioServicio.cambiarClave(claveActual, legajoDni, clave, clave2);
            model.put("exito", "La contraseña ha sido actualizada correctamente.");
            return "pass_modificar.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "pass_modificar.html";
        }
    }
}
    

