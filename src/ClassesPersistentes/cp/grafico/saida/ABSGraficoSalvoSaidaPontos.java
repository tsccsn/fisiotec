/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico.saida;

import cp.CPPontoDeEstagio;
import java.io.Serializable;

/**
 *
 * @author Thiago
 */
public class ABSGraficoSalvoSaidaPontos implements Serializable {

    private static final long serialVersionUID = 1L;

	
	
    //ATRIBUTOS
    private long id;
    private CPPontoDeEstagio ponto;
    private ABSGraficoSaidaSalvo grafico;

    //GET'S
    public long getId() {
        return id;
    }

    public ABSGraficoSaidaSalvo getGrafico() {
        return grafico;
    }

    public CPPontoDeEstagio getPonto() {
        return ponto;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setGrafico(ABSGraficoSaidaSalvo grafico) {
        this.grafico = grafico;
    }

    public void setPonto(CPPontoDeEstagio ponto) {
        this.ponto = ponto;
    }
}
