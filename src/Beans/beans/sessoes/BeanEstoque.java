/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.sessoes;

import beans.listas.BeanListUnidades;
import cp.estoque.CPAdministradorEstoque;
import cp.estoque.registroLogin.CPRegistroLoginAdministradorEstoque;
import dao.estoque.DaoUnidadeDeMedida;
import dao.estoque.login.DaoAdministradorEstoque;
import dao.estoque.login.DaoRegistroLoginAdministradorEstoque;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import utilidades.Boleanos;
import utilidades.data.UtilData;
import utilidades.hibernate.SessionUtil;
import utilidades.mensagens.UtilMensagens;
import utilidades.seguranca.Url;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "beanAdiminEstoque")
@SessionScoped
public class BeanEstoque extends Thread implements Serializable {

    private CPAdministradorEstoque administrador = new CPAdministradorEstoque();
    private CPAdministradorEstoque administradorManipulado = new CPAdministradorEstoque();
    private List<CPAdministradorEstoque> administradores = DaoAdministradorEstoque.getAll();
    private static final long serialVersionUID = 1L;
    private CPRegistroLoginAdministradorEstoque registroLogin;
    private boolean emailPrincipal = true; // 0 - principal | 1 - secundario
    private String codigoSecreto;
    private String emailOuLogin;
    private String confirmaCodigoSecreto;
    private String novaSenha;
    private String confirmacaoNovaSenha = "";
    private String senhaAntiga;
    private boolean fazendoBobagem = false;
    private CPAdministradorEstoque novoUsuario = new CPAdministradorEstoque();
    private String mensagemDaBobagem = "";

    
    private String login;
    private String senha;
    
    public void fazendoBobagem() {
        if (fazendoBobagem) {
            UtilMensagens.alerta(mensagemDaBobagem);
            fazendoBobagem = false;
        }

    }

    public void desbloqueiaConta() {
        administradorManipulado.setContaAtiva(true);
        DaoAdministradorEstoque.merge(administradorManipulado);
        UtilMensagens.ok("Conta do(a) " + administradorManipulado.getPrimeiroNome() + " foi desbloqueada ");
    }

    public void salvarNovoAdministrador() {
        if (!novoUsuario.getSenha().equals(confirmacaoNovaSenha)) {
            UtilMensagens.alerta("Senhas não coincidem");
        } else {
            novoUsuario.setContaAtiva(true);
            DaoAdministradorEstoque.merge(novoUsuario);
            novoUsuario = new CPAdministradorEstoque();
            confirmacaoNovaSenha = "";
            UtilMensagens.ok("Novo usuário registrado");
        }
    }

    public void setMensagemDaBobagem(String mensagemDaBobagem) {
        this.mensagemDaBobagem = mensagemDaBobagem;
    }

    public String getMensagemDaBobagem() {
        return mensagemDaBobagem;
    }

