/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.portal;

import beans.listas.BeanListPontoEstagio;
import beans.listas.portal.BeanListAlunos;
import beans.listas.portal.BeanListProfessores;
import beans.listas.portal.BeanListTurmas;
import beans.sessoes.BeanPortal;
import cp.CPAlunoPonto;
import cp.CPProfessorPonto;
import cp.portal.CPTurma;
import cp.portal.CPTurmaAlunos;
import cp.portal.registroLogin.CPRegistroLoginAdministrador;
import cp.portal.usuarios.CPAdministrador;
import cp.portal.usuarios.CPAluno;
import cp.portal.usuarios.CPProfessor;
import dao.DaoProfessorPonto;
import dao.portal.DaoAlunoPonto;
import dao.portal.DaoTurma;
import dao.portal.DaoTurmaAluno;
import dao.portal.registroLogin.DaoRegistroLoginAdministrador;
import dao.portal.usuarios.DaoAdministrador;
import dao.portal.usuarios.DaoAluno;
import dao.portal.usuarios.DaoProfessor;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utilidades.mensagens.UtilMensagens;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "beanAdmin")
@ViewScoped
public class BeanAdministrador implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public void atualizaAdministrador(BeanPortal portal) {
        portal.setAdministrador(((CPAdministrador) UtilString.setaPrimeiroESegundoNomeNosUsuarios(portal.getAdministrador())));
        DaoAdministrador.merge(portal.getAdministrador());
        UtilMensagens.alerta(UtilMensagens.contaAtualizada);
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
    }
    
    
    public void atualizaSenhaAdministrador(BeanPortal portal) {
        if (portal.getSenhaAntiga().equals(portal.getAdministrador().getSenha())) {
            //senha antiga confere
            if (portal.getNovaSenha().equals(portal.getConfirmacaoNovaSenha())) {
                //senhas conicidem
                portal.getAdministrador().setSenha(portal.getNovaSenha());
                DaoAdministrador.merge(portal.getAdministrador());
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
    
    public void cadastraTurma(BeanListTurmas lt) {
        DaoTurma.merge(lt.getTurma());
        UtilMensagens.ok(UtilMensagens.turmaAdd);
        lt.setTurma(new CPTurma());
    }
    
    public void alteraTurma(BeanListTurmas lt) {
        DaoTurma.merge(lt.getTurma());
        UtilMensagens.ok(UtilMensagens.turmaAlterada);
        lt.setTurma(new CPTurma());
    }
    
    public void removeTurma(BeanListTurmas lt) {
        DaoTurma.deleta(lt.getTurma());
        if (lt.getTurmas().contains(lt.getTurma())) {
            lt.getTurmas().remove(lt.getTurma());
        }
        if (lt.getTurmasManipuladas().contains(lt.getTurma())) {
            lt.getTurmasManipuladas().remove(lt.getTurma());
        }
        lt.setTurma(new CPTurma());
        UtilMensagens.ok(UtilMensagens.turmaAlterada);
    }
    
    public void removeProfessor(BeanListProfessores lp) {
        lp.getProfessoresManipulados().remove(lp.getProfessorManipulado());
        lp.getProfessores().remove(lp.getProfessorManipulado());
        DaoProfessor.deleta(lp.getProfessorManipulado());
        UtilMensagens.ok(UtilMensagens.professorDeletado);
    }
    
    public void atualizaProfessor(BeanListProfessores lp, BeanListPontoEstagio lpe) {
        boolean atualizou = false;
        lp.setProfessorManipulado(((CPProfessor) UtilString.setaPrimeiroESegundoNomeNosUsuarios(lp.getProfessorManipulado())));
        CPProfessorPonto professorPonto = DaoProfessorPonto.getByProfessor(lp.getProfessorManipulado());
        if (professorPonto == null && lpe.getPontoSelecioneIndexado() == null) {
            //sem ponto antes, sem ponto agora
        } else if (professorPonto == null && lpe.getPontoSelecioneIndexado() != null) {
            //tava sem ponto antes, agora tem
            CPProfessorPonto pp = new CPProfessorPonto();
            pp.setProfessor(lp.getProfessorManipulado());
            pp.setPonto(lpe.getPontoSelecioneIndexado());
            DaoProfessorPonto.novo(pp);
            atualizou = true;
        } else if (professorPonto != null && lpe.getPontoSelecioneIndexado() == null) {
            //tinha antes, agora n tem mais
            DaoProfessorPonto.fimVigencia(professorPonto);
            atualizou = true;
        } else {
            //tinha antes, e ainda tem
            if (professorPonto.getPonto().getId() == lpe.getPontoSelecioneIndexado().getId()) {
                // ponto anterior iqual ao novo
            } else {
                //ponto novo diferente do antigo
                DaoProfessorPonto.fimVigencia(professorPonto);
                CPProfessorPonto p = new CPProfessorPonto();
                p.setProfessor(lp.getProfessorManipulado());
                p.setPonto(lpe.getPontoSelecioneIndexado());
                DaoProfessorPonto.novo(p);
                atualizou = true;
            }
        }
        if (!atualizou) {
            DaoProfessor.merge(lp.getProfessorManipulado());
        }
        lp.ordenaManipulados();
        UtilMensagens.info(UtilMensagens.professorAtualizado);
    }
    
    public void cadastrasProfessor(BeanListProfessores lp, BeanListPontoEstagio beanlistaDePontos) {
        lp.setProfessor(((CPProfessor) UtilString.setaPrimeiroESegundoNomeNosUsuarios(lp.getProfessor())));
        lp.getProfessor().setContaAtiva(true);
        lp.setProfessor(DaoProfessor.mergeII(lp.getProfessor()));
        if (beanlistaDePontos.getPontoSelecioneIndexado() != null) {
            CPProfessorPonto c = new CPProfessorPonto();
            c.setProfessor(lp.getProfessor());
            c.setPonto(beanlistaDePontos.getPontoSelecioneIndexado());
            DaoProfessorPonto.novo(c);
        }
        lp.setProfessor(new CPProfessor());
        
        beanlistaDePontos.setIndexDoPontoManipulado(-1);
        UtilMensagens.ok("Professor cadastrado!");
    }
    
    public void atualizaAluno(BeanListAlunos listAlunos, BeanListPontoEstagio listPontoEstagio, BeanListTurmas listTurmas) {
        CPTurmaAlunos turmaAlunos = DaoTurmaAluno.buscaPorAlunoVigente(listAlunos.getAlunoManipulado());
        CPAlunoPonto alunoPonto = DaoAlunoPonto.buscaPorAluno(listAlunos.getAlunoManipulado());
        listAlunos.setAluno((CPAluno) UtilString.setaPrimeiroESegundoNomeNosUsuarios(listAlunos.getAlunoManipulado()));
        boolean atualizou = false;
        if (turmaAlunos == null && listTurmas.getTurmaSelecionada() == null) {
            //tava sem turma, e continua sem turma
        } else if (turmaAlunos == null && listTurmas.getTurmaSelecionada() != null) {
            //tava sem turma, agora tem
            CPTurmaAlunos turmaAluno = new CPTurmaAlunos();
            turmaAluno.setTurma(listTurmas.getTurmaSelecionada());
            turmaAluno.setAluno(listAlunos.getAlunoManipulado());
            DaoTurmaAluno.salva(turmaAluno);
            atualizou = true;
        } else if (turmaAlunos != null && listTurmas.getTurmaSelecionada() == null) {
            //tinha turma, não tem mais
            DaoTurmaAluno.fimVigencia(turmaAlunos);
            atualizou = true;
        } else {
            //tinha turma, e ainda tem
            if (listTurmas.getTurmaSelecionada().getId() == turmaAlunos.getTurma().getId()) {
                //turma n mudou
            } else {
                //mudou de turma
                DaoTurmaAluno.fimVigencia(turmaAlunos);
                turmaAlunos.setTurma(listTurmas.getTurmaSelecionada());
                DaoTurmaAluno.salva(turmaAlunos);
                atualizou = true;
            }
        }
        if (!atualizou) {
            DaoAluno.merge(listAlunos.getAlunoManipulado());
        }
        
        if (alunoPonto == null && listPontoEstagio.getPontoSelecioneIndexado() == null) {
            //tava sem ponto, e continua sem ponto
        } else if (alunoPonto == null && listPontoEstagio.getPontoSelecioneIndexado() != null) {
            //tava sem ponto, agora tem
            CPAlunoPonto alunoPont = new CPAlunoPonto();
            alunoPont.setAluno(listAlunos.getAlunoManipulado());
            alunoPont.setPonto(listPontoEstagio.getPontoSelecioneIndexado());
            DaoAlunoPonto.salvar(alunoPont);
            atualizou = true;
        } else if (alunoPonto != null && listPontoEstagio.getPontoSelecioneIndexado() == null) {
            //tinha ponto, não tem mais
            DaoAlunoPonto.fimVigencia(alunoPonto);
            atualizou = true;
        } else {
            //tinha ponto e ainda tem
            if (alunoPonto.getPonto().getId() == listPontoEstagio.getPontoSelecioneIndexado().getId()) {
                //n mudou de ponto
            } else {
                //mudou de ponto
                DaoAlunoPonto.fimVigencia(alunoPonto);
                alunoPonto.setPonto(listPontoEstagio.getPontoSelecioneIndexado());
                DaoAlunoPonto.salvar(alunoPonto);
                atualizou = true;
            }
        }
        if (!atualizou) {
            DaoAluno.merge(listAlunos.getAlunoManipulado());
        }
        listAlunos.getAlunos().remove(listAlunos.getIndexManipulado());
        listAlunos.getAlunos().add(listAlunos.getAlunoManipulado());
        listAlunos.ordenaAlunos();
        UtilMensagens.info(UtilMensagens.alunoAtualizado);
    }
    
    public void cadastraAluno(BeanListAlunos listAlunos, BeanListPontoEstagio listPontoEstagio, BeanListTurmas listTurmas) {
        listAlunos.setAluno((CPAluno) UtilString.setaPrimeiroESegundoNomeNosUsuarios(listAlunos.getAluno()));
        listAlunos.setAluno(DaoAluno.mergeII(listAlunos.getAluno()));
        if (listPontoEstagio.getPontoSelecioneIndexado() != null) {
            CPAlunoPonto alunoPonto = new CPAlunoPonto();
            alunoPonto.setPonto(listPontoEstagio.getPontoSelecioneIndexado());
            alunoPonto.setAluno(listAlunos.getAluno());
            alunoPonto.setVigente(true);
            DaoAlunoPonto.salvar(alunoPonto);
        }
        if (listTurmas.getTurmaSelecionada() != null) {
            CPTurmaAlunos turmaAlunos = new CPTurmaAlunos();
            turmaAlunos.setAluno(listAlunos.getAluno());
            turmaAlunos.setTurma(listTurmas.getTurmaSelecionada());
            DaoTurmaAluno.salva(turmaAlunos);
        }
        listAlunos.setAluno(new CPAluno());
        listPontoEstagio.setIndexDoPontoSelecione(-1);
        listTurmas.setIndexTurmaSelecionada(-1);
        UtilMensagens.info(UtilMensagens.alunoCadastrado);
    }
    
    public void deletaAluno(BeanListAlunos listAlunos) {
        listAlunos.getAlunos().remove(listAlunos.getAlunoManipulado());
        listAlunos.getAlunosManipulados().remove(listAlunos.getAlunoManipulado());
        DaoAluno.deleta(listAlunos.getAlunoManipulado());
        UtilMensagens.ok(UtilMensagens.alunoDeletado);
    }
}
