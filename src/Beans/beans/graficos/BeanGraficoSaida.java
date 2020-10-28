/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.graficos;

import beans.listas.BeanListPontoEstagio;
import beans.listas.BeanListProdutos;
import beans.listas.grafico.BeanListGraficosSaidaSalvos;
import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import cp.grafico.ABSGraficoSalvo;
import cp.grafico.InterfaceGraficoCustom;
import cp.grafico.InterfaceGraficoPreDet;
import cp.grafico.saida.ABSGraficoSaidaSalvo;
import cp.grafico.saida.custom.CPGraficoSaidaCustom;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminado;
import dao.grafico.saida.custom.DaoGraficoSaidaCustom;
import dao.grafico.saida.custom.DaoGraficoSaidaCustomPontos;
import dao.grafico.saida.custom.DaoGraficoSaidaCustomProdutos;
import dao.grafico.saida.predeterminada.DaoGraficoSaidaPredeterminada;
import dao.grafico.saida.predeterminada.DaoGraficoSaidaPredeterminadaPontos;
import dao.grafico.saida.predeterminada.DaoGraficoSaidaPredeterminadaProdutos;
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
 * @author Thiago
 */
@ManagedBean(name = "graficoSaida")
@ViewScoped
public class BeanGraficoSaida implements Serializable {

    private static final long serialVersionUID = 1L;
    //# pre-determinado | custom
    private int tipoDeConsulta = 0;
    private NewGeradorXML geradorXml = new NewGeradorXML();
    private String tipoGrafico = "MSColumn3D";
    private String cor = "#FFF";
    //data
    private String stepWizr = "arquivados";

    public void linha() {
        this.tipoGrafico = "MSLine";
    }

    public void primeiroPassoSalvarConsultaComoNova(BeanListPontoEstagio beanListPontoEstagio, BeanListProdutos beanListProdutos, BeanListGraficosSaidaSalvos beanListGraficosSalvos, Boleanos boleanos) {
        //DATA
        switch (tipoDeConsulta) {
            case 0://pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(beanListGraficosSalvos.getGrafSaidaPreDet());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    stepWizr = "data";
                    return;
                }
                break;
            case 1://custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(beanListGraficosSalvos.getGrafSaidaCustom());
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
        //PONTOS
        if (beanListPontoEstagio.getPontosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.pontoNemEumNaLista);
            stepWizr = "pontos";
            return;
        }

