/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import cp.CPPontoDeEstagio;
import cp.CPProfessorPonto;
import cp.portal.CPTurmaAlunos;
import cp.portal.usuarios.CPProfessor;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoProfessorPonto {

    private static DaoGenerico<CPProfessorPonto> daoP = new DaoGenerico<>(CPProfessorPonto.class);

    public static void novo(CPProfessorPonto p) {
        p.setDataQueAssumiu(UtilData.getDataTimestamp());
        p.setVigente(true);
        daoP.merge(p);
    }

    public static void deleta(CPProfessorPonto e){
        daoP.delete(e);
    }
    
    public static void fimVigencia(CPProfessorPonto t){
        t.setDataQueEntregou(UtilData.getDataTimestamp());
        t.setVigente(false);
        daoP.merge(t);
    }
    
    public static CPPontoDeEstagio pontoAtualDoProfessor(CPProfessor profe) {
        HibernateUtil.abre();
        String sql = "from CPProfessorPonto where professor.id = " + profe.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return new CPPontoDeEstagio();
        } else {
            return ((CPProfessorPonto) list.get(0)).getPonto();
        }
    }
    public static CPProfessorPonto getByProfessor(CPProfessor profe) {
        HibernateUtil.abre();
        String sql = "from CPProfessorPonto where professor.id = " + profe.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return (CPProfessorPonto) list.get(0);
        }
    }
    public static List<CPProfessorPonto> getAllByProfessor(CPProfessor profe) {
        HibernateUtil.abre();
        String sql = "from CPProfessorPonto where professor.id = " + profe.getId() + " ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    public static List<CPProfessorPonto> professorPontoAnteriores(CPProfessor xProfessor){
        HibernateUtil.abre();
        String sql = "from CPProfessorPonto where professor.id = " + xProfessor.getId() + " and vigente = false ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}
