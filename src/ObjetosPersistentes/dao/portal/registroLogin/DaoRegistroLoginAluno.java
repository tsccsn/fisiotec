/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal.registroLogin;

import cp.portal.registroLogin.CPRegistroLoginAluno;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.DaoGenerico;
import java.util.List;
import org.hibernate.Query;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoRegistroLoginAluno {

    private static DaoGenerico<CPRegistroLoginAluno> daoR = new DaoGenerico<CPRegistroLoginAluno>(CPRegistroLoginAluno.class);

    public static void merge(CPRegistroLoginAluno registroLoginAluno) {
        daoR.merge(registroLoginAluno);
    }

    public static void deleta(CPRegistroLoginAluno r) {
        daoR.delete(r);
    }

    public static List<CPRegistroLoginAluno> getAllByAluno(CPAluno p) {
        HibernateUtil.abre();
        String sql = "from CPRegistroLoginAluno where aluno.id = " + p.getId() + " ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPRegistroLoginAluno> list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}
