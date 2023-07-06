package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.CompradoresRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

@Service
public class CompradoresServicio {
    //Servicio para comtrolar el AMBL de compradores
    @Autowired
    private CompradoresRepositorio compradorRepositorio;
    //Creamos un metodo para crear un comprador pasandole los parametros necesarios para eso 
    public void crearComprador(String nombreApellido, Long dni, Integer edad, String email) throws MiException {
        //LLamamos al metodo validar para evitar errores
        if(compradorRepositorio.buscarPorDni(dni)==null){
        
        validarComprador(nombreApellido, dni, email);
        Compradores comprador = new Compradores();
        
        // Cambiamos la forma de ingreso del nombre y apellido
            String nombreCompleto = nombreApellido;
            String[] palabras = nombreCompleto.split(" ");
            String nombreNuevo = "";
            for (String palabra : palabras) {
            String primeraLetra = palabra.substring(0, 1).toUpperCase();
            String restoPalabra = palabra.substring(1).toLowerCase();
            nombreNuevo += primeraLetra + restoPalabra + " ";
}
        comprador.setNombreApellido(nombreNuevo);
        comprador.setDni(dni);
        comprador.setEdad(edad);
        comprador.setFechaDeAlta(new Date());
        comprador.setActivo(true);
        email=email.toLowerCase();
        comprador.setEmail(email);
        //Usamos el repositorio comprador para guardar el comprador y registrarlo correctamente
        compradorRepositorio.save(comprador);
        }else{
            System.out.println("Ya se encuentra ese dni");
            throw new MiException("El dni ingresado ya se encuentra registrado");
            
        }
    }
    //Creamos un metodo para modificar un comprador pasandole los parametros necesarios para eso 
    public void modificarComprador(String id, String nombreApellido, Long dni, String email, Integer edad) throws MiException {
        //LLamamos al metodo validar para evitar errores
        validarComprador(nombreApellido, dni, email);
         //Usamos un optional para asegurarnos que el comprador este presente 
        Optional<Compradores> respuesta = compradorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Compradores comprador = respuesta.get();
// Cambiamos la forma de ingreso del nombre y apellido
            String nombreCompleto = nombreApellido;
            String[] palabras = nombreCompleto.split(" ");
            String nombreNuevo = "";
            for (String palabra : palabras) {
            String primeraLetra = palabra.substring(0, 1).toUpperCase();
            String restoPalabra = palabra.substring(1).toLowerCase();
            nombreNuevo += primeraLetra + restoPalabra + " ";
}
        comprador.setNombreApellido(nombreNuevo);
            comprador.setDni(dni);
            comprador.setEdad(edad);
            email=email.toLowerCase();
            comprador.setEmail(email);
             //Usamos el repositorio comprador para guardar el comprador y registrarlo correctamente
            compradorRepositorio.save(comprador);
        }
    }
    //Usamos el repositorio comprador para buscar uno
    public Compradores getOne(String id) {
        return compradorRepositorio.getOne(id);
    }
    //Usamos el repositorio comprador para buscar los registros y hacer una lista
    public List<Compradores> listarCompradores() {

        List<Compradores> comprador = new ArrayList();

        comprador = compradorRepositorio.findAll();

        return comprador;
    }
    //Usamos el repositorio comprador para eliminar uno luego de buscarlo 
    public void eliminarComprador(String id) throws MiException {
        //Optional para asegurarnos que el comprador buscado esta presente
        Optional<Compradores> respuesta = compradorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Compradores comprador = respuesta.get();

            compradorRepositorio.delete(comprador);

        }
    }
   @Transactional
    public void cambiarEstado(String id) throws Exception {
        Optional<Compradores> respuesta = compradorRepositorio.findById(id);

        if (respuesta.isPresent()) {
             Compradores comprador = respuesta.get();
                if (comprador.getActivo().equals(Boolean.TRUE)) {
                    comprador.setActivo(Boolean.FALSE);
                } else if (comprador.getActivo().equals(Boolean.FALSE)) {
                    comprador.setActivo(Boolean.TRUE);
                }
            } else {
                throw new MiException("No se pudo cambiar el estado del empleado");
            }

        }
    
    //Metodo para validar que los datos necesarios sean correctos y esten presentes
    private void validarComprador(String nombreApellido, Long dni, String email) throws MiException {

        if (nombreApellido.isEmpty() || nombreApellido.length()<4) {
            throw new MiException("Debe ingresar un nombre");
        }
        if (dni == null || dni == 0 || dni < 5000000) {
            throw new MiException("Debe ingresar un dni");
        }
        if (email.isEmpty()) {
            throw new MiException("Debe ingresar un email");
        }

        if (!(email.contains("@") && email.contains("."))) {
            throw new MiException("Debe ingresar un email valido");
        }
    }
}