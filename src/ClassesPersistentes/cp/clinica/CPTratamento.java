/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.clinica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Felipe Machado
 */
@Entity
public class CPTratamento implements Serializable {

    private Long id;
    private int numTratamento;
    private Date dia;
    private Date horaInicial;
    private Date horaFinal;
    private int box;
    private String status;
    private String obs;
    private CPSessao sessao;

    public int getNumTratamento() {
        return numTratamento;
    }

    public void setNumTratamento(int numTratamento) {
        this.numTratamento = numTratamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    @Temporal(TemporalType.DATE)
    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    @Temporal(TemporalType.TIME)
    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    @Temporal(TemporalType.TIME)
    public Date getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPSessao getSessao() {
        return sessao;
    }

    public void setSessao(CPSessao sessao) {
        this.sessao = sessao;
    }
}
