package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Turno;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private String dni;
    private Integer edad;
    private Boolean activo;

    @Temporal(TemporalType.DATE)
    private Date fechaDeAlta;

    @Enumerated(EnumType.STRING)
    private Turno turnos;

}
