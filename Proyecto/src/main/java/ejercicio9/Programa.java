package ejercicio9;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Programa {
    private static final String QUERY = "SELECT city_id, city FROM city where city=?";
    private static final String QUERY2 = "SELECT country_id, country FROM country where country=?";
    private static final String INSERTAR_PAIS= "INSERT INTO country (country) VALUES (?)";
    private static final String INSERTAR_CIUDAD= "INSERT INTO city(city, country_id) values (?, ?)";
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
          insertarPais(nuevoPais);
        }

        int idPais= recuperarIdPais(nuevoPais);
        insertarCiudad(nuevaCiudad,idPais);
        System.out.println(idPais);
    }

    private static int recuperarIdPais(String nuevoPais) {
        int countryId=-1;
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(QUERY2)) {
            ps.setString(1, nuevoPais);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    countryId=rs.getInt("country_id");
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countryId;
    }

    private static void insertarCiudad(String nuevaCiudad, int idPais) {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(INSERTAR_CIUDAD)) {
            ps.setString(1, nuevaCiudad);
            ps.setInt(2,idPais);
            int filasAfectadas= ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.printf("La ciudad %s ha sido añadida\n", nuevaCiudad);
            } else {
                System.out.println("No se pudo agregar el país.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertarPais(String nuevoPais) {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(INSERTAR_PAIS)) {
            ps.setString(1, nuevoPais);
                int filasAfectadas= ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.printf("El país %s ha sido añadido\n", nuevoPais);
            } else {
                System.out.println("No se pudo agregar el país.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean paisExiste(String nuevoPais) {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASS);
             PreparedStatement ps = connection.prepareStatement(QUERY2)) {
            ps.setString(1, nuevoPais);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.isBeforeFirst();
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
                return rs.isBeforeFirst();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
