package ejercicio6;

import java.sql.*;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String SQUERY = "select first_name, last_name, email, active, a.address, a.address2, a.district, a.postal_code, a.phone, c2.city, c3.country  from customer c " +
            "inner join sakila.address a on c.address_id = a.address_id " +
            "inner join sakila.city c2 on a.city_id = c2.city_id " +
            "inner join sakila.country c3 on c2.country_id = c3.country_id " +
            "where customer_id=?";


    public static void main(String[] args) throws SQLException {

        try (Connection conection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
             PreparedStatement pS = conection.prepareStatement(SQUERY)) {
            pS.setInt(1, 3);
            try (ResultSet rs = pS.executeQuery()) {
                while (rs.next()) {
                    String c1 = rs.getString("first_name");
                    String c2= rs.  getString("last_name");
                    String c3= rs.getString("email");
                    boolean c4= rs.getBoolean("active");
                    String c5= rs.getString("address");
                    String c6= rs.getString("address2");
                    String c7= rs.getString("district");
                    String c8 =rs.getString("postal_code");
                    String c9= rs.getString("phone");
                    String c10= rs.getString("city");
                    String c11= rs.getString("country");
                    System.out.println(c1+ "\n"+c2+"\n"+c3+"\n"+c4+"\n"+c5+"\n"+c6+"\n"+c7+"\n"+c8+"\n"+c9+"\n"+c10+"\n"+c11);
                }

            }
        }
    }
}
