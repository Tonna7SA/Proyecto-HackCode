package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.UsuariosServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
// Controladora para el portal y sus acciones
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuariosServicio usuarioServicio;
   //Llevamos la vista al index
    @GetMapping("/")
    public String index() {

        return "index.html";
    }
    //llamamos al metodo registrar
    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }
    // Luego de pasar los datos por parametro utilizamos el servicio usuario para registrar uno
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, String password2, ModelMap modelo, RedirectAttributes redirectAttributes) {
       
        // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuarioServicio.registrar(nombre, email, password, password2);

            redirectAttributes.addFlashAttribute("exito", "El usuario fue cargado correctamente!");
            
            return "redirect:/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
           
            return "registro.html";
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
    @PreAuthorize("hasAnyRole('ROLE_EMP', 'ROLE_ADM')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuarios logueado = (Usuarios) session.getAttribute("usuariosession");

        if (logueado.getRoles().toString().equals("ADM")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }
    //llevamos al usuario a la vista de modificacion en caso de ser admin o empleado
    @PreAuthorize("hasAnyRole('ROLE_EMP', 'ROLE_ADM')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuarios usuario = (Usuarios) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "usuario_modificar.html";
    }
    //Llevamos al usuario con los datos y lo traido del GetMapping a realizar la modificacion del get en caso de ser admin o empleado y tener la autorizacion
    @PreAuthorize("hasAnyRole('ROLE_EMP', 'ROLE_ADM')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo, RedirectAttributes redirectAttributes) {

       // Metodo try and catch para asegurarnos de captar errores 
        try {
            usuarioServicio.actualizar(id, nombre, email, password, password2);

            redirectAttributes.addFlashAttribute("exito", "El usuario fue actualizado correctamente!");

            return "redirect:/";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";
        }
    }
    //Llamamos al servicio usuario para listar
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Usuarios> usuarios = usuarioServicio.listarUsuarios();
        modelo.put("usuarios", usuarios);

        return "usuario_list.html";
    }

}
