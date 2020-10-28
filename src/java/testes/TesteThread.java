/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

/**
 *
 * @author Thiago
 */
public class TesteThread extends Thread {

    public void exibeABC() {
        System.out.println("A");
        System.out.println("B");
    }

    @Override
    public synchronized void start() {
        try {
            super.start();
            super.sleep(1000);
            exibeABC();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    

}
