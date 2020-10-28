/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.clinica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Felipe Machado
 */
@Entity
public class CPSessao implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CPPaciente paciente;
    private String patologia;
    private int qtdTratamento;
    @Temporal(TemporalType.DATE)
    private Date diaCad;
    private String obs;
    private String status;
    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CPTratamento> tratamentos;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDiaCad() {
        return diaCad;
    }

    public void setDiaCad(Date diaCad) {
        this.diaCad = diaCad;
    }

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

    public CPPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CPPaciente paciente) {
        this.paciente = paciente;
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    public int getQtdTratamento() {
        return qtdTratamento;
    }

    public void setQtdTratamento(int qtdTratamento) {
        this.qtdTratamento = qtdTratamento;
    }

    public List<CPTratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(List<CPTratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }

    public void adicionaItem(CPTratamento tratamento) {
        if (tratamentos == null) {
            tratamentos = new ArrayList<>();
        }
        tratamento.setNumTratamento(tratamentos.size() + 1);
        tratamento.setSessao(this);
        this.tratamentos.add(tratamento);
    }

    public void removeItem(CPTratamento tratamento) {
        for (CPTratamento xTratamento : tratamentos) {
            if (xTratamento.getNumTratamento() == tratamento.getNumTratamento()) {
                tratamentos.remove(xTratamento);
            }
        }
    }
}
