package com.parquedediversiones.lostresmosqueDEVS.Servicios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import com.parquedediversiones.lostresmosqueDEVS.Excepciones.MiException;
import com.parquedediversiones.lostresmosqueDEVS.Repositorios.CompradoresRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Service
public class CompradoresServicio {

    @Autowired
    private CompradoresRepositorio compradorRepositorio;

    public void crearComprador(String nombre, String apellido, Integer dni, Integer edad, Date fechaDeaALta, Boolean activo, String email) throws MiException {

        validarComprador(nombre, apellido, dni, email);

        Compradores comprador = new Compradores();

        comprador.setNombre(nombre);
        comprador.setApellido(apellido);
        comprador.setDni(dni);
        comprador.setEdad(edad);
        comprador.setFechaDeAlta(new Date());
        comprador.setActivo(true);
        comprador.setEmail(email);

        compradorRepositorio.save(comprador);

    }

    public void modificarComprador(String id, String nombre, String apellido, Integer dni, Integer edad, Boolean activo, String email) throws MiException {

        validarComprador(email, apellido, dni, email);

        Optional<Compradores> respuesta = compradorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Compradores comprador = respuesta.get();

            comprador.setNombre(nombre);
            comprador.setApellido(apellido);
            comprador.setDni(dni);
            comprador.setEdad(edad);
            comprador.setActivo(true);
            comprador.setEmail(email);

            compradorRepositorio.save(comprador);
        }
    }

    public Compradores getOne(String id) {
        return compradorRepositorio.getOne(id);
    }

    public List<Compradores> listarCompradores() {

        List<Compradores> comprador = new ArrayList();

        comprador = compradorRepositorio.findAll();

        return comprador;
    }

    public void eliminarComprador(String id) throws MiException {

        Optional<Compradores> respuesta = compradorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Compradores comprador = respuesta.get();

            compradorRepositorio.delete(comprador);

        }
    }

    private void validarComprador(String nombre, String apellido, Integer dni, String email) throws MiException {

        if (nombre.isEmpty() || nombre == null || nombre.length() < 3) {
            throw new MiException("Debe ingresar un nombre");
        }
        if (apellido.isEmpty() || apellido == null || apellido.length() < 3) {
            throw new MiException("Debe ingresar un apellido");
        }
        if (nombre.isEmpty() || nombre == null || dni < 6) {
            throw new MiException("Debe ingresar un dni");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("Debe ingresar un email");
        }

        if (email.contains("@") && email.contains(".")) {
            throw new MiException("Debe ingresar un email valido");
        }
    }
}
