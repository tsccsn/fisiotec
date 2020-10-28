/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.mensagens;

import cp.portal.CPPedidoAlteracaoNota;
import dao.portal.DaoPedidoDeAlteracaoDeNota;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import utilidades.data.UtilData;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "msgAdmin")
@SessionScoped
public class UtilMensagensAdministrador implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean exibiu = false;
    private List<CPPedidoAlteracaoNota> pedidosPendentes = DaoPedidoDeAlteracaoDeNota.getAllPendentes();
    private int posPedidoEmQuestao;

    public void aceitarSolicitacao(CPPedidoAlteracaoNota xPedido) {
        xPedido.setAceito(true);
        xPedido.setDataRespondido(UtilData.getDataTimestamp());
        xPedido.getAlunoPonto().setNota(xPedido.getNovaNota());
        DaoPedidoDeAlteracaoDeNota.merge(xPedido);
        pedidosPendentes.remove(xPedido);
        UtilMensagens.ok("Solicitação respondida");
    }

    public void negarSolicitacao(CPPedidoAlteracaoNota xPedido) {
        xPedido.setAceito(false);
        xPedido.setDataRespondido(UtilData.getDataTimestamp());
        DaoPedidoDeAlteracaoDeNota.merge(xPedido);
        pedidosPendentes.remove(xPedido);
        UtilMensagens.ok("Solicitação respondida");
    }

    public void logout() {
        exibiu = false;
    }

    public void exibeMensagens() {
        pedidosPendentes = DaoPedidoDeAlteracaoDeNota.getAllPendentes();
        if (!exibiu) {
            if (!pedidosPendentes.isEmpty()) {
                if (pedidosPendentes.size() <= 3) {
                    for (CPPedidoAlteracaoNota xPedido : pedidosPendentes) {
                        UtilMensagens.info("O(a) professor(a) " + UtilString.nomeFormal(xPedido.getProfessor()) + " solicitou revisão de nota!");
                    }
                    UtilMensagens.info("Você pode aceitar ou não a solicitação <a href=\"http://localhost:8084/F3/portal/administrador/professor/alertas.xhtml\" title=\"Visualizar alertas\">"
                            + "<span style=\"color:#00F;text-decoration:underline\">aqui</span></a>");
                } else {
                    for (int i = 0; i < 3; i++) {
                        UtilMensagens.info("O(a) professor(a) " + UtilString.nomeFormal(pedidosPendentes.get(i).getProfessor()) + " solicitou revisão da nota dele");
                    }
                    UtilMensagens.info("Ainda há mais avisos, você pode visualizalos <a href=\"http://localhost:8084/F3/portal/administrador/professor/alertas.xhtml\" title=\"Visualizar alertas\" >"
                            + "<span style=\"color:#00F;text-decoration:underline\">aqui</span></a>");
                }
            }
        }
        exibiu = true;
    }

    public List<CPPedidoAlteracaoNota> getPedidosPendentes() {
        return pedidosPendentes;
    }

    public boolean isExibiu() {
        return exibiu;
    }

    public void setExibiu(boolean exibiu) {
        this.exibiu = exibiu;
    }

    public void setPedidosPendentes(List<CPPedidoAlteracaoNota> pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public int getPosPedidoEmQuestao() {
        return posPedidoEmQuestao;
    }

    public void setPosPedidoEmQuestao(int posPedidoEmQuestao) {
        this.posPedidoEmQuestao = posPedidoEmQuestao;
    }
}
