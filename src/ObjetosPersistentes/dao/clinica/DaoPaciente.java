/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.clinica;

import cp.clinica.CPPaciente;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Felipe Machado
 */
public class DaoPaciente {
    public static DaoGenerico<CPPaciente> daoU = new DaoGenerico<CPPaciente>(CPPaciente.class);
    
    public static void merge(CPPaciente p){
        daoU.merge(p);
    }
    
    public static CPPaciente mergeII(CPPaciente p){
        return daoU.mergeII(p);
    }
    
    public static void deleta(CPPaciente p){
        daoU.delete(p);
    }
    
    public static List<CPPaciente> getAll(String ordem){
        return daoU.getAll(ordem);
    }
    public static List<CPPaciente> getAll(){
        return daoU.getAll();
    }
    
    public static CPPaciente getById(long id){
        return daoU.getById(id);
    }
    
    public static CPPaciente getByName(String nome){
        
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPPaciente u where u.nome = :nome");
        q.setString("nome", nome);
        CPPaciente paciente = (CPPaciente) q.uniqueResult();
        return paciente;
    }
    public static List<CPPaciente> pesquisa(CPPaciente paciente) {
        String hql = null;
        Session s = HibernateUtil.getSession();

        if (!paciente.getNome().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.nome like :nome";
            } else {
                hql += " and nome like :nome";
            }
        }
        if (!paciente.getSexo().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.sexo like :sexo";
            } else {
                hql += " and c.sexo like :sexo";
            }
        }
        if (!paciente.getRaca().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.raca like :raca";
            } else {
                hql += " and c.raca like :raca";
            }
        }
        if (paciente.getDataNasc() != null) {
            if (hql == null) {
                hql = "from CPPaciente c where c.dataNasc like :dataNasc";
            } else {
                hql += " and c.dataNasc like :dataNasc";
            }
        }
        if (!paciente.getNaturalidade().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.naturalidade like :naturalidade";
            } else {
                hql += " and c.naturalidade like :naturalidade";
            }
        }
        if (!paciente.getBairro().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.bairro like :bairro";
            } else {
                hql += " and c.bairro like :bairro";
            }
        }
        if (!paciente.getCidade().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.cidade like :cidade";
            } else {
                hql += " and c.cidade like :cidade";
            }
        }
        if (!paciente.getEstado().isEmpty()) {
            if (hql == null) {
                hql = "from CPPaciente c where c.estado like :estado";
            } else {
                hql += " and c.estado like :estado";
            }
        }
        System.out.println(hql);
        Query q = s.createQuery(hql);
        if (!paciente.getNome().isEmpty()) {
            q.setString("nome", paciente.getNome());
        }
        if (!paciente.getSexo().isEmpty()) {
            q.setString("sexo", paciente.getSexo());
        }
        if (!paciente.getRaca().isEmpty()) {
            q.setString("raca", paciente.getRaca());
        }
        if (paciente.getDataNasc() != null) {
            q.setDate("dataNasc", paciente.getDataNasc());
        }
        if (!paciente.getNaturalidade().isEmpty()) {
            q.setString("naturalidade", paciente.getNaturalidade());
        }
        if (!paciente.getBairro().isEmpty()) {
            q.setString("bairro", paciente.getBairro());
        }
        if (!paciente.getCidade().isEmpty()) {
            q.setString("cidade", paciente.getCidade());
        }
        if (!paciente.getEstado().isEmpty()) {
            q.setString("estado", paciente.getEstado());
        }

        List<CPPaciente> lista = q.list();
        return lista;
    }
    public static List<String> listas(String atributo){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select u." + atributo + " from CPPaciente u group by u." + atributo);
        List<String> lista = q.list();
        return lista;
    }
}
