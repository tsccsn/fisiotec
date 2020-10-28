/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package beans.listas;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.enumerado.AgrupamentoCustom;
import utilidades.enumerado.AgrupamentoPredeterminado;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "agrupamento")
@ViewScoped
public class Agrupamentos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    public List<String> agrupamentoCustom() {
        List<String> res = new LinkedList<>();
        for (AgrupamentoCustom s : AgrupamentoCustom.values()) {
            res.add(s.dias);
        }
        return res;
    }

    public List<String> agrupamentoPredeterminado() {
        List<String> res = new LinkedList<>();
        for (AgrupamentoPredeterminado s : AgrupamentoPredeterminado.values()) {
            res.add(s.dias);
        }
        return res;
    }
}
