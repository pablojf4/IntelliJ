package ejercicio10;

import java.sql.*;
import java.util.Scanner;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String CONTRASENA = "pwdsakilauser";
    private static final Scanner sc = new Scanner(System.in);
    private static final String EXISTE_PAIS = "select country_id, country from country where country=?";
    private static final String CREAR_PAIS = "insert into country(country) values(?)";
    private static final String SELEC_PAISES= "select * from country";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENA)) {
            String pais = pedirPais();

            if (paisExiste(pais, connection)) {
                System.out.println("El país ya existe");
            } else {
                crearPais(pais, connection);
            }
        }

    }

    private static void crearPais(String pais, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELEC_PAISES, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
             ResultSet resultSet = ps.executeQuery();
            resultSet.moveToInsertRow();
            resultSet.updateString("country", pais);
            resultSet.insertRow();


        }
    }

    private static String pedirPais() {
        String pais = "";
        while (pais.isEmpty()) {
            System.out.println("Dime el nombre de un país");
            pais = sc.nextLine();
        }
        return pais;
    }

    private static boolean paisExiste(String pais, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(EXISTE_PAIS)) {
            ps.setString(1, pais);
            try (ResultSet resultSet = ps.executeQuery()) {
                return resultSet.isBeforeFirst();
            }
        }
    }
}
