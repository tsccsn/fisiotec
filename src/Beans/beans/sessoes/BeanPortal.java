/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package beans.sessoes;

import cp.portal.registroLogin.ABSREgistroLogin;
import cp.portal.registroLogin.CPRegistroLoginAdministrador;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.ABSUsuario;
import cp.portal.usuarios.CPAdministrador;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.portal.registroLogin.DaoRegistroLoginAdministrador;
import dao.portal.registroLogin.DaoRegistroLoginProfessor;
import dao.portal.usuarios.DaoAdministrador;
import dao.portal.usuarios.DaoProfessor;
import java.io.IOException;
import java.io.Serializable;
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
@ManagedBean(name = "portalGenerico")
@SessionScoped
public class BeanPortal implements Serializable {

    private static final long serialVersionUID = 1L;
    private ABSUsuario usuario = new ABSUsuario() {
    };
    private ABSREgistroLogin registroLogin;
    private CPAdministrador administrador = new CPAdministrador();
    private CPProfessor professor = new CPProfessor();
    private CPAluno aluno = new CPAluno();
    private boolean emailPrincipal = true; // 0 - principal | 1 - secundario
    private String codigoSecreto;
    private String emailOuLogin;
    private String confirmaCodigoSecreto;
    private String novaSenha;
    private String confirmacaoNovaSenha;
    private String senhaAntiga;
    private boolean fazendoBobagem = false;
    private String mensagemDaBobagem = "";

    public void setMensagemDaBobagem(String mensagemDaBobagem) {
        this.mensagemDaBobagem = mensagemDaBobagem;
    }

    public String getMensagemDaBobagem() {
        return mensagemDaBobagem;
    }

    public List<CPRegistroLoginAdministrador> ultimosLoginsAdministrador() {
        return DaoRegistroLoginAdministrador.ultimosLogins(((CPAdministrador) usuario), 10);
    }

    public List<CPRegistroLoginProfessor> ultimosLoginsprofessor() {
        return DaoRegistroLoginProfessor.ultimosLogins(((CPProfessor) usuario), 10);
    }
    // 0 - admin
    // 1 - professor
    // 2 - aluno
    private int tipoUsuario;

    public void mensagemBobagem() {
        if (fazendoBobagem) {
            UtilMensagens.alerta(mensagemDaBobagem);
            fazendoBobagem = false;
        }
    }

    public ABSREgistroLogin getRegistroLogin() {
        return registroLogin;
    }

    public void setRegistroLogin(ABSREgistroLogin registroLogin) {
        this.registroLogin = registroLogin;
    }

