/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.email;

import cp.portal.usuarios.ABSUsuario;
import org.apache.commons.mail.HtmlEmail;
import utilidades.string.UtilString;

/**
 *
 * @author Felipe Machado
 */
public class Email {

    private final String hostName = "mail.f3soft.net";
    private final String emailContato = "contato@f3soft.net";
    private final String emailSuporte = "suporte@f3soft.net";
    private final String senhaEmail = "pvyfq2jtbv";
    private String assinaturaDe;
    private String assinaturaPara;
    private String assunto;
    private String msg;
    private String emailDestinatario;
    private String emailRemetente;
    private boolean urgente;
    private ABSUsuario destinatario;

    
    public Email(ABSUsuario prUsuario) {
     this.destinatario = prUsuario;   
    }

    public Email() {
    }

    
    public void primeiroAcesso(){
        this.setAssinaturaDe("Suporte F3Soft");
        this.setAssinaturaPara(UtilString.trataNomeUsuario(this.destinatario));
        this.setAssunto("Primeiro acesso");
        this.setEmailDestinatario(this.destinatario.getEmailPrincipal());
        this.setMsg("<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><link type=\"text/css\" rel=\"stylesheet\" href=\"http://f3soft:8084/fisiotec/javax.faces.resource/theme.css.xhtml?ln=primefaces-aristo\" /><link type=\"text/css\" rel=\"stylesheet\" href=\"http://f3soft:8084/fisiotec/javax.faces.resource/style.css.xhtml?ln=css\" /><link type=\"text/css\" rel=\"stylesheet\" href=\"http://f3soft:8084/fisiotec/javax.faces.resource/primefaces.css.xhtml?ln=primefaces&amp;v=3.0.RC2\" /><script type=\"text/javascript\" src=\"http://f3soft:8084/fisiotec/javax.faces.resource/jquery/jquery.js.xhtml?ln=primefaces&amp;v=3.0.RC2\"></script><script type=\"text/javascript\" src=\"http://f3soft:8084/fisiotec/javax.faces.resource/primefaces.js.xhtml?ln=primefaces&amp;v=3.0.RC2\"></script><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><link rel=\"shortcut icon\" href=\"http://f3soft:8084/fisiotec/resources/imagens/stethoscope.png\" type=\"image/x-icon\" /><title>VR1</title></head><body><div class=\"bordaExterna\"><div id=\"todoConteudo\" class=\"todoConteudo\"><div class=\"logo\"></div><div style=\"margin-top: 7px;\"><div id=\"j_idt117\" class=\"ui-panel ui-widget ui-widget-content ui-corner-all\"><div id=\"j_idt117_header\" class=\"ui-panel-titlebar ui-widget-header ui-corner-all\"><span class=\"ui-panel-title\">VR2</span></div><div id=\"j_idt117_content\" class=\"ui-panel-content ui-widget-content\"><div style=\"height: 100%; border-radius: 10px; padding: 40px;\"><div style=\"width: 100%; text-align: center;font-family: Arial,sans-serif; font-size: 26px; \">VR3</div><div style=\"width: 100%; text-align: center;font-family: Arial,sans-serif; font-size: 18px; \"><br />VR4<br />VR5</div></div></div></div><script id=\"j_idt117_s\" type=\"text/javascript\">PrimeFaces.cw('Panel','widget_j_idt117',{id:'j_idt117'});</script></div><div class=\"rodape\"></div></div></div></body></html>");
        this.enviaEmail();
    }
    
    public void lembraSenha(ABSUsuario u, String codigoSecreto, boolean emailPrincipal) {
//        Email e = new Email();
//        e.setAssinaturaDe("Suporte F3Soft");
//        e.setAssinaturaPara(UtilString.trataNomeUsuario(u));
//        e.setAssunto("Relembrando senha");
//        if (emailPrincipal) {
//            e.setEmailDestinatario(u.getEmailPrincipal());
//        } else {
//            e.setEmailDestinatario(u.getEmailSecundario());
//        }
//        e.setEmailRemetente(e.getEmailContato());
//        String codigoSecreto = UtilString.geraCodigoSecreto();
//        e.setMsg("O código secreto para redefinição de senha é : " + codigoSecreto);
//        e.enviaEmail();
        System.out.println("email enviado para " + UtilString.trataNomeUsuario(u) + " Relembrando senha O código secreto para redefinição de senha é : " + codigoSecreto);
    }

    public void desbloquearConta(ABSUsuario u, String codigoSecreto, boolean emailPrincipal) {
        Email e = new Email();
        e.setAssinaturaDe("Suporte F3Soft");
        e.setAssinaturaPara(UtilString.trataNomeUsuario(u));
        e.setAssunto("Relembrando senha");
        if (emailPrincipal) {
            e.setEmailDestinatario(u.getEmailPrincipal());
        } else {
            e.setEmailDestinatario(u.getEmailSecundario());
        }
        e.setEmailRemetente(e.getEmailContato());
        codigoSecreto = UtilString.geraCodigoSecreto();
        e.setMsg("O código secreto para redefinição de senha é : " + codigoSecreto);
        e.enviaEmail();
        System.out.println("email enviado para " + UtilString.trataNomeUsuario(u) + " desbloqueio conta O código secreto para desbloquei de conta é : " + codigoSecreto);
    }

    public boolean enviaEmail() {
        try {
            org.apache.commons.mail.Email email = new HtmlEmail();
            email.setHostName(hostName);
            email.setCharset("UTF-8");
            email.addTo(emailDestinatario, assinaturaPara);
            email.setFrom(emailRemetente, assinaturaDe);
            email.setSubject(assunto);
            email.setMsg(msg);
            email.setAuthentication(emailRemetente, senhaEmail);
            if (this.urgente) {
                email.addHeader("X-Priority", "1");
                email.addHeader("Priority", "Urgent");
                email.addHeader("Importance", "high");
            }
            email.send();
            return true;
        } catch (Exception ex) {
            System.out.println("treta : " + ex.getMessage());
            return false;
        }
    }

    public String getAssinaturaDe() {
        return assinaturaDe;
    }

    public void setAssinaturaDe(String assinaturaDe) {
        this.assinaturaDe = assinaturaDe;
    }

    public String getAssinaturaPara() {
        return assinaturaPara;
    }

    public void setAssinaturaPara(String assinaturaPara) {
        this.assinaturaPara = assinaturaPara;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    public String getEmailRemetente() {
        return emailRemetente;
    }

    public void setEmailRemetente(String emailRemetente) {
        this.emailRemetente = emailRemetente;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public String getEmailSuporte() {
        return emailSuporte;
    }
}