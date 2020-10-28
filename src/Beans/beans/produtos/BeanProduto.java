package beans.produtos;

import beans.listas.BeanListProdutos;
import beans.listas.BeanListUnidades;
import cp.estoque.CPAlertasQuantidadeProduto;
import cp.estoque.CPProduto;
import cp.estoque.CPUnidadeDeMedida;
import cp.estoque.entrada.CPEntradaProduto;
import cp.estoque.saida.CPSaidaProduto;
import cp.grafico.entrada.custom.CPGraficoEntradaCustomProdutos;
import cp.grafico.entrada.preDeterminado.CPGraficoEntradaPreDeterminadoProdutos;
import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustomProdutos;
import cp.grafico.entradasaida.preDeterminado.CPGraficoEntradaSaidaPreDeterminadoProdutos;
import cp.grafico.saida.custom.CPGraficoSaidaCustomProdutos;
import cp.grafico.saida.preDeterminado.CPGraficoSaidaPreDeterminadoProdutos;
import dao.estoque.DaoAlertasQuantidadeProduto;
import dao.estoque.DaoProduto;
import dao.estoque.DaoUnidadeDeMedida;
import dao.estoque.entrada.DaoEntradaProduto;
import dao.estoque.saida.DaoSaidaProduto;
import dao.grafico.entrada.custom.DaoGraficoEntradaCustomProdutos;
import dao.grafico.entrada.preDeterminada.DaoGraficoEntradaPreDeterminadaProdutos;
import dao.grafico.entradaSaida.custom.DaoGraficoEntradaSaidaCustomProdutos;
import dao.grafico.entradaSaida.preDeterminada.DaoGraficoEntradaSaidaPreDeterminadoProdutos;
import dao.grafico.saida.custom.DaoGraficoSaidaCustomProdutos;
import dao.grafico.saida.predeterminada.DaoGraficoSaidaPredeterminadaProdutos;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.mensagens.UtilMensagens;

