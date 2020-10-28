/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.estoque.entrada;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

/**
 *
 * @author Thiago
 */
@Audited
@Entity
public class CPEntrada implements Serializable {

    private static final long serialVersionUID = 1L;
    //ATRIBUTOS
    private long id;
    private Timestamp DataEntrada;

    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public Timestamp getDataEntrada() {
        return DataEntrada;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setDataEntrada(Timestamp DataEntrada) {
        this.DataEntrada = DataEntrada;
    }
}
