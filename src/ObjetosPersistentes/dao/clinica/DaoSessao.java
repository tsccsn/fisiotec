/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.clinica;

import cp.clinica.CPPaciente;
import cp.clinica.CPSessao;
import cp.clinica.CPTratamento;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Felipe Machado
 */
public class DaoSessao {
    
    public static DaoGenerico<CPSessao> daoU = new DaoGenerico<CPSessao>(CPSessao.class);
    
    public static void merge(CPSessao p){
        daoU.merge(p);
    }
    
    public static void deleta(CPSessao p){
        daoU.delete(p);
    }
    
    public static List<CPSessao> getAll(String ordem){
        return daoU.getAll(ordem);
    }
    public static List<CPSessao> getAll(){
        return daoU.getAll();
    }
    
    public static CPSessao getById(long id){
        return daoU.getById(id);
    }
    
    public static List<CPSessao> getByDay(Date dia){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPSessao u where u.dia = :dia");
        q.setDate("dia", dia);
        List<CPSessao> lista = q.list();
        return lista;
    }
   
    public static List<CPTratamento> getTratAtuais(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPTratamento u where u.status = :status or u.status = :trat order by u.dia, u.horaInicial");
        q.setParameter("status", "Em Aberto");
        q.setParameter("trat", "Em Tratamento");
        List<CPTratamento> lista = q.list();
        return lista;
    }
    
    public static List<CPTratamento> getTratAntigos(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPTratamento u where u.status != :status or u.status != :trat");
        q.setParameter("status", "Em Aberto");
        q.setParameter("trat", "Em Tratamento");
        List<CPTratamento> lista = q.list();
        return lista;
    }
    
    public static List<CPTratamento> getSesTratamentos(CPSessao sessao){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPTratamento u where u.sessao.id like :sessao");
        q.setParameter("sessao", sessao.getId());
        List<CPTratamento> lista = q.list();
        return lista;
    }
    
    public static List<CPSessao> pesquisa(CPPaciente paciente, String patologia, int qtdTratamento, Date diaCad, String obs, String status, Date de, Date ate) {
        String hql = null;
        Session s = HibernateUtil.getSession();

        if (paciente != null) {
            if (hql == null) {
                hql = "from CPSessao c where c.paciente like :paciente";
            } else {
                hql += " and c.paciente like :paciente";
            }
        }
        if (!patologia.equals("")) {
            if (hql == null) {
                hql = "from CPSessao c where c.patologia like :patologia";
            } else {
                hql += " and c.patologia like :patologia";
            }
        }
        if (qtdTratamento != 0) {
            if (hql == null) {
                hql = "from CPSessao c where c.qtdTratamento like :qtdTratamento";
            } else {
                hql += " and c.qtdTratamento like :qtdTratamento";
            }
        }
        if (diaCad != null) {
            if (hql == null) {
                hql = "from CPSessao c where c.diaCad like :diaCad";
            } else {
                hql += " and c.diaCad like :diaCad";
            }
        }
        if (!obs.isEmpty()) {
            if (hql == null) {
                hql = "from CPSessao c where c.obs like :obs";
            } else {
                hql += " and c.obs like :obs";
            }
        }
        if (!status.equals("")) {
            if (hql == null) {
                hql = "from CPSessao c where c.status like :status";
            } else {
                hql += " and c.status like :status";
            }
        }
        if (de != null && ate != null) {
            if (hql == null) {
                hql = "from CPSessao c where c.dia between :de and :ate";
            } else {
                hql += " and c.dia between :de and :ate";
            }
        }
        if (de != null && ate == null) {
            if (hql == null) {
                hql = "from CPSessao c where c.dia between :de and :hoje";
            } else {
                hql += " and c.dia between :de and :hoje";
            }
        }
        if (de == null && ate != null) {
            if (hql == null) {
                hql = "from CPSessao c where c.dia like :ate";
            } else {
                hql += " and c.dia like " + ate;
            }
        }
        System.out.println(hql);
        Query q = s.createQuery(hql);
        if (paciente != null) {
            q.setParameter("paciente", paciente);
        }
        if (!patologia.equals("")) {
            q.setString("patologia", patologia);
        }
        if (qtdTratamento != 0) {
            q.setInteger("qtdTratamento", qtdTratamento);
        }
        if (diaCad != null) {
            q.setDate("diaCad", diaCad);
        }
        if (!obs.isEmpty()) {
            q.setString("obs", "%" + obs + "%");
        }
        if (!status.equals("")) {
            q.setString("status", status);
        }
        
        if (de != null && ate != null) {
            q.setDate("de", de);
            q.setDate("ate", ate);
        }
        if (de != null && ate == null) {
            q.setDate("de", de);
            q.setDate("hoje", new Date());
        }
        if (de == null && ate != null) {
            q.setDate("ate", ate);
        }

        List<CPSessao> lista = q.list();
        return lista;
    }
    public static List<String> tiposPatologia(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select u.patologia from CPSessao u group by u.patologia");
        List<String> lista = q.list();
        return lista;
    }
}
