/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal.usuarios;

import cp.CPProfessorPonto;
import cp.portal.CPPedidoAlteracaoNota;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPProfessor;
import dao.DaoGenerico;
import dao.DaoProfessorPonto;
import dao.portal.DaoPedidoDeAlteracaoDeNota;
import dao.portal.registroLogin.DaoRegistroLoginProfessor;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
public class DaoProfessor {

    private static DaoGenerico<CPProfessor> daoAd = new DaoGenerico<>(CPProfessor.class);

    public static void merge(CPProfessor u) {
        u = (CPProfessor) UtilString.setaPrimeiroESegundoNomeNosUsuarios(u);
        daoAd.merge(u);
    }

    public static List<CPProfessor> getAll() {
        return daoAd.getAll("nomeCompleto");
    }

    public static CPProfessor mergeII(CPProfessor u) {
        return (CPProfessor) daoAd.mergeII((CPProfessor) u);
    }

    public static void deleta(CPProfessor professor) {
        //registro login
        List<CPRegistroLoginProfessor> registrosLogin = DaoRegistroLoginProfessor.getAllByProfessor(professor);
        for (CPRegistroLoginProfessor xRegistroLogin : registrosLogin) {
            DaoRegistroLoginProfessor.deleta(xRegistroLogin);
        }
        //pedido de alteração de nota
        List<CPPedidoAlteracaoNota> pedidosDeAlteracaoDeNotas = DaoPedidoDeAlteracaoDeNota.getAllByProfessor(professor);
        for (CPPedidoAlteracaoNota xPedido : pedidosDeAlteracaoDeNotas) {
            DaoPedidoDeAlteracaoDeNota.deleta(xPedido);
        }
        //pontos q ele andou
        List<CPProfessorPonto> pontosQueOProfAndou = DaoProfessorPonto.getAllByProfessor(professor);
        for (CPProfessorPonto xPontosQueOProfAndou : pontosQueOProfAndou) {
            DaoProfessorPonto.deleta(xPontosQueOProfAndou);
        }
        //por fim deleta o professor
        daoAd.delete(professor);
    }

    private static CPRegistroLoginProfessor zeraTentativaLoginERegistraLogin(CPProfessor a) {
        List<CPProfessor> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.isContaAtiva()) {
            a.setTentativasLogin(0);
        }
        merge(a);
        CPRegistroLoginProfessor r = new CPRegistroLoginProfessor();
        r.setProfessor(a);
        r.setHoraLogado(UtilData.getDataTimestamp());
        r = DaoRegistroLoginProfessor.mergeII(r);
        return r;
    }

    private static CPProfessor addTentativaLogin(CPProfessor a) {
        List<CPProfessor> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.getTentativasLogin() < 5) {
            if (a.getTentativasLogin() == 4) {
                a.setContaAtiva(false);
            }
            a.setTentativasLogin(a.getTentativasLogin() + 1);
        }
        return mergeII(a);
    }

    public static int getNumeroTentativas(CPProfessor a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).getTentativasLogin();
    }

    public static boolean getContaAtiva(CPProfessor a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).isContaAtiva();
    }

    public static CPRegistroLoginProfessor validaLogin(CPProfessor u) {
        HibernateUtil.abre();
        String sql = "from " + UtilString.getNomeClasse(u) + " c where c.login = '" + u.getLogin() + "' and c.senha = '" + u.getSenha() + "' ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPProfessor> list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {//errou senha
            addTentativaLogin(u);
            return new CPRegistroLoginProfessor();
        } else {// acertou senha
            return zeraTentativaLoginERegistraLogin(u);
        }
    }

    public static void desbloqueiaConta(CPProfessor u) {
        if (existEmail(u)) {
            u = getByEmailProfessor(new CPProfessor(null, null, u.getEmailPrincipal()));
        } else {
            u = getByLoginProfessor(new CPProfessor(u.getLogin(), null, null));
        }
        u.setTentativasLogin(0);
        u.setContaAtiva(true);
        merge(u);
    }

    public static CPProfessor getByIDProfessor(long i) {
        return (CPProfessor) daoAd.getByIdClass(i, UtilString.getNomeClasse(CPProfessor.class));
    }

    public static CPProfessor getByEmailProfessor(CPProfessor a) {
        return (CPProfessor) daoAd.buscaPersonalizada("emailPrincipal", a.getEmailPrincipal(), 3).get(0);
    }

    public static CPProfessor getByEmailProfessor(String a) {
        return (CPProfessor) daoAd.buscaPersonalizada("emailPrincipal", a, 3).get(0);
    }

    public static CPProfessor getByLoginProfessor(CPProfessor a) {
        return (CPProfessor) daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0);
    }

    public static CPProfessor getByLoginProfessor(String a) {
        return (CPProfessor) daoAd.buscaPersonalizada("login", a, 3).get(0);
    }

    public static boolean existLogin(CPProfessor u) {
        if (daoAd.buscaPersonalizada("login", u.getLogin(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean existEmail(CPProfessor u) {
        if (daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static CPProfessor getByLogin(CPProfessor u) {
        return (CPProfessor) daoAd.buscaPersonalizada("login", u.getLogin(), 3).get(0);
    }

    public static CPProfessor getByEmail(CPProfessor u) {
        return (CPProfessor) daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).get(0);
    }
}
