/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thiago-Asus
 */
@ManagedBean(name = "boleanos")
@ViewScoped
public class Boleanos implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final long timeDefault = 300;
    public static final long timeDefaultGerarGrafico = 1200;
    
    
    public class Bol implements Serializable {

        private static final long serialVersionUID = 1L;
        private boolean valor1 = false;
        private boolean valor2 = false;

        public void setV1(boolean t) {
            valor1 = t;
        }

        public void setV2(boolean t) {
            valor2 = t;
        }

        public boolean v1() {
            return valor1;
        }

        public boolean v2() {
            return valor2;
        }

        public void mudaV1() {
            if (valor1) {
                valor1 = false;
            } else {
                valor1 = true;
            }
        }

        public void mudaV2() {
            if (valor2) {
                valor2 = false;
            } else {
                valor2 = true;
            }
        }

        public void mudaAmbos() {
            mudaV1();
            mudaV2();
        }

        public void ambosTrue() {
            setV1(true);
            setV2(true);
        }

        public void ambosFalse() {
            setV1(false);
            setV2(false);
        }
    }
    private List<Bol> boleanos = new LinkedList<Bol>();

    public boolean verA(int pos) {
        try {
            return boleanos.get(pos).v1();
        } catch (IndexOutOfBoundsException e) {
            trataExecao(pos);
            return boleanos.get(pos).v1();
        }

    }

    public boolean verB(int pos) {
        try {
            return boleanos.get(pos).v2();
        } catch (IndexOutOfBoundsException e) {
            trataExecao(pos);
            return boleanos.get(pos).v2();
        }
    }

    public void mudaA(long pos, long time) {
        try {
            Thread.sleep(time);
            boleanos.get((int) pos).mudaV1();
        } catch (Exception e) {
            trataExecao((int) pos);
            boleanos.get((int) pos).mudaV1();
        }
    }

    public void mudaB(long pos, long time) {
        try {
            Thread.sleep(time);
            boleanos.get((int) pos).mudaV2();
        } catch (Exception e) {
            trataExecao((int) pos);
            boleanos.get((int) pos).mudaV2();
        }
    }

    public void mudaAmbos(long pos, long time) {
        try {
            Thread.sleep(time);
            boleanos.get((int) pos).mudaAmbos();
        } catch (Exception e) {
            trataExecao((int) pos);
            boleanos.get((int) pos).mudaAmbos();
        }

    }

    public void ambosTrue(long pos) {
        try {
            boleanos.get((int) pos).ambosTrue();
        } catch (IndexOutOfBoundsException e) {
            trataExecao((int) pos);
            boleanos.get((int) pos).ambosTrue();
        }

    }

    public void ambosFalse(long pos, long time) {
        try {
            Thread.sleep(time);
            boleanos.get((int) pos).ambosFalse();
        } catch (Exception ex) {
            trataExecao((int) pos);
            boleanos.get((int) pos).ambosFalse();
        }
    }
    
    public void ambosFalse(long pos1, long pos2, long time){
        ambosFalse(pos1, time);
        ambosFalse(pos2, time);
    }
    public void ambosFalse(long pos1, long pos2, long pos3, long time){
        ambosFalse(pos1, time);
        ambosFalse(pos2, time);
        ambosFalse(pos3, time);
    }

    public int verTamanho() {
        return boleanos.size();
    }

    public boolean meVeUmTrue() {
        return true;
    }

    public boolean meVeUmFalse() {
        return false;
    }

    public void trataExecao(int pos) {
        int quantidadeParaAdicinar = (pos - verTamanho());
        for (int i = 0; i <= quantidadeParaAdicinar; i++) {
            boleanos.add(new Bol());
        }
    }

    public long getTimeDefault() {
        return timeDefault;
    }
    
}