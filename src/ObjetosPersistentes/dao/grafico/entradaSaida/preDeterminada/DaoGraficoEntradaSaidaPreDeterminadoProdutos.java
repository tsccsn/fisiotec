/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.entradaSaida.preDeterminada;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.entradasaida.preDeterminado.CPGraficoEntradaSaidaPreDeterminadoProdutos;
import dao.DaoGenerico;
import dao.estoque.DaoProduto;
import dao.grafico.interfaces.InterfaceDaoGraficoProdutos;
import java.util.LinkedList;
import java.util.List;
import utilidades.comparadores.OrdenaProdutos;

/**
 *
 * @author Thiago
 */
public class DaoGraficoEntradaSaidaPreDeterminadoProdutos implements InterfaceDaoGraficoProdutos<CPGraficoEntradaSaidaPreDeterminadoProdutos> {

    DaoGenerico<CPGraficoEntradaSaidaPreDeterminadoProdutos> dao = new DaoGenerico<>(CPGraficoEntradaSaidaPreDeterminadoProdutos.class);

    @Override
    public void merge(CPGraficoEntradaSaidaPreDeterminadoProdutos t) {
        dao.merge(t);
    }

    @Override
    public CPGraficoEntradaSaidaPreDeterminadoProdutos mergeII(CPGraficoEntradaSaidaPreDeterminadoProdutos t) {
        return mergeII(t);
    }

    @Override
    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        for (CPProduto pp : produtos) {
            CPGraficoEntradaSaidaPreDeterminadoProdutos gsp = new CPGraficoEntradaSaidaPreDeterminadoProdutos();
            gsp.setGrafico(graficoSalvo);
            gsp.setProduto(pp);
            merge(gsp);
        }
    }

    @Override
    public void deleta(CPGraficoEntradaSaidaPreDeterminadoProdutos t) {
        dao.delete(t);
    }

    @Override
    public List<CPGraficoEntradaSaidaPreDeterminadoProdutos> getPorProdutos(CPProduto produto) {
        return dao.buscaPersonalizada("produto_id", produto.getId(), 3);
    }

    
    
    @Override
    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo) {
        List<CPGraficoEntradaSaidaPreDeterminadoProdutos> byGrafico = getPorGrafico(graficoSalvo);
        List<CPProduto> resultado = new LinkedList<>();
        for (CPGraficoEntradaSaidaPreDeterminadoProdutos xSaidaCustomProdutos : byGrafico) {
            resultado.add(xSaidaCustomProdutos.getProduto());
        }
        OrdenaProdutos.ordena(resultado);
        return resultado;
    }

    @Override
    public List<CPGraficoEntradaSaidaPreDeterminadoProdutos> getPorGrafico(ABSGraficoSalvo graficoSalvo) {
        return dao.buscaPersonalizada("grafico_id", graficoSalvo.getId(), 3);
    }

    @Override
    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        List<Long> idProdutosAntigos = new LinkedList<>();
        List<Long> idProdutosEntrado = new LinkedList<>();
        List<Long> idProdutosParaSalvar = new LinkedList<>();
        List<Long> idProdutosParaDeletar = new LinkedList<>();
        List<CPGraficoEntradaSaidaPreDeterminadoProdutos> listGSP = getPorGrafico(graficoSalvo);

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
        for (CPGraficoEntradaSaidaPreDeterminadoProdutos sp : listGSP) {
            if (idProdutosParaDeletar.contains(sp.getProduto().getId())) {
                deleta(sp);
            }
        }

        for (Long id : idProdutosParaSalvar) {
            CPGraficoEntradaSaidaPreDeterminadoProdutos c = new CPGraficoEntradaSaidaPreDeterminadoProdutos();
            c.setGrafico(graficoSalvo);
            c.setProduto(DaoProduto.getById(id));
            merge(c);
        }
    }

    @Override
    public CPGraficoEntradaSaidaPreDeterminadoProdutos getByID(long id) {
        return dao.getById(id);
    }
}
