/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.saida.predeterminada;

import cp.CPPontoDeEstagio;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.saida.ABSGraficoSaidaSalvo;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminado;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminadoPontos;
import dao.DaoGenerico;
import dao.DaoPontoDeEstagio;
import dao.grafico.interfaces.InterfaceDaoGraficoPontos;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DaoGraficoSaidaPredeterminadaPontos implements InterfaceDaoGraficoPontos<CPGraficoSaidaPreDeterminadoPontos> {

    public static DaoGenerico<CPGraficoSaidaPreDeterminadoPontos> daoGSP = new DaoGenerico<>(CPGraficoSaidaPreDeterminadoPontos.class);

    @Override
    public void merge(CPGraficoSaidaPreDeterminadoPontos objeto) {
        daoGSP.merge(objeto);
    }

    @Override
    public void mergeS(ABSGraficoSalvo grafico, List<CPPontoDeEstagio> p) {
        for (CPPontoDeEstagio pp : p) {
            CPGraficoSaidaPreDeterminadoPontos gsp = new CPGraficoSaidaPreDeterminadoPontos();
            gsp.setGrafico((ABSGraficoSaidaSalvo) grafico);
            gsp.setPonto(pp);
            merge(gsp);
        }
    }

    @Override
    public void delete(CPGraficoSaidaPreDeterminadoPontos objeto) {
        daoGSP.delete(objeto);
    }

    @Override
    public List<CPPontoDeEstagio> getPontos(ABSGraficoSalvo grafico) {
        List<CPGraficoSaidaPreDeterminadoPontos> buscaPersonalizada = daoGSP.buscaPersonalizada("grafico_id", grafico.getId(), 3);
        List<CPPontoDeEstagio> pontos = new LinkedList<>();
        for (CPGraficoSaidaPreDeterminadoPontos xSaidaCustomPontos : buscaPersonalizada) {
            pontos.add(xSaidaCustomPontos.getPonto());
        }
        return pontos;
    }

    @Override
    public List<CPGraficoSaidaPreDeterminadoPontos> getSaidaCustomPontos(ABSGraficoSalvo grafico) {
        return daoGSP.buscaPersonalizada("grafico_id", grafico.getId(), 3);
    }

    @Override
    public void atualizaPontos(ABSGraficoSalvo grafico, List<CPPontoDeEstagio> pontos) {
        List<Long> idPontosAntigos = new LinkedList<>();
        List<Long> idPontosEntrado = new LinkedList<>();
        List<Long> idPontosParaSalvar = new LinkedList<>();
        List<Long> idPontosParaDeletar = new LinkedList<>();
        List<CPGraficoSaidaPreDeterminadoPontos> listaGraficosSaidaCustomPontos = getSaidaCustomPontos(grafico);

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
        for (long i : idPontosParaDeletar) {
            System.out.println("--" + i);
        }

        //deletanto
        for (CPGraficoSaidaPreDeterminadoPontos sp : listaGraficosSaidaCustomPontos) {
            if (idPontosParaDeletar.contains(sp.getPonto().getId())) {
                delete(sp);
            }
        }
        if (idPontosParaSalvar.isEmpty()) {
            DaoGraficoSaidaPredeterminada.merge((CPGraficoSaidaPreDeterminado)grafico);
        } else {
            //salvando
            for (Long id : idPontosParaSalvar) {
                CPGraficoSaidaPreDeterminadoPontos c = new CPGraficoSaidaPreDeterminadoPontos();
                c.setGrafico((ABSGraficoSaidaSalvo)grafico);
                c.setPonto(DaoPontoDeEstagio.getById(id));
                merge(c);
            }
        }
    }


    

}
