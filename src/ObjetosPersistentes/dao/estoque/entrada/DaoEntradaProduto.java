/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.estoque.entrada;

import cp.estoque.CPProduto;
import cp.estoque.entrada.CPEntrada;
import cp.estoque.entrada.CPEntradaProduto;
import dao.DaoGenerico;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago-Asus
 */
@SuppressWarnings("unchecked")
public class DaoEntradaProduto {

    private static DaoGenerico<CPEntradaProduto> daoEP = new DaoGenerico<>(CPEntradaProduto.class);

    public static void merge(CPEntradaProduto e) {
        System.out.println("entrando o produto: "+e.getProduto().toString());
        System.out.println("quantidade de : "+e.getQuantidade());
        System.out.println("quantidade atual : "+e.getProduto().getQuantidadeEmStoque());
        System.out.println("quantidade nova : "+e.getProduto().getQuantidadeEmStoque() + e.getQuantidade() );
        e.getProduto().setQuantidadeEmStoque(e.getProduto().getQuantidadeEmStoque() + e.getQuantidade() );
        daoEP.merge(e);
    }

    public static void delete(CPEntradaProduto e) {
        daoEP.delete(e);
    }

    public static List<CPEntradaProduto> cpEntradaProdutoDeDeterminadoRegistroEntrada(CPEntrada entrada) {
        return daoEP.buscaPersonalizada("entrada_id", entrada.getId(), 3);
    }

    public static List<CPEntradaProduto> buscaPorProduto(CPProduto p) {
        HibernateUtil.abre();
        String sql = "from CPEntradaProduto cp where cp.produto.id = " + p.getId();
        Query q = HibernateUtil.getSessao().createQuery(sql);
        try {
            List<CPEntradaProduto> lista = (List<CPEntradaProduto>) q.list();
            return lista;
        } catch (IndexOutOfBoundsException e) {
            return null;
        } finally {
            HibernateUtil.fecha();
        }
    }

    public static List<CPEntradaProduto> produtosMovimentadosNoPeriodo(List<CPProduto> produtosMovimentados, Date de, Date ate) {
        List<Long> idsProdutos = new LinkedList<>();
        for (CPProduto p : produtosMovimentados) {
            idsProdutos.add(p.getId());
        }
        HibernateUtil.abre();
        String sql = "from CPEntradaProduto ep where ep.entrada.dataEntrada > :de and ep.entrada.dataEntrada < "
                + ":ate and ep.produto.id in (:produto) order by ep.entrada.dataEntrada ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        q.setParameterList("produto", idsProdutos);
        q.setTimestamp("de", de);
        q.setTimestamp("ate", ate);
        List<CPEntradaProduto> lista = q.list();
        HibernateUtil.fecha();
        return lista;
    }
}
