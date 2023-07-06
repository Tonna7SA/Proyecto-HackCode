package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.EmpleadosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.JuegosServicio;
import com.parquedediversiones.lostresmosqueDEVS.Servicios.UsuariosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

@Controller
@RequestMapping("/imagen")
public class ImagenControladora {

    @Autowired
    UsuariosServicio usuarioServicio;
    @Autowired
    EmpleadosServicio empleadoServicio;
    @Autowired
    JuegosServicio juegosServicio;
    
    @GetMapping("/{legajoDni}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable("legajoDni") Long legajoDni){
        
        Usuarios usuario = usuarioServicio.getone(legajoDni);
        
        byte [] imagen = usuario.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/empleados/{legajoDni}")
    
    public ResponseEntity<byte[]> imagenEmpleado (@PathVariable("legajoDni") Long legajoDni){
        
        Empleados empleado = empleadoServicio.getOne(legajoDni);
        
        byte [] imagen = empleado.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/juegos/{id}")
    
    public ResponseEntity<byte[]> imagenJuego (@PathVariable("id") String id){
        
        Juegos juego = juegosServicio.getOne(id);
        
        byte [] imagen = juego.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    
    
    
}
