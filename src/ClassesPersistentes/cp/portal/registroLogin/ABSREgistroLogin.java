/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cp.portal.registroLogin;

import cp.portal.usuarios.ABSUsuario;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Thiago-Asus
 */
public abstract class ABSREgistroLogin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    //CONSTRUTUTORES

    //ATRIBUTOS    
    private long id;
    private ABSUsuario usuario;
    private Timestamp horaLogado;
    private Timestamp horaDeslogado;

    //GET'S
    public long getId() {
        return id;
    }

    public Timestamp getHoraDeslogado() {
        return horaDeslogado;
    }

    public Timestamp getHoraLogado() {
        return horaLogado;
    }

    public ABSUsuario getUsuario() {
        return usuario;
    }

    //SET'S
    public void setId(long id) {
        this.id = id;
    }

    public void setHoraDeslogado(Timestamp horaDeslogado) {
        this.horaDeslogado = horaDeslogado;
    }

    public void setHoraLogado(Timestamp horaLogado) {
        this.horaLogado = horaLogado;
    }

    public void setUsuario(ABSUsuario usuario) {
        this.usuario = usuario;
    }
}
