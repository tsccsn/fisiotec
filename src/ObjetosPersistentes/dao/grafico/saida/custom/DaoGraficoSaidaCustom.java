/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.saida.custom;

import cp.grafico.saida.ABSGraficoSaidaSalvo;
import cp.grafico.saida.custom.CPGraficoSaidaCustom;
import dao.DaoGenerico;
import java.util.List;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
public class DaoGraficoSaidaCustom {

    private static DaoGenerico<CPGraficoSaidaCustom> daoGSP = new DaoGenerico<>(CPGraficoSaidaCustom.class);

    public static CPGraficoSaidaCustom existeEsseNome(CPGraficoSaidaCustom saidaCustom) {
        return existeEsseNome(saidaCustom.getNome());
    }

    public static CPGraficoSaidaCustom existeEsseNome(String s) {
        try {
            List buscaPersonalizada = daoGSP.buscaPersonalizada("nome", s, 3);
            if (buscaPersonalizada.isEmpty()) {
                return null;
            } else {
                return (CPGraficoSaidaCustom) buscaPersonalizada.get(0);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static CPGraficoSaidaCustom mergeII(CPGraficoSaidaCustom saidaCustom) {
        saidaCustom.setDataModificado(UtilData.getDataTimestamp());
        CPGraficoSaidaCustom mergeII = (CPGraficoSaidaCustom) daoGSP.mergeII(saidaCustom);
        return mergeII;
    }

    public static void merge(CPGraficoSaidaCustom objeto) {
        daoGSP.merge(objeto);
    }

    public static void delete(CPGraficoSaidaCustom objeto) {
        daoGSP.delete(objeto);
    }

    public static CPGraficoSaidaCustom getById(long id) {
        return (CPGraficoSaidaCustom) daoGSP.getById(id);
    }

    public static CPGraficoSaidaCustom getByNome(String nome) {
        return (CPGraficoSaidaCustom) daoGSP.buscaPersonalizada("nome", nome, 3).get(0);
    }

    public static List<CPGraficoSaidaCustom> getAllOrderByDataSalvo() {
        return daoGSP.getAll("datamodificado");
    }
}
