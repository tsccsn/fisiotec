/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.mensagens;

import cp.estoque.CPProduto;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "msg")
@ViewScoped
public class UtilMensagens implements Serializable {

    private static final long serialVersionUID = 1L;
    

    // #PRODUTOS
    public static String produtoCadastrado = "Produto inserido com sucesso!";
    public static String produtoRemovido = "Produto removido com sucesso!";
    public static String produtoAlterando = "Produto pronto para ser alterado!";
    public static String produtoAlterandoQuantidadeRegistroSaida = "Quantidade do produto pronta para ser alterada!";
    public static String produtoAtualizado = "Produto foi atualizaso com sucesso!";
    public static String produtoDeletado = "Produto foi deletado com sucesso!";
    public static String produtoDeletandoSimples = "Remover o produto VR1 do registro de produtos!";
    public static String produtoDeletandoCompleta = "Remover o produto VR1 do registro de produtos e também em qualquer registro que ele conste";
    public static String produtosTodosAdd = "Todos os produtos já foram adicionados, não há mais produtos a ser adicionado!";
    public static String produtoQuantidadeSaidaAtualizado = "A quantidade de saída do produto foi atualizada com sucesso!";
    public static String produtoAddNaLista = "Produto adicionado na lista com sucesso!";
    public static String produtoNemNaLista = "Ops! Adicione um produto, antes de seguir!";
    // #FIM PRODUTOS
    // # GRAFICO
    public static String graficoDeletado = "Grafico deletado com sucesso!";
    public static String graficoCarregado = "Grafico carregado com sucesso!";
    public static String graficoSalvo = "Grafico salvo com sucesso!";
    public static String graficoAtualizado = "Grafico atualizado com sucesso!";
    public static String graficoExistenteEscolhaOutro = "Já existe um gráfico com este nome!";
    // # FIM GRAFICO
    // # REGISTRO SAIDA
    public static String registroDeSaidaSalvo = "Registro de saida salvo!";
    public static String registroDeSaidaSemProdutis = "Não há produtos no registro!";
    public static String registroDeSaidaTentandoTirarMaisDoQueTem = "A quantidade do produto VR1 é de VR2, portanto você não pode retirar mais do que possui!";
    // # FIM REGISTRO SAIDA
    // # DATA
    public static String dataInicioMaiorQueFim = "Ops! A data inicial não pode ser maior q a final!";
    public static String dataMesInvalida = "Se deseja agrupar por mes, então selecione um espaço de tempo com no mínimo um mês!";
    public static String dataBimestreInvalida = "Se deseja agrupar por bimestre, então selecione um espaço de tempo com no mínimo um mês!";
    public static String dataTrimestreInvalida = "Se deseja agrupar por trimestre, então selecione um espaço de tempo com no mínimo um mês!";
    public static String dataSemestreInvalida = "Se deseja agrupar por semestre, então selecione um espaço de tempo com no mínimo um mês!";
    public static String dataAnoInvalida = "Se deseja agrupar por ano, então selecione um espaço de tempo com no mínimo um mês!";
    public static String dataVazia = "Ops! A data está vazia!";
    public static String dataDiasInvalida = "Se deseja agrupar por VR1 dias então selecione um espaço de tempo com no minimo VR1 dias !";
    public static String dataErrada = "Ops! Data Inválida!";
    // # FIM DATA
    // # REGISTRO ENTRADA
    public static String registroDeEntradaSalvo = "Registro de entrada salvo!";
    public static String registroDeEntradaSemProdutos = "Não há produtos no registro!";
    // # FIM REGISTRO ENTRADA   
    // # VALIDADORES CAMPOS
    public static String campoVazio = "O campo não pode ficar vazio!";
    public static String buscaNumeroNull = "Se não for utilizar o recurso, deixe 0 (zero) no campo!";
    public static String selecioneUmaOpcao = "Selecione uma das opções!";
    public static String validaTamanhoLogin = "O login deve ter no mínimo 3 caracteres!";
    public static String validaTamanhoSenha = "A senha deve ter no mínimo 3 caracteres!";
    public static String validaTamanhoNome = "o nome deve ter entre no mínimo 3 caracteres!";
    // # VALIDADORES FIM CAMPOS
    // # NUMEROS
    public static String numeroMenorQZero = "Não é permitido a inserção de um número menor que zero";
    public static String numeroMenorQUm = "Não é permitido a inserção de um número menor que um!";
    // # FIM NUMEROS
    // # BUSCA
    public static String buscaVazia = "Sua busca não deu resultado algum!";
    public static String buscaRealizada = "Sua Busca retornou VR1 resultados";
    // # FIM BUSCA
    // # UNIDADE
    public static String unidadeRegistrada = "Unidade Registrada!";
    public static String unidadeAtualizada = "Unidade atualizada!";
    // # FIM UNIDADE
    // # PONTOS
    public static String pontosTodosAdd = "Todos os pontos já foram adicionados!";
    public static String pontoAdd = "Ponto adicionado na lista com sucesso!";
    public static String pontoRemovido = "Ponto removido da lista com sucesso!";
    public static String pontoNemEumNaLista = "Selecione pelo menos um ponto de estágio para seguir!";
    // # FIM PONTOS
    //# TURMAS
    public static String turmaAdd = "Turma cadastrada!";
    public static String turmaAlterada = "Turma atualizada com sucesso!";
    public static String turmaRemovida = "Turma removida com sucesso!";
    //# FIM TURMAS
    // # ALUNOS
    public static String alunoAtualizado = "Aluno atualizado com sucesso!";
    public static String alunoCadastrado = "Aluno cadastrado com sucesso!";
    public static String alunoDeletado = "Aluno deletado com sucesso!";
    // # FIM ALUNOS
    // # PROFESSORES
    public static String professorAtualizado = "Professor atualizado com sucesso!";
    public static String professorCadastrado = "Professor cadastrado com sucesso!";
    public static String professorDeletado = "Professor removido com sucesso!";
    // # FIM PROFESSORES
    // # SENHA
    public static String senhaNaoCoincidem = "Ops! Senha não confere!";
    public static String senhaAntigaErrada = "Você errou sua senha antiga!";
    public static String senhaAtualizada = "Senha atualizada!";
    // # FIM SENHA
    // # CONTA 
    public static String contaAtualizada = "Você atualizou a conta corretamente!";
    // # FIM CONTA
    // # ALTERACAO NOTA
    public static String alteracaoNotaFeitoPedido = "Pedido de alteração de nota concluído!";
    // # FRIM ALTERACAO NOTA

