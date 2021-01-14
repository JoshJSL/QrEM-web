package entidades;

import BD.SQLDatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class NewMain {
  public static void main(String[] args){
      System.out.println(Paramedico.buscarPaciente(2).getAlergias());
      
  }
}

