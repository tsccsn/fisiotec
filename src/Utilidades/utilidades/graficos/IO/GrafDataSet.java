/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.graficos.IO;

import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Thiago
 */
public class GrafDataSet implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private List<String> valores = new LinkedList<>();
    private CPPontoDeEstagio ponto = new CPPontoDeEstagio();

    @Override
    public String toString() {
        String r = "";
        r = "--------"+nome + "--------\n";
        for(String s : valores){
            r = r.concat(s+"\n");
        }
        return r;
    }

    
    
    public GrafDataSet() {
    }

    public GrafDataSet(String nome) {
        this.nome = nome;
    }

    public void addValor(String v) {
        valores.add(v);
    }

    public void addValor(int v) {
        valores.add(Integer.toString(v));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getValores() {
        return valores;
    }

    public void setValores(List<String> valores) {
        this.valores = valores;
    }

    public void replicaUltimoValor() {
        addValor(valores.get((valores.size() - 1)));
    }

    public void addAoUltimoValor(int valor) {
        int ultimoValor = Integer.parseInt(valores.get(valores.size() - 1));
        valores.remove(valores.size() - 1);
        ultimoValor += valor;
        addValor(Integer.toString(ultimoValor));
    }

    public CPPontoDeEstagio getPonto() {
        return ponto;
    }

    public void setPonto(CPPontoDeEstagio ponto) {
        this.ponto = ponto;
    }

}
