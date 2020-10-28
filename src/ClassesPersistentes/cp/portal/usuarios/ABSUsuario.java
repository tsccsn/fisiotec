/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal.usuarios;

import java.io.Serializable;

/**
 *
 * @author Thiago-Asus
 */
public abstract class ABSUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
    //CONSTRUTORES
    //ATRIBUTOS
    private long id;
    private String nomeCompleto;
    private String primeiroNome;
    private String segundoNome;
    private String login;
    private String senha;
    private String emailPrincipal;
    private String emailSecundario;
    private boolean contaAtiva;
    private int tentativasLogin;

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setContaAtiva(boolean contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTentativasLogin(int tentativasLogin) {
        this.tentativasLogin = tentativasLogin;
    }

    //GET'S
    public long getId() {
        return id;
    }
    
    public boolean isContaAtiva() {
        return contaAtiva;
    }

    public String getEmailPrincipal() {
        return emailPrincipal;
    }

    public String getEmailSecundario() {
        return emailSecundario;
    }

    public String getLogin() {
        return login;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public String getSenha() {
        return senha;
    }

    public int getTentativasLogin() {
        return tentativasLogin;
    }
}
