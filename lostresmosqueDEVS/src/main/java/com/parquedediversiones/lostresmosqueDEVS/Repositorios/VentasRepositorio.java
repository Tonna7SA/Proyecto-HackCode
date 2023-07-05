/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parquedediversiones.lostresmosqueDEVS.Repositorios;


import com.parquedediversiones.lostresmosqueDEVS.Entidades.Ventas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pergo
 */
@Repository
public interface VentasRepositorio extends JpaRepository <Ventas, String>{
    
    @Query(value = "select * from ventas where fecha like :dni%", nativeQuery = true)
    public List<Ventas> DniDevuelveId(@Param("dni") Long dni);
    
}
