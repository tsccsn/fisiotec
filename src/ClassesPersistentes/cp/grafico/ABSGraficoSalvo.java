/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.grafico;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Thiago
 */
public abstract class ABSGraficoSalvo implements Serializable {

    private static final long serialVersionUID = 1L;
    //ATRIBUTOS    
    private long id;
    private String nome;
    private String agrupamento;
    private Timestamp de;
    private Timestamp ate;
    private Timestamp dataModificado;

    //GET'S
    public long getId() {
        return id;
    }

    public Timestamp getDataModificado() {
        return dataModificado;
    }

    public String getAgrupamento() {
        return agrupamento;
    }

    public Timestamp getAte() {
        return ate;
    }

    public String getNome() {
        return nome;
    }

    public Timestamp getDe() {
        return de;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setDataModificado(Timestamp dataModificado) {
        this.dataModificado = dataModificado;
    }

    public void setAgrupamento(String agrupamento) {
        this.agrupamento = agrupamento;
    }

    public void setAte(Timestamp ate) {
        this.ate = ate;
    }

    public void setDe(Timestamp de) {
        this.de = de;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
