
package com.parquedediversiones.lostresmosqueDEVS.Controladores;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
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
 * @author Tonna/SA FR34K
 */
/**/
@Controller
@RequestMapping("/imagen")
public class ImagenControladora {

    @Autowired
    UsuariosServicio usuarioServicio;
    
    @GetMapping("/perfil/{legajoDni}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable Long legajoDni){
        
        Usuarios usuario = usuarioServicio.getone(legajoDni);
        
        byte [] imagen = usuario.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    
    
}
