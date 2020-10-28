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
public class CPExame implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    private CPPaciente paciente;
    
    @Temporal(TemporalType.DATE)
    private Date dataAnexo;
    
    private String endAnexo;

    public Date getDataAnexo() {
        return dataAnexo;
    }

    public void setDataAnexo(Date dataAnexo) {
        this.dataAnexo = dataAnexo;
    }

    public String getEndAnexo() {
        return endAnexo;
    }

    public void setEndAnexo(String endAnexo) {
        this.endAnexo = endAnexo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CPPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CPPaciente paciente) {
        this.paciente = paciente;
    }
    
    
    
}
