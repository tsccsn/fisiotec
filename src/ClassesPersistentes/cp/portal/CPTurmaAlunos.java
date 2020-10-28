/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal;

import cp.portal.usuarios.CPAluno;
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
public class CPTurmaAlunos implements Serializable {

    private static final long serialVersionUID = 1L;
    //CONSTRUTUTORES
    //ATRIBUTOS    
    private long id;
    private CPAluno aluno;
    private CPTurma turma;
    private boolean vigente;
    private Timestamp dataEntrou;
    private Timestamp dataSaiu;

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
    public CPTurma getTurma() {
        return turma;
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

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setAluno(CPAluno aluno) {
        this.aluno = aluno;
    }

    public void setTurma(CPTurma turma) {
        this.turma = turma;
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
}