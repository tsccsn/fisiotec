/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.xml;

import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import cp.estoque.entrada.CPEntradaProduto;
import cp.estoque.saida.CPSaidaProduto;
import cp.grafico.ABSGraficoSalvo;
import dao.DaoPontoDeEstagio;
import dao.estoque.entrada.DaoEntradaProduto;
import dao.estoque.saida.DaoSaidaProduto;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import utilidades.comparadores.OrdenaEntradaProduto;
import utilidades.comparadores.OrdenaSaidaProduto;
import utilidades.data.UtilData;
import utilidades.graficos.IO.GrafDataSet;
import utilidades.mensagens.UtilMensagens;
import utilidades.seguranca.Url;
import utilidades.sql.SQLConsulta;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago-Asus
 */
public class NewGeradorXML implements Serializable {

    private static final long serialVersionUID = 1L;
    //# ENTRADA
    private List<CPEntradaProduto> listCPEntradaProdutos = new LinkedList<>();
    private List<GrafDataSet> listDataSetEntrada = new LinkedList<>();
    //# FIM ENTRADA
    private List<CPSaidaProduto> listCpProdutosSaidosNoPeriodo = new LinkedList<>();
    private List<String> listDatasApresentavel = new LinkedList<>();
    private List<String> listDatasParaConsulta = new LinkedList<>();
    private List<String> listDatasAbreviadas = new LinkedList<>();
    private List<GrafDataSet> listDataSetSaida = new LinkedList<>();
    private List<CPPontoDeEstagio> pontosDeEstagio = DaoPontoDeEstagio.getAll("nome");
    private int cont = 0;
    private String codigoSecreto;
    // # IO
    // # Atributos
    private List<GrafDataSet> listDataSetDiferencaIO = new LinkedList<>();
    private List<GrafDataSet> listDataSetDiferencaIOVariacao = new LinkedList<>();
    private List<List<GrafDataSet>> listDataSetDiferencaIOPorcentagemConsumidaPorCadaProduto = new LinkedList<>();
    //data - produto | % consumida em cada ponto
    private HashMap<String, List<String>> mapaIO = new HashMap<>();
    //data - produto | % qtd consumida
    private HashMap<String, String> mapaTotalPorData = new HashMap<>();
    // # FIM Atributos  

    public void defineDataSetDiferencaEntradaSaida(List<CPProduto> produtos, ABSGraficoSalvo grafico) {
        listDataSetDiferencaIO = new LinkedList<>();

        grafico = acertaData(grafico);

        // # ENTRADA
        defineCPEntradaProdutoNoPerido(produtos, grafico);
        defineDataSetEntrada(produtos);
        // # FIM ENTRADA

        // # SAIDA
        defineCPSaidaProdutosNoPeriodo(DaoPontoDeEstagio.getAll("nome"), produtos, grafico);
        defineDataSetSaidaGeral(produtos);
        // # FIM SAIDA

        for (GrafDataSet xGrafDataSet : listDataSetSaida) {

            GrafDataSet dif = new GrafDataSet();
            dif.setNome(xGrafDataSet.getNome());
            int ds = listDataSetSaida.indexOf(xGrafDataSet);
            int v = 0;

            List<String> difValores = new LinkedList<>();
            for (String xS : xGrafDataSet.getValores()) {
                int entrou = Integer.parseInt(listDataSetEntrada.get(ds).getValores().get(v));
                int saiu = Integer.parseInt(listDataSetSaida.get(ds).getValores().get(v));
                difValores.add(entrou - saiu + "");
                v++;
            }

            dif.setValores(difValores);
            listDataSetDiferencaIO.add(dif);
            geraGraf("diferencaEntradaSaida", "Saldo", listDataSetDiferencaIO);
        }
    }

