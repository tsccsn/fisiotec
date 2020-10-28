/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.entradaSaida.custom;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustom;
import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustomProdutos;
import dao.DaoGenerico;
import dao.estoque.DaoProduto;
import dao.grafico.interfaces.InterfaceDaoGraficoProdutos;
import java.util.LinkedList;
import java.util.List;
import utilidades.comparadores.OrdenaProdutos;

/**
 *
 * @author Thiago-Asus
 */
public class DaoGraficoEntradaSaidaCustomProdutos implements InterfaceDaoGraficoProdutos<CPGraficoEntradaSaidaCustomProdutos> {

    private static DaoGenerico<CPGraficoEntradaSaidaCustomProdutos> daoESP = new DaoGenerico<>(CPGraficoEntradaSaidaCustomProdutos.class);

    @Override
    public CPGraficoEntradaSaidaCustomProdutos getByID(long id) {
       return daoESP.getById(id);
    }

    @Override
    public void merge(CPGraficoEntradaSaidaCustomProdutos t) {
        daoESP.merge(t);
    }

    @Override
    public CPGraficoEntradaSaidaCustomProdutos mergeII(CPGraficoEntradaSaidaCustomProdutos t) {
        return (CPGraficoEntradaSaidaCustomProdutos) daoESP.mergeII(t);
    }

    @Override
    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
        for (CPProduto xProduto : produtos) {
            CPGraficoEntradaSaidaCustomProdutos gep = new CPGraficoEntradaSaidaCustomProdutos();
            gep.setGrafico(graficoSalvo);
            gep.setProduto(xProduto);
            daoESP.merge(gep);
        }
    }

    @Override
    public void deleta(CPGraficoEntradaSaidaCustomProdutos t) {
       daoESP.delete(t);
    }

    @Override
    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo) {
        List<CPGraficoEntradaSaidaCustomProdutos> entradaProdutos = getPorGrafico(graficoSalvo);
        List<CPProduto> res = new LinkedList<>();
        for (CPGraficoEntradaSaidaCustomProdutos xEntradaCustomProdutos : entradaProdutos) {
            res.add(xEntradaCustomProdutos.getProduto());
        }
        OrdenaProdutos.ordena(res);
        return res;
    }

    @Override
    public List<CPGraficoEntradaSaidaCustomProdutos> getPorGrafico(ABSGraficoSalvo graficoSalvo) {
        return daoESP.buscaPersonalizada("grafico_id", graficoSalvo.getId(), 3);
    }

    @Override
    public List<CPGraficoEntradaSaidaCustomProdutos> getPorProdutos(CPProduto produto) {
        return daoESP.buscaPersonalizada("produto_id", produto.getId(), 3);
    }

    @Override
    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos) {
         List<Long> idAtuais = new LinkedList<>();
        List<Long> idAntigos = new LinkedList<>();
        List<Long> idProdutosParaSalvar = new LinkedList<>();
        List<Long> idProdutosParaDeletar = new LinkedList<>();
        List<CPGraficoEntradaSaidaCustomProdutos> entradaProdutos = getPorGrafico(graficoSalvo);

        //carrega id's novos
        for (CPProduto xProduto : produtos) {
            idAtuais.add(xProduto.getId());
        }

        //carrega id's antigos
        for (CPGraficoEntradaSaidaCustomProdutos xGrafSalvoProdutos : entradaProdutos) {
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
        for (CPGraficoEntradaSaidaCustomProdutos sp : entradaProdutos) {
            if (idProdutosParaDeletar.contains(sp.getProduto().getId())) {
                daoESP.delete(sp);
            }
        }


        if (idProdutosParaSalvar.isEmpty()) {
            DaoGraficoEntradaSaidaCustom.merge((CPGraficoEntradaSaidaCustom) graficoSalvo);
        } else {
            for (Long id : idProdutosParaSalvar) {
                CPGraficoEntradaSaidaCustomProdutos c = new CPGraficoEntradaSaidaCustomProdutos();
                c.setGrafico(graficoSalvo);
                c.setProduto(DaoProduto.getById(id));
                merge(c);
            }
        }
    }


}
