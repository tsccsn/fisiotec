/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.portal;

import cp.portal.CPTurma;
import cp.portal.CPTurmaAlunos;
import cp.portal.usuarios.CPAluno;
import dao.DaoGenerico;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago-Asus
 */
public class DaoTurmaAluno {

    private static DaoGenerico<CPTurmaAlunos> daoTA = new DaoGenerico<>(CPTurmaAlunos.class);

    public static void salva(CPTurmaAlunos t) {
        System.out.println("salvando o aluno "+t.getAluno().getNomeCompleto()+" na sua nova turma: "+t.getTurma().getCodigoTurma());
        CPTurmaAlunos n = new CPTurmaAlunos();
        n.setAluno(t.getAluno());
        n.setTurma(t.getTurma());
        n.setDataEntrou(UtilData.getDataTimestamp());
        n.setVigente(true);
        daoTA.salvar(n);
    }

    public static CPTurmaAlunos mergeII(CPTurmaAlunos t) {
        CPTurmaAlunos n = new CPTurmaAlunos();
        n.setAluno(t.getAluno());
        n.setTurma(t.getTurma());
        n.setDataEntrou(UtilData.getDataTimestamp());
        n.setVigente(true);
        daoTA.merge(n);
        return (CPTurmaAlunos) daoTA.mergeII(n);
    }

    public static void fimVigencia(CPTurmaAlunos t) {
        t.setVigente(false);
        t.setDataSaiu(UtilData.getDataTimestamp());
        daoTA.merge(t);
    }

    public static void deleta(CPTurmaAlunos turmaAlunos) {
        daoTA.delete(turmaAlunos);
    }

    public static CPTurma turmaAtualDoAluno(CPAluno aluno) {
        HibernateUtil.abre();
        String sql = "from CPTurmaAlunos where aluno.id = " + aluno.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return ((CPTurmaAlunos) list.get(0)).getTurma();
        }
    }

    public static List<CPTurmaAlunos> buscaPorTurmaVigente(CPTurma turma) {
        HibernateUtil.abre();
        String sql = "from CPTurmaAlunos where turma.id = " + turma.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public static CPTurmaAlunos buscaPorAlunoVigente(CPAluno aluno) {
        HibernateUtil.abre();
        String sql = "from CPTurmaAlunos where aluno.id = " + aluno.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return (CPTurmaAlunos) list.get(0);
        }
    }
    public static List<CPTurmaAlunos> getAllByAluno(CPAluno aluno) {
        HibernateUtil.abre();
        String sql = "from CPTurmaAlunos where aluno.id = " + aluno.getId() + " ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }

    public static List<CPAluno> alunosDeDeterminadaTurma(CPTurma turma) {
        List<CPTurmaAlunos> buscaPorTurma = buscaPorTurmaVigente(turma);
        List<CPAluno> alunos = new LinkedList<>();
        for (CPTurmaAlunos xTurmaAlunos : buscaPorTurma) {
            alunos.add(xTurmaAlunos.getAluno());
        }
        return alunos;
    }

    public void desfazRelacaoAlunoTurma(CPAluno aluno) {
        CPTurmaAlunos buscaPorAluno = buscaPorAlunoVigente(aluno);
        deleta(buscaPorAluno);
    }

    public static List<CPTurmaAlunos> turmasAnteriores(CPAluno a) {
        HibernateUtil.abre();
        String sql = "from CPTurmaAlunos where aluno.id = " + a.getId() + " and vigente = false ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        return list;
    }
}