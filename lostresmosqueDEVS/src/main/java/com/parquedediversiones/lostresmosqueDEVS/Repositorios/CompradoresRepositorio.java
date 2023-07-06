package com.parquedediversiones.lostresmosqueDEVS.Repositorios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Compradores;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Los3MosqueDEVS
 */
/**/

@Repository
public interface CompradoresRepositorio extends JpaRepository<Compradores, String>{
    
        
    @Query(value = "Select * from Compradores where dni = :dni ", nativeQuery = true)
    public Compradores buscarPorDni(@Param("dni") Long dni);
    
    @Query(value = "select * from compradores where dni like :dni%", nativeQuery = true)
    public List<Compradores> DniDevuelveId(@Param("dni") Long dni);
    
    
    
}
