/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.portal;

import beans.listas.portal.BeanListAlunos;
import beans.sessoes.BeanPortal;
import cp.CPAlunoPonto;
import cp.CPPontoDeEstagio;
import cp.portal.CPPedidoAlteracaoNota;
import cp.portal.registroLogin.CPRegistroLoginProfessor;
import cp.portal.usuarios.CPProfessor;
import dao.portal.DaoPedidoDeAlteracaoDeNota;
import dao.portal.usuarios.DaoProfessor;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import objetos.portal.usuarios.OBJProfessor;
import org.primefaces.context.RequestContext;
import utilidades.Boleanos;
import utilidades.data.UtilData;
import utilidades.hibernate.SessionUtil;
import utilidades.mensagens.UtilMensagens;
import utilidades.seguranca.Url;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "beanProfessor")
@SessionScoped
public class BeanProfessor implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<Long, CPPedidoAlteracaoNota> pedidos = new HashMap<>();
    private String justificativa;
    private OBJProfessor objProfessor = new OBJProfessor();

    public void logarProfessor() {
        if (DaoProfessor.existLogin(objProfessor.getObjProfessorManipulado())) {// se login válido
            if (!DaoProfessor.getContaAtiva(objProfessor.getObjProfessorManipulado())) { // confere bloqueio
                SessionUtil.addErrorMessage("contaBloqueada");
            } else { // se não está bloqueado
                objProfessor.setObjRegistroLoginAtual(DaoProfessor.validaLogin(objProfessor.getObjProfessorManipulado())); //convefere senha
                if (objProfessor.getObjRegistroLoginAtual().getId() > 0) {
                    // se acertou senha
                    SessionUtil.addSuccessMessage("logou");
                    objProfessor.setObjProfessor(DaoProfessor.getByLogin(objProfessor.getObjProfessorManipulado()));
                    RequestContext.getCurrentInstance().addCallbackParam("logou", true);
                    //FacesContext.getCurrentInstance().getExternalContext().redirect(Url.local + "portal/professor/inicio.xhtml");
                } else {//se errou senha
                    RequestContext.getCurrentInstance().addCallbackParam("treme", true);
                    switch (DaoProfessor.getNumeroTentativas(objProfessor.getObjProfessorManipulado())) {
                        case 1:
                            SessionUtil.addErrorMessage("errologin1");
                            break;
                        case 2:
                            SessionUtil.addErrorMessage("errologin2");
                            break;
                        case 3:
                            SessionUtil.addErrorMessage("errologin3");
                            break;
                        case 4:
                            SessionUtil.addErrorMessage("errologin4");
                            break;
                        case 5:
                            SessionUtil.addErrorMessage("errologin5");
                            break;
                    }
                }
            }
        } else { // se login invalido, exibe erro
            RequestContext.getCurrentInstance().addCallbackParam("treme", true);
            SessionUtil.addErrorMessage("loginerrado");
        }
    }

    public void atualizaProfessor(BeanPortal portal) {
        portal.setProfessor(((CPProfessor) UtilString.setaPrimeiroESegundoNomeNosUsuarios(portal.getProfessor())));
        DaoProfessor.merge(portal.getProfessor());
        UtilMensagens.alerta(UtilMensagens.contaAtualizada);
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
        portal.setProfessor(portal.getProfessor());
        portal.setUsuario(portal.getProfessor());
        portal.getRegistroLogin().setUsuario(portal.getProfessor());
    }

    public void atualizaSenhaAdministrador(BeanPortal portal) {
        if (portal.getSenhaAntiga().equals(portal.getProfessor().getSenha())) {
            //senha antiga confere
            if (portal.getNovaSenha().equals(portal.getConfirmacaoNovaSenha())) {
                //senhas conicidem
                portal.getProfessor().setSenha(portal.getNovaSenha());
                System.out.println("nova senha: " + portal.getNovaSenha());
                portal.setProfessor(portal.getProfessor());
                portal.setUsuario(portal.getProfessor());
                portal.getRegistroLogin().setUsuario(portal.getProfessor());
                DaoProfessor.merge(portal.getProfessor());
                UtilMensagens.alerta(UtilMensagens.senhaAtualizada);
                portal.setSenhaAntiga("");
                portal.setNovaSenha("");
                portal.setConfirmacaoNovaSenha("");
                RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
            } else {
                UtilMensagens.alerta(UtilMensagens.senhaNaoCoincidem);
            }
        } else {
            UtilMensagens.alerta(UtilMensagens.senhaAntigaErrada);
        }
    }

    public boolean confereSeExisteAlgo(CPAlunoPonto xAlunoPonto) {
        if (pedidos.containsKey(xAlunoPonto.getId())) {
            return true;
        }
        return false;
    }

    public boolean confereSeExistePedidoPendente(CPAlunoPonto xAlunoPonto) {
        if (pedidos.containsKey(xAlunoPonto.getId())) {
            if (pedidos.get(xAlunoPonto.getId()).getDataRespondido() == null) {
                return true;
            }
        }
        return false;
    }

    public boolean confereSeExistePedidoRespondido(CPAlunoPonto xAlunoPonto) {
        if (pedidos.containsKey(xAlunoPonto.getId())) {
            if (pedidos.get(xAlunoPonto.getId()).getDataRespondido() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean confereSeFoiAceitoPositivamente(CPAlunoPonto xAlunoPonto) {
        CPPedidoAlteracaoNota get = pedidos.get(xAlunoPonto.getId());
        System.out.println("pedido de alteração de nota do aluno" + xAlunoPonto.getAluno().getNomeCompleto() + " foi " + get.isAceito());
        return get.isAceito();
    }

    public void carregaPedidos(CPProfessor xProfessor) {
        List<CPPedidoAlteracaoNota> listaDePedidosDeAlteracaoDeNota = DaoPedidoDeAlteracaoDeNota.getAllByProfessor(xProfessor);
        for (CPPedidoAlteracaoNota xPedidoAlteracaoNota : listaDePedidosDeAlteracaoDeNota) {
            pedidos.put(xPedidoAlteracaoNota.getAlunoPonto().getId(), xPedidoAlteracaoNota);
        }
    }

    public void alteraNota(BeanListAlunos listaAlunos, Boleanos boleanos) {
        listaAlunos.setNota(0);
        if (listaAlunos.getAlunoPontoManipulado().getNota() > -1) {//já lançou nota
            RequestContext.getCurrentInstance().addCallbackParam("lancouNota", true);
            boleanos.ambosTrue(1);
        } else {
            boleanos.ambosTrue(0);
            //listaAlunos.getAlunoPontoManipulado().setNota(0);
            RequestContext.getCurrentInstance().addCallbackParam("lancouNota", false);
        }
    }

    public void lancaNota(BeanListAlunos listaDeAlunos) {
        listaDeAlunos.getAlunoPontoManipulado().setNota(listaDeAlunos.getNota());
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
    }

    public void geraPedidoDeAlteracaoDeNota(BeanListAlunos listaDeAlunos, CPProfessor professor, Boleanos boleanos) {
        CPPedidoAlteracaoNota pedido = new CPPedidoAlteracaoNota();
        pedido.setAlunoPonto(listaDeAlunos.getAlunoPontoManipulado());
        pedido.setNovaNota(listaDeAlunos.getNota());
        pedido.setNotaAntiga(listaDeAlunos.getAlunoPontoManipulado().getNota());
        pedido.setProfessor(professor);
        pedido.setJustificativa(justificativa);
        pedido.setDataGerado(UtilData.getDataTimestamp());
        DaoPedidoDeAlteracaoDeNota.merge(pedido);
        boleanos.ambosFalse(1, 0);
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
        UtilMensagens.ok(UtilMensagens.alteracaoNotaFeitoPedido);
        carregaPedidos(professor);
        justificativa = "";
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public HashMap<Long, CPPedidoAlteracaoNota> getPedidos() {
        return pedidos;
    }

    public void setPedidos(HashMap<Long, CPPedidoAlteracaoNota> pedidos) {
        this.pedidos = pedidos;
    }

    public CPPontoDeEstagio getPontoAtualProfessor() {
        return objProfessor.getObjPontoAtual();
    }

    // # GET's SET's 
    public OBJProfessor getObjProfessor() {
        return objProfessor;
    }

    public void setObjProfessor(OBJProfessor objProfessor) {
        this.objProfessor = objProfessor;
    }
}
