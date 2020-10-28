/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.listas.grafico;

import cp.estoque.CPProduto;
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
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "listaDeGraficosEntradaSalvos")
@ViewScoped
public class BeanListGraficosEntradaSalvos implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ABSGraficoEntradaSalvo> graficosEntradaSalvos;
    private List<ABSGraficoEntradaSalvo> graficosEntradaSalvosManipulados;
    private ABSGraficoEntradaSalvo absGrafEntradaSalvo;
    private ABSGraficoEntradaSalvo absGrafEntradaSalvoPraSerDeletado;
    private ABSGraficoEntradaSalvo absGrafEntradaSalvoManipulado;
    private CPGraficoEntradaCustom grafEntradaCustom;
    private CPGraficoEntradaPreDeterminado grafEntradaPreDet;
    private String nome = "";
    // # 0 pre deter - 1 custom - 2 amobs - 
    private int exibePorTipo = 0;

    public BeanListGraficosEntradaSalvos() {

        graficosEntradaSalvos = new LinkedList<>();
        for (CPGraficoEntradaCustom xCustom : DaoGraficoEntradaCustom.getAllOrderByDataSalvo()) {
            graficosEntradaSalvos.add(xCustom);
        }
        for (CPGraficoEntradaPreDeterminado xPreDeterminado : DaoGraficoEntradaPreDeterminada.getAllOrderByDataSalvo()) {
            graficosEntradaSalvos.add(xPreDeterminado);
        }
        graficosEntradaSalvosManipulados = graficosEntradaSalvos;
        absGrafEntradaSalvo = ABSGraficoEntradaSalvo.getInstance();
        absGrafEntradaSalvoPraSerDeletado = ABSGraficoEntradaSalvo.getInstance();
        absGrafEntradaSalvoManipulado = ABSGraficoEntradaSalvo.getInstance();
        grafEntradaCustom = new CPGraficoEntradaCustom();
        grafEntradaPreDet = new CPGraficoEntradaPreDeterminado();
        nome = "";
        // # 0 pre deter - 1 custom - 2 amobs - 
        exibePorTipo = 0;
    }

    @Deprecated
    public void atualizaListaDeGraficos() {
        graficosEntradaSalvos = new LinkedList<>();
        for (CPGraficoEntradaCustom xCustom : DaoGraficoEntradaCustom.getAllOrderByDataSalvo()) {
            graficosEntradaSalvos.add(xCustom);
        }
        for (CPGraficoEntradaPreDeterminado xPreDeterminado : DaoGraficoEntradaPreDeterminada.getAllOrderByDataSalvo()) {
            graficosEntradaSalvos.add(xPreDeterminado);
        }
        filtra(false);
    }

    public boolean confereSeABSGrafEntradaSalvoJahTahSalvo() {
        if (absGrafEntradaSalvo.getId() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void limpaSessaoGraficoSalvoManipulado() {
        absGrafEntradaSalvo.setId(0);
    }

    public List<String> autoComplete(String s) {
        List<String> resultado = new LinkedList<>();
        for (ABSGraficoEntradaSalvo xSalvo : graficosEntradaSalvos) {
            if (xSalvo.getNome().contains(nome) || nome.contains(xSalvo.getNome())) {
                resultado.add(xSalvo.getNome());
            }
        }
        return resultado;
    }

    public void filtra(boolean exibeMensagem) {
        graficosEntradaSalvosManipulados = new LinkedList<>();
        // por tipo
        switch (exibePorTipo) {
            case 0:
                graficosEntradaSalvosManipulados = graficosEntradaSalvos;
                break;
            case 1:
                for (ABSGraficoEntradaSalvo xSalvo : graficosEntradaSalvos) {
                    if (xSalvo instanceof InterfaceGraficoPreDet) {
                        graficosEntradaSalvosManipulados.add(xSalvo);
                    }
                }
                break;
            case 2:
                for (ABSGraficoEntradaSalvo xSalvo : graficosEntradaSalvos) {
                    if (xSalvo instanceof InterfaceGraficoPreDet) {
                        graficosEntradaSalvosManipulados.add(xSalvo);
                    }
                }
                break;
            case 3:
                graficosEntradaSalvosManipulados = graficosEntradaSalvos;
                break;

        }
        //por nome
        if (!nome.isEmpty()) {
            List<ABSGraficoEntradaSalvo> resultado = new LinkedList<>();
            for (ABSGraficoEntradaSalvo aBSGraficoSaidaSalvo : graficosEntradaSalvosManipulados) {
                if (aBSGraficoSaidaSalvo.getNome().toLowerCase().contains(nome.toLowerCase())) {
                    resultado.add(aBSGraficoSaidaSalvo);
                }
            }
            graficosEntradaSalvosManipulados = resultado;
        }
        if (exibeMensagem) {
            UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", graficosEntradaSalvosManipulados.size() + ""));
        }

    }

    public List<CPProduto> listaProdutosDeGraficoSalvo(ABSGraficoEntradaSalvo graficoEntradaSalvo) {
        if(graficoEntradaSalvo instanceof InterfaceGraficoCustom){
            return new DaoGraficoEntradaCustomProdutos().getProdutos(graficoEntradaSalvo);
        } else if (graficoEntradaSalvo instanceof InterfaceGraficoPreDet){
            return new DaoGraficoEntradaPreDeterminadaProdutos().getProdutos((CPGraficoEntradaPreDeterminado)graficoEntradaSalvo);
        }
        return null;
    }

    public void deletaGrafico() {
        if(absGrafEntradaSalvoPraSerDeletado instanceof InterfaceGraficoCustom){
            DaoGraficoEntradaCustom.delete((CPGraficoEntradaCustom)absGrafEntradaSalvoPraSerDeletado);
        } else if (absGrafEntradaSalvoPraSerDeletado instanceof InterfaceGraficoPreDet){
            DaoGraficoEntradaPreDeterminada.delete((CPGraficoEntradaPreDeterminado)absGrafEntradaSalvoPraSerDeletado);
        }
        graficosEntradaSalvos.remove(absGrafEntradaSalvoPraSerDeletado);
        graficosEntradaSalvosManipulados.remove(absGrafEntradaSalvoPraSerDeletado);
        absGrafEntradaSalvoPraSerDeletado = null;
        UtilMensagens.ok(UtilMensagens.graficoDeletado);
    }

    public ABSGraficoEntradaSalvo graficoManipulado(int tipoGraficop) {
        switch (tipoGraficop) {
            case 0://pre
                return grafEntradaPreDet;
            case 1://custom
                return grafEntradaCustom;
        }
        return null;
    }

    // # GET E SET
    public ABSGraficoEntradaSalvo getAbsGrafEntradaSalvo() {
        return absGrafEntradaSalvo;
    }

    public void setAbsGrafEntradaSalvo(ABSGraficoEntradaSalvo absGrafEntradaSalvo) {
        this.absGrafEntradaSalvo = absGrafEntradaSalvo;
    }

    public void setAbsGrafEntradaSalvo2(ABSGraficoEntradaSalvo absGrafEntradaSalvo) {
        absGrafEntradaSalvoManipulado = absGrafEntradaSalvo;
        this.absGrafEntradaSalvo.setAgrupamento(absGrafEntradaSalvo.getAgrupamento());
        this.absGrafEntradaSalvo.setAte(absGrafEntradaSalvo.getAte());
        this.absGrafEntradaSalvo.setDe(absGrafEntradaSalvo.getDe());
        this.absGrafEntradaSalvo.setDataModificado(absGrafEntradaSalvo.getDataModificado());
        this.absGrafEntradaSalvo.setNome(absGrafEntradaSalvo.getNome());
        this.absGrafEntradaSalvo.setId(absGrafEntradaSalvo.getId());
        if (absGrafEntradaSalvo instanceof InterfaceGraficoCustom) {
            this.grafEntradaCustom.setAgrupamento(absGrafEntradaSalvo.getAgrupamento());
            this.grafEntradaCustom.setAte(absGrafEntradaSalvo.getAte());
            this.grafEntradaCustom.setDe(absGrafEntradaSalvo.getDe());
            this.grafEntradaCustom.setDataModificado(absGrafEntradaSalvo.getDataModificado());
            this.grafEntradaCustom.setNome(absGrafEntradaSalvo.getNome());
        } else if (absGrafEntradaSalvo instanceof InterfaceGraficoPreDet) {
            this.grafEntradaPreDet.setAgrupamento(absGrafEntradaSalvo.getAgrupamento());
            this.grafEntradaPreDet.setAte(absGrafEntradaSalvo.getAte());
            this.grafEntradaPreDet.setDe(absGrafEntradaSalvo.getDe());
            this.grafEntradaPreDet.setDataModificado(absGrafEntradaSalvo.getDataModificado());
            this.grafEntradaPreDet.setNome(absGrafEntradaSalvo.getNome());
        }
    }

    public ABSGraficoEntradaSalvo getAbsGrafEntradaSalvoPraSerDeletado() {
        return absGrafEntradaSalvoPraSerDeletado;
    }

    public void setAbsGrafEntradaSalvoPraSerDeletado(ABSGraficoEntradaSalvo absGrafEntradaSalvoPraSerDeletado) {
        this.absGrafEntradaSalvoPraSerDeletado = absGrafEntradaSalvoPraSerDeletado;
    }

    public int getExibePorTipo() {
        return exibePorTipo;
    }

    public void setExibePorTipo(int exibePorTipo) {
        this.exibePorTipo = exibePorTipo;
    }

    public CPGraficoEntradaCustom getGrafEntradaCustom() {
        return grafEntradaCustom;
    }

    public void setGrafEntradaCustom(CPGraficoEntradaCustom grafEntradaCustom) {
        this.grafEntradaCustom = grafEntradaCustom;
    }

    public CPGraficoEntradaPreDeterminado getGrafEntradaPreDet() {
        return grafEntradaPreDet;
    }

    public void setGrafEntradaPreDet(CPGraficoEntradaPreDeterminado grafEntradaPreDet) {
        this.grafEntradaPreDet = grafEntradaPreDet;
    }

    public List<ABSGraficoEntradaSalvo> getGraficosEntradaSalvos() {
        return graficosEntradaSalvos;
    }

    public void setGraficosEntradaSalvos(List<ABSGraficoEntradaSalvo> graficosEntradaSalvos) {
        this.graficosEntradaSalvos = graficosEntradaSalvos;
    }

    public List<ABSGraficoEntradaSalvo> getGraficosEntradaSalvosManipulados() {
        return graficosEntradaSalvosManipulados;
    }

    public void setGraficosEntradaSalvosManipulados(List<ABSGraficoEntradaSalvo> graficosEntradaSalvosManipulados) {
        this.graficosEntradaSalvosManipulados = graficosEntradaSalvosManipulados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ABSGraficoEntradaSalvo getAbsGrafEntradaSalvoManipulado() {
        return absGrafEntradaSalvoManipulado;
    }

    public void setAbsGrafEntradaSalvoManipulado(ABSGraficoEntradaSalvo absGrafEntradaSalvoManipulado) {
        this.absGrafEntradaSalvoManipulado = absGrafEntradaSalvoManipulado;
    }
}
