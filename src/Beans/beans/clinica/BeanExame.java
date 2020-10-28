/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.clinica;

import cp.clinica.CPExame;
import cp.clinica.CPPaciente;
import dao.clinica.DaoExame;
import dao.clinica.DaoGenerico;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utilidades.hibernate.SessionUtil;
import utilidades.mensagens.UtilMensagens;
import utilidades.seguranca.Url;

/**
 *
 * @author Felipe Machado
 */
@ManagedBean
@ViewScoped
public class BeanExame implements Serializable {
    
    private CPExame anexo = new CPExame();
    private List<CPExame> anexos = new ArrayList<>();
    private List<CPExame> anexos2 = new ArrayList<>();
    private List<CPPaciente> pacientes = new ArrayList<>();
    private Long idPaciente;
    private String nomeArquivo;
    private UploadedFile arquivo;
    private List<String> arquivos;
    
    private int idImagem;

    public int getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }
    
    
    public List<CPExame> getAnexos2() {
        return anexos2;
    }
    
    public void setAnexos2(List<CPExame> anexos2) {
        this.anexos2 = anexos2;
    }
    
    public List<String> getArquivos() {
        return arquivos;
    }
    
    public void setArquivos(List<String> arquivos) {
        this.arquivos = arquivos;
    }
    
    public List<CPPaciente> getPacientes() {
        return pacientes;
    }
    
    public void setPacientes(List<CPPaciente> pacientes) {
        this.pacientes = pacientes;
    }
    
    public UploadedFile getArquivo() {
        return arquivo;
    }
    
    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    public CPExame getAnexo() {
        return anexo;
    }
    
    public void setAnexo(CPExame anexo) {
        this.anexo = anexo;
    }
    
    public List<CPExame> getAnexos() {
        anexos = DaoExame.getAll();
        return anexos;
    }
    
    public void setAnexos(List<CPExame> anexos) {
        this.anexos = anexos;
    }
    
    public Long getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public void excluir() {
        DaoGenerico<CPExame> dao = new DaoGenerico<>(CPExame.class);
        try {
            dao.delete(anexo);
            anexo = new CPExame();
            SessionUtil.addSuccessMessage("OperacaoSucesso");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("OperacaoFracasso");
        }
    }
    
    public void addPaciente() {
        DaoGenerico<CPPaciente> dao = new DaoGenerico<>(CPPaciente.class);
        CPPaciente paciente = dao.getById(idPaciente);
        anexo.setPaciente(paciente);
    }
    
    public void fileUploadAction(FileUploadEvent event) {
        this.arquivo = event.getFile();
    }
    
    public void listaAnexosDoPaciente() {
        if (anexo.getPaciente().getId() > 0) {
            List<CPExame> anexos1 = getAnexos();
            for (CPExame xExame : anexos1) {
                if (xExame.getPaciente().getId() == anexo.getPaciente().getId()) {
                    anexos2.add(xExame);
                }
            }
        } else {
            UtilMensagens.alerta("Secione um paciente!");
        }
        
    }
    
    public void salvar() {
        
        nomeArquivo = arquivo.getFileName(); //Nome do arquivo enviado
        
        byte[] conteudo = arquivo.getContents(); //Conteudo a ser gravado no arquivo
        
        File file = new File(Url.localpc+"resources/arquivosUpados/" + nomeArquivo); //Cria uma referencia para arquivo no caminho passado
        try {
            //Escreve o arquivo e salva
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(conteudo);
            fos.flush();
            fos.close();

            //salva no banco
            anexo.setDataAnexo(new Date());
            anexo.setEndAnexo(nomeArquivo);
            DaoExame.merge(anexo);
            anexo = new CPExame();
            
            FacesContext.getCurrentInstance().addMessage("paginaCadastro", new FacesMessage("Arquivo salvo com Sucesso!!"));
            
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage("paginaCadastro", new FacesMessage("Erro ao salvar o arquivo!"));
        }
        
    }
}
