/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.estoque.entrada;

import cp.estoque.CPProduto;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
@Audited
@Entity
public class CPEntradaProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    //ATRIBUTOS
    private long id;
    private CPEntrada entrada;
    private CPProduto produto;
    private int quantidade;

    //CONTRUTUORES
    public CPEntradaProduto() {
    }

    public CPEntradaProduto(CPEntrada entrada) {
        this.entrada = entrada;
    }

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = CPEntrada.class)
    @Cascade(CascadeType.MERGE)
    public CPEntrada getEntrada() {
        return entrada;
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = CPProduto.class)
    @Cascade(CascadeType.MERGE)
    public CPProduto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setProduto(CPProduto produto) {
        this.produto = produto;
    }

    public void setEntrada(CPEntrada entrada) {
        this.entrada = entrada;
    }

    @Override
    public String toString() {
        return (UtilData.diaNmesNanoNL(entrada.getDataEntrada().getTime()) + " | " + produto.toString() + " | " + quantidade);
    }
}
