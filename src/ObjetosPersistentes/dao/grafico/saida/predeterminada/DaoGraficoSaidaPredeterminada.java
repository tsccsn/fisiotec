/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.saida.predeterminada;

import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminado;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminadoPontos;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminadoProdutos;
import dao.DaoGenerico;
import java.util.List;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
public class DaoGraficoSaidaPredeterminada {

    private static DaoGenerico<CPGraficoSaidaPreDeterminado> daoGSP = new DaoGenerico<>(CPGraficoSaidaPreDeterminado.class);

    public static CPGraficoSaidaPreDeterminado existeEsseNome(CPGraficoSaidaPreDeterminado saidaCustom) {
        return existeEsseNome(saidaCustom.getNome());
    }
    public static CPGraficoSaidaPreDeterminado existeEsseNome(String nome) {
        try {
            List buscaPersonalizada = daoGSP.buscaPersonalizada("nome", nome, 3);
            if (buscaPersonalizada.isEmpty()) {
                return null;
            } else {
                return (CPGraficoSaidaPreDeterminado) buscaPersonalizada.get(0);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static CPGraficoSaidaPreDeterminado mergeII(CPGraficoSaidaPreDeterminado saidaCustom) {
        saidaCustom.setDataModificado(UtilData.getDataTimestamp());
        return (CPGraficoSaidaPreDeterminado) daoGSP.mergeII(saidaCustom);
    }

    public static void merge(CPGraficoSaidaPreDeterminado objeto) {
        daoGSP.merge(objeto);
    }

    public static void delete(CPGraficoSaidaPreDeterminado objeto) {
        List<CPGraficoSaidaPreDeterminadoProdutos> porGrafico = new DaoGraficoSaidaPredeterminadaProdutos().getPorGrafico(objeto);
        for (CPGraficoSaidaPreDeterminadoProdutos xSP : porGrafico) {
            new DaoGraficoSaidaPredeterminadaProdutos().deleta(xSP);
        }
        List<CPGraficoSaidaPreDeterminadoPontos> saidaCustomPontos = new DaoGraficoSaidaPredeterminadaPontos().getSaidaCustomPontos(objeto);
        for (CPGraficoSaidaPreDeterminadoPontos xSP : saidaCustomPontos) {
            new DaoGraficoSaidaPredeterminadaPontos().delete(xSP);
        }
        daoGSP.delete(objeto);
    }

    public static CPGraficoSaidaPreDeterminado getById(long id) {
        return (CPGraficoSaidaPreDeterminado) daoGSP.getById(id);
    }

    public static CPGraficoSaidaPreDeterminado getByNome(String nome) {
        return (CPGraficoSaidaPreDeterminado) daoGSP.buscaPersonalizada("nome", nome, 3).get(0);
    }

    public static List<CPGraficoSaidaPreDeterminado> getAllOrderByDataSalvo() {
        return daoGSP.getAll("datamodificado");
    }
}
