package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.exceptions.BaseDatosException;

import java.sql.*;

public class HistorialListaPreciosController {

    public int crearHistorialListaPrecios(String nombre, int idProveedor) throws BaseDatosException{
        String sql = "INSERT INTO historial_lista_precios (nombre_lista, fecha, id_proveedor) VALUES (?, NOW(), ?)";

        try (Connection connection = ConexionBD.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, nombre);
            statement.setInt(2, idProveedor);

            int filas = statement.executeUpdate();
            if (filas == 0)
                throw new BaseDatosException("No se pudo insertar registro en el historial de lista de precios.");;

            System.out.println("Se registro correctamente el historial de la lista de precios.");

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    throw new BaseDatosException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al crear historial de lista de precios: " + e.getMessage(), e);
        }
    }

    public void mostrarHistorialListasPrecios() throws BaseDatosException {
        String sql = """
                SELECT hlp.id_lista, hlp.nombre_lista, hlp.fecha, p.nombre AS proveedor
                FROM historial_lista_precios hlp
                JOIN proveedor p ON hlp.id_proveedor = p.id_proveedor
                ORDER BY hlp.fecha DESC
                """;

        try (Connection connection = ConexionBD.getConexion();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.printf("%-5s %-25s %-20s %-20s%n", "ID", "Nombre Lista", "Fecha", "Proveedor");
            System.out.println("-----------------------------------------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-5d %-25s %-20s %-20s%n",
                        resultSet.getInt("id_lista"),
                        resultSet.getString("nombre_lista"),
                        resultSet.getTimestamp("fecha"),
                        resultSet.getString("proveedor"));
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al mostrar el historial de lista de precios: " + e.getMessage(), e);
        }
    }
}
