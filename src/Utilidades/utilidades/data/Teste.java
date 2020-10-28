/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.data;

import java.util.Calendar;
import org.joda.time.DateTime;

/**
 *
 * @author Thiago
 */
public class Teste {

    static int getDiferencaDatas(java.util.Calendar d1, java.util.Calendar d2) {

        if (d1.after(d2)) {
            java.util.Calendar auxiliar = d1;
            d1 = d2;
            d2 = auxiliar;
        }

        int diferenca = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);

        if (d1.get(java.util.Calendar.YEAR) != y2) {

            d1 = (java.util.Calendar) d1.clone();

            do {
                diferenca += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }

        return diferenca;
    }

    public static void main(String[] args) {
         DateTime dt = new DateTime(2011,12,15,12,20);
         System.out.println(UtilData.diaNmesNanoNHMS(dt.getMillis()));

    }
}
