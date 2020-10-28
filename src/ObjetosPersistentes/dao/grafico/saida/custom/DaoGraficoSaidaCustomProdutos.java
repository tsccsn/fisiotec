/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.saida.custom;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.saida.custom.CPGraficoSaidaCustom;
import cp.grafico.saida.custom.CPGraficoSaidaCustomProdutos;
import dao.DaoGenerico;
import dao.estoque.DaoProduto;
import dao.grafico.interfaces.InterfaceDaoGraficoProdutos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DaoGraficoSaidaCustomProdutos implements InterfaceDaoGraficoProdutos<CPGraficoSaidaCustomProdutos> {

    public static DaoGenerico<CPGraficoSaidaCustomProdutos> daoGSP = new DaoGenerico<>(CPGraficoSaidaCustomProdutos.class);

    @Override
    public CPGraficoSaidaCustomProdutos getByID(long id) {
        return daoGSP.getById(id);
    }

    @Override
    public void merge(CPGraficoSaidaCustomProdutos t) {
        daoGSP.merge(t);
    }

    @Override
    public CPGraficoSaidaCustomProdutos mergeII(CPGraficoSaidaCustomProdutos t) {
        return (CPGraficoSaidaCustomProdutos) daoGSP.mergeII(t);
    }

    @Override
    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        for (CPProduto pp : produtos) {
            CPGraficoSaidaCustomProdutos gsp = new CPGraficoSaidaCustomProdutos();
            gsp.setGrafico((CPGraficoSaidaCustom) graficoSalvo);
            gsp.setProduto(pp);
            merge(gsp);
        }
    }

    @Override
    public void deleta(CPGraficoSaidaCustomProdutos t) {
        daoGSP.delete(t);
    }

    @Override
    public List<CPGraficoSaidaCustomProdutos> getPorProdutos(CPProduto produto) {
        return daoGSP.buscaPersonalizada("produto_id", produto.getId(), 3);
    }

    
    @Override
    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo) {
        List<CPGraficoSaidaCustomProdutos> byGrafico = getPorGrafico(graficoSalvo);
        List<CPProduto> resultado = new LinkedList<>();
        for (CPGraficoSaidaCustomProdutos xSaidaCustomProdutos : byGrafico) {
            resultado.add(xSaidaCustomProdutos.getProduto());
        }
        return resultado;
    }

    @Override
    public List<CPGraficoSaidaCustomProdutos> getPorGrafico(ABSGraficoSalvo graficoSalvo) {
        return daoGSP.buscaPersonalizada("grafico_id", graficoSalvo.getId(), 3);
    }

    @Override
    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        List<Long> idProdutosAntigos = new LinkedList<>();
        List<Long> idProdutosEntrado = new LinkedList<>();
        List<Long> idProdutosParaSalvar = new LinkedList<>();
        List<Long> idProdutosParaDeletar = new LinkedList<>();
        List<CPGraficoSaidaCustomProdutos> listGSP = getPorGrafico(graficoSalvo);

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
        for (CPGraficoSaidaCustomProdutos sp : listGSP) {
            if (idProdutosParaDeletar.contains(sp.getProduto().getId())) {
                deleta(sp);
            }
        }

        for (Long id : idProdutosParaSalvar) {
            CPGraficoSaidaCustomProdutos c = new CPGraficoSaidaCustomProdutos();
            c.setGrafico((CPGraficoSaidaCustom) graficoSalvo);
            c.setProduto(DaoProduto.getById(id));
            merge(c);
        }
    }
}
