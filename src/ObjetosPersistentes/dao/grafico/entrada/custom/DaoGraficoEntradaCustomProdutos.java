/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.grafico.entrada.custom;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.entrada.custom.CPGraficoEntradaCustom;
import cp.grafico.entrada.custom.CPGraficoEntradaCustomProdutos;
import dao.DaoGenerico;
import dao.estoque.DaoProduto;
import dao.grafico.interfaces.InterfaceDaoGraficoProdutos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago-Asus
 */
public class DaoGraficoEntradaCustomProdutos implements InterfaceDaoGraficoProdutos<CPGraficoEntradaCustomProdutos> {

    private static DaoGenerico<CPGraficoEntradaCustomProdutos> daoECP = new DaoGenerico<>(CPGraficoEntradaCustomProdutos.class);

    @Override
    public CPGraficoEntradaCustomProdutos getByID(long id) {
        return daoECP.getById(id);
    }
    
    @Override
    public void merge(CPGraficoEntradaCustomProdutos t) {
        daoECP.merge(t);
    }

    @Override
    public CPGraficoEntradaCustomProdutos mergeII(CPGraficoEntradaCustomProdutos t) {
        return (CPGraficoEntradaCustomProdutos) daoECP.mergeII(t);
    }

    @Override
    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        for (CPProduto xProduto : produtos) {
            CPGraficoEntradaCustomProdutos gep = new CPGraficoEntradaCustomProdutos();
            gep.setGrafico(graficoSalvo);
            gep.setProduto(xProduto);
            daoECP.merge(gep);
        }
    }

    @Override
    public void deleta(CPGraficoEntradaCustomProdutos t) {
        daoECP.delete(t);
    }

    @Override
    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo) {
        List<CPGraficoEntradaCustomProdutos> entradaProdutos = getPorGrafico(graficoSalvo);
        List<CPProduto> res = new LinkedList<>();
        for (CPGraficoEntradaCustomProdutos xEntradaCustomProdutos : entradaProdutos) {
            res.add(xEntradaCustomProdutos.getProduto());
        }
        return res;
    }

    @Override
    public List<CPGraficoEntradaCustomProdutos> getPorGrafico(ABSGraficoSalvo graficoSalvo) {
        return daoECP.buscaPersonalizada("grafico_id", graficoSalvo.getId(), 3);
    }

    @Override
    public List<CPGraficoEntradaCustomProdutos> getPorProdutos(CPProduto produto) {
        return daoECP.buscaPersonalizada("produto_id", produto.getId(), 3);
    }
    
    @Override
    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        List<Long> idAtuais = new LinkedList<>();
        List<Long> idAntigos = new LinkedList<>();
        List<Long> idProdutosParaSalvar = new LinkedList<>();
        List<Long> idProdutosParaDeletar = new LinkedList<>();
        List<CPGraficoEntradaCustomProdutos> entradaProdutos = getPorGrafico(graficoSalvo);

        //carrega id's novos
        for (CPProduto xProduto : produtos) {
            idAtuais.add(xProduto.getId());
        }

        //carrega id's antigos
        for (CPGraficoEntradaCustomProdutos xGrafSalvoProdutos : entradaProdutos) {
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
        for (CPGraficoEntradaCustomProdutos sp : entradaProdutos) {
            if (idProdutosParaDeletar.contains(sp.getProduto().getId())) {
                daoECP.delete(sp);
            }
        }


        if (idProdutosParaSalvar.isEmpty()) {
            DaoGraficoEntradaCustom.merge((CPGraficoEntradaCustom) graficoSalvo);
        } else {
            for (Long id : idProdutosParaSalvar) {
                CPGraficoEntradaCustomProdutos c = new CPGraficoEntradaCustomProdutos();
                c.setGrafico(graficoSalvo);
                c.setProduto(DaoProduto.getById(id));
                merge(c);
            }
        }
    }

    
}
