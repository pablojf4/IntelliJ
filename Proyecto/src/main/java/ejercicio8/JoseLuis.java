package ejercicio8;

import java.sql.*;
import java.util.Scanner;

public class JoseLuis {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String PASS = "pwdsakilauser";
    private static final String QUERY = "select email from customer where customer_id=?";
    private static final String UPDATE = "update customer set email=? where customer_id=?";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        System.out.println("Dime el id del cliente");
        int idCliente = Integer.parseInt(scanner.nextLine());

        if (!existeCliente(idCliente)) {
            System.out.println("No existe el cliente");
            return;
        }

        System.out.println("introduce el nuevo correo eléctronico");
        String nuevoCorreo = scanner.nextLine();
        if (!modificarCorreoCliente(idCliente, nuevoCorreo)) {
            System.out.println("No se ha podido modificar el correo del cliente");
        } else {
            System.out.println("Se ha modificado con éxito");

            // Mostrar el correo electrónico actualizado del cliente
            String correoActualizado = obtenerCorreoCliente(idCliente);
            System.out.println("Correo electrónico actualizado: " + correoActualizado);
        }
    }

    private static boolean modificarCorreoCliente(int idCliente, String nuevoCorreo) throws SQLException {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, nuevoCorreo);
            ps.setInt(2, idCliente);
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

    private static boolean existeCliente(int idCliente) throws SQLException {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(QUERY)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Devuelve true si hay al menos una fila en el ResultSet
            }
        }
    }

    private static String obtenerCorreoCliente(int idCliente) throws SQLException {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(QUERY)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email"); // Devuelve el correo electrónico del cliente
                }
            }
        }
        return null; // Si no se encuentra el cliente, devuelve null
    }
}
