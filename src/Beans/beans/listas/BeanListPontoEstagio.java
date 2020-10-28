/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package beans.listas;

import cp.CPPontoDeEstagio;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.DaoPontoDeEstagio;
import dao.DaoProfessorPonto;
import dao.portal.DaoAlunoPonto;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.comparadores.OrdenaPontoDeEstagio;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "listaDePontos")
@ViewScoped
public class BeanListPontoEstagio implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<CPPontoDeEstagio> pontos = DaoPontoDeEstagio.getAll("nome");
    private List<CPPontoDeEstagio> pontosManipulados = new LinkedList<>();
    private List<CPPontoDeEstagio> pontosDesativados = DaoPontoDeEstagio.getAllDesativados();
    private CPPontoDeEstagio pontoManipulado = new CPPontoDeEstagio();
    private int indexDoPonto = 0;
    private int indexDoPontoManipulado = 0;
    private int indexDoPontoSelecione = -1;
    private List<String>indexDosPontosSelecione = new LinkedList<>();
    private List<String>indexDosPontosSelecioneII = new LinkedList<>();
    private int criterioPontosAnteriores = -1;

    public List<CPPontoDeEstagio> getPontosDesativados() {
        return pontosDesativados;
    }

    public void setPontosDesativados(List<CPPontoDeEstagio> pontosDesativados) {
        this.pontosDesativados = pontosDesativados;
    }

    
    public void salvaPonto(){
        DaoPontoDeEstagio.merge(pontoManipulado);
        pontoManipulado = new CPPontoDeEstagio();
        UtilMensagens.ok("Ponto de estágio cadastrado com sucesso!");
    }
    
   
    public void destaivaPonto(){
        pontos.remove(pontoManipulado);
        if(pontosManipulados.contains(pontoManipulado)){
            pontosManipulados.remove(pontoManipulado);
        }
        DaoPontoDeEstagio.destaivaPonto(pontoManipulado);
        UtilMensagens.ok("Ponto desativado.");
    }
    
    public void ativar(){
        pontosDesativados.remove(pontoManipulado);
        DaoPontoDeEstagio.ativaPonto(pontoManipulado);
        UtilMensagens.ok("Ponto ativado.");
    }
    
    public List<String> getIndexDosPontosSelecione() {
        return indexDosPontosSelecione;
    }

    public void setIndexDosPontosSelecione(List<String> indexDosPontosSelecione) {
        this.indexDosPontosSelecione = indexDosPontosSelecione;
    }

    public void setIndexDosPontosSelecioneII(List<String> indexDosPontosSelecioneII) {
        this.indexDosPontosSelecioneII = indexDosPontosSelecioneII;
    }

    public List<String> getIndexDosPontosSelecioneII() {
        return indexDosPontosSelecioneII;
    }

    public void setPontoAtualDoAluno(CPAluno aluno) {
        CPPontoDeEstagio pontoAtualDoAluno = DaoAlunoPonto.pontoAtualDoAluno(aluno);
        if (pontoAtualDoAluno != null) {
            for (CPPontoDeEstagio xPonto : pontos) {
                if (xPonto.getId() == pontoAtualDoAluno.getId()) {
                    indexDoPontoSelecione = pontos.indexOf(xPonto);
                }
            }
        } else {
            indexDoPontoSelecione = -1;
        }
    }
    public void setPontoAtualDoProfessor(CPProfessor professor) {
        CPPontoDeEstagio pontoAtualDoAluno = DaoProfessorPonto.pontoAtualDoProfessor(professor);
        if (pontoAtualDoAluno != null) {
            for (CPPontoDeEstagio xPonto : pontos) {
                if (xPonto.getId() == pontoAtualDoAluno.getId()) {
                    indexDoPontoSelecione = pontos.indexOf(xPonto);
                }
            }
        } else {
            indexDoPontoSelecione = -1;
        }
    }

    public CPPontoDeEstagio getPontoSelecioneIndexado() {
        if (indexDoPontoSelecione < 0) {
            return null;
        } else {
            return pontos.get(indexDoPontoSelecione);
        }
    }

    public long getIdDoPontoSelecionado() {
        if (indexDoPontoSelecione < 0) {
            return 0l;
        } else {
            return pontos.get(indexDoPontoSelecione).getId();
        }
    }

    public CPPontoDeEstagio getPontoIndexado() {
        return pontos.get(indexDoPonto);
    }

    public CPPontoDeEstagio getPontoManipualdoIndexado() {
        return pontosManipulados.get(indexDoPontoManipulado);
    }

    private void ordena() {
        OrdenaPontoDeEstagio p = new OrdenaPontoDeEstagio();
        Collections.sort(pontos, p);
        Collections.sort(pontosManipulados, p);
    }

    public void sobe(CPPontoDeEstagio p) {
        pontos.add(p);
        pontosManipulados.remove(p);
        ordena();
    }

    public void desce() {
        if (indexDoPonto >= 0) {
            pontosManipulados.add(pontos.get(indexDoPonto));
            pontos.remove(indexDoPonto);
            ordena();
            if (pontos.isEmpty()) {
                indexDoPonto = -1;
            } else {
                indexDoPonto = 0;
            }
            UtilMensagens.info(UtilMensagens.pontoAdd);
        } else {
            UtilMensagens.info(UtilMensagens.pontosTodosAdd);
        }
    }

    public void sobeIndexado() {
        pontos.add(pontosManipulados.get(indexDoPontoManipulado));
        pontosManipulados.remove(indexDoPontoManipulado);
        ordena();
        UtilMensagens.info(UtilMensagens.pontoRemovido);
    }

    public void carregaManipulados(List<CPPontoDeEstagio> p) {
        for (CPPontoDeEstagio pt : p) {
            addProdutoNaListPontoManip(pt);
        }
    }

    public void zeraManipulados() {
        pontosManipulados = new LinkedList<>();
    }

    public void addProdutoNaListPontoManip(CPPontoDeEstagio pt) {
        long id = pt.getId();
        int pos = 0;
        for (CPPontoDeEstagio p : pontos) {
            if (p.getId() == id) {
                pos = pontos.indexOf(p);
            }
        }
        pontos.remove(pos);
        pontosManipulados.add(pt);
    }

    public void removeDaListaManipulada(CPPontoDeEstagio p) {
        pontosManipulados.remove(p);
    }

    public void addProdutoNaLista(CPPontoDeEstagio p) {
        pontos.add(p);
    }

    public void saiDeProdParaProdManip() {
        pontosManipulados.add(pontos.get(indexDoPonto));
        pontos.remove(indexDoPonto);
    }

    public List<Long> getIdsPontosManipulados() {
        List<Long> res = new LinkedList<>();
        for (CPPontoDeEstagio p : pontosManipulados) {
            res.add(p.getId());
        }
        return res;
    }

    public int getIndexDoPonto() {
        return indexDoPonto;
    }

    public List<CPPontoDeEstagio> getPontos() {
        return pontos;
    }

    public List<CPPontoDeEstagio> getPontosManipulados() {
        return pontosManipulados;
    }

    public void setIndexDoPonto(int indexDoPonto) {
        this.indexDoPonto = indexDoPonto;
    }

    public CPPontoDeEstagio getPontoManipulado() {
        return pontoManipulado;
    }

    public void setPontoManipulado(CPPontoDeEstagio pontoManipulado) {
        this.pontoManipulado = pontoManipulado;
    }

    public void setPontosManipulados(List<CPPontoDeEstagio> pontosManipulados) {
        pontos = DaoPontoDeEstagio.getAll("nome");
        this.pontosManipulados = new LinkedList<>();
        carregaManipulados(pontosManipulados);
    }

    public int getIndexDoPontoSelecione() {
        return indexDoPontoSelecione;
    }

    public void setIndexDoPontoSelecione(int indexDoPontoSelecione) {
        this.indexDoPontoSelecione = indexDoPontoSelecione;
    }

    public int getIndexDoPontoManipulado() {
        return indexDoPontoManipulado;
    }

    public void setIndexDoPontoManipulado(int indexDoPontoManipulado) {
        this.indexDoPontoManipulado = indexDoPontoManipulado;
    }

    public void setCriterioPontosAnteriores(int criterioPontosAnteriores) {
        this.criterioPontosAnteriores = criterioPontosAnteriores;
    }

    public int getCriterioPontosAnteriores() {
        return criterioPontosAnteriores;
    }
    
}
