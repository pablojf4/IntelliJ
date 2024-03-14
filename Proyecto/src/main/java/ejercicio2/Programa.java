package ejercicio2;

import java.sql.*;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String SQL_QUERY_01 = "select f.title,f.release_year,l1.name as language,l2.name as original_language, f.rating, f.description from film f join language l1 on f.language_id= l1.language_id left join  language l2 on f.original_language_id = l2.language_id order by f.title asc";

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(SQL_QUERY_01)) {
            System.out.printf("El tipo dinámico de la conexión es %s\n", connection.getClass().getName());
            System.out.printf("El tipo dinámico del statement es %s\n", statement.getClass().getName());
            System.out.printf("El tipo dinámico del resulset es %s\n", resultados.getClass().getName());

            int numFila = 1;
            while (resultados.next()) {
                System.out.printf("En registro %s\n", numFila++);
                String title = resultados.getString("title");
                int release_year = resultados.getInt("release_year");
                String language_id = resultados.getString("language");
                String original_language_id = resultados.getString("original_language");
                if(original_language_id==null){
                    original_language_id="Desconocido";
                }
                String rating = resultados.getString("rating");
                String description = resultados.getString("description");
                System.out.printf("%s - %s - %s - %s -%s\n", title, release_year, language_id, original_language_id, rating);
                System.out.printf("%s\n", description);
                System.out.println("-".repeat(100));
            }
        }
    }
}
