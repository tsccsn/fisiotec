/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.clinica;

import cp.clinica.CPExame;
import cp.clinica.CPPaciente;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Felipe Machado
 */
public class DaoExame {
    
    public static DaoGenerico<CPExame> daoU = new DaoGenerico<CPExame>(CPExame.class);
    
    public static void merge(CPExame p){
        daoU.merge(p);
    }
    
    public static void deleta(CPExame p){
        daoU.delete(p);
    }
    
    public static List<CPExame> getAll(String ordem){
        return daoU.getAll(ordem);
    }
    public static List<CPExame> getAll(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPExame u order by u.paciente.nome");
        List<CPExame> lista = q.list();
        return lista;
    }
    
    public static List<CPExame> getAllByPaciente(CPPaciente paciente){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPExame where paciente.id = "+paciente.getId()+" ");
        List<CPExame> lista = q.list();
        return lista;
    }
    
    public static CPExame getById(long id){
        return daoU.getById(id);
    }
    
}
