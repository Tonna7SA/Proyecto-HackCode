
package com.parquedediversiones.lostresmosqueDEVS.Entidades;

import com.parquedediversiones.lostresmosqueDEVS.Enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

public class Usuarios {
     
    // Esta es una entidad madre/padre que sera utilizada en otras entidades
    //Declaramos el Id como long porque sera un atributo dado como legajo/dni y los demas atributos de la entidad
    @Id
    private Long legajoDni;
    private String nombreUsuario;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Rol roles;

    @OneToOne
    private Imagen imagen;
}
