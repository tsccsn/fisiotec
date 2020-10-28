/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.clinica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Felipe Machado
 */
@Entity
public class CPConsulta implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    private CPPaciente paciente;
    
    @Temporal(TemporalType.DATE)
    private Date dia;
    
    @Temporal(TemporalType.TIME)
    private Date horaInicial;
    
    @Temporal(TemporalType.TIME)
    private Date horaFinal;
    
    private String obs;
    
    private String status;
    
    private int box;

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Date getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial;
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
    
    
}
