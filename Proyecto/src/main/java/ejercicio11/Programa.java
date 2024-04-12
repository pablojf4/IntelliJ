package ejercicio11;

import java.sql.*;
import java.util.Scanner;

public class Programa {
    private static final String CADENA_CONEXION = "jdbc:mariadb://localhost/sakila";
    private static final String USUARIO = "sakilauser";
    private static final String CONTRASENA = "pwdsakilauser";
    private static final Scanner sc = new Scanner(System.in);
    private static final String EXISTE_PAIS = "select country_id, country from country where country=?";
    private static final String SELEC_PAISES= "select * from country";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENA)) {
            String pais = pedirPaisExistente();

            if (!paisExiste(pais, connection)) {
                System.out.println("El país no existe😢😢😢");
            } else {
                String paisNuevo= pedirPaisNuevo();
                if(paisExiste(paisNuevo,connection)){
                    System.out.println("Ya existe lo siento");
                }else{
                    modificarPais(pais,paisNuevo, connection);
                }

            }
        }

    }

    private static String pedirPaisNuevo() {
        String pais = "";
        while (pais.isEmpty()) {
            System.out.println("Dime el nombre de un país");
            pais = sc.nextLine();
        }
        return pais;
    }
//esta forma sería con la sintaxis de sql: select * from country where country =?
//    private static void modificarPais(String pais, String paisNuevo, Connection connection) throws SQLException {
//        try (PreparedStatement ps = connection.prepareStatement(SELEC_PAISES, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
//            ps.setString(1,pais);
//             ResultSet resultSet = ps.executeQuery();
//             if(resultSet.next()){
//                 resultSet.updateString("country", paisNuevo);
//                 resultSet.updateRow();
//             }
//
//
//
//        }
//    }
   //otra forma de hacerlo
private static void modificarPais(String pais, String paisNuevo, Connection connection) throws SQLException {
    try (PreparedStatement ps = connection.prepareStatement(SELEC_PAISES, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            if(resultSet.getString("country").equals(pais)){
                resultSet.updateString("country", paisNuevo);
                resultSet.updateRow();
            }

        }



    }
}

    private static String pedirPaisExistente() {
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
