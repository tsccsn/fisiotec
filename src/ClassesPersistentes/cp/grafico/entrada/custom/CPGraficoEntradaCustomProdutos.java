/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico.entrada.custom;

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
 * @author Thiago-Asus
 */
@Audited
@Entity
public class CPGraficoEntradaCustomProdutos extends ABSGraficoSalvoProdutos implements Serializable {

	private static final long serialVersionUID = 1L;
	
    //GET'S
    @Id
    @GeneratedValue
    @Override
    public long getId() {
        return super.getId();
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @Override
    public CPGraficoEntradaCustom getGrafico() {
        return (CPGraficoEntradaCustom) super.getGrafico();
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @Override
    public CPProduto getProduto() {
        return super.getProduto();
    }

    //SET'S
    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public void setGrafico(ABSGraficoSalvo grafico) {
        super.setGrafico(grafico);
    }

    @Override
    public void setProduto(CPProduto produto) {
        super.setProduto(produto);
    }

}