    public void defineDataSetVariacaoDaQuantidadeEmEstoque(List<CPProduto> produtos, ABSGraficoSalvo grafico) {
        listDataSetDiferencaIOVariacao = new LinkedList<>();
        //é preciso saber a quantidade em estoque do produto na data inicial do periodo

        for (CPProduto xProduto : produtos) {

            GrafDataSet ds = new GrafDataSet(xProduto.toString());

            int quantidadeAntesDoPeriodo = SQLConsulta.quantidadeEmEstoqueDeUmProdutoXEmDataY(grafico.getDe(), xProduto);
            int quantidadeAtualNaLinhaDoTempo = quantidadeAntesDoPeriodo;
            List<String> valores = new LinkedList<>();

            for (String xData : listDatasParaConsulta) {
                Timestamp i = UtilData.converteEmDate(xData.substring(0, 10));
                Timestamp f = UtilData.converteEmDate(xData.substring(13));
                int entrou = SQLConsulta.quantidadeEntradaDeUmProdutoXEmUmPeriodoDeTempoY(i, f, xProduto);
                int saiu = SQLConsulta.quantidadeSaidaDeUmProdutoXEmUmPeriodoDeTempoY(i, f, xProduto);
                int valor = quantidadeAtualNaLinhaDoTempo + entrou - saiu;
                quantidadeAtualNaLinhaDoTempo = valor;
                valores.add(quantidadeAtualNaLinhaDoTempo + "");
            }

            ds.setValores(valores);
            listDataSetDiferencaIOVariacao.add(ds);
            geraGraf("variacaoQuantidade", "Vriação de Quantidade", listDataSetDiferencaIOVariacao);
        }
    }

    public void diferencaDeConsumoEntreOSPontos(List<CPProduto> produtos) {
        listDataSetDiferencaIOPorcentagemConsumidaPorCadaProduto = new LinkedList<>();
        //data - produto | % consumida em cada ponto
        mapaIO = new HashMap<>();
        for (String xData : listDatasParaConsulta) {
            Timestamp i = UtilData.converteEmDate(xData.substring(0, 10));
            Timestamp f = UtilData.converteEmDate(xData.substring(13));
            i = UtilData.inicioDia(i);
            f = UtilData.fimDia(f);
            // varendo o mes de Janeiro
            for (CPProduto xProduto : produtos) {
                List<Integer> listaDeValores = new LinkedList<>();
                //varrendo o produto alcool
                for (CPPontoDeEstagio xPonto : pontosDeEstagio) {
                    //varrendo o ponto tal
                    listaDeValores.add(SQLConsulta.quantidadeSaidaDeUmProdutoXParaUmPontoZEmUmPeriodoDeTempoY(i, f, xProduto, xPonto));
                }
                int total = 0;
                for (Integer xValor : listaDeValores) {
                    total = total + xValor;
                }
                mapaTotalPorData.put(listDatasParaConsulta.indexOf(xData) + "-" + produtos.indexOf(xProduto), total + "");
                List<String> listaDePorcentagem = new LinkedList<>();
                // total = 100%
                // xValor  = ?
                double p = (total / 100d);
                for (Integer xValor : listaDeValores) {
                    try {
                        listaDePorcentagem.add((new DecimalFormat("0.0000").format((xValor / p))).replace(",", ".") + "");
                    } catch (ArithmeticException e) {
                        listaDePorcentagem.add("0");
                    }

                }
                mapaIO.put(listDatasParaConsulta.indexOf(xData) + "-" + produtos.indexOf(xProduto), listaDePorcentagem);
            }
        }
        // produto - ponto | valores
        HashMap<String, List<Double>> produtoPonto = new HashMap<>();
        for (CPProduto xProduto : produtos) {
            for (CPPontoDeEstagio xPonto : pontosDeEstagio) {
                List<Double> treta = new LinkedList<>();
                produtoPonto.put(produtos.indexOf(xProduto) + "-" + pontosDeEstagio.indexOf(xPonto), treta);
            }
        }
        geraGrafDiferencaDeConsumoEntreOsPontos("porcentagem", "% consumida de cada ponto", mapaIO, produtos);
    }

