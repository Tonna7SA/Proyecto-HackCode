package com.parquedediversiones.lostresmosqueDEVS.Repositorios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Empleados;
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
public interface EmpleadosRepositorio extends JpaRepository<Empleados, Long>{
 
    @Query(value = "select * from usuarios where roles like \"EMP\" or roles like \"SUP\";", nativeQuery = true)
    public List<Empleados> buscarPorRol();
    
    @Query(value = "select * from usuarios where legajo_dni like :dni% and dtype like \"empleados\"", nativeQuery = true)
    public List<Empleados> DniDevuelveId(@Param("dni") Long dni);
}

