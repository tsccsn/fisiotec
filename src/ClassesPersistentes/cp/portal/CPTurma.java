/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal;

import cp.portal.usuarios.CPProfessor;
import java.io.Serializable;
import java.sql.Timestamp;
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
public class CPTurma implements Serializable {
    //CONSTRUTUTORES

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //ATRIBUTOS    
    private long id;
    private String codigoTurma;
    private Timestamp dataInicio;
    private Timestamp dataTermino;
    private boolean vigente;

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public Timestamp getDataInicio() {
        return dataInicio;
    }

    public Timestamp getDataTermino() {
        return dataTermino;
    }

    public boolean isVigente() {
        return vigente;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public void setDataInicio(Timestamp dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataTermino(Timestamp dataTermino) {
        this.dataTermino = dataTermino;
    }

    public void setVigente(boolean status) {
        this.vigente = status;
    }
}
