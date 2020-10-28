/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal;

import cp.portal.CPPedidoAlteracaoNota;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPProfessor;
import dao.DaoGenerico;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoPedidoDeAlteracaoDeNota {

    private static DaoGenerico<CPPedidoAlteracaoNota> daoPAN = new DaoGenerico<>(CPPedidoAlteracaoNota.class);

    public static void merge(CPPedidoAlteracaoNota e) {
        daoPAN.merge(e);
    }

    public static void deleta(CPPedidoAlteracaoNota e) {
        daoPAN.delete(e);
    }
    public static List<CPPedidoAlteracaoNota> getAll(){
        HibernateUtil.abre();
        String sql = "from CPPedidoAlteracaoNota where dataRespondido";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPPedidoAlteracaoNota> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    public static List<CPPedidoAlteracaoNota> getAllPendentes(){
        HibernateUtil.abre();
        String sql = "from CPPedidoAlteracaoNota where dataRespondido is null";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPPedidoAlteracaoNota> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
    public static List<CPPedidoAlteracaoNota> getAllRespondidos(){
        HibernateUtil.abre();
        String sql = "from CPPedidoAlteracaoNota where dataRespondido is null";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPPedidoAlteracaoNota> list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public static List<CPPedidoAlteracaoNota> getAllByProfessor(CPProfessor p) {
        HibernateUtil.abre();
        String sql = "from CPPedidoAlteracaoNota where professor.id = " + p.getId() + " ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPPedidoAlteracaoNota> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}
