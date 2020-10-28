/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.estoque.login;

import cp.estoque.CPAdministradorEstoque;
import cp.estoque.registroLogin.CPRegistroLoginAdministradorEstoque;
import dao.DaoGenerico;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago-Asus
 */
public class DaoRegistroLoginAdministradorEstoque {

    private static DaoGenerico<CPRegistroLoginAdministradorEstoque> dao = new DaoGenerico<>(CPRegistroLoginAdministradorEstoque.class);

    public static void merg(CPRegistroLoginAdministradorEstoque e) {
        dao.merge(e);
    }
    public static CPRegistroLoginAdministradorEstoque mergII(CPRegistroLoginAdministradorEstoque e) {
        return (CPRegistroLoginAdministradorEstoque) dao.mergeII(e);
    }

    public static List<CPRegistroLoginAdministradorEstoque> registrosDeLogin(CPAdministradorEstoque a, int quantidadeDeResultados) {
        HibernateUtil.abre();
        String sql = "from CPRegistroLoginAdministradorEstoque where usuario.id = " + a.getId() + " order by horaLogado desc ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        q.setMaxResults(quantidadeDeResultados);
        List<CPRegistroLoginAdministradorEstoque> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}
