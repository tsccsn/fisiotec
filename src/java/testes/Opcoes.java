/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

/**
 *
 * @author Thiago
 */
public enum Opcoes {

    op1("1 dia"), op2("3 dias");
    
    public String dia;

    private Opcoes(String name) {
        this.dia = name;
    }

    public static Opcoes getFace(int face) {
        return Opcoes.values()[face];
    }
}
