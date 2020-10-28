/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.estoque;

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
public class CPAlertasQuantidadeProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long id;
    private CPProduto produto;
    private Timestamp dataAtivo;
    private Timestamp dataDesativo;
    private boolean vigente;

    
    public Timestamp getDataAtivo() {
        return dataAtivo;
    }

    public Timestamp getDataDesativo() {
        return dataDesativo;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.MERGE)
    public CPProduto getProduto() {
        return produto;
    }

    public boolean isVigente() {
        return vigente;
    }
    
    // # SET

    public void setDataAtivo(Timestamp dataAtivo) {
        this.dataAtivo = dataAtivo;
    }

    public void setDataDesativo(Timestamp dataDesativo) {
        this.dataDesativo = dataDesativo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProduto(CPProduto produto) {
        this.produto = produto;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
    
}
