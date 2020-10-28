/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.grafico.entrada.preDeterminada;

import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminado;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminadoProdutos;
import dao.DaoGenerico;
import java.util.List;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago-Asus
 */
public class DaoGraficoEntradaPreDeterminada {

    private static DaoGenerico<CPGraficoEntradaPreDeterminado> dgeCustom = new DaoGenerico<>(CPGraficoEntradaPreDeterminado.class);

    public static void merge(CPGraficoEntradaPreDeterminado g) {
        g.setDataModificado(UtilData.getDataTimestamp());
        dgeCustom.merge(g);
    }

    public static CPGraficoEntradaPreDeterminado mergeII(CPGraficoEntradaPreDeterminado g) {
        g.setDataModificado(UtilData.getDataTimestamp());
        return (CPGraficoEntradaPreDeterminado) dgeCustom.mergeII(g);
    }

    public static void delete(CPGraficoEntradaPreDeterminado ge) {
        List<CPGraficoEntradaPreDeterminadoProdutos> porGrafico = new DaoGraficoEntradaPreDeterminadaProdutos().getPorGrafico(ge);
        for (CPGraficoEntradaPreDeterminadoProdutos xGrafico : porGrafico) {
            new DaoGraficoEntradaPreDeterminadaProdutos().deleta(xGrafico);
        }
        dgeCustom.delete(ge);
    }

    public static CPGraficoEntradaPreDeterminado getById(long id) {
        return (CPGraficoEntradaPreDeterminado) dgeCustom.getById(id);
    }

    public static CPGraficoEntradaPreDeterminado existeEsseNome(CPGraficoEntradaPreDeterminado grafEntradaSalvo) {
        return existeEsseNome(grafEntradaSalvo.getNome());
    }

    public static CPGraficoEntradaPreDeterminado existeEsseNome(String nome) {
        try {
            List buscaPersonalizada = dgeCustom.buscaPersonalizada("nome", nome, 3);
            if (buscaPersonalizada.isEmpty()) {
                return null;
            } else {
                return (CPGraficoEntradaPreDeterminado) buscaPersonalizada.get(0);

            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static List<CPGraficoEntradaPreDeterminado> getAllOrderByDataSalvo() {
        return dgeCustom.getAll("datamodificado");
    }
}
