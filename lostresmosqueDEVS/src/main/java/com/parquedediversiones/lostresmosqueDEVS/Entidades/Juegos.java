package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Tonna/SA FR34K
 */
/**/
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Juegos {
     //Declaramos el Id como autoGenerable y los demas atributos de la entidad
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombreDelJuego;

    @Temporal(TemporalType.DATE)
    private Date fechaDeAlta;
    private Integer capacidadMaxima;
    private String tipoDeJuego;
    private Integer cantEmpleados;
    private Integer precioDelJuego;
    //Relaciones
    @OneToMany
    private List<Empleados> empleado;

}
