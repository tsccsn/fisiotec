/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.entradaSaida.custom;

import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustom;
import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustomProdutos;
import dao.DaoGenerico;
import java.util.List;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago-Asus
 */
public class DaoGraficoEntradaSaidaCustom {
    private static DaoGenerico<CPGraficoEntradaSaidaCustom> daoES = new DaoGenerico<>(CPGraficoEntradaSaidaCustom.class);
    
    public static void merge(CPGraficoEntradaSaidaCustom saidaCustom){
        daoES.merge(saidaCustom);
    }
    
    public static CPGraficoEntradaSaidaCustom mergeII(CPGraficoEntradaSaidaCustom saidaCustom){
        saidaCustom.setDataModificado(UtilData.getDataTimestamp());
        return (CPGraficoEntradaSaidaCustom) daoES.mergeII(saidaCustom);
    }
    
    public static void deleta(CPGraficoEntradaSaidaCustom saidaCustom){
        List<CPGraficoEntradaSaidaCustomProdutos> porGrafico = new DaoGraficoEntradaSaidaCustomProdutos().getPorGrafico(saidaCustom);
        for (CPGraficoEntradaSaidaCustomProdutos xGrafico : porGrafico) {
            new DaoGraficoEntradaSaidaCustomProdutos().deleta(xGrafico);
        }
        daoES.delete(saidaCustom);
    }
    
    public List<CPGraficoEntradaSaidaCustom> getAll(){
        return daoES.getAll();
    }
    public static List<CPGraficoEntradaSaidaCustom> getAllOrderByDataSalvo() {
        return daoES.getAll("datamodificado");
    }
     public static CPGraficoEntradaSaidaCustom existeEsseNome(String nome) {
        try {
            List buscaPersonalizada = daoES.buscaPersonalizada("nome", nome, 3);
            if (buscaPersonalizada.isEmpty()) {
                return null;
            } else {
                return (CPGraficoEntradaSaidaCustom) buscaPersonalizada.get(0);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
    
}
