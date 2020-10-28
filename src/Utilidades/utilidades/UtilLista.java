/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "utilLista")
@ViewScoped
public class UtilLista implements Serializable {

    private String estilo = "linha1";

    public String linha(boolean muda) {
        if (muda) {
            if (estilo.equals("linha1")) {
                estilo = "linha2";
            } else {
                estilo = "linha1";
            }
        }
        return estilo;
    }

    public List recebeObjetoRetornaLista(Object o) {
        List r = new LinkedList();
        r.add(o);
        return r;
    }
}
