/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import cp.estoque.CPProduto;

/**
 *
 * @author Thiago
 */
public class UtilMensagensGraficos {
    //GRAFICOS
    public static String semMovimentacaoNaPrimeiraData = "O produto P não foi movimentado na data de D, "
            + "por isso lhe foi atribuido a valor (V) da ultima movimentação antes do periodo selecionado, que foi na data de T";
    
    public static String nuncaMovimentado = "O produto P nunca foi movimentado, foi lhe atribuido zero em todas as datas";
    
    public static String replicaUltimaData = "O produto P não foi movimentado na data de D, "
            + "por isso lhe foi atribuido a valor (V) da movimentação anterior que foi na data de T";
    //FIM GRAFICOS

    public static String getSemMovimentacaoNaPrimeiraData(CPProduto p, String dataSemMovi, String valor, String dataAtribuida) {
        return semMovimentacaoNaPrimeiraData.replace("P", p.toString()).replace("D", dataSemMovi).replace("V", valor).replace("T", dataAtribuida);
    }
    
    public static String getSemMovimentacaoEmTerceira(CPProduto p, String dataSemMovi, String valor, String dataAtribuida) {
        return replicaUltimaData.replace("P", p.toString()).replace("D", dataSemMovi).replace("V", valor).replace("T", dataAtribuida);
    }
    
    public static String getNuncaMovimentado(CPProduto p ){
        return nuncaMovimentado.replace("P", p.toString());
    }
}