    public void geraGrafDiferencaDeConsumoEntreOsPontos(String identificadorXML, String nomeGrafico, HashMap<String, List<String>> mapa, List<CPProduto> produtos) {
        List<String> cores = new LinkedList<>();
        cores.add("d54f54");
        cores.add("41c1ff");
        cores.add("daa8ff");
        cores.add("9ec673");
        cores.add("f0a271");
        cores.add("d5fd5e");
        cores.add("ffff6e");
        cores.add("ebffff");
        cores.add("4ec0c0");
        Element chart = new Element("chart");
        chart.setAttribute("caption", nomeGrafico);
        chart.setAttribute("showValues", "0");
        chart.setAttribute("bgColor", "FFFFFF");
        chart.setAttribute("showBorder", "0");
        chart.setAttribute("palette", "3");
        chart.setAttribute("numdivlines", "3");
        chart.setAttribute("numberPrefix", "%");
        chart.setAttribute("showSum", "0");
        chart.setAttribute("useRoundEdges", "1");
        Element categories = new Element("categories");
        for (String c : listDatasAbreviadas) {
            Element e = new Element("category");
            e.setAttribute("Name", c);
            categories.addContent(e);
        }
        chart.addContent(categories);
        //data - produto | % consumida em cada ponto
        for (CPProduto xProduto : produtos) {
            Element dataSetProduto = new Element("dataset");
            for (CPPontoDeEstagio xPonto : pontosDeEstagio) {
                Element dataSetProdutoPonto = new Element("dataset");
                dataSetProdutoPonto.setAttribute("color", cores.get(pontosDeEstagio.indexOf(xPonto)));
                dataSetProdutoPonto.setAttribute("seriesName", xProduto.toString() + " - " + xPonto.getNome());
                List<String> valores = new LinkedList<>();
                for (String xData : listDatasParaConsulta) {
                    //lista de valores do produto e do ponto
                    List<String> get = mapa.get(listDatasParaConsulta.indexOf(xData) + "-" + produtos.indexOf(xProduto));
                    valores.add(get.get(pontosDeEstagio.indexOf(xPonto)) + "");
                }
                for (String xString : valores) {
                    Element set = new Element("set");
                    set.setAttribute("value", xString);
                    dataSetProdutoPonto.addContent(set);
                }

                dataSetProduto.addContent(dataSetProdutoPonto);
            }
            chart.addContent(dataSetProduto);
        }
        //Criando o documento XML (montado)  
        Document doc = new Document();

        doc.setRootElement(chart);

        escreveXML(doc, identificadorXML);
    }
    // # GET

    public List<GrafDataSet> getListDataSetDiferencaIOVariacao() {
        return listDataSetDiferencaIOVariacao;
    }

    public List<GrafDataSet> getListDataSetDiferencaIO() {
        return listDataSetDiferencaIO;
    }

    // # FIM IO
    public void geraCodigo() {
        apagaArquivosAnteriores();
        codigoSecreto = UtilString.geraCodigoSecreto();
    }

    public String verData(int tipoDeConsulta) {
        switch (tipoDeConsulta) {
            case 0: //pre determinado
                if (cont < listDatasApresentavel.size()) {
                    cont++;
                    return listDatasApresentavel.get(cont - 1);
                } else {
                    cont = 1;
                    return listDatasApresentavel.get(cont - 1);
                }
            case 1:
                if (cont < listDatasParaConsulta.size()) {
                    cont++;
                    return listDatasParaConsulta.get(cont - 1);
                } else {
                    cont = 1;
                    return listDatasParaConsulta.get(cont - 1);
                }
        }
        return "";

    }

    public void defineCPSaidaProdutosNoPeriodo(List<CPPontoDeEstagio> pontos, List<CPProduto> produtos, ABSGraficoSalvo gs) {
        listCpProdutosSaidosNoPeriodo = DaoSaidaProduto.produtosMovimentadosNoPeriodo(produtos, gs.getDe(), gs.getAte(), pontos);
    }

    public void defineCPEntradaProdutoNoPerido(List<CPProduto> produtos, ABSGraficoSalvo gs) {
        listCPEntradaProdutos = DaoEntradaProduto.produtosMovimentadosNoPeriodo(produtos, gs.getDe(), gs.getAte());
    }

    public void defineDataSetSaidaGeral(List<CPProduto> produtos) {
        listDataSetSaida = defineDataSetSaida(produtos, listCpProdutosSaidosNoPeriodo);
    }

    public List<GrafDataSet> dataSetDoPonto(List<CPProduto> produtos, CPPontoDeEstagio ponto) {
        List<CPSaidaProduto> p = new LinkedList<>();
        for (CPSaidaProduto sp : listCpProdutosSaidosNoPeriodo) {
            if (sp.getSaida().getDestino().getId() == ponto.getId()) {
                p.add(sp);
            }
        }
        return defineDataSetSaida(produtos, p);
    }

    public void defineDataSetEntrada(List<CPProduto> produtos) {
        listDataSetEntrada = new LinkedList<>();
        OrdenaEntradaProduto osp = new OrdenaEntradaProduto();
        Collections.sort(listCPEntradaProdutos, osp);
        int pos = 0;
        for (CPProduto p : produtos) {
            GrafDataSet ds = new GrafDataSet(p.toString());
            for (String d : listDatasParaConsulta) {
                Timestamp i = UtilData.converteEmDate(d.substring(0, 10));
                Timestamp f = UtilData.converteEmDate(d.substring(13));
                i = UtilData.inicioDia(i);
                f = UtilData.fimDia(f);

                int quantidade = 0;
                try {
                    while (p.getId() == listCPEntradaProdutos.get(pos).getProduto().getId()
                            && listCPEntradaProdutos.get(pos).getEntrada().getDataEntrada().getTime() >= i.getTime()
                            && listCPEntradaProdutos.get(pos).getEntrada().getDataEntrada().getTime() < f.getTime()) {

                        quantidade += listCPEntradaProdutos.get(pos).getQuantidade();
                        pos++;
                    }
                } catch (IndexOutOfBoundsException e) {
                } finally {
                    ds.addValor(quantidade);
                }
            }
            listDataSetEntrada.add(ds);
        }
    }

    public void criaDatasParaRealizarConsultas(ABSGraficoSalvo gs) {
        listDatasParaConsulta = UtilData.criaIntervaloDeDatasPreDeterminadasAndCustom(gs);
    }

    public ABSGraficoSalvo acertaData(ABSGraficoSalvo g) {
        return UtilData.acertaData(g);
    }

    public void geraGrafDeCadaPonto(List<CPProduto> produtos, List<CPPontoDeEstagio> pontos) {
        for (CPPontoDeEstagio p : pontos) {
            geraGraf("grafico" + p.getId(), p.getNome(), dataSetDoPonto(produtos, p));
        }
    }

    public void geraGrafGeral(String identificadorXML, String nomeGrafico) {
        geraGraf(identificadorXML, nomeGrafico, listDataSetSaida);
    }

    public void geraGrafEntrada() {
        geraGraf("grafico", "Gráfico entrada", listDataSetEntrada);
    }

    public void geraGrafPizza(String identificadorXML, String nomeGrafico, List<String> nome, List<String> valores) {
        Element chart = new Element("chart");
        chart.setAttribute("caption", nomeGrafico);
        chart.setAttribute("showValues", "0");
        chart.setAttribute("bgColor", "FFFFFF");
        chart.setAttribute("showBorder", "0");
        Element set = new Element("set");
        for (String xValor : valores) {
            set.setAttribute("value", xValor);
            set.setAttribute("label", nome.get(valores.indexOf(xValor)));
        }
        chart.addContent(set);
        //Criando o documento XML (montado)  
        Document doc = new Document();
        doc.setRootElement(chart);
        escreveXML(doc, identificadorXML);
    }

    public void geraGraf(String identificadorXML, String nomeGrafico, List<GrafDataSet> ds) {
        Element chart = new Element("chart");
        chart.setAttribute("caption", nomeGrafico);
        chart.setAttribute("showValues", "0");
        chart.setAttribute("bgColor", "FFFFFF");
        chart.setAttribute("showBorder", "0");
        Element categories = new Element("categories");
        for (String c : listDatasApresentavel) {
            Element e = new Element("category");
            e.setAttribute("Name", c);
            categories.addContent(e);

        }
        chart.addContent(categories);
        for (GrafDataSet g : ds) {
            Element e = new Element("dataset");
            e.setAttribute("seriesName", g.getNome());
            for (String s : g.getValores()) {
                Element m = new Element("set");
                m.setAttribute("value", s);
                e.addContent(m);
            }
            chart.addContent(e);
        }
        //Criando o documento XML (montado)  
        Document doc = new Document();
        doc.setRootElement(chart);
        escreveXML(doc, identificadorXML);
    }

