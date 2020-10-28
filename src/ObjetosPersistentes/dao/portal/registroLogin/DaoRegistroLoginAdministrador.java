/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal.registroLogin;

import cp.portal.registroLogin.ABSREgistroLogin;
import cp.portal.registroLogin.CPRegistroLoginAdministrador;
import cp.portal.usuarios.CPAdministrador;
import dao.DaoGenerico;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoRegistroLoginAdministrador {
    private static DaoGenerico<CPRegistroLoginAdministrador> daoR = new DaoGenerico<CPRegistroLoginAdministrador>(CPRegistroLoginAdministrador.class);
    
    public static void merge(CPRegistroLoginAdministrador registroLoginAdministrador){
       daoR.merge(registroLoginAdministrador);
    }
    
    public static List<CPRegistroLoginAdministrador> ultimosLogins(CPAdministrador a, int quantidadeDeResultados){
        HibernateUtil.abre();
        String sql = "from CPRegistroLoginAdministrador where administrador.id = " + a.getId() + "  ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        q.setMaxResults(quantidadeDeResultados);
        List<CPRegistroLoginAdministrador> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    
}
