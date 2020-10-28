/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.interfaces;

import cp.CPPontoDeEstagio;
import cp.grafico.ABSGraficoSalvo;
import java.util.List;

/**
 *
 * @author Thiago
 */
public interface InterfaceDaoGraficoPontos<T> {
    
    public void merge(T objeto);

    public void mergeS(ABSGraficoSalvo grafico, List<CPPontoDeEstagio> p);

    public void delete(T objeto);

    public List<CPPontoDeEstagio> getPontos(ABSGraficoSalvo grafico);

    public List<T> getSaidaCustomPontos(ABSGraficoSalvo grafico);

    public void atualizaPontos(ABSGraficoSalvo grafico, List<CPPontoDeEstagio> pontos);
}
