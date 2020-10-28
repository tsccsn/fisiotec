/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import cp.CPAlunoPonto;
import cp.CPPontoDeEstagio;
import cp.clinica.CPConsulta;
import cp.clinica.CPPaciente;
import cp.clinica.CPSessao;
import cp.clinica.CPTratamento;
import cp.estoque.CPAdministradorEstoque;
import cp.estoque.CPProduto;
import cp.estoque.CPUnidadeDeMedida;
import cp.estoque.entrada.CPEntrada;
import cp.estoque.entrada.CPEntradaProduto;
import cp.estoque.saida.CPSaida;
import cp.estoque.saida.CPSaidaProduto;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminado;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminado;
import cp.portal.CPTurma;
import cp.portal.CPTurmaAlunos;
import cp.portal.usuarios.ABSUsuario;
import cp.portal.usuarios.CPAdministrador;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.DaoPontoDeEstagio;
import dao.clinica.DaoConsuta;
import dao.clinica.DaoPaciente;
import dao.clinica.DaoTratamento;
import dao.estoque.DaoProduto;
import dao.estoque.DaoUnidadeDeMedida;
import dao.estoque.entrada.DaoEntrada;
import dao.estoque.entrada.DaoEntradaProduto;
import dao.estoque.login.DaoAdministradorEstoque;
import dao.estoque.saida.DaoSaida;
import dao.estoque.saida.DaoSaidaProduto;
import dao.portal.DaoAlunoPonto;
import dao.portal.DaoTurma;
import dao.portal.DaoTurmaAluno;
import dao.portal.usuarios.DaoAdministrador;
import dao.portal.usuarios.DaoAluno;
import dao.portal.usuarios.DaoProfessor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.DateTime;
import utilidades.data.UtilData;
import utilidades.email.Email;
import utilidades.graficos.IO.GrafDataSet;
import utilidades.xml.NewGeradorXML;

/**
 *
 * @author Thiago
 */
public class NovoTeste extends Thread {

