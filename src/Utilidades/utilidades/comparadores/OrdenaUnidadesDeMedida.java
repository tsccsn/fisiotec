/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.comparadores;

import cp.estoque.CPUnidadeDeMedida;
import java.util.Comparator;

/**
 *
 * @author Thiago
 */
public class OrdenaUnidadesDeMedida implements Comparator<CPUnidadeDeMedida>{
    
    @Override
    public int compare(CPUnidadeDeMedida p1, CPUnidadeDeMedida p2) {
        return p1.getNome().toString().toLowerCase().compareTo(p2.getNome().toString().toLowerCase());
    }
}
