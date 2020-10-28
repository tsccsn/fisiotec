/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos;

import javax.faces.bean.ManagedBean;
import utilidades.hibernate.SessionUtil;

/**
 *
 * @author Thiago-Asus
 */
@ManagedBean(name="msgs")
public class BeanMensagem {
    private String mensagem = "valor";

    private String erro = "erro!";

    
    public void men(){
        this.mensagem = mensagem + " =D ";
        SessionUtil.addSuccessMessage("ok");
    }
    
    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
    
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
}