    private void escreveXML(Document d, String identificadorXML) {
        try {
            XMLOutputter out = new XMLOutputter();
            File f = new File(Url.localpc + "estoque/grafico/xml/" + codigoSecreto + identificadorXML + ".xml");
            f.delete();
            try (FileWriter writer = new java.io.FileWriter(f)) {
                out.output(d, writer);
                writer.flush();
            }
        } catch (Exception e) {
            System.out.println("pau ao escrever xml!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.getMessage();
        }
    }

    public void apagaArquivosAnteriores() {

        File dir = new File(Url.localpc + "estoque/grafico/xml/");
        File[] listFiles = dir.listFiles();
        System.out.println(listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isFile()) {
                listFiles[i].delete();
            }
        }
    }

    public List<String> abreviaData(ABSGraficoSalvo gs) {
        List<String> l = new LinkedList<>();
        for (String s : listDatasApresentavel) {
            l.add(s.replace("Semestre", "Sem").replace("de", "-").
                    replace("Bimestre", "Bim").replace("de", "-").
                    replace("Trimestre", "Tri").replace("de", "-").
                    replace("Janeiro", "Jan").
                    replace("Fevereiro", "Fev").
                    replace("Março", "Mar").
                    replace("Maio", "Mai").
                    replace("Junho", "Jun").
                    replace("Julho", "Jul").
                    replace("Agosto", "Ago").
                    replace("Setembro", "Set").
                    replace("Outubro", "Out").
                    replace("Setembro", "Set").
                    replace("Novembro", "Nov").
                    replace("Dezembro", "Dez"));
        }
        listDatasAbreviadas = l;
        return l;
    }

    public void deixaDataApresentavel(ABSGraficoSalvo gs) {
        listDatasApresentavel = UtilData.deixaDataApresentavel(gs, listDatasParaConsulta);
    }

    public List<GrafDataSet> getListDataSetSaida() {
        return listDataSetSaida;
    }

    public List<CPEntradaProduto> getListCPEntradaProdutos() {
        return listCPEntradaProdutos;
    }

    public List<GrafDataSet> getListDataSetEntrada() {
        return listDataSetEntrada;
    }

    public List<String> getListDatasParaConsulta() {
        return listDatasParaConsulta;
    }

    public List<String> getListDatasApresentavel() {
        return listDatasApresentavel;
    }

    public HashMap<String, List<String>> getMapaIO() {
        return mapaIO;
    }

    public HashMap<String, String> getMapaTotalPorData() {
        return mapaTotalPorData;
    }

    public String getCodigoSecreto() {
        return codigoSecreto;
    }

    // # PRIVATE
    private List<GrafDataSet> defineDataSetSaida(List<CPProduto> produtos, List<CPSaidaProduto> listSaidaProduto) {
        List<GrafDataSet> r = new LinkedList<>();
        OrdenaSaidaProduto osp = new OrdenaSaidaProduto();
        Collections.sort(listSaidaProduto, osp);
        int pos = 0;
        for (CPProduto p : produtos) {
            GrafDataSet ds = new GrafDataSet(p.toString());
            for (String d : listDatasParaConsulta) {
                Timestamp i = UtilData.inicioDia(UtilData.converteEmDate(d.substring(0, 10)).getTime());
                Timestamp f = UtilData.fimDia(UtilData.converteEmDate(d.substring(13)));
                int quantidade = 0;
                try {
                    while (p.getId() == listSaidaProduto.get(pos).getProduto().getId()
                            && listSaidaProduto.get(pos).getSaida().getDataSaida().getTime() >= i.getTime()
                            && listSaidaProduto.get(pos).getSaida().getDataSaida().getTime() < f.getTime()) {

                        quantidade += listSaidaProduto.get(pos).getQuantidade();
                        pos++;
                    }
                } catch (IndexOutOfBoundsException e) {
                } finally {
                    ds.addValor(quantidade);
                }
            }
            r.add(ds);
        }
        return r;
    }
    // # FIMPRIVATE
}
