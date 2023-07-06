package com.parquedediversiones.lostresmosqueDEVS.Repositorios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Juegos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

@Repository
public interface JuegosRepositorio extends JpaRepository<Juegos, String>{
    
    List<Juegos> findByNombreDelJuegoContainingIgnoreCase(String keyword);
    
}
