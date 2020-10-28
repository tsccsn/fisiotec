/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.numeros;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thiago-Asus
 */
@ManagedBean(name = "utilNumeros")
@ViewScoped
public class UtilNumeros implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static int randomNumeroEntreXeY(int x, int y) {
        return (int) ((Math.random()) * (y - x + 1) + x);
    }
    
    public int longToInt(long s){
        return (int) s;
    }
    
}
