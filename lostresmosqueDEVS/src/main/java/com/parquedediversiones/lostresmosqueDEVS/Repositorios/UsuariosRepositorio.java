/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parquedediversiones.lostresmosqueDEVS.Repositorios;

import com.parquedediversiones.lostresmosqueDEVS.Entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tonna/SA FR34K
 */
@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuarios, Long> {

    @Query(value = " Select * from Usuarios where email = :email ", nativeQuery = true)
    public Usuarios buscarPorEmail(@Param("email") String email);

}
