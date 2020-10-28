/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos;

import cp.estoque.CPAlertasQuantidadeProduto;
import dao.estoque.DaoAlertasQuantidadeProduto;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "avisos")
@ViewScoped
public class BeanAvisos implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CPAlertasQuantidadeProduto> alertasQuantidadeProdutos = DaoAlertasQuantidadeProduto.getAllByDataGerado();

    
    public void geraAlertas(){
        
    }
    
    public List<CPAlertasQuantidadeProduto> getAlertasQuantidadeProdutos() {
        return alertasQuantidadeProdutos;
    }

    public void setAlertasQuantidadeProdutos(List<CPAlertasQuantidadeProduto> alertasQuantidadeProdutos) {
        this.alertasQuantidadeProdutos = alertasQuantidadeProdutos;
    }
    
}