    public void logoff() {
        registroLogin.setHoraDeslogado(UtilData.getDataTimestamp());
        DaoRegistroLoginAdministradorEstoque.merg(registroLogin);
        administrador = new CPAdministradorEstoque();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(Url.local + "estoque/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BeanPortal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void atualizaUnidade(BeanListUnidades listaDeUnidades) {
        listaDeUnidades.getUnidades().remove(listaDeUnidades.getPosManipulado());
        DaoUnidadeDeMedida.merge(listaDeUnidades.getUnidadeManipulada());
        listaDeUnidades.getUnidades().add(listaDeUnidades.getUnidadeManipulada());
        listaDeUnidades.ordena();
        UtilMensagens.info(UtilMensagens.unidadeAtualizada);
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
    }

    public List<CPRegistroLoginAdministradorEstoque> ultimosLogins(CPAdministradorEstoque administrador) {
        return DaoRegistroLoginAdministradorEstoque.registrosDeLogin(administrador, 10);
    }

    public void atualizaAdministrador() {
        administrador = (CPAdministradorEstoque) UtilString.setaPrimeiroESegundoNomeNosUsuarios(administrador);
        DaoAdministradorEstoque.merge(administrador);
        UtilMensagens.alerta(UtilMensagens.contaAtualizada);
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
    }

    public void atualizaSenhaAdministrador() {
        if (senhaAntiga.equals(administrador.getSenha())) {
            //senha antiga confere
            if (novaSenha.equals(confirmacaoNovaSenha)) {
                //senhas conicidem
                administrador.setSenha(novaSenha);
                DaoAdministradorEstoque.merge(administrador);
                UtilMensagens.alerta(UtilMensagens.senhaAtualizada);
                senhaAntiga = "";
                novaSenha = "";
                confirmacaoNovaSenha = "";
                RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
            } else {
                UtilMensagens.alerta(UtilMensagens.senhaNaoCoincidem);
            }
        } else {
            UtilMensagens.alerta(UtilMensagens.senhaAntigaErrada);
        }
    }

    public void loga() {
        if (DaoAdministradorEstoque.existLogin(administrador)) {// se login válido
            if (!DaoAdministradorEstoque.getContaAtiva(administrador)) { // confere bloqueio
                UtilMensagens.info("Sua conta está bloqueada");
            } else { // se não está bloqueado
                CPRegistroLoginAdministradorEstoque validaLogin = DaoAdministradorEstoque.validaLogin(administrador); //convefere senha
                if (validaLogin != null) {
                    // se acertou senha
                    administrador = DaoAdministradorEstoque.getByLogin(administrador);
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Url.local + "estoque/inicio.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(BeanEstoque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {//se errou senha
                    switch (DaoAdministradorEstoque.getNumeroTentativas(administrador)) {
                        case 1:
                            SessionUtil.addErrorMessage("errologin1");
                            break;
                        case 2:
                            SessionUtil.addErrorMessage("errologin2");
                            break;
                        case 3:
                            SessionUtil.addErrorMessage("errologin3");
                            break;
                        case 4:
                            SessionUtil.addErrorMessage("errologin4");
                            break;
                        case 5:
                            SessionUtil.addErrorMessage("errologin5");
                            break;
                    }
                }
            }
        } else { // se login invalido, exibe erro
            SessionUtil.addErrorMessage("loginerrado");
        }
    }

    public void logoff(boolean redireciona) {
        if (registroLogin.getHoraDeslogado() != null) {
            registroLogin.setHoraDeslogado(UtilData.getDataTimestamp());
            DaoRegistroLoginAdministradorEstoque.merg(registroLogin);
            administrador = new CPAdministradorEstoque();
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(Url.local + "estoque/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BeanPortal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void primeiroAcesso() {
        this.administrador.setEmailPrincipal(emailOuLogin);
        if (DaoAdministradorEstoque.existEmail(administrador)) {
            CPAdministradorEstoque admin = DaoAdministradorEstoque.getByEmail(administrador);
            String login = admin.getLogin();
            if (login != null) {
                UtilMensagens.info("Você já possui seu login e sua senha!");
            } else {
                setCodigoSecreto(UtilString.geraCodigoSecreto());
                System.out.println("codigo secreto :" + codigoSecreto);
                admin.setLogin(admin.getPrimeiroNome());
                admin.setSenha(codigoSecreto);
                admin.getPrimeiroNome();
                DaoAdministradorEstoque.merge(admin);
                UtilMensagens.info("Informações enviadas por email");
                RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
            }
        } else {
            UtilMensagens.info("O email não exsite!");
        }
    }

    public void lembrarSenha(Boleanos boleanos) {
        administrador.setEmailPrincipal(emailOuLogin);
        administrador.setLogin(emailOuLogin);
        if (DaoAdministradorEstoque.existEmail(administrador)) {
            this.setCodigoSecreto(UtilString.geraCodigoSecreto());
            //Email.lembraSenha(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
            boleanos.ambosTrue(1);
            RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
            SessionUtil.addSuccessMessage("redefinirsenha");
            System.out.println("codigo secreto :" + codigoSecreto);
        } else if (DaoAdministradorEstoque.existLogin(administrador)) {
            setCodigoSecreto(UtilString.geraCodigoSecreto());
            //Email.lembraSenha(DaoGenericoUsuario.getByLogin(usuario), codigoSecreto, emailPrincipal);
            boleanos.ambosTrue(1);
            RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
            SessionUtil.addSuccessMessage("redefinirsenha");
            System.out.println("codigo secreto :" + codigoSecreto);
        } else {
            SessionUtil.addErrorMessage("emailOuLoginNaoEncontrado");
        }
    }

    public void solicitarCodigoSecretoParaDesbloqueioDeConta(Boleanos b) {
        System.out.println("email ou login: " + emailOuLogin);
        administrador.setEmailPrincipal(emailOuLogin);
        administrador.setLogin(emailOuLogin);
        if (DaoAdministradorEstoque.existEmail(administrador)) {
            setCodigoSecreto(UtilString.geraCodigoSecreto());
            //Email.desbloquearConta(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
            SessionUtil.addSuccessMessage("confirmeCodigoSecreto");
            b.ambosTrue(3l);
            RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
            System.out.println("codigo secreto :" + codigoSecreto);
        } else if (DaoAdministradorEstoque.existLogin(administrador)) {
            setCodigoSecreto(UtilString.geraCodigoSecreto());
            //Email.desbloquearConta(DaoGenericoUsuario.getByLogin(usuario), codigoSecreto, emailPrincipal);
            SessionUtil.addSuccessMessage("confirmeCodigoSecreto");
            b.ambosTrue(3l);
            RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
            System.out.println("codigo secreto :" + codigoSecreto);
        } else {
            SessionUtil.addErrorMessage("emailOuLoginNaoEncontrado");
            RequestContext.getCurrentInstance().addCallbackParam("boleano", false);
        }
    }

    public void confirmarCodigoSecretoEDesbloquearConta() {
        if (codigoSecreto != null) {
            administrador.setEmailPrincipal(emailOuLogin);
            administrador.setLogin(emailOuLogin);
            if (codigoSecreto.equals(confirmaCodigoSecreto)) {
                SessionUtil.addSuccessMessage("desbloqueado");
                DaoAdministradorEstoque.desbloqueiaConta(administrador);
            } else {
                codigoSecreto = null;
                SessionUtil.addErrorMessage("codigoSecretoErrado");
                SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
            }
        } else {
            SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
            SessionUtil.addErrorMessage("soliciteNovoCodigoSecreto");
        }


    }

    public void redefineSenha() {
        if (codigoSecreto == null) {
            SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
            SessionUtil.addErrorMessage("soliciteNovoCodigoSecreto");
            RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
        } else {
            if (codigoSecreto.equals(confirmaCodigoSecreto)) {
                if (novaSenha.equals(confirmacaoNovaSenha)) {
                    if (DaoAdministradorEstoque.existEmail(administrador)) {
                        CPAdministradorEstoque u = DaoAdministradorEstoque.getByEmail(administrador);
                        u.setSenha(novaSenha);
                        DaoAdministradorEstoque.merge(u);
                    } else if (DaoAdministradorEstoque.existLogin(administrador)) {
                        CPAdministradorEstoque u = DaoAdministradorEstoque.getByLogin(administrador);
                        u.setSenha(novaSenha);
                        DaoAdministradorEstoque.merge(u);
                    }
                    SessionUtil.addSuccessMessage("senhaRedefinida");
                    RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
                } else {
                    SessionUtil.addErrorMessage("senhasNaoCoincidem");
                }
            } else {
                SessionUtil.addErrorMessage("codigoSecretoErrado");
                SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
                RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
                codigoSecreto = null;
            }
        }
    }

    public void logarAdministrador() {
        boolean resultado = false;
        if (DaoAdministradorEstoque.existLogin(administrador)) {// se login válido
            if (!DaoAdministradorEstoque.getContaAtiva(administrador)) { // confere bloqueio
                SessionUtil.addErrorMessage("contaBloqueada");
            } else { // se não está bloqueado
                CPRegistroLoginAdministradorEstoque validaLogin = DaoAdministradorEstoque.validaLogin(administrador); //convefere senha
                if (validaLogin != null) {
                    // se acertou senha
                    SessionUtil.addSuccessMessage("logou");
                    registroLogin = validaLogin;
                    administrador = DaoAdministradorEstoque.getByLogin(administrador);
                    resultado = true;
                    // FacesContext.getCurrentInstance().getExternalContext().redirect(UtilMensagens.local + "estoque/inicio.xhtml");
                } else {//se errou senha
                    switch (DaoAdministradorEstoque.getNumeroTentativas(administrador)) {
                        case 1:
                            SessionUtil.addErrorMessage("errologin1");
                            break;
                        case 2:
                            SessionUtil.addErrorMessage("errologin2");
                            break;
                        case 3:
                            SessionUtil.addErrorMessage("errologin3");
                            break;
                        case 4:
                            SessionUtil.addErrorMessage("errologin4");
                            break;
                        case 5:
                            SessionUtil.addErrorMessage("errologin5");
                            break;
                    }
                }
            }
        } else { // se login invalido, exibe erro
            SessionUtil.addErrorMessage("loginerrado");
        }
        RequestContext.getCurrentInstance().addCallbackParam("resultado", resultado);
    }

    public String getCodigoSecreto() {
        return codigoSecreto;
    }

    public void setCodigoSecreto(String codigoSecreto) {
        this.codigoSecreto = codigoSecreto;
    }

    public String getConfirmaCodigoSecreto() {
        return confirmaCodigoSecreto;
    }

    public void setConfirmaCodigoSecreto(String confirmaCodigoSecreto) {
        this.confirmaCodigoSecreto = confirmaCodigoSecreto;
    }

    public String getConfirmacaoNovaSenha() {
        return confirmacaoNovaSenha;
    }

    public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
        this.confirmacaoNovaSenha = confirmacaoNovaSenha;
    }

    public String getEmailOuLogin() {
        return emailOuLogin;
    }

    public void setEmailOuLogin(String emailOuLogin) {
        this.emailOuLogin = emailOuLogin;
    }

    public boolean isEmailPrincipal() {
        return emailPrincipal;
    }

    public void setEmailPrincipal(boolean emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public void setFazendoBobagem(boolean fazendoBobagem) {
        this.fazendoBobagem = fazendoBobagem;
    }

    public boolean isFazendoBobagem() {
        return fazendoBobagem;
    }

    public CPAdministradorEstoque getAdministrador() {
        return administrador;
    }

    public void setAdministrador(CPAdministradorEstoque administrador) {
        this.administrador = administrador;
    }

    public CPAdministradorEstoque getUsuario() {
        return administrador;
    }

    public void setUsuario(CPAdministradorEstoque usuario) {
        this.administrador = usuario;
    }

    public CPAdministradorEstoque getNovoUsuario() {
        return novoUsuario;
    }

    public void setNovoUsuario(CPAdministradorEstoque novoUsuario) {
        this.novoUsuario = novoUsuario;
    }

    public CPAdministradorEstoque getAdministradorManipulado() {
        return administradorManipulado;
    }

    public List<CPAdministradorEstoque> getAdministradores() {
        List<CPAdministradorEstoque> r = new LinkedList<>();
        for (CPAdministradorEstoque xAdmin : administradores) {
            if (xAdmin.getId() != administrador.getId()) {
                r.add(xAdmin);
            }
        }
        return r;
    }

    public CPRegistroLoginAdministradorEstoque getRegistroLogin() {
        return registroLogin;
    }

    public void setAdministradorManipulado(CPAdministradorEstoque administradorManipulado) {
        this.administradorManipulado = administradorManipulado;
    }

    public void setAdministradores(List<CPAdministradorEstoque> administradores) {
        this.administradores = administradores;
    }

    public void setRegistroLogin(CPRegistroLoginAdministradorEstoque registroLogin) {
        this.registroLogin = registroLogin;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
