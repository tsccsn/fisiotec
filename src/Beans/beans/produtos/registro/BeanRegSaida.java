/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos.registro;

import cp.estoque.saida.CPSaida;
import cp.estoque.saida.CPSaidaProduto;
import dao.estoque.saida.DaoSaida;
import dao.estoque.saida.DaoSaidaProduto;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "beanCPSaida")
@ViewScoped
public class BeanRegSaida implements Serializable {
    
    private List<CPSaida> registrosCPsaida = DaoSaida.getAllOrderByData();
    private List<CPSaida> registrosCPsaidaManipulado = registrosCPsaida;
    private Timestamp de;
    private Timestamp ate;
    
    public List<CPSaidaProduto> cpSaidaProdutoDeSaida(CPSaida saida) {
        return DaoSaidaProduto.cpSaidaProdutoDeDeterminadoRegistroSaida(saida);
    }
    
    public void filtrar(long ponto) {
        registrosCPsaidaManipulado = new LinkedList<>();
        for (CPSaida xSaida : registrosCPsaida) {
            boolean add = true;

            // # DE
            if (de != null) {
                if (xSaida.getDataSaida().getTime() < de.getTime()) {
                    add = false;
                }
            }
            // # ATE
            if (ate != null) {
                if (xSaida.getDataSaida().getTime() > ate.getTime()) {
                    add = false;
                }
            }
            
            if (ponto > 0) {
                if(xSaida.getDestino().getId() != ponto){
                    add = false;
                }
            }
            
            if (add) {
                registrosCPsaidaManipulado.add(xSaida);
            }
            
        }
//        for (CPSaida xSaida : registrosCPsaida) {
//            //DE
//            if(de!=null){
//                if(xSaida.getDataSaida().getTime() > de.getTime() ){
//                    break;
//                }
//            }
//            if(ate!=null){
//                if(xSaida.getDataSaida().getTime() < ate.getTime() ){
//                    break;
//                }
//            }
//            if(ponto!=null){
//                if(xSaida.getDestino().getId() != ponto.getId()){
//                    break;
//                }
//            }
//            registrosCPsaidaManipulado.add(xSaida);
//        }
        UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", registrosCPsaidaManipulado.size() + ""));
        
    }

    // # GET SET
    public List<CPSaida> getRegistrosCPsaida() {
        return registrosCPsaida;
    }
    
    public void setRegistrosCPsaida(List<CPSaida> registrosCPsaida) {
        this.registrosCPsaida = registrosCPsaida;
    }
    
    public Timestamp getAte() {
        return ate;
    }
    
    public Timestamp getDe() {
        return de;
    }
    
    public void setAte(Timestamp ate) {
        this.ate = ate;
    }
    
    public void setDe(Timestamp de) {
        this.de = de;
    }
    
    public List<CPSaida> getRegistrosCPsaidaManipulado() {
        return registrosCPsaidaManipulado;
    }
    
    public void setRegistrosCPsaidaManipulado(List<CPSaida> registrosCPsaidaManipulado) {
        this.registrosCPsaidaManipulado = registrosCPsaidaManipulado;
    }
}
