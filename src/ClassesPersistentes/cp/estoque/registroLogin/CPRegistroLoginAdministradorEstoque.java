/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.estoque.registroLogin;

import cp.estoque.CPAdministradorEstoque;
import cp.portal.registroLogin.ABSREgistroLogin;
import cp.portal.usuarios.ABSUsuario;
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
public class CPRegistroLoginAdministradorEstoque extends ABSREgistroLogin {

    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public Timestamp getHoraDeslogado() {
        return super.getHoraDeslogado();
    }

    @Override
    public Timestamp getHoraLogado() {
        return super.getHoraLogado();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPAdministradorEstoque getUsuario() {
        return (CPAdministradorEstoque) super.getUsuario();
    }

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

    @Override
    public void setUsuario(ABSUsuario usuario) {
        super.setUsuario(usuario);
    }
}
