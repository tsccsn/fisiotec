/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.comparadores;

import cp.estoque.entrada.CPEntradaProduto;
import java.util.Comparator;

/**
 *
 * @author Thiago-Asus
 */
public class OrdenaEntradaProduto implements Comparator<CPEntradaProduto>{
    
    @Override
    public int compare(CPEntradaProduto p1, CPEntradaProduto p2) {
        return p1.getProduto().toString().toLowerCase().compareTo(p2.getProduto().toString().toLowerCase());
    }
}
