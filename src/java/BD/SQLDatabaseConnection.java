package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabaseConnection {

    Connection conexion = null;

    private void conectar() {
        String connectionUrl
                = "jdbc:sqlserver://127.0.0.1:1433;"
                + "database=qrem;"
                + "user=sa;"
                + "password=n0m3l0;";

        try {
            conexion = DriverManager.getConnection(connectionUrl);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet consulta(String sql) throws SQLException {
        conectar();
        if (conexion != null) {
            ResultSet result;
            Statement stat = conexion.createStatement();
            result = stat.executeQuery(sql);
            return result;
        }
        return null;
    }

    public int insert(String sql) throws SQLException {
        conectar();
        if (conexion != null) {
            int res;
            Statement stat = conexion.createStatement();

            res = stat.executeUpdate(sql);
            return res;
        }
        return -1;
    }
    public void cierra() throws SQLException{
        conexion.close();
    }
}
