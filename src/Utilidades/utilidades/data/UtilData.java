/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.data;

import cp.grafico.ABSGraficoSalvo;
import cp.grafico.InterfaceGraficoCustom;
import cp.grafico.InterfaceGraficoPreDet;
import cp.grafico.entrada.ABSGraficoEntradaSalvo;
import cp.grafico.entrada.custom.CPGraficoEntradaCustom;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminado;
import cp.grafico.saida.custom.CPGraficoSaidaCustom;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminado;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import utilidades.mensagens.UtilMensagens;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "utilData")
public class UtilData implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String horaInicialClinica = "6" ;
    private static final String horaFinalClinica = "18" ;

    public String getHoraFinalClinica() {
        return horaFinalClinica;
    }

    public String getHoraInicialClinica() {
        return horaInicialClinica;
    }
    
    public static SimpleDateFormat forma = new SimpleDateFormat();

    public static Timestamp getDataTimestamp() {
        return new Timestamp((DateTime.now().getMillis()));
    }

    public Date hoje() {
        return new DateTime(fimDia(DateTime.now().getMillis()).getTime()).toDate();
    }

    public static ABSGraficoSalvo acertaData(ABSGraficoSalvo g) {
        if (g instanceof InterfaceGraficoCustom) {
            g = acertaDataCustom(g);
        } else if (g instanceof InterfaceGraficoPreDet) {
            g = acertaDataPreDeterminado(g);
        }
        return g;
    }

    public static String validaDataGrafico(ABSGraficoSalvo salvo) {
        if (salvo instanceof InterfaceGraficoCustom) {
            if (salvo.getDe() == null || salvo.getAte() == null) { // uma das datas nulas
                return UtilMensagens.dataVazia;
            } else { // se ambas datas possuem valor
                //ver se inicio é maior que fim
                if (salvo.getAte().getTime() < salvo.getDe().getTime()) {
                    return UtilMensagens.dataInicioMaiorQueFim;
                } else {
                    //diferenca em dias
                    int dias = Integer.parseInt(salvo.getAgrupamento().substring(0, salvo.getAgrupamento().indexOf(" ")));

                    if (!UtilData.validaPorDias(salvo.getDe().getTime(), salvo.getAte().getTime(), dias)) {
                        return UtilMensagens.dataDiasInvalida(dias);
                    } else {
                        return "";
                    }
                }
            }
        } else if (salvo instanceof InterfaceGraficoPreDet) {
            if (salvo.getDe() == null || salvo.getAte() == null) { // uma das datas nulas
                return UtilMensagens.dataVazia;
            } else {
                if (salvo.getDe().getTime() > salvo.getAte().getTime()) {// inicio maior que fim
                    return UtilMensagens.dataInicioMaiorQueFim;
                } else {
                    switch (salvo.getAgrupamento()) {
                        case "Mês":// mensal
                            if (!UtilData.validaPorMes(salvo.getDe().getTime(), salvo.getAte().getTime())) {
                                return UtilMensagens.dataMesInvalida;
                            } else {
                                return "";
                            }
                        case "Bimestre":// bimestral
                            if (!UtilData.validaPorBimestre(salvo.getDe().getTime(), salvo.getAte().getTime())) {
                                return UtilMensagens.dataBimestreInvalida;
                            } else {
                                return "";
                            }
                        case "Trimestre":// trimestral
                            if (!UtilData.validaPorTrimestre(salvo.getDe().getTime(), salvo.getAte().getTime())) {
                                return UtilMensagens.dataTrimestreInvalida;
                            } else {
                                return "";
                            }
                        case "Semestre":// semestre
                            if (!UtilData.validaPorSemestre(salvo.getDe().getTime(), salvo.getAte().getTime())) {
                                return UtilMensagens.dataSemestreInvalida;
                            } else {
                                return "";
                            }
                        case "Ano":// anual
                            if (!UtilData.validaPorAno(salvo.getDe().getTime(), salvo.getAte().getTime())) {
                                return UtilMensagens.dataAnoInvalida;
                            } else {
                                return "";
                            }
                    }
                }
            }
        }
        return "";
    }

    public static List<String> criaIntervaloDeDatasPreDeterminadasAndCustom(ABSGraficoSalvo gs) {
        List<String> listDatas = new LinkedList<>();
        if (gs instanceof InterfaceGraficoPreDet) {
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
        } else if (gs instanceof InterfaceGraficoCustom) {
            listDatas = criaCategoriasCustom(gs);
        }
        return listDatas;
    }

    public static String dataFormatadaParaExibicaoSegundoCategoriaPreDeterminadaLong(Long d, String formato) {
        return dataFormatadaParaExibicaoSegundoCategoriaPreDeterminada(new Timestamp(d), formato);
    }

    public static String dataFormatadaParaExibicaoSegundoCategoriaPreDeterminada(Timestamp d, String formato) {
        switch (formato) {
            case "Mês":
                return UtilString.abreviaMes(mesE(d)) + " de " + ano(d);
            case "Bimestre":
                return (mes(fimBimestre(d)) / 2) + "º Bim de " + ano(d);
            case "Trimestre":
                return (mes(fimTrimestre(d)) / 3) + "º Tri de " + ano(d);
            case "Semestre":
                return (mes(fimSemestre(d)) / 6) + "º Sem de " + ano(d);
            case "Ano":
                return ano(d) + "";
        }
        return diaNmesNanoN(d);
    }

    public String dataFormatadaParaExibicaoSegundoCategoriaPreDeterminadaDe(long data, String agrupamento) {
        try {
            return dataFormatadaParaExibicaoSegundoCategoriaPreDeterminadaLong(data, agrupamento);
        } catch (Exception e) {
            return "indefinido";
        }

    }

    public String dataFormatadaParaExibicaoSegundoCategoriaPreDeterminadaAte(long data, String agrupamento) {
        try {
            return dataFormatadaParaExibicaoSegundoCategoriaPreDeterminadaLong(data, agrupamento);
        } catch (Exception e) {
            return "indefinido";
        }


    }

    @Deprecated
    public static boolean igualdadeDMA(Timestamp d1, Timestamp d2) {
        if (diaNmesNanoN(d2).equals(diaNmesNanoN(d1))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean igualdadeDMA(Long d1, Long d2) {
        return igualdadeDMA(new Timestamp(d1), new Timestamp(d2));
    }

    public static Timestamp fimDataEHoraProximoMes(Timestamp d) {
        d = addMes(d);
        d = fimMesDataEHora(d);
        return d;
    }

    public static Timestamp fimDataEHoraProximoMes(Long d) {
        return fimDataEHoraProximoMes(new Timestamp(d));
    }

    public static Timestamp addMes(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.plusMonths(1);
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp addMes(Long d) {
        return addMes(new Timestamp(d));
    }

    public static Timestamp addBimestre(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.plusMonths(2);
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp addBimestre(Long d) {
        return addBimestre(new Timestamp(d));
    }

    public static String diaNmesN(Timestamp d) {
        String formato = "dd/MM";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String diaNmesN(Long d) {
        return diaNmesN(new Timestamp(d));
    }

    public static String diaNmesNanoN(Timestamp d) {
        String formato = "dd/MM/yyyy";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String diaNmesNanoNT(Timestamp d) {
        return diaNmesNanoN(new Timestamp(d.getTime()));
    }

    public static String diaNmesNanoNL(Long d) {
        return diaNmesNanoN(new Timestamp(d));
    }

    public static String mesNanoN(Timestamp d) {
        String formato = "MM/yyyy";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String mesNanoN(Long d) {
        return mesNanoN(new Timestamp(d));
    }

    public static String diaNmesNanoNHM(Timestamp d) {
        String formato = "dd/MM/yyyy H:m";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String diaNmesNanoNHM(Long d) {
        return diaNmesNanoNHM(new Timestamp(d));
    }

    public static String mesEanoN(Timestamp d) {
        return mes(d) + "/" + ano(d);
    }

    public static String mesEanoN(Long d) {
        return mesEanoN(new Timestamp(d));
    }

    public static String diaNmesNanoNHMS(Timestamp d) {
        String formato = "dd/MM/yyyy H:m:s";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String diaNmesNanoNHMS(Long d) {
        return diaNmesNanoNHMS(new Timestamp(d));
    }

    public static String diaNmesNanoNHMSMS(Timestamp d) {
        String formato = "dd/MM/yyyy H:m:s:S";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }
    public static String horaMinutoSegundo(Timestamp d) {
        String formato = "H:m:s";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }
    public static String horaMinuto(Timestamp d) {
        String formato = "H:m:s";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String diaNmesNanoNHMSMS(Long d) {
        return diaNmesNanoNHMSMS(new Timestamp(d));
    }

    @Deprecated
    public static String aaaaMMddHHmmssParaConsulta(Timestamp d, boolean full) {
        //2011-07-18 17:28:51
        String formato = "yyyy-MM-dd";
        forma = new SimpleDateFormat(formato);
        if (full) {
            return forma.format(d) + " 23:59:59";
        } else {
            return forma.format(d) + " 00:00:00";
        }
    }

    @Deprecated
    public static String aaaaMMddHHmmssParaConsulta(Long d, boolean full) {
        return aaaaMMddHHmmssParaConsulta(new Timestamp(d), full);
    }

    public static String dataFormal(Timestamp d) {
        String formato = "EEEE',' d 'de' MMMM 'de' yyyy";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    public static String dataFormal2(Long d) {
        return dataFormal(new Timestamp(d));
    }

    public static String AnoNMesNDiaN(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "yyyy-MM-dd";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }
    
    @Deprecated
    public static String aaaaMMddHHmmssParaConsulta(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "yyyy-MM-dd";
        forma = new SimpleDateFormat(formato);
        return forma.format(d);
    }

    @Deprecated
    public static String aaaaMMddHHmmssParaConsulta(Long d) {
        return aaaaMMddHHmmssParaConsulta(new Timestamp(d));
    }

    public static Timestamp fimDia(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.withHourOfDay(23).
                withMinuteOfHour(59).
                withSecondOfMinute(59).
                withMillisOfSecond(999);
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp fimDia(Long d) {
        return fimDia(new Timestamp(d));
    }

    public static Timestamp inicioDia(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());

        try {
            dt = dt.withHourOfDay(0).
                    withMinuteOfHour(0).
                    withSecondOfMinute(0).
                    withMillisOfSecond(0);
        } catch (Exception e) {
            dt = dt.withHourOfDay(1).
                    withMinuteOfHour(0).
                    withSecondOfMinute(0).
                    withMillisOfSecond(0);
        }


        return new Timestamp(dt.getMillis());
    }

    public static Timestamp inicioDia(Long d) {
        return inicioDia(new Timestamp(d));
    }

    public static Timestamp fimAno(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.withMonthOfYear(12);
        dt = dt.withDayOfMonth(31);
        return fimDia(new Timestamp(dt.getMillis()));
    }

    public static Timestamp fimAno(Long d) {
        return fimAno(new Timestamp(d));
    }

    public static Timestamp inicioAno(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.withMonthOfYear(1);
        dt = dt.withDayOfMonth(1);
        return inicioDia(dt.getMillis());
    }

    public static Timestamp inicioAno(Long d) {
        return inicioAno(new Timestamp(d));
    }

    public static Timestamp inicioSemestre(Timestamp d) {
        DateTime dt = new DateTime(d);
        if (mes(d) > 6) {
            dt = dt.withMonthOfYear(6);
        } else {
            dt = dt.withMonthOfYear(1);
        }
        return inicioMesDiaEHora(dt.getMillis());
    }

    public static Timestamp inicioSemestre(Long d) {
        return inicioSemestre(new Timestamp(d));
    }

    public static Timestamp fimSemestre(Timestamp d) {
        DateTime dt = new DateTime(d);
        if (mes(d) > 6) {
            dt = dt.withMonthOfYear(12);
        } else {
            dt = dt.withMonthOfYear(6);
        }
        return fimMesDataEHora(dt.getMillis());
    }

    public static Timestamp fimSemestre(Long d) {
        return fimSemestre(new Timestamp(d));
    }

    public static Timestamp inicioMesDiaEHora(Timestamp d) {
        d = inicioDia(d);
        d = inicioMes(d);
        return d;
    }

    public static Timestamp inicioMesDiaEHora(Long d) {
        return inicioMesDiaEHora(new Timestamp(d));
    }

    public static Timestamp fimMesDataEHora(Timestamp d) {
        d = fimMes(d);
        d = fimDia(d);
        return d;
    }

    public static Timestamp fimMesDataEHora(Long d) {
        return fimMesDataEHora(new Timestamp(d));
    }

    public static int dia(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "dd";
        forma = new SimpleDateFormat(formato);
        return Integer.parseInt(forma.format(d));
    }

    public static int dia(Long d) {
        return dia(new Timestamp(d));
    }

    public static int mes(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "MM";
        forma = new SimpleDateFormat(formato);
        return Integer.parseInt(forma.format(d));
    }

    public static int mes(Long d) {
        return mes(new Timestamp(d));
    }

    public static String mesE(Timestamp d) {
        String mes = "";
        switch (mes(d)) {
            case 1:
                mes = "Janeiro";
                break;
            case 2:
                mes = "Fevereiro";
                break;
            case 3:
                mes = "Março";
                break;
            case 4:
                mes = "Abril";
                break;
            case 5:
                mes = "Maio";
                break;
            case 6:
                mes = "Junho";
                break;
            case 7:
                mes = "Julho";
                break;
            case 8:
                mes = "Agosto";
                break;
            case 9:
                mes = "Setembro";
                break;
            case 10:
                mes = "Outubro";
                break;
            case 11:
                mes = "Novembro";
                break;
            case 12:
                mes = "Dezembro";
                break;
        }
        return mes;
    }

    public static String mesE(Long d) {
        return mesE(new Timestamp(d));
    }

    public static String mesEAbre(Timestamp d) {
        String mes = "";
        switch (mes(d)) {
            case 1:
                mes = "Jan";
                break;
            case 2:
                mes = "Fev";
                break;
            case 3:
                mes = "Mar";
                break;
            case 4:
                mes = "Abr";
                break;
            case 5:
                mes = "Mai";
                break;
            case 6:
                mes = "Jun";
                break;
            case 7:
                mes = "Jul";
                break;
            case 8:
                mes = "Ago";
                break;
            case 9:
                mes = "Set";
                break;
            case 10:
                mes = "Out";
                break;
            case 11:
                mes = "Nov";
                break;
            case 12:
                mes = "Dez";
                break;
        }
        return mes;
    }

    public static String mesEAbre(Long d) {
        return mesEAbre(new Timestamp(d));
    }

    public static int ano(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "yyyy";
        forma = new SimpleDateFormat(formato);
        return Integer.parseInt(forma.format(d));
    }

    public static int ano(Long d) {
        return ano(new Timestamp(d));
    }

    public static int hora(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "H";
        forma = new SimpleDateFormat(formato);
        return Integer.parseInt(forma.format(d));
    }

    
    public static int hora(Long d) {
        return hora(new Timestamp(d));
    }

    public static int minuto(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "m";
        forma = new SimpleDateFormat(formato);
        return Integer.parseInt(forma.format(d));
    }

    public static int minuto(Long d) {
        return minuto(new Timestamp(d));
    }

    public static int segundo(Timestamp d) {
        //2011-07-18 17:28:51
        String formato = "s";
        forma = new SimpleDateFormat(formato);
        return Integer.parseInt(forma.format(d));
    }

    public static int segundo(Long d) {
        return segundo(new Timestamp(d));
    }

    public static Timestamp converteEmDate(String d) {
        if (d.length() < 8) {// 00/0000
            int mes = Integer.parseInt(d.substring(0, d.indexOf("/")));
            d = d.substring(d.indexOf("/") + 1);
            int ano = Integer.parseInt(d);
            DateTime dt = new DateTime(ano, mes, 1, 0, 0);
            return new Timestamp(dt.getMillis());
        } else {// 00/00/0000
            int dia = Integer.parseInt(d.substring(0, d.indexOf("/")));
            d = d.substring(d.indexOf("/") + 1);
            int mes = Integer.parseInt(d.substring(0, d.indexOf("/")));
            d = d.substring(d.indexOf("/") + 1);
            int ano = Integer.parseInt(d);
            DateTime dt = new DateTime();
            dt = dt.withYear(ano);
            dt = dt.withMonthOfYear(mes);
            dt = dt.withDayOfMonth(dia);
            return new Timestamp(dt.getMillis());
        }
    }

    public static int diferencaEmDias(Timestamp inicial, Timestamp fim) {
        return (Days.daysBetween(new DateTime(inicial.getTime()), new DateTime(fim.getTime())).getDays());
    }

    public static int diferencaEmDias(Long d, Long d2) {
        return diferencaEmDias(new Timestamp(d), new Timestamp(d2));
    }

    public static Timestamp inicioMes(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.withDayOfMonth(1);
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp inicioMes(Long d) {
        return inicioMes(new Timestamp(d));
    }

    public static Timestamp fimMes(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.plusMonths(1).withDayOfMonth(1).plusDays(-1);
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp fimMes(Long d) {
        return fimMes(new Timestamp(d));
    }

    public static int diferencaEmMeses(Timestamp ini, Timestamp fim) {
        return Months.monthsBetween(new DateTime(ini.getTime()), new DateTime(fim.getTime())).getMonths();
    }

    public static int diferencaEmMeses(Long ini, Long fim) {
        return diferencaEmMeses(new Timestamp(ini), new Timestamp(fim));
    }

    public static Timestamp addDia(Timestamp d) {
        DateTime dt = new DateTime(d.getTime());
        dt = dt.plusDays(1);
        return new Timestamp(dt.getMillis());
    }

    public static Timestamp addDia(Long d) {
        return addDia(new Timestamp(d));
    }

    public static Timestamp inicioBimestre(Timestamp d) {
        DateTime dt = new DateTime(d);
        if (mes(d) % 2 == 0) {
            if (mes(d) != 1) {
                dt = dt.withMonthOfYear(mes(d) - 1);
            }
        }
        return inicioMesDiaEHora(dt.getMillis());
    }

    public static Timestamp inicioBimestre(Long d) {
        return inicioBimestre(new Timestamp(d));
    }

    public static Timestamp fimBimestre(Timestamp d) {
        DateTime dt = new DateTime(d);
        if (mes(d) % 2 != 0) {
            dt = dt.plusMonths(1);
        }
        return fimMesDataEHora(dt.getMillis());
    }

    public static Timestamp fimBimestre(Long d) {
        return fimBimestre(new Timestamp(d));
    }

    public static Timestamp inicioTrimestre(Timestamp d) {
        DateTime dt = new DateTime(d);
        if (mes(d) % 3 != 0) {
            if (mes(d) == (mes(d) % 3)) {
                dt = dt.withMonthOfYear(1);
            } else {
                dt = dt.withMonthOfYear(mes(d) - (mes(d) % 3));
            }
        }
        return inicioMesDiaEHora(dt.getMillis());
    }

    public static Timestamp inicioTrimestre(Long d) {
        return inicioTrimestre(new Timestamp(d));
    }

    public static Timestamp fimTrimestre(Timestamp d) {
        DateTime dt = new DateTime(d);
        if (mes(d) % 3 != 0) {
            dt = dt.plusMonths(3 - (mes(d) % 3));
        }
        return fimMesDataEHora(dt.getMillis());
    }

    public static Timestamp fimTrimestre(Long d) {
        return fimTrimestre(new Timestamp(d));
    }

    private static List<String> reformulaCategoriasMensais(List<String> listDatas) {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            l.add(UtilData.mesE(UtilData.converteEmDate(s.substring(0, 10))) + "/" + UtilData.ano(UtilData.converteEmDate(s.substring(0, 10))));
        }
        return l;
    }

    private static List<String> reformulaCategoriasBimestrais(List<String> listDatas) {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add((UtilData.mes(f) / 2) + "º Bimestre de " + UtilData.ano(f));
        }
        return l;
    }

    private static List<String> reformulaCategoriasTrimestrais(List<String> listDatas) {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add((UtilData.mes(f) / 3) + "º Trimestre de " + UtilData.ano(f));
        }
        return l;

    }

    private static List<String> reformulaCategoriasSemestrais(List<String> listDatas) {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add((UtilData.mes(f) / 6) + "º Semestre de " + UtilData.ano(f));
        }
        return l;
    }

    private static List<String> reformulaCategoriasAnuais(List<String> listDatas) {
        List<String> l = new LinkedList<>();
        for (String s : listDatas) {
            Timestamp f = UtilData.converteEmDate(s.substring(13));
            l.add(UtilData.ano(f) + "");
        }
        return l;
    }

    public static List<String> deixaDataApresentavel(ABSGraficoSalvo gs, List<String> datas) {
        List<String> r = new LinkedList<>();
        if (gs instanceof InterfaceGraficoCustom) {
            r = deixaDataApresentavelGrafCustom(datas);
        } else if (gs instanceof InterfaceGraficoPreDet) {
            r = reformulaDatasPreDeterminadasDeixandoApresentavel(datas, gs.getAgrupamento());
        }
        return r;
    }

    public static List<String> deixaDataApresentavelGrafCustom(List<String> datas) {
        List<String> r = new LinkedList<>();
        // # REGRAS
        // * primeira posicao sempre mostra data inicial inteira, e abrevia a segunda ( 10/11/2010 - 15/11)
        // * não abrevia a segunda se ambas forem de anos diferentes 
        for (String d : datas) {
            Timestamp i = UtilData.inicioDia(UtilData.converteEmDate(d.substring(0, 10)));
            Timestamp f = UtilData.fimDia(UtilData.converteEmDate(d.substring(13)));
            if (datas.indexOf(d) == 0) { //se for a primera data
                if (ano(f) != ano(i)) {//se anos diferentes mostra ambas datas inteiras
                    r.add(diaNmesNanoN(i) + " - " + diaNmesNanoN(f));
                } else {//se não abrevia a segunda
                    r.add(diaNmesNanoN(i) + " - " + diaNmesN(f));
                }
            } else {//n é a primeira data
                if (ano(f) != ano(i)) { // anos diferentes  
                    r.add(diaNmesNanoN(i) + " - " + diaNmesNanoN(f)); // mostra a data ineira
                } else {// anos iguais
                    if (mes(i) == 1) {//se a primeira data está no peimeiro mes bota o ano
                        r.add(diaNmesNanoN(i) + " - " + diaNmesN(f)); // e na segunda corta o ano
                    } else {
                        r.add(diaNmesN(i) + " - " + diaNmesN(f)); // se não corta o ano em ambas
                    }
                }
            }
        }
        return r;
    }

    public static List<String> reformulaDatasPreDeterminadasDeixandoApresentavel(List<String> datas, String agrupamento) {
        List<String> r = new LinkedList<>();
        switch (agrupamento) {
            case "Mês":// mensal
                r = reformulaCategoriasMensais(datas);
                break;
            case "Bimestre":// bimestral
                r = reformulaCategoriasBimestrais(datas);
                break;
            case "Trimestre":// trimestral
                r = reformulaCategoriasTrimestrais(datas);
                break;
            case "Semestre":// semestre
                r = reformulaCategoriasSemestrais(datas);
                break;
            case "Ano":// anual
                r = reformulaCategoriasAnuais(datas);
                break;
        }
        return r;
    }

    // !!!!!!!! crer que as datas estejam certinhas al entrar aqui ! ! ! !
    public static List<String> criaCategoriasCustom(ABSGraficoSalvo gs) {
        List<String> datas = new LinkedList<>();
        Timestamp aux = new Timestamp(gs.getDe().getTime());
        int agrupamento = Integer.parseInt(gs.getAgrupamento().substring(0, gs.getAgrupamento().indexOf(" ")));
        while (!aux.equals(gs.getAte())) {
            String s;
            s = UtilData.diaNmesNanoN(aux) + " - ";
            aux.setTime(UtilData.fimDia(UtilData.addDias(aux.getTime(), agrupamento)).getTime());
            datas.add(s.concat(UtilData.diaNmesNanoN(aux)));
        }
        return datas;
    }

    public static ABSGraficoSalvo acertaDataCustom(ABSGraficoSalvo gs) {
        int agrupamento = Integer.parseInt(gs.getAgrupamento().substring(0, gs.getAgrupamento().indexOf(" ")));
        int diferencaEmDias = UtilData.diferencaEmDias(gs.getDe().getTime(), gs.getAte().getTime());
        if (!(diferencaEmDias % agrupamento == 0)) {
            gs.getAte().setTime(UtilData.addDias(gs.getAte().getTime(), agrupamento - (diferencaEmDias % agrupamento)));
        }
        gs.getDe().setTime(UtilData.inicioDia(gs.getDe().getTime()).getTime());
        gs.getAte().setTime(UtilData.fimDia(gs.getAte().getTime()).getTime());
        return gs;
    }

    public static Long addDias(Long data, int quantidadeDeDias) {
        return new DateTime(data).plusDays(quantidadeDeDias).getMillis();
    }

    public static ABSGraficoSalvo acertaDataPreDeterminado(ABSGraficoSalvo g) {
        switch (g.getAgrupamento()) {
            case "Mês":// mensal
                g.setDe(new Timestamp(inicioMesDiaEHora(g.getDe()).getTime()));
                g.setAte(new Timestamp(fimMesDataEHora(g.getAte()).getTime()));
                break;
            case "Bimestre":// bimestral
                g.setDe(new Timestamp(inicioBimestre(g.getDe()).getTime()));
                g.setAte(new Timestamp(fimBimestre(g.getAte()).getTime()));
                break;
            case "Trimestre":// trimestral
                g.setDe(new Timestamp(inicioTrimestre(g.getDe()).getTime()));
                g.setAte(new Timestamp(inicioTrimestre(g.getAte()).getTime()));
                break;
            case "Semestre":// semestre
                g.setDe(new Timestamp(inicioSemestre(g.getDe()).getTime()));
                g.setAte(new Timestamp(inicioSemestre(g.getAte()).getTime()));
                break;
            case "Ano":// anual
                g.setDe(new Timestamp(inicioAno(g.getDe()).getTime()));
                g.setAte(new Timestamp(inicioAno(g.getAte()).getTime()));
                break;
        }
        return g;
    }

    // # CONFERE DIFERENÇA PARA VALIDAR DATAS !(leva-se em conta que o inicio seja menor que o fim sempre!)
    public static boolean validaPorMes(Long inicio, Long fim) {
        if (ano(inicio) < ano(fim)) {
            return true;
        } else {
            if (mes(inicio) < mes(fim)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validaPorBimestre(Long inicio, Long fim) {
        DateTime dataInicio = new DateTime(inicio);
        DateTime dataFim = new DateTime(fim);
        if (dataInicio.plusMonths(2).getMillis() <= dataFim.getMillis()) {
            return true;
        }
        return false;
    }

    public static boolean validaPorTrimestre(Long inicio, Long fim) {
        DateTime dataInicio = new DateTime(inicio);
        DateTime dataFim = new DateTime(fim);
        if (dataInicio.plusMonths(3).getMillis() <= dataFim.getMillis()) {
            return true;
        }
        return false;
    }

    public static boolean validaPorSemestre(Long inicio, Long fim) {
        DateTime dataInicio = new DateTime(inicio);
        DateTime dataFim = new DateTime(fim);
        if (dataInicio.plusMonths(6).getMillis() <= dataFim.getMillis()) {
            return true;
        }
        return false;
    }

    public static boolean validaPorAno(Long inicio, Long fim) {
        if (ano(inicio) < ano(fim)) {
            return true;
        }
        return false;
    }

    public static boolean validaPorDias(Long inicio, Long fim, int dias) {
        DateTime i = new DateTime(inicio).plusDays(dias);
        DateTime f = new DateTime(fim);
        if (i.getMillis() <= f.getMillis()) {
            return true;
        }
        return false;
    }
    // # FIM CONFERE DIFERENÇA PARA VALIDAR DATAS
}
