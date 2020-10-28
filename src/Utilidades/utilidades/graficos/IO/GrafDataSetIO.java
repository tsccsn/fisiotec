/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.graficos.IO;

import cp.CPPontoDeEstagio;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class GrafDataSetIO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private List<String> valores = new LinkedList<>();
    private List<String> valoresSaida = new LinkedList<>();
    private List<String> valoresEntrada = new LinkedList<>();
    private CPPontoDeEstagio ponto = new CPPontoDeEstagio();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CPPontoDeEstagio getPonto() {
        return ponto;
    }

    public void setPonto(CPPontoDeEstagio ponto) {
        this.ponto = ponto;
    }

    public List<String> getValores() {
        return valores;
    }

    public void setValores(List<String> valores) {
        this.valores = valores;
    }

    public List<String> getValoresEntrada() {
        return valoresEntrada;
    }

    public void setValoresEntrada(List<String> valoresEntrada) {
        this.valoresEntrada = valoresEntrada;
    }

    public List<String> getValoresSaida() {
        return valoresSaida;
    }

    public void setValoresSaida(List<String> valoresSaida) {
        this.valoresSaida = valoresSaida;
    }
    
    
    
}
