package db;

import org.junit.Test;

import java.sql.Connection;

public class DbConnectionTest {
    @Test
    public void deveEstabelecerConexaoComOBancoCOrretamente(){
        Connection connection = DB.getConnection();
        if(connection != null) {
            System.out.println("Conexão com o banco realizada com sucesso!");
        }
        DB.closeConnection();
    }
}
