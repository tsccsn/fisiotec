/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package beans.listas.grafico;

import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
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
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago-Asus
 */
@ManagedBean(name = "listaDeGraficosSaidaSalvos")
@ViewScoped
public class BeanListGraficosSaidaSalvos implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ABSGraficoSaidaSalvo> graficosSaidaSalvos;
    private List<ABSGraficoSaidaSalvo> graficosSaidaSalvosManipulados;
    private ABSGraficoSaidaSalvo absGrafSaidaSalvo;
    private ABSGraficoSaidaSalvo absGrafSaidaSalvoPraSerDeletado;
    private ABSGraficoSaidaSalvo absGrafSaidaSalvoPraSerManipulado;
    private CPGraficoSaidaCustom grafSaidaCustom;
    private CPGraficoSaidaPreDeterminado grafSaidaPreDet;
    private String nome;
    // # 0 pre deter - 1 custom - 2 amobs - 
    private int exibePorTipo;

    public BeanListGraficosSaidaSalvos() {
        graficosSaidaSalvos = new LinkedList<>();
        for (CPGraficoSaidaPreDeterminado xPreDeterminado : DaoGraficoSaidaPredeterminada.getAllOrderByDataSalvo()) {
            graficosSaidaSalvos.add(xPreDeterminado);
        }
        for (CPGraficoSaidaCustom xCustom : DaoGraficoSaidaCustom.getAllOrderByDataSalvo()) {
            graficosSaidaSalvos.add(xCustom);
        }
        graficosSaidaSalvosManipulados = graficosSaidaSalvos;
        absGrafSaidaSalvo = ABSGraficoSaidaSalvo.getInstance();
        absGrafSaidaSalvoPraSerDeletado = ABSGraficoSaidaSalvo.getInstance();
        absGrafSaidaSalvoPraSerManipulado = ABSGraficoSaidaSalvo.getInstance();
        grafSaidaCustom = new CPGraficoSaidaCustom();
        grafSaidaPreDet = new CPGraficoSaidaPreDeterminado();
        String nome = "";
        // # 0 pre deter - 1 custom - 2 amobs - 
        int exibePorTipo = -1;
    }

    public void limpaSessaoGraficoSalvoManipulado() {
        absGrafSaidaSalvo.setId(0);
    }

    public ABSGraficoSaidaSalvo graficoManipulado(int tipoGraficop) {
        switch (tipoGraficop) {
            case 0://pre
                return grafSaidaPreDet;
            case 1://custom
                return grafSaidaCustom;
        }
        return null;
    }

    public void deletaGrafSaida() {
        if (graficosSaidaSalvos.contains(absGrafSaidaSalvoPraSerDeletado)) {
            graficosSaidaSalvos.remove(absGrafSaidaSalvoPraSerDeletado);
        }
        if (graficosSaidaSalvosManipulados.contains(absGrafSaidaSalvoPraSerDeletado)) {
            graficosSaidaSalvosManipulados.remove(absGrafSaidaSalvoPraSerDeletado);
        }

        if (absGrafSaidaSalvoPraSerDeletado.getId() == absGrafSaidaSalvoPraSerManipulado.getId() &&
                UtilString.getNomeClasse(absGrafSaidaSalvoPraSerManipulado).equals(UtilString.getNomeClasse(absGrafSaidaSalvoPraSerDeletado))) {
            absGrafSaidaSalvo.setId(0);
        }
        
        if (absGrafSaidaSalvoPraSerDeletado instanceof InterfaceGraficoCustom) {
            DaoGraficoSaidaCustom.delete((CPGraficoSaidaCustom) absGrafSaidaSalvoPraSerDeletado);
        } else {
            DaoGraficoSaidaPredeterminada.delete((CPGraficoSaidaPreDeterminado) absGrafSaidaSalvoPraSerDeletado);
        }
        UtilMensagens.ok(UtilMensagens.graficoDeletado);
    }

    public List<String> autoComplete(String s) {
        List<String> resultado = new LinkedList<>();
        for (ABSGraficoSaidaSalvo aBSGraficoSaidaSalvo : graficosSaidaSalvos) {
            if (aBSGraficoSaidaSalvo.getNome().toLowerCase().startsWith(s.toLowerCase())) {
                resultado.add(aBSGraficoSaidaSalvo.getNome());
            }
        }
        return resultado;
    }

    public void filtra(boolean exibeMensagem) {
        // por tipo
        switch (exibePorTipo) {
            case 0:
                graficosSaidaSalvosManipulados = graficosSaidaSalvos;
                break;
            case 1:
                graficosSaidaSalvosManipulados = new LinkedList<>();
                for (ABSGraficoSaidaSalvo aBSGraficoSaidaSalvo : graficosSaidaSalvos) {
                    if (aBSGraficoSaidaSalvo instanceof InterfaceGraficoPreDet) {
                        graficosSaidaSalvosManipulados.add(aBSGraficoSaidaSalvo);
                    }
                }
                break;
            case 2:
                graficosSaidaSalvosManipulados = new LinkedList<>();
                for (ABSGraficoSaidaSalvo aBSGraficoSaidaSalvo : graficosSaidaSalvos) {
                    if (aBSGraficoSaidaSalvo instanceof InterfaceGraficoCustom) {
                        graficosSaidaSalvosManipulados.add(aBSGraficoSaidaSalvo);
                    }
                }
                break;
            case 3:
                graficosSaidaSalvosManipulados = graficosSaidaSalvos;
                break;

        }
        //por nome
        if (!nome.isEmpty()) {
            List<ABSGraficoSaidaSalvo> resultado = new LinkedList<>();
            for (ABSGraficoSaidaSalvo aBSGraficoSaidaSalvo : graficosSaidaSalvosManipulados) {
                if (aBSGraficoSaidaSalvo.getNome().toLowerCase().contains(nome.toLowerCase())) {
                    resultado.add(aBSGraficoSaidaSalvo);
                }
            }
            graficosSaidaSalvosManipulados = resultado;
        }
        if (exibeMensagem) {
            UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", graficosSaidaSalvosManipulados.size() + ""));
        }
    }

    public List<CPPontoDeEstagio> listaPontosDeGraficoSalvo(ABSGraficoSaidaSalvo g) {
        if (g instanceof InterfaceGraficoCustom) {
            return new DaoGraficoSaidaCustomPontos().getPontos((CPGraficoSaidaCustom) g);
        } else if (g instanceof InterfaceGraficoPreDet) {
            return new DaoGraficoSaidaPredeterminadaPontos().getPontos((CPGraficoSaidaPreDeterminado) g);
        }
        return null;
    }

    public List<CPProduto> listaProdutosDeGraficoSalvo(ABSGraficoSaidaSalvo g) {
        if (g instanceof InterfaceGraficoCustom) {
            return new DaoGraficoSaidaCustomProdutos().getProdutos(g);
        } else if (g instanceof InterfaceGraficoPreDet) {
            return new DaoGraficoSaidaPredeterminadaProdutos().getProdutos((CPGraficoSaidaPreDeterminado) g);
        }
        return null;
    }

    public boolean confereSeABSGrafSaidaSalvoJahTahSalvo() {
        if (absGrafSaidaSalvo.getId()>0) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public void atualizaListaDeGraficosSaidaSalvo() {
        graficosSaidaSalvos = new LinkedList<>();
        for (CPGraficoSaidaPreDeterminado xPreDeterminado : DaoGraficoSaidaPredeterminada.getAllOrderByDataSalvo()) {
            graficosSaidaSalvos.add(xPreDeterminado);
        }
        for (CPGraficoSaidaCustom xCustom : DaoGraficoSaidaCustom.getAllOrderByDataSalvo()) {
            graficosSaidaSalvos.add(xCustom);
        }
        filtra(false);
    }

    public boolean confereSeOGraficoSalvoManipuladoFoiAleteradoDATA() {
        if (absGrafSaidaSalvo instanceof InterfaceGraficoCustom) {
            if (!UtilData.mesNanoN(absGrafSaidaSalvo.getDe().getTime()).equals(UtilData.mesNanoN(grafSaidaCustom.getDe().getTime()))
                    || !UtilData.mesNanoN(absGrafSaidaSalvo.getAte().getTime()).equals(UtilData.mesNanoN(grafSaidaCustom.getAte().getTime()))) {
                return true;
            }
        } else if (absGrafSaidaSalvo instanceof InterfaceGraficoPreDet) {
            if (!UtilData.mesNanoN(absGrafSaidaSalvo.getDe().getTime()).equals(UtilData.mesNanoN(grafSaidaPreDet.getDe().getTime()))
                    || !UtilData.mesNanoN(absGrafSaidaSalvo.getAte().getTime()).equals(UtilData.mesNanoN(grafSaidaPreDet.getAte().getTime()))) {
                return true;
            }
        }
        return false;
    }

    public boolean confereSeOGraficoSalvoManipuladoFoiAleteradoPRODUTOS(List<CPProduto> produtos) {
        //confere PRODUTOS
        if (listaProdutosDeGraficoSalvo(absGrafSaidaSalvo).size() != produtos.size()) {
            return true;
        } else {
            List<Long> idsProdutoGrafico = new LinkedList<>();
            List<Long> idsProduto = new LinkedList<>();
            for (CPProduto xProduto : listaProdutosDeGraficoSalvo(absGrafSaidaSalvo)) {
                idsProdutoGrafico.add(xProduto.getId());
            }
            for (CPProduto xProduto : produtos) {
                idsProduto.add(xProduto.getId());
            }
            if (!idsProduto.equals(idsProdutoGrafico)) {
                return true;
            }
        }
        return false;
    }

    public boolean confereSeOGraficoSalvoManipualdoFoiAletaradoPONTOS(List<CPPontoDeEstagio> pontos) {
        if (listaPontosDeGraficoSalvo(absGrafSaidaSalvo).size() != pontos.size()) {
            return true;
        } else {
            List<Long> idsPontosGrafico = new LinkedList<>();
            List<Long> idsPontos = new LinkedList<>();
            for (CPPontoDeEstagio xPonto : pontos) {
                idsPontos.add(xPonto.getId());
            }
            for (CPPontoDeEstagio xPonto : listaPontosDeGraficoSalvo(absGrafSaidaSalvo)) {
                idsPontosGrafico.add(xPonto.getId());
            }
            if (!idsPontos.equals(idsPontosGrafico)) {
                return true;
            }
            return false;
        }
    }

    //# GET E SET
    public List<ABSGraficoSaidaSalvo> getGraficosSaidaSalvos() {
        return graficosSaidaSalvos;
    }

    public void setGraficosSaidaSalvos(List<ABSGraficoSaidaSalvo> graficosSalvos) {
        this.graficosSaidaSalvos = graficosSalvos;
    }

    public CPGraficoSaidaCustom getGrafSaidaCustom() {
        return grafSaidaCustom;
    }

    public void setGrafSaidaCustom(CPGraficoSaidaCustom grafSaidaCustom) {
        this.grafSaidaCustom = grafSaidaCustom;
    }

    public CPGraficoSaidaPreDeterminado getGrafSaidaPreDet() {
        return grafSaidaPreDet;
    }

    public void setGrafSaidaPreDet(CPGraficoSaidaPreDeterminado grafSaidaPreDet) {
        this.grafSaidaPreDet = grafSaidaPreDet;
    }

    public ABSGraficoSaidaSalvo getAbsGrafSaidaSalvo() {
        return absGrafSaidaSalvo;
    }

    public void setAbsGrafSaidaSalvo(ABSGraficoSaidaSalvo absGrafSaidaSalvo) {
        this.absGrafSaidaSalvo = absGrafSaidaSalvo;
    }

    public void setAbsGrafSaidaSalvo2(ABSGraficoSaidaSalvo absGrafSaidaSalvo) {
        absGrafSaidaSalvoPraSerManipulado = absGrafSaidaSalvo;
        this.absGrafSaidaSalvo.setAgrupamento(absGrafSaidaSalvo.getAgrupamento());
        this.absGrafSaidaSalvo.setAte(absGrafSaidaSalvo.getAte());
        this.absGrafSaidaSalvo.setDe(absGrafSaidaSalvo.getDe());
        this.absGrafSaidaSalvo.setDataModificado(absGrafSaidaSalvo.getDataModificado());
        this.absGrafSaidaSalvo.setNome(absGrafSaidaSalvo.getNome());
        this.absGrafSaidaSalvo.setId(absGrafSaidaSalvo.getId());
        if (absGrafSaidaSalvo instanceof InterfaceGraficoCustom) {
            this.grafSaidaCustom.setAgrupamento(absGrafSaidaSalvo.getAgrupamento());
            this.grafSaidaCustom.setAte(absGrafSaidaSalvo.getAte());
            this.grafSaidaCustom.setDe(absGrafSaidaSalvo.getDe());
            this.grafSaidaCustom.setDataModificado(absGrafSaidaSalvo.getDataModificado());
            this.grafSaidaCustom.setNome(absGrafSaidaSalvo.getNome());
        } else if (absGrafSaidaSalvo instanceof InterfaceGraficoPreDet) {
            this.grafSaidaPreDet.setAgrupamento(absGrafSaidaSalvo.getAgrupamento());
            this.grafSaidaPreDet.setAte(absGrafSaidaSalvo.getAte());
            this.grafSaidaPreDet.setDe(absGrafSaidaSalvo.getDe());
            this.grafSaidaPreDet.setDataModificado(absGrafSaidaSalvo.getDataModificado());
            this.grafSaidaPreDet.setNome(absGrafSaidaSalvo.getNome());
        }
    }

    public List<ABSGraficoSaidaSalvo> getGraficosSaidaSalvosManipulados() {
        return graficosSaidaSalvosManipulados;
    }

    public int getExibePorTipo() {
        return exibePorTipo;
    }

    public void setExibePorTipo(int exibePorTipo) {
        this.exibePorTipo = exibePorTipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGraficosSaidaSalvosManipulados(List<ABSGraficoSaidaSalvo> graficosSaidaSalvosManipulados) {
        this.graficosSaidaSalvosManipulados = graficosSaidaSalvosManipulados;
    }

    @Deprecated
    public void setaBSGrafEntradaSalvo2(ABSGraficoSaidaSalvo absGrafSaidaSalvo) {
        if (absGrafSaidaSalvo instanceof InterfaceGraficoCustom) {
            this.absGrafSaidaSalvo = (CPGraficoSaidaCustom) absGrafSaidaSalvo;
        } else if (absGrafSaidaSalvo instanceof InterfaceGraficoPreDet) {
            this.absGrafSaidaSalvo = (CPGraficoSaidaPreDeterminado) absGrafSaidaSalvo;
        }
    }

    public ABSGraficoSaidaSalvo getAbsGrafSaidaSalvoPraSerDeletado() {
        return absGrafSaidaSalvoPraSerDeletado;
    }

    public void setAbsGrafSaidaSalvoPraSerDeletado(ABSGraficoSaidaSalvo absGrafSaidaSalvoPraSerDeletado) {
        this.absGrafSaidaSalvoPraSerDeletado = absGrafSaidaSalvoPraSerDeletado;
    }

    public ABSGraficoSaidaSalvo getAbsGrafSaidaSalvoPraSerManipulado() {
        return absGrafSaidaSalvoPraSerManipulado;
    }

    public void setAbsGrafSaidaSalvoPraSerManipulado(ABSGraficoSaidaSalvo absGrafSaidaSalvoPraSerManipulado) {
        this.absGrafSaidaSalvoPraSerManipulado = absGrafSaidaSalvoPraSerManipulado;
    }
}
