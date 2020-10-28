/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico.saida.custom;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import cp.CPPontoDeEstagio;
import cp.grafico.saida.ABSGraficoSalvoSaidaPontos;

/**
 *
 * @author Thiago
 */
@Audited
@Entity
public class CPGraficoSaidaCustomPontos extends ABSGraficoSalvoSaidaPontos implements Serializable {

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
    public CPGraficoSaidaCustom getGrafico() {
        return (CPGraficoSaidaCustom) super.getGrafico();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @Override
    public CPPontoDeEstagio getPonto() {
        return super.getPonto();
    }

    //SET'S
    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public void setGrafico(CPGraficoSaidaCustom grafico) {
        super.setGrafico(grafico);
    }

    @Override
    public void setPonto(CPPontoDeEstagio ponto) {
        super.setPonto(ponto);
    }
}
