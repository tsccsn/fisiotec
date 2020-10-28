/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.comparadores;

import cp.estoque.saida.CPSaidaProduto;
import java.util.Comparator;

/**
 *
 * @author Thiago
 */
public class OrdenaSaidaProduto implements Comparator<CPSaidaProduto> {

    @Override
    public int compare(CPSaidaProduto p1, CPSaidaProduto p2) {
        return p1.getProduto().toString().toLowerCase().compareTo(p2.getProduto().toString().toLowerCase());
    }
}
