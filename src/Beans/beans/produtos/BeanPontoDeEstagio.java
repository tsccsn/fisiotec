/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.produtos;

import cp.CPPontoDeEstagio;
import dao.DaoPontoDeEstagio;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Thiago
 */
@ManagedBean(name="pontoE")
public class BeanPontoDeEstagio {
    private List<CPPontoDeEstagio> pontos = DaoPontoDeEstagio.getAll("nome");

    public List<CPPontoDeEstagio> getPontos() {
        return pontos;
    }

    public void setPontos(List<CPPontoDeEstagio> pontos) {
        this.pontos = pontos;
    }
    
}
