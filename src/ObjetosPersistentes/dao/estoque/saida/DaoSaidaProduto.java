/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.estoque.saida;

import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import cp.estoque.saida.CPSaida;
import cp.estoque.saida.CPSaidaProduto;
import dao.DaoGenerico;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoSaidaProduto {

    public static DaoGenerico<CPSaidaProduto> daoSP = new DaoGenerico<>(CPSaidaProduto.class);

    public static void merge(CPSaidaProduto sp) {
        sp.getProduto().setQuantidadeEmStoque(sp.getProduto().getQuantidadeEmStoque() - sp.getQuantidade());
        daoSP.merge(sp);
    }

    public static void delete(CPSaidaProduto p) {
        daoSP.delete(p);
    }

    public static List<CPSaidaProduto> cpSaidaProdutoDeDeterminadoRegistroSaida(CPSaida saida) {
        return daoSP.buscaPersonalizada("saida_id", saida.getId(), 3);
    }

    public static List<CPSaidaProduto> getAll() {
        return daoSP.getAll();
    }

    public static CPSaidaProduto getByID(long id) {
        return daoSP.getById(id);
    }

    public static List<CPSaidaProduto> getListaDeProdutosDeUmaSaida(long id) {
        //from  saidaproduto where saida_id = 2 and produto id = x
        return daoSP.buscaPersonalizada("saida_id", id, 3);
    }

    public static CPSaidaProduto getDeterminadoProdutoDeDeterminadaSaida(long idSaida, long idProduto) {
        //from  saidaproduto where saida_id = 2 and produto id = x
        return daoSP.buscaPersonalizada2(idSaida, idProduto);
    }

    @SuppressWarnings("unchecked")
    public static List<CPSaidaProduto> produtosMovimentadosNoPeriodo(List<CPProduto> produtosMovimentados, Date de, Date ate, List<CPPontoDeEstagio> pontos) {
        HibernateUtil.abre();
        String sql = "from CPSaidaProduto cp where cp.saida.dataSaida > :de and cp.saida.dataSaida < "
                + ":ate and cp.produto.id in (:produto) and cp.saida.destino.id in (:destino) order by cp.saida.dataSaida ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<Long> idPontos = new LinkedList<>();
        for (CPPontoDeEstagio p : pontos) {
            idPontos.add(p.getId());
        }
        List<Long> idProdutos = new LinkedList<>();
        for (CPProduto p : produtosMovimentados) {
            idProdutos.add(p.getId());
        }
        q.setParameterList("produto", idProdutos);
        q.setParameterList("destino", idPontos);
        q.setTimestamp("de", de);
        q.setTimestamp("ate", ate);
        List<CPSaidaProduto> lista = q.list();
        HibernateUtil.fecha();
        return lista;
    }

    public static CPSaidaProduto ultimaMovimentacaoDeProduto(CPProduto produto, Timestamp ate) {
        HibernateUtil.abre();
        ate = UtilData.fimDia(ate);
        String sql = "from CPSaidaProduto cp where cp.saida.dataSaida < :ate and cp.produto.id  = :produto order by cp.saida.dataSaida ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        q.setLong("produto", produto.getId());
        q.setTimestamp("ate", new Timestamp(UtilData.fimDia(ate).getTime()));
        try {
            CPSaidaProduto lista = (CPSaidaProduto) q.list().get(0);
            return lista;
        } catch (IndexOutOfBoundsException e) {
            return null;
        } finally {
            HibernateUtil.fecha();
        }
    }

    public static CPSaidaProduto buscaPorProduto(CPProduto p) {
        HibernateUtil.abre();
        String sql = "from CPSaidaProduto cp where cp.produto.id = " + p.getId();
        Query q =  HibernateUtil.getSessao().createQuery(sql);
        try {
            CPSaidaProduto lista = (CPSaidaProduto) q.list().get(0);
            return lista;
        } catch (IndexOutOfBoundsException e) {
            return null;
        } finally {
            HibernateUtil.fecha();
        }
    }

    public static List<CPSaidaProduto> buscaPorProdutos(CPProduto p) {
        HibernateUtil.abre();
        String sql = "from CPSaidaProduto cp where cp.produto.id = " + p.getId();
        Query q =  HibernateUtil.getSessao().createQuery(sql);
        try {
            List<CPSaidaProduto> lista = (List<CPSaidaProduto>) q.list();
            return lista;
        } catch (IndexOutOfBoundsException e) {
            return null;
        } finally {
            HibernateUtil.fecha();
        }
    }
}
