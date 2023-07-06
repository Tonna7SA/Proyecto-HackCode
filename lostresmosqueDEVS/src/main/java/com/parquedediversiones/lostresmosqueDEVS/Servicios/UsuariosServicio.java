package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Imagen;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

@Service
public class UsuariosServicio implements UserDetailsService {
    //Servicio para comtrolar el AMBL de usuarios

    @Autowired
    private UsuariosRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Creamos un metodo para crear un juego pasandole los parametros necesarios para eso 
    @Transactional
    public void registrar(Long legajoDni, String nombreUsuario, String email, String password, String password2, MultipartFile archivo) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validar(legajoDni, nombreUsuario, email, password, password2);

        Usuarios usuario = new Usuarios();

        usuario.setLegajoDni(legajoDni);
        // Cambiamos la forma de ingreso del nombre del Usuario
        String nombreCompleto = nombreUsuario;
        String[] palabras = nombreCompleto.split(" ");
        String nombreNuevo = "";
        for (String palabra : palabras) {
            String primeraLetra = palabra.substring(0, 1).toUpperCase();
            String restoPalabra = palabra.substring(1).toLowerCase();
            nombreNuevo += primeraLetra + restoPalabra + " ";
        }
        usuario.setNombreUsuario(nombreNuevo);

        email = email.toLowerCase();
        usuario.setEmail(email);

        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRoles(Rol.ADM);

        //Usamos el repositorio usuario para guardar el usuario y registrarlo correctamente
        usuarioRepositorio.save(usuario);

    }

    //Creamos un metodo para modificar una entrada pasandole los parametros necesarios para eso
    @Transactional
    public void actualizar(Long legajoDni, String nombreUsuario, String email) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarDatos(legajoDni, nombreUsuario, email);
        //Usamos un optional para asegurarnos que el usuario este presente 
        Optional<Usuarios> respuesta = usuarioRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {

            Usuarios usuario = respuesta.get();
            // Cambiamos la forma de ingreso del nombre del Usuario
            String nombreCompleto = nombreUsuario;
            String[] palabras = nombreCompleto.split(" ");
            String nombreNuevo = "";
            for (String palabra : palabras) {
                String primeraLetra = palabra.substring(0, 1).toUpperCase();
                String restoPalabra = palabra.substring(1).toLowerCase();
                nombreNuevo += primeraLetra + restoPalabra + " ";
            }
            usuario.setNombreUsuario(nombreNuevo);

            email = email.toLowerCase();
            usuario.setEmail(email);

//            String idImagen = null;
//            if (usuario.getImagen() != null) {
//                idImagen = usuario.getImagen().getId();
//            }
//            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
//            usuario.setImagen(imagen);
//            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            //Usamos el repositorio usuario para guardar el usuario y registrarlo correctamente
            usuarioRepositorio.save(usuario);

        }

    }

    @Transactional
    public void actualizarfoto(Long legajoDni, MultipartFile archivo) throws MiException {

        Optional<Usuarios> respuesta = usuarioRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {

            Usuarios usuario = respuesta.get();

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);
//            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            //Usamos el repositorio usuario para guardar el usuario y registrarlo correctamente
            usuarioRepositorio.save(usuario);

        }

    }
    //Metodo para validar que los datos necesarios sean correctos y esten presentes

    private void validar(Long legajoDni, String nombreUsuario, String email, String password, String password2) throws MiException {

        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("El password no puede ser nulo, estar vacío o tener menos de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (legajoDni == null) {
            throw new MiException("el legajo ingresadas deben ser iguales");
        }

    }

    private void validarDatos(Long legajoDni, String nombreUsuario, String email) throws MiException {

        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }
        if (legajoDni == null) {
            throw new MiException("el legajo ingresadas deben ser iguales");
        }

    }
//Creamos un metodo para el logeo de los usuarios

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRoles().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }

    }

    @Transactional
    public void cambiarClave(String claveActual, Long legajoDni, String clave, String clave2) throws MiException {

        Optional<Usuarios> respuesta = usuarioRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {

            if (clave.isEmpty() || clave == null || clave.length() <= 5) {
                throw new MiException("La contraseña no puede ser vacía y debe contener más de 5 caracteres.");
            }

            if (!clave.equals(clave)) {
                throw new MiException("Las contraseñás no coinciden.");
            }

            Usuarios usuario = respuesta.get();

            String encodedPassword = usuario.getPassword();

            if (bCryptPasswordEncoder.matches(claveActual, encodedPassword)) {
                usuario.setPassword(new BCryptPasswordEncoder().encode(clave));

                usuarioRepositorio.save(usuario);
            } else {
                throw new MiException("Las contraseñás actual no es correcta.");
            }

        }

    }
    //Usamos el repositorio usuario para buscar uno

    public Usuarios getone(Long legajoDni) {
        return usuarioRepositorio.getOne(legajoDni);

    }

    //Usamos el repositorio usuario para eliminar uno luego de buscarlo 
    public void eliminarUsuario(Long legajoDni) throws MiException {

        Optional<Usuarios> respuesta = usuarioRepositorio.findById(legajoDni);

        if (respuesta.isPresent()) {

            Usuarios usuario = respuesta.get();

            usuarioRepositorio.delete(usuario);

        }
    }
    //Usamos el repositorio usuario para buscar los registros y hacer una lista

    public List<Usuarios> listarUsuarios() {

        List<Usuarios> usuario = new ArrayList();

        usuario = usuarioRepositorio.buscarPorRol();

        return usuario;
    }

}
