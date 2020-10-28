/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal.registroLogin;

import cp.portal.usuarios.ABSUsuario;
import cp.portal.usuarios.CPAdministrador;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

/**
 *
 * @author Thiago-Asus
 */
@Audited
@Entity
public class CPRegistroLoginAdministrador extends ABSREgistroLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPAdministrador getAdministrador() {
        return (CPAdministrador) super.getUsuario();
    }

    @Override
    public Timestamp getHoraDeslogado() {
        return super.getHoraDeslogado();
    }

    @Override
    public Timestamp getHoraLogado() {
        return super.getHoraLogado();
    }

    //SET'S
    @Override
    public void setHoraDeslogado(Timestamp horaDeslogado) {
        super.setHoraDeslogado(horaDeslogado);
    }

    @Override
    public void setHoraLogado(Timestamp horaLogado) {
        super.setHoraLogado(horaLogado);
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public void setAdministrador(CPAdministrador admin) {
        super.setUsuario(admin);
    }

    @Override
    public void setUsuario(ABSUsuario usuario) {
        super.setUsuario(usuario);
    }
    
    
    
    

}