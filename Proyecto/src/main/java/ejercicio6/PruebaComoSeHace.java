package ejercicio6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PruebaComoSeHace {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String QUERY = "select first_name, last_name, email, active, a.address, a.address2, a.district, a.postal_code, a.phone, c2.city, c3.country  from customer c " +
            "inner join sakila.address a on c.address_id = a.address_id " +
            "inner join sakila.city c2 on a.city_id = c2.city_id " +
            "inner join sakila.country c3 on c2.country_id = c3.country_id " +
            "where customer_id=?";

    public static void main(String[] args) {
        try (Connection connection= DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD)) {

            try (PreparedStatement ps= connection.prepareStatement(QUERY)){
                ps.setInt(1,3);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
