/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos.portal.usuarios;

import cp.CPAlunoPonto;
import cp.CPPontoDeEstagio;
import cp.CPProfessorPonto;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.DaoProfessorPonto;
import dao.portal.DaoAlunoPonto;
import dao.portal.registroLogin.DaoRegistroLoginProfessor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class OBJProfessor implements Serializable {

    private CPProfessor objProfessor = new CPProfessor();
    private CPProfessor objProfessorManipulado = new CPProfessor();
    private CPPontoDeEstagio objPontoAtual = new CPPontoDeEstagio();
    private CPRegistroLoginProfessor objRegistroLoginAtual = new CPRegistroLoginProfessor();
    private HashMap<Long, CPProfessorPonto> listProfessorPontosAnteriores = new HashMap<>();
    private HashMap<Long, CPPontoDeEstagio> listPontosAnteriores = new HashMap<>();
    private HashMap<Long, CPRegistroLoginProfessor> listRegistroDeLogin = new HashMap<>();
    private HashMap<Long, CPAlunoPonto> listAlunoPonto = new HashMap<>();
    private HashMap<Long, CPAluno> listAlunosAtuais = new HashMap<>();

    public OBJProfessor() {
    }
    
    public void carregaPontosAnteriores() {
        List<CPProfessorPonto> pontosAnteriores = DaoProfessorPonto.professorPontoAnteriores(objProfessor);
        for (CPProfessorPonto xProfessorPonto : pontosAnteriores) {
            listPontosAnteriores.put(xProfessorPonto.getPonto().getId(), xProfessorPonto.getPonto());
        }
    }

    public void carregaProfessorPontoAnteriores() {
        List<CPProfessorPonto> pontosAnteriores = DaoProfessorPonto.professorPontoAnteriores(objProfessor);
        for (CPProfessorPonto xProfessorPonto : pontosAnteriores) {
            listProfessorPontosAnteriores.put(xProfessorPonto.getId(), xProfessorPonto);
        }
    }

    public void carregaRegistroDeLogins(long tamanho) {
        List<CPRegistroLoginProfessor> ultimosLogins = DaoRegistroLoginProfessor.ultimosLogins(objProfessor, (int) tamanho);
        for (CPRegistroLoginProfessor xRegistro : ultimosLogins) {
            listRegistroDeLogin.put(xRegistro.getId(), xRegistro);
        }
    }

    public void carregaAlunoPonto() {
        List<CPAlunoPonto> buscaCPAlunoPorPonto = new ArrayList<>();
        if (objPontoAtual.getId() > 0) {
            buscaCPAlunoPorPonto = DaoAlunoPonto.buscaCPAlunoPorPonto(objPontoAtual);
        }
        for (CPAlunoPonto xAlunoPonto : buscaCPAlunoPorPonto) {
            listAlunoPonto.put(xAlunoPonto.getId(), xAlunoPonto);
        }
    }

    public void carregaAlunosAtuais() {
        List<CPAlunoPonto> buscaCPAlunoPorPonto = new ArrayList<>();
        if (objPontoAtual.getId() > 0) {
            buscaCPAlunoPorPonto = DaoAlunoPonto.buscaCPAlunoPorPonto(objPontoAtual);
        }
        for (CPAlunoPonto xAlunoPonto : buscaCPAlunoPorPonto) {
            listAlunosAtuais.put(xAlunoPonto.getAluno().getId(), xAlunoPonto.getAluno());
        }
    }

    // # GET's TRABALHADOS
    public CPProfessor getObjProfessor() {
        return objProfessor;
    }

    public CPPontoDeEstagio getObjPontoAtual() {
        return DaoProfessorPonto.pontoAtualDoProfessor(objProfessor);
    }

    public Collection<CPPontoDeEstagio> getListPontosAnteriores() {
        return listPontosAnteriores.values();
    }

    public Collection<CPProfessorPonto> getListProfessorPontosAnteriores() {
        return listProfessorPontosAnteriores.values();
    }

    public Collection<CPRegistroLoginProfessor> getListRegistroDeLogin() {
        return listRegistroDeLogin.values();
    }

    // # GET's SIMPLES
    public CPProfessor getObjProfessorManipulado() {
        return objProfessorManipulado;
    }

    public CPRegistroLoginProfessor getObjRegistroLoginAtual() {
        return objRegistroLoginAtual;
    }
    
    // # SET's SIMPLES

    public void setObjProfessor(CPProfessor objProfessor) {
        this.objProfessor = objProfessor;
    }

    public void setObjProfessorManipulado(CPProfessor objProfessorManipulado) {
        this.objProfessorManipulado = objProfessorManipulado;
    }

    public void setObjRegistroLoginAtual(CPRegistroLoginProfessor objRegistroLoginAtual) {
        this.objRegistroLoginAtual = objRegistroLoginAtual;
    }
    
}
