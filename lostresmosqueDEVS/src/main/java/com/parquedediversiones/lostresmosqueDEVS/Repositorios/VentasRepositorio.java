/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parquedediversiones.lostresmosqueDEVS.Repositorios;


import com.parquedediversiones.lostresmosqueDEVS.Entidades.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Pergo
 */
@Repository
public interface VentasRepositorio extends JpaRepository <Ventas, String>{
    
}
