package beans.listas;

import cp.estoque.CPUnidadeDeMedida;
import dao.estoque.DaoUnidadeDeMedida;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.comparadores.OrdenaUnidadesDeMedida;

@ManagedBean(name = "listaDeUnidades")
@ViewScoped
public class BeanListUnidades implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<CPUnidadeDeMedida> unidades = DaoUnidadeDeMedida.getAll("nome");
    private CPUnidadeDeMedida unidade = new CPUnidadeDeMedida();
    private CPUnidadeDeMedida unidadeManipulada = new CPUnidadeDeMedida();
    private long idUnidadeManipulado;
    private List<String> indexSListaUnidade = new LinkedList<>();
    private int posManipulado = -1;
    
    public void ordena() {
        OrdenaUnidadesDeMedida o = new OrdenaUnidadesDeMedida();
        Collections.sort(unidades, o);
    }
    
    public CPUnidadeDeMedida unidadeManipuladaPos() {
        return unidades.get(posManipulado);
    }
    
    public int getPosManipulado() {
        return posManipulado;
    }
    
    public void setPosManipulado(int posManipulado) {
        this.posManipulado = posManipulado;
    }
    
    public List<String> getIndexSListaUnidade() {
        return indexSListaUnidade;
    }
    
    public void setIndexSListaUnidade(List<String> indexSListaUnidade) {
        this.indexSListaUnidade = indexSListaUnidade;
    }
    
    public List<CPUnidadeDeMedida> getUnidadesIndexadas() {
        List<CPUnidadeDeMedida> uni = new LinkedList<>();
        for (String s : indexSListaUnidade) {
            uni.add(unidades.get(Integer.parseInt(s)));
        }
        return uni;
    }
    
    public List<Long> getIdUnidadesIndexadas() {
        List<Long> uni = new LinkedList<>();
        if (indexSListaUnidade.contains("qualquer")) {
            return uni;
        } else {
            for (String s : indexSListaUnidade) {
                uni.add(unidades.get(Integer.parseInt(s)).getId());
            }
        }
        return uni;
    }
    
    public CPUnidadeDeMedida unidadeReferenteAoId() {
        for (CPUnidadeDeMedida u : unidades) {
            if (u.getId() == idUnidadeManipulado) {
                return u;
            }
        }
        return null;
    }
    
    public List<CPUnidadeDeMedida> getUnidades() {
        return unidades;
    }
    
    public long getIdUnidadeManipulado() {
        return idUnidadeManipulado;
    }
    
    public void setUnidades(List<CPUnidadeDeMedida> unidades) {
        this.unidades = unidades;
    }
    
    public void setIdUnidadeManipulado(long idUnidadeManipulado) {
        this.idUnidadeManipulado = idUnidadeManipulado;
    }
    
    public CPUnidadeDeMedida getUnidade() {
        return unidade;
    }
    
    public void setUnidade(CPUnidadeDeMedida unidade) {
        this.unidade = unidade;
    }
    
    public CPUnidadeDeMedida getUnidadeManipulada() {
        return unidadeManipulada;
    }
    
    public void setUnidadeManipulada(CPUnidadeDeMedida unidadeManipulada) {
        this.unidadeManipulada = unidadeManipulada;
    }
}
