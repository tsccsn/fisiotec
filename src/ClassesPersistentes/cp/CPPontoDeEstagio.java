/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp;

import java.io.Serializable;
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
public class CPPontoDeEstagio implements Serializable {

    private static final long serialVersionUID = 1L;
    //ATRIBUTOS
    private long id;
    private String nome;
    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    //GET'S
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
