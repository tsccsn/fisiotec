/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package beans.graficos;

import beans.listas.BeanListProdutos;
import beans.listas.grafico.BeanListGraficosEntradaSalvos;
import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.InterfaceGraficoCustom;
import cp.grafico.InterfaceGraficoPreDet;
import cp.grafico.entrada.ABSGraficoEntradaSalvo;
import cp.grafico.entrada.custom.CPGraficoEntradaCustom;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminado;
import dao.grafico.entrada.custom.DaoGraficoEntradaCustom;
import dao.grafico.entrada.custom.DaoGraficoEntradaCustomProdutos;
import dao.grafico.entrada.preDeterminada.DaoGraficoEntradaPreDeterminada;
import dao.grafico.entrada.preDeterminada.DaoGraficoEntradaPreDeterminadaProdutos;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utilidades.Boleanos;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;
import utilidades.string.UtilString;
import utilidades.xml.NewGeradorXML;

/**
 *
 * @author Thiago-Asus
 */
@ManagedBean(name = "grafEntrada")
@ViewScoped
public class BeanGraficoEntrada implements Serializable {

    private static final long serialVersionUID = 1L;
    private NewGeradorXML geradorXml = new NewGeradorXML();
    private String tipoGrafico = "MSColumn3D";
    private String stepWizr = "arquivados";
    private int tipoDeConsulta = 0;

    public void carregaGrafico(List<CPProduto> produtos, ABSGraficoEntradaSalvo graficoSalvo) {
        if (graficoSalvo instanceof InterfaceGraficoCustom) {
            tipoDeConsulta = 1;
        } else if (graficoSalvo instanceof InterfaceGraficoPreDet) {
            tipoDeConsulta = 0;
        }
        graficoSalvo = (ABSGraficoEntradaSalvo) geradorXml.acertaData(graficoSalvo);
        geradorXml.defineCPEntradaProdutoNoPerido(produtos, graficoSalvo);
        geradorXml.criaDatasParaRealizarConsultas(graficoSalvo);
        geradorXml.defineDataSetEntrada(produtos);
        geradorXml.deixaDataApresentavel(graficoSalvo);
        geradorXml.geraGrafEntrada();
        navegaNoWizard(4l);
        try {
            Thread.sleep(Boleanos.timeDefaultGerarGrafico);
        } catch (InterruptedException ex) {
        }
        UtilMensagens.info(UtilMensagens.graficoCarregado);
    }

