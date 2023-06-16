
package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.UsuariosRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Service
public class UsuariosServicio implements UserDetailsService{
    //Servicio para comtrolar el AMBL de usuarios
@Autowired
    private UsuariosRepositorio usuarioRepositorio;
    
     //Creamos un metodo para crear un juego pasandole los parametros necesarios para eso 
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MiException{
         //LLamamos al metodo validar para evitar errores
        validar(nombre, email, password, password2);
        
        Usuarios usuario = new Usuarios();
        
        usuario.setNombreUsuario(nombre);
        usuario.setEmail(email);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuario.setRoles(Rol.EMP);
        
        System.out.println(usuario.toString());
         //Usamos el repositorio usuario para guardar el usuario y registrarlo correctamente
        usuarioRepositorio.save(usuario);
    
}
    
       //Creamos un metodo para modificar una entrada pasandole los parametros necesarios para eso
       @Transactional
    public void actualizar(String idUsuario, String nombre, String email, String password, String password2) throws MiException{
         //LLamamos al metodo validar para evitar errores
        validar(nombre, email, password, password2);
         //Usamos un optional para asegurarnos que el usuario este presente 
        Optional<Usuarios> respuesta = usuarioRepositorio.findById(idUsuario);
        
        if (respuesta.isPresent()){
            
             Usuarios usuario = respuesta.get();
             usuario.setNombreUsuario(nombre);
             usuario.setEmail(email);
             
             usuario.setPassword(new BCryptPasswordEncoder().encode(password));
             //Usamos el repositorio usuario para guardar el usuario y registrarlo correctamente
             usuarioRepositorio.save(usuario);
            
        }
        
}
    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    private void validar(String nombre, String email, String password, String password2) throws MiException{

if(nombre.isEmpty() || nombre == null){
    throw new MiException("El nombre no puede ser nulo o estar vacío");
}

if(email.isEmpty() || email == null){
    throw new MiException("El email no puede ser nulo o estar vacío");
}

if(password.isEmpty() || password == null || password.length() <=5){
    throw new MiException("El password no puede ser nulo, estar vacío o tener menos de 5 dígitos");
}

if(!password.equals(password2)){
    throw new MiException("Las contraseñas ingresadas deben ser iguales");
}
        
    }
//Creamos un metodo para el logeo de los usuarios
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     Usuarios usuario = usuarioRepositorio.buscarPorEmail(email);
     
     if(usuario != null){
         
         List<GrantedAuthority> permisos = new ArrayList();
         
         GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRoles().toString());
         
         permisos.add(p);
         
         ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
         
         HttpSession session = attr.getRequest().getSession(true);
         
         session.setAttribute("usuariosession", usuario);
         
         return new User(usuario.getEmail(), usuario.getPassword(), permisos);
         
     }else{
         return null;
     }
     
    }
     //Usamos el repositorio usuario para buscar uno
    public Usuarios getone(String id){
        return  usuarioRepositorio.getOne(id);
        
    }
    
      //Usamos el repositorio usuario para eliminar uno luego de buscarlo 
      public void eliminarUsuario(String id) throws MiException{

        Optional<Usuarios> respuesta = usuarioRepositorio.findById(id);
        
        if (respuesta.isPresent()) {

            Usuarios usuario = respuesta.get();
            
            usuarioRepositorio.delete(usuario);

        }
    }
        //Usamos el repositorio usuario para buscar los registros y hacer una lista
       public List<Usuarios> listarUsuarios() {

        List<Usuarios> usuario = new ArrayList();

        usuario = usuarioRepositorio.findAll();
        
        return usuario;
    }
    
    


}
