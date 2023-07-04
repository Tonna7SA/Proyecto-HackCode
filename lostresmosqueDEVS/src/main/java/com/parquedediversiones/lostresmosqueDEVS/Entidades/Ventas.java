
package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import java.util.Date;
import java.util.List;
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
     //Declaramos el Id como autoGenerable y los demas atributos de la entidad
     @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idVenta;
     private String nombreJuego;
     private String tipoJuego;
     private Integer precioJuego;
     private String nombreComprador;
     private String dniComprador;
     private String emailComprador;
     private String nombreVendedor;
     private Long legajoVendedor;
    private Integer totalVenta;
    private Date fechaVenta;
   
    //Relaciones
    @OneToOne
    private Empleados empleado;
    
    @OneToOne
    private Compradores comprador;
    
    

}
