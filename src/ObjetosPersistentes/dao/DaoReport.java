/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import cp.CPReport;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
public class DaoReport {

    private static DaoGenerico<CPReport> dao = new DaoGenerico<>(CPReport.class);
    
    public static void merge(CPReport t) {
        t.setHoraOcorrido(UtilData.getDataTimestamp());
        dao.merge(t);
    }
}
