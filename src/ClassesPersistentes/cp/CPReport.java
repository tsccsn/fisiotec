/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp;

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
public class CPReport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long id;
    private String detalhesDoErro;
    private Timestamp horaOcorrido;
    
    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public String getDetalhesDoErro() {
        return detalhesDoErro;
    }

    public Timestamp getHoraOcorrido() {
        return horaOcorrido;
    }

    public void setDetalhesDoErro(String detalhesDoErro) {
        this.detalhesDoErro = detalhesDoErro;
    }

    public void setHoraOcorrido(Timestamp horaOcorrido) {
        this.horaOcorrido = horaOcorrido;
    }

    public void setId(long id) {
        this.id = id;
    }
}
