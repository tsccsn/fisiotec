/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.enumerado;

/**
 *
 * @author Thiago
 */
public enum AgrupamentoPredeterminado {

    op1("Mês"),
    op2("Bimestre"),
    op3("Trimestre"),
    op4("Semestre"),
    op5("Ano");
    
    public String dias;

    private AgrupamentoPredeterminado(String name) {
        this.dias = name;
    }

    public static AgrupamentoPredeterminado getVaor(int face) {
        return AgrupamentoPredeterminado.values()[face];
    }
}
