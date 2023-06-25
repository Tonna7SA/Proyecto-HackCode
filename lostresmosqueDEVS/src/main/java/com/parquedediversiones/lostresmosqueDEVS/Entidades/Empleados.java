package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Turno;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)

public class Empleados extends Usuarios {
     //Declaramos atributos de la entidad el id sera heredado de usuarios asi como los demas atributos 
    
    private Integer edad;
    private Boolean activo;

    @Temporal(TemporalType.DATE)
    private Date fechaDeAlta;

    @Enumerated(EnumType.STRING)
    private Turno turnos;
  
    //Relaciones
    @OneToOne
    private Juegos juego;
}