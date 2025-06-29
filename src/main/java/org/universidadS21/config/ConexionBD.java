package org.universidadS21.config;

import org.universidadS21.exceptions.BaseDatosException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/globaltech_formosa";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    // Metodo para obtener una conexion a mi bd mysql
    public static Connection getConexion() throws BaseDatosException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new BaseDatosException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }
}
