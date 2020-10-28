/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.clinica;

import utilidades.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author thiago
 */
public class DaoGenerico<T> {

    private final Class<T> classe;

    public DaoGenerico(Class<T> classe) {
        this.classe = classe;
    }

    public void merge(T t) {
        Session s = HibernateUtil.getSession();
        Transaction tra = s.beginTransaction();
        s.merge(t);
        tra.commit();
    }
    
    public T mergeII(T t){
        Session s = HibernateUtil.getSession();
        Transaction tra = s.beginTransaction();
        t = (T) s.merge(t);
        tra.commit();
        return t;
    }

    public void delete(T t) {
        Session s = HibernateUtil.getSession();
        Transaction tra = s.beginTransaction();
        s.delete(t);
        tra.commit();
    }

    public List<T> getAll(String ordem) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from " + classe.getName() + " order by " + ordem);
        List<T> lista = q.list();
        return lista;
    }

    public List<T> getAll() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from " + classe.getName());
        List<T> lista = q.list();
        return lista;
    }

    public T getById(Long id) {
        Session s = HibernateUtil.getSession();
        return (T) s.load(classe, id);
    }

    public T getByName(String nome) {
        Session s = HibernateUtil.getSession();
        return (T) s.load(classe, nome);
    }
    
    public List<T> getByData(int mes) {
        Session s = HibernateUtil.getSession();
        Query q;
        List<T> list;
        q = s.createQuery("from " + classe.getName() + " where MONTH(data) = " + mes);
        list = q.list();
        return list;
    
    }

    /**
     * 
     * @param campoTabela string referente ao campo da tabela
     * @param oQueSeBusca buscando por
     * @param posicaoBsuca 0 para %*% | 1 para *% | 2 para %* | 3 para =
     * @return 
     */
    public List<T> buscaPersonalizada(Object campoTabela, Object oQueSeBusca, int posicaoBsuca) {
        Session s = HibernateUtil.getSession();
        Query q;
        List<T> list;
        if (campoTabela.toString().equals("id")) {
            q = s.createQuery("from " + classe.getName() + " where id = " + oQueSeBusca.toString());
        } else {
            if (posicaoBsuca == 0) {
                q = s.createQuery("from " + classe.getName() + "  where " + campoTabela.toString() + " like '%" + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 1) {
                q = s.createQuery("from " + classe.getName() + "  where " + campoTabela.toString() + " like '" + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 2) {
                q = s.createQuery("from " + classe.getName() + "  where " + campoTabela.toString() + " like '%" + oQueSeBusca.toString() + "'");
            } else {
                q = s.createQuery("from " + classe.getName() + "  where " + campoTabela.toString() + " = '" + oQueSeBusca.toString() + "'");
            }
        }
        list = q.list();
        return list;
    }

    //from  saidaproduto where saida_id = x and produto id = x
    public T buscaPersonalizada2(long idSaida, long idProduto) {
        Session s = HibernateUtil.getSession();
        Query q;
        List<T> list;
        q = s.createQuery("from " + classe.getName() + "  where saida_id = " + idSaida + " and produto_id = "+idProduto);
        list = q.list();
        return list.get(0);
    }
    public List<T> buscaPersonalizada(List<Object> busca, List<Object> campo) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from " + classe.getName());
        return null;
    }
}
