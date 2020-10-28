/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos;

/**
 *
 * @author Thiago
 */
import beans.listas.BeanListProdutos;
import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import cp.estoque.saida.CPSaida;
import cp.estoque.saida.CPSaidaProduto;
import dao.estoque.DaoAlertasQuantidadeProduto;
import dao.estoque.saida.DaoSaida;
import dao.estoque.saida.DaoSaidaProduto;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utilidades.Boleanos;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;

@ManagedBean(name = "beanSaida")
@ViewScoped
public class BeanSaida implements Serializable {

    private static final long serialVersionUID = 1L;
    private int quantidade = 1;
    private int novaQuantidade = 1;
    private List<CPSaidaProduto> listaSaidaProduto = new LinkedList<>();
    private int indexSaidaProduto = -1;
    private String stepWizr = "localSaida";//arquivados data

    public void novaQuantidade(Boleanos boleanos) {
        if (listaSaidaProduto.get(indexSaidaProduto).getProduto().getQuantidadeEmStoque() < novaQuantidade) {
            UtilMensagens.alerta(UtilMensagens.registroDeSaidaTentandoTirarMaisDoQueTemMethStc(listaSaidaProduto.get(indexSaidaProduto).getProduto()));
        } else if (listaSaidaProduto.get(indexSaidaProduto).getProduto().getQuantidadeMinimaEmStoque()
                >= (listaSaidaProduto.get(indexSaidaProduto).getProduto().getQuantidadeEmStoque() - novaQuantidade)) {
            RequestContext.getCurrentInstance().addCallbackParam("abre", true);
            boleanos.ambosTrue(3);
        } else {
            listaSaidaProduto.get(indexSaidaProduto).setQuantidade(novaQuantidade);
            RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
        }
    }

    public void setaNovaQuantidadeMesmoEmNivelCritico(Boleanos boleanos) {
        listaSaidaProduto.get(indexSaidaProduto).setQuantidade(novaQuantidade);
    }

    public void registraSaida(CPPontoDeEstagio p, BeanListProdutos lp) {
        if (lp.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.registroDeSaidaSemProdutis);
        } else {
            CPSaida s = new CPSaida();
            s.setDestino(p);
            s.setDataSaida(UtilData.getDataTimestamp());
            s = DaoSaida.mergeII(s);
            for (CPSaidaProduto xSaidaProduto : listaSaidaProduto) {
                xSaidaProduto.setSaida(s);
                DaoSaidaProduto.merge(xSaidaProduto);
                if(xSaidaProduto.getProduto().getQuantidadeMinimaEmStoque() >= (xSaidaProduto.getProduto().getQuantidadeEmStoque() - xSaidaProduto.getQuantidade())){
                    DaoAlertasQuantidadeProduto.geraNovoAviso(xSaidaProduto.getProduto());
                }
            }
            UtilMensagens.ok(UtilMensagens.registroDeSaidaSalvo);
            lp.zeraManipulados();
        }
        listaSaidaProduto = new LinkedList<>(); 
   }

    public void navegaNoWizard(String navega) {
        stepWizr = navega;
    }

    public void addProduto(BeanListProdutos listadeprodutios, Boleanos boleanos) {
        if (listadeprodutios.getProdutoIndexado() != null) {
            if (listadeprodutios.getProdutoIndexado().getQuantidadeEmStoque() < quantidade) {
                UtilMensagens.alerta(UtilMensagens.registroDeSaidaTentandoTirarMaisDoQueTemMethStc(listadeprodutios.getProdutoIndexado()));
            } else if (listadeprodutios.getProdutoIndexado().getQuantidadeMinimaEmStoque() >= (listadeprodutios.getProdutoIndexado().getQuantidadeEmStoque() - quantidade)) {
                boleanos.ambosTrue(2);
                RequestContext.getCurrentInstance().addCallbackParam("abre", true);
            } else {
                CPSaidaProduto sp = new CPSaidaProduto();
                sp.setProduto(listadeprodutios.getProdutoIndexado());
                sp.setQuantidade(quantidade);
                quantidade = 1;
                listadeprodutios.desce();
                listaSaidaProduto.add(sp);
            }
        }else{
            UtilMensagens.info(UtilMensagens.produtosTodosAdd);
        }

    }

    public void addProdutoMesmoEleFicandoEmNivelCritico(BeanListProdutos p) {
        CPSaidaProduto sp = new CPSaidaProduto();
        sp.setProduto(p.getProdutoIndexado());
        sp.setQuantidade(quantidade);
        quantidade = 1;
        p.desce();
        listaSaidaProduto.add(sp);
    }

    public void removeProduto() {
        listaSaidaProduto.remove(indexSaidaProduto);
        UtilMensagens.info(UtilMensagens.produtoRemovido);
    }

    public boolean confereNivelCritico(CPSaidaProduto sp) {
        //p.getProdutoIndexado().getQuantidadeMinimaEmStoque() >= (p.getProdutoIndexado().getQuantidadeEmStoque() - quantidade)
        if (sp == null) {
            return false;
        } else {
            if (sp.getProduto().getQuantidadeMinimaEmStoque() >= (sp.getProduto().getQuantidadeEmStoque() - sp.getQuantidade())) {
                return true;
            } else {
                return false;
            }
        }

    }

    public void remove() {
        listaSaidaProduto.remove(indexSaidaProduto);
    }

    /**
     * get e set
     *
     */
    public String getStepWizr() {
        return stepWizr;
    }

    public void setStepWizr(String stepWizr) {
        this.stepWizr = stepWizr;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getNovaQuantidade() {
        return novaQuantidade;
    }

    public void setNovaQuantidade(int novaQuantidade) {
        this.novaQuantidade = novaQuantidade;
    }

    public void setIndexSaidaProduto(int indexSaidaProduto) {
        this.indexSaidaProduto = indexSaidaProduto;
    }

    public int getIndexSaidaProduto() {
        return indexSaidaProduto;
    }

    public void setListaSaidaProduto(List<CPSaidaProduto> listaSaidaProduto) {
        this.listaSaidaProduto = listaSaidaProduto;
    }

    public List<CPSaidaProduto> getListaSaidaProduto() {
        return listaSaidaProduto;
    }
}
