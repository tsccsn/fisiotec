/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp;

import cp.portal.usuarios.CPProfessor;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.envers.Audited;

/**
 *
 * @author Thiago
 */
@Audited
@Entity
public class CPProfessorPonto implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Timestamp dataQueAssumiu;
    private Timestamp dataQueEntregou;
    private CPProfessor professor;
    private CPPontoDeEstagio ponto;
    private boolean vigente;
    
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPonto(CPPontoDeEstagio ponto) {
        this.ponto = ponto;
    }

    public void setDataQueAssumiu(Timestamp dataQueAssumiu) {
        this.dataQueAssumiu = dataQueAssumiu;
    }

    public void setDataQueEntregou(Timestamp dataQueEntregou) {
        this.dataQueEntregou = dataQueEntregou;
    }

    public void setVigente(boolean status) {
        this.vigente = status;
    }
    
    

    
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPPontoDeEstagio getPonto() {
        return ponto;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPProfessor getProfessor() {
        return professor;
    }

    public void setProfessor(CPProfessor professor) {
        this.professor = professor;
    }

    public Timestamp getDataQueAssumiu() {
        return dataQueAssumiu;
    }

    public Timestamp getDataQueEntregou() {
        return dataQueEntregou;
    }

    public boolean isVigente() {
        return vigente;
    }
    
}
