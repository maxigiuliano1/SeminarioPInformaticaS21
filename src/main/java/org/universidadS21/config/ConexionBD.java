package org.universidadS21.config;

import org.universidadS21.exceptions.ConexionBdException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/globaltech_formosa";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    // Metodo para obtener una conexion a mi bd mysql
    public static Connection getConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Metodo para cerrar una conexión (en caso de ser necesario usarlo)
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) { //Connection maneja las exepciones mediante SQLException
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
}
