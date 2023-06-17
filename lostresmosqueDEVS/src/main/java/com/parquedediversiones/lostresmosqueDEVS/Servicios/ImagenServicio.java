
package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Imagen;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Service
public class ImagenServicio {

    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    public Imagen guardar(MultipartFile archivo) throws MiException{
        
        if(archivo != null){
            try {
             
                Imagen imagen = new Imagen();
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    
    public Imagen actualizar(MultipartFile archivo, String idImagen ) throws MiException{
        
         if(archivo != null){
            try {
             
                Imagen imagen = new Imagen();
                
                if(idImagen != null){
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    
                    if(respuesta.isPresent()){
                        imagen = respuesta.get();
                    }
                    
                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
        
        
    }
    
    
    
    
}

