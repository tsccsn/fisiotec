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
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago-Asus
 */
public class DaoAdministradorEstoque {

    private static DaoGenerico<CPAdministradorEstoque> daoAd = new DaoGenerico<>(CPAdministradorEstoque.class);

    public static void merge(CPAdministradorEstoque a) {
        a = (CPAdministradorEstoque) UtilString.setaPrimeiroESegundoNomeNosUsuarios(a);
        daoAd.merge(a);
    }

    public static List<CPAdministradorEstoque> getAll() {
        return daoAd.getAll();

    }

    public static CPAdministradorEstoque mergeII(CPAdministradorEstoque u) {
        return (CPAdministradorEstoque) daoAd.mergeII((CPAdministradorEstoque) u);
    }

    private static CPRegistroLoginAdministradorEstoque zeraTentativaLoginERegistraLogin(CPAdministradorEstoque a) {
        List<CPAdministradorEstoque> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.isContaAtiva()) {
            a.setTentativasLogin(0);
        }
        merge(a);
        CPRegistroLoginAdministradorEstoque r = new CPRegistroLoginAdministradorEstoque();
        r.setUsuario((CPAdministradorEstoque) a);
        r.setHoraLogado(UtilData.getDataTimestamp());
        r = DaoRegistroLoginAdministradorEstoque.mergII(r);
        return r;
    }

    private static CPAdministradorEstoque addTentativaLogin(CPAdministradorEstoque a) {
        List<CPAdministradorEstoque> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.getTentativasLogin() < 5) {
            if (a.getTentativasLogin() == 4) {
                a.setContaAtiva(false);
            }
            a.setTentativasLogin(a.getTentativasLogin() + 1);
        }
        return mergeII(a);
    }

    public static int getNumeroTentativas(CPAdministradorEstoque a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).getTentativasLogin();
    }

    public static boolean getContaAtiva(CPAdministradorEstoque a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).isContaAtiva();
    }

    public static CPRegistroLoginAdministradorEstoque validaLogin(CPAdministradorEstoque u) {
        HibernateUtil.abre();
        String sql = "from CPAdministradorEstoque c where c.login = '" + u.getLogin() + "' and c.senha = '" + u.getSenha() + "' ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPAdministradorEstoque> list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {//errou senha
            addTentativaLogin(u);
            return null;
        } else { // acertou senha
            return zeraTentativaLoginERegistraLogin(u);
        }
    }

    public static void desbloqueiaConta(CPAdministradorEstoque u) {
        if (existEmail(u)) {
            u = getByEmailAdministrador(new CPAdministradorEstoque(null, null, u.getEmailPrincipal()));
        } else {
            u = getByLoginAdministrador(new CPAdministradorEstoque(u.getLogin(), null, null));
        }
        u.setTentativasLogin(0);
        u.setContaAtiva(true);
        merge(u);
    }

    public static CPAdministradorEstoque getByIDAdministrador(long i) {
        return (CPAdministradorEstoque) daoAd.getByIdClass(i, UtilString.getNomeClasse(CPAdministradorEstoque.class));
    }

    public static CPAdministradorEstoque getByEmailAdministrador(CPAdministradorEstoque a) {
        return (CPAdministradorEstoque) daoAd.buscaPersonalizada("emailPrincipal", a.getEmailPrincipal(), 3).get(0);
    }

    public static CPAdministradorEstoque getByEmailAdministrador(String a) {
        return (CPAdministradorEstoque) daoAd.buscaPersonalizada("emailPrincipal", a, 3).get(0);
    }

    public static CPAdministradorEstoque getByLoginAdministrador(CPAdministradorEstoque a) {
        return (CPAdministradorEstoque) daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0);
    }

    public static CPAdministradorEstoque getByLoginAdministrador(String a) {
        return (CPAdministradorEstoque) daoAd.buscaPersonalizada("login", a, 3).get(0);
    }

    public static boolean existLogin(CPAdministradorEstoque u) {
        if (daoAd.buscaPersonalizada("login", u.getLogin(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean existEmail(CPAdministradorEstoque u) {
        System.out.println("conferindo se existe o email: " + u.getEmailPrincipal());
        if (daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean existEmail(String prEmail) {
        if (daoAd.buscaPersonalizada("emailPrincipal", prEmail, 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static CPAdministradorEstoque getByLogin(CPAdministradorEstoque u) {
        return (CPAdministradorEstoque) daoAd.buscaPersonalizada("login", u.getLogin(), 3).get(0);
    }

    public static CPAdministradorEstoque getByEmail(CPAdministradorEstoque u) {
        return (CPAdministradorEstoque) daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).get(0);
    }
}
