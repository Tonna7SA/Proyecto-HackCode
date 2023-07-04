package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Imagen;
import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.EmpleadosRepositorio;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.JuegosRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class JuegosServicio {
    //Servicio para comtrolar el AMBL de juegos
    @Autowired
    private JuegosRepositorio juegosRepositorio;
    @Autowired
    private EmpleadosRepositorio empleadoRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    //Creamos un metodo para crear un juego pasandole los parametros necesarios para eso 
    public void crearJuego(MultipartFile archivo, String nombreDelJuego, Integer capacidadMaxima, 
            String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego) throws MiException {
        //LLamamos al metodo validar para evitar errores
       validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);
        //Usamos el empleado repositorio para buscar que haya uno presente por la relacion entre estos
        Juegos juego = new Juegos();
        
        juego.setNombreDelJuego(nombreDelJuego);
        juego.setFechaDeAlta(new Date());
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setHoraApertura("12:00");
        juego.setHoraCierre("19:00");
        juego.setPrecioDelJuego(precioDelJuego);
        Imagen imagen = imagenServicio.guardar(archivo);
        juego.setImagen(imagen);
        
         //Usamos el repositorio juego para guardar el juego y registrarlo correctamente
        juegosRepositorio.save(juego);


    }
     //Creamos un metodo para modificar un juego pasandole los parametros necesarios para eso 
    @Transactional
    public void modificarJuego(MultipartFile archivo, String id, String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarJuego(nombreDelJuego, capacidadMaxima, tipoDeJuego, cantEmpleados, precioDelJuego);
         //Usamos un optional para asegurarnos que el juego este presente 
        Optional<Juegos> respuestaJuego = juegosRepositorio.findById(id);
        
        if (respuestaJuego.isPresent()) {
            Juegos juego = respuestaJuego.get();

        juego.setNombreDelJuego(nombreDelJuego);
        juego.setCapacidadMaxima(capacidadMaxima);
        juego.setTipoDeJuego(tipoDeJuego);
        juego.setCantEmpleados(cantEmpleados);
        juego.setPrecioDelJuego(precioDelJuego);

        String idImagen = null;
             if(juego.getImagen() !=null){
                   idImagen = juego.getImagen().getId();
                    Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                    juego.setImagen(imagen);
             }else{
                 
             }
             

             juegosRepositorio.save(juego);

        }
    }
    //Usamos el repositorio juego para buscar uno
    public Juegos getOne(String id) {
        return juegosRepositorio.getOne(id);
    }
    
     //Usamos el repositorio juego para buscar los registros y hacer una lista
    public List<Juegos> listarJuegos() {

        List<Juegos> juegos = new ArrayList();

        juegos = juegosRepositorio.findAll();

        return juegos;
    }
    //Usamos el repositorio juego para eliminar uno luego de buscarlo 
    public void eliminarJuego(String id) throws MiException {

        Optional<Juegos> respuesta = juegosRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Juegos juego = respuesta.get();

            juegosRepositorio.delete(juego);

        }
    }
    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    private void validarJuego(String nombreDelJuego, Integer capacidadMaxima, String tipoDeJuego, Integer cantEmpleados, Integer precioDelJuego) throws MiException {

        if (nombreDelJuego.isEmpty() || nombreDelJuego == null || nombreDelJuego.length() < 3) {
            throw new MiException("Debe ingresar un nombre");
        }
        if (capacidadMaxima == null || capacidadMaxima < 10) {
            throw new MiException("Debe ingresar una capacidad");
        }
        if (tipoDeJuego.isEmpty() || tipoDeJuego == null ) {
            throw new MiException("Debe ingresar el tipo de Juego");
        }
        if (cantEmpleados == null || cantEmpleados < 1) {
            throw new MiException("Debe ingresar la cantidad de empleados");
        }

        if (precioDelJuego == null || precioDelJuego < 1) {
            throw new MiException("Debe ingresar un email valido");
        }
    }
}
