
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
 * @author Tonna/SA FR34K
 */
/**/
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Entradas {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private Integer numeroTicket;
    
    @Temporal(TemporalType.DATE)
    private Date fechaTicket;
    private Integer cantidadDePersonas;
    private Integer precioJuego;
    private Integer precioTotal;
    
    @OneToOne
    private Empleados empleado;
    @OneToOne
    private Juegos juego;
    @OneToOne
    private Compradores comprador;
    
    
    

}
