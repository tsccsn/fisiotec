/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.clinica;

import cp.clinica.CPConsulta;
import cp.clinica.CPPaciente;
import dao.clinica.DaoConsuta;
import dao.clinica.DaoGenerico;
import java.io.Serializable;
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
public class BeanConsulta implements Serializable {

    private CPConsulta consulta = new CPConsulta();
    private CPConsulta pesquisaConsulta = new CPConsulta();
    private List<CPConsulta> consultas = DaoConsuta.getAtuais();
    private List<CPConsulta> consultasManipuladas = consultas;
    private List<CPConsulta> consultasAntigas = new ArrayList<>();
    private CPPaciente paciente = new CPPaciente();
    private Long idPaciente;
    private boolean modoEditar, modoFimConsulta;
    private int qtdList;
    private String tipoFicha = "printFichas";
    private boolean btPrint = false;
    private Date diaConsulta;
    private Date de, ate, hoje;

    public boolean isModoFimConsulta() {
        return modoFimConsulta;
    }

    public void setModoFimConsulta(boolean modoFimConsulta) {
        this.modoFimConsulta = modoFimConsulta;
    }

    public CPPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CPPaciente paciente) {
        this.paciente = paciente;
    }

    public Date getHoje() {
        hoje = new Date();
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }

    public CPConsulta getPesquisaConsulta() {
        return pesquisaConsulta;
    }

    public void setPesquisaConsulta(CPConsulta pesquisaConsulta) {
        this.pesquisaConsulta = pesquisaConsulta;
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

    public boolean isBtPrint() {
        return btPrint;
    }

    public void setBtPrint(boolean btPrint) {
        this.btPrint = btPrint;
    }

    public List<CPConsulta> getConsultasAntigas() {
        qtdList = consultasAntigas.size();
        return consultasAntigas;
    }

    public void setConsultasAntigas(List<CPConsulta> consultasAntigas) {
        this.consultasAntigas = consultasAntigas;
    }

    public Date getDiaConsulta() {
        return diaConsulta;
    }

    public void setDiaConsulta(Date diaConsulta) {
        this.diaConsulta = diaConsulta;
    }

    public String getTipoFicha() {
        return tipoFicha;
    }

    public void setTipoFicha(String tipoFicha) {
        this.tipoFicha = tipoFicha;
    }

    public int getQtdList() {
        return qtdList;
    }

    public void setQtdList(int qtdList) {
        this.qtdList = qtdList;
    }

    public boolean isModoEditar() {
        return modoEditar;
    }

    public void setModoEditar(boolean modoEditar) {
        this.modoEditar = modoEditar;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public CPConsulta getConsulta() {
        return consulta;
    }

    public void setConsulta(CPConsulta consulta) {
        this.consulta = consulta;
    }

    public List<CPConsulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<CPConsulta> consultas) {
        this.consultas = consultas;
    }

    public void gravar() {
        try {
            if (consulta.getId() == null) {
                consulta.setStatus("Aberto");
            }
            DaoConsuta.merge(consulta);
            consulta = new CPConsulta();
            SessionUtil.addSuccessMessage("OperacaoSucesso");

        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
            System.out.println(e.getMessage());
        }
    }

    public void excluir() {
        DaoGenerico<CPConsulta> dao = new DaoGenerico<CPConsulta>(CPConsulta.class);
        try {
            dao.delete(consulta);
            consulta = new CPConsulta();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void addPaciente() {
        DaoGenerico<CPPaciente> dao = new DaoGenerico<CPPaciente>(CPPaciente.class);
        CPPaciente paciente = dao.getById(idPaciente);
        consulta.setPaciente(paciente);
    }

    public void iniciarConsulta() {
        try {
            consulta.setStatus("Tratamento");
            DaoConsuta.merge(consulta);
            consulta = new CPConsulta();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void finalizarConsulta() {
        try {
            DaoConsuta.merge(consulta);
            consulta = new CPConsulta();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }

    public void listarPorDia() {
        if (diaConsulta == null) {
            diaConsulta = new Date();
        }
        consultasManipuladas = new LinkedList<>();
        if (diaConsulta != null) {
            System.out.println("buscado: "+UtilData.diaNmesNanoNL(diaConsulta.getTime()));
            for (CPConsulta xTratamento : consultas) {
                if (UtilData.diaNmesNanoNL(xTratamento.getDia().getTime()).equals(UtilData.diaNmesNanoNL(diaConsulta.getTime()))) {
                    consultasManipuladas.add(xTratamento);
                }
            }
        }else{
            consultasManipuladas = consultas;
        }
    }

    public List<CPConsulta> listarAtuais() {
        return DaoConsuta.getAtuais();
    }

    public void listarAntigas() {
        consultasAntigas = DaoConsuta.getAntigas();
    }

    public void listarPorIntervaloDeTempo() {
        consultasAntigas = DaoConsuta.getPorIntervalo(de, ate);
    }

    public void verificarValores() {
        if (consulta.getHoraFinal().after(consulta.getHoraInicial())) {
            if (!DaoConsuta.jaExiste(consulta.getBox(), consulta.getDia(), consulta.getHoraInicial(), consulta.getHoraFinal())) {
                if (consulta.getBox() != 0){
                    this.gravar();
                }else{
                    UtilMensagens.alerta("O Box não pode ser zero");
                }
            } else {
                UtilMensagens.alerta("Já existe consulta neste horário!");
            }
        } else {
            UtilMensagens.alerta("A hora final deve ser depois da hora inicial!");
        }
    }

    public void pesquisaGeral() {
        consultasAntigas = DaoConsuta.pesquisa(pesquisaConsulta.getPaciente(), pesquisaConsulta.getDia(), pesquisaConsulta.getHoraInicial(), pesquisaConsulta.getHoraFinal(), pesquisaConsulta.getObs(), pesquisaConsulta.getStatus(), pesquisaConsulta.getBox(), de, ate);
    }
}
