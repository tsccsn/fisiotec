/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.estoque;

import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import cp.estoque.entrada.CPEntradaProduto;
import cp.estoque.saida.CPSaidaProduto;
import dao.DaoGenerico;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoProduto {

    private static DaoGenerico<CPProduto> daoP = new DaoGenerico<>(CPProduto.class);

    public static List<CPProduto> produtosComNivelBaixo() {
        HibernateUtil.abre();
        String sql = "from CPProduto cp where cp.quantidadeMinimaEmStoque >= cp.quantidadeEmStoque ";
        Query q =  HibernateUtil.getSessao().createQuery(sql);
        List<CPProduto> lista = (List<CPProduto>) q.list();
        HibernateUtil.fecha();
        return lista;
    }

    public static void merge(CPProduto p) {
        daoP.merge(p);
    }

    public static Object mergeII(CPProduto p) {
        return daoP.mergeII(p);
    }

    public static Object salva(CPProduto p) {
        return daoP.salvar(p);
    }

    public static void deleta(CPProduto p) {
        daoP.delete(p);
    }

    public static List<CPProduto> getAll(String ordem) {
        return daoP.getAll(ordem);
    }

    public static List<CPProduto> getAll() {
        return daoP.getAll();
    }

    public static CPProduto getById(long id) {
        return daoP.getById(id);
    }
}
