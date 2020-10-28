/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.data.UtilData;
import utilidades.graficos.IO.GrafDataSet;
import utilidades.mensagens.UtilMensagens;
import utilidades.seguranca.Url;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "geraPDF")
@ViewScoped
public class GeraPdfGraficoIO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String urlDownload;
    private String localArquivo = Url.localpc+"estoque/grafico/pdf/";
    Image cabecalho;

    public GeraPdfGraficoIO() {
        try {
            cabecalho = Image.getInstance(Url.localpcResources + "imagens/logopdf.png");
            cabecalho.scaleAbsoluteWidth(419);
            cabecalho.scaleAbsoluteHeight(44);
            cabecalho.setAlignment(Image.ALIGN_CENTER);
        } catch (BadElementException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void apagaArquivosAnteriores() {
        File dir = new File(localArquivo);
        File[] listFiles = dir.listFiles();
        System.out.println(listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isFile()) {
                listFiles[i].delete();
            }
        }
    }

    public void porcentagemConsumidaPorMultiplosrodutos(List<String> listDatasApresentavel,
            HashMap<String, String> mapaTotalPorData,
            HashMap<String, List<String>> mapaIO,
            List<CPProduto> produtos, List<CPPontoDeEstagio> pontos) {
        try {
            apagaArquivosAnteriores();
            Document document = new Document(PageSize.A4);
            String nomeArquivo = UtilString.geraCodigoSecreto() + "porcentagem-consumida-por-todos-os-produtos.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(localArquivo+ nomeArquivo));

            document.open();



            int numeroColunas = 3;
            PdfPTable conteudo = new PdfPTable(numeroColunas);
            Font fonteCabeca = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            Font fonteNormal = new Font(Font.FontFamily.TIMES_ROMAN, 7);

            for (CPProduto xProduto : produtos) {
                PdfPCell produto = new PdfPCell(new Phrase(xProduto.toString(), fonteCabeca));
                produto.setBackgroundColor(new BaseColor(204, 204, 204));
                produto.setBorderColor(new BaseColor(157, 157, 157));
                produto.setPadding(5);
                produto.setColspan(3);
                produto.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

                PdfPCell head1 = new PdfPCell(new Phrase("Data", fonteCabeca));
                head1.setBackgroundColor(new BaseColor(204, 204, 204));
                head1.setBorderColor(new BaseColor(157, 157, 157));
                head1.setPadding(5);

                PdfPCell head2 = new PdfPCell(new Phrase("Total Consumido", fonteCabeca));
                head2.setBackgroundColor(new BaseColor(204, 204, 204));
                head2.setBorderColor(new BaseColor(157, 157, 157));
                head2.setPadding(5);

                PdfPCell head3 = new PdfPCell(new Phrase("Consumo por ponto", fonteCabeca));
                head3.setBackgroundColor(new BaseColor(204, 204, 204));
                head3.setBorderColor(new BaseColor(157, 157, 157));
                head3.setPadding(5);

                conteudo.addCell(produto);
                conteudo.addCell(head1);
                conteudo.addCell(head2);
                conteudo.addCell(head3);


                BaseColor baseColor = new BaseColor(244, 254, 240);
                boolean cor = false;
                // data - produto | qtd consmida
                for (String xData : listDatasApresentavel) {

                    PdfPCell c = new PdfPCell(new Phrase(xData, fonteNormal));
                    c.setBorderColor(new BaseColor(157, 157, 157));
                    c.setBackgroundColor(baseColor);
                    c.setPadding(5);
                    c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    conteudo.addCell(c);

                    PdfPCell c2 = new PdfPCell(new Phrase(mapaTotalPorData.get(listDatasApresentavel.indexOf(xData) + "-" + produtos.indexOf(xProduto)), fonteNormal));
                    c2.setBorderColor(new BaseColor(157, 157, 157));
                    c2.setBackgroundColor(baseColor);
                    c2.setPadding(5);
                    c2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    conteudo.addCell(c2);


                    PdfPTable consumoPorPonto = new PdfPTable(3);
                    PdfPCell head11 = new PdfPCell(new Phrase("Ponto", fonteCabeca));
                    head11.setBackgroundColor(new BaseColor(204, 204, 204));
                    head11.setBorderColor(new BaseColor(157, 157, 157));
                    head11.setPadding(5);

                    PdfPCell head12 = new PdfPCell(new Phrase("Consumo", fonteCabeca));
                    head12.setBackgroundColor(new BaseColor(204, 204, 204));
                    head12.setBorderColor(new BaseColor(157, 157, 157));
                    head12.setPadding(5);

                    PdfPCell head13 = new PdfPCell(new Phrase("% Consumo", fonteCabeca));
                    head13.setBackgroundColor(new BaseColor(204, 204, 204));
                    head13.setBorderColor(new BaseColor(157, 157, 157));
                    head13.setPadding(5);

                    consumoPorPonto.addCell(head11);
                    consumoPorPonto.addCell(head12);
                    consumoPorPonto.addCell(head13);


                    BaseColor baseColor2 = new BaseColor(244, 254, 240);
                    boolean cor2 = false;
                    for (CPPontoDeEstagio xPonto : pontos) {

                        PdfPCell cPonto = new PdfPCell(new Phrase(xPonto.getNome(), fonteNormal));
                        cPonto.setBorderColor(new BaseColor(157, 157, 157));
                        cPonto.setBackgroundColor(baseColor2);
                        cPonto.setPadding(5);


                        String total = mapaTotalPorData.get(listDatasApresentavel.indexOf(xData) + "-" + produtos.indexOf(xProduto));
                        String porcentagem = mapaIO.get(listDatasApresentavel.indexOf(xData) + "-" + produtos.indexOf(xProduto)).get(pontos.indexOf(xPonto));
                        String consumo = "0";

                        if (!total.equals("0")) {
                            int t = Integer.parseInt(total);
                            double t2 = t / 100d;
                            Double d = new Double(porcentagem);
                            d = d * t2;
                            consumo = new DecimalFormat("0").format((d));
                        }


                        PdfPCell c2Ponto = new PdfPCell(new Phrase(consumo, fonteNormal));
                        c2Ponto.setBorderColor(new BaseColor(157, 157, 157));
                        c2Ponto.setBackgroundColor(baseColor2);
                        c2Ponto.setPadding(5);
                        if (!consumo.equals("0")) {
                            consumo = mapaIO.get(listDatasApresentavel.indexOf(xData) + "-" + produtos.indexOf(xProduto)).get(pontos.indexOf(xPonto));
                        }

                        PdfPCell c3Ponto = new PdfPCell(new Phrase(consumo + "%", fonteNormal));
                        c3Ponto.setBorderColor(new BaseColor(157, 157, 157));
                        c3Ponto.setBackgroundColor(baseColor2);
                        c3Ponto.setPadding(5);


                        consumoPorPonto.addCell(cPonto);
                        consumoPorPonto.addCell(c2Ponto);
                        consumoPorPonto.addCell(c3Ponto);
                        if (cor2) {
                            baseColor2 = new BaseColor(244, 254, 240);
                            cor2 = false;
                        } else {
                            baseColor2 = new BaseColor(241, 242, 241);
                            cor2 = true;
                        }
                    }


                    conteudo.addCell(consumoPorPonto);
                    if (cor) {
                        baseColor = new BaseColor(244, 254, 240);
                        cor = false;
                    } else {
                        baseColor = new BaseColor(241, 242, 241);
                        cor = true;
                    }
                }
                PdfPCell head1Vazio = new PdfPCell();
                head1Vazio.setBorder(0);
                head1Vazio.setPadding(10);
                conteudo.addCell(head1Vazio);
                conteudo.addCell(head1Vazio);
                conteudo.addCell(head1Vazio);
            }



            Paragraph titulo = new Paragraph(new Phrase("Porcentagem consumida dos determinados produtos", fonteNormal));
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);

            conteudo.setSpacingAfter(10);
            document.add(conteudo);


            Paragraph data = new Paragraph(new Phrase(UtilData.dataFormal2(UtilData.getDataTimestamp().getTime()) + " - " + UtilData.horaMinuto(UtilData.getDataTimestamp()), fonteNormal));
            data.setAlignment(Paragraph.ALIGN_CENTER);
            data.setSpacingAfter(10);
            document.add(data);

            document.close();

            urlDownload = localArquivo + nomeArquivo;
        } catch (Exception e) {
        }
    }

    public void porcentagemConsumidaPorUmProduto(List<String> listDatasApresentavel,
            HashMap<String, String> mapaTotalPorData, HashMap<String, List<String>> mapaIO, int indexDoProduto, String nomeProduto, List<CPPontoDeEstagio> pontos) {
        try {
            apagaArquivosAnteriores();
            Document document = new Document(PageSize.A4);
            String nomeArquivo = UtilString.geraCodigoSecreto() + nomeProduto.replace(" ", "-").replace(".", "") + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(localArquivo + nomeArquivo));

            document.open();

            int numeroColunas = 3;
            PdfPTable conteudo = new PdfPTable(numeroColunas);
            Font fonteCabeca = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            Font fonteNormal = new Font(Font.FontFamily.TIMES_ROMAN, 7);

            PdfPCell produto = new PdfPCell(new Phrase(nomeProduto, fonteCabeca));
            produto.setBackgroundColor(new BaseColor(204, 204, 204));
            produto.setBorderColor(new BaseColor(157, 157, 157));
            produto.setPadding(5);
            produto.setColspan(3);
            produto.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            PdfPCell head1 = new PdfPCell(new Phrase("Data", fonteCabeca));
            head1.setBackgroundColor(new BaseColor(204, 204, 204));
            head1.setBorderColor(new BaseColor(157, 157, 157));
            head1.setPadding(5);

            PdfPCell head2 = new PdfPCell(new Phrase("Total Consumido", fonteCabeca));
            head2.setBackgroundColor(new BaseColor(204, 204, 204));
            head2.setBorderColor(new BaseColor(157, 157, 157));
            head2.setPadding(5);

            PdfPCell head3 = new PdfPCell(new Phrase("Consumo por ponto", fonteCabeca));
            head3.setBackgroundColor(new BaseColor(204, 204, 204));
            head3.setBorderColor(new BaseColor(157, 157, 157));
            head3.setPadding(5);

            conteudo.addCell(produto);
            conteudo.addCell(head1);
            conteudo.addCell(head2);
            conteudo.addCell(head3);

            BaseColor baseColor = new BaseColor(244, 254, 240);
            boolean cor = false;
            // data - produto | qtd consmida
            for (String xData : listDatasApresentavel) {

                PdfPCell c = new PdfPCell(new Phrase(xData, fonteNormal));
                c.setBorderColor(new BaseColor(157, 157, 157));
                c.setBackgroundColor(baseColor);
                c.setPadding(5);
                c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                conteudo.addCell(c);

                PdfPCell c2 = new PdfPCell(new Phrase(mapaTotalPorData.get(listDatasApresentavel.indexOf(xData) + "-" + indexDoProduto), fonteNormal));
                c2.setBorderColor(new BaseColor(157, 157, 157));
                c2.setBackgroundColor(baseColor);
                c2.setPadding(5);
                c2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                conteudo.addCell(c2);


                PdfPTable consumoPorPonto = new PdfPTable(3);
                PdfPCell head11 = new PdfPCell(new Phrase("Ponto", fonteCabeca));
                head11.setBackgroundColor(new BaseColor(204, 204, 204));
                head11.setBorderColor(new BaseColor(157, 157, 157));
                head11.setPadding(5);

                PdfPCell head12 = new PdfPCell(new Phrase("Consumo", fonteCabeca));
                head12.setBackgroundColor(new BaseColor(204, 204, 204));
                head12.setBorderColor(new BaseColor(157, 157, 157));
                head12.setPadding(5);

                PdfPCell head13 = new PdfPCell(new Phrase("% Consumo", fonteCabeca));
                head13.setBackgroundColor(new BaseColor(204, 204, 204));
                head13.setBorderColor(new BaseColor(157, 157, 157));
                head13.setPadding(5);

                consumoPorPonto.addCell(head11);
                consumoPorPonto.addCell(head12);
                consumoPorPonto.addCell(head13);


                BaseColor baseColor2 = new BaseColor(244, 254, 240);
                boolean cor2 = false;
                for (CPPontoDeEstagio xPonto : pontos) {

                    PdfPCell cPonto = new PdfPCell(new Phrase(xPonto.getNome(), fonteNormal));
                    cPonto.setBorderColor(new BaseColor(157, 157, 157));
                    cPonto.setBackgroundColor(baseColor2);
                    cPonto.setPadding(5);


                    String total = mapaTotalPorData.get(listDatasApresentavel.indexOf(xData) + "-" + indexDoProduto);
                    String porcentagem = mapaIO.get(listDatasApresentavel.indexOf(xData) + "-" + indexDoProduto).get(pontos.indexOf(xPonto));
                    String consumo = "0";

                    if (!total.equals("0")) {
                        int t = Integer.parseInt(total);
                        double t2 = t / 100d;
                        Double d = new Double(porcentagem);
                        d = d * t2;
                        consumo = new DecimalFormat("0").format((d));
                    }


                    PdfPCell c2Ponto = new PdfPCell(new Phrase(consumo, fonteNormal));
                    c2Ponto.setBorderColor(new BaseColor(157, 157, 157));
                    c2Ponto.setBackgroundColor(baseColor2);
                    c2Ponto.setPadding(5);
                    if (!consumo.equals("0")) {
                        consumo = mapaIO.get(listDatasApresentavel.indexOf(xData) + "-" + indexDoProduto).get(pontos.indexOf(xPonto));
                    }

                    PdfPCell c3Ponto = new PdfPCell(new Phrase(consumo + "%", fonteNormal));
                    c3Ponto.setBorderColor(new BaseColor(157, 157, 157));
                    c3Ponto.setBackgroundColor(baseColor2);
                    c3Ponto.setPadding(5);


                    consumoPorPonto.addCell(cPonto);
                    consumoPorPonto.addCell(c2Ponto);
                    consumoPorPonto.addCell(c3Ponto);
                    if (cor2) {
                        baseColor2 = new BaseColor(244, 254, 240);
                        cor2 = false;
                    } else {
                        baseColor2 = new BaseColor(241, 242, 241);
                        cor2 = true;
                    }
                }


                conteudo.addCell(consumoPorPonto);
                if (cor) {
                    baseColor = new BaseColor(244, 254, 240);
                    cor = false;
                } else {
                    baseColor = new BaseColor(241, 242, 241);
                    cor = true;
                }
            }
            cabecalho.setSpacingAfter(10);
            document.add(cabecalho);


            Paragraph titulo = new Paragraph(new Phrase("Porcentagem consumida do produo: " + nomeProduto, fonteCabeca));
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);

            conteudo.setSpacingAfter(10);
            document.add(conteudo);

            Paragraph data = new Paragraph(new Phrase(UtilData.dataFormal2(UtilData.getDataTimestamp().getTime()) + " - " + UtilData.horaMinuto(UtilData.getDataTimestamp()), fonteNormal));
            data.setAlignment(Paragraph.ALIGN_CENTER);
            data.setSpacingAfter(10);
            document.add(data);

            document.close();

            urlDownload = localArquivo + nomeArquivo;
        } catch (Exception e) {
        }

    }

    public void variacaoNoEstoqueDeUmUnicoProduto(GrafDataSet listDataSetDiferencaIOVariacao, List<String> listDatasApresentavel) {
        procedimentoPadraoUnicoDataSet(listDataSetDiferencaIOVariacao, listDatasApresentavel,
                UtilString.geraCodigoSecreto() + "variação-de-estoque-do-produto-"
                + listDataSetDiferencaIOVariacao.getNome().replace(" ", "-").replace(".", "") + ".pdf", "Variação da quantidade em estoque do produto: " + listDataSetDiferencaIOVariacao.getNome());
    }

    public void variacaoNoEstoqueDeTodosOsProdutos(List<GrafDataSet> listDataSetDiferencaIOVariacao, List<String> listDatasApresentavel) {
        procedimentoPadraoMuiltoplosDataSet(listDataSetDiferencaIOVariacao, listDatasApresentavel, UtilString.geraCodigoSecreto() + "variação-de-estoque-de-todos-os-produto.pdf", "Variação de estoque dos seguintes produtos");
    }

    public void diferencaEntradaSaidaDeTodosOsProdutos(List<GrafDataSet> listDataSetDiferencaIOVariacao, List<String> listDatasApresentavel) {
        String nomeArquivo = UtilString.geraCodigoSecreto() + "saldo-de-todos-os-produtos.pdf";
        procedimentoPadraoMuiltoplosDataSet(listDataSetDiferencaIOVariacao, listDatasApresentavel, nomeArquivo, "Diferenca de entrada e saída de produtos");
    }

    public void diferencaEntradaSaidaDeUmUnicoProduto(GrafDataSet listDataSetDiferencaIOVariacao, List<String> listDatasApresentavel) {
        String nomeArquivo = UtilString.geraCodigoSecreto() + "saldo-de-" + listDataSetDiferencaIOVariacao.getNome().replace(" ", "-").replace(".", "") + ".pdf";
        procedimentoPadraoUnicoDataSet(listDataSetDiferencaIOVariacao, listDatasApresentavel, nomeArquivo, "Diferença de entrada e saida do produto: " + listDataSetDiferencaIOVariacao.getNome());
    }

    // # PROCEDIMENTOS PADRÃO
    private void procedimentoPadraoUnicoDataSet(GrafDataSet listDataSetDiferencaIOVariacao, List<String> listDatasApresentavel, String nomeArquivo, String tituloDocumento) {
        try {
            apagaArquivosAnteriores();
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(localArquivo + nomeArquivo));


            Font cabeca = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            Font linhas = new Font(Font.FontFamily.TIMES_ROMAN, 8);

            document.open();
            Image cabecalho = Image.getInstance("C:/Arquivos de programas/Apache Software Foundation/Tomcat 7.0/webapps/f3/web/resources/imagens/logopdf.png");
            cabecalho.scaleAbsoluteWidth(419);
            cabecalho.scaleAbsoluteHeight(44);
            cabecalho.setAlignment(Image.ALIGN_CENTER);

            int numeroColunas = 2;
            PdfPTable conteudo = new PdfPTable(numeroColunas);

            PdfPCell produto = new PdfPCell(new Phrase(listDataSetDiferencaIOVariacao.getNome(), cabeca));
            produto.setBackgroundColor(new BaseColor(204, 204, 204));
            produto.setBorderColor(new BaseColor(157, 157, 157));
            produto.setPadding(5);
            produto.setColspan(2);
            produto.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            PdfPCell head1 = new PdfPCell(new Phrase("Periodo", cabeca));
            head1.setBackgroundColor(new BaseColor(204, 204, 204));
            head1.setBorderColor(new BaseColor(157, 157, 157));
            head1.setPadding(5);

            PdfPCell head2 = new PdfPCell(new Phrase("Quantidade", cabeca));
            head2.setBackgroundColor(new BaseColor(204, 204, 204));
            head2.setBorderColor(new BaseColor(157, 157, 157));
            head2.setPadding(5);






            conteudo.addCell(produto);
            conteudo.addCell(head1);
            conteudo.addCell(head2);
            int cont = 0;
            BaseColor baseColor = new BaseColor(244, 254, 240);
            boolean cor = false;
            for (String xValor : listDataSetDiferencaIOVariacao.getValores()) {
                PdfPCell c = new PdfPCell(new Phrase(xValor, linhas));
                c.setBorderColor(new BaseColor(157, 157, 157));
                c.setBackgroundColor(baseColor);
                c.setPadding(5);


                PdfPCell c2 = new PdfPCell(new Phrase(listDatasApresentavel.get(cont), linhas));
                c2.setBorderColor(new BaseColor(157, 157, 157));
                c2.setBackgroundColor(baseColor);
                c2.setBorderColor(new BaseColor(157, 157, 157));
                c2.setPadding(5);
                conteudo.addCell(c2);
                conteudo.addCell(c);

                if (cor) {
                    baseColor = new BaseColor(244, 254, 240);
                    cor = false;
                } else {
                    baseColor = new BaseColor(241, 242, 241);
                    cor = true;
                }

                cont++;
            }

            Paragraph titulo = new Paragraph(new Phrase(tituloDocumento, cabeca));
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);

            conteudo.setSpacingAfter(10);
            document.add(conteudo);

            Paragraph data = new Paragraph(new Phrase(UtilData.dataFormal2(UtilData.getDataTimestamp().getTime()) + " - " + UtilData.horaMinuto(UtilData.getDataTimestamp()), linhas));
            data.setAlignment(Paragraph.ALIGN_CENTER);
            data.setSpacingAfter(10);
            document.add(data);

            document.close();

            urlDownload = localArquivo + nomeArquivo;
        } catch (Exception ex) {
            Logger.getLogger(GeraPdfGraficoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void procedimentoPadraoMuiltoplosDataSet(List<GrafDataSet> listDataSetDiferencaIOVariacao, List<String> listDatasApresentavel, String nomeArquivo, String tituloDocumento) {
        try {
            apagaArquivosAnteriores();


            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(localArquivo + nomeArquivo));

            document.open();

            Font cabeca = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            Font linhas = new Font(Font.FontFamily.TIMES_ROMAN, 8);

            int numeroColunas = 2;
            PdfPTable conteudo = new PdfPTable(numeroColunas);
            for (GrafDataSet xDataSet : listDataSetDiferencaIOVariacao) {
                PdfPCell produto = new PdfPCell(new Phrase(xDataSet.getNome(), cabeca));
                produto.setBackgroundColor(new BaseColor(204, 204, 204));
                produto.setBorderColor(new BaseColor(157, 157, 157));
                produto.setPadding(5);
                produto.setColspan(2);
                produto.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

                PdfPCell head1 = new PdfPCell(new Phrase("Periodo", cabeca));
                head1.setBackgroundColor(new BaseColor(204, 204, 204));
                head1.setBorderColor(new BaseColor(157, 157, 157));
                head1.setPadding(5);

                PdfPCell head2 = new PdfPCell(new Phrase("Quantidade", cabeca));
                head2.setBackgroundColor(new BaseColor(204, 204, 204));
                head2.setBorderColor(new BaseColor(157, 157, 157));
                head2.setPadding(5);

                conteudo.addCell(produto);
                conteudo.addCell(head1);
                conteudo.addCell(head2);

                int cont = 0;
                BaseColor baseColor = new BaseColor(244, 254, 240);
                boolean cor = false;
                for (String xValor : xDataSet.getValores()) {
                    PdfPCell c = new PdfPCell(new Phrase(xValor, linhas));
                    c.setBorderColor(new BaseColor(157, 157, 157));
                    c.setBackgroundColor(baseColor);
                    c.setPadding(5);


                    PdfPCell c2 = new PdfPCell(new Phrase(listDatasApresentavel.get(cont), linhas));
                    c2.setBorderColor(new BaseColor(157, 157, 157));
                    c2.setBackgroundColor(baseColor);
                    c2.setBorderColor(new BaseColor(157, 157, 157));
                    c2.setPadding(5);
                    conteudo.addCell(c2);
                    conteudo.addCell(c);

                    if (cor) {
                        baseColor = new BaseColor(244, 254, 240);
                        cor = false;
                    } else {
                        baseColor = new BaseColor(241, 242, 241);
                        cor = true;
                    }

                    cont++;
                }
                PdfPCell head1Vazio = new PdfPCell();
                head1Vazio.setBorder(0);
                head1Vazio.setPadding(10);
                conteudo.addCell(head1Vazio);
                conteudo.addCell(head1Vazio);
            }

            cabecalho.setSpacingAfter(10);
            document.add(cabecalho);

            Paragraph titulo = new Paragraph(new Phrase(tituloDocumento, cabeca));
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);

            document.add(conteudo);
            conteudo.setSpacingAfter(10);


            Paragraph data = new Paragraph(new Phrase(UtilData.dataFormal2(UtilData.getDataTimestamp().getTime()) + " - " + UtilData.horaMinuto(UtilData.getDataTimestamp()), linhas));
            data.setAlignment(Paragraph.ALIGN_CENTER);
            data.setSpacingAfter(10);
            document.add(data);

            document.close();

            urlDownload = localArquivo + nomeArquivo;
        } catch (Exception ex) {
            Logger.getLogger(GeraPdfGraficoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // # FIM PROCEDIMENTOS PADRÃO

    public String getUrlDownload() {
        return urlDownload;
    }
}
