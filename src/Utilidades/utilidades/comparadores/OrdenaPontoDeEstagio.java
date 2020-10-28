/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.comparadores;

import cp.CPPontoDeEstagio;
import java.util.Comparator;

/**
 *
 * @author Thiago
 */
public class OrdenaPontoDeEstagio implements Comparator<CPPontoDeEstagio>{
    
    @Override
    public int compare(CPPontoDeEstagio p1, CPPontoDeEstagio p2) {
        return p1.getNome().toLowerCase().compareTo(p2.getNome().toLowerCase());
    }
}

