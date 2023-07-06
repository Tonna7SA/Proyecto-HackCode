package com.parquedediversiones.lostresmosqueDEVS.Repositorios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
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
public interface UsuariosRepositorio extends JpaRepository<Usuarios, Long> {

    @Query(value = "Select * from Usuarios where email = :email ", nativeQuery = true)
    public Usuarios buscarPorEmail(@Param("email") String email);
    
    @Query(value = "select * from usuarios where roles like\"ADM\"", nativeQuery = true)
    public List<Usuarios> buscarPorRol();
    
    @Query(value = "select * from usuarios where legajo_dni like :dni% and dtype like \"usuarios\"", nativeQuery = true)
    public List<Usuarios> DniDevuelveId(@Param("dni") Long dni);

}
