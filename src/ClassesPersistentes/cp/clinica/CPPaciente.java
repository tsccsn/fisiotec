/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cp.clinica;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Felipe Machado
 */
@Entity
public class CPPaciente implements Serializable {

    //Dados do Paciente
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String pai;
    private String mae;
    private String sexo;
    private String raca;
    @Temporal(TemporalType.DATE)
    private Date dataNasc;
    private String naturalidade;
    //Documentos
    private String rg;
    private String cpf;
    //EndereÃ§o
    private String logradouro;
    private int num;
    private String bairro;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
    //Contatos
    private String fone;
    private String celfone;
    private String email;
    //ResponsÃ¡vel
    private String nomeResp;
    private String foneResp;
    //Dados Adicionais
    private String rendaMensal;
    private String escolaridade;
    private String profissao;

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getFoneResp() {
        return foneResp;
    }

    public void setFoneResp(String foneResp) {
        this.foneResp = foneResp;
    }

    public String getNomeResp() {
        return nomeResp;
    }

    public void setNomeResp(String nomeResp) {
        this.nomeResp = nomeResp;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(String rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCelfone() {
        return celfone;
    }

    public void setCelfone(String celfone) {
        this.celfone = celfone;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date converter(String data) {
        Date dateformated;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateformated = (Date) formatter.parse(data);
            // paciente.setDataNasc(dateformated);
            return dateformated;
        } catch (Exception e) {
            return null;
        }
    }
}
