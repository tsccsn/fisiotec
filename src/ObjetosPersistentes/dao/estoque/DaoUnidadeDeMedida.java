/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.estoque;

import cp.estoque.CPUnidadeDeMedida;
import dao.DaoGenerico;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class DaoUnidadeDeMedida {
    
    private static DaoGenerico<CPUnidadeDeMedida> daoUM = new DaoGenerico<CPUnidadeDeMedida>(CPUnidadeDeMedida.class);
    
    public static void merge(CPUnidadeDeMedida u){
        daoUM.merge(u);
    }
    public static CPUnidadeDeMedida mergeII(CPUnidadeDeMedida u){
        return (CPUnidadeDeMedida) daoUM.mergeII(u);
    }
    public static List<CPUnidadeDeMedida> getAll(String ordem){
        return daoUM.getAll(ordem);
    }
    public static List<CPUnidadeDeMedida> getAll(){
        return daoUM.getAll();
    }
    public static CPUnidadeDeMedida getById(long id){
        return daoUM.getById(id);
    }
}
