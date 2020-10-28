/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.estoque.saida;

import cp.CPPontoDeEstagio;
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
public class CPSaida implements Serializable {

    private static final long serialVersionUID = 1L;

    //CONSTRUTORES
    public CPSaida(long id) {
        this.id = id;
    }

    public CPSaida() {
    }
    //ATRIBUTOS
    private long id;
    private CPPontoDeEstagio destino;
    private Timestamp dataSaida;

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPPontoDeEstagio getDestino() {
        return destino;
    }

    public Timestamp getDataSaida() {
        return dataSaida;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setDataSaida(Timestamp dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setDestino(CPPontoDeEstagio destino) {
        this.destino = destino;
    }
}
