package ejercicio9;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Programa {
    private static final String QUERY = "SELECT * FROM city where city=?";
    private static final String QUERY2 = "SELECT * FROM country where country=?";
    private static final String INSERTAR_PAIS= "INSERT INTO country (country) VALUES (?)";
    private static String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static String USUARIO = "sakilauser";
    private static String PASS = "pwdsakilauser";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Dime el nombre de la nueva ciudad");
        String nuevaCiudad = sc.nextLine();

        if (existeCiudad(nuevaCiudad)) {
            System.out.println("La ciudad existe.");
            return;

        }
        System.out.println("Dime el nombre del país");
        String nuevoPais = sc.nextLine();
        if (paisExiste(nuevoPais)) {
            System.out.println("Se va a utilizar el país ya existente");
        }else{
//            insertarPais(nuevoPais);
        }


    }

//    private static void insertarPais(String nuevoPais) {
//        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
//             PreparedStatement ps = connection.prepareStatement(INSERTAR_PAIS)) {
//            ps.setString(1, nuevoPais);
//            try (ResultSet rs = ps.executeQuery()) {
//                String pais= rs.getString("country");
//                System.out.printf("El país %s ha sido añadido",pais);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static boolean paisExiste(String nuevoPais) {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(QUERY2)) {
            ps.setString(1, nuevoPais);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean existeCiudad(String nuevaCiudad) {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(QUERY)) {
            ps.setString(1, nuevaCiudad);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
