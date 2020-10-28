/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package testes;

import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
public class Treta  {
	
	private Treta(){
		
	}
    
	private static Treta t = new Treta();
	
	public static Treta getInstance(){
		return t;
	}
	
    private String cod = UtilString.geraCodigoSecreto();

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}
    
     
    
}
