/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal.usuarios;

import cp.CPPontoDeEstagio;
import java.io.Serializable;
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
public class CPAluno extends ABSUsuario implements Serializable {

    public CPAluno(String login, String senha, String emailPrincipal) {
        super.setLogin(login);
        super.setSenha(senha);
        super.setEmailPrincipal(emailPrincipal);
    }

    public CPAluno() {
    }
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public String getEmailPrincipal() {
        return super.getEmailPrincipal();
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
    public boolean isContaAtiva() {
        return super.isContaAtiva();
    }

    @Override
    public int getTentativasLogin() {
        return super.getTentativasLogin();
    }

    @Override
    public String getEmailSecundario() {
        return super.getEmailSecundario();
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
    
    
    
    
    
    
    
    
   
    
}
