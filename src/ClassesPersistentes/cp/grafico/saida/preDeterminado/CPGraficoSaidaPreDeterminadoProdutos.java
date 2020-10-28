/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico.saida.preDeterminado;

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
import cp.grafico.ABSGraficoSalvoProdutos;
import cp.grafico.ABSGraficoSalvo;

/**
 *
 * @author Thiago
 */
@Audited
@Entity
public class CPGraficoSaidaPreDeterminadoProdutos extends ABSGraficoSalvoProdutos implements Serializable {

    private static final long serialVersionUID = 1L;

    //CONSTRUTORES
    public CPGraficoSaidaPreDeterminadoProdutos() {
    }

    public CPGraficoSaidaPreDeterminadoProdutos(CPGraficoSaidaPreDeterminado grafSa, CPProduto p) {
        super.setProduto(p);
        super.setGrafico(grafSa);
    }

    //GET'S
    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPGraficoSaidaPreDeterminado getGrafico() {
        return (CPGraficoSaidaPreDeterminado) super.getGrafico();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @Override
    public CPProduto getProduto() {
        return super.getProduto();
    }

    //SET'S
    public void setGrafico(CPGraficoSaidaPreDeterminado grafico) {
        super.setGrafico(grafico);
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public void setProduto(CPProduto produto) {
        super.setProduto(produto);
    }
}
