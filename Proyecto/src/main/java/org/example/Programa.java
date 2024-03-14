package org.example;

import java.sql.*;
import java.time.LocalDateTime;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String SQL_QUERY_01 = "select country,last_update, country_id from country";

    public static void main(String[] args) throws SQLException {
        System.out.printf("Son las %s y este es el primer programa en IntelliJ Idea", LocalDateTime.now());

        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(SQL_QUERY_01)) {
            System.out.printf("El tipo dinámico de la conexión es %s\n",connection.getClass().getName());
            System.out.printf("El tipo dinámico del statement es %s\n",statement.getClass().getName());
            System.out.printf("El tipo dinámico del resulset es %s\n",resultados.getClass().getName());

            int numFila=1;
            while(resultados.next()){
                System.out.printf("En registro %s\n",numFila++);
                int countriId= resultados.getInt("country_id");
                String countryName= resultados.getString("country");
                Timestamp lastUpdate= resultados.getTimestamp("last_update");
                System.out.printf("%s - %s - %s\n", countriId, countryName, lastUpdate);
                System.out.println("-".repeat(100));
            }
        }
        System.out.println("Fin del programa");


    }
}
