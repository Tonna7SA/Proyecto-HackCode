
package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)

public class Usuarios {
     //Declaramos el Id como autoGenerable y los demas atributos de la entidad
    // Esta es una entidad madre/padre que sera utilizada en otras entidades
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    
    private String id;
    private String nombreUsuario;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Rol roles;

}
