/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.listas.grafico;

import cp.estoque.CPProduto;
import cp.grafico.InterfaceGraficoCustom;
import cp.grafico.InterfaceGraficoPreDet;
import cp.grafico.entradasaida.ABSGraficoEntradaSaida;
import cp.grafico.entradasaida.custom.CPGraficoEntradaSaidaCustom;
import cp.grafico.entradasaida.preDeterminado.CPGraficoEntradaSaidaPreDeterminado;
import dao.grafico.entradaSaida.custom.DaoGraficoEntradaSaidaCustom;
import dao.grafico.entradaSaida.custom.DaoGraficoEntradaSaidaCustomProdutos;
import dao.grafico.entradaSaida.preDeterminada.DaoGraficoEntradaSaidaPreDeterminado;
import dao.grafico.entradaSaida.preDeterminada.DaoGraficoEntradaSaidaPreDeterminadoProdutos;
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
 * @author Thiago
 */
@ManagedBean(name = "listaDeGraficosIO")
@ViewScoped
public class BeanListGraficosEntradaESaidaSalvos implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ABSGraficoEntradaSaida> graficosEntradaSaidaSalvos;
    private List<ABSGraficoEntradaSaida> graficosEntradaSaidaSalvosManipulados;
    private ABSGraficoEntradaSaida absGraficoEntradaSaidaSalvo;
    private ABSGraficoEntradaSaida absGraficoEntradaSaidaSalvoPraSerDeletado;
    private ABSGraficoEntradaSaida absGraficoEntradaSaidaPraSerManipulado;
    private CPGraficoEntradaSaidaCustom cpGraficoEntradaSaidaCustom;
    private CPGraficoEntradaSaidaPreDeterminado cpGraficoEntradaSaidaPreDeterminado;
    private String nome;
    // # 0 pre deter - 1 custom - 2 amobs - 
    private int exibePorTipo;

    public void setaHoje(){
        cpGraficoEntradaSaidaCustom.setAte(UtilData.getDataTimestamp());
        cpGraficoEntradaSaidaPreDeterminado.setAte(UtilData.getDataTimestamp());
    }
    
    public BeanListGraficosEntradaESaidaSalvos() {
        graficosEntradaSaidaSalvos = new LinkedList<>();
        for (CPGraficoEntradaSaidaPreDeterminado xPreDeterminado : DaoGraficoEntradaSaidaPreDeterminado.getAllOrderByDataSalvo()) {
            graficosEntradaSaidaSalvos.add(xPreDeterminado);
        }
        for (CPGraficoEntradaSaidaCustom xCustom : DaoGraficoEntradaSaidaCustom.getAllOrderByDataSalvo()) {
            graficosEntradaSaidaSalvos.add(xCustom);
        }
        graficosEntradaSaidaSalvosManipulados = graficosEntradaSaidaSalvos;
        absGraficoEntradaSaidaSalvo = ABSGraficoEntradaSaida.getInstance();
        absGraficoEntradaSaidaSalvoPraSerDeletado = ABSGraficoEntradaSaida.getInstance();
        absGraficoEntradaSaidaPraSerManipulado = ABSGraficoEntradaSaida.getInstance();
        cpGraficoEntradaSaidaCustom = new CPGraficoEntradaSaidaCustom();
        cpGraficoEntradaSaidaPreDeterminado = new CPGraficoEntradaSaidaPreDeterminado();
        String nome = "";
        // # 0 pre deter - 1 custom - 2 amobs - 
        int exibePorTipo = -1;
    }

    public void limpaSessaoGraficoSalvoManipulado() {
        absGraficoEntradaSaidaSalvo.setId(0);
    }

    public ABSGraficoEntradaSaida graficoManipulado(int tipoGraficop) {
        switch (tipoGraficop) {
            case 0://pre
                return cpGraficoEntradaSaidaPreDeterminado;
            case 1://custom
                return cpGraficoEntradaSaidaCustom;
        }
        return null;
    }

    public void deletaGrafSaida() {
        if (graficosEntradaSaidaSalvos.contains(absGraficoEntradaSaidaSalvoPraSerDeletado)) {
            graficosEntradaSaidaSalvos.remove(absGraficoEntradaSaidaSalvoPraSerDeletado);
        }
        if (graficosEntradaSaidaSalvosManipulados.contains(absGraficoEntradaSaidaSalvoPraSerDeletado)) {
            graficosEntradaSaidaSalvosManipulados.remove(absGraficoEntradaSaidaSalvoPraSerDeletado);
        }

        if (absGraficoEntradaSaidaSalvoPraSerDeletado.getId() == absGraficoEntradaSaidaPraSerManipulado.getId()
                && UtilString.getNomeClasse(absGraficoEntradaSaidaPraSerManipulado).equals(UtilString.getNomeClasse(absGraficoEntradaSaidaSalvoPraSerDeletado))) {
            absGraficoEntradaSaidaSalvo.setId(0);
        }

        if (absGraficoEntradaSaidaSalvoPraSerDeletado instanceof InterfaceGraficoCustom) {
            DaoGraficoEntradaSaidaCustom.deleta((CPGraficoEntradaSaidaCustom) absGraficoEntradaSaidaSalvoPraSerDeletado);
        } else {
            DaoGraficoEntradaSaidaPreDeterminado.deleta((CPGraficoEntradaSaidaPreDeterminado) absGraficoEntradaSaidaSalvoPraSerDeletado);
        }
        UtilMensagens.ok(UtilMensagens.graficoDeletado);
    }

    public List<String> autoComplete(String s) {
        List<String> resultado = new LinkedList<>();
        for (ABSGraficoEntradaSaida xGraficoSalvo : graficosEntradaSaidaSalvos) {
            if (xGraficoSalvo.getNome().toLowerCase().startsWith(s.toLowerCase())) {
                resultado.add(xGraficoSalvo.getNome());
            }
        }
        return resultado;
    }

    public void filtra(boolean exibeMensagem) {
        // por tipo
        switch (exibePorTipo) {
            case 0:
                graficosEntradaSaidaSalvosManipulados = graficosEntradaSaidaSalvos;
                break;
            case 1:
                graficosEntradaSaidaSalvosManipulados = new LinkedList<>();
                for (ABSGraficoEntradaSaida xGraficoSalvo : graficosEntradaSaidaSalvos) {
                    if (xGraficoSalvo instanceof InterfaceGraficoPreDet) {
                        graficosEntradaSaidaSalvosManipulados.add(xGraficoSalvo);
                    }
                }
                break;
            case 2:
                graficosEntradaSaidaSalvosManipulados = new LinkedList<>();
                for (ABSGraficoEntradaSaida xGraficoSalvo : graficosEntradaSaidaSalvos) {
                    if (xGraficoSalvo instanceof InterfaceGraficoCustom) {
                        graficosEntradaSaidaSalvosManipulados.add(xGraficoSalvo);
                    }
                }
                break;
            case 3:
                graficosEntradaSaidaSalvosManipulados = graficosEntradaSaidaSalvos;
                break;

        }
        //por nome
        if (!nome.isEmpty()) {
            List<ABSGraficoEntradaSaida> resultado = new LinkedList<>();
            for (ABSGraficoEntradaSaida xGraficoSalvo : graficosEntradaSaidaSalvosManipulados) {
                if (xGraficoSalvo.getNome().toLowerCase().contains(nome.toLowerCase())) {
                    resultado.add(xGraficoSalvo);
                }
            }
            graficosEntradaSaidaSalvosManipulados = resultado;
        }
        if (exibeMensagem) {
            UtilMensagens.info(UtilMensagens.buscaRealizada.replace("VR1", graficosEntradaSaidaSalvosManipulados.size() + ""));
        }
    }

    public List<CPProduto> listaProdutosDeGraficoSalvo(ABSGraficoEntradaSaida g) {
        if (g instanceof InterfaceGraficoCustom) {
            return new DaoGraficoEntradaSaidaCustomProdutos().getProdutos(g);
        } else if (g instanceof InterfaceGraficoPreDet) {
            return new DaoGraficoEntradaSaidaPreDeterminadoProdutos().getProdutos(g);/*CANCELA CAST*/
        }
        return null;
    }

    public boolean confereSeABSGrafSaidaSalvoJahTahSalvo() {
        if (absGraficoEntradaSaidaSalvo.getId() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public void atualizaListaDeGraficosSaidaSalvo() {
        graficosEntradaSaidaSalvos = new LinkedList<>();
        for (CPGraficoEntradaSaidaPreDeterminado xPreDeterminado : DaoGraficoEntradaSaidaPreDeterminado.getAllOrderByDataSalvo()) {
            graficosEntradaSaidaSalvos.add(xPreDeterminado);
        }
        for (CPGraficoEntradaSaidaCustom xCustom : DaoGraficoEntradaSaidaCustom.getAllOrderByDataSalvo()) {
            graficosEntradaSaidaSalvos.add(xCustom);
        }
        filtra(false);
    }

    public boolean confereSeOGraficoSalvoManipuladoFoiAleteradoDATA() {
        if (absGraficoEntradaSaidaSalvo instanceof InterfaceGraficoCustom) {
            if (!UtilData.mesNanoN(absGraficoEntradaSaidaSalvo.getDe().getTime()).equals(UtilData.mesNanoN(cpGraficoEntradaSaidaCustom.getDe().getTime()))
                    || !UtilData.mesNanoN(absGraficoEntradaSaidaSalvo.getAte().getTime()).equals(UtilData.mesNanoN(cpGraficoEntradaSaidaCustom.getAte().getTime()))) {
                return true;
            }
        } else if (absGraficoEntradaSaidaSalvo instanceof InterfaceGraficoPreDet) {
            if (!UtilData.mesNanoN(absGraficoEntradaSaidaSalvo.getDe().getTime()).equals(UtilData.mesNanoN(cpGraficoEntradaSaidaPreDeterminado.getDe().getTime()))
                    || !UtilData.mesNanoN(absGraficoEntradaSaidaSalvo.getAte().getTime()).equals(UtilData.mesNanoN(cpGraficoEntradaSaidaPreDeterminado.getAte().getTime()))) {
                return true;
            }
        }
        return false;
    }

    public boolean confereSeOGraficoSalvoManipuladoFoiAleteradoPRODUTOS(List<CPProduto> produtos) {
        //confere PRODUTOS
        if (listaProdutosDeGraficoSalvo(absGraficoEntradaSaidaSalvo).size() != produtos.size()) {
            return true;
        } else {
            List<Long> idsProdutoGrafico = new LinkedList<>();
            List<Long> idsProduto = new LinkedList<>();
            for (CPProduto xProduto : listaProdutosDeGraficoSalvo(absGraficoEntradaSaidaSalvo)) {
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

    //# GET E SET
    public void setAbsGrafSaidaSalvo2(ABSGraficoEntradaSaida absGrafSaidaSalvo) {
        absGraficoEntradaSaidaPraSerManipulado = absGrafSaidaSalvo;
        this.absGraficoEntradaSaidaSalvo.setAgrupamento(absGrafSaidaSalvo.getAgrupamento());
        this.absGraficoEntradaSaidaSalvo.setAte(absGrafSaidaSalvo.getAte());
        this.absGraficoEntradaSaidaSalvo.setDe(absGrafSaidaSalvo.getDe());
        this.absGraficoEntradaSaidaSalvo.setDataModificado(absGrafSaidaSalvo.getDataModificado());
        this.absGraficoEntradaSaidaSalvo.setNome(absGrafSaidaSalvo.getNome());
        this.absGraficoEntradaSaidaSalvo.setId(absGrafSaidaSalvo.getId());
        if (absGrafSaidaSalvo instanceof InterfaceGraficoCustom) {
            this.cpGraficoEntradaSaidaCustom.setAgrupamento(absGrafSaidaSalvo.getAgrupamento());
            this.cpGraficoEntradaSaidaCustom.setAte(absGrafSaidaSalvo.getAte());
            this.cpGraficoEntradaSaidaCustom.setDe(absGrafSaidaSalvo.getDe());
            this.cpGraficoEntradaSaidaCustom.setDataModificado(absGrafSaidaSalvo.getDataModificado());
            this.cpGraficoEntradaSaidaCustom.setNome(absGrafSaidaSalvo.getNome());
        } else if (absGrafSaidaSalvo instanceof InterfaceGraficoPreDet) {
            this.cpGraficoEntradaSaidaPreDeterminado.setAgrupamento(absGrafSaidaSalvo.getAgrupamento());
            this.cpGraficoEntradaSaidaPreDeterminado.setAte(absGrafSaidaSalvo.getAte());
            this.cpGraficoEntradaSaidaPreDeterminado.setDe(absGrafSaidaSalvo.getDe());
            this.cpGraficoEntradaSaidaPreDeterminado.setDataModificado(absGrafSaidaSalvo.getDataModificado());
            this.cpGraficoEntradaSaidaPreDeterminado.setNome(absGrafSaidaSalvo.getNome());
        }
    }

    public ABSGraficoEntradaSaida getAbsGraficoEntradaSaidaPraSerManipulado() {
        return absGraficoEntradaSaidaPraSerManipulado;
    }

    public void setAbsGraficoEntradaSaidaPraSerManipulado(ABSGraficoEntradaSaida absGraficoEntradaSaidaPraSerManipulado) {
        this.absGraficoEntradaSaidaPraSerManipulado = absGraficoEntradaSaidaPraSerManipulado;
    }

    public ABSGraficoEntradaSaida getAbsGraficoEntradaSaidaSalvo() {
        return absGraficoEntradaSaidaSalvo;
    }

    public void setAbsGraficoEntradaSaidaSalvo(ABSGraficoEntradaSaida absGraficoEntradaSaidaSalvo) {
        this.absGraficoEntradaSaidaSalvo = absGraficoEntradaSaidaSalvo;
    }

    public ABSGraficoEntradaSaida getAbsGraficoEntradaSaidaSalvoPraSerDeletado() {
        return absGraficoEntradaSaidaSalvoPraSerDeletado;
    }

    public void setAbsGraficoEntradaSaidaSalvoPraSerDeletado(ABSGraficoEntradaSaida absGraficoEntradaSaidaSalvoPraSerDeletado) {
        this.absGraficoEntradaSaidaSalvoPraSerDeletado = absGraficoEntradaSaidaSalvoPraSerDeletado;
    }

    public CPGraficoEntradaSaidaCustom getCpGraficoEntradaSaidaCustom() {
        return cpGraficoEntradaSaidaCustom;
    }

    public void setCpGraficoEntradaSaidaCustom(CPGraficoEntradaSaidaCustom cpGraficoEntradaSaidaCustom) {
        this.cpGraficoEntradaSaidaCustom = cpGraficoEntradaSaidaCustom;
    }

    public CPGraficoEntradaSaidaPreDeterminado getCpGraficoEntradaSaidaPreDeterminado() {
        return cpGraficoEntradaSaidaPreDeterminado;
    }

    public void setCpGraficoEntradaSaidaPreDeterminado(CPGraficoEntradaSaidaPreDeterminado cpGraficoEntradaSaidaPreDeterminado) {
        this.cpGraficoEntradaSaidaPreDeterminado = cpGraficoEntradaSaidaPreDeterminado;
    }

    public int getExibePorTipo() {
        return exibePorTipo;
    }

    public void setExibePorTipo(int exibePorTipo) {
        this.exibePorTipo = exibePorTipo;
    }

    public List<ABSGraficoEntradaSaida> getGraficosEntradaSaidaSalvos() {
        return graficosEntradaSaidaSalvos;
    }

    public void setGraficosEntradaSaidaSalvos(List<ABSGraficoEntradaSaida> graficosEntradaSaidaSalvos) {
        this.graficosEntradaSaidaSalvos = graficosEntradaSaidaSalvos;
    }

    public List<ABSGraficoEntradaSaida> getGraficosEntradaSaidaSalvosManipulados() {
        return graficosEntradaSaidaSalvosManipulados;
    }

    public void setGraficosEntradaSaidaSalvosManipulados(List<ABSGraficoEntradaSaida> graficosEntradaSaidaSalvosManipulados) {
        this.graficosEntradaSaidaSalvosManipulados = graficosEntradaSaidaSalvosManipulados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
