/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.comparadores;

import cp.portal.usuarios.ABSUsuario;
import java.util.Comparator;

/**
 *
 * @author Thiago
 */
public class OrdenaUsuarios implements Comparator<ABSUsuario> {

    @Override
    public int compare(ABSUsuario a1, ABSUsuario a2) {
        return a1.getNomeCompleto().toLowerCase().compareTo(a2.getNomeCompleto().toLowerCase());
    }
}
