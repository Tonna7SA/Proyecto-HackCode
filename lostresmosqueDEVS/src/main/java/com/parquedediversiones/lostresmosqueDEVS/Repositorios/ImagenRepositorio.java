package com.parquedediversiones.lostresmosqueDEVS.Repositorios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {

}