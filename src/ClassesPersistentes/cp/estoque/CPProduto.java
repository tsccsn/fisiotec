/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.estoque;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

/**
 *
 * @author Thiago
 */
@Audited
@Entity
public class CPProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    //CONSTRUTORES

    public CPProduto() {
    }

    public CPProduto(CPProduto p) {
        this.id = p.getId();
        this.nome = p.getNome();
        this.quantidadeEmStoque = p.getQuantidadeEmStoque();
        this.medida = p.getMedida();
        this.unidadeDeMedida = p.getUnidadeDeMedida();
    }
    
    //ATRIBUTOS
    private long id;
    private String nome;
    private double medida;
    private int quantidadeEmStoque;
    private int quantidadeMinimaEmStoque;
    private CPUnidadeDeMedida unidadeDeMedida;

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPUnidadeDeMedida getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public String getNome() {
        return nome;
    }

    public double getMedida() {
        return medida;
    }

    public int getQuantidadeEmStoque() {
        return quantidadeEmStoque;
    }
    public int getQuantidadeMinimaEmStoque() {
        return quantidadeMinimaEmStoque;
    }

    
    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidadeEmStoque(int quantidadeEmStoque) {
        this.quantidadeEmStoque = quantidadeEmStoque;
    }

    public void setMedida(double medida) {
        this.medida = medida;
    }

    public void setUnidadeDeMedida(CPUnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public void setQuantidadeMinimaEmStoque(int quantidadeMinimaEmStoque) {
        this.quantidadeMinimaEmStoque = quantidadeMinimaEmStoque;
    }
    
    

    //TO STRING
    @Override
    public String toString() {
        return this.nome + " - " + this.medida + " " + this.unidadeDeMedida.getAbreviacao();
    }
}
