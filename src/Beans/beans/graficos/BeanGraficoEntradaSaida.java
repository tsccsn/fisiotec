/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.graficos;

import beans.listas.BeanListProdutos;
import beans.listas.grafico.BeanListGraficosEntradaESaidaSalvos;
import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import cp.grafico.InterfaceGraficoCustom;
import cp.grafico.InterfaceGraficoPreDet;
import cp.grafico.entradasaida.ABSGraficoEntradaSaida;
import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustom;
import cp.grafico.entradasaida.preDeterminado.CPGraficoEntradaSaidaPreDeterminado;
import dao.DaoPontoDeEstagio;
import dao.grafico.entradaSaida.custom.DaoGraficoEntradaSaidaCustom;
import dao.grafico.entradaSaida.custom.DaoGraficoEntradaSaidaCustomProdutos;
import dao.grafico.entradaSaida.preDeterminada.DaoGraficoEntradaSaidaPreDeterminado;
import dao.grafico.entradaSaida.preDeterminada.DaoGraficoEntradaSaidaPreDeterminadoProdutos;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utilidades.Boleanos;
import utilidades.UtilLista;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;
import utilidades.string.UtilString;
import utilidades.xml.NewGeradorXML;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "graficoIO")
@ViewScoped
public class BeanGraficoEntradaSaida implements Serializable {

    private static final long serialVersionUID = 1L;
    private String stepWizr = "arquivados";
    private int tipoDeConsulta = 0;
    private NewGeradorXML gerador = new NewGeradorXML();

