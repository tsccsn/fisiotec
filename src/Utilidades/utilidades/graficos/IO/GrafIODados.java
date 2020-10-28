/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.graficos.IO;

import java.util.Date;

/**
 *
 * @author Thiago
 */
public class GrafIODados {
    
    private String nome;
    private Date data;
    private int quantidade;
    private int idProduto;
    
    public GrafIODados(String nome,  Date data, int quantidade, int idProduto) {
        this.nome = nome;
        this.data = data;
        this.quantidade = quantidade;
        this.idProduto = idProduto;
    }
    
    public GrafIODados() {
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
    
}
