/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package testes;

import cp.estoque.CPProduto;
import java.sql.Timestamp;
import org.joda.time.DateTime;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
public class Teste {

    public static void main(String[] args) {
        // 16/10/2011
        if(1 > 2){
            System.out.println(" 1 é maior q 2");
        }else{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i==5){
                    
                    System.out.println("i = 5");
                    break;
                }
            }
            System.out.println("ok");
        }
   }
//
//    public static int tamanhoSaida = 0;
//    public static int tamanhoEntrada = 0;
//
//    public static void main(String[] args) {
//        // 16/10/2011
//        BeanGrafSaida bg = new BeanGrafSaida();
//        BeanListProdutos lp = new BeanListProdutos();
//        System.out.println("ok");
//    }
//
//    public static void teste() {
//        if (1 < 2) {
//            System.out.println("1");
//            return;
//        }
//        if (1 < 3) {
//            System.out.println("aff!");
//        }
//    }
//
//    public static void geraBancoFazTudo() {
//        geraBanco();
//        System.out.println("--------------------------------------------- banco gerado");
//        cadastraPonto();
//        System.out.println("--------------------------------------------- ponto cadastrado");
//        cadastraUnidades();
//        System.out.println("--------------------------------------------- unidades cadastradas");
//        cadastraProdutos();
//        System.out.println("--------------------------------------------- produtos cadastrados");
//        novoRegistroSaida();
//        System.out.println("--------------------------------------------- registro saida prontos");
//        novoRegistroSaidaProduto();
//        System.out.println("--------------------------------------------- produtos das saidas feito");
//        novoRegistroEntrada();
//        System.out.println("--------------------------------------------- registro de entrada pronto");
//        novoEntradaProdutos();
//        System.out.println("--------------------------------------------- registro entrada produtos feito");
//        novosGraficosCustom();
//        System.out.println("--------------------------------------------- graficos salvo custom prontos");
//        cadastraAdministrador();
//        System.out.println("--------------------------------------------- cadastro de administrador feito");
//        cadastraProfessor();
//        System.out.println("--------------------------------------------- cadastro de professor feito");
//        cadastraAluno();
//        System.out.println("--------------------------------------------- cadastro de aluno feito");
//        cadastraTurma();
//        System.out.println("--------------------------------------------- cadastro de turmas feito");
//        ligaProfessorATurma();
//        System.out.println("--------------------------------------------- liga professor a turma feito");
//        System.out.println("--------------------------------------------- liga aluno a turma feito");
//        ligaTurmaAPonto();
//        System.out.println("--------------------------------------------- liga turma a ponto feito");
//        System.out.println("--------------------------------------------- tudo feito --------------------------------------------- ");
//    }
//
//    public static String inverteString(String s) {
//        String res = "";
//        for (int i = s.length() - 1; i >= 0; i--) {
//            res = res.concat(s.charAt(i) + "");
//        }
//        return res;
//    }
//
//    public static String inverteStringDuplicandoChars(String s) {
//        String res = "";
//        for (int i = s.length() - 1; i >= 0; i--) {
//            res = res.concat(s.charAt(i) + "" + s.charAt(i));
//        }
//        return res;
//    }
//
//    public static void cadastraAdministrador() {
//        // Administrador
//        CPAdministrador ad = new CPAdministrador();
//        ad.setEmailPrincipal("thiaguerd@hotmail.com");
//        ad.setEmailSecundario("thiago_som@hotmail.com");
//        ad.setLogin("thiaguerd");
//        ad.setNomeCompleto("Thiago Feitosa de Souza");
//        ad.setPrimeiroNome("Thiago");
//        ad.setSegundoNome("Feitosa");
//        ad.setSenha("pvyfq2jtbv");
//        ad.setTentativasLogin(0);
//        ad.setContaAtiva(true);
//
//        DaoGenericoUsuario.merge(ad);
//    }
//
//    public static void novosGraficosCustom() {
//        // saida custom 1
//        CPGraficoSaidaCustom c = new CPGraficoSaidaCustom();
//        c.setAgrupamento("10 dias");
//        c.setDe(UtilData.inicioMes(UtilData.getDataTimestamp()));
//        c.setAte((UtilData.fimMes(UtilData.getDataTimestamp())));
//        c.setNome("salvo 1");
//        c.setDataModificado(new Timestamp(new Date().getTime()));
//        c = (CPGraficoSaidaCustom) DaoGrafSaida.mergeII(c);
//
//        // produtos da saida custom 1
//        CPGraficoSaidaCustomProdutos gsc = new CPGraficoSaidaCustomProdutos();
//        gsc.setGrafico(c);
//        gsc.setProduto(DaoProduto.getById(1));
//        DaoGrafSaidaProdutos.merge(gsc);
//
//        gsc.setProduto(DaoProduto.getById(2));
//        DaoGrafSaidaProdutos.merge(gsc);
//
//        // pontos da saida custom 1
//        CPGraficoSaidaCustomPontos gscp = new CPGraficoSaidaCustomPontos();
//        gscp.setGrafico(c);
//        gscp.setPonto(DaoPontoDeEstagio.getById(1l));
//        DaoGrafSaidaPonto.merge(gscp);
//
//        gscp.setPonto(DaoPontoDeEstagio.getById(2l));
//        DaoGrafSaidaPonto.merge(gscp);
//
//        gscp.setPonto(DaoPontoDeEstagio.getById(3l));
//        DaoGrafSaidaPonto.merge(gscp);
//
//        // saida custom 2
//        c = new CPGraficoSaidaCustom();
//        c.setAgrupamento("5 dias");
//        c.setDe(UtilData.inicioMes(UtilData.getDataTimestamp()));
//        c.setAte(UtilData.fimMes(UtilData.getDataTimestamp()));
//        c.setNome("salvo 2");
//        c.setDataModificado(new Timestamp(new Date().getTime()));
//        c = (CPGraficoSaidaCustom) DaoGrafSaida.mergeII(c);
//
//        // produtos de saida custom 2
//        gsc = new CPGraficoSaidaCustomProdutos();
//        gsc.setGrafico(c);
//        gsc.setProduto(DaoProduto.getById(3));
//        DaoGrafSaidaProdutos.merge(gsc);
//
//        gsc.setProduto(DaoProduto.getById(4));
//        DaoGrafSaidaProdutos.merge(gsc);
//
//        // pontos da saida custom 1
//        CPGraficoSaidaCustomPontos gscp2 = new CPGraficoSaidaCustomPontos();
//        gscp2.setGrafico(c);
//        gscp2.setPonto(DaoPontoDeEstagio.getById(3l));
//        DaoGrafSaidaPonto.merge(gscp2);
//
//        gscp2.setPonto(DaoPontoDeEstagio.getById(4l));
//        DaoGrafSaidaPonto.merge(gscp2);
//
//        gscp2.setPonto(DaoPontoDeEstagio.getById(5l));
//        DaoGrafSaidaPonto.merge(gscp2);
//    }
//
//    public static void novoRegistroEntrada() {
//        Timestamp dataInicial = UtilData.getDataTimestamp();
//        Calendar c = Calendar.getInstance();
//        c.setTime(dataInicial);
//        c.add(Calendar.YEAR, -3);
//        dataInicial = new Timestamp(c.getTime().getTime());
//
//        Timestamp hoje = UtilData.getDataTimestamp();
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(hoje);
//        c2.add(Calendar.MONTH, 2);
//        hoje = new Timestamp(c2.getTime().getTime());
//
//        while (UtilData.ano(dataInicial) < 2013) {
//            CPEntrada e = new CPEntrada();
//            e.setDataEntrada(new Timestamp(dataInicial.getTime()));
//            DaoEntrada.merge(e);
//            c.add(Calendar.DAY_OF_WEEK, ((int) (Math.random() * 5) + 1));
//            dataInicial = new Timestamp(c.getTime().getTime());
//            tamanhoEntrada++;
//        }
//
//    }
//
//    public static void novoEntradaProdutos() {
//        // para cada registro de saida
//        for (long i = 1; i <= tamanhoEntrada; i++) {
//            // add de 2 a 5 produtos
//            int quantidadeDeProdutosDestaSaida = ((int) (Math.random() * 4) + 2);
//            // lista de id de produtos
//            List<Integer> listaDeProdutos = new LinkedList<Integer>();
//            // enquanto o tamanho da lista for menor que a quantidade de
//            // produtos
//            while (listaDeProdutos.size() < quantidadeDeProdutosDestaSaida) {
//                // id candidado de 1 a 5
//                int possivelId = ((int) (Math.random() * 5) + 1);
//                // add novo candidado
//                if (!listaDeProdutos.contains(possivelId)) {
//                    listaDeProdutos.add(possivelId);
//                }
//            }
//            CPEntrada e = DaoEntrada.getById(i);
//            // terminou de add os candidados
//            for (Integer integer : listaDeProdutos) {
//                CPEntradaProduto ep = new CPEntradaProduto();
//                ep.setEntrada(e);
//                ep.setQuantidade((int) (Math.random() * 151) + 50);
//                ep.setProduto(DaoProduto.getById(integer));
//                DaoEntradaProduto.merge(ep);
//            }
//        }
//    }
//
//    public static void novoRegistroSaida() {
//
//        Timestamp dataInicial = UtilData.getDataTimestamp();
//        Calendar c = Calendar.getInstance();
//        c.setTime(dataInicial);
//        c.add(Calendar.YEAR, -3);
//        dataInicial = new Timestamp(c.getTime().getTime());
//
//        Date hoje = new Date();
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(hoje);
//        c2.add(Calendar.MONTH, 2);
//        hoje = c2.getTime();
//        CPSaida s = new CPSaida();
//        while (UtilData.ano(dataInicial) < 2013) {
//            s = new CPSaida();
//            s.setDataSaida(new Timestamp(dataInicial.getTime()));
//            CPPontoDeEstagio pe = DaoPontoDeEstagio.getById((int) ((Math.random() * 5) + 1));
//            s.setDestino(pe);
//            s = DaoSaida.mergeII(s);
//            c.add(Calendar.DAY_OF_WEEK, ((int) (Math.random() * 5) + 1));
//            dataInicial = new Timestamp(c.getTime().getTime());
//        }
//        tamanhoSaida = Integer.parseInt(s.getId().toString());
//    }
//
//    public static void novoRegistroSaidaProduto() {
//
//        // para cada registro de saida
//        for (int i = 1; i <= tamanhoSaida; i++) {
//            // add de 2 a 5 produtos
//            int quantidadeDeProdutosDestaSaida = ((int) (Math.random() * 4) + 2);
//            // lista de id de produtos
//            List<Integer> listaDeProdutos = new LinkedList<>();
//            // enquanto o tamanho da lista for menor que a quantidade de
//            // produtos
//            while (listaDeProdutos.size() < quantidadeDeProdutosDestaSaida) {
//                // id candidado de 1 a 5
//                int possivelId = ((int) (Math.random() * 5) + 1);
//                // add novo candidado
//                if (!listaDeProdutos.contains(possivelId)) {
//                    listaDeProdutos.add(possivelId);
//                }
//
//            }
//            CPSaida s = DaoSaida.getById(i);
//            // terminou de add os candidados
//            for (Integer integer : listaDeProdutos) {
//                CPSaidaProduto sp = new CPSaidaProduto();
//                sp.setSaida(s);
//                sp.setQuantidade((int) (Math.random() * 151) + 50);
//                sp.setProduto(DaoProduto.getById(integer));
//                DaoSaidaProduto.merge(sp);
//            }
//        }
//    }
//
//    public static void cadastraProdutos() {
//        CPProduto p = new CPProduto();
//        // -------1----------------------
//        p.setMedida(950);
//        p.setNome("Alcool");
//        p.setQuantidadeEmStoque(1000);
//        p.setQuantidadeMinimaEmStoque(600);
//        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(5));
//        DaoProduto.merge(p);
//        // --------2---------------------
//        p.setMedida(1);
//        p.setNome("Papel");
//        p.setQuantidadeEmStoque(1000);
//        p.setQuantidadeMinimaEmStoque(600);
//        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(2));
//        DaoProduto.merge(p);
//        // ---------3--------------------
//        p.setMedida(1.5);
//        p.setNome("Gel de ultra som");
//        p.setQuantidadeEmStoque(1000);
//        p.setQuantidadeMinimaEmStoque(600);
//        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(5));
//        DaoProduto.merge(p);
//        // ----------4-------------------
//        p.setMedida(50);
//        p.setNome("Caixa de luvas");
//        p.setQuantidadeEmStoque(1000);
//        p.setQuantidadeMinimaEmStoque(600);
//        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(4));
//        DaoProduto.merge(p);
//        // -----------5------------------
//        p.setMedida(50);
//        p.setNome("Caixa de máscaras");
//        p.setQuantidadeEmStoque(1000);
//        p.setQuantidadeMinimaEmStoque(600);
//        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(4));
//        DaoProduto.merge(p);
//        // -----------5------------------
//        p.setMedida(150);
//        p.setNome("Mercuruio");
//        p.setQuantidadeEmStoque(1000);
//        p.setQuantidadeMinimaEmStoque(600);
//        p.setUnidadeDeMedida(DaoUnidadeDeMedida.getById(1));
//        DaoProduto.merge(p);
//    }
//
//    public static void cadastraUnidades() {
//        CPUnidadeDeMedida u = new CPUnidadeDeMedida();
//        // ----------1-------------------
//        u.setNome("Mililitro");
//        u.setAbreviacao("ml");
//        DaoUnidadeDeMedida.merge(u);
//        // -----------2------------------
//        u.setNome("Resma");
//        u.setAbreviacao("Res.");
//        DaoUnidadeDeMedida.merge(u);
//        // ------------3-----------------
//        u.setNome("Kilograma");
//        u.setAbreviacao("Kg");
//        DaoUnidadeDeMedida.merge(u);
//        // -------------4----------------
//        u.setNome("Unidade");
//        u.setAbreviacao("Uni.");
//        DaoUnidadeDeMedida.merge(u);
//        // --------------5---------------
//        u.setNome("Litro");
//        u.setAbreviacao("L");
//        DaoUnidadeDeMedida.merge(u);
//    }
//
//    public static void cadastraPonto() {
//        CPPontoDeEstagio pe = new CPPontoDeEstagio();
//        pe.setNome("Hospital Maldito");
//        DaoPontoDeEstagio.merge(pe);
//
//        pe.setNome("Clínica Sombria");
//        DaoPontoDeEstagio.merge(pe);
//
//        pe.setNome("Psiquiatria na Mansão");
//        DaoPontoDeEstagio.merge(pe);
//
//        pe.setNome("Morte Lenta");
//        DaoPontoDeEstagio.merge(pe);
//
//        pe.setNome("Chamem o Doutor!");
//        DaoPontoDeEstagio.merge(pe);
//    }
//
//    public static void cadastraProfessor() {
//        CPProfessor p = new CPProfessor();
//        p.setContaAtiva(true);
//        p.setEmailPrincipal("professormaldito@f3soft.net");
//        p.setEmailSecundario("professormaldito2@f3soft.net");
//        p.setLogin("professormaldito");
//        p.setNomeCompleto("Professor Maldito das Cruzes");
//        p.setPrimeiroNome("Professor");
//        p.setSegundoNome("Maldito");
//        p.setSenha("malditoprofessor");
//        p.setTentativasLogin(0);
//
//        DaoGenericoUsuario.merge(p);
//
//        // -----------------------
//        p.setContaAtiva(true);
//        p.setEmailPrincipal("professorsombrio@f3soft.net");
//        p.setEmailSecundario("professorsombrio2@f3soft.net");
//        p.setLogin("professorsombrio");
//        p.setNomeCompleto("Professor Sombrio de Algoz");
//        p.setPrimeiroNome("Professor");
//        p.setSegundoNome("Sombrio");
//        p.setSenha("sombrioprofessor");
//        p.setTentativasLogin(0);
//
//        DaoGenericoUsuario.merge(p);
//
//        // -----------------------
//        p.setContaAtiva(true);
//        p.setEmailPrincipal("professormansao@f3soft.net");
//        p.setEmailSecundario("professormansao2@f3soft.net");
//        p.setLogin("professormansao");
//        p.setNomeCompleto("Professor Mansão de Valviera");
//        p.setPrimeiroNome("Professor");
//        p.setSegundoNome("Mansão");
//        p.setSenha("mansaoprofessor");
//        p.setTentativasLogin(0);
//
//        DaoGenericoUsuario.merge(p);
//
//        // -----------------------
//        p.setContaAtiva(true);
//        p.setEmailPrincipal("professorlento@f3soft.net");
//        p.setEmailSecundario("professorlento2@f3soft.net");
//        p.setLogin("professorlento");
//        p.setNomeCompleto("Professor Lento de Muniz");
//        p.setPrimeiroNome("Professor");
//        p.setSegundoNome("Lento");
//        p.setSenha("lentoprofessor");
//        p.setTentativasLogin(0);
//
//        DaoGenericoUsuario.merge(p);
//
//        // -----------------------
//        p.setContaAtiva(true);
//        p.setEmailPrincipal("professordoutor@f3soft.net");
//        p.setEmailSecundario("professordoutor2@f3soft.net");
//        p.setLogin("professordoutor");
//        p.setNomeCompleto("Professor Doutor de Oliveira");
//        p.setPrimeiroNome("Professor");
//        p.setSegundoNome("Doutor");
//        p.setSenha("doutorprofessor");
//        p.setTentativasLogin(0);
//
//        DaoGenericoUsuario.merge(p);
//
//    }
//
//    @Deprecated
//    public static void ligaProfessorATurma() {
//        /*
//         * CPTurma p = DaoTurma.getByID(1l);
//         * p.setProfessor(DaoGenericoUsuario.getByIDProfessor(1l));
//         * DaoTurma.merge(p);
//         *
//         * p = DaoTurma.getByID(2l);
//         * p.setProfessor(DaoGenericoUsuario.getByIDProfessor(2l));
//         * DaoTurma.merge(p);
//         *
//         * p = DaoTurma.getByID(3l);
//         * p.setProfessor(DaoGenericoUsuario.getByIDProfessor(3l));
//         * DaoTurma.merge(p);
//         *
//         * p = DaoTurma.getByID(4l);
//         * p.setProfessor(DaoGenericoUsuario.getByIDProfessor(4l));
//         * DaoTurma.merge(p);
//         *
//         * p = DaoTurma.getByID(5l);
//         * p.setProfessor(DaoGenericoUsuario.getByIDProfessor(5l));
//         * DaoTurma.merge(p);
//         *
//         */
//    }
//
//    public static void cadastraAluno() {
//        CPAluno a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno1@f3soft.net");
//        a.setEmailSecundario("aluno01@f3soft.net");
//        a.setLogin("aluno1");
//        a.setNomeCompleto("Aluno Um de Oliveira");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Um");
//        a.setSenha("aluno1");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//        // --------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno2@f3soft.net");
//        a.setEmailSecundario("aluno02@f3soft.net");
//        a.setLogin("aluno2");
//        a.setNomeCompleto("Aluno Dois de Souza");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Dois");
//        a.setSenha("aluno2");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno3@f3soft.net");
//        a.setEmailSecundario("aluno03@f3soft.net");
//        a.setLogin("aluno3");
//        a.setNomeCompleto("Aluno Três de Oliveira");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Três");
//        a.setSenha("aluno3");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno4@f3soft.net");
//        a.setEmailSecundario("aluno04@f3soft.net");
//        a.setLogin("aluno4");
//        a.setNomeCompleto("Aluno Quatro Cruz");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Quatro");
//        a.setSenha("aluno4");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno5@f3soft.net");
//        a.setEmailSecundario("aluno05@f3soft.net");
//        a.setLogin("aluno5");
//        a.setNomeCompleto("Aluno Cinco Souza");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Cinco");
//        a.setSenha("aluno5");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno1@f3soft.net");
//        a.setEmailSecundario("aluno01@f3soft.net");
//        a.setLogin("aluno6");
//        a.setNomeCompleto("Aluno Seis de Oliveira");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Seis");
//        a.setSenha("aluno6");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno7@f3soft.net");
//        a.setEmailSecundario("aluno07@f3soft.net");
//        a.setLogin("aluno7");
//        a.setNomeCompleto("Aluno Sete Rodrigues");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Sete");
//        a.setSenha("aluno7");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno8@f3soft.net");
//        a.setEmailSecundario("aluno08@f3soft.net");
//        a.setLogin("aluno8");
//        a.setNomeCompleto("Aluno Oito Levita");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Oito");
//        a.setSenha("aluno8");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno9@f3soft.net");
//        a.setEmailSecundario("aluno09@f3soft.net");
//        a.setLogin("aluno9");
//        a.setNomeCompleto("Aluno Nove Neto");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Nove");
//        a.setSenha("aluno9");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//
//        // ---------------------------------------
//        a = new CPAluno();
//        a.setContaAtiva(true);
//        a.setEmailPrincipal("aluno10@f3soft.net");
//        a.setEmailSecundario("aluno010@f3soft.net");
//        a.setLogin("aluno10");
//        a.setNomeCompleto("Aluno Dez Combis");
//        a.setPrimeiroNome("Aluno");
//        a.setSegundoNome("Dez");
//        a.setSenha("alunoz");
//        a.setTentativasLogin(0);
//        DaoGenericoUsuario.merge(a);
//    }
//
//    public static void cadastraTurma() {
//        CPTurma t = new CPTurma();
//        t.setCodigoTurma("Turma Amaldiçoada");
//        t.setDataInicio(new Timestamp(UtilData.converteEmDate("02/05/2008").getTime()));
//        t.setDataTermino(new Timestamp(UtilData.converteEmDate("02/05/2012").getTime()));
//        t.setStatus(true);
//        DaoTurma.merge(t);
//        // -----------------------
//        t = new CPTurma();
//        t.setCodigoTurma("Turma Sombria");
//        t.setDataInicio(new Timestamp(UtilData.converteEmDate("02/05/2008").getTime()));
//        t.setDataTermino(new Timestamp(UtilData.converteEmDate("02/05/2012").getTime()));
//        t.setStatus(true);
//        DaoTurma.merge(t);
//        // -----------------------
//        t = new CPTurma();
//        t.setCodigoTurma("Turma da Mansão");
//        t.setDataInicio(new Timestamp(UtilData.converteEmDate("02/05/2008").getTime()));
//        t.setDataTermino(new Timestamp(UtilData.converteEmDate("02/05/2012").getTime()));
//        t.setStatus(true);
//        DaoTurma.merge(t);
//        // -----------------------
//        t = new CPTurma();
//        t.setCodigoTurma("Turma Lenta");
//        t.setDataInicio(new Timestamp(UtilData.converteEmDate("02/05/2008").getTime()));
//        t.setDataTermino(new Timestamp(UtilData.converteEmDate("02/05/2012").getTime()));
//        t.setStatus(true);
//        DaoTurma.merge(t);
//        // -----------------------
//        t = new CPTurma();
//        t.setCodigoTurma("Turma de Doutores");
//        t.setDataInicio(new Timestamp(UtilData.converteEmDate("02/05/2008").getTime()));
//        t.setDataTermino(new Timestamp(UtilData.converteEmDate("02/05/2012").getTime()));
//        t.setStatus(true);
//        DaoTurma.merge(t);
//    }
//
//    public static void ligaAlunoATurma() {
//        List<CPAluno> alunos = new LinkedList<CPAluno>();
//        alunos.add(DaoGenericoUsuario.getByIDAluno(1l));
//        alunos.add(DaoGenericoUsuario.getByIDAluno(2l));
//        DaoTurma.ligaAlunosATurma(alunos, DaoTurma.getByID(1l));
//
//        // ----------------------
//        alunos = new LinkedList<CPAluno>();
//        alunos.add(DaoGenericoUsuario.getByIDAluno(3l));
//        alunos.add(DaoGenericoUsuario.getByIDAluno(4l));
//        DaoTurma.ligaAlunosATurma(alunos, DaoTurma.getByID(2l));
//
//        // ----------------------
//        alunos = new LinkedList<CPAluno>();
//        alunos.add(DaoGenericoUsuario.getByIDAluno(5l));
//        alunos.add(DaoGenericoUsuario.getByIDAluno(6l));
//        DaoTurma.ligaAlunosATurma(alunos, DaoTurma.getByID(3l));
//
//        // ----------------------
//        alunos = new LinkedList<CPAluno>();
//        alunos.add(DaoGenericoUsuario.getByIDAluno(7l));
//        alunos.add(DaoGenericoUsuario.getByIDAluno(8l));
//        DaoTurma.ligaAlunosATurma(alunos, DaoTurma.getByID(4l));
//
//        // ----------------------
//        alunos = new LinkedList<CPAluno>();
//        alunos.add(DaoGenericoUsuario.getByIDAluno(9l));
//        alunos.add(DaoGenericoUsuario.getByIDAluno(10l));
//        DaoTurma.ligaAlunosATurma(alunos, DaoTurma.getByID(5l));
//    }
//
//    @Deprecated
//    public static void ligaTurmaAPonto() {
//        /*
//         * CPPontoDeEstagio pe = DaoPontoDeEstagio.getById(1l);
//         * pe.setTurma(DaoTurma.getByID(1l)); DaoPontoDeEstagio.merge(pe);
//         *
//         * pe = DaoPontoDeEstagio.getById(2l);
//         * pe.setTurma(DaoTurma.getByID(2l)); DaoPontoDeEstagio.merge(pe);
//         *
//         * pe = DaoPontoDeEstagio.getById(3l);
//         * pe.setTurma(DaoTurma.getByID(3l)); DaoPontoDeEstagio.merge(pe);
//         *
//         * pe = DaoPontoDeEstagio.getById(4l);
//         * pe.setTurma(DaoTurma.getByID(4l)); DaoPontoDeEstagio.merge(pe);
//         *
//         * pe = DaoPontoDeEstagio.getById(5l);
//         * pe.setTurma(DaoTurma.getByID(5l)); DaoPontoDeEstagio.merge(pe);
//         *
//         */
//    }
//
//    public static void geraBanco() {
//        GeraBanco.faz();
//    }
}
