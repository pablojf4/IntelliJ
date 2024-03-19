package ejercicio7;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String QUERY = """
            select rental_date, nvl(return_date, 'Pendiente') as fechaDevolucion, f.title
            from rental r
            inner join sakila.inventory i on r.inventory_id = i.inventory_id
            inner join sakila.film f on i.film_id = f.film_id
            where r.customer_id=?
            order by rental_date desc
            """;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime el id del cliente");
        int idCliente = Integer.parseInt(sc.nextLine());

        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD)) {

            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {


                ps.setInt(1, idCliente);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Date fecha= rs.getDate("rental_date");
                        Date fechaDevolucion= rs.getDate("fechaDevolucion");
                        String titulo= rs.getString("title");
                        System.out.printf("%s\n%s\n%s\n", fecha, fechaDevolucion, titulo);

                    }

                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
