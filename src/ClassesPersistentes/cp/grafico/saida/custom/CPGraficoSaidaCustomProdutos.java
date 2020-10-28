/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.grafico.saida.custom;

import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvoProdutos;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

/**
 *
 * @author Thiago-Asus
 */
@Audited
@Entity
public class CPGraficoSaidaCustomProdutos extends ABSGraficoSalvoProdutos implements Serializable {

    private static final long serialVersionUID = 1L;

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
    public CPGraficoSaidaCustom getGrafico() {
        return (CPGraficoSaidaCustom) super.getGrafico();
    }

    @Override
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPProduto getProduto() {
        return super.getProduto();
    }

    //SET'S
    public void setGrafico(CPGraficoSaidaCustom grafico) {
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
