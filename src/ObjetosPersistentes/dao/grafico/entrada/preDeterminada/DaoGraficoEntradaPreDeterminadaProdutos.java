/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.grafico.entrada.preDeterminada;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.entrada.custom.CPGraficoEntradaCustomProdutos;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminado;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminadoProdutos;
import dao.DaoGenerico;
import dao.estoque.DaoProduto;
import dao.grafico.interfaces.InterfaceDaoGraficoProdutos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago-Asus
 */
public class DaoGraficoEntradaPreDeterminadaProdutos implements InterfaceDaoGraficoProdutos<CPGraficoEntradaPreDeterminadoProdutos> {

    private static DaoGenerico<CPGraficoEntradaPreDeterminadoProdutos> daoECP = new DaoGenerico<>(CPGraficoEntradaPreDeterminadoProdutos.class);

    @Override
    public CPGraficoEntradaPreDeterminadoProdutos getByID(long id) {
        return daoECP.getById(id);
    }

    @Override
    public void merge(CPGraficoEntradaPreDeterminadoProdutos t) {
        daoECP.merge(t);
    }

    @Override
    public CPGraficoEntradaPreDeterminadoProdutos mergeII(CPGraficoEntradaPreDeterminadoProdutos t) {
        return (CPGraficoEntradaPreDeterminadoProdutos) daoECP.mergeII(t);
    }

    @Override
    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        for (CPProduto xProduto : produtos) {
            CPGraficoEntradaPreDeterminadoProdutos gep = new CPGraficoEntradaPreDeterminadoProdutos();
            gep.setGrafico(graficoSalvo);
            gep.setProduto(xProduto);
            daoECP.merge(gep);
        }
    }

    @Override
    public void deleta(CPGraficoEntradaPreDeterminadoProdutos t) {
        daoECP.delete(t);
    }

    @Override
    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo) {
        List<CPGraficoEntradaPreDeterminadoProdutos> entradaProdutos = getPorGrafico(graficoSalvo);
        List<CPProduto> res = new LinkedList<>();
        for (CPGraficoEntradaPreDeterminadoProdutos xEntradaCustomProdutos : entradaProdutos) {
            res.add(xEntradaCustomProdutos.getProduto());
        }
        return res;
    }

    @Override
    public List<CPGraficoEntradaPreDeterminadoProdutos> getPorProdutos(CPProduto produto) {
        return daoECP.buscaPersonalizada("produto_id", produto.getId(), 3);
    }
    
    @Override
    public List<CPGraficoEntradaPreDeterminadoProdutos> getPorGrafico(ABSGraficoSalvo graficoSalvo) {
        return daoECP.buscaPersonalizada("grafico_id", graficoSalvo.getId(), 3);
    }

    @Override
    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        List<Long> idAtuais = new LinkedList<>();
        List<Long> idAntigos = new LinkedList<>();
        List<Long> idProdutosParaSalvar = new LinkedList<>();
        List<Long> idProdutosParaDeletar = new LinkedList<>();
        List<CPGraficoEntradaPreDeterminadoProdutos> entradaProdutos = getPorGrafico(graficoSalvo);

        //carrega id's novos
        for (CPProduto xProduto : produtos) {
            idAtuais.add(xProduto.getId());
        }

        //carrega id's antigos
        for (CPGraficoEntradaPreDeterminadoProdutos xGrafSalvoProdutos : entradaProdutos) {
            idAntigos.add(xGrafSalvoProdutos.getProduto().getId());
        }

        //se id novo n está em antigo, salva novo
        for (Long id : idAtuais) {
            if (!idAntigos.contains(id)) {
                idProdutosParaSalvar.add(id);
            }
        }


        //se id antigo n está em novo, deleta antigo
        for (Long id : idAntigos) {
            if (!idAtuais.contains(id)) {
                idProdutosParaDeletar.add(id);
            }
        }

        //deletanto
        for (CPGraficoEntradaPreDeterminadoProdutos sp : entradaProdutos) {
            if (idProdutosParaDeletar.contains(sp.getProduto().getId())) {
                daoECP.delete(sp);
            }
        }


        if (idProdutosParaSalvar.isEmpty()) {
            DaoGraficoEntradaPreDeterminada.merge((CPGraficoEntradaPreDeterminado) graficoSalvo);
        } else {
            for (Long id : idProdutosParaSalvar) {
                CPGraficoEntradaPreDeterminadoProdutos c = new CPGraficoEntradaPreDeterminadoProdutos();
                c.setGrafico(graficoSalvo);
                c.setProduto(DaoProduto.getById(id));
                merge(c);
            }
        }
    }
}
