/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.sql;


/**
 *
 * @author Thiago
 */
public class SQLGraficoIO {
/*
    private boolean produtoadd = false;

    private String getExtruturaConsulta() {
        return "select t2.nomeProduto \"nomeProduto\", t2.medidaProduto "
                + "\"medidaProduto\", t2.abreviacaoUnidade \"abreviacaoUnidade\", t2.quantidade "
                + "\"quantidadeSaida\", t2.dataSaida \"dataSaida\", t2.idProduto \"idProduto\", "
                + "t2.idSaida \"idSaida\" from (  select * from ( " + getExtruturaBaseDeConsulta() + "  ) t !  ) t2";
    }

    private String getExtruturaBaseDeConsulta() {
        return "select p.nome \"nomeProduto\", "
                + "p.medida \"medidaProduto\", u.abreviacao \"abreviacaoUnidade\", p.id \"idProduto\", "
                + "sp.quantidade \"quantidade\", s.dataSaida \"dataSaida\", pe.nome \"destino\", pe.id "
                + "\"idDestino\", s.id \"idSaida\" from cpsaidaproduto sp inner join cpsaida s inner "
                + "join cppontodeestagio pe inner join cpproduto p inner join cpunidadedemedida u on "
                + "sp.saida_id = s.id and sp.produto_id = p.id and pe.id = s.destino_id and "
                + "p.unidadeDeMedida_id = u.id";
    }

    private String setDataNoSQL(String sql, Date inicio, Date fim) {
        //where t.dataSaida >= '?1' and t.dataSaida <= '?2'
        sql = " where t.dataSaida >= '?1' and t.dataSaida <= '?2'".replace("?1", UtilData.aaaaMMddHHmmssParaConsulta(inicio, false)).replace("?2", UtilData.aaaaMMddHHmmssParaConsulta(fim, true));
        sql = getExtruturaConsulta().replace("!", sql);
        return sql;
    }

    private String addProdutoCondicional(String sql, CPProduto p) {
        if (produtoadd) {
            sql += " or t2.idProduto = " + p.getId();
        } else {
            sql += " where  t2.idProduto = " + p.getId();
        }
        produtoadd = true;
        return sql;
    }

    private String addListaProdutosCondicional(String sql, List<CPProduto> produtos) {
        for (CPProduto p : produtos) {
            sql = addProdutoCondicional(sql, p);
        }
        return sql;
    }

    public List<GrafIODados>  preencheDadosDoGrafico(Date inicio, Date fim, List<CPProduto> produtos ) {
        try {
            List<GrafIODados> dadosDoGrafico = new LinkedList<GrafIODados>();
            Conexao c = new Conexao();
            Connection cnx = c.pegaConexao();
            Statement cs = cnx.createStatement();
            String sql = "";
            sql = setDataNoSQL(sql, inicio, fim);
            sql = addListaProdutosCondicional(sql, produtos);
            System.out.println("sql: "+sql);
            ResultSet rs = cs.executeQuery(sql + " order by t2.dataSaida");
            while (rs.next()) {
                dadosDoGrafico.add(new GrafIODados(rs.getString("nomeProduto"), rs.getDate("dataSaida"), rs.getInt("quantidadeSaida"), rs.getInt("idProduto")));
            }
            return dadosDoGrafico;
        } catch (Exception ex) {
            System.out.println("PAU ao executar: " + ex.getMessage());
            return null;
        }
    }

    public List<Object> buscaUltimaQuantidadeMovimentadaDeUmPrdotuo(Date d, CPProduto p) {
        try {
            Conexao c = new Conexao();
            Connection cnx = c.pegaConexao();
            Statement cs = cnx.createStatement();
            String sql = "select t2.quantidade \"quantidade\", MAX(t2.dataSaida) \"data\" from ("
                    + "" + getExtruturaBaseDeConsulta() + " )  t2 where t2.dataSaida <= '" + UtilData.aaaaMMddHHmmssParaConsulta(d, false) + "' and t2.idProduto = " + p.getId();
            System.out.println("sql 2:" +sql);
            ResultSet rs = cs.executeQuery(sql);
            rs.next();
            List<Object> res = new LinkedList<Object>();
            res.add(rs.getInt("quantidade"));
            res.add(UtilData.diaNmesNanoN(rs.getDate("data")));
            return res;
        } catch (Exception ex) {
            System.out.println("buscando objeto sem movimentação " + ex.getMessage());
            return null;
        }
    }

    public List<Date> defineCategorias(Date inicio, Date fim) {
        try {
            List<Date> l = new LinkedList<Date>();
            Conexao c = new Conexao();
            Connection cnx = c.pegaConexao();
            Statement cs = cnx.createStatement();
            String sql = setDataNoSQL(getExtruturaBaseDeConsulta(), inicio, fim);
            sql = "select DISTINCT t3.dataSaida from ( " + sql + " ) t3 order by 1";
            ResultSet rs = cs.executeQuery(sql);
            while (rs.next()) {
                //System.out.println(rs.getDate(1));
                l.add(rs.getDate(1));
            }
            return l;
        } catch (Exception ex) {
            System.out.println("PAU ao defineCategorias: " + ex.getMessage());
            return null;
        }
    }
    * 
    */
}