@ManagedBean(name = "prod")
@ViewScoped
public class BeanProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> tiposDeBuscarPorNome = new LinkedList<>();
    private int posTiposDeBuscarPorNome = 0;
    //#DELETAR 0 - simples | 1 - completa 
    private String deletarCompletamente = "0";
    //#MEDIDA
    private double medidaMenor;
    private double medidaMaior;
    //#QUANIDADE
    private int quantidadeMenor;
    private int quantidadeMaior;
    private String operadorLogicoDaMedida = "e";
    private String operadorLogicoDaQuantidade = "e";
    private String resultadoTextualBusca = "";

    //REVISTO
    public void updateProduto(CPUnidadeDeMedida unidade, BeanListProdutos lp) {
        lp.getProdutoManipulado().setUnidadeDeMedida(unidade);
        lp.getProdutoManipulado().setId(lp.getProdutoManipulado().getId());
        lp.removeProdIndexDaListProd();
        lp.addProdutoNaLista(lp.getProdutoManipulado());
    }

    public void mergeUnidade(BeanListUnidades u) {
        DaoUnidadeDeMedida.merge(u.getUnidade());
        UtilMensagens.ok(UtilMensagens.unidadeRegistrada);
        u.setUnidade(new CPUnidadeDeMedida());
    }

    public void pesquisaAvancada(BeanListProdutos lp, BeanListUnidades lu) {
        resultadoTextualBusca = "Buscando por";
        lp.zeraManipulados();
        for (CPProduto produtoDaVez : lp.getProdutos()) {

            /*
             * lógica do validador: pode usar ou não um filtro e se usado, pode
             * excluir ou incluir um produto se não se usa o filtro = 0 se usa o
             * filtro e inclui = 1 se usa o filtro e exclui = 2
             */

            //validando pelo nome
            boolean validaPeloNome = true;
            if (!lp.getProdutoManipulado().getNome().equals("")) {
                switch (posTiposDeBuscarPorNome) {
                    case 0:
                        if (!produtoDaVez.getNome().toLowerCase().startsWith(lp.getProdutoManipulado().getNome().toLowerCase())) {
                            validaPeloNome = false;
                        }
                        break;
                    case 1:
                        if (!produtoDaVez.getNome().toLowerCase().contains(lp.getProdutoManipulado().getNome().toLowerCase())) {
                            validaPeloNome = false;
                        }
                        break;
                    case 2:
                        if (!produtoDaVez.getNome().toLowerCase().endsWith(lp.getProdutoManipulado().getNome().toLowerCase())) {
                            validaPeloNome = false;
                        }
                        break;
                }
            }

            //validando pela unidade
            boolean validaPelaUnidade = false;
            if (lu.getIndexSListaUnidade().isEmpty()) {//há algum validador de unidade?
                validaPelaUnidade = true;
            } else if (lu.getIndexSListaUnidade().contains("qualquer")) {//entre eles há o validador "qualquer?"
                validaPelaUnidade = true;
            } else if (lu.getIdUnidadesIndexadas().contains(produtoDaVez.getId())) {//confere se  o ID da unidade do produto da vez está entre os validadores
                validaPelaUnidade = true;
            }

            //Validadando pela medida
            boolean validaPelaMedida = true;
            if (medidaMaior != 0 && medidaMenor != 0) {
                if (operadorLogicoDaMedida.equals("e")) {
                    if (!(produtoDaVez.getMedida() > medidaMaior && produtoDaVez.getMedida() < medidaMenor)) {
                        validaPelaMedida = false;
                    }
                } else {
                    if (!(produtoDaVez.getMedida() > medidaMaior || produtoDaVez.getMedida() < medidaMenor)) {
                        validaPelaMedida = false;
                    }
                }
            } else {
                if (medidaMaior != 0) {
                    if (!(produtoDaVez.getMedida() > medidaMaior)) {
                        validaPelaMedida = false;
                    }
                }
                if (medidaMenor != 0) {
                    if (!(produtoDaVez.getMedida() < medidaMenor)) {
                        validaPelaMedida = false;
                    }
                }
            }

            //validando pela quantidade
            boolean validaPelaQuantidade = true;
            if (quantidadeMaior != 0 && quantidadeMenor != 0) {
                if (operadorLogicoDaMedida.equals("e")) {
                    if (!(produtoDaVez.getQuantidadeEmStoque() > quantidadeMaior && produtoDaVez.getQuantidadeEmStoque() < quantidadeMenor)) {
                        validaPelaQuantidade = false;
                    }
                } else {
                    if (!(produtoDaVez.getQuantidadeEmStoque() > quantidadeMaior || produtoDaVez.getQuantidadeEmStoque() < quantidadeMenor)) {
                        validaPelaQuantidade = false;
                    }
                }
            } else {
                if (quantidadeMaior != 0) {
                    if (!(produtoDaVez.getQuantidadeEmStoque() > quantidadeMaior)) {
                        validaPelaQuantidade = false;
                    }
                }
                if (quantidadeMenor != 0) {
                    if (!(produtoDaVez.getQuantidadeEmStoque() < quantidadeMenor)) {
                        validaPelaQuantidade = false;
                    }
                }
            }

            //confere validadores
            if (validaPeloNome && validaPelaUnidade && validaPelaMedida && validaPelaQuantidade) {
                lp.addProdutoNaListaManipulada(produtoDaVez);
            }
        }
        //escrevendo consulta textutual
        //nome
        if (!lp.getProdutoManipulado().getNome().equals("")) {
            resultadoTextualBusca += " produtos que";
            switch (posTiposDeBuscarPorNome) {
                case 0:
                    resultadoTextualBusca += " iniciam seus nomes com \"" + lp.getProdutoManipulado().getNome() + "\"";
                    break;
                case 1:
                    resultadoTextualBusca += " possuam em seus nomes: \"" + lp.getProdutoManipulado().getNome() + "\"";
                    break;
                case 2:
                    resultadoTextualBusca += " terminem seus nomes com \"" + lp.getProdutoManipulado().getNome() + "\"";
                    break;
            }
        } else {
            resultadoTextualBusca += " produtos com qualquer nome";
        }

        //unidade
        //se há mais de um selecionado
        resultadoTextualBusca += " que a unidade seja";
        if (lu.getIndexSListaUnidade().size() > 0) {
            //se um dos selecionado é qualquer
            if (!lu.getIndexSListaUnidade().contains("qualquer")) {
                //se há mais de um selecionado
                if (lu.getIndexSListaUnidade().size() > 1) {

                    for (String index : lu.getIndexSListaUnidade()) {
                        if (lu.getIndexSListaUnidade().indexOf(index) == lu.getIndexSListaUnidade().size() - 1) {
                            resultadoTextualBusca += " \"" + lu.getUnidades().get(Integer.parseInt(index)).getNome() + "\"";
                        } else {
                            resultadoTextualBusca += " \"" + lu.getUnidades().get(Integer.parseInt(index)).getNome() + "\", ou que seja";
                        }
                    }
                } else {
                    resultadoTextualBusca += " \"" + lu.getUnidades().get(Integer.parseInt(lu.getIndexSListaUnidade().get(0))).getNome() + "\"";
                }
            } else {
                resultadoTextualBusca += " qualquer uma";
            }
        } else {
            resultadoTextualBusca += " qualquer uma";
        }

        //medida
        resultadoTextualBusca += ", que a medida seja";
        if (medidaMaior != 0 && medidaMenor != 0) {
            if (operadorLogicoDaMedida.equals("e")) {
                resultadoTextualBusca += " maior que: " + medidaMaior + " e menor que: " + medidaMenor;
            } else {
                resultadoTextualBusca += " maior que: " + medidaMaior + " ou menor que: " + medidaMenor;
            }
        } else {
            if (medidaMaior != 0) {
                resultadoTextualBusca += " maior que: " + medidaMaior + "";
            } else {
                if (medidaMenor != 0) {
                    resultadoTextualBusca += " menor que: " + medidaMenor + "";
                } else {
                    resultadoTextualBusca += " qualquer uma";
                }
            }
        }

        //quantidade
        resultadoTextualBusca += ", que a quantidade seja";
        //se há algo em ambos campos
        if (quantidadeMaior != 0 && quantidadeMenor != 0) {
            // se o operador seja E
            if (operadorLogicoDaQuantidade.equals("e")) {
                resultadoTextualBusca += " maior que: " + quantidadeMaior + " e menor que: " + quantidadeMenor;
            } else {
                resultadoTextualBusca += " maior que: " + quantidadeMaior + " ou menor que: " + quantidadeMenor;
            }
        } else {
            if (quantidadeMaior != 0) {
                resultadoTextualBusca += " maior que: " + quantidadeMaior + "";
            } else {
                if (quantidadeMenor != 0) {
                    resultadoTextualBusca += " menor que: " + quantidadeMenor + "";
                } else {
                    resultadoTextualBusca += " qualquer uma";
                }
            }
        }
        UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", lp.getProdutosManipulados().size() + " resultados."));
    }

    public void pesquisaSimples(BeanListProdutos lp) {
        lp.zeraManipulados();
        for (CPProduto p : lp.getProdutos()) {
            if (p.getNome().toLowerCase().startsWith(lp.getProdutoManipulado().getNome().toLowerCase())) {
                lp.getProdutosManipulados().add(p);
            }
        }
        if (lp.getProdutosManipulados().isEmpty()) {
            UtilMensagens.alerta(UtilMensagens.buscaVazia);
        } else {
            UtilMensagens.ok(UtilMensagens.buscaRealizada.replace("VR1", lp.getProdutosManipulados().size() + " resultados."));
        }
    }

    public void mergProduto(BeanListProdutos lp, BeanListUnidades lu) {
        lp.getProdutoManipulado().setUnidadeDeMedida(lu.unidadeManipuladaPos());
        DaoProduto.merge(lp.getProdutoManipulado());
        lp.setProdutoManipulado(new CPProduto());
        lu.setPosManipulado(-1);
        UtilMensagens.ok(UtilMensagens.produtoCadastrado);
    }

    public void deletaProduto(BeanListProdutos lp) {

        //CPProduto está relacionado com outras 7 (SEIS) tabelas


        //# CPSaidaProduto
        List<CPSaidaProduto> buscaPorProdutos = DaoSaidaProduto.buscaPorProdutos(lp.getProdutoManipulado());
        for (CPSaidaProduto sp : buscaPorProdutos) {
            DaoSaidaProduto.delete(sp);
        }

        System.out.println("deletou CPSaidaProduto " + buscaPorProdutos.size());

        //# CPEntradaProduto
        List<CPEntradaProduto> buscaPorProduto = DaoEntradaProduto.buscaPorProduto(lp.getProdutoManipulado());
        for (CPEntradaProduto ep : buscaPorProduto) {
            DaoEntradaProduto.delete(ep);
        }

        System.out.println("deletou CPEntradaProduto " + buscaPorProduto.size());

        //# CPGraficoSaidaCustomProdutos
        //# CPGraficoSaidaPreDeterminadoProdutos
        List<CPGraficoSaidaCustomProdutos> porProdutos = new DaoGraficoSaidaCustomProdutos().getPorProdutos(lp.getProdutoManipulado());
        List<CPGraficoSaidaPreDeterminadoProdutos> porProdutos1 = new DaoGraficoSaidaPredeterminadaProdutos().getPorProdutos(lp.getProdutoManipulado());

        for (CPGraficoSaidaPreDeterminadoProdutos cpgspdp : porProdutos1) {
            new DaoGraficoSaidaPredeterminadaProdutos().deleta(cpgspdp);
        }

        System.out.println("deletou CPGraficoSaidaPreDeterminadoProdutos " + porProdutos1.size());

        for (CPGraficoSaidaCustomProdutos xx : porProdutos) {
            new DaoGraficoSaidaCustomProdutos().deleta(xx);
        }

        System.out.println("deletou CPGraficoSaidaCustomProdutos " + porProdutos.size());


        //# CPGraficoEntradaPreDeterminadoProdutos
        //# CPGraficoEntradaCustomProdutos
        List<CPGraficoEntradaCustomProdutos> porProdutos2 = new DaoGraficoEntradaCustomProdutos().getPorProdutos(lp.getProdutoManipulado());
        for (CPGraficoEntradaCustomProdutos xx : porProdutos2) {
            new DaoGraficoEntradaCustomProdutos().deleta(xx);
        }

        System.out.println("deletou CPGraficoEntradaCustomProdutos " + porProdutos2.size());


        List<CPGraficoEntradaPreDeterminadoProdutos> porProdutos3 = new DaoGraficoEntradaPreDeterminadaProdutos().getPorProdutos(lp.getProdutoManipulado());
        for (CPGraficoEntradaPreDeterminadoProdutos xx : porProdutos3) {
            new DaoGraficoEntradaPreDeterminadaProdutos().deleta(xx);
        }
        System.out.println("deletou CPGraficoEntradaPreDeterminadoProdutos " + porProdutos3.size());

        //# entrada saida custom e pre
        List<CPGraficoEntradaSaidaCustomProdutos> porProdutos4 = new DaoGraficoEntradaSaidaCustomProdutos().getPorProdutos(lp.getProdutoManipulado());
        List<CPGraficoEntradaSaidaPreDeterminadoProdutos> porProdutos5 = new DaoGraficoEntradaSaidaPreDeterminadoProdutos().getPorProdutos(lp.getProdutoManipulado());
        for (CPGraficoEntradaSaidaCustomProdutos xx : porProdutos4) {
            new DaoGraficoEntradaSaidaCustomProdutos().deleta(xx);
        }

        System.out.println("deletou CPGraficoEntradaSaidaCustomProdutos " + porProdutos4.size());

        for (CPGraficoEntradaSaidaPreDeterminadoProdutos xx : porProdutos5) {
            new DaoGraficoEntradaSaidaPreDeterminadoProdutos().deleta(xx);
        }
        System.out.println("deletou CPGraficoEntradaSaidaPreDeterminadoProdutos " + porProdutos5.size());
        //alertas
        List<CPAlertasQuantidadeProduto> porProduto = DaoAlertasQuantidadeProduto.getPorProdutoAll(lp.getProdutoManipulado());
        for (CPAlertasQuantidadeProduto xx : porProduto) {
            DaoAlertasQuantidadeProduto.deleta(xx);
        }
        System.out.println("deletou CPAlertasQuantidadeProduto " + porProduto.size());

        DaoProduto.deleta(lp.getProdutoManipulado());
        UtilMensagens.ok(UtilMensagens.produtoRemovido);

    }

    public void updateProduto(BeanListProdutos p, BeanListUnidades u) {
        if (!(p.getProdutoManipulado().getUnidadeDeMedida().getId() == u.unidadeReferenteAoId().getId())) {
            p.getProdutoManipulado().setUnidadeDeMedida(u.unidadeReferenteAoId());
        }
        DaoProduto.merge(p.getProdutoManipulado());
        p.atualizaProdutoNaLista(p.getProdutoManipulado());
        UtilMensagens.ok(UtilMensagens.produtoAtualizado);
    }

    //Get e Set
    public BeanProduto() {
        tiposDeBuscarPorNome.add("Inicie com");
        tiposDeBuscarPorNome.add("Contenha");
        tiposDeBuscarPorNome.add("Termine com");
    }

    public int getPosTiposDeBuscarPorNome() {
        return posTiposDeBuscarPorNome;
    }

    public void setPosTiposDeBuscarPorNome(int posTiposDeBuscarPorNome) {
        this.posTiposDeBuscarPorNome = posTiposDeBuscarPorNome;
    }

    public List<String> getTiposDeBuscarPorNome() {
        return tiposDeBuscarPorNome;
    }

    public void setTiposDeBuscarPorNome(List<String> tiposDeBuscarPorNome) {
        this.tiposDeBuscarPorNome = tiposDeBuscarPorNome;
    }

    public double getMedidaMaior() {
        return medidaMaior;
    }

    public void setMedidaMaior(double medidaMaior) {
        this.medidaMaior = medidaMaior;
    }

    public double getMedidaMenor() {
        return medidaMenor;
    }

    public void setMedidaMenor(double medidaMenor) {
        this.medidaMenor = medidaMenor;
    }

    public int getQuantidadeMaior() {
        return quantidadeMaior;
    }

    public void setQuantidadeMaior(int quantidadeMaior) {
        this.quantidadeMaior = quantidadeMaior;
    }

    public int getQuantidadeMenor() {
        return quantidadeMenor;
    }

    public void setQuantidadeMenor(int quantidadeMenor) {
        this.quantidadeMenor = quantidadeMenor;
    }

    public String getOperadorLogicoDaMedida() {
        return operadorLogicoDaMedida;
    }

    public void setOperadorLogicoDaMedida(String operadorLogicoDaMedida) {
        this.operadorLogicoDaMedida = operadorLogicoDaMedida;
    }

    public String getOperadorLogicoDaQuantidade() {
        return operadorLogicoDaQuantidade;
    }

    public void setOperadorLogicoDaQuantidade(String operadorLogicoDaQuantidade) {
        this.operadorLogicoDaQuantidade = operadorLogicoDaQuantidade;
    }

    public String getResultadoTextualBusca() {
        return resultadoTextualBusca;
    }

    public void setResultadoTextualBusca(String resultadoTextualBusca) {
        this.resultadoTextualBusca = resultadoTextualBusca;
    }

    public String getDeletarCompletamente() {
        return deletarCompletamente;
    }

    public void setDeletarCompletamente(String deletarCompletamente) {
        this.deletarCompletamente = deletarCompletamente;
    }
}