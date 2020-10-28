/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.comparadores;

import cp.estoque.CPProduto;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class OrdenaProdutos implements Comparator<CPProduto>, Serializable {

	private static final long serialVersionUID = 1L;
	
    @Override
    public  int compare(CPProduto p1, CPProduto p2) {
        return p1.getNome().toLowerCase().compareTo(p2.getNome().toLowerCase());
    }
    
    public static void ordena(List<CPProduto> p){
        OrdenaProdutos o = new OrdenaProdutos();
        Collections.sort(p,o);
    }
    
}
