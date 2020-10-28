/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.grafico.interfaces;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import java.util.List;

/**
 *
 * @author Thiago-Asus
 */
public interface InterfaceDaoGraficoProdutos<T> {

    public void merge(T t);

    public T mergeII(T t);

    public void mergList(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos);

    public void deleta(T t);

    public List<CPProduto> getProdutos(ABSGraficoSalvo graficoSalvo);
    
    public List<T> getPorProdutos(CPProduto produto);

    public List<T> getPorGrafico(ABSGraficoSalvo graficoSalvo);

    public void atualizaProdutos(ABSGraficoSalvo graficoSalvo, List<CPProduto> produtos);
   
    public T getByID(long id);
}