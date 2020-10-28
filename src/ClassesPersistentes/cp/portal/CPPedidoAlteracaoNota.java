/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.portal;

import cp.CPAlunoPonto;
import cp.portal.usuarios.CPProfessor;
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
public class CPPedidoAlteracaoNota implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private CPProfessor professor;
    private CPAlunoPonto alunoPonto;
    private double novaNota;
    private double notaAntiga;
    private Timestamp dataGerado;
    private Timestamp dataRespondido;
    private String justificativa;
    private boolean aceito;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPAlunoPonto getAlunoPonto() {
        return alunoPonto;
    }

    public double getNovaNota() {
        return novaNota;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPProfessor getProfessor() {
        return professor;
    }

    public Timestamp getDataGerado() {
        return dataGerado;
    }

    public Timestamp getDataRespondido() {
        return dataRespondido;
    }

    public double getNotaAntiga() {
        return notaAntiga;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public boolean isAceito() {
        return aceito;
    }

    public void setAceito(boolean aceito) {
        this.aceito = aceito;
    }
    
    public void setAlunoPonto(CPAlunoPonto alunoPonto) {
        this.alunoPonto = alunoPonto;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNovaNota(double novaNota) {
        this.novaNota = novaNota;
    }

    public void setProfessor(CPProfessor professor) {
        this.professor = professor;
    }

    public void setDataGerado(Timestamp dataGerado) {
        this.dataGerado = dataGerado;
    }

    public void setDataRespondido(Timestamp dataRespondido) {
        this.dataRespondido = dataRespondido;
    }

    public void setNotaAntiga(double notaAntiga) {
        this.notaAntiga = notaAntiga;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
    
}