    public void logoff() {
        registroLogin.setHoraDeslogado(UtilData.getDataTimestamp());
        if (registroLogin instanceof CPRegistroLoginAdministrador) {
            administrador = new CPAdministrador();
            DaoRegistroLoginAdministrador.merge((CPRegistroLoginAdministrador) registroLogin);
        } else if (registroLogin instanceof CPRegistroLoginProfessor) {
            professor = new CPProfessor();
            DaoRegistroLoginProfessor.merge((CPRegistroLoginProfessor) registroLogin);
        }
        usuario = new ABSUsuario() {
        };
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(Url.local + "portal/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BeanPortal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void primeiroAcessoProfessor() {
        this.professor.setEmailPrincipal(emailOuLogin);
        if (DaoProfessor.existEmail(professor)) {
            CPProfessor prof = DaoProfessor.getByEmail(professor);

            String login = prof.getLogin();

            if (login != null) {
                UtilMensagens.info("Você já tem seu login e senha");
            } else {
                setCodigoSecreto(UtilString.geraCodigoSecreto());
                System.out.println("codigo secreto :" + codigoSecreto);
                prof.setLogin(prof.getPrimeiroNome());
                prof.setSenha(codigoSecreto);
                prof.getPrimeiroNome();
                DaoProfessor.merge(prof);
                UtilMensagens.info("Informações enviadas por email");
                RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
            }


        } else {
            UtilMensagens.info("Email inexistente!");
        }
    }

    public void lembrarSenha(Boleanos boleanos) {
        usuario.setEmailPrincipal(emailOuLogin);
        usuario.setLogin(emailOuLogin);
        if (usuario instanceof CPAdministrador) {
            if (DaoAdministrador.existEmail((CPAdministrador) usuario)) {
                this.setCodigoSecreto(UtilString.geraCodigoSecreto());
                //Email.lembraSenha(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
                boleanos.ambosTrue(1);
                RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
                SessionUtil.addSuccessMessage("redefinirsenha");
                System.out.println("codigo secreto :" + codigoSecreto);
            } else if (DaoAdministrador.existLogin((CPAdministrador) usuario)) {
                setCodigoSecreto(UtilString.geraCodigoSecreto());
                //Email.lembraSenha(DaoGenericoUsuario.getByLogin(usuario), codigoSecreto, emailPrincipal);
                boleanos.ambosTrue(1);
                RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
                SessionUtil.addSuccessMessage("redefinirsenha");
                System.out.println("codigo secreto :" + codigoSecreto);
            } else {
                SessionUtil.addErrorMessage("emailOuLoginNaoEncontrado");
            }
        } else if (usuario instanceof CPProfessor) {
            if (DaoProfessor.existEmail((CPProfessor) usuario)) {
                setCodigoSecreto(UtilString.geraCodigoSecreto());
                //Email.lembraSenha(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
                boleanos.ambosTrue(1);
                RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
                SessionUtil.addSuccessMessage("redefinirsenha");
                System.out.println("codigo secreto :" + codigoSecreto);
            } else if (DaoProfessor.existLogin((CPProfessor) usuario)) {
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
//                if (DaoAluno.existEmail((CPAluno) usuario)) {
//                    setCodigoSecreto(UtilString.geraCodigoSecreto());
//                    //Email.lembraSenha(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
//                    boleanos.ambosTrue(1);
//                    RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
//                    SessionUtil.addSuccessMessage("redefinirsenha");
//                    System.out.println("codigo secreto :" + codigoSecreto);
//                } else if (DaoAluno.existLogin((CPAluno) usuario)) {
//                    setCodigoSecreto(UtilString.geraCodigoSecreto());
//                    //Email.lembraSenha(DaoGenericoUsuario.getByLogin(usuario), codigoSecreto, emailPrincipal);
//                    boleanos.ambosTrue(1);
//                    RequestContext.getCurrentInstance().addCallbackParam("redefineSenha", true);
//                    SessionUtil.addSuccessMessage("redefinirsenha");
//                    System.out.println("codigo secreto :" + codigoSecreto);
//                } else {
//                    SessionUtil.addErrorMessage("emailOuLoginNaoEncontrado");
//                }
    }

    public void solicitarCodigoSecretoParaDesbloqueioDeConta(Boleanos b) {
        usuario.setEmailPrincipal(emailOuLogin);
        usuario.setLogin(emailOuLogin);
        switch (tipoUsuario) {
            case 0:
                if (DaoAdministrador.existEmail((CPAdministrador) usuario)) {
                    setCodigoSecreto(UtilString.geraCodigoSecreto());
                    //Email.desbloquearConta(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
                    SessionUtil.addSuccessMessage("confirmeCodigoSecreto");
                    b.ambosTrue(3l);
                    RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
                    System.out.println("codigo secreto :" + codigoSecreto);
                } else if (DaoAdministrador.existLogin((CPAdministrador) usuario)) {
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
                break;
            case 1:
                break;
            case 2:
                break;
        }
//        if (DaoGenericoUsuario.existEmail(usuario)) {
//            setCodigoSecreto(UtilString.geraCodigoSecreto());
//            Email.desbloquearConta(DaoGenericoUsuario.getByEmail(usuario), codigoSecreto, emailPrincipal);
//            SessionUtil.addSuccessMessage("confirmeCodigoSecreto");
//            b.ambosTrue(3l);
//            RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
//        } else if (DaoGenericoUsuario.existLogin(usuario)) {
//            setCodigoSecreto(UtilString.geraCodigoSecreto());
//            Email.desbloquearConta(DaoGenericoUsuario.getByLogin(usuario), codigoSecreto, emailPrincipal);
//            SessionUtil.addSuccessMessage("confirmeCodigoSecreto");
//            b.ambosTrue(3l);
//            RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
//        } else {
//            SessionUtil.addErrorMessage("emailOuLoginNaoEncontrado");
//            RequestContext.getCurrentInstance().addCallbackParam("boleano", false);
//        }
    }

    public void confirmarCodigoSecretoEDesbloquearConta() {
        if (codigoSecreto != null) {
            usuario.setEmailPrincipal(emailOuLogin);
            usuario.setLogin(emailOuLogin);
            switch (tipoUsuario) {
                case 0:
                    if (codigoSecreto.equals(confirmaCodigoSecreto)) {
                        SessionUtil.addSuccessMessage("desbloqueado");
                        DaoAdministrador.desbloqueiaConta((CPAdministrador) usuario);
                    } else {
                        codigoSecreto = null;
                        SessionUtil.addErrorMessage("codigoSecretoErrado");
                        SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        } else {
            SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
            SessionUtil.addErrorMessage("soliciteNovoCodigoSecreto");
        }

//        if (codigoSecreto != null) {
//            usuario.setEmailPrincipal(emailOuLogin);
//            usuario.setLogin(emailOuLogin);
//            if (codigoSecreto.equals(confirmaCodigoSecreto)) {
//                SessionUtil.addSuccessMessage("desbloqueado");
//                DaoGenericoUsuario.desbloqueiaConta(usuario);
//            } else {
//                codigoSecreto = null;
//                SessionUtil.addErrorMessage("codigoSecretoErrado");
//                SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
//            }
//        } else {
//            SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
//            SessionUtil.addErrorMessage("soliciteNovoCodigoSecreto"); 
//        }
    }

    public void redefineSenha() {
//        if (codigoSecreto == null) {
//            SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
//            SessionUtil.addErrorMessage("soliciteNovoCodigoSecreto");
//             RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
//        } else {
//            if (codigoSecreto.equals(confirmaCodigoSecreto)) {
//                if (novaSenha.equals(confirmacaoNovaSenha)) {
//                    if (DaoGenericoUsuario.existEmail(usuario)) {
//                        ABSUsuario u = DaoGenericoUsuario.getByEmail(usuario);
//                        u.setSenha(novaSenha);
//                        DaoGenericoUsuario.merge(u);
//                    } else if (DaoGenericoUsuario.existLogin(usuario)) {
//                        ABSUsuario u = DaoGenericoUsuario.getByLogin(usuario);
//                        u.setSenha(novaSenha);
//                        DaoGenericoUsuario.merge(u);
//                    }
//                    SessionUtil.addSuccessMessage("senhaRedefinida");
//                } else {
//                    SessionUtil.addErrorMessage("senhasNaoCoincidem");
//                }
//            } else {
//                SessionUtil.addErrorMessage("codigoSecretoErrado");
//                SessionUtil.addErrorMessage("redefinicaoDeSenhaEncerrada");
//                RequestContext.getCurrentInstance().addCallbackParam("boleano", true);
//                codigoSecreto = null;
//            }
//        }
    }

    public void logarAdministrador() {
        if (DaoAdministrador.existLogin((CPAdministrador) usuario)) {// se login válido
            if (!DaoAdministrador.getContaAtiva((CPAdministrador) usuario)) { // confere bloqueio
                SessionUtil.addErrorMessage("contaBloqueada");
            } else { // se não está bloqueado
                CPRegistroLoginAdministrador validaLogin = DaoAdministrador.validaLogin((CPAdministrador) usuario); //convefere senha
                if (validaLogin != null) {
                    // se acertou senha
                    SessionUtil.addSuccessMessage("logou");
                    registroLogin = validaLogin;
                    administrador = DaoAdministrador.getByLogin((CPAdministrador) usuario);
                    usuario = administrador;
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(Url.local + "portal/administrador/inicio.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(BeanPortal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {//se errou senha
                    RequestContext.getCurrentInstance().addCallbackParam("treme", true);
                    switch (DaoAdministrador.getNumeroTentativas((CPAdministrador) usuario)) {
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
            RequestContext.getCurrentInstance().addCallbackParam("treme", true);
            SessionUtil.addErrorMessage("loginerrado");
        }
    }

    public CPAdministrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(CPAdministrador administrador) {
        this.administrador = administrador;
    }

    public CPAluno getAluno() {
        return aluno;
    }

    public void setAluno(CPAluno aluno) {
        this.aluno = aluno;
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

    public CPProfessor getProfessor() {
        return professor;
    }

    public void setProfessor(CPProfessor professor) {
        this.professor = professor;
    }

    public ABSUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(ABSUsuario usuario) {
        this.usuario = usuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
}
