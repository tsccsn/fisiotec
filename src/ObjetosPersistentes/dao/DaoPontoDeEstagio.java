/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao;

import cp.CPPontoDeEstagio;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoPontoDeEstagio {

    private static DaoGenerico<CPPontoDeEstagio> daoP = new DaoGenerico<>(CPPontoDeEstagio.class);

    public static void merge(CPPontoDeEstagio p) {
        p.setAtivo(true);
        daoP.merge(p);
    }

    public static List<CPPontoDeEstagio> getAllAtivos() {
        HibernateUtil.abre();
        String sql = "from CPPontoDeEstagio where ativo = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    

    public static void destaivaPonto(CPPontoDeEstagio p ){
        p.setAtivo(false);
        daoP.merge(p);
    }
    public static void ativaPonto(CPPontoDeEstagio p ){
        p.setAtivo(true);
        daoP.merge(p);
    }
    
    public static List<CPPontoDeEstagio> getAll(String ordem) {
        HibernateUtil.abre();
        String sql = "from CPPontoDeEstagio where ativo = true order by nome";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    public static List<CPPontoDeEstagio> getAllDesativados() {
        HibernateUtil.abre();
        String sql = "from CPPontoDeEstagio where ativo = false";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    

    public static CPPontoDeEstagio getById(long id) {
        return daoP.getById(id);
    }

    public static void delete(CPPontoDeEstagio p) {
        daoP.delete(p);
    }
}
