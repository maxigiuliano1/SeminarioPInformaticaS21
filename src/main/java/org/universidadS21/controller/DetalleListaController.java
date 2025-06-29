package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.exceptions.BaseDatosException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleListaController {

    public void insertarDetalleLista(int idLista, int idProducto, double precioProveedor, double precioVenta) throws BaseDatosException{
        String sql = "INSERT INTO detalle_lista (id_lista, id_producto, precio_proveedor, precio_venta) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConexionBD.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idLista);
            statement.setInt(2, idProducto);
            statement.setDouble(3, precioProveedor);
            statement.setDouble(4, precioVenta);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new BaseDatosException("Error al insertar detalle de lista: " + e.getMessage(), e);
        }
    }

    public void mostrarDetalleLista(int idLista) throws BaseDatosException {
        String sql = """
                SELECT p.nombre, dl.precio_proveedor, dl.precio_venta
                FROM detalle_lista dl
                JOIN producto p ON dl.id_producto = p.id_producto
                WHERE dl.id_lista = ?
                """;

        try (Connection connection = ConexionBD.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idLista);
            ResultSet resultSet = statement.executeQuery();

            System.out.printf("%-30s %-15s %-15s%n", "Producto", "Precio Proveedor", "Precio Venta");
            System.out.println("------------------------------------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-30s %-15.2f %-15.2f%n",
                        resultSet.getString("nombre"),
                        resultSet.getDouble("precio_proveedor"),
                        resultSet.getDouble("precio_venta"));
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al mostar detalle de lista: " + e.getMessage(), e);
        }
    }
}
