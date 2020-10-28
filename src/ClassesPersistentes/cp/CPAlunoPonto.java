/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp;

import cp.portal.usuarios.CPAluno;
import java.io.Serializable;
import java.sql.Timestamp;
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
public class CPAlunoPonto implements Serializable {

    private static final long serialVersionUID = 1L;
    //ATRIBUTOS
    private long id;
    private CPAluno aluno;
    private CPPontoDeEstagio ponto;
    private boolean vigente;
    private Timestamp dataSaiu;
    private Timestamp dataEntrou;
    private double nota;

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPAluno getAluno() {
        return aluno;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPPontoDeEstagio getPonto() {
        return ponto;
    }

    public boolean isVigente() {
        return vigente;
    }

    public Timestamp getDataEntrou() {
        return dataEntrou;
    }

    public Timestamp getDataSaiu() {
        return dataSaiu;
    }

    public double getNota() {
        return nota;
    }
    

    public void setAluno(CPAluno aluno) {
        this.aluno = aluno;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPonto(CPPontoDeEstagio ponto) {
        this.ponto = ponto;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public void setDataEntrou(Timestamp dataEntrou) {
        this.dataEntrou = dataEntrou;
    }

    public void setDataSaiu(Timestamp dataSaiu) {
        this.dataSaiu = dataSaiu;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
}
