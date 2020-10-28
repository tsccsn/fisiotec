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
import dao.estoque.saida.DaoSaidaProduto;
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import utilidades.comparadores.OrdenaEntradaProduto;
import utilidades.comparadores.OrdenaSaidaProduto;
import utilidades.data.UtilData;
import utilidades.graficos.IO.GrafDataSet;

/**
 *
 * @author Thiago-Asus
 */
public class GeradorXML implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<CPEntradaProduto> listCpProdutosEntradosNoPeriodo = new LinkedList<>();
    private List<CPSaidaProduto> listCpProdutosSaidosNoPeriodo = new LinkedList<>();
    private List<String> listDatas = new LinkedList<>();
    private List<GrafDataSet> listDataSet = new LinkedList<>();
    private int cont = 0;

    public void geraGrafSaidaDePontoDeterminado(CPPontoDeEstagio p, List<CPProduto> produtos, ABSGraficoSalvo gs, String identificador) {
        geraGrafIO(abreviaData(), dataSetDeDeterminadoPonto(p, gs, produtos), identificador, p.getNome());
    }

    public String verData() {
        if (cont < listDatas.size()) {
            cont++;
            return listDatas.get(cont - 1);
        } else {
            cont = 1;
            return listDatas.get(cont - 1);
        }
    }

    public ABSGraficoSalvo acertaDataPreDeterminado(ABSGraficoSalvo g) {
        switch (g.getAgrupamento()) {
            case "Mês":// mensal
                g.setDe(new Timestamp(UtilData.inicioMesDiaEHora(g.getDe()).getTime()));
                g.setAte(new Timestamp(UtilData.fimMesDataEHora(g.getAte()).getTime()));
                break;
            case "Bimestre":// bimestral
                g.setDe(new Timestamp(UtilData.inicioBimestre(g.getDe()).getTime()));
                g.setAte(new Timestamp(UtilData.fimBimestre(g.getAte()).getTime()));
                break;
            case "Trimestre":// trimestral
                g.setDe(new Timestamp(UtilData.inicioTrimestre(g.getDe()).getTime()));
                g.setAte(new Timestamp(UtilData.inicioTrimestre(g.getAte()).getTime()));
                break;
            case "Semestre":// semestre
                g.setDe(new Timestamp(UtilData.inicioSemestre(g.getDe()).getTime()));
                g.setAte(new Timestamp(UtilData.inicioSemestre(g.getAte()).getTime()));
                break;
            case "anos":// anual
                g.setDe(new Timestamp(UtilData.inicioAno(g.getDe()).getTime()));
                g.setAte(new Timestamp(UtilData.inicioAno(g.getAte()).getTime()));
                break;
        }
        return g;
    }

    public void defineCPSaidaProdutosDnoPeriodo(List<CPPontoDeEstagio> pontos, List<CPProduto> produtos, ABSGraficoSalvo gs) {
        setListCpProdutosSaidosNoPeriodo(DaoSaidaProduto.produtosMovimentadosNoPeriodo(produtos, gs.getDe(), gs.getAte(), pontos));
    }

    private List<String> abreviaData() {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
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
        return l;
    }

    public void geraGraf(String identificadorXML, String nomeGrafico) {
        geraGrafIO(abreviaData(), listDataSet, identificadorXML, nomeGrafico);
    }

    public void defineDataSetPredeterminadoSaida(List<CPProduto> produtos) {
        listDataSet = new LinkedList<>();
        OrdenaSaidaProduto osp = new OrdenaSaidaProduto();
        Collections.sort(listCpProdutosSaidosNoPeriodo, osp);
        int pos = 0;
        for (CPProduto p : produtos) {
            GrafDataSet ds = new GrafDataSet(p.toString());
            for (String d : listDatas) {
                Date i = UtilData.inicioDia(UtilData.converteEmDate(d.substring(0, 10)));
                Date f = UtilData.fimDia(UtilData.converteEmDate(d.substring(13)));
                int quantidade = 0;
                try {
                    while (p.getId() == listCpProdutosSaidosNoPeriodo.get(pos).getProduto().getId()
                            && listCpProdutosSaidosNoPeriodo.get(pos).getSaida().getDataSaida().getTime() >= i.getTime()
                            && listCpProdutosSaidosNoPeriodo.get(pos).getSaida().getDataSaida().getTime() < f.getTime()) {

                        quantidade += listCpProdutosSaidosNoPeriodo.get(pos).getQuantidade();
                        pos++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                } finally {
                    ds.addValor(quantidade);
                }
            }
            listDataSet.add(ds);
        }
    }

    public void defineDataSetPredeterminadoEntrada(List<CPProduto> produtos) {
        listDataSet = new LinkedList<>();
        OrdenaEntradaProduto oep = new OrdenaEntradaProduto();
        Collections.sort(listCpProdutosEntradosNoPeriodo, oep);
        int pos = 0;
        for (CPProduto p : produtos) {
            GrafDataSet ds = new GrafDataSet(p.toString());
            for (String d : listDatas) {
                Date i = UtilData.inicioDia(UtilData.converteEmDate(d.substring(0, 10)));
                Date f = UtilData.fimDia(UtilData.converteEmDate(d.substring(0, 10)));
                int quantidade = 0;
                try {
                    while (p.getId() == listCpProdutosEntradosNoPeriodo.get(pos).getProduto().getId()
                            && listCpProdutosEntradosNoPeriodo.get(pos).getEntrada().getDataEntrada().getTime() >= i.getTime()
                            && listCpProdutosEntradosNoPeriodo.get(pos).getEntrada().getDataEntrada().getTime() < f.getTime()) {

                        quantidade += listCpProdutosEntradosNoPeriodo.get(pos).getQuantidade();
                        pos++;
                    }
                } catch (IndexOutOfBoundsException e) {
                } finally {
                    ds.addValor(quantidade);
                }
            }
            listDataSet.add(ds);
        }
    }

    public void criaCategoriasPredeterminadas(ABSGraficoSalvo gs) {
        listDatas = new LinkedList<>();
        Timestamp aux = gs.getDe();
        while (aux.getTime() < gs.getAte().getTime()) {
            String s = "";
            s = UtilData.diaNmesNanoN(aux);
            switch (gs.getAgrupamento()) {
                case "Mês":
                    // Mês
                    aux = UtilData.fimMesDataEHora(aux);
                    break;
                case "Bimestre":
                    //Bimestre
                    aux = UtilData.fimBimestre(aux);
                    break;
                case "Trimestre":
                    //Trimestre
                    aux = UtilData.fimTrimestre(aux);
                    break;
                case "Semestre":
                    //Semestre
                    aux = UtilData.fimSemestre(aux);
                    break;
                case "Ano":
                    //Ano
                    aux = UtilData.fimAno(aux);
                    break;
            }
            s = s.concat(" - " + UtilData.diaNmesNanoN(aux));
            aux = UtilData.addDia(aux);
            listDatas.add(s);
        }
    }

    private static void geraGrafIO(List<String> categorias, List<GrafDataSet> ds, String identificadorXML, String nomeGrafico) {
        Element chart = new Element("chart");
        chart.setAttribute("caption", nomeGrafico);
        chart.setAttribute("showValues", "0");
        chart.setAttribute("bgColor", "FFFFFF");
        chart.setAttribute("showBorder", "0");
        Element categories = new Element("categories");
        for (String c : categorias) {
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

    private static void escreveXML(Document d, String identificadorXML) {
        try {
            XMLOutputter out = new XMLOutputter();
            File f = new File("C:/Users/Thiago-Asus/Desktop/p/F3/web/estoque/grafico/xml/" + identificadorXML + ".xml");
            f.delete();
            java.io.FileWriter writer = new java.io.FileWriter(f);
            out.output(d, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("pau ao escrever xml!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.getMessage();
        }
    }

    public void reformulaCategorias(ABSGraficoSalvo gs) {
        switch (gs.getAgrupamento()) {
            case "Mês":
                // Mês
                reformulaCategorias();
                break;
            case "Bimestre":
                //Bimestre
                reformulaCategoriasBimestrais();
                break;
            case "Trimestre":
                //Trimestre
                reformulaCategoriasTrimestrais();
                break;
            case "Semestre":
                //Semestre
                reformulaCategoriasSemestrais();
                break;
            case "Ano":
                //Ano
                reformulaCategoriasAnuais();
                break;
        }
    }

    private void reformulaCategorias() {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            l.add(UtilData.mesE(UtilData.converteEmDate(s.substring(0, 10))) + "/" + UtilData.ano(UtilData.converteEmDate(s.substring(0, 10))));
        }
        listDatas = l;
    }

    private void reformulaCategoriasBimestrais() {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add((UtilData.mes(f) / 2) + "º Bimestre de " + UtilData.ano(f));
        }
        listDatas = l;
    }

    private void reformulaCategoriasTrimestrais() {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add((UtilData.mes(f) / 3) + "º Trimestre de " + UtilData.ano(f));
        }
        listDatas = l;
    }

    private void reformulaCategoriasSemestrais() {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add((UtilData.mes(f) / 6) + "º Semestre de " + UtilData.ano(f));
        }
        listDatas = l;
    }

    private void reformulaCategoriasAnuais() {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add(UtilData.ano(f) + "");
        }
        listDatas = l;
    }

    public List<GrafDataSet> dataSetDeDeterminadoPonto(CPPontoDeEstagio p, ABSGraficoSalvo gs, List<CPProduto> produtos) {
        List<String> novasDatas = datas(gs);
        List<GrafDataSet> res = new LinkedList<>();
        List<CPPontoDeEstagio> lp = new LinkedList<>();
        lp.add(p);
        OrdenaSaidaProduto osp = new OrdenaSaidaProduto();
        Collections.sort(listCpProdutosSaidosNoPeriodo, osp);
        int pos = 0;
        for (CPProduto pr : produtos) {
            GrafDataSet ds = new GrafDataSet(pr.toString());
            for (String d : novasDatas) {
                Timestamp i = UtilData.converteEmDate(d.substring(0, 10));
                Timestamp f = UtilData.converteEmDate(d.substring(13));
                i = UtilData.inicioDia(i);
                f = UtilData.fimDia(f);

                int quantidade = 0;
                try {
                    while (pr.getId() == listCpProdutosSaidosNoPeriodo.get(pos).getProduto().getId()
                            && listCpProdutosSaidosNoPeriodo.get(pos).getSaida().getDataSaida().getTime() >= i.getTime()
                            && listCpProdutosSaidosNoPeriodo.get(pos).getSaida().getDataSaida().getTime() < f.getTime()
                            && listCpProdutosSaidosNoPeriodo.get(pos).getSaida().getDestino().getId() == p.getId()) {

                        quantidade += listCpProdutosSaidosNoPeriodo.get(pos).getQuantidade();
                        pos++;
                    }
                } catch (IndexOutOfBoundsException e) {
                } finally {
                    ds.addValor(quantidade);
                }
            }
            res.add(ds);
        }
        
        return res;
    }

    private List<String> datas(ABSGraficoSalvo absgs) {
        List<String> novasDatas = new LinkedList<>();
        Timestamp aux = UtilData.getDataTimestamp();
        aux = absgs.getDe();
        while (aux.getTime() < absgs.getAte().getTime()) {
            String s = "";
            s = UtilData.diaNmesNanoN(aux);
            switch (absgs.getAgrupamento()) {
                case "Mês":
                    // MÃªs
                    aux = UtilData.fimMesDataEHora(aux);
                    break;
                case "Bimestre":
                    //Bimestre
                    aux = UtilData.fimBimestre(aux);
                    break;
                case "Trimestre":
                    //Trimestre
                    aux = UtilData.fimTrimestre(aux);
                    break;
                case "Semestre":
                    //Semestre
                    aux = UtilData.fimSemestre(aux);
                    break;
                case "Ano":
                    //Ano
                    aux = UtilData.fimAno(aux);
                    break;
            }
            s = s.concat(" - " + UtilData.diaNmesNanoN(aux));
            aux = UtilData.addDia(aux);
            novasDatas.add(s);
        }
        return novasDatas;
    }

    //get e set
    public List<GrafDataSet> getListDataSet() {
        return listDataSet;
    }

    public void setListDataSet(List<GrafDataSet> listDataSet) {
        this.listDataSet = listDataSet;
    }

    public List<String> getListDatas() {
        return listDatas;
    }

    public void setListDatas(List<String> listDatas) {
        this.listDatas = listDatas;
    }

    public List<CPEntradaProduto> getListCpProdutosEntradosNoPeriodo() {
        return listCpProdutosEntradosNoPeriodo;
    }

    public void setListCpProdutosEntradosNoPeriodo(List<CPEntradaProduto> listCpProdutosEntradosNoPeriodo) {
        this.listCpProdutosEntradosNoPeriodo = listCpProdutosEntradosNoPeriodo;
    }

    public List<CPSaidaProduto> getListCpProdutosSaidosNoPeriodo() {
        return listCpProdutosSaidosNoPeriodo;
    }

    public void setListCpProdutosSaidosNoPeriodo(List<CPSaidaProduto> listCpProdutosSaidosNoPeriodo) {
        this.listCpProdutosSaidosNoPeriodo = listCpProdutosSaidosNoPeriodo;
    }
}
