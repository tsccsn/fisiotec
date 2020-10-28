/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.entradaSaida.preDeterminada;

import cp.grafico.entradasaida.preDeterminado.CPGraficoEntradaSaidaPreDeterminado;
import cp.grafico.entradasaida.preDeterminado.CPGraficoEntradaSaidaPreDeterminadoProdutos;
import dao.DaoGenerico;
import java.util.List;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
public class DaoGraficoEntradaSaidaPreDeterminado {

    private static DaoGenerico<CPGraficoEntradaSaidaPreDeterminado> daoES = new DaoGenerico<>(CPGraficoEntradaSaidaPreDeterminado.class);

    public static void merge(CPGraficoEntradaSaidaPreDeterminado saidaCustom) {
        daoES.merge(saidaCustom);
    }

    public static CPGraficoEntradaSaidaPreDeterminado mergeII(CPGraficoEntradaSaidaPreDeterminado saidaCustom) {
        saidaCustom.setDataModificado(UtilData.getDataTimestamp());
        return (CPGraficoEntradaSaidaPreDeterminado) daoES.mergeII(saidaCustom);
    }

    public static void deleta(CPGraficoEntradaSaidaPreDeterminado saidaCustom) {
        List<CPGraficoEntradaSaidaPreDeterminadoProdutos> porGrafico = new DaoGraficoEntradaSaidaPreDeterminadoProdutos().getPorGrafico(saidaCustom);
        for (CPGraficoEntradaSaidaPreDeterminadoProdutos xGrafico : porGrafico) {
            new DaoGraficoEntradaSaidaPreDeterminadoProdutos().deleta(xGrafico);
        }
        daoES.delete(saidaCustom);
    }

    public List<CPGraficoEntradaSaidaPreDeterminado> getAll() {
        return daoES.getAll();
    }
    public static List<CPGraficoEntradaSaidaPreDeterminado> getAllOrderByDataSalvo() {
        return daoES.getAll("datamodificado");
    }
    public static CPGraficoEntradaSaidaPreDeterminado existeEsseNome(String nome) {
        try {
            List buscaPersonalizada = daoES.buscaPersonalizada("nome", nome, 3);
            if (buscaPersonalizada.isEmpty()) {
                return null;
            } else {
                return (CPGraficoEntradaSaidaPreDeterminado) buscaPersonalizada.get(0);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
}
