/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.estoque.entrada;

import cp.estoque.entrada.CPEntrada;
import dao.DaoGenerico;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DaoEntrada {

    private static DaoGenerico<CPEntrada> daoE = new DaoGenerico<>(CPEntrada.class);

    public static void merge(CPEntrada e) {
        daoE.merge(e);
    }

    public static List<CPEntrada> getAllOrderByData() {
        return daoE.getAll("dataEntrada");
    }

    public static CPEntrada mergeII(CPEntrada e) {
        return (CPEntrada) daoE.mergeII(e);
    }

    public static CPEntrada getById(long id) {
        return daoE.getById(id);
    }
}
