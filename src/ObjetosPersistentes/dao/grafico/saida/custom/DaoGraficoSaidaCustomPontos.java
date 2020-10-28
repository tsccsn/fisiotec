/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.saida.custom;

import cp.CPPontoDeEstagio;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.saida.ABSGraficoSaidaSalvo;
import cp.grafico.saida.custom.CPGraficoSaidaCustom;
import cp.grafico.saida.custom.CPGraficoSaidaCustomPontos;
import dao.DaoGenerico;
import dao.DaoPontoDeEstagio;
import dao.grafico.interfaces.InterfaceDaoGraficoPontos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DaoGraficoSaidaCustomPontos implements InterfaceDaoGraficoPontos<CPGraficoSaidaCustomPontos> {

    public static DaoGenerico<CPGraficoSaidaCustomPontos> daoGSP = new DaoGenerico<>(CPGraficoSaidaCustomPontos.class);

    @Override
    public void merge(CPGraficoSaidaCustomPontos objeto) {
        daoGSP.merge(objeto);
    }

    @Override
    public void mergeS(ABSGraficoSalvo grafico, List<CPPontoDeEstagio> p) {
        for (CPPontoDeEstagio pp : p) {
            CPGraficoSaidaCustomPontos gsp = new CPGraficoSaidaCustomPontos();
            gsp.setGrafico((ABSGraficoSaidaSalvo) grafico);
            gsp.setPonto(pp);
            merge(gsp);
        }
    }

    @Override
    public void delete(CPGraficoSaidaCustomPontos objeto) {
        daoGSP.delete(objeto);
    }

    @Override
    public List<CPPontoDeEstagio> getPontos(ABSGraficoSalvo grafico) {
        List<CPGraficoSaidaCustomPontos> buscaPersonalizada = daoGSP.buscaPersonalizada("grafico_id", grafico.getId(), 3);
        List<CPPontoDeEstagio> pontos = new LinkedList<>();
        for (CPGraficoSaidaCustomPontos xSaidaCustomPontos : buscaPersonalizada) {
            pontos.add(xSaidaCustomPontos.getPonto());
        }
        return pontos;
    }

    @Override
    public List<CPGraficoSaidaCustomPontos> getSaidaCustomPontos(ABSGraficoSalvo grafico) {
        return daoGSP.buscaPersonalizada("grafico_id", grafico.getId(), 3);
    }

    @Override
    public void atualizaPontos(ABSGraficoSalvo grafico, List<CPPontoDeEstagio> pontos) {
        List<Long> idPontosAntigos = new LinkedList<>();
        List<Long> idPontosEntrado = new LinkedList<>();
        List<Long> idPontosParaSalvar = new LinkedList<>();
        List<Long> idPontosParaDeletar = new LinkedList<>();
        List<CPGraficoSaidaCustomPontos> listaGraficosSaidaCustomPontos = getSaidaCustomPontos(grafico);

        //carrega id's novos
        for (CPPontoDeEstagio p : pontos) {
            idPontosEntrado.add(p.getId());
        }

        //carrega id's antigos
        for (CPPontoDeEstagio p : getPontos(grafico)) {
            idPontosAntigos.add(p.getId());
        }

        //se id novo n está em antigo, salva novo
        for (Long id : idPontosEntrado) {
            if (!idPontosAntigos.contains(id)) {
                idPontosParaSalvar.add(id);
            }
        }

        //se id antigo n está em novo, deleta antigo
        for (Long id : idPontosAntigos) {
            if (!idPontosEntrado.contains(id)) {
                idPontosParaDeletar.add(id);
            }
        }
        System.out.println("deletar id's:");
        for (long i : idPontosParaDeletar) {
            System.out.println("--" + i);
        }

        //deletanto
        for (CPGraficoSaidaCustomPontos sp : listaGraficosSaidaCustomPontos) {
            if (idPontosParaDeletar.contains(sp.getPonto().getId())) {
                delete(sp);
            }
        }
        if (idPontosParaSalvar.isEmpty()) {
            DaoGraficoSaidaCustom.merge((CPGraficoSaidaCustom) grafico);
        } else {
            //salvando
            for (Long id : idPontosParaSalvar) {
                CPGraficoSaidaCustomPontos c = new CPGraficoSaidaCustomPontos();
                c.setGrafico((ABSGraficoSaidaSalvo) grafico);
                c.setPonto(DaoPontoDeEstagio.getById(id));
                merge(c);
            }
        }
    }
}
