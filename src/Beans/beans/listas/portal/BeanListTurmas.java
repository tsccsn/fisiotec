/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.listas.portal;

import cp.portal.CPTurma;
import cp.portal.usuarios.CPAluno;
import dao.portal.DaoTurma;
import dao.portal.DaoTurmaAluno;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "listaDeTurmas")
@ViewScoped
public class BeanListTurmas implements Serializable {

    private static final long serialVersionUID = 1L;
    private CPTurma turma = new CPTurma();
    private List<CPTurma> turmas = DaoTurma.getAllOrderByCodigo();
    private List<CPTurma> turmasManipuladas = turmas;
    private int indexTurmaSelecionada = -1;
    private List<String> indexTurmasSelecionadas = new LinkedList<>();

    public List<String> getIndexTurmasSelecionadas() {
        return indexTurmasSelecionadas;
    }

    public void setIndexTurmasSelecionadas(List<String> indexTurmasSelecionadas) {
        this.indexTurmasSelecionadas = indexTurmasSelecionadas;
    }

    public CPTurma getTurmaSelecionada() {
        if (indexTurmaSelecionada < 0) {
            return null;
        } else {
            return turmas.get(indexTurmaSelecionada);
        }
    }

    public CPTurma turmaSelecionada() {
        if (indexTurmaSelecionada > -1) {
            return turmas.get(indexTurmaSelecionada);
        } else {
            return null;
        }

    }

    public void setTurmaByAluno(CPAluno aluno) {
        CPTurma turmaAtualDoAluno = DaoTurmaAluno.turmaAtualDoAluno(aluno);
        if (turmaAtualDoAluno != null) {
            for (CPTurma xTurma : turmas) {
                if (xTurma.getId() == turmaAtualDoAluno.getId()) {
                    indexTurmaSelecionada = turmas.indexOf(xTurma);
                    break;
                }
            }
        } else {
            indexTurmaSelecionada = -1;
        }
    }
    // # GET e SET

    public CPTurma getTurma() {
        return turma;
    }

    public void setTurma(CPTurma turma) {
        this.turma = turma;
    }

    public List<CPTurma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<CPTurma> turmas) {
        this.turmas = turmas;
    }

    public List<CPTurma> getTurmasManipuladas() {
        return turmasManipuladas;
    }

    public void setTurmasManipuladas(List<CPTurma> turmasManipuladas) {
        this.turmasManipuladas = turmasManipuladas;
    }

    public int getIndexTurmaSelecionada() {
        return indexTurmaSelecionada;
    }

    public void setIndexTurmaSelecionada(int indexTurmaSelecionada) {
        this.indexTurmaSelecionada = indexTurmaSelecionada;
    }
}
