
import cp.CPReport;
import dao.DaoReport;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thiago
 */
@ManagedBean(name = "beanReport")
@ViewScoped
public class ReportandoErro implements Serializable{

    private CPReport report = new CPReport();

    public CPReport getReport() {
        return report;
    }

    public void setReport(CPReport report) {
        this.report = report;
    }

    public void salvaAutomatico() {
        report.setDetalhesDoErro("relatorio automatico");
        DaoReport.merge(report);
    }

    public void salvaReport() {
        DaoReport.merge(report);
        report = new CPReport();
        UtilMensagens.info("Sintimos pelo contrangimento, agora com sua ajuda buscaremos uma solução.");
    }
}
