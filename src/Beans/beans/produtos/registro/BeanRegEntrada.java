/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos.registro;

import cp.CPPontoDeEstagio;
import dao.estoque.entrada.DaoEntrada;
import dao.estoque.entrada.DaoEntradaProduto;
import cp.estoque.entrada.CPEntrada;
import cp.estoque.entrada.CPEntradaProduto;
import cp.estoque.saida.CPSaida;
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
@ManagedBean(name = "beanCPEntrada")
@ViewScoped
public class BeanRegEntrada implements Serializable {

    private List<CPEntrada> registrosCPEntrada = DaoEntrada.getAllOrderByData();
    private List<CPEntrada> registrosCPEntradaManipulado = registrosCPEntrada;
    
    private Timestamp de;
    private Timestamp ate;
    
    public List<CPEntradaProduto> cpSaidaProdutoDeSaida(CPEntrada entrada) {
        return DaoEntradaProduto.cpEntradaProdutoDeDeterminadoRegistroEntrada(entrada);
    }

    public void filtrar(){
        registrosCPEntradaManipulado = new LinkedList<>();
        for (CPEntrada xEntrada : registrosCPEntrada) {
            boolean add = true;

            // # DE
            if (de != null) {
                if (xEntrada.getDataEntrada().getTime() < de.getTime()) {
                    add = false;
                }
            }
            // # ATE
            if (ate != null) {
                if (xEntrada.getDataEntrada().getTime() > ate.getTime()) {
                    add = false;
                }
            }
            if (add) {
                registrosCPEntradaManipulado.add(xEntrada);
            }
            
        }
        UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", registrosCPEntradaManipulado.size() + ""));
    }
    
    public Timestamp getAte() {
        return ate;
    }

    public void setAte(Timestamp ate) {
        this.ate = ate;
    }

    public Timestamp getDe() {
        return de;
    }

    public void setDe(Timestamp de) {
        this.de = de;
    }

    public List<CPEntrada> getRegistrosCPEntrada() {
        return registrosCPEntrada;
    }

    public void setRegistrosCPEntrada(List<CPEntrada> registrosCPEntrada) {
        this.registrosCPEntrada = registrosCPEntrada;
    }

    public List<CPEntrada> getRegistrosCPEntradaManipulado() {
        return registrosCPEntradaManipulado;
    }

    public void setRegistrosCPEntradaManipulado(List<CPEntrada> registrosCPEntradaManipulado) {
        this.registrosCPEntradaManipulado = registrosCPEntradaManipulado;
    }

    
    
}