    public static void alerta(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
    }

    public static void info(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO , msg, msg));
    }

    public static void ok(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg));
    }

    //# GET's
    public String getCampoVazio() {
        return campoVazio;
    }

    public String getProdutoAlterando() {
        return produtoAlterando;
    }

    public String getProdutoAtualizado() {
        return produtoAtualizado;
    }

    public String getProdutoCadastrado() {
        return produtoCadastrado;
    }

    public String getProdutoRemovido() {
        return produtoRemovido;
    }

    public String getBuscaVazia() {
        return buscaVazia;
    }

    public String getBuscaRealizada() {
        return buscaRealizada;
    }

    public String getBuscaNumeroNull() {
        return buscaNumeroNull;
    }

    public String getNumeroMenorQZero() {
        return numeroMenorQZero;
    }

    public String getNumeroMenorQUm() {
        return numeroMenorQUm;
    }

    public String getUnidadeRegistrada() {
        return unidadeRegistrada;
    }
    public String getSelecioneUmaOpcao() {
        return selecioneUmaOpcao;
    }

    public String getProdutoDeletado() {
        return produtoDeletado;
    }

    public String getProdutoDeletandoCompleta() {
        return produtoDeletandoCompleta;
    }

    public String produtoDeletandoCompleta(CPProduto p) {
        return produtoDeletandoCompleta.replace("VR1", "\"" + p.toString() + "\"");
    }

    public String getProdutoDeletandoSimples() {
        return produtoDeletandoSimples;
    }

    public String produtoDeletandoSimples(CPProduto p) {
        return produtoDeletandoSimples.replace("VR1", p.toString());
    }

    public String getProdutosTodosAdd() {
        return produtosTodosAdd;
    }

    public String getProdutoQuantidadeSaidaAtualizado() {
        return produtoQuantidadeSaidaAtualizado;
    }

    public String getProdutoAddNaLista() {
        return produtoAddNaLista;
    }

    public String getProdutoAlterandoQuantidadeRegistroSaida() {
        return produtoAlterandoQuantidadeRegistroSaida;
    }

    public static String registroDeSaidaTentandoTirarMaisDoQueTemMethStc(CPProduto p) {
        return registroDeSaidaTentandoTirarMaisDoQueTem.replace("VR1", p.toString()).replace("VR2", p.getQuantidadeEmStoque() + "");
    }

    public String registroDeSaidaTentandoTirarMaisDoQueTem(CPProduto p) {
        return registroDeSaidaTentandoTirarMaisDoQueTem.replace("VR1", p.toString()).replace("VR2", p.getQuantidadeEmStoque() + "");
    }

    public String getRegistroDeSaidaTentandoTirarMaisDoQueTem() {
        return registroDeSaidaTentandoTirarMaisDoQueTem;
    }

    public String getGraficoDeletado() {
        return graficoDeletado;
    }

    public String getDataInicioMaiorQueFim() {
        return dataInicioMaiorQueFim;
    }

    public String getDataMesInvalida() {
        return dataMesInvalida;
    }

    public String getDataAnoInvalida() {
        return dataAnoInvalida;
    }

    public String getDataBimestreInvalida() {
        return dataBimestreInvalida;
    }

    public String getDataSemestreInvalida() {
        return dataSemestreInvalida;
    }

    public String getDataTrimestreInvalida() {
        return dataTrimestreInvalida;
    }

    public String getDataVazia() {
        return dataVazia;
    }

    public String getDataDiasInvalida() {
        return dataDiasInvalida;
    }

    public static String dataDiasInvalida(int dias) {
        return dataDiasInvalida.replace("VR1", dias + "");
    }

    public String getPontosTodosAdd() {
        return pontosTodosAdd;
    }

    public String getProdutoNemNaLista() {
        return produtoNemNaLista;
    }

    public String getGraficoCarregado() {
        return graficoCarregado;
    }

    public String getGraficoSalvo() {
        return graficoSalvo;
    }

    public String getPontoAdd() {
        return pontoAdd;
    }

    public String getPontoRemovido() {
        return pontoRemovido;
    }

    public String getGraficoAtualizado() {
        return graficoAtualizado;
    }

    public String getTurmaAdd() {
        return turmaAdd;
    }

    public String getDataErrada() {
        return dataErrada;
    }

    public String getGraficoExistenteEscolhaOutro() {
        return graficoExistenteEscolhaOutro;
    }

    public String getValidaTamanhoLogin() {
        return validaTamanhoLogin;
    }

    public String getValidaTamanhoSenha() {
        return validaTamanhoSenha;
    }

    public String getValidaTamanhoNome() {
        return validaTamanhoNome;
    }

    /**
     *
     *  // # VALIDADORES CAMPOS public static String campoVazio = "O campo alí
     * não pode ficar vazio!"; public static String buscaNumeroNull = "Se vc não
     * for usar o recurso deixe 0 (zero) no campo "; public static String
     * selecioneUmaOpcao = "Selecione uma das opções"; public static String
     * validaTamanhoLogin = "o login deve ter entre 3 e 20 caracteres"; public
     * static String validaTamanhoSenha = "o senha deve ter entre 3 e 20
     * caracteres"; // # VALIDADORES FIM CAMPOS
     */
}
