package utilidades.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private Connection conexao = null;

    public Conexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fisiotec","root", "");
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public Connection pegaConexao(){
        return this.conexao;
    }

    public void fechaConexao(){
       
    }
    /*
    public static void main(String args[]){
        Conexao c= new Conexao();
        c.pegaConexao();
        System.out.println("ok");
    }
     * 
     */
}