    public void primeiroPassoSalvarConsultaComoNova(BeanListProdutos beanListProdutos, BeanListGraficosEntradaESaidaSalvos beanListGraficosSalvos, Boleanos boleanos) {
        //DATA
        switch (tipoDeConsulta) {
            case 0://pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(beanListGraficosSalvos.getCpGraficoEntradaSaidaPreDeterminado());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    stepWizr = "data";
                    return;
                }
                break;
            case 1://custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(beanListGraficosSalvos.getCpGraficoEntradaSaidaPreDeterminado());
                if (!validaDataGraficoCustom.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoCustom);
                    stepWizr = "data";
                    return;
                }
                break;
        }
        //PRODUTOS
        if (beanListProdutos.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.produtoNemNaLista);
            stepWizr = "produtos";
            return;
        }
        ABSGraficoEntradaSaida confereDisponibilidadeNome = confereDisponibilidadeNome(beanListGraficosSalvos);
        if (confereDisponibilidadeNome == null) {
            salvarGrafico(beanListGraficosSalvos, beanListProdutos);
            boleanos.ambosFalse(3, 0);
        } else {
            beanListGraficosSalvos.setAbsGraficoEntradaSaidaPraSerManipulado(confereDisponibilidadeNome);
            RequestContext.getCurrentInstance().addCallbackParam("nomeRepetido", true);
            boleanos.ambosTrue(4);
        }
    }

    public void salvaConsultaComoNova(BeanListProdutos beanListProdutos, BeanListGraficosEntradaESaidaSalvos beanListGraficosSalvos, Boleanos boleanos) {
        if (confereDisponibilidadeNome(beanListGraficosSalvos) != null) {
            UtilMensagens.alerta(UtilMensagens.graficoExistenteEscolhaOutro);
        } else {
            boleanos.ambosFalse(1, 0);
            salvarGrafico(beanListGraficosSalvos, beanListProdutos);
            RequestContext.getCurrentInstance().addCallbackParam("fechaJanela", true);
        }
    }

    private ABSGraficoEntradaSaida confereDisponibilidadeNome(BeanListGraficosEntradaESaidaSalvos beanListGraficosSalvos) {
        CPGraficoEntradaSaidaPreDeterminado preDeterminado = DaoGraficoEntradaSaidaPreDeterminado.existeEsseNome(beanListGraficosSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
        if (preDeterminado != null) {
            return preDeterminado;
        }
        CPGraficoEntradaSaidaCustom custom = DaoGraficoEntradaSaidaCustom.existeEsseNome(beanListGraficosSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
        if (custom != null) {
            return custom;
        }
        return null;
    }

    public void salvarGrafico(BeanListGraficosEntradaESaidaSalvos beanListGraficosSaidaSalvos, BeanListProdutos beanListProdutos) {
        //pra salvar um novo grafico precisa-se saber se o grafico a ser salvo é custom ou pre
        switch (tipoDeConsulta) {
            case 0: //pre deter
                //cria nova instancia
                CPGraficoEntradaSaidaPreDeterminado saidaPreDeterminado = new CPGraficoEntradaSaidaPreDeterminado();
                saidaPreDeterminado.setNome(beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
                saidaPreDeterminado.setAgrupamento(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().getAgrupamento());
                saidaPreDeterminado.setAte(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().getAte());
                saidaPreDeterminado.setDe(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().getDe());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(new ABSGraficoEntradaSaida() {});
                beanListGraficosSaidaSalvos.setCpGraficoEntradaSaidaCustom(new CPGraficoEntradaSaidaCustom());
                beanListGraficosSaidaSalvos.setCpGraficoEntradaSaidaPreDeterminado(new CPGraficoEntradaSaidaPreDeterminado());
                saidaPreDeterminado = DaoGraficoEntradaSaidaPreDeterminado.mergeII(saidaPreDeterminado);
                new DaoGraficoEntradaSaidaPreDeterminadoProdutos().mergList(saidaPreDeterminado, beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGraficoEntradaSaidaPraSerManipulado(saidaPreDeterminado);
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(saidaPreDeterminado);
                UtilMensagens.ok(UtilMensagens.graficoSalvo);
                break;
            case 1: //custom
                CPGraficoEntradaSaidaCustom saidaCustom = new CPGraficoEntradaSaidaCustom();
                saidaCustom.setNome(beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
                saidaCustom.setAgrupamento(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().getAgrupamento());
                saidaCustom.setAte(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().getAte());
                saidaCustom.setDe(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().getDe());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(new ABSGraficoEntradaSaida() {});
                beanListGraficosSaidaSalvos.setCpGraficoEntradaSaidaCustom(new CPGraficoEntradaSaidaCustom());
                beanListGraficosSaidaSalvos.setCpGraficoEntradaSaidaPreDeterminado(new CPGraficoEntradaSaidaPreDeterminado());
                saidaCustom = DaoGraficoEntradaSaidaCustom.mergeII(saidaCustom);
                new DaoGraficoEntradaSaidaCustomProdutos().mergList(saidaCustom, beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGraficoEntradaSaidaPraSerManipulado(saidaCustom);
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(saidaCustom);
                UtilMensagens.ok(UtilMensagens.graficoSalvo);
                break;
        }
        beanListGraficosSaidaSalvos.atualizaListaDeGraficosSaidaSalvo();
        beanListGraficosSaidaSalvos.filtra(false);
    }

    public void atualizaGraficoSalvo(BeanListGraficosEntradaESaidaSalvos beanListGraficosSaidaSalvos, BeanListProdutos beanListProdutos) {
        ABSGraficoEntradaSaida grafico;
        grafico = beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaPraSerManipulado();
        if (!UtilString.getNomeClasse(beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta)).equals(UtilString.getNomeClasse(grafico))) {
            if (beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoCustom) {
                DaoGraficoEntradaSaidaPreDeterminado.deleta((CPGraficoEntradaSaidaPreDeterminado) grafico);
                beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().setNome(beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
                beanListGraficosSaidaSalvos.setCpGraficoEntradaSaidaCustom(DaoGraficoEntradaSaidaCustom.mergeII((CPGraficoEntradaSaidaCustom) beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta)));
                new DaoGraficoEntradaSaidaCustomProdutos().mergList(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom(), beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGraficoEntradaSaidaPraSerManipulado(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom());
            } else if (beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoPreDet) {
                DaoGraficoEntradaSaidaCustom.deleta((CPGraficoEntradaSaidaCustom) grafico);
                beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().setNome(beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
                beanListGraficosSaidaSalvos.setCpGraficoEntradaSaidaPreDeterminado(DaoGraficoEntradaSaidaPreDeterminado.mergeII((CPGraficoEntradaSaidaPreDeterminado) beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta)));
                new DaoGraficoEntradaSaidaPreDeterminadoProdutos().mergList(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado(), beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGraficoEntradaSaidaPraSerManipulado(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado());
            }
        } else {
            if (grafico instanceof InterfaceGraficoCustom) {
                grafico.setAgrupamento(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().getAgrupamento());
                grafico.setDe(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().getDe());
                grafico.setAte(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom().getAte());
                grafico.setNome(beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
                DaoGraficoEntradaSaidaCustom.merge((CPGraficoEntradaSaidaCustom) grafico);
                new DaoGraficoEntradaSaidaCustomProdutos().atualizaProdutos((CPGraficoEntradaSaidaCustom) grafico, beanListProdutos.getProdutosManipulados());
            } else if (grafico instanceof InterfaceGraficoPreDet) {
                grafico.setAgrupamento(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().getAgrupamento());
                grafico.setDe(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().getDe());
                grafico.setAte(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado().getAte());
                grafico.setNome(beanListGraficosSaidaSalvos.getAbsGraficoEntradaSaidaSalvo().getNome());
                DaoGraficoEntradaSaidaPreDeterminado.merge((CPGraficoEntradaSaidaPreDeterminado) grafico);
                new DaoGraficoEntradaSaidaPreDeterminadoProdutos().atualizaProdutos((CPGraficoEntradaSaidaPreDeterminado) grafico, beanListProdutos.getProdutosManipulados());
            }
            beanListGraficosSaidaSalvos.setAbsGraficoEntradaSaidaPraSerManipulado(grafico);
            beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(grafico);
        }
        beanListGraficosSaidaSalvos.atualizaListaDeGraficosSaidaSalvo();
        beanListGraficosSaidaSalvos.filtra(false);
        UtilMensagens.ok(UtilMensagens.graficoAtualizado);
    }

    public void geraGrafico(List<CPProduto> produtos, BeanListGraficosEntradaESaidaSalvos salvos) {
        gerador.geraCodigo();
        if (tipoDeConsulta == 0) {
            salvos.setCpGraficoEntradaSaidaPreDeterminado((CPGraficoEntradaSaidaPreDeterminado) gerador.acertaData(salvos.getCpGraficoEntradaSaidaPreDeterminado()));
            gerador.criaDatasParaRealizarConsultas(salvos.getCpGraficoEntradaSaidaPreDeterminado());
            gerador.deixaDataApresentavel(salvos.getCpGraficoEntradaSaidaPreDeterminado());
            gerador.abreviaData(salvos.getCpGraficoEntradaSaidaPreDeterminado());
            gerador.defineDataSetVariacaoDaQuantidadeEmEstoque(produtos, salvos.getCpGraficoEntradaSaidaPreDeterminado());
            gerador.defineDataSetDiferencaEntradaSaida(produtos, salvos.getCpGraficoEntradaSaidaPreDeterminado());
            gerador.diferencaDeConsumoEntreOSPontos(produtos);
        } else {
            salvos.setCpGraficoEntradaSaidaCustom((CPGraficoEntradaSaidaCustom) gerador.acertaData(salvos.getCpGraficoEntradaSaidaCustom()));
            gerador.criaDatasParaRealizarConsultas(salvos.getCpGraficoEntradaSaidaCustom());
            gerador.deixaDataApresentavel(salvos.getCpGraficoEntradaSaidaCustom());
            gerador.abreviaData(salvos.getCpGraficoEntradaSaidaCustom());
            gerador.defineDataSetVariacaoDaQuantidadeEmEstoque(produtos, salvos.getCpGraficoEntradaSaidaCustom());
            gerador.defineDataSetDiferencaEntradaSaida(produtos, salvos.getCpGraficoEntradaSaidaCustom());
            gerador.diferencaDeConsumoEntreOSPontos(produtos);
        }
        try {
            Thread.sleep(Boleanos.timeDefaultGerarGrafico);
        } catch (InterruptedException ex) {
            Logger.getLogger(BeanGraficoEntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
        }
        //quantidadeEmEstoqueDeUmProdutoXEmDataY
    }
    
    public void carregaGrafico(BeanListGraficosEntradaESaidaSalvos beanList , ABSGraficoEntradaSaida graficoSalvo) {
        gerador.geraCodigo();
        List<CPProduto> produtos = beanList.listaProdutosDeGraficoSalvo(graficoSalvo);
        beanList.setAbsGrafSaidaSalvo2(graficoSalvo);
        if (graficoSalvo instanceof InterfaceGraficoCustom) {
            tipoDeConsulta = 1;
            beanList.setCpGraficoEntradaSaidaCustom((CPGraficoEntradaSaidaCustom) gerador.acertaData(graficoSalvo));
        } else if (graficoSalvo instanceof InterfaceGraficoPreDet) {
            tipoDeConsulta = 0;
            beanList.setCpGraficoEntradaSaidaPreDeterminado((CPGraficoEntradaSaidaPreDeterminado) gerador.acertaData(graficoSalvo));
        }
        gerador.criaDatasParaRealizarConsultas(graficoSalvo);
        gerador.deixaDataApresentavel(graficoSalvo);
        gerador.abreviaData(graficoSalvo);
        gerador.defineDataSetVariacaoDaQuantidadeEmEstoque(produtos, graficoSalvo);
        gerador.defineDataSetDiferencaEntradaSaida(produtos, graficoSalvo);
        gerador.diferencaDeConsumoEntreOSPontos(produtos);
        navegaNoWizard("grafico");
        UtilMensagens.info(UtilMensagens.graficoCarregado);
        try {
            Thread.sleep(Boleanos.timeDefaultGerarGrafico);
        } catch (InterruptedException ex) {
            Logger.getLogger(BeanGraficoEntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // # METODOS DE AMBIENTE
    public void confereProdutos(BeanListProdutos lp, BeanListGraficosEntradaESaidaSalvos beanListGraficosSaidaSalvos) {
        if (lp.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.produtoNemNaLista);
        } else {
            navegaNoWizard("grafico");
            geraGrafico(lp.getProdutosManipulados(), beanListGraficosSaidaSalvos);
        }
    }

    public void confereData(BeanListGraficosEntradaESaidaSalvos gs) {
        switch (tipoDeConsulta) {
            case 0: //pre determinado
                String validaDataGraficoPre = UtilData.validaDataGrafico(gs.getCpGraficoEntradaSaidaPreDeterminado());
                if (!validaDataGraficoPre.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPre);
                    return;
                }
                break;
            case 1: //custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(gs.getCpGraficoEntradaSaidaCustom());
                if (!validaDataGraficoCustom.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoCustom);
                    return;
                }
                break;
        }
        RequestContext.getCurrentInstance().addCallbackParam("segue", true);
        navegaNoWizard("produtos");
    }
    public void confereData2(BeanListGraficosEntradaESaidaSalvos gs, CPProduto p, Boleanos boleanos) {
        switch (tipoDeConsulta) {
            case 0: //pre determinado
                String validaDataGraficoPre = UtilData.validaDataGrafico(gs.getCpGraficoEntradaSaidaPreDeterminado());
                if (!validaDataGraficoPre.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPre);
                    return;
                }
                break;
            case 1: //custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(gs.getCpGraficoEntradaSaidaCustom());
                if (!validaDataGraficoCustom.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoCustom);
                    return;
                }
                break;
        }
        geraGrafico(new UtilLista().recebeObjetoRetornaLista(p), gs);
        boleanos.ambosTrue(1);
        RequestContext.getCurrentInstance().addCallbackParam("segue", true);
    }

    public void tentaAtualizarGraficoManipulado(BeanListGraficosEntradaESaidaSalvos beanListGraficosSaidaSalvos, BeanListProdutos beanListProdutos) {
        //DATA
        switch (tipoDeConsulta) {
            case 0://pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaPreDeterminado());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    stepWizr = "data";
                    return;
                }
                break;
            case 1://custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(beanListGraficosSaidaSalvos.getCpGraficoEntradaSaidaCustom());
                if (!validaDataGraficoCustom.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoCustom);
                    stepWizr = "data";
                    return;
                }
                break;
        }
        //PRODUTOS
        if (beanListProdutos.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.produtoNemNaLista);
            stepWizr = "produtos";
            return;
        }
        atualizaGraficoSalvo(beanListGraficosSaidaSalvos, beanListProdutos);
    }

    public void navegaNoWizard(String navega) {
        stepWizr = navega;
    }

    // # GET e SET
    public String getStepWizr() {
        return stepWizr;
    }

    public void setStepWizr(String stepWizr) {
        this.stepWizr = stepWizr;
    }

    public int getTipoDeConsulta() {
        return tipoDeConsulta;
    }

    public void setTipoDeConsulta(int tipoDeConsulta) {
        this.tipoDeConsulta = tipoDeConsulta;
    }

    public NewGeradorXML getGerador() {
        return gerador;
    }
}
