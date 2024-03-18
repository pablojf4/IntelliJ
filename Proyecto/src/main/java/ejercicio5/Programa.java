package ejercicio5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String UPDATE = "UPDATE language set name= substring(name, 1, length(name)-1) where name like '%X'";

    public static void main(String[] args) throws SQLException {

       try (Connection conection= DriverManager.getConnection(CADENA_CONEXION,USUARIO,PASSWORD);
            Statement statement= conection.createStatement()){

         int numFilasAfectadas=  statement.executeUpdate(UPDATE);
           System.out.printf("Se han modificado %d filas",numFilasAfectadas);
       } catch(SQLException e){
           System.out.printf("Error al conectar: %s\n", e.getMessage());
       }
    }
}
