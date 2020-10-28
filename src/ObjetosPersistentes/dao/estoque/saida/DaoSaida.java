/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.estoque.saida;

import cp.estoque.saida.CPSaida;
import dao.DaoGenerico;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoSaida {

    private static DaoGenerico<CPSaida> daoS = new DaoGenerico<>(CPSaida.class);

    public static void merge(CPSaida s) {
        daoS.merge(s);
    }

    public static List<CPSaida> getAllOrderByData() {
        return daoS.getAll("dataSaida");
    }

    public static CPSaida mergeII(CPSaida s) {
        return (CPSaida) daoS.mergeII(s);
    }

    public static List<CPSaida> getByData(int mes) {
        return daoS.getByData(mes);
    }

    @SuppressWarnings("unchecked")
    public static List<CPSaida> getByData(Timestamp inicio, Timestamp fim) {
        HibernateUtil.abre();
        String sql = "from CPSaida where dataSaida >=  " + UtilData.aaaaMMddHHmmssParaConsulta(inicio) + "  and dataSaida <= true "
                + " " + UtilData.aaaaMMddHHmmssParaConsulta(fim) + " ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public static int lastId() {
        long i = daoS.getNextId(CPSaida.class);
        return (int) i - 1;
    }

    public static CPSaida getById(long id) {
        return daoS.getById(id);
    }
}
