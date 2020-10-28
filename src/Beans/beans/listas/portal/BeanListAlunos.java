/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.listas.portal;

import beans.listas.BeanListPontoEstagio;
import cp.CPAlunoPonto;
import cp.CPPontoDeEstagio;
import cp.CPProfessorPonto;
import cp.estoque.CPProduto;
import cp.portal.CPTurma;
import cp.portal.CPTurmaAlunos;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.DaoProfessorPonto;
import dao.portal.DaoAlunoPonto;
import dao.portal.DaoTurmaAluno;
import dao.portal.usuarios.DaoAluno;
import dao.portal.usuarios.DaoProfessor;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.comparadores.OrdenaUsuarios;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "listaDeAlunos")
@ViewScoped
public class BeanListAlunos implements Serializable {

    private static final long serialVersionUID = 1L;
    private CPAluno aluno = new CPAluno();
    private CPAluno alunoManipulado;
    private List<CPAluno> alunos = new DaoAluno().getAll("nomeCompleto");
    private List<CPAluno> alunosManipulados = alunos;
    private CPAlunoPonto alunoPontoManipulado;
    private int indexManipulado;
    private String nomeAluno;
    private double nota = 0;

    public List<String> autoCompleta(String s) {
        List<String> resultado = new LinkedList<>();
        for (CPAluno xAluno : alunos) {
            if (xAluno.getNomeCompleto().toLowerCase().startsWith(s.toLowerCase())) {
                resultado.add(xAluno.getNomeCompleto());
            }
        }
        return resultado;
    }

    public List<CPAlunoPonto> alunosDeDeterminadoProfessor(CPProfessor xProfessor) {
        CPPontoDeEstagio pontoAtualDoProfessor = DaoProfessorPonto.pontoAtualDoProfessor(xProfessor);
        if (pontoAtualDoProfessor != null) {
            return DaoAlunoPonto.buscaCPAlunoPorPonto(pontoAtualDoProfessor);
        }
        return null;
    }
    /*
     *
     * public List<String> autoComplete(String s) { List<String> resultado = new
     * LinkedList<>(); for (CPProduto p : produtos) { if
     * (p.getNome().toLowerCase().startsWith(s.toLowerCase())) {
     * resultado.add(p.getNome()); } } return resultado; }
     */

    public void filtraAlunos(BeanListPontoEstagio lp, BeanListTurmas lt) {
        alunosManipulados = new LinkedList<>();
        for (CPAluno xAluno : alunos) {
            //por turma
            boolean turma = false;
            if (lt.getIndexTurmasSelecionadas().isEmpty()) {
                turma = true;
            } else {
                if (lt.getIndexTurmasSelecionadas().contains("qualquer")) {
                    turma = true;
                } else {
                    CPTurma turmaAtualDoAluno = turmaAtualDoAluno(xAluno);
                    if (turmaAtualDoAluno != null) {
                        if (lt.getIndexTurmasSelecionadas().contains(turmaAtualDoAluno.getId() + "")) {
                            turma = true;
                        }
                    }
                }
            }
            //por ponto
            boolean ponto = false;
            if (lp.getIndexDosPontosSelecione().isEmpty()) {
                ponto = true;
            } else {
                if (lp.getIndexDosPontosSelecione().contains("qualquer")) {
                    ponto = true;
                } else {
                    CPPontoDeEstagio pontoAtualDoAluno = pontoAtualDoAluno(xAluno);
                    if (pontoAtualDoAluno != null) {
                        if (lp.getIndexDosPontosSelecione().contains(pontoAtualDoAluno.getId() + "")) {
                            ponto = true;
                        }
                    }
                }
            }

            //por ponto anteriores
            //valida pelos pontos ateriores
            boolean pontosAnteriores = false;
            List<CPAlunoPonto> pontosAtenrioresProf = pontosAteriores(xAluno);
            if (lp.getIndexDosPontosSelecioneII().isEmpty()) {
                pontosAnteriores = true;
            } else if (lp.getIndexDosPontosSelecioneII().contains("qualquer")) {
                pontosAnteriores = true;
            } else if (pontosAtenrioresProf != null) {
                List<String> pontosPassadoPeloProfessor = new LinkedList<>();
                for (CPAlunoPonto xProfessorPonto : pontosAtenrioresProf) {
                    pontosPassadoPeloProfessor.add(xProfessorPonto.getPonto().getId() + "");
                }
                for (String xPontoSelecionado : lp.getIndexDosPontosSelecioneII()) {

                    if (pontosPassadoPeloProfessor.contains(xPontoSelecionado)) {
                        pontosAnteriores = true;
                        if (lp.getCriterioPontosAnteriores() == 1) {
                            break;
                        }
                    } else {
                        //  Tenha passado por TODOS os selecionados
                        if (lp.getCriterioPontosAnteriores() == 0) {
                            pontosAnteriores = false;
                            break;
                        }
                    }
                }
            }

            //por nome
            boolean nome = false;
            if (nomeAluno.isEmpty()) {
                nome = true;
            } else {
                if (xAluno.getNomeCompleto().toLowerCase().contains(nomeAluno.toLowerCase())) {
                    nome = true;
                }
            }
            if (ponto && turma && nome && pontosAnteriores) {
                alunosManipulados.add(xAluno);
            }
        }
        UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", alunosManipulados.size() + ""));
    }