    private static int tamanhoSaida;
    private static int tamanhoEntrada;

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("A");
        System.out.println("B");
    }

    public static void main(String[] args) {

        Email t = new Email();
        t.desbloquearConta(DaoAdministrador.getByIDAdministrador(1l), "TRETACOD", true);
        System.out.println("ok");
    }

    public static void populaFelipe() {
        CPPaciente p = new CPPaciente();
        p.setBairro("bairro do paciente");
        p.setCelfone("(68)9281-2198");
        p.setCep("69.905-100");
        p.setCidade("Rio Branco");
        p.setComplemento("Complemento");
        p.setCpf("00196016207");
        p.setDataNasc(UtilData.converteEmDate("05/02/1991"));
        p.setEmail("thiaguerd@hotmail.com");
        p.setEstado("Acre");
        p.setFone("(68)9281-2198");
        p.setLogradouro("largadouro");
        p.setMae("Minha mãe");
        p.setNaturalidade("Naturalidade");
        p.setNome("Thiago Feitosa");
        p.setNum(10);
        p.setPai("Nome do meu pai");
        p.setSexo("Masculino");
        p.setRg("1050418");
        p.setRaca("Negra");
        p = DaoPaciente.mergeII(p);

        p = new CPPaciente();
        p.setBairro("Aviário");
        p.setCelfone("(68)8403-1523");
        p.setCep("69.905-100");
        p.setCidade("Rio Branco");
        p.setComplemento("Complemento");
        p.setCpf("85876895253");
        p.setDataNasc(UtilData.converteEmDate("05/02/1991"));
        p.setEmail("flpmchd@hotmail.com");
        p.setEstado("Acre");
        p.setFone("(68)3224-9039");
        p.setLogradouro("Travessa Santa Luzia");
        p.setMae("Ruth Machado");
        p.setNaturalidade("Rio Branco");
        p.setNome("Felipe Machado Carneiro");
        p.setNum(125);
        p.setPai("Antonio Carneiro");
        p.setSexo("Masculino");
        p.setRg("410352");
        p.setRaca("Parda");
        p = DaoPaciente.mergeII(p);

        CPConsulta c = new CPConsulta();
        c.setPaciente(p);
        c.setBox(4);
        c.setDia(UtilData.converteEmDate("16/12/2011"));
        c.setHoraFinal(new DateTime(2011, 12, 16, 14, 55).toDate());
        c.setHoraInicial(new DateTime(2011, 12, 16, 14, 00).toDate());
        c.setStatus("Aberto");
        c = DaoConsuta.mergeII(c);

        CPSessao s = new CPSessao();
        s.setDiaCad(UtilData.converteEmDate("19/12/2011"));
        s.setPaciente(p);
        s.setPatologia("Ortopedia");
        s.setQtdTratamento(2);
        s.setStatus("Em Aberto");


        CPTratamento t = new CPTratamento();
        t.setBox(5);
        t.setDia(UtilData.converteEmDate("20/12/2011"));
        t.setHoraFinal(new DateTime(2011, 12, 20, 14, 55).toDate());
        t.setHoraInicial(new DateTime(2011, 12, 20, 14, 00).toDate());
        t.setNumTratamento(1);
        t.setSessao(s);
        t.setStatus("Em Aberto");
        t = DaoTratamento.mergeII(t);

        t = new CPTratamento();
        t.setBox(5);
        t.setDia(UtilData.converteEmDate("21/12/2011"));
        t.setHoraFinal(new DateTime(2011, 12, 21, 14, 55).toDate());
        t.setHoraInicial(new DateTime(2011, 12, 21, 14, 00).toDate());
        t.setNumTratamento(1);
        t.setSessao(s);
        t.setStatus("Em Aberto");
        t = DaoTratamento.mergeII(t);
    }

    public static String divisor(String total, String porcentagem) {
        int t = Integer.parseInt(total);
        double t2 = t / 100d;
        Double d = new Double(porcentagem);
        d = d * t2;
        return new DecimalFormat("0").format((d));
    }

    public static void paciente() {
        CPPaciente p = new CPPaciente();
        p.setBairro("bairro do paciente");
        p.setCelfone("6892812198");
        p.setCep("69.905-100");
        p.setCidade("Rio Branco");
        p.setComplemento("Complemento");
        p.setCpf("00196016207");
        p.setDataNasc(new Date(UtilData.converteEmDate("05/02/1991").getTime()));
        p.setEmail("thiaguerd@hotmail.com");
        p.setEstado("Acre");
        p.setFone("6892812198");
        p.setLogradouro("largadouro!");
        p.setMae("Minha mãe");
        p.setNaturalidade("Naturalidade");
        p.setNome("Thiago Feitosa");
        p.setNum(10);
        p.setPai("Nome do meu pai");
        p.setSexo("Masculino");
        p.setRg("1050418");
        p.setRaca("Negra");
        DaoPaciente.merge(p);
    }

    public static void fazATreta() {
        geraBanco();
        cadastraPonto();
        cadastraUnidades();
        cadastraProdutos();
        novoRegistroEntrada();
        novoEntradaProdutos();
        novoRegistroSaida();
        novoRegistroSaidaProduto();
        treta();
        alunos();
        alunoPonto();
        turmas();
        alunoTurma();
        administrador();
        professor();
        cadastraUsuarioPortal();
    }

    public static void cadastraUsuarioPortal() {
        CPAdministradorEstoque e = new CPAdministradorEstoque();
        e.setContaAtiva(true);
        e.setEmailPrincipal("usuarioportal@email.com");
        e.setEmailSecundario("segundoemail@email.com");
        e.setLogin("thiaguerd");
        e.setNomeCompleto("Claudinha Claudines Cunha");
        e.setSenha("000");
        e.setPrivilegios(true);
        DaoAdministradorEstoque.merge(e);
    }

    public static void treta() {
        // 16/10/2011
        List<CPProduto> produtos = DaoProduto.getAll("nome");
        List<CPPontoDeEstagio> pontos = DaoPontoDeEstagio.getAll("nome");

        NewGeradorXML geradorXml = new NewGeradorXML();
        // ENTRADA
        CPGraficoEntradaPreDeterminado g = new CPGraficoEntradaPreDeterminado();
        g.setDe(UtilData.converteEmDate("01/01/2011"));
        g.setAte(UtilData.converteEmDate("30/04/2011"));
        g.setAgrupamento("Mês");
        g = (CPGraficoEntradaPreDeterminado) geradorXml.acertaData(g);
        geradorXml.defineCPEntradaProdutoNoPerido(produtos, g);
        geradorXml.criaDatasParaRealizarConsultas(g);
        geradorXml.defineDataSetEntrada(produtos);


        NewGeradorXML geradorXML2 = new NewGeradorXML();
        //SAIDA

        CPGraficoSaidaPreDeterminado saidaSalvo = new CPGraficoSaidaPreDeterminado();
        saidaSalvo.setDe(UtilData.converteEmDate("01/01/2011"));
        saidaSalvo.setAte(UtilData.converteEmDate("30/04/2011"));
        saidaSalvo.setAgrupamento("Mês");
        saidaSalvo = (CPGraficoSaidaPreDeterminado) geradorXML2.acertaData(saidaSalvo);
        geradorXML2.defineCPSaidaProdutosNoPeriodo(pontos, produtos, saidaSalvo);
        geradorXML2.criaDatasParaRealizarConsultas(saidaSalvo);
        geradorXML2.defineDataSetSaidaGeral(produtos);



        System.out.println(" - - - - datas");
        for (String s : geradorXML2.getListDatasParaConsulta()) {
            System.out.println(s);
        }

        System.out.println("");
        System.out.println("");
        System.out.println(" - - - - - - - - saida - - - - - - - -");
        List<GrafDataSet> listDataSetSaida = geradorXML2.getListDataSetSaida();
        for (GrafDataSet xGrafDataSet : listDataSetSaida) {

            System.out.println("- - - - - - - -" + xGrafDataSet.getNome());
            int i = 0;
            for (String xS : xGrafDataSet.getValores()) {

                System.out.println(geradorXML2.getListDatasParaConsulta().get(i) + " - " + xS);
                i++;
            }
        }
        System.out.println("");
        System.out.println("");
        System.out.println(" - - - - - - - - entrada - - - - - - - -");
        List<GrafDataSet> listDataSetEntrada = geradorXml.getListDataSetEntrada();
        for (GrafDataSet xDataSet : listDataSetEntrada) {
            System.out.println("- - - - - - - -" + xDataSet.getNome());
            int i = 0;
            for (String xS : xDataSet.getValores()) {
                System.out.println(geradorXML2.getListDatasParaConsulta().get(i) + " - " + xS);
                i++;
            }
        }


        List<GrafDataSet> diferenca = new LinkedList<>();

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
            diferenca.add(dif);
        }

        System.out.println("");
        System.out.println("");
        System.out.println(" - - - - - - - - diferenca - - - - - - - -");
        for (GrafDataSet xDif : diferenca) {
            System.out.println("- - - - - - - -" + xDif.getNome());
            int i = 0;
            for (String xS : xDif.getValores()) {
                System.out.println(geradorXML2.getListDatasParaConsulta().get(i) + " - " + xS);
                i++;
            }
        }






        System.out.println("ok");





    }

    public static void fazTudo() {
        geraBanco();
        cadastraPonto();
        cadastraUnidades();
        cadastraProdutos();
        novoRegistroEntrada();
        novoEntradaProdutos();
        novoRegistroSaida();
        novoRegistroSaidaProduto();
        turmas();
        alunos();
    }

    public static void primos() {
        for (int i = 10; i < 1000; i++) {
            boolean primo = false;
            for (int j = 2; j <= (i / 2) + 1; j++) {
                if (i % j == 0) {
                    primo = true;
                }
            }
            if (!primo) {
                System.out.println(i);
            }
        }
    }

    public static void geraBanco() {
        GeraBanco.faz();
    }

    public static void cadastraPonto() {
        CPPontoDeEstagio pe = new CPPontoDeEstagio();
        pe.setNome("Hospital Maldito");
        DaoPontoDeEstagio.merge(pe);

        pe.setNome("Clínica Sombria");
        DaoPontoDeEstagio.merge(pe);

        pe.setNome("Psiquiatria na Mansão");
        DaoPontoDeEstagio.merge(pe);

        pe.setNome("Morte Lenta");
        DaoPontoDeEstagio.merge(pe);

        pe.setNome("Chamem o Doutor!");
        DaoPontoDeEstagio.merge(pe);
    }

    public static void cadastraUnidades() {
        CPUnidadeDeMedida u = new CPUnidadeDeMedida();
        // ----------1-------------------
        u.setNome("Mililitro");
        u.setAbreviacao("ml");
        DaoUnidadeDeMedida.merge(u);
        // -----------2------------------
        u.setNome("Resma");
        u.setAbreviacao("Res.");
        DaoUnidadeDeMedida.merge(u);
        // ------------3-----------------
        u.setNome("Kilograma");
        u.setAbreviacao("Kg");
        DaoUnidadeDeMedida.merge(u);
        // -------------4----------------
        u.setNome("Unidade");
        u.setAbreviacao("Uni.");
        DaoUnidadeDeMedida.merge(u);
        // --------------5---------------
        u.setNome("Litro");
        u.setAbreviacao("L");
        DaoUnidadeDeMedida.merge(u);
    }

    public static void cadastraProdutos() {
        CPProduto p = new CPProduto();
        // -------1----------------------
        p.setMedida(950);
        p.setNome("Alcool");
        p.setQuantidadeEmStoque(1000);
        p.setQuantidadeMinimaEmStoque(600);
        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(5));
        DaoProduto.merge(p);
        // --------2---------------------
        p.setMedida(1);
        p.setNome("Papel");
        p.setQuantidadeEmStoque(1000);
        p.setQuantidadeMinimaEmStoque(600);
        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(2));
        DaoProduto.merge(p);
        // ---------3--------------------
        p.setMedida(1.5);
        p.setNome("Gel de ultra som");
        p.setQuantidadeEmStoque(1000);
        p.setQuantidadeMinimaEmStoque(600);
        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(5));
        DaoProduto.merge(p);
        // ----------4-------------------
        p.setMedida(50);
        p.setNome("Caixa de luvas");
        p.setQuantidadeEmStoque(1000);
        p.setQuantidadeMinimaEmStoque(600);
        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(4));
        DaoProduto.merge(p);
        // -----------5------------------
        p.setMedida(50);
        p.setNome("Caixa de máscaras");
        p.setQuantidadeEmStoque(1000);
        p.setQuantidadeMinimaEmStoque(600);
        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(4));
        DaoProduto.merge(p);
        // -----------5------------------
        p.setMedida(150);
        p.setNome("Mercuruio");
        p.setQuantidadeEmStoque(1000);
        p.setQuantidadeMinimaEmStoque(600);
        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(1));
        DaoProduto.merge(p);
    }

    public static void novoRegistroEntrada() {
        Timestamp dataInicial = UtilData.getDataTimestamp();
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicial);
        c.add(Calendar.YEAR, -3);
        dataInicial = new Timestamp(c.getTime().getTime());

        Timestamp hoje = UtilData.getDataTimestamp();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(hoje);
        c2.add(Calendar.MONTH, 2);
        hoje = new Timestamp(c2.getTime().getTime());
        while (UtilData.ano(dataInicial) < 2015) {
            CPEntrada e = new CPEntrada();
            e.setDataEntrada(new Timestamp(dataInicial.getTime()));
            DaoEntrada.merge(e);
            c.add(Calendar.DAY_OF_WEEK, ((int) (Math.random() * 5) + 1));
            dataInicial = new Timestamp(c.getTime().getTime());
            tamanhoEntrada++;
        }
    }

    public static void novoEntradaProdutos() {
        // para cada registro de saida
        for (long i = 1; i <= tamanhoEntrada; i++) {
            // add de 2 a 5 produtos
            int quantidadeDeProdutosDestaSaida = ((int) (Math.random() * 4) + 2);
            // lista de id de produtos
            List<Integer> listaDeProdutos = new LinkedList<Integer>();
            // enquanto o tamanho da lista for menor que a quantidade de
            // produtos
            while (listaDeProdutos.size() < quantidadeDeProdutosDestaSaida) {
                // id candidado de 1 a 5
                int possivelId = ((int) (Math.random() * 5) + 1);
                // add novo candidado
                if (!listaDeProdutos.contains(possivelId)) {
                    listaDeProdutos.add(possivelId);
                }
            }
            CPEntrada e = DaoEntrada.getById(i);
            // terminou de add os candidados
            for (Integer integer : listaDeProdutos) {
                CPEntradaProduto ep = new CPEntradaProduto();
                ep.setEntrada(e);
                ep.setQuantidade((int) (Math.random() * 10) + 10);
                ep.setProduto(DaoProduto.getById(integer));
                DaoEntradaProduto.merge(ep);
            }
        }
    }

    public static void novoRegistroSaida() {

        Timestamp dataInicial = UtilData.getDataTimestamp();
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicial);
        c.add(Calendar.YEAR, -3);
        dataInicial = new Timestamp(c.getTime().getTime());

        Date hoje = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(hoje);
        c2.add(Calendar.MONTH, 2);
        hoje = c2.getTime();
        CPSaida s = new CPSaida();
        while (UtilData.ano(dataInicial) < 2015) {
            s = new CPSaida();
            s.setDataSaida(new Timestamp(dataInicial.getTime()));
            CPPontoDeEstagio pe = DaoPontoDeEstagio.getById((int) ((Math.random() * 5) + 1));
            s.setDestino(pe);
            s = DaoSaida.mergeII(s);
            c.add(Calendar.DAY_OF_WEEK, ((int) (Math.random() * 5) + 1));
            dataInicial = new Timestamp(c.getTime().getTime());
        }
        tamanhoSaida = (int) s.getId();
    }

    public static void novoRegistroSaidaProduto() {

        // para cada registro de saida
        for (int i = 1; i <= tamanhoSaida; i++) {
            // add de 2 a 5 produtos
            int quantidadeDeProdutosDestaSaida = ((int) (Math.random() * 4) + 2);
            // lista de id de produtos
            List<Integer> listaDeProdutos = new LinkedList<>();
            // enquanto o tamanho da lista for menor que a quantidade de
            // produtos
            while (listaDeProdutos.size() < quantidadeDeProdutosDestaSaida) {
                // id candidado de 1 a 5
                int possivelId = ((int) (Math.random() * 5) + 1);
                // add novo candidado
                if (!listaDeProdutos.contains(possivelId)) {
                    listaDeProdutos.add(possivelId);
                }
            }
            CPSaida s = DaoSaida.getById(i);
            // terminou de add os candidados
            for (Integer integer : listaDeProdutos) {
                CPSaidaProduto sp = new CPSaidaProduto();
                sp.setSaida(s);
                sp.setQuantidade((int) (Math.random() * 8) + 8);
                sp.setProduto(DaoProduto.getById(integer));
                DaoSaidaProduto.merge(sp);
            }
        }
    }

    public static void turmas() {
        CPTurma turma = new CPTurma();
        turma.setCodigoTurma("001");
        turma.setDataInicio(UtilData.converteEmDate("01/01/2008"));
        turma.setDataTermino(UtilData.converteEmDate("01/01/2012"));
        turma.setVigente(true);
        DaoTurma.merge(turma);

        turma.setCodigoTurma("002");
        turma.setDataInicio(UtilData.converteEmDate("01/01/2009"));
        turma.setDataTermino(UtilData.converteEmDate("01/01/2013"));
        turma.setVigente(true);
        DaoTurma.merge(turma);

        turma.setCodigoTurma("003");
        turma.setDataInicio(UtilData.converteEmDate("01/01/2010"));
        turma.setDataTermino(UtilData.converteEmDate("01/01/2014"));
        turma.setVigente(true);
        DaoTurma.merge(turma);

        turma.setCodigoTurma("004");
        turma.setDataInicio(UtilData.converteEmDate("01/01/2011"));
        turma.setDataTermino(UtilData.converteEmDate("01/01/2015"));
        turma.setVigente(true);
        DaoTurma.merge(turma);
        turma.setCodigoTurma("005");
        turma.setDataInicio(UtilData.converteEmDate("01/01/2012"));
        turma.setDataTermino(UtilData.converteEmDate("01/01/2016"));
        turma.setVigente(true);
        DaoTurma.merge(turma);
    }

    public static void alunos() {
        CPAluno a = new CPAluno();
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno1@f3soft.net");
        a.setEmailSecundario("aluno01@f3soft.net");
        a.setLogin("aluno1");
        a.setNomeCompleto("Aluno Um de Oliveira Machado Le Pedreira Construção Luz Vital");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Um");
        a.setSenha("aluno1");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);
        // --------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno2@f3soft.net");
        a.setEmailSecundario("aluno02@f3soft.net");
        a.setLogin("aluno2");
        a.setNomeCompleto("Aluno Dois de Souza Aquino Oliveira Chumbega Hipnoze");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Dois");
        a.setSenha("aluno2");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);
        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno3@f3soft.net");
        a.setEmailSecundario("aluno03@f3soft.net");
        a.setLogin("aluno3");
        a.setNomeCompleto("Aluno Três de Oliveira Fernandes Mustafa Rodrigues Hospital Jacinto Tubérculo");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Três");
        a.setSenha("aluno3");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);
        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno4@f3soft.net");
        a.setEmailSecundario("aluno04@f3soft.net");
        a.setLogin("aluno4");
        a.setNomeCompleto("Aluno Quatro Cruz Constatino Geraldo Rogério Souza Pereira Farmácia Epitáfico Rusbraldo Aquino Eustáqui La Paquita");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Quatro");
        a.setSenha("aluno4");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);
        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno5@f3soft.net");
        a.setEmailSecundario("aluno05@f3soft.net");
        a.setLogin("aluno5");
        a.setNomeCompleto("Aluno Cinco Souza");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Cinco");
        a.setSenha("aluno5");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);
        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno1@f3soft.net");
        a.setEmailSecundario("aluno01@f3soft.net");
        a.setLogin("aluno6");
        a.setNomeCompleto("Aluno Seis de Oliveira");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Seis");
        a.setSenha("aluno6");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);

        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno7@f3soft.net");
        a.setEmailSecundario("aluno07@f3soft.net");
        a.setLogin("aluno7");
        a.setNomeCompleto("Aluno Sete Rodrigues");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Sete");
        a.setSenha("aluno7");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);

        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno8@f3soft.net");
        a.setEmailSecundario("aluno08@f3soft.net");
        a.setLogin("aluno8");
        a.setNomeCompleto("Aluno Oito Levita");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Oito");
        a.setSenha("aluno8");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);

        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno9@f3soft.net");
        a.setEmailSecundario("aluno09@f3soft.net");
        a.setLogin("aluno9");
        a.setNomeCompleto("Aluno Nove Neto");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Nove");
        a.setSenha("aluno9");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);

        // ---------------------------------------
        a.setContaAtiva(true);
        a.setEmailPrincipal("aluno10@f3soft.net");
        a.setEmailSecundario("aluno010@f3soft.net");
        a.setLogin("aluno10");
        a.setNomeCompleto("Aluno Dez Combis");
        a.setPrimeiroNome("Aluno");
        a.setSegundoNome("Dez");
        a.setSenha("alunoz");
        a.setTentativasLogin(0);
        DaoAluno.merge(a);
    }

    public static void alunoPonto() {
        CPAlunoPonto p = new CPAlunoPonto();
        p.setAluno(DaoAluno.getByID(1));
        p.setPonto(DaoPontoDeEstagio.getById(1));
        DaoAlunoPonto.salvar(p);
        //--------------------------
        p.setAluno(DaoAluno.getByID(2));
        p.setPonto(DaoPontoDeEstagio.getById(2));
        DaoAlunoPonto.salvar(p);
        //--------------------------
        p.setAluno(DaoAluno.getByID(3));
        p.setPonto(DaoPontoDeEstagio.getById(3));
        DaoAlunoPonto.salvar(p);
        //--------------------------
        p.setAluno(DaoAluno.getByID(4));
        p.setPonto(DaoPontoDeEstagio.getById(4));
        DaoAlunoPonto.salvar(p);
        //--------------------------
        p.setAluno(DaoAluno.getByID(5));
        p.setPonto(DaoPontoDeEstagio.getById(5));
        DaoAlunoPonto.salvar(p);
    }

    public static void alunoTurma() {
        CPTurmaAlunos turmaAlunos = new CPTurmaAlunos();
        turmaAlunos.setAluno(DaoAluno.getByID(1));
        turmaAlunos.setTurma(DaoTurma.getByID(1));
        turmaAlunos.setVigente(true);
        DaoTurmaAluno.salva(turmaAlunos);
        //----------------------------------
        turmaAlunos.setAluno(DaoAluno.getByID(2));
        turmaAlunos.setTurma(DaoTurma.getByID(3));
        turmaAlunos.setVigente(true);
        DaoTurmaAluno.salva(turmaAlunos);
        //----------------------------------
        turmaAlunos.setAluno(DaoAluno.getByID(3));
        turmaAlunos.setTurma(DaoTurma.getByID(4));
        turmaAlunos.setVigente(true);
        DaoTurmaAluno.salva(turmaAlunos);
        //----------------------------------
        turmaAlunos.setAluno(DaoAluno.getByID(5));
        turmaAlunos.setTurma(DaoTurma.getByID(5));
        turmaAlunos.setVigente(true);
        DaoTurmaAluno.salva(turmaAlunos);
    }

    public static void professor() {

        CPProfessor p = new CPProfessor();
        p.setContaAtiva(true);
        p.setEmailPrincipal("professormaldito@f3soft.net");
        p.setEmailSecundario("professormaldito2@f3soft.net");
        p.setLogin("professormaldito");
        p.setNomeCompleto("Professor Maldito das Cruzes");
        p.setPrimeiroNome("Professor");
        p.setSegundoNome("Maldito");
        p.setSenha("malditoprofessor");
        p.setTentativasLogin(0);

        DaoProfessor.merge(p);

        // -----------------------
        p.setContaAtiva(true);
        p.setEmailPrincipal("professorsombrio@f3soft.net");
        p.setEmailSecundario("professorsombrio2@f3soft.net");
        p.setLogin("professorsombrio");
        p.setNomeCompleto("Professor Sombrio de Algoz");
        p.setPrimeiroNome("Professor");
        p.setSegundoNome("Sombrio");
        p.setSenha("sombrioprofessor");
        p.setTentativasLogin(0);

        DaoProfessor.merge(p);

        // -----------------------
        p.setContaAtiva(true);
        p.setEmailPrincipal("professormansao@f3soft.net");
        p.setEmailSecundario("professormansao2@f3soft.net");
        p.setLogin("professormansao");
        p.setNomeCompleto("Professor Mansão de Valviera");
        p.setPrimeiroNome("Professor");
        p.setSegundoNome("Mansão");
        p.setSenha("mansaoprofessor");
        p.setTentativasLogin(0);

        DaoProfessor.merge(p);

        // -----------------------
        p.setContaAtiva(true);
        p.setEmailPrincipal("professorlento@f3soft.net");
        p.setEmailSecundario("professorlento2@f3soft.net");
        p.setLogin("professorlento");
        p.setNomeCompleto("Professor Lento de Muniz");
        p.setPrimeiroNome("Professor");
        p.setSegundoNome("Lento");
        p.setSenha("lentoprofessor");
        p.setTentativasLogin(0);

        DaoProfessor.merge(p);

        // -----------------------
        p.setContaAtiva(true);
        p.setEmailPrincipal("professordoutor@f3soft.net");
        p.setEmailSecundario("professordoutor2@f3soft.net");
        p.setLogin("professordoutor");
        p.setNomeCompleto("Professor Doutor de Oliveira");
        p.setPrimeiroNome("Professor");
        p.setSegundoNome("Doutor");
        p.setSenha("doutorprofessor");
        p.setTentativasLogin(0);

        DaoProfessor.merge(p);
    }

    public static void professorPonto() {
    }

    public static void administrador() {

        // Administrador
        CPAdministrador ad = new CPAdministrador();
        ad.setEmailPrincipal("thiaguerd@hotmail.com");
        ad.setEmailSecundario("thiago_som@hotmail.com");
        ad.setLogin("thiaguerd");
        ad.setNomeCompleto("Thiago Feitosa de Souza");
        ad.setPrimeiroNome("Thiago");
        ad.setSegundoNome("Feitosa");
        ad.setSenha("000");
        ad.setTentativasLogin(0);
        ad.setContaAtiva(true);

        DaoAdministrador.merge(ad);
    }
}
