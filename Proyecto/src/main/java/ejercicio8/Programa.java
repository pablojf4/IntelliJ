package ejercicio8;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASSWORD = "pwdsakilauser";
    private static final String QUERY = """
               select (email) from customer where customer_id=?;
            """;
    private static final String UPDATE = """
            UPDATE customer SET email = ? WHERE customer_id =?;
            """;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dime el id del cliente");
        int idCliente = Integer.parseInt(sc.nextLine());


        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD)) {

            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {


                ps.setInt(1, idCliente);
                try (ResultSet rs = ps.executeQuery()) {

                    if (!existeCliente(connection, idCliente)) {
                        System.out.println("El cliente no existe");
                    } else {
                        while (rs.next()) {
                            String correo = rs.getString("email");
                            System.out.printf("Correo Antigüo: %s\n", correo);

                        }
                    }



                }


            }
            try(PreparedStatement ps2= connection.prepareStatement(UPDATE)) {
                System.out.println("Dime el nuevo correo");
                String correoNuevo = sc.nextLine();
                ps2.setString(1,correoNuevo);
                ps2.setInt(2,idCliente);
                int filas = ps2.executeUpdate();
                System.out.println("El correo ha sido actualizado");
                System.out.printf("Se han actualizado %d filas\n",filas);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean existeCliente(Connection connection, int idCliente) {
        try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
