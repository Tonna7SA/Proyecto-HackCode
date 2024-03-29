package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * @author Los3MosqueDEVS
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
    
    private String horaApertura;
    private String horaCierre;
    
    @OneToOne
    private Imagen imagen;
}
