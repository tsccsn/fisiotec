/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.clinica;

import cp.clinica.CPSessao;
import cp.clinica.CPTratamento;
import java.util.Date;
import java.util.List;
import org.hibernate.*;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Felipe Machado
 */
public class DaoTratamento {

    public static DaoGenerico<CPTratamento> daoU = new DaoGenerico<CPTratamento>(CPTratamento.class);

    public static void merge(CPTratamento p) {
        daoU.merge(p);
    }

    public static CPTratamento mergeII(CPTratamento p) {
        return daoU.mergeII(p);
    }

    public static void deleta(CPTratamento p) {
        daoU.delete(p);
    }

    public static List<CPTratamento> getAll(String ordem) {
        return daoU.getAll(ordem);
    }

    public static List<CPTratamento> getAll() {
        return daoU.getAll();
    }

    public static CPTratamento getById(long id) {
        return daoU.getById(id);
    }

    public static List<CPTratamento> getByDay(Date dia) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPTratamento u where u.dia like :dia order by u.horaInicial");
        q.setDate("dia", dia);
        List<CPTratamento> lista = q.list();
        return lista;
    }

    public static List<CPTratamento> getSesTratamentos(CPSessao sessao) {
        Session s = HibernateUtil.getSession();
        System.out.println(sessao.getPaciente().getNome());
        Query q = s.createQuery("from CPTratamento u where u.sessao like :sessao");
        q.setParameter("sessao", sessao);
        List<CPTratamento> lista = q.list();
        return lista;
    }
}
