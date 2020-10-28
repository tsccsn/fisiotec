/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.saida.predeterminada;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminado;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminadoProdutos;
import dao.DaoGenerico;
import dao.estoque.DaoProduto;
import dao.grafico.interfaces.InterfaceDaoGraficoProdutos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DaoGraficoSaidaPredeterminadaProdutos implements InterfaceDaoGraficoProdutos<CPGraficoSaidaPreDeterminadoProdutos> {

    public static DaoGenerico<CPGraficoSaidaPreDeterminadoProdutos> daoGSP = new DaoGenerico<>(CPGraficoSaidaPreDeterminadoProdutos.class);

    @Override
    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        List<Long> idProdutosAntigos = new LinkedList<>();
        List<Long> idProdutosEntrado = new LinkedList<>();
        List<Long> idProdutosParaSalvar = new LinkedList<>();
        List<Long> idProdutosParaDeletar = new LinkedList<>();
        List<CPGraficoSaidaPreDeterminadoProdutos> listGSP = getPorGrafico(graficoSalvo);

        //carrega id's novos
        for (CPProduto p : produtos) {
            idProdutosEntrado.add(p.getId());
        }

        //carrega id's antigos
        for (CPProduto p : getProdutos(graficoSalvo)) {
            idProdutosAntigos.add(p.getId());
        }

        //se id novo n está em antigo, salva novo
        for (Long id : idProdutosEntrado) {
            if (!idProdutosAntigos.contains(id)) {
                idProdutosParaSalvar.add(id);
            }
        }

        //se id antigo n está em novo, deleta antigo
        for (Long id : idProdutosAntigos) {
            if (!idProdutosEntrado.contains(id)) {
                idProdutosParaDeletar.add(id);
            }
        }

        //deletanto
        for (CPGraficoSaidaPreDeterminadoProdutos sp : listGSP) {
            if (idProdutosParaDeletar.contains(sp.getProduto().getId())) {
                deleta(sp);
            }
        }

        for (Long id : idProdutosParaSalvar) {
            CPGraficoSaidaPreDeterminadoProdutos c = new CPGraficoSaidaPreDeterminadoProdutos();
            c.setGrafico((CPGraficoSaidaPreDeterminado) graficoSalvo);
            c.setProduto(DaoProduto.getById(id));
            merge(c);
        }
    }

    @Override
    public void deleta(CPGraficoSaidaPreDeterminadoProdutos t) {
        daoGSP.delete(t);
    }

    @Override
    public List<CPGraficoSaidaPreDeterminadoProdutos> getPorProdutos(CPProduto produto) {
        return daoGSP.buscaPersonalizada("produto_id", produto.getId(), 3);
    }
    
    @Override
    public List<CPGraficoSaidaPreDeterminadoProdutos> getPorGrafico(ABSGraficoSalvo graficoSalvo) {
        return daoGSP.buscaPersonalizada("grafico_id", graficoSalvo.getId(), 3);
    }

    @Override
    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo) {
        List<CPGraficoSaidaPreDeterminadoProdutos> byGrafico = getPorGrafico(graficoSalvo);
        List<CPProduto> resultado = new LinkedList<>();
        for (CPGraficoSaidaPreDeterminadoProdutos xSaidaCustomProdutos : byGrafico) {
            resultado.add(xSaidaCustomProdutos.getProduto());
        }
        return resultado;
    }

    @Override
    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        for (CPProduto pp : produtos) {
            CPGraficoSaidaPreDeterminadoProdutos gsp = new CPGraficoSaidaPreDeterminadoProdutos();
            gsp.setGrafico((CPGraficoSaidaPreDeterminado) graficoSalvo);
            gsp.setProduto(pp);
            merge(gsp);
        }
    }

    @Override
    public CPGraficoSaidaPreDeterminadoProdutos mergeII(CPGraficoSaidaPreDeterminadoProdutos t) {
        return (CPGraficoSaidaPreDeterminadoProdutos) daoGSP.mergeII(t);
    }

    @Override
    public void merge(CPGraficoSaidaPreDeterminadoProdutos t) {
        daoGSP.merge(t);
    }

    @Override
    public CPGraficoSaidaPreDeterminadoProdutos getByID(long id) {
        return daoGSP.getById(id);
    }
}
