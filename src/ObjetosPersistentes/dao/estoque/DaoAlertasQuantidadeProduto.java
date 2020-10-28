/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.estoque;

import cp.CPProfessorPonto;
import cp.estoque.CPAlertasQuantidadeProduto;
import cp.estoque.CPProduto;
import dao.DaoGenerico;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class DaoAlertasQuantidadeProduto {
    
    private static DaoGenerico<CPAlertasQuantidadeProduto> daoAP = new DaoGenerico<>(CPAlertasQuantidadeProduto.class);
    
    public static void merge(CPAlertasQuantidadeProduto alertasQuantidadeProduto){
        daoAP.merge(alertasQuantidadeProduto);
    }
    
    public static List<CPAlertasQuantidadeProduto> getAllByDataGerado(){
        HibernateUtil.abre();
        String sql = "from CPAlertasQuantidadeProduto where vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    
    public static List<CPAlertasQuantidadeProduto> getPorProduto(CPProduto p){
        HibernateUtil.abre();
        String sql = "from CPAlertasQuantidadeProduto where produto.id = " + p.getId() + " and vigente = true ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    public static List<CPAlertasQuantidadeProduto> getPorProdutoAll(CPProduto p){
        HibernateUtil.abre();
        String sql = "from CPAlertasQuantidadeProduto where produto.id = " + p.getId() + "  ";
        Query q = HibernateUtil.getSessao().createQuery(sql);
        List list = q.list();
        HibernateUtil.fecha();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
    
    public static void geraNovoAviso(CPProduto produto){
        CPAlertasQuantidadeProduto alerta = new CPAlertasQuantidadeProduto();
        alerta.setProduto(produto);
        alerta.setDataAtivo(UtilData.getDataTimestamp());
        alerta.setVigente(true);
        merge(alerta);
    }
    public static void deleta(CPAlertasQuantidadeProduto p){
        daoAP.delete(p);
    }
}
