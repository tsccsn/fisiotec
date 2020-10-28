/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal.registroLogin;

import cp.portal.usuarios.ABSUsuario;
import cp.portal.usuarios.CPProfessor;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Thiago-Asus
 */
@Entity
public class CPRegistroLoginProfessor extends ABSREgistroLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPProfessor getProfessor() {
        return (CPProfessor) super.getUsuario();
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

    public void setProfessor(CPProfessor prof) {
        super.setUsuario(prof);
    }


}