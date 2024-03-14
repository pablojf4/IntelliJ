package ejercicio3;

import java.sql.*;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String SQL_QUERY_01 = "select Date(payment_date)as fecha,SUM(amount) as suma from payment group by Date(payment_date) order by Date(payment_date) desc";

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(SQL_QUERY_01)) {
            System.out.printf("El tipo dinámico de la conexión es %s\n", connection.getClass().getName());
            System.out.printf("El tipo dinámico del statement es %s\n", statement.getClass().getName());
            System.out.printf("El tipo dinámico del resulset es %s\n", resultados.getClass().getName());


            while (resultados.next()) {
                double sumaDinero = resultados.getDouble("suma");
                Date payment_date = resultados.getDate("fecha");
                System.out.printf("Dinero: %f - Fecha:%s\n",sumaDinero,payment_date);

            }
        }
    }
}