    public List<CPTurmaAlunos> turmasAnteriores(CPAluno aluno) {
        List<CPTurmaAlunos> turmasAnteriores = DaoTurmaAluno.turmasAnteriores(aluno);
        return turmasAnteriores;
    }

    public void ordenaAlunos() {
        OrdenaUsuarios p = new OrdenaUsuarios();
        Collections.sort(alunos, p);
    }

    public CPPontoDeEstagio pontoAtualDoAluno(CPAluno aluno) {
        return DaoAlunoPonto.pontoAtualDoAluno(aluno);
    }

    public CPAlunoPonto cpAlunoPonto(CPAluno a) {
        return DaoAlunoPonto.buscaPorAluno(a);
    }

    public CPTurma turmaAtualDoAluno(CPAluno aluno) {
        return DaoTurmaAluno.turmaAtualDoAluno(aluno);
    }

    public List<CPAlunoPonto> pontosAteriores(CPAluno aluno) {
        return DaoAlunoPonto.pontosAnteriores(aluno);
    }

    // # GET e SET
    public List<CPAluno> alunosDaTurma(CPTurma turma) {
        return DaoTurmaAluno.alunosDeDeterminadaTurma(turma);
    }

    public CPAluno getAluno() {
        return aluno;
    }

    public void setAluno(CPAluno aluno) {
        this.aluno = aluno;
    }

    public List<CPAluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<CPAluno> alunos) {
        this.alunos = alunos;
    }

    public CPAluno getAlunoManipulado() {
        return alunoManipulado;
    }

    public void setAlunoManipulado(CPAluno alunoManipulado) {
        this.alunoManipulado = alunoManipulado;
    }

    public void setIndexManipulado(int indexManipulado) {
        this.indexManipulado = indexManipulado;
    }

    public int getIndexManipulado() {
        return indexManipulado;
    }

    public List<CPAluno> getAlunosManipulados() {
        return alunosManipulados;
    }

    public void setAlunosManipulados(List<CPAluno> alunosManipulados) {
        this.alunosManipulados = alunosManipulados;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nome) {
        this.nomeAluno = nome;
    }

    public CPAlunoPonto getAlunoPontoManipulado() {
        return alunoPontoManipulado;
    }

    public void setAlunoPontoManipulado(CPAlunoPonto alunoPontoManipulado) {
        this.alunoPontoManipulado = alunoPontoManipulado;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
