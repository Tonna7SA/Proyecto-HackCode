
package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Ventas {
     @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Integer totalVenta;
    private Date fechaVenta;
     
    @OneToOne
    private Empleados empleado;
    @OneToMany
    private Entradas entrada;
    
    @OneToOne
    private Compradores comprador;
    
    

}
