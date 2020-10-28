/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.listas.portal;

import beans.listas.BeanListPontoEstagio;
import cp.CPPontoDeEstagio;
import cp.CPProfessorPonto;
import cp.portal.usuarios.CPProfessor;
import dao.DaoProfessorPonto;
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
@ManagedBean(name = "listaDeProfessores")
@ViewScoped
public class BeanListProfessores implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private CPProfessor professor = new CPProfessor();
    private CPProfessor professorManipulado = new CPProfessor();
    private List<CPProfessor> professores = DaoProfessor.getAll();
    private List<CPProfessor> professoresManipulados = professores;
    private String nomeProfessor;
    
    public void filtroHistorico(BeanListPontoEstagio lp) {
        professoresManipulados = new LinkedList<>();
        for (CPProfessor xProfessor : professores) {
            //valida pelo nome
            boolean nome = false;
            if (nomeProfessor.isEmpty()) {
                nome = true;
            } else {
                if (xProfessor.getNomeCompleto().toLowerCase().startsWith(nomeProfessor.toLowerCase())) {
                    nome = true;
                }
            }
            //valida pelo ponto atual
            boolean ponto = false;
            if (lp.getIndexDosPontosSelecione().isEmpty()) {
                ponto = true;
            } else if (lp.getIndexDosPontosSelecione().contains("qualquer")) {
                ponto = true;
            } else if (pontoAtualDoProfessor(xProfessor) != null) {
                if (lp.getIndexDosPontosSelecione().contains(pontoAtualDoProfessor(xProfessor).getId() + "")) {
                    ponto = true;
                }
            }

            //valida pelos pontos ateriores
            boolean pontosAnteriores = false;
            List<CPProfessorPonto> pontosAtenrioresProf = pontosAnteriores(xProfessor);
            if (lp.getIndexDosPontosSelecioneII().isEmpty()) {
                pontosAnteriores = true;
            } else if (lp.getIndexDosPontosSelecioneII().contains("qualquer")) {
                pontosAnteriores = true;
            } else if (pontosAtenrioresProf != null) {
                List<String> pontosPassadoPeloProfessor = new LinkedList<>();
                for (CPProfessorPonto xProfessorPonto : pontosAtenrioresProf) {
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
            if (ponto && nome && pontosAnteriores) {
                professoresManipulados.add(xProfessor);
            }
        }
        ordenaManipulados();
        UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", professoresManipulados.size() + ""));
    }
    
    public void filtrar(BeanListPontoEstagio lp) {
        professoresManipulados = new LinkedList<>();
        for (CPProfessor xProfessor : professores) {
            //valida pelo nome
            boolean nome = false;
            if (nomeProfessor.isEmpty()) {
                nome = true;
            } else {
                if (xProfessor.getNomeCompleto().toLowerCase().startsWith(nomeProfessor.toLowerCase())) {
                    nome = true;
                }
            }
            //valida pelo ponto
            boolean ponto = false;
            if (lp.getIndexDosPontosSelecione().isEmpty()) {
                ponto = true;
            } else if (lp.getIndexDosPontosSelecione().contains("qualquer")) {
                ponto = true;
            } else if (pontoAtualDoProfessor(xProfessor) != null) {
                if (lp.getIndexDosPontosSelecione().contains(pontoAtualDoProfessor(xProfessor).getId() + "")) {
                    ponto = true;
                }
            }
            
            
            if (ponto && nome) {
                professoresManipulados.add(xProfessor);
            }
        }
        ordenaManipulados();
        UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", professoresManipulados.size() + ""));
    }
    
    public List<String> autoCompleta(String s) {
        List<String> resultado = new LinkedList<>();
        for (CPProfessor xProfessor : professores) {
            if (xProfessor.getNomeCompleto().toLowerCase().startsWith(s.toLowerCase())) {
                resultado.add(xProfessor.getNomeCompleto());
            }
        }
        return resultado;
    }
    
    public void ordenaManipulados() {
        OrdenaUsuarios p = new OrdenaUsuarios();
        Collections.sort(professoresManipulados, p);
    }
    
    public void ordena() {
        OrdenaUsuarios p = new OrdenaUsuarios();
        Collections.sort(professoresManipulados, p);
    }
    
    public CPPontoDeEstagio pontoAtualDoProfessor(CPProfessor prof) {
        return DaoProfessorPonto.pontoAtualDoProfessor(prof);
    }
    
    public List<CPProfessorPonto> pontosAnteriores(CPProfessor xProfessor) {
        return DaoProfessorPonto.professorPontoAnteriores(xProfessor);
    }
    
    public List<CPProfessor> getProfessores() {
        return professores;
    }
    
    public CPProfessor getProfessor() {
        return professor;
    }
    
    public void setProfessor(CPProfessor professor) {
        this.professor = professor;
    }
    
    public CPProfessor getProfessorManipulado() {
        return professorManipulado;
    }
    
    public void setProfessorManipulado(CPProfessor professorManipulado) {
        this.professorManipulado = professorManipulado;
    }
    
    public String getNomeProfessor() {
        return nomeProfessor;
    }
    
    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
    
    public List<CPProfessor> getProfessoresManipulados() {
        return professoresManipulados;
    }
}
