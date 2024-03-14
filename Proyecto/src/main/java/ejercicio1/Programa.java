package ejercicio1;

import java.sql.*;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String SQL_QUERY_01 = "select title,release_year,language_id, original_language_id, rating, description from film";
    public static void main(String[] args) throws SQLException{

        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(SQL_QUERY_01)) {
            System.out.printf("El tipo dinámico de la conexión es %s\n",connection.getClass().getName());
            System.out.printf("El tipo dinámico del statement es %s\n",statement.getClass().getName());
            System.out.printf("El tipo dinámico del resulset es %s\n",resultados.getClass().getName());

            int numFila=1;
            while(resultados.next()){
                System.out.printf("En registro %s\n",numFila++);
                String title= resultados.getString("title");
                int release_year= resultados.getInt("release_year");
                int language_id= resultados.getInt("language_id");
                int original_language_id= resultados.getInt("original_language_id");
                String rating= resultados.getString("rating");
                String description= resultados.getString("description");
                System.out.printf("%s - %s - %d - %s -%s\n", title, release_year, language_id, original_language_id, rating);
                System.out.printf("%s\n",description);
                System.out.println("-".repeat(100));
            }
        }
    }
}
