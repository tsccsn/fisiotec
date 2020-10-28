/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos;

import beans.listas.BeanListProdutos;
import cp.estoque.CPAlertasQuantidadeProduto;
import cp.estoque.CPProduto;
import cp.estoque.entrada.CPEntrada;
import cp.estoque.entrada.CPEntradaProduto;
import dao.estoque.DaoAlertasQuantidadeProduto;
import dao.estoque.entrada.DaoEntrada;
import dao.estoque.entrada.DaoEntradaProduto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "prodsEntrando")
@ViewScoped
public class BeanEntrada implements Serializable {

    private static final long serialVersionUID = 1L;
    private int quantidade = 1;
    private int novaQuantidade;
    private String stepWizr = "produtos";
    private HashMap<Long, CPEntradaProduto> produtosEntrando = new HashMap<>();

    public void addProdutoNaLista(BeanListProdutos lp) {
        if (lp.getProdutoIndexado() != null) {
            addProduto(lp.getProdutoIndexado(), quantidade);
            lp.desce();
            quantidade = 1;
        }
    }

    private void addProduto(CPProduto p, int quantidade) {
        CPEntradaProduto pe = new CPEntradaProduto();
        pe.setProduto(p);
        pe.setQuantidade(quantidade);
        produtosEntrando.put(p.getId(), pe);
    }

    public void removeProduto(BeanListProdutos beanListProdutos) {
        beanListProdutos.sobe();
        produtosEntrando.remove(beanListProdutos.getProdutoIndexado().getId());
    }

    private void altera(CPProduto xProduto, int xQuantidade) {
        CPEntradaProduto get = produtosEntrando.get(xProduto.getId());
        produtosEntrando.remove(xProduto.getId());;
        get.setQuantidade(xQuantidade);
        produtosEntrando.put(xProduto.getId(), get);
    }

    public void novaQuantidade(CPProduto xProduto) {
        altera(xProduto, novaQuantidade);
        UtilMensagens.ok(UtilMensagens.produtoQuantidadeSaidaAtualizado);
    }

    //@Gambiarra em usar lista de produtos e não lista de CPentradaProdutos
    public void registraEntradaDosProdutos(BeanListProdutos lp) {
        if (lp.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.registroDeEntradaSemProdutos);
        } else {
            CPEntrada e = new CPEntrada();
            e.setDataEntrada(UtilData.getDataTimestamp());
            e = DaoEntrada.mergeII(e);
            for (CPProduto xProduto : lp.getProdutosManipulados()) {
                CPEntradaProduto cPEntradaProduto = produtosEntrando.get(xProduto.getId());
                DaoEntradaProduto.merge(cPEntradaProduto);
                List<CPAlertasQuantidadeProduto> alertas = DaoAlertasQuantidadeProduto.getPorProduto(xProduto);
                if (alertas!=null) {
                    for (CPAlertasQuantidadeProduto xAlerta : alertas) {
                        if (xAlerta.isVigente() && cPEntradaProduto.getQuantidade() + xProduto.getQuantidadeEmStoque() >= xProduto.getQuantidadeMinimaEmStoque()) {
                            xAlerta.setVigente(false);
                            DaoAlertasQuantidadeProduto.merge(xAlerta);
                        }
                    }
                }
            }
            lp.zeraManipulados();
            lp.atualizaProdutos();
            UtilMensagens.ok(UtilMensagens.registroDeEntradaSalvo);
        }

    }

    public void navegaNoWizard(long navega) {
        switch ((int) navega) {
            //em PRODUTOS indo para CONFIRMA
            case 0:
                stepWizr = "confirme";
                break;
            //em CONFIRMA indo para produtos
            case 1:
                stepWizr = "produtos";
                break;
            // em PRODUTOS indo para CONFIRMAR
        }
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStepWizr() {
        return stepWizr;
    }

    public void setStepWizr(String stepWizr) {
        this.stepWizr = stepWizr;
    }

    public int getNovaQuantidade() {
        return novaQuantidade;
    }

    public void setNovaQuantidade(int novaQuantidade) {
        this.novaQuantidade = novaQuantidade;
    }

    public HashMap<Long, CPEntradaProduto> getProdutosEntrando() {
        return produtosEntrando;
    }

    public void setProdutosEntrando(HashMap<Long, CPEntradaProduto> produtosEntrando) {
        this.produtosEntrando = produtosEntrando;
    }
}
