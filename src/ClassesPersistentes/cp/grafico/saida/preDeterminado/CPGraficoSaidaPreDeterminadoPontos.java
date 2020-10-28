/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico.saida.preDeterminado;

import cp.CPPontoDeEstagio;
import cp.grafico.saida.ABSGraficoSaidaSalvo;
import cp.grafico.saida.ABSGraficoSalvoSaidaPontos;
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
public class CPGraficoSaidaPreDeterminadoPontos extends ABSGraficoSalvoSaidaPontos implements Serializable {

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
    @Cascade(CascadeType.SAVE_UPDATE)
    public CPGraficoSaidaPreDeterminado getGrafico() {
        return (CPGraficoSaidaPreDeterminado) super.getGrafico();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    @Override
    public CPPontoDeEstagio getPonto() {
        return super.getPonto();
    }
    
    //SET's
    @Override
    public void setGrafico(ABSGraficoSaidaSalvo grafico) {
        super.setGrafico(grafico);
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public void setPonto(CPPontoDeEstagio ponto) {
        super.setPonto(ponto);
    }
    
}
