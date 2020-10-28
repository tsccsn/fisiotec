/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.enumerado;


/**
 *
 * @author Thiago
 */
public enum AgrupamentoCustom {
    op1("1 dia"),
    op2("3 dias"),
    op4("5 dias"),
    op5("7 dias"),
    op6("10 dias"),
    op7("14 dias"),
    op8("21 dias"),
    op9("28 dias"),
    op10("30 dias"),
    op11("35 dias"),
    op12("42 dias"),
    op13("49 dias"),
    op14("56 dias"),
    op15("60 dias"),
    op16("90 dias"),
    op17("120 dias");
    
    public String dias;

    private AgrupamentoCustom(String name) {
        this.dias = name;
    }

    public static AgrupamentoCustom getVaor(int face) {
        return AgrupamentoCustom.values()[face];
    }
}
