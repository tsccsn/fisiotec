/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package dao.portal;

import cp.portal.CPTurma;
import cp.portal.CPTurmaAlunos;
import cp.portal.usuarios.CPAluno;
import dao.DaoGenerico;
import java.util.List;

/**
 *
 * @author Thiago-Asus
 */
public class DaoTurma {

    private static DaoGenerico<CPTurma> daoT = new DaoGenerico<>(CPTurma.class);

    public static void merge(CPTurma t) {
        daoT.merge(t);
    }

    public static CPTurma mergeII(CPTurma t) {
        return (CPTurma) daoT.mergeII(t);
    }

    public static void deleta(CPTurma t){
        daoT.delete(t);
    }
    
    public static void ligaAlunosATurma(List<CPAluno> alunos, CPTurma turma) {
        for (CPAluno a : alunos) {
            CPTurmaAlunos ta = new CPTurmaAlunos();
            ta.setAluno(a);
            ta.setTurma(turma);
            DaoTurmaAluno.salva(ta);
        }
    }

    public static CPTurma getByID(long id) {
        return daoT.getById(id);
    }

    public static List<CPTurma> getAllOrderByCodigo() {
        return daoT.getAll("codigoTurma");
    }

    
}
