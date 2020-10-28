/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.clinica;

import cp.clinica.CPPaciente;
import cp.clinica.CPSessao;
import cp.clinica.CPTratamento;
import dao.clinica.DaoConsuta;
import dao.clinica.DaoGenerico;
import dao.clinica.DaoSessao;
import dao.clinica.DaoTratamento;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import utilidades.data.UtilData;
import utilidades.hibernate.SessionUtil;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Felipe Machado
 */
@ManagedBean
@ViewScoped
public class BeanSessao implements Serializable {

    private CPSessao sessao = new CPSessao();
    private CPSessao viewSessao = new CPSessao();
    private CPTratamento tratamento = new CPTratamento();
    private CPTratamento pesquisaTratamento = new CPTratamento();
    private List<CPSessao> sessoes = new ArrayList<>();
    private List<CPSessao> listaSessoes = new ArrayList<>();
    private List<CPTratamento> tratamentos = DaoSessao.getTratAtuais();
    private List<CPTratamento> tratamentosManipulados = DaoSessao.getTratAtuais();
    private List<CPTratamento> viewTratamentos = new ArrayList<>();
    private Long idPaciente;
    private boolean modoEditar, modoFimSessao;
    private int qtdList;
    private Date diaConsulta;
    private Long idSessao;
    private CPSessao pesquisaSessao = new CPSessao();
    private Date de, ate, hoje;
    private Timestamp dataDeconsulta;

    public CPSessao getViewSessao() {
        return viewSessao;
    }

    public void setViewSessao(CPSessao viewSessao) {
        this.viewSessao = viewSessao;
    }

    public List<CPTratamento> getViewTratamentos() {
        return viewTratamentos;
    }

    public void setViewTratamentos(List<CPTratamento> viewTratamentos) {
        this.viewTratamentos = viewTratamentos;
    }

    public List<CPSessao> getListaSessoes() {
        return listaSessoes;
    }

    public void setListaSessoes(List<CPSessao> listaSessoes) {
        this.listaSessoes = listaSessoes;
    }

    public Date getHoje() {
        hoje = new Date();
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }

    public boolean isModoFimSessao() {
        return modoFimSessao;
    }

    public void setModoFimSessao(boolean modoFimSessao) {
        this.modoFimSessao = modoFimSessao;
    }

    public CPTratamento getPesquisaTratamento() {
        return pesquisaTratamento;
    }

    public void setPesquisaTratamento(CPTratamento pesquisaTratamento) {
        this.pesquisaTratamento = pesquisaTratamento;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public CPSessao getPesquisaSessao() {
        return pesquisaSessao;
    }

    public void setPesquisaSessao(CPSessao pesquisaSessao) {
        this.pesquisaSessao = pesquisaSessao;
    }

    public Long getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Long idSessao) {
        this.idSessao = idSessao;
    }

    public List<CPTratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(List<CPTratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }

    public Date getDiaConsulta() {
        return diaConsulta;
    }