        ABSGraficoSaidaSalvo confereDisponibilidadeNome = confereDisponibilidadeNome(beanListGraficosSalvos);
        if (confereDisponibilidadeNome == null) {
            salvarGrafico(beanListGraficosSalvos, beanListProdutos, beanListPontoEstagio);
        } else {
            beanListGraficosSalvos.setAbsGrafSaidaSalvoPraSerManipulado(confereDisponibilidadeNome);
            RequestContext.getCurrentInstance().addCallbackParam("nomeRepetido", true);
            boleanos.ambosTrue(4);
        }
    }

    public void salvaConsultaComoNova(BeanListPontoEstagio beanListPontoEstagio, BeanListProdutos beanListProdutos, BeanListGraficosSaidaSalvos beanListGraficosSalvos, Boleanos boleanos) {
        if (confereDisponibilidadeNome(beanListGraficosSalvos) != null) {
            UtilMensagens.alerta(UtilMensagens.graficoExistenteEscolhaOutro);
        } else {
            salvarGrafico(beanListGraficosSalvos, beanListProdutos, beanListPontoEstagio);
            boleanos.ambosFalse(5, 0);
            RequestContext.getCurrentInstance().addCallbackParam("fechaJanela", true);
        }
    }

    /**
     * confere se é possivel savar o grafico com o nome tal
     *
     * @param beanListGraficosSalvos
     * @return
     */
    private ABSGraficoSaidaSalvo confereDisponibilidadeNome(BeanListGraficosSaidaSalvos beanListGraficosSalvos) {
        CPGraficoSaidaPreDeterminado preDeterminado = DaoGraficoSaidaPredeterminada.existeEsseNome(beanListGraficosSalvos.getAbsGrafSaidaSalvo().getNome());
        if (preDeterminado != null) {
            return preDeterminado;
        }
        CPGraficoSaidaCustom custom = DaoGraficoSaidaCustom.existeEsseNome(beanListGraficosSalvos.getAbsGrafSaidaSalvo().getNome());
        if (custom != null) {
            return custom;
        }
        return null;
    }

    /**
     * procedimento padrão pra salvar gráfico
     *
     *
     *
     * @param beanListGraficosSaidaSalvos
     * @param beanListProdutos
     * @param beanListPontoEstagio
     */
    public void salvarGrafico(BeanListGraficosSaidaSalvos beanListGraficosSaidaSalvos, BeanListProdutos beanListProdutos, BeanListPontoEstagio beanListPontoEstagio) {
        //pra salvar um novo grafico precisa-se saber se o grafico a ser salvo é custom ou pre
        switch (tipoDeConsulta) {
            case 0: //pre deter
                //cria nova instancia
                CPGraficoSaidaPreDeterminado saidaPreDeterminado = new CPGraficoSaidaPreDeterminado();
                saidaPreDeterminado.setNome(beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvo().getNome());
                saidaPreDeterminado.setAgrupamento(beanListGraficosSaidaSalvos.getGrafSaidaPreDet().getAgrupamento());
                saidaPreDeterminado.setAte(beanListGraficosSaidaSalvos.getGrafSaidaPreDet().getAte());
                saidaPreDeterminado.setDe(beanListGraficosSaidaSalvos.getGrafSaidaPreDet().getDe());
                saidaPreDeterminado = DaoGraficoSaidaPredeterminada.mergeII(saidaPreDeterminado);
                new DaoGraficoSaidaPredeterminadaPontos().mergeS(saidaPreDeterminado, beanListPontoEstagio.getPontosManipulados());
                new DaoGraficoSaidaPredeterminadaProdutos().mergList(saidaPreDeterminado, beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvoPraSerManipulado(saidaPreDeterminado);
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(saidaPreDeterminado);
                UtilMensagens.ok(UtilMensagens.graficoSalvo);
                break;
            case 1: //custom
                CPGraficoSaidaCustom saidaCustom = new CPGraficoSaidaCustom();
                saidaCustom.setNome(beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvo().getNome());
                saidaCustom.setAgrupamento(beanListGraficosSaidaSalvos.getGrafSaidaCustom().getAgrupamento());
                saidaCustom.setAte(beanListGraficosSaidaSalvos.getGrafSaidaCustom().getAte());
                saidaCustom.setDe(beanListGraficosSaidaSalvos.getGrafSaidaCustom().getDe());
                saidaCustom = DaoGraficoSaidaCustom.mergeII(saidaCustom);
                new DaoGraficoSaidaCustomPontos().mergeS(saidaCustom, beanListPontoEstagio.getPontosManipulados());
                new DaoGraficoSaidaCustomProdutos().mergList(saidaCustom, beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvoPraSerManipulado(saidaCustom);
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(saidaCustom);
                UtilMensagens.ok(UtilMensagens.graficoSalvo);
                break;
        }
        beanListGraficosSaidaSalvos.atualizaListaDeGraficosSaidaSalvo();
        beanListGraficosSaidaSalvos.filtra(false);
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
    public void atualizaGraficoSalvo(BeanListGraficosSaidaSalvos beanListGraficosSaidaSalvos, BeanListProdutos beanListProdutos, BeanListPontoEstagio beanListPontoEstagio) {
//        ABSGraficoSaidaSalvo grafico = beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvoPraSerManipulado();
//        if (!UtilString.getNomeClasse(beanListGraficosEntradaSalvos.graficoManipulado(tipoDeConsulta)).equals(UtilString.getNomeClasse(grafico))) {
//            if (beanListGraficosEntradaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoCustom) {
//                DaoGraficoEntradaPreDeterminada.delete((CPGraficoEntradaPreDeterminado) grafico);
//                beanListGraficosEntradaSalvos.getGrafEntradaCustom().setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
//                grafico = DaoGraficoEntradaCustom.mergeII((CPGraficoEntradaCustom) beanListGraficosEntradaSalvos.getGrafEntradaCustom());
//                new DaoGraficoEntradaCustomProdutos().mergList((CPGraficoEntradaCustom) grafico, beanListProdutos.getProdutosManipulados());
//                beanListGraficosEntradaSalvos.setAbsGrafEntradaSalvo2(grafico);
//            } else if (beanListGraficosEntradaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoPreDet) {
//                DaoGraficoEntradaCustom.delete((CPGraficoEntradaCustom) grafico);
//                beanListGraficosEntradaSalvos.getGrafEntradaPreDet().setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
//                grafico = (CPGraficoEntradaPreDeterminado) DaoGraficoEntradaPreDeterminada.mergeII((CPGraficoEntradaPreDeterminado) beanListGraficosEntradaSalvos.getGrafEntradaPreDet());
//                new DaoGraficoEntradaPreDeterminadaProdutos().mergList((CPGraficoEntradaPreDeterminado) grafico, beanListProdutos.getProdutosManipulados());
//                beanListGraficosEntradaSalvos.setAbsGrafEntradaSalvo2(grafico);
//            }
//        } else { 
//            if (grafico instanceof InterfaceGraficoCustom) {
//                grafico.setAgrupamento(beanListGraficosEntradaSalvos.getGrafEntradaCustom().getAgrupamento());
//                grafico.setDe(beanListGraficosEntradaSalvos.getGrafEntradaCustom().getDe());
//                grafico.setAte(beanListGraficosEntradaSalvos.getGrafEntradaCustom().getAte());
//                grafico.setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
//                new DaoGraficoEntradaCustomProdutos().atualizaProdutos((CPGraficoEntradaCustom) grafico, beanListProdutos.getProdutosManipulados());
//            } else if (grafico instanceof InterfaceGraficoPreDet) {
//                grafico.setAgrupamento(beanListGraficosEntradaSalvos.getGrafEntradaPreDet().getAgrupamento());
//                grafico.setDe(beanListGraficosEntradaSalvos.getGrafEntradaPreDet().getDe());
//                grafico.setAte(beanListGraficosEntradaSalvos.getGrafEntradaPreDet().getAte());
//                grafico.setNome(beanListGraficosEntradaSalvos.getAbsGrafEntradaSalvo().getNome());
//                new DaoGraficoEntradaPreDeterminadaProdutos().atualizaProdutos((CPGraficoEntradaPreDeterminado) grafico, beanListProdutos.getProdutosManipulados());
//            }
//        }
//        beanListGraficosEntradaSalvos.atualizaListaDeGraficos();
//        beanListGraficosEntradaSalvos.filtra(false);
        //---------------------------------------------------------
        ABSGraficoSaidaSalvo grafico = beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvoPraSerManipulado();
        if (!UtilString.getNomeClasse(beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta)).equals(UtilString.getNomeClasse(grafico))) {
            if (beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoCustom) {
                DaoGraficoSaidaPredeterminada.delete((CPGraficoSaidaPreDeterminado) grafico);
                beanListGraficosSaidaSalvos.getGrafSaidaCustom().setNome(beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvo().getNome());
                grafico = DaoGraficoSaidaCustom.mergeII((CPGraficoSaidaCustom) beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta));
                new DaoGraficoSaidaCustomPontos().mergeS(grafico, beanListPontoEstagio.getPontosManipulados());
                new DaoGraficoSaidaCustomProdutos().mergList(grafico, beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(grafico);
            } else if (beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta) instanceof InterfaceGraficoPreDet) {
                DaoGraficoSaidaCustom.delete((CPGraficoSaidaCustom) grafico);
                beanListGraficosSaidaSalvos.getGrafSaidaPreDet().setNome(beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvo().getNome());
                grafico = DaoGraficoSaidaPredeterminada.mergeII((CPGraficoSaidaPreDeterminado) beanListGraficosSaidaSalvos.graficoManipulado(tipoDeConsulta));
                new DaoGraficoSaidaPredeterminadaPontos().mergeS(grafico, beanListPontoEstagio.getPontosManipulados());
                new DaoGraficoSaidaPredeterminadaProdutos().mergList(grafico, beanListProdutos.getProdutosManipulados());
                beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(grafico);
            }
        } else {
            if (grafico instanceof InterfaceGraficoCustom) {
                grafico.setAgrupamento(beanListGraficosSaidaSalvos.getGrafSaidaCustom().getAgrupamento());
                grafico.setDe(beanListGraficosSaidaSalvos.getGrafSaidaCustom().getDe());
                grafico.setAte(beanListGraficosSaidaSalvos.getGrafSaidaCustom().getAte());
                grafico.setNome(beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvo().getNome());
                DaoGraficoSaidaCustom.merge((CPGraficoSaidaCustom) grafico);
                new DaoGraficoSaidaCustomPontos().atualizaPontos((CPGraficoSaidaCustom) grafico, beanListPontoEstagio.getPontosManipulados());
                new DaoGraficoSaidaCustomProdutos().atualizaProdutos((CPGraficoSaidaCustom) grafico, beanListProdutos.getProdutosManipulados());
            } else if (grafico instanceof InterfaceGraficoPreDet) {
                grafico.setAgrupamento(beanListGraficosSaidaSalvos.getGrafSaidaPreDet().getAgrupamento());
                grafico.setDe(beanListGraficosSaidaSalvos.getGrafSaidaPreDet().getDe());
                grafico.setAte(beanListGraficosSaidaSalvos.getGrafSaidaPreDet().getAte());
                grafico.setNome(beanListGraficosSaidaSalvos.getAbsGrafSaidaSalvo().getNome());
                DaoGraficoSaidaPredeterminada.merge((CPGraficoSaidaPreDeterminado) grafico);
                new DaoGraficoSaidaPredeterminadaPontos().atualizaPontos((CPGraficoSaidaPreDeterminado) grafico, beanListPontoEstagio.getPontosManipulados());
                new DaoGraficoSaidaPredeterminadaProdutos().atualizaProdutos((CPGraficoSaidaPreDeterminado) grafico, beanListProdutos.getProdutosManipulados());
            }
            beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvoPraSerManipulado(grafico);
            beanListGraficosSaidaSalvos.setAbsGrafSaidaSalvo2(grafico);
        }
        beanListGraficosSaidaSalvos.atualizaListaDeGraficosSaidaSalvo();
        beanListGraficosSaidaSalvos.filtra(false);
        UtilMensagens.ok(UtilMensagens.graficoAtualizado);
    }

    /**
     * carrega gráfico salvo
     *
     * @param pontos
     * @param produtos
     * @param graficoSalvo
     */
    public void carregaGrafico(List<CPPontoDeEstagio> pontos, List<CPProduto> produtos, ABSGraficoSalvo graficoSalvo) {
        if (graficoSalvo instanceof InterfaceGraficoCustom) {
            tipoDeConsulta = 1;
        } else if (graficoSalvo instanceof InterfaceGraficoPreDet) {
            tipoDeConsulta = 0;
        }
        graficoSalvo = geradorXml.acertaData(graficoSalvo);
        geradorXml.defineCPSaidaProdutosNoPeriodo(pontos, produtos, graficoSalvo);
        geradorXml.criaDatasParaRealizarConsultas(graficoSalvo);
        geradorXml.defineDataSetSaidaGeral(produtos);
        geradorXml.deixaDataApresentavel(graficoSalvo);
        geradorXml.geraGrafGeral("grafico", "Todos os pontos");
        geradorXml.geraGrafDeCadaPonto(produtos, pontos);
        navegaNoWizard(5l);
        try {
            Thread.sleep(Boleanos.timeDefaultGerarGrafico);
        } catch (InterruptedException ex) {
        }
        UtilMensagens.info(UtilMensagens.graficoCarregado);
    }

    private void geraGrafico(BeanListPontoEstagio lpe, BeanListProdutos lp, BeanListGraficosSaidaSalvos lgs) {
        switch (tipoDeConsulta) {
            case 0: //pre determinado
                lgs.setGrafSaidaPreDet((CPGraficoSaidaPreDeterminado) geradorXml.acertaData(lgs.getGrafSaidaPreDet()));
                geradorXml.defineCPSaidaProdutosNoPeriodo(lpe.getPontosManipulados(), lp.getProdutosManipulados(), lgs.getGrafSaidaPreDet());
                geradorXml.criaDatasParaRealizarConsultas(lgs.getGrafSaidaPreDet());
                geradorXml.defineDataSetSaidaGeral(lp.getProdutosManipulados());
                geradorXml.deixaDataApresentavel(lgs.getGrafSaidaPreDet());
                geradorXml.geraGrafGeral("grafico", "Todos os pontos");
                geradorXml.geraGrafDeCadaPonto(lp.getProdutosManipulados(), lpe.getPontosManipulados());
                break;
            case 1: // custom
                lgs.setGrafSaidaCustom((CPGraficoSaidaCustom) geradorXml.acertaData(lgs.getGrafSaidaCustom()));
                geradorXml.defineCPSaidaProdutosNoPeriodo(lpe.getPontosManipulados(), lp.getProdutosManipulados(), lgs.getGrafSaidaCustom());
                geradorXml.criaDatasParaRealizarConsultas(lgs.getGrafSaidaCustom());
                geradorXml.defineDataSetSaidaGeral(lp.getProdutosManipulados());
                geradorXml.deixaDataApresentavel(lgs.getGrafSaidaCustom());
                geradorXml.geraGrafGeral("grafico", "Todos os pontos");
                geradorXml.geraGrafDeCadaPonto(lp.getProdutosManipulados(), lpe.getPontosManipulados());
                break;
        }
        navegaNoWizard(5l);
        try {
            Thread.sleep(Boleanos.timeDefaultGerarGrafico);
        } catch (InterruptedException ex) {
        }
    }

    // # METODOS DE AMBIENTE
    public void confereProdutos(BeanListProdutos lp) {
        if (lp.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.produtoNemNaLista);
        } else {
            navegaNoWizard(3);
        }
    }

    public void conferePontos(BeanListPontoEstagio lp, BeanListProdutos lpp, BeanListGraficosSaidaSalvos lgs) {
        if (lp.getPontosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.produtoNemNaLista);
        } else {
            navegaNoWizard(5);
            geraGrafico(lp, lpp, lgs);
        }
    }

    //atulizar esse método, sem repetições de destino
    public void navegaNoWizard(long navega) {
        switch ((int) navega) {
            //em ARQUIVADOS indo para data
            case 0:
                stepWizr = "data";
                break;
            //em DATA indo para PRODUTOS
            case 1:
                stepWizr = "produtos";
                break;
            // em DATA indo para Arquivados
            case 2:
                stepWizr = "arquivados";
                break;
            //em PRODUTOS indo para PONTO 
            case 3:
                stepWizr = "pontos";
                break;
            //em PRODUTOS indo para DATA
            case 4:
                stepWizr = "data";
                break;
            //em PONTO indo para GRAFICO
            case 5:
                stepWizr = "grafico";
                break;
            //em PONTO indo para PRODUTOS
            case 6:
                stepWizr = "produtos";
                break;
            //em GRAFICO indo para PONTOS
            case 7:
                stepWizr = "pontos";
                break;
        }

    }

    public void confereData(BeanListGraficosSaidaSalvos gs) {
        switch (tipoDeConsulta) {
            case 0: //pre determinado
                String validaDataGraficoPre = UtilData.validaDataGrafico(gs.getGrafSaidaPreDet());
                if (!validaDataGraficoPre.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPre);
                    return;
                }
                break;
            case 1: //custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(gs.getGrafSaidaCustom());
                if (!validaDataGraficoCustom.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoCustom);
                    return;
                }
                break;
        }
        navegaNoWizard(1l);
    }

    /**
     * método executado na janela de grafico manipulado tenta pq pode haver
     * coisas irregulares, falta de produto ou ponto data inválida etc
     *
     * @param beanListGraficosSaidaSalvos
     * @param beanListProdutos
     * @param beanListPontoEstagio
     */
    public void tentaAtualizarGraficoManipulado(BeanListGraficosSaidaSalvos beanListGraficosSaidaSalvos, BeanListProdutos beanListProdutos, BeanListPontoEstagio beanListPontoEstagio) {
        //DATA
        switch (tipoDeConsulta) {
            case 0://pre determinado
                String validaDataGraficoPreD = UtilData.validaDataGrafico(beanListGraficosSaidaSalvos.getGrafSaidaPreDet());
                if (!validaDataGraficoPreD.isEmpty()) {
                    UtilMensagens.info(validaDataGraficoPreD);
                    stepWizr = "data";
                    return;
                }
                break;
            case 1://custom
                String validaDataGraficoCustom = UtilData.validaDataGrafico(beanListGraficosSaidaSalvos.getGrafSaidaCustom());
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
        //PONTOS
        if (beanListPontoEstagio.getPontosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.pontoNemEumNaLista);
            stepWizr = "produtos";
            return;
        }
        atualizaGraficoSalvo(beanListGraficosSaidaSalvos, beanListProdutos, beanListPontoEstagio);
    }

    //# GET E SET
    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public NewGeradorXML getGeradorXml() {
        return geradorXml;
    }

    public void setGeradorXml(NewGeradorXML geradorXml) {
        this.geradorXml = geradorXml;
    }

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

    public String getTipoGrafico() {
        return tipoGrafico;
    }

    public void setTipoGrafico(String tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }
}