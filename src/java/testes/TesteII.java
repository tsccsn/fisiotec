/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

/**
 *
 * @author Thiago
 */
public class TesteII implements Runnable {

    @Override
    public void run() {
        exibieNumeros();
    }

    public void exibieNumeros() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("1");
        System.out.println("2");
    }
}
