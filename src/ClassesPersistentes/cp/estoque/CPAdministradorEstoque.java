/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.estoque;

import cp.portal.usuarios.ABSUsuario;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

/**
 *
 * @author Thiago-Asus
 */
@Audited
@Entity
public class CPAdministradorEstoque extends ABSUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public CPAdministradorEstoque() {
    }
    
    private boolean privilegios;

    public boolean isPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(boolean privilegios) {
        this.privilegios = privilegios;
    }

    @Override
    public boolean isContaAtiva() {
        return super.isContaAtiva();
    }

    public CPAdministradorEstoque(String login, String senha, String emailPrincipal) {
        super.setEmailPrincipal(emailPrincipal);
        super.setLogin(login);
        super.setSenha(senha);
    }

    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setContaAtiva(boolean contaAtiva) {
        super.setContaAtiva(contaAtiva);
    }

    @Override
    public void setEmailPrincipal(String emailPrincipal) {
        super.setEmailPrincipal(emailPrincipal);
    }

    @Override
    public void setEmailSecundario(String emailSecundario) {
        super.setEmailSecundario(emailSecundario);
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public void setLogin(String login) {
        super.setLogin(login);
    }

    @Override
    public void setNomeCompleto(String nomeCompleto) {
        super.setNomeCompleto(nomeCompleto);
    }

    @Override
    public void setPrimeiroNome(String primeiroNome) {
        super.setPrimeiroNome(primeiroNome);
    }

    @Override
    public void setSegundoNome(String segundoNome) {
        super.setSegundoNome(segundoNome);
    }

    @Override
    public void setSenha(String senha) {
        super.setSenha(senha);
    }

    @Override
    public void setTentativasLogin(int tentativasLogin) {
        super.setTentativasLogin(tentativasLogin);
    }

    @Override
    public String getEmailPrincipal() {
        return super.getEmailPrincipal();
    }

    @Override
    public String getEmailSecundario() {
        return super.getEmailSecundario();
    }

    @Override
    public String getLogin() {
        return super.getLogin();
    }

    @Override
    public String getNomeCompleto() {
        return super.getNomeCompleto();
    }

    @Override
    public String getPrimeiroNome() {
        return super.getPrimeiroNome();
    }

    @Override
    public String getSegundoNome() {
        return super.getSegundoNome();
    }

    @Override
    public String getSenha() {
        return super.getSenha();
    }

    @Override
    public int getTentativasLogin() {
        return super.getTentativasLogin();
    }
}
