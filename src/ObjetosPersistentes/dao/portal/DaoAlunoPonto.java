/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.portal;

import cp.CPAlunoPonto;
import cp.CPPontoDeEstagio;
import cp.portal.usuarios.CPAluno;
import dao.DaoGenerico;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoAlunoPonto {

    private static DaoGenerico<CPAlunoPonto> daoTA = new DaoGenerico<>(CPAlunoPonto.class);

    public static void deleta(CPAlunoPonto e ){
        daoTA.delete(e);
    }
    
    public static void salvar(CPAlunoPonto alunoPonto) {
        CPAlunoPonto c = new CPAlunoPonto();
        c.setAluno(alunoPonto.getAluno());
        c.setPonto(alunoPonto.getPonto());
        c.setDataEntrou(UtilData.getDataTimestamp());
        c.setVigente(true);
        c.setNota(-1d);
        daoTA.merge(c);
    }

    public static CPAlunoPonto mergeII(CPAlunoPonto alunoPonto) {
        alunoPonto.setDataEntrou(UtilData.getDataTimestamp());
        alunoPonto.setVigente(true);
        alunoPonto.setNota(-1d);
        return (CPAlunoPonto) daoTA.mergeII(alunoPonto);
    }

    public static void fimVigencia(CPAlunoPonto p) {
        p.setVigente(false);
        p.setDataSaiu(UtilData.getDataTimestamp());
        daoTA.merge(p);
    }

    public static List<CPAlunoPonto> getAllAluno(CPAluno aluno) {
        HibernateUtil.abre();
        String sql = "from CPAlunoPonto where aluno.id = " + aluno.getId() + "  ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public static CPAlunoPonto buscaPorAluno(CPAluno aluno) {
        HibernateUtil.abre();
        String sql = "from CPAlunoPonto where aluno.id = " + aluno.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return ((CPAlunoPonto) list.get(0));
        }
    }

    public static CPAlunoPonto buscaPorPonto(CPPontoDeEstagio ponto) {
        HibernateUtil.abre();
        String sql = "from CPAlunoPonto where ponto.id = " + ponto.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return ((CPAlunoPonto) list.get(0));
        }
    }
    public static List<CPAlunoPonto> buscaCPAlunoPorPonto(CPPontoDeEstagio ponto) {
        HibernateUtil.abre();
        String sql = "from CPAlunoPonto where ponto.id = " + ponto.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    public static CPPontoDeEstagio pontoAtualDoAluno(CPAluno a) {
        HibernateUtil.abre();
        String sql = "from CPAlunoPonto where aluno.id = " + a.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return ((CPAlunoPonto) list.get(0)).getPonto();
        }
    }

    public static List<CPAlunoPonto> pontosAnteriores(CPAluno a) {
        HibernateUtil.abre();
        String sql = "from CPAlunoPonto where aluno.id = " + a.getId() + " and vigente = false ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}