    public void setDiaConsulta(Date diaConsulta) {
        System.out.println("setou novo valor");
        System.out.println(diaConsulta);
        this.diaConsulta = diaConsulta;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public boolean isModoEditar() {
        return modoEditar;
    }

    public void setModoEditar(boolean modoEditar) {
        this.modoEditar = modoEditar;
    }

    public int getQtdList() {
        return qtdList;
    }

    public void setQtdList(int qtdList) {
        this.qtdList = qtdList;
    }

    public CPSessao getSessao() {
        return sessao;
    }

    public void setSessao(CPSessao sessao) {
        this.sessao = sessao;
    }

    public List<CPSessao> getSessoes() {
        if (sessoes.isEmpty()) {
            sessoes = DaoSessao.getAll();
        }
        this.qtdList = sessoes.size();
        return sessoes;
    }

    public void setSessoes(List<CPSessao> sessoes) {
        this.sessoes = sessoes;
    }

    public CPTratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(CPTratamento tratamento) {
        this.tratamento = tratamento;
    }

    public void adicionaTratamento() {
        if (tratamento.getHoraFinal().after(tratamento.getHoraInicial())) {
            if (!DaoConsuta.jaExiste(tratamento.getBox(), tratamento.getDia(), tratamento.getHoraInicial(), tratamento.getHoraFinal())) {
                if (tratamento.getBox() != 0) {
                    tratamento.setStatus("Em Aberto");
                    sessao.adicionaItem(tratamento);
                    sessao.setQtdTratamento(sessao.getQtdTratamento() + 1);
                    tratamento = new CPTratamento();
                } else {
                    FacesContext.getCurrentInstance().addMessage("paginaCadastro", new FacesMessage("Box não pode ser 0!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("paginaCadastro", new FacesMessage("Já existe consulta ou sessão neste horário!"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("paginaCadastro", new FacesMessage("A hora final deve ser depois da hora inicial!"));
        }

    }
    public void removeTratamento(){
        sessao.removeItem(tratamento);
        sessao.setQtdTratamento(sessao.getQtdTratamento() - 1);
    }
    public void gravar() {
        try {
            if (sessao.getId() == null) {
                sessao.setStatus("Em Aberto");
            }
            sessao.setDiaCad(new Date());
            DaoSessao.merge(sessao);
            sessao = new CPSessao();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void gravarTratamento() {
        try {
            if (tratamento.getId() == null) {
                tratamento.setStatus("Em Aberto");
            }
            DaoTratamento.merge(tratamento);
            sessao.setQtdTratamento(sessao.getQtdTratamento() + 1);
            sessao = new CPSessao();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }
    
    public void excluir() {

        try {
            DaoSessao.deleta(sessao);
            sessao = new CPSessao();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }
    public void excluirTratamento() {

        try {
            DaoTratamento.deleta(tratamento);
            tratamento = new CPTratamento();
            sessao.setQtdTratamento(sessao.getQtdTratamento() - 1);
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void addPaciente() {
        DaoGenerico<CPPaciente> dao = new DaoGenerico<>(CPPaciente.class);
        CPPaciente paciente = dao.getById(idPaciente);
        sessao.setPaciente(paciente);
    }

    public void iniciarTratamento() {
        try {
            System.out.println("numero : " + tratamento.getNumTratamento());
            if (tratamento.getNumTratamento() == 1) {
                tratamento.getSessao().setStatus("Em Tratamento");
            }
            tratamento.setStatus("Em Tratamento");
            DaoTratamento.merge(tratamento);
            tratamento = new CPTratamento();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void finalizarTratamento() {
        try {
            if (tratamento.getNumTratamento() == tratamento.getSessao().getQtdTratamento()) {

                tratamento.getSessao().setStatus(tratamento.getStatus());
            }
            DaoTratamento.merge(tratamento);
            tratamento = new CPTratamento();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void listarPorDia() {
        tratamentosManipulados = new LinkedList<>();

        if (dataDeconsulta != null) {
            System.out.println("buscado: " + UtilData.diaNmesNanoNL(dataDeconsulta.getTime()));
            for (CPTratamento xTratamento : tratamentos) {
                if (UtilData.diaNmesNanoNL(xTratamento.getDia().getTime()).equals(UtilData.diaNmesNanoNL(dataDeconsulta.getTime()))) {
                    tratamentosManipulados.add(xTratamento);
                }
            }
        } else {
            System.out.println("dia da consulta está nulo");
            tratamentosManipulados = tratamentos;
        }


    }

    public int QtdConsultas() {
        qtdList = tratamentos.size();
        return qtdList;
    }

    public void verificarDados() {
        if (sessao.getQtdTratamento() == sessao.getTratamentos().size()) {
            this.gravar();
        } else {
            UtilMensagens.alerta("A quantidade de dias de sessões deve ser o mesmo do campo quantidade de sessões!");
        }
    }

    public List<CPTratamento> listTratamentos() {
        List<CPTratamento> lista = DaoTratamento.getSesTratamentos(this.sessao);
        return lista;
    }

    public void pesquisando() {
        this.sessoes = DaoSessao.pesquisa(pesquisaSessao.getPaciente(), pesquisaSessao.getPatologia(), pesquisaSessao.getQtdTratamento(), pesquisaSessao.getDiaCad(), pesquisaSessao.getObs(), pesquisaSessao.getStatus(), de, ate);
    }

    public void listarTodas() {
        sessoes = DaoSessao.getAll();
    }

    public List<String> patologias(String query) {
        List<String> sugestoes = new ArrayList<>();
        for (String pt : DaoSessao.tiposPatologia()) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<CPTratamento> getTratamentosManipulados() {
        return tratamentosManipulados;
    }

    public Timestamp getDataDeconsulta() {
        return dataDeconsulta;
    }

    public void setDataDeconsulta(Timestamp dataDeconsulta) {
        this.dataDeconsulta = dataDeconsulta;
    }
    
    public void listarTratamentosParaSessao(){
        viewTratamentos = DaoSessao.getSesTratamentos(this.sessao);
    }
    
}