    public void geraGraf(List<CPProduto> produtos, BeanListGraficosEntradaSalvos lgs) {
        switch (tipoDeConsulta) {
            case 0:// pre determinado
                lgs.setGrafEntradaPreDet((CPGraficoEntradaPreDeterminado) geradorXml.acertaData(lgs.getGrafEntradaPreDet()));
                geradorXml.defineCPEntradaProdutoNoPerido(produtos, lgs.getGrafEntradaPreDet());
                geradorXml.criaDatasParaRealizarConsultas(lgs.getGrafEntradaPreDet());
                geradorXml.defineDataSetEntrada(produtos);
                geradorXml.deixaDataApresentavel(lgs.getGrafEntradaPreDet());
                geradorXml.geraGrafEntrada();
                break;
            case 1:
                lgs.setGrafEntradaCustom((CPGraficoEntradaCustom) geradorXml.acertaData(lgs.getGrafEntradaCustom()));
                geradorXml.defineCPEntradaProdutoNoPerido(produtos, lgs.getGrafEntradaCustom());
                geradorXml.criaDatasParaRealizarConsultas(lgs.getGrafEntradaCustom());
                geradorXml.defineDataSetEntrada(produtos);
                geradorXml.deixaDataApresentavel(lgs.getGrafEntradaCustom());
                geradorXml.geraGrafEntrada();
                break;
        }
        navegaNoWizard(4);
        try {
            Thread.sleep(Boleanos.timeDefaultGerarGrafico);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * confere se é possivel savar o grafico com o nome tal
     *
     * @param beanListGraficosSalvos
     * @return
     */
    private ABSGraficoEntradaSalvo confereDisponibilidadeNome(BeanListGraficosEntradaSalvos listGraficosEntradaSalvos) {
        CPGraficoEntradaPreDeterminado preDet = DaoGraficoEntradaPreDeterminada.existeEsseNome(listGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
        CPGraficoEntradaCustom custom = DaoGraficoEntradaCustom.existeEsseNome(listGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
        if (custom == null && preDet == null) {
            return null;
        } else {
            if (custom != null) {
                return custom;
            } else {
                return preDet;
            }
        }
    }

    public void tentaSalvarGrafico(BeanListGraficosEntradaSalvos listGraficosEntradaSalvos, BeanListProdutos beanListProdutos) {
        if (confereDisponibilidadeNome(listGraficosEntradaSalvos) != null) {
            UtilMensagens.alerta(UtilMensagens.graficoExistenteEscolhaOutro);
            RequestContext.getCurrentInstance().addCallbackParam("salva", false);
        } else {
            salvarGrafico(listGraficosEntradaSalvos, beanListProdutos);
            RequestContext.getCurrentInstance().addCallbackParam("salva", true);
        }
    }

    /**
     * método padrão para salvar um gráfico
     *
     * @param listGraficosEntradaSalvos
     * @param beanListProdutos
     */
    public void salvarGrafico(BeanListGraficosEntradaSalvos listGraficosEntradaSalvos, BeanListProdutos beanListProdutos) {
        switch (tipoDeConsulta) {
            case 0: //pre deter
                CPGraficoEntradaPreDeterminado grafEntradaPreDeterminado = new CPGraficoEntradaPreDeterminado();
                grafEntradaPreDeterminado.setNome(listGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
                grafEntradaPreDeterminado.setDe(listGraficosEntradaSalvos.getGrafEntradaPreDet().getDe());
                grafEntradaPreDeterminado.setAte(listGraficosEntradaSalvos.getGrafEntradaPreDet().getAte());
                grafEntradaPreDeterminado.setAgrupamento(listGraficosEntradaSalvos.getGrafEntradaPreDet().getAgrupamento());
                grafEntradaPreDeterminado = DaoGraficoEntradaPreDeterminada.mergeII(grafEntradaPreDeterminado);
                new DaoGraficoEntradaPreDeterminadaProdutos().mergList(grafEntradaPreDeterminado, beanListProdutos.getProdutosManipulados());
                listGraficosEntradaSalvos.setAbsGrafEntradaSalvo2(grafEntradaPreDeterminado);
                break;
            case 1: //custom
                CPGraficoEntradaCustom grafEntradaCustom = new CPGraficoEntradaCustom();
                grafEntradaCustom.setNome(listGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
                grafEntradaCustom.setDe(listGraficosEntradaSalvos.getGrafEntradaCustom().getDe());
                grafEntradaCustom.setAte(listGraficosEntradaSalvos.getGrafEntradaCustom().getAte());
                grafEntradaCustom.setAgrupamento(listGraficosEntradaSalvos.getGrafEntradaCustom().getAgrupamento());
                grafEntradaCustom = DaoGraficoEntradaCustom.mergeII(grafEntradaCustom);
                new DaoGraficoEntradaCustomProdutos().mergList(grafEntradaCustom, beanListProdutos.getProdutosManipulados());
                listGraficosEntradaSalvos.setAbsGrafEntradaSalvo2(grafEntradaCustom);
                break;
        }
        listGraficosEntradaSalvos.atualizaListaDeGraficos();
        listGraficosEntradaSalvos.filtra(false);
        UtilMensagens.ok(UtilMensagens.graficoSalvo);
    }

    public void atualizaGraficoManipulado(BeanListGraficosEntradaSalvos listGraficosEntradaSalvos, BeanListProdutos beanListProdutos) {
        //DATA
        switch (tipoDeConsulta) {
            case 0://pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(listGraficosEntradaSalvos.getGrafEntradaPreDet());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    stepWizr = "data";
                    return;
                }
                break;
            case 1://custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(listGraficosEntradaSalvos.getGrafEntradaCustom());
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
        atualizaGrafico(listGraficosEntradaSalvos, beanListProdutos);
        UtilMensagens.ok(UtilMensagens.graficoAtualizado);
        RequestContext.getCurrentInstance().addCallbackParam("salva", true);
    }

    /**
     * salva APENAS o grafico manipulado na sessão lembrando que o grafico
     * manipulado na sessão é o único que PODE MUDAR DE TIPO tanto custom -> pre
     * determinado quanto pre determiando -> custom
     *
     * @param beanListGraficosSaidaSalvos
     * @param beanListProdutos
     * @param beanListPontoEstagio
     */
    private void atualizaGrafico(BeanListGraficosEntradaSalvos beanListGraficosEntradaSalvos, BeanListProdutos beanListProdutos) {
        ABSGraficoEntradaSalvo grafico = beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvoManipulado();
        if (!UtilString.getNomeClasse(beanListGraficosEntradaSalvos.graficoManipulado(tipoDeConsulta)).equals(UtilString.getNomeClasse(grafico))) {
            if (beanListGraficosEntradaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoCustom) {
                DaoGraficoEntradaPreDeterminada.delete((CPGraficoEntradaPreDeterminado) grafico);
                beanListGraficosEntradaSalvos.getGrafEntradaCustom().setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
                grafico = DaoGraficoEntradaCustom.mergeII((CPGraficoEntradaCustom) beanListGraficosEntradaSalvos.getGrafEntradaCustom());
                new DaoGraficoEntradaCustomProdutos().mergList((CPGraficoEntradaCustom) grafico, beanListProdutos.getProdutosManipulados());
                beanListGraficosEntradaSalvos.setAbsGrafEntradaSalvo2(grafico);
            } else if (beanListGraficosEntradaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoPreDet) {
                DaoGraficoEntradaCustom.delete((CPGraficoEntradaCustom) grafico);
                beanListGraficosEntradaSalvos.getGrafEntradaPreDet().setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
                grafico = (CPGraficoEntradaPreDeterminado) DaoGraficoEntradaPreDeterminada.mergeII((CPGraficoEntradaPreDeterminado) beanListGraficosEntradaSalvos.getGrafEntradaPreDet());
                new DaoGraficoEntradaPreDeterminadaProdutos().mergList((CPGraficoEntradaPreDeterminado) grafico, beanListProdutos.getProdutosManipulados());
                beanListGraficosEntradaSalvos.setAbsGrafEntradaSalvo2(grafico);
            }
        } else { 
            if (grafico instanceof InterfaceGraficoCustom) {
                grafico.setAgrupamento(beanListGraficosEntradaSalvos.getGrafEntradaCustom().getAgrupamento());
                grafico.setDe(beanListGraficosEntradaSalvos.getGrafEntradaCustom().getDe());
                grafico.setAte(beanListGraficosEntradaSalvos.getGrafEntradaCustom().getAte());
                grafico.setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
                new DaoGraficoEntradaCustomProdutos().atualizaProdutos((CPGraficoEntradaCustom) grafico, beanListProdutos.getProdutosManipulados());
            } else if (grafico instanceof InterfaceGraficoPreDet) {
                grafico.setAgrupamento(beanListGraficosEntradaSalvos.getGrafEntradaPreDet().getAgrupamento());
                grafico.setDe(beanListGraficosEntradaSalvos.getGrafEntradaPreDet().getDe());
                grafico.setAte(beanListGraficosEntradaSalvos.getGrafEntradaPreDet().getAte());
                grafico.setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
                new DaoGraficoEntradaPreDeterminadaProdutos().atualizaProdutos((CPGraficoEntradaPreDeterminado) grafico, beanListProdutos.getProdutosManipulados());
            }
        }
        beanListGraficosEntradaSalvos.atualizaListaDeGraficos();
        beanListGraficosEntradaSalvos.filtra(false);
    }

    public ABSGraficoSalvo buscarGraficoSalvoPeloNome(BeanListGraficosEntradaSalvos salvos) {
        for (ABSGraficoSalvo xSalvo : salvos.getGraficosEntradaSalvos()) {
            if (xSalvo instanceof InterfaceGraficoPreDet && xSalvo.getNome().equals(salvos.getAbsGrafEntradaSalvo().getNome())) {
                return xSalvo;
            }
        }
        for (ABSGraficoSalvo xSalvo : salvos.getGraficosEntradaSalvos()) {
            if (xSalvo instanceof InterfaceGraficoCustom && xSalvo.getNome().equals(salvos.getAbsGrafEntradaSalvo().getNome())) {
                return xSalvo;
            }
        }
        return null;
    }

    public void primeiroPassoParaSalvarConsultaComoNova(BeanListGraficosEntradaSalvos beanListGraficosEntradaSalvos, BeanListProdutos beanListProdutos, Boleanos boleanos) {
        //DATA
        switch (tipoDeConsulta) {
            case 0://pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(beanListGraficosEntradaSalvos.getGrafEntradaPreDet());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    stepWizr = "data";
                    return;
                }
                break;
            case 1://custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(beanListGraficosEntradaSalvos.getGrafEntradaCustom());
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

        ABSGraficoEntradaSalvo confereDisponibilidadeNome = confereDisponibilidadeNome(beanListGraficosEntradaSalvos);
        if (confereDisponibilidadeNome != null) {
            beanListGraficosEntradaSalvos.setAbsGrafEntradaSalvoManipulado(confereDisponibilidadeNome);
            RequestContext.getCurrentInstance().addCallbackParam("salva", false);
            boleanos.ambosTrue(2l);
        } else {
            salvarGrafico(beanListGraficosEntradaSalvos, beanListProdutos);
            RequestContext.getCurrentInstance().addCallbackParam("salva", true);
        }
    }

    // # MÉTODOS DE AMBIENTE
    public boolean confereData(BeanListGraficosEntradaSalvos gs) {
        switch (tipoDeConsulta) {
            case 0: //pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(gs.getGrafEntradaPreDet());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    return false;
                }
                break;
            case 1: //custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(gs.getGrafEntradaCustom());
                if (!validaDataGraficoCustom.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoCustom);
                    return false;
                }
                break;
        }
        navegaNoWizard(2l);
        return true;
    }

    public void confereProdutos(BeanListProdutos lp, BeanListGraficosEntradaSalvos lg) {
        if (lp.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.produtoNemNaLista);
        } else {
            geraGraf(lp.getProdutosManipulados(), lg);
        }
    }
    // # FIM MÉTODOS DE AMBIENTE

    public void navegaNoWizard(long navegacao) {
        switch ((int) navegacao) {
            case 0:// SALVOS TO DATA
                stepWizr = "data";
                break;
            case 1: // DATA TO SALVOS
                stepWizr = "arquivados";
                break;
            case 2:// DATA TO PRODUTOS
                stepWizr = "produtos";
                break;
            case 3:// PRODUTOS TO DATA
                stepWizr = "data";
                break;
            case 4: // PRODUTOS TO GRÁFICO
                stepWizr = "grafico";
                break;
            case 5: // GRÁFICO TO PRODUTOS
                stepWizr = "produtos";
                break;
        }
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

    public NewGeradorXML getGeradorXml() {
        return geradorXml;
    }

    public void setGeradorXml(NewGeradorXML geradorXml) {
        this.geradorXml = geradorXml;
    }

    public String getTipoGrafico() {
        return tipoGrafico;
    }

    public void setTipoGrafico(String tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }
}