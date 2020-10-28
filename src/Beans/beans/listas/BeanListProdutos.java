/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package beans.listas;

import cp.estoque.CPProduto;
import cp.estoque.entrada.CPEntradaProduto;
import dao.estoque.DaoProduto;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.comparadores.OrdenaProdutos;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "listaDeProdutos")
@ViewScoped
public class BeanListProdutos implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<CPProduto> produtos = DaoProduto.getAll("nome");
    private List<CPProduto> produtosManipulados = new LinkedList<>();
    private CPProduto produtoManipulado = new CPProduto();
    private int indexDoProduto = 0;
    private int indexDoProdutoSelecione = -1;
    private int indexDoProdutoManipulado = 0;


    public void atualizaProdutos() {
        produtos = DaoProduto.getAll("nome");
    }

    public List<String> autoComplete(String s) {
        List<String> resultado = new LinkedList<>();
        for (CPProduto p : produtos) {
            if (p.getNome().toLowerCase().startsWith(s.toLowerCase())) {
                resultado.add(p.getNome());
            }
        }
        return resultado;
    }

    public void anulaProdutoManipulado() {
        produtoManipulado = new CPProduto();
    }

    public CPProduto getProdutoManipuladoIndexado() {
        if (indexDoProdutoManipulado >= 0) {
            return produtosManipulados.get(indexDoProdutoManipulado);
        } else {
            return null;
        }
    }

    private void organiza() {
        OrdenaProdutos p = new OrdenaProdutos();
        Collections.sort(produtos, p);
        Collections.sort(produtosManipulados, p);
    }

    //MÉTODOS DE TROCA
    public void desce() {
        if (indexDoProduto >= 0) {
            produtosManipulados.add(produtos.get(indexDoProduto));
            produtos.remove(indexDoProduto);
            if (!produtos.isEmpty()) {
                indexDoProduto = 0;
            } else {
                indexDoProduto = -1;
            }
            organiza();
            UtilMensagens.info(UtilMensagens.produtoAddNaLista);
        } else {
            UtilMensagens.info(UtilMensagens.produtosTodosAdd);
        }
    }


    public void desce(CPProduto p) {
        produtosManipulados.add(p);
        produtos.remove(p);
        organiza();
    }

    public void sobe() {
        produtos.add(produtosManipulados.get(indexDoProduto));
        produtosManipulados.remove(indexDoProduto);
        organiza();
        UtilMensagens.info(UtilMensagens.produtoRemovido);
    }

    public void sobe(CPProduto p) {
        produtos.add(p);
        produtosManipulados.remove(p);
        organiza();
        UtilMensagens.info(UtilMensagens.produtoRemovido);
    }

    public void sobeManipulado() {
        produtos.add(produtosManipulados.get(indexDoProdutoManipulado));
        produtosManipulados.remove(indexDoProdutoManipulado);
        organiza();
    }
    //FIM MÉTODOS DE TROCA

    public void carregaManipulados(List<CPProduto> p) {
        for (CPProduto cPProduto : p) {
            addProdutoNaListProdutoManip(cPProduto);
        }
    }

    public void zeraManipulados() {
        produtosManipulados = new LinkedList<>();
    }

    public void addProdutoNaListProdutoManip(CPProduto produto) {
        long id = produto.getId();
        int pos = 0;
        for (CPProduto p : produtos) {
            if (p.getId() == id) {
                pos = produtos.indexOf(p);
            }
        }
        produtos.remove(pos);
        produtosManipulados.add(produto);
        organiza();
    }

    public void zeraIndex() {
        indexDoProduto = 0;
    }

    public void saiDeProdParaProdManip() {
        addProdIndexNaListManip();
        removeProdIndexDaListProd();
        zeraIndex();
        organiza();
    }

    public void atualizaProdutoNaLista(CPProduto p) {
        produtos.remove(indexDoProduto);
        produtos.add(p);
        organiza();
    }

    public CPProduto getProdutoIndexado() {
        if (indexDoProduto >= 0) {
            return produtos.get(indexDoProduto);
        } else {
            return null;
        }
    }

    public void addProdIndexNaListManip() {
        produtosManipulados.add(produtos.get(indexDoProduto));
    }

    public void removeDeManipuladoEAddEmProdutos(CPProduto p) {
        produtosManipulados.remove(p);
        produtos.add(p);
    }

    public void removeProdIndexDaListManip() {
        produtosManipulados.remove(produtos.get(indexDoProduto));
    }

    public void removeProdIndexDaListProd() {
        produtos.remove(indexDoProduto);
        organiza();
        if (!produtos.isEmpty()) {
            indexDoProduto = 0;
        } else {
            indexDoProduto = -1;
        }
    }

    public void addProdutoNaLista(CPProduto p) {
        produtos.add(p);
        Collections.sort(produtos, new OrdenaProdutos());
    }

    public void removeProdutoDaLista(CPProduto p) {
        produtos.remove(p);
    }

    public void addProdutoNaListaManipulada(CPProduto p) {
        produtosManipulados.add(p);
        Collections.sort(produtosManipulados, new OrdenaProdutos());
    }

    public void removeProdutoDaListaManipulada(CPProduto p) {
        produtosManipulados.remove(p);
    }

    public String toString() {
        System.out.println("----------Produtos----------");
        for (CPProduto p : produtos) {
            System.out.println(p.toString());
        }
        System.out.println("----------Produtos Manipulados----------");
        for (CPProduto p : produtosManipulados) {
            System.out.println(p.toString());
        }
        return null;
    }

    //get e set
    public List<CPProduto> getProdutos() {
        organiza();
        return produtos;
    }

    public List<CPProduto> getProdutosManipulados() {
        OrdenaProdutos op = new OrdenaProdutos();
        Collections.sort(produtosManipulados, op);
        return produtosManipulados;
    }

    public List<Long> getIdsProdutosManipulados() {
        List<Long> res = new LinkedList<Long>();
        for (CPProduto p : produtosManipulados) {
            res.add(p.getId());
        }
        return res;
    }

    public int getIndexDoProduto() {
        return indexDoProduto;
    }

    public void setIndexDoProduto(int indexDoProduto) {
        this.indexDoProduto = indexDoProduto;
    }

    public CPProduto getProdutoManipulado() {
        return produtoManipulado;
    }

    public void setProdutoManipulado(CPProduto produtoManipulado) {
        this.produtoManipulado = new CPProduto(produtoManipulado);
    }

    public void setProdutosManipulados(List<CPProduto> produtosManipulados) {
        this.produtos = DaoProduto.getAll("nome");
        this.produtosManipulados = new LinkedList<>();
        carregaManipulados(produtosManipulados);
    }

    public void setIndexDoProdutoSelecione(int indexDoProdutoSelecione) {
        this.indexDoProdutoSelecione = indexDoProdutoSelecione;
    }

    public int getIndexDoProdutoSelecione() {
        return indexDoProdutoSelecione;
    }

    public int getIndexDoProdutoManipulado() {
        return indexDoProdutoManipulado;
    }

    public void setIndexDoProdutoManipulado(int indexDoProdutoManipulado) {
        this.indexDoProdutoManipulado = indexDoProdutoManipulado;
    }
}
