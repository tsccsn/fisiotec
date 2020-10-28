/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal.usuarios;

import cp.CPAlunoPonto;
import cp.portal.CPTurmaAlunos;
import cp.portal.registroLogin.CPRegistroLoginAluno;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPAluno;
import dao.DaoGenerico;
import dao.portal.DaoAlunoPonto;
import dao.portal.DaoTurmaAluno;
import dao.portal.registroLogin.DaoRegistroLoginAluno;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
public class DaoAluno {

    private static DaoGenerico<CPAluno> daoAd = new DaoGenerico<>(CPAluno.class);

    public static void deleta(CPAluno aluno) {
        //registros de aluno
        List<CPRegistroLoginAluno> registrosAluno = DaoRegistroLoginAluno.getAllByAluno(aluno);
        for (CPRegistroLoginAluno xRegistro : registrosAluno) {
            DaoRegistroLoginAluno.deleta(xRegistro);
        }
        //turmas
        List<CPTurmaAlunos> turmasAluno = DaoTurmaAluno.getAllByAluno(aluno);
        for (CPTurmaAlunos xTurmaAluno : turmasAluno) {
            DaoTurmaAluno.deleta(xTurmaAluno);
        }
        //pontos
        List<CPAlunoPonto> alunoPontos = DaoAlunoPonto.getAllAluno(aluno);
        for (CPAlunoPonto xAlunoPonto : alunoPontos) {
            DaoAlunoPonto.deleta(xAlunoPonto);
        }
        daoAd.delete(aluno);
    }

    public static void merge(CPAluno u) {
        u = (CPAluno) UtilString.setaPrimeiroESegundoNomeNosUsuarios(u);
        daoAd.merge(u);
    }

    public List<CPAluno> getAll(String s) {
        return daoAd.getAll(s);
    }

    public static CPAluno mergeII(CPAluno u) {
        return (CPAluno) daoAd.mergeII((CPAluno) u);
    }

    private static CPAluno zeraTentativaLoginERegistraLogin(CPAluno a) {
        List<CPAluno> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.isContaAtiva()) {
            a.setTentativasLogin(0);
        }
        merge(a);
        CPRegistroLoginAluno r = new CPRegistroLoginAluno();
        r.setUsuario(a);
        r.setHoraLogado(UtilData.getDataTimestamp());
        DaoRegistroLoginAluno.merge(r);
        return a;
    }

    private static CPAluno addTentativaLogin(CPAluno a) {
        List<CPAluno> buscaPersonalizada = daoAd.buscaPersonalizada("login", a.getLogin(), 3);
        a = buscaPersonalizada.get(0);
        if (a.getTentativasLogin() < 5) {
            if (a.getTentativasLogin() == 4) {
                a.setContaAtiva(false);
            }
            a.setTentativasLogin(a.getTentativasLogin() + 1);
        }
        return mergeII(a);
    }

    public static int getNumeroTentativas(CPAluno a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).getTentativasLogin();
    }

    public static boolean getContaAtiva(CPAluno a) {
        return daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0).isContaAtiva();
    }

    public static boolean validaLogin(CPAluno u) {
        HibernateUtil.abre();
        String sql = "from " + UtilString.getNomeClasse(u) + " c where c.login = '" + u.getLogin() + "' and c.senha = '" + u.getSenha() + "' ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List<CPAluno> list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {//errou senha
            addTentativaLogin(u);
            return false;
        } else {// acertou senha
            zeraTentativaLoginERegistraLogin(u);
            return true;
        }
    }

    public static void desbloqueiaConta(CPAluno u) {
        if (existEmail(u)) {
            u = getByEmailCPAluno(new CPAluno(null, null, u.getEmailPrincipal()));
        } else {
            u = getByLoginCPAluno(new CPAluno(u.getLogin(), null, null));
        }
        u.setTentativasLogin(0);
        u.setContaAtiva(true);
        merge(u);
    }

    public static CPAluno getByID(long i) {
        return (CPAluno) daoAd.getByIdClass(i, UtilString.getNomeClasse(CPAluno.class));
    }

    public static CPAluno getByEmailCPAluno(CPAluno a) {
        return (CPAluno) daoAd.buscaPersonalizada("emailPrincipal", a.getEmailPrincipal(), 3).get(0);
    }

    public static CPAluno getByEmailCPAluno(String a) {
        return (CPAluno) daoAd.buscaPersonalizada("emailPrincipal", a, 3).get(0);
    }

    public static CPAluno getByLoginCPAluno(CPAluno a) {
        return (CPAluno) daoAd.buscaPersonalizada("login", a.getLogin(), 3).get(0);
    }

    public static CPAluno getByLoginCPAluno(String a) {
        return (CPAluno) daoAd.buscaPersonalizada("login", a, 3).get(0);
    }

    public static boolean existLogin(CPAluno u) {
        if (daoAd.buscaPersonalizada("login", u.getLogin(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean existEmail(CPAluno u) {
        if (daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static CPAluno getByLogin(CPAluno u) {
        return (CPAluno) daoAd.buscaPersonalizada("login", u.getLogin(), 3).get(0);
    }

    public static CPAluno getByEmail(CPAluno u) {
        return (CPAluno) daoAd.buscaPersonalizada("emailPrincipal", u.getEmailPrincipal(), 3).get(0);
    }
}
