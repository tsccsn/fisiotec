/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico;

import cp.estoque.CPProduto;
import java.io.Serializable;

/**
 *
 * @author Thiago-Asus
 */
public abstract class ABSGraficoSalvoProdutos implements Serializable {

    private static final long serialVersionUID = 1L;
    //CONSTRUTUTORES
    //ATRIBUTOS    
    private long id;
    private ABSGraficoSalvo grafico;
    private CPProduto produto;

    //GET'S
    protected long getId() {
        return id;
    }

    protected ABSGraficoSalvo getGrafico() {
        return grafico;
    }

    protected CPProduto getProduto() {
        return produto;
    }

    //SET'S
    protected void setId(long id) {
        this.id = id;
    }

    protected void setGrafico(ABSGraficoSalvo grafico) {
        this.grafico = grafico;
    }

    protected void setProduto(CPProduto produto) {
        this.produto = produto;
    }
}