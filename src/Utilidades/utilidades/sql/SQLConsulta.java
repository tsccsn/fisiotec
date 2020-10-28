/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.sql;

import cp.CPPontoDeEstagio;
import cp.estoque.CPProduto;
import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import utilidades.data.UtilData;
import utilidades.graficos.IO.GrafIODados;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Thiago
 */
public class SQLConsulta {

    public static int quantidadeEmEstoqueDeUmProdutoXEmDataY(Timestamp data, CPProduto produto) {
        BigDecimal entrou;
        BigDecimal saiu;
        BigDecimal quantidadeAtual;
        BigDecimal resultado;
        //o quanto entrou
        String sql = "  select"
                + "         sum(ep.quantidade)"
                + "     from"
                + "         cpentrada e,"
                + "         cpentradaproduto ep"
                + "     where"
                + "         e.id = ep.entrada_id and"
                + "         e.dataEntrada >= '" + UtilData.AnoNMesNDiaN(data) + " 0:0:0' and"
                + "         ep.produto_id = " + produto.getId() + " ";
        HibernateUtil.abre();
        Query q = HibernateUtil.getSessao().createSQLQuery(sql);
        entrou = (BigDecimal) q.list().get(0);
        if(entrou == null){
            entrou = new BigDecimal(0);
        }
        //o quanto saiu
        sql = "     select "
                + "     sum(sp.quantidade) "
                + " from "
                + "     cpsaida s,"
                + "     cpsaidaproduto sp"
                + " where "
                + "     sp.saida_id = s.id and "
                + "     s.dataSaida >= '" + UtilData.AnoNMesNDiaN(data) + " 0:0:0' and  "
                + "     sp.produto_id = " + produto.getId() + " ";

        q = HibernateUtil.getSessao().createSQLQuery(sql);
        saiu = (BigDecimal) q.list().get(0);
        quantidadeAtual = new BigDecimal(produto.getQuantidadeEmStoque() + "");
        if (saiu != null) {
            System.out.println("saiu : "+saiu);
            System.out.println("quantidade atual: "+quantidadeAtual);
            System.out.println("entrou : "+entrou);
            resultado = entrou.subtract(saiu).subtract(quantidadeAtual);
        } else {
            resultado = quantidadeAtual;
        }
        HibernateUtil.fecha();
        if (resultado.intValue() < 0) {
            return Integer.parseInt(resultado.multiply(new BigDecimal(-1)) + "");
        } else {
            return Integer.parseInt(resultado + "");
        }

    }

    public static int quantidadeEntradaDeUmProdutoXEmUmPeriodoDeTempoY(Timestamp inicio, Timestamp fim, CPProduto produto) {
        HibernateUtil.abre();
        String sql = " select "
                + "         sum(ep.quantidade)"
                + "     from"
                + "         cpentrada e,"
                + "         cpentradaproduto ep"
                + "     where"
                + "         e.id = ep.entrada_id and"
                + "         e.dataEntrada >= '" + UtilData.AnoNMesNDiaN(inicio) + " 0:0:0' and"
                + "         e.dataEntrada <= '" + UtilData.AnoNMesNDiaN(fim) + " 23:59:59' and "
                + "         ep.produto_id = " + produto.getId() + " ";
        Query q = HibernateUtil.getSessao().createSQLQuery(sql);
        int entrou = 0;
        Object res = q.list().get(0);
        if (res != null) {
            entrou = Integer.parseInt(q.list().get(0) + "");
        }
        HibernateUtil.fecha();
        return entrou;
    }

    public static int quantidadeSaidaDeUmProdutoXEmUmPeriodoDeTempoY(Timestamp inicio, Timestamp fim, CPProduto produto) {
        String sql = " select"
                + "         sum(sp.quantidade)"
                + "     from"
                + "         cpsaida s,"
                + "         cpsaidaproduto sp"
                + "     where "
                + "         sp.saida_id = s.id and "
                + "         s.dataSaida >= '" + UtilData.AnoNMesNDiaN(inicio) + " 0:0:0' and"
                + "         s.dataSaida < '" + UtilData.AnoNMesNDiaN(fim) + " 23:59:59' and "
                + "         sp.produto_id = " + produto.getId() + " ";
        int saiu = 0;
        Query q = HibernateUtil.getSessao().createSQLQuery(sql);
        Object res = q.list().get(0);
        if (res != null) {
            saiu = Integer.parseInt(q.list().get(0) + "");
        }
        HibernateUtil.fecha();
        return saiu;
    }

    public static int quantidadeSaidaDeUmProdutoXParaUmPontoZEmUmPeriodoDeTempoY(Timestamp inicio, Timestamp fim, CPProduto produto, CPPontoDeEstagio ponto) {
        String sql = " select"
                + "         sum(sp.quantidade)"
                + "     from"
                + "         cpsaida s,"
                + "         cpsaidaproduto sp"
                + "     where "
                + "         sp.saida_id = s.id and "
                + "         s.dataSaida >= '" + UtilData.AnoNMesNDiaN(inicio) + " 0:0:0' and"
                + "         s.dataSaida < '" + UtilData.AnoNMesNDiaN(fim) + " 23:59:59' and "
                + "         sp.produto_id = " + produto.getId() + " and"
                + "         s.destino_id = " + ponto.getId() + " ";
        Query q = HibernateUtil.getSessao().createSQLQuery(sql);
        int entrou = 0;
        try {
            entrou = Integer.parseInt(q.list().get(0) + "");
        } catch (NumberFormatException e) {
        }
        HibernateUtil.fecha();
        return entrou;
    }
    @SuppressWarnings("unused")
    private String sqlBuscaDadosGrafIO = "selectCONCAT(t2.nomeProduto , \" - \",  t2.medidaProduto , \" \", t2.abreviacaoUnidade) \"nomeProduto\",t2.quantidade \"quantidade\",t2.dataSaida \"dataSaida\"from(	select*from(	selectp.nome \"nomeProduto\",p.medida \"medidaProduto\",u.abreviacao \"abreviacaoUnidade\",p.id \"idProduto\",sp.quantidade \"quantidade\",s.dataSaida \"dataSaida\",pe.nome \"destino\",pe.id \"idDestino\",s.id \"idSaida\"fromcpsaidaproduto sp inner join cpsaida s inner joincppontodeestagio pe inner joincpproduto p inner joincpunidadedemedida uonsp.saida_id = s.id and sp.produto_id = p.id andpe.id = s.destino_id andp.unidadeDeMedida_id = u.id ) twheret.dataSaida >= '2010:07:19 00:00:00' andt.dataSaida <= '2010:07:24 00:00:00') t2wheret2.idProduto = 1 ort2.idProduto = 2 ort2.idProduto = 3";

    public static List<GrafIODados> buscaDadosIO(Timestamp inicio, Timestamp fim, List<CPProduto> p) {
        try {
            //resultado 
            List<GrafIODados> resultado = new LinkedList<GrafIODados>();


            Conexao c = new Conexao();
            Connection cnx = c.pegaConexao();
            Statement cs = cnx.createStatement();
            //seleciona o nome a quantidade e a data de todos os produtos que sairam entre a data x e a data Y se o produto for A ou B
            String sql = "select d.nomeDoProduto, d.quantidadeSaida, d.dataSaida, d.idDaSaida from dadossaida d where "
                    + "d.dataSaida > '" + inicio + "' AND d.dataSaida < '" + fim + "'";
            for (CPProduto produto : p) {
                //se for o primeiro
                if (p.indexOf(produto) == 0) {
                    sql += " AND d.idDoProduto = " + produto.getId();
                } else {
                    //se for o ultimo
                    if (p.indexOf(produto) == p.size()) {
                        sql += " OR d.idDoProduto = " + produto.getId() + " ";
                        //se n for o primeiro nem o ultimo
                    } else {
                        sql += " OR d.idDoProduto = " + produto.getId();
                    }
                }
            }
            sql += ";";
            System.out.println(sql);
            ResultSet rs = cs.executeQuery(sql);
            while (rs.next()) {
                resultado.add(new GrafIODados(rs.getString("nomeProduto"), new Timestamp(rs.getDate("dataSaida").getTime()), rs.getInt("quantidadeSaida"), rs.getInt("idProduto")));
            }
            return resultado;
        } catch (SQLException ex) {
            System.out.println("deu merda!" + ex.getMessage());
            return null;
        }
    }

    public static void consultaHQL() {
    }
}
