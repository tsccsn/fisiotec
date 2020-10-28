/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.estoque.saida;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import cp.estoque.CPProduto;

@Audited
@Entity
public class CPSaidaProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    //CONSTRUTUTORES
    public CPSaidaProduto(CPSaida saida, int quantidade) {
        this.saida = saida;
        this.quantidade = quantidade;
    }

    public CPSaidaProduto() {
    }
    //ATRIBUTOS    
    private long id;
    private CPSaida saida;
    private CPProduto produto;
    private int quantidade;

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPProduto getProduto() {
        return produto;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPSaida getSaida() {
        return saida;
    }

    public int getQuantidade() {
        return quantidade;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setProduto(CPProduto produto) {
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setSaida(CPSaida saida) {
        this.saida = saida;
    }
}
