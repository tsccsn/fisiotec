/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.clinica;

import cp.clinica.CPPaciente;
import dao.clinica.DaoGenerico;
import dao.clinica.DaoPaciente;
import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import utilidades.hibernate.SessionUtil;
import utilidades.mensagens.UtilMensagens;

/**
 *
 * @author Felipe Machado
 */
@ManagedBean
@ViewScoped
public class BeanPaciente implements Serializable {

    private CPPaciente paciente = new CPPaciente();
    private CPPaciente relatorioPaciente = new CPPaciente();
    private List<CPPaciente> pacientes = new ArrayList<CPPaciente>();
    private List<CPPaciente> pacientesPesquisa = new ArrayList<CPPaciente>();
    private boolean modoEditar;
    private String tipoFicha = "printFichas";

    public List<CPPaciente> getPacientesPesquisa() {
        if (pacientesPesquisa.isEmpty()) {
            pacientesPesquisa = DaoPaciente.getAll();
        }
        return pacientesPesquisa;
    }

    public void setPacientesPesquisa(List<CPPaciente> pacientesPesquisa) {
        this.pacientesPesquisa = pacientesPesquisa;
    }

    public CPPaciente getRelatorioPaciente() {
        return relatorioPaciente;
    }

    public void setRelatorioPaciente(CPPaciente relatorioPaciente) {
        this.relatorioPaciente = relatorioPaciente;
    }

    public String obtTipoFicha() {
        System.out.println(tipoFicha);
        return tipoFicha;
    }

    public String getTipoFicha() {
        return tipoFicha;
    }

    public void setTipoFicha(String tipoFicha) {
        this.tipoFicha = tipoFicha;
    }

    public boolean isModoEditar() {
        return modoEditar;
    }

    public void setModoEditar(boolean modoEditar) {
        this.modoEditar = modoEditar;
    }

    public CPPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CPPaciente paciente) {
        this.paciente = paciente;
        System.out.print(paciente.getNome());
    }

    public List<CPPaciente> getPacientes() {
        pacientes = new DaoGenerico<CPPaciente>(CPPaciente.class).getAll();
        return pacientes;
    }

    public void setPacientes(List<CPPaciente> pacientes) {
        this.pacientes = pacientes;
    }

    public void gravar() {
        try {
            if (paciente.getId() == null) {
                DaoPaciente.merge(paciente);
            } else {
                DaoPaciente.merge(paciente);
            }
            paciente = new CPPaciente();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
            System.out.print(e.getMessage());
        }
    }

    public void atualizaPaciente() {
        DaoPaciente.merge(paciente);
        UtilMensagens.info("Paciente atualizado corretamente");
        RequestContext.getCurrentInstance().addCallbackParam("fecha", true);
    }

    public void excluir() {
        DaoGenerico<CPPaciente> dao = new DaoGenerico<>(CPPaciente.class);
        try {
            dao.delete(paciente);
            paciente = new CPPaciente();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
            System.out.print(e.getMessage());
        }
    }

    public void validateCPF(FacesContext context, UIComponent toValidate, Object value) {

        String strCpf = value.toString();

        if (strCpf.equals("")) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage(" *CPF Inválido ");
            context.addMessage(null, message);
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.  
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divisão por 11.  
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.  
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que está sendo validado.  
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.  
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.  
        if (!nDigVerific.equals(nDigResult)) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage(" *CPF Inválido ");
            context.addMessage(null, message);
        }
    }

    public List<CPPaciente> completa(String query) {
        List<CPPaciente> sugestoes = new ArrayList<CPPaciente>();
        for (CPPaciente pt : this.getPacientes()) {
            if (pt.getNome().toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public void pesquisaGeral() {
        pacientesPesquisa = DaoPaciente.pesquisa(relatorioPaciente);
    }

    public List<String> nomes(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("nome")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<String> sexos(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("sexo")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<String> racas(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("raca")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<String> naturalidades(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("naturalidade")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<String> bairros(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("bairro")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<String> cidades(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("cidade")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }

    public List<String> estados(String query) {
        List<String> sugestoes = new ArrayList<String>();
        for (String pt : DaoPaciente.listas("estado")) {
            if (pt.toUpperCase().startsWith(query.toUpperCase())) {
                sugestoes.add(pt);
            }
        }
        return sugestoes;
    }
}
