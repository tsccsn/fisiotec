/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal.usuarios;

import cp.portal.registroLogin.CPRegistroLoginAdministrador;
import cp.portal.usuarios.CPAdministrador;
import dao.DaoGenerico;
import dao.portal.registroLogin.DaoRegistroLoginAdministrador;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
public class DaoAdministrador {

    private static DaoGenerico<CPAdministrador> daoAd = new DaoGenerico<>(CPAdministrador.class);

    public static void merge(CPAdministrador u) {
        u = (CPAdministrador) UtilString.setaPrimeiroESegundoNomeNosUsuarios(u);
        daoAd.merge(u);
    }

    public static CPAdministrador mergeII(CPAdministrador u) {
        return (CPAdministrador) daoAd.mergeII((CPAdministrador) u);
    }

    private static CPRegistroLoginAdministrador zeraTentativaLoginERegistraLogin(CPAdministrador a) {
        List<CPAdministrador> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.isContaAtiva()) {
            a.setTentativasLogin(0);
        }
        merge(a);
        CPRegistroLoginAdministrador r = new CPRegistroLoginAdministrador();
        r.setUsuario((CPAdministrador) a);
        r.setHoraLogado(UtilData.getDataTimestamp());
        DaoRegistroLoginAdministrador.merge(r);
        return r;
    }

    private static CPAdministrador addTentativaLogin(CPAdministrador a) {
        List<CPAdministrador> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.getTentativasLogin() < 5) {
            if (a.getTentativasLogin() == 4) {
                a.setContaAtiva(false);
            }
            a.setTentativasLogin(a.getTentativasLogin() + 1);
        }
        return mergeII(a);
    }

    public static int getNumeroTentativas(CPAdministrador a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).getTentativasLogin();
    }

    public static boolean getContaAtiva(CPAdministrador a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).isContaAtiva();
    }

    public static CPRegistroLoginAdministrador validaLogin(CPAdministrador u) {
        HibernateUtil.abre();
        String sql = "from " + UtilString.getNomeClasse(u) + " c where c.login = '" + u.getLogin() + "' and c.senha = '" + u.getSenha() + "' ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPAdministrador> list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {//errou senha
            addTentativaLogin(u);
            return null;
        } else { // acertou senha
            return zeraTentativaLoginERegistraLogin(u);
        }
    }

    public static void desbloqueiaConta(CPAdministrador u) {
        if (existEmail(u)) {
            u = getByEmailAdministrador(new CPAdministrador(null, null, u.getEmailPrincipal()));
        } else {
            u = getByLoginAdministrador(new CPAdministrador(u.getLogin(), null, null));
        }
        u.setTentativasLogin(0);
        u.setContaAtiva(true);
        merge(u);
    }

    public static CPAdministrador getByIDAdministrador(long i) {
        return (CPAdministrador) daoAd.getByIdClass(i, UtilString.getNomeClasse(CPAdministrador.class));
    }

    public static CPAdministrador getByEmailAdministrador(CPAdministrador a) {
        return (CPAdministrador) daoAd.buscaPersonalizada("emailPrincipal", a.getEmailPrincipal(), 3).get(0);
    }

    public static CPAdministrador getByEmailAdministrador(String a) {
        return (CPAdministrador) daoAd.buscaPersonalizada("emailPrincipal", a, 3).get(0);
    }

    public static CPAdministrador getByLoginAdministrador(CPAdministrador a) {
        return (CPAdministrador) daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0);
    }

    public static CPAdministrador getByLoginAdministrador(String a) {
        return (CPAdministrador) daoAd.buscaPersonalizada("login", a, 3).get(0);
    }

    public static boolean existLogin(CPAdministrador u) {
        if (daoAd.buscaPersonalizada("login", u.getLogin(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean existEmail(CPAdministrador u) {
        if (daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static CPAdministrador getByLogin(CPAdministrador u) {
        return (CPAdministrador) daoAd.buscaPersonalizada("login", u.getLogin(), 3).get(0);
    }

    public static CPAdministrador getByEmail(CPAdministrador u) {
        return (CPAdministrador) daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).get(0);
    }
}
