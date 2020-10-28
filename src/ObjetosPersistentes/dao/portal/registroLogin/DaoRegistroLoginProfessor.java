/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal.registroLogin;

import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPAdministrador;
import cp.portal.usuarios.CPProfessor;
import dao.DaoGenerico;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
public class DaoRegistroLoginProfessor {
     private static DaoGenerico<CPRegistroLoginProfessor> daoR = new DaoGenerico<>(CPRegistroLoginProfessor.class);
    
    public static void merge(CPRegistroLoginProfessor registroLoginProfessor){
       daoR.merge(registroLoginProfessor);
    }
    public static CPRegistroLoginProfessor mergeII(CPRegistroLoginProfessor registroLoginProfessor){
       return (CPRegistroLoginProfessor) daoR.mergeII(registroLoginProfessor);
    }
    
    public static void deleta(CPRegistroLoginProfessor r){
        daoR.delete(r);
    }
    
    public static List<CPRegistroLoginProfessor> getAllByProfessor(CPProfessor p){
        HibernateUtil.abre();
        String sql = "from CPRegistroLoginProfessor where professor.id = " + p.getId() + " ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPRegistroLoginProfessor> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    public static List<CPRegistroLoginProfessor> ultimosLogins(CPProfessor a, int quantidadeDeResultados){
        HibernateUtil.abre();
        String sql = "from CPRegistroLoginProfessor where professor.id = " + a.getId() + "  ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        q.setMaxResults(quantidadeDeResultados);
        List<CPRegistroLoginProfessor> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}
