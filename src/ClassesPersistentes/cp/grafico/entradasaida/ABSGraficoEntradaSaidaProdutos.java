/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.grafico.entradasaida;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.ABSGraficoSalvoProdutos;

/**
 *
 * @author Thiago-Asus
 */
public class ABSGraficoEntradaSaidaProdutos extends ABSGraficoSalvoProdutos {

    @Override
    protected ABSGraficoSalvo getGrafico() {
        return super.getGrafico();
    }

    @Override
    protected long getId() {
        return super.getId();
    }

    @Override
    protected CPProduto getProduto() {
        return super.getProduto();
    }

    @Override
    protected void setGrafico(ABSGraficoSalvo grafico) {
        super.setGrafico(grafico);
    }

    @Override
    protected void setId(long id) {
        super.setId(id);
    }

    @Override
    protected void setProduto(CPProduto produto) {
        super.setProduto(produto);
    }
}
