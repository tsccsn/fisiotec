/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import cp.grafico.entrada.custom.CPGraficoEntradaCustom;
import java.sql.Connection;
import java.util.List;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilidades.hibernate.HibernateUtil;
import utilidades.string.UtilString;

/**
 *
 * @author thiago
 */
public class DaoGenerico<T> {

    private final Class<T> classe;

    public String nomeClasse() {
        return getClass().toString().substring(
                getClass().toString().lastIndexOf(".") + 1);
    }

    public DaoGenerico(Class<T> classe) {
        this.classe = classe;
    }

    public void merge(T t) {
        HibernateUtil.abre();
        Transaction tra = HibernateUtil.getSessao().beginTransaction();
        HibernateUtil.getSessao().merge(t);
        tra.commit();
        HibernateUtil.getSessao().evict(t);
    }

    public Object mergeII(T t) {
        HibernateUtil.abre();
        Transaction tra = HibernateUtil.getSessao().beginTransaction();
        Object merge = HibernateUtil.getSessao().merge(t);
        tra.commit();
        HibernateUtil.fecha();
        return merge;
    }

    public Object salvar(T t) {
        HibernateUtil.abre();
        Transaction tra = HibernateUtil.getSessao().beginTransaction();
        Object merge = HibernateUtil.getSessao().save(t);
        tra.commit();
        HibernateUtil.fecha();
        return merge;
    }

    public void delete(T t) {
        HibernateUtil.abre();
        Transaction tra = HibernateUtil.getSessao().beginTransaction();
        try{
            HibernateUtil.getSessao().delete(t);
        }catch (NonUniqueObjectException e){
            HibernateUtil.novaSessao();
            HibernateUtil.getSessao().delete(t);
        }
        
        tra.commit();
        HibernateUtil.fecha();
    }

