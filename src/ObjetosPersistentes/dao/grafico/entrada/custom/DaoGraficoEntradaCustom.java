/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.grafico.entrada.custom;

import cp.grafico.entrada.custom.CPGraficoEntradaCustom;
import cp.grafico.entrada.custom.CPGraficoEntradaCustomProdutos;
import dao.DaoGenerico;
import java.util.List;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago-Asus
 */
public class DaoGraficoEntradaCustom {
    
    private static DaoGenerico<CPGraficoEntradaCustom> dgeCustom = new DaoGenerico<>(CPGraficoEntradaCustom.class);
    
    public static void merge(CPGraficoEntradaCustom g) {
        g.setDataModificado(UtilData.getDataTimestamp());
        dgeCustom.merge(g);
    }
    
    public static CPGraficoEntradaCustom mergeII(CPGraficoEntradaCustom g) {
        g.setDataModificado(UtilData.getDataTimestamp());
        return (CPGraficoEntradaCustom) dgeCustom.mergeII(g);
    }
    
    public static void delete(CPGraficoEntradaCustom ge) {
        List<CPGraficoEntradaCustomProdutos> porGrafico = new DaoGraficoEntradaCustomProdutos().getPorGrafico(ge);
        for (CPGraficoEntradaCustomProdutos xGrafico : porGrafico) {
            new DaoGraficoEntradaCustomProdutos().deleta(xGrafico);
        }
        dgeCustom.delete(ge);
    }
    
    public static CPGraficoEntradaCustom getById(long id) {
        return (CPGraficoEntradaCustom) dgeCustom.getById(id);
    }
    
    public static CPGraficoEntradaCustom existeEsseNome(CPGraficoEntradaCustom grafEntradaSalvo) {
        return existeEsseNome(grafEntradaSalvo.getNome());
    }

    public static CPGraficoEntradaCustom existeEsseNome(String nome) {
        try {
            List buscaPersonalizada = dgeCustom.buscaPersonalizada("nome", nome, 3);
            if (buscaPersonalizada.isEmpty()) {
                return null;
            } else {
                return (CPGraficoEntradaCustom) buscaPersonalizada.get(0);
                
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    public static List<CPGraficoEntradaCustom> getAllOrderByDataSalvo() {
        return dgeCustom.getAll("datamodificado");
    }
}