    public List<T> getAll(String ordem) {
        HibernateUtil.abre();
        Query q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + " order by "
                + ordem);
        List<T> lista = q.list();
        HibernateUtil.fecha();
        return lista;
    }

    public List<T> getAll() {
        HibernateUtil.abre();
        Query q = HibernateUtil.getSessao().createQuery("from " + classe.getName());
        List<T> lista = q.list();
        HibernateUtil.fecha();
        return lista;
    }

    public T getByIdClass(long id, Object o) {
        HibernateUtil.abre();
        Query q = HibernateUtil.getSessao().createQuery("from " + UtilString.getNomeClasse(o)
                + " where id = " + id + " ");
        List<T> lista = q.list();
        HibernateUtil.fecha();
        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }

    public T getById(long id) {
        HibernateUtil.abre();
        Object load = HibernateUtil.getSessao().load(classe, id);
        //HibernateUtil.getSessao().evict(load);
        HibernateUtil.fecha();
        return (T) load;
    }

    public List<T> getByData(int mes) {
        HibernateUtil.abre();
        Query q;
        List<T> list;
        q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + " where MONTH(data) = "
                + mes);
        list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public long getNextId(Object o) {
        HibernateUtil.abre();
        Query q = HibernateUtil.getSessao().createSQLQuery("select max(id) from "
                + UtilString.getNomeClasse(o));
        List<T> list = q.list();
        try {
            return Long.parseLong(list.get(0).toString()) + 1l;
        } catch (NullPointerException e) {
            return 1l;
        } finally {
            HibernateUtil.fecha();
        }

    }

    /**
     *
     * @param campoTabela string referente ao campo da tabela
     * @param oQueSeBusca buscando por
     * @param posicaoBsuca 0 para %*% | 1 para *% | 2 para %* | 3 para =
     * @return
     */
    public List<T> buscaPersonalizada(Object campoTabela, Object oQueSeBusca, int posicaoBsuca) {
        HibernateUtil.abre();
        Query q;
        List<T> list;
        if (campoTabela.toString().equals("id")) {
            q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + " where id = "
                    + oQueSeBusca.toString());
        } else {
            if (posicaoBsuca == 0) {
                q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + "  where "
                        + campoTabela.toString() + " like '%"
                        + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 1) {
                q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + "  where "
                        + campoTabela.toString() + " like '"
                        + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 2) {
                q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + "  where "
                        + campoTabela.toString() + " like '%"
                        + oQueSeBusca.toString() + "'");
            } else {
                q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + "  where "
                        + campoTabela.toString() + " = '"
                        + oQueSeBusca.toString() + "'");
            }
        }
        list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public List buscaPersonalizada(String nomeTabela, Object campoTabela, Object oQueSeBusca, int posicaoBsuca) {
        HibernateUtil.abre();
        Query q;
        List list;
        if (campoTabela.toString().equals("id")) {
            q = HibernateUtil.getSessao().createQuery("from " + nomeTabela + " where id = "
                    + oQueSeBusca.toString());
        } else {
            if (posicaoBsuca == 0) {
                q = HibernateUtil.getSessao().createQuery("from " + nomeTabela + "  where "
                        + campoTabela.toString() + " like '%"
                        + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 1) {
                q = HibernateUtil.getSessao().createQuery("from " + nomeTabela + "  where "
                        + campoTabela.toString() + " like '"
                        + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 2) {
                q = HibernateUtil.getSessao().createQuery("from " + nomeTabela + "  where "
                        + campoTabela.toString() + " like '%"
                        + oQueSeBusca.toString() + "'");
            } else {
                q = HibernateUtil.getSessao().createQuery("from " + nomeTabela + "  where "
                        + campoTabela.toString() + " = '"
                        + oQueSeBusca.toString() + "'");
            }
        }
        list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public List buscaPersonalizada(String nomeTabela, Object campoTabela, Object oQueSeBusca, Object campoTabela2, Object oQueSeBusca2) {
        HibernateUtil.abre();
        Query q;
        List list;
        q = HibernateUtil.getSessao().createQuery("from " + nomeTabela + "  where " + campoTabela.toString() + " = '" + oQueSeBusca.toString() + "' and "
                + campoTabela2 + " = '" + oQueSeBusca2 + "' ");
        list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    /**
     *
     * @param campoTabela string referente ao campo da tabela
     * @param oQueSeBusca buscando por
     * @param posicaoBsuca 0 para %*% | 1 para *% | 2 para %* | 3 para =
     * @return
     */
    @Deprecated
    public List<T> buscaPersonalizada(Object campoTabela, Object oQueSeBusca, int posicaoBsuca, String nomeClasse) {


        HibernateUtil.abre();
        Query q;
        List<T> list;
        if (campoTabela.toString().equals("id")) {
            q = HibernateUtil.getSessao().createQuery("from " + nomeClasse + " where id = "
                    + oQueSeBusca.toString());
        } else {
            if (posicaoBsuca == 0) {
                q = HibernateUtil.getSessao().createQuery("from " + nomeClasse + "  where " + campoTabela.toString() + " like '%" + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 1) {
                q = HibernateUtil.getSessao().createQuery("from " + nomeClasse + "  where " + campoTabela.toString() + " like '" + oQueSeBusca.toString() + "%'");
            } else if (posicaoBsuca == 2) {
                q = HibernateUtil.getSessao().createQuery("from " + nomeClasse + "  where " + campoTabela.toString() + " like '%" + oQueSeBusca.toString() + "'");
            } else {
                q = HibernateUtil.getSessao().createQuery("from " + nomeClasse + "  where " + campoTabela.toString() + " = '" + oQueSeBusca.toString() + "'");
            }
        }
        list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    // from saidaproduto where saida_id = x and produto id = x
    public T buscaPersonalizada2(long idSaida, long idProduto) {
        HibernateUtil.abre();
        Query q;
        List<T> list;
        q = HibernateUtil.getSessao().createQuery("from " + classe.getName() + "  where saida_id = " + idSaida + " and produto_id = " + idProduto);
        list = q.list();
        HibernateUtil.fecha();
        return list.get(0);
    }
}
