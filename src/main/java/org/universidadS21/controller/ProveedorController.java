package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.model.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    public void insertarProveedor(Proveedor proveedor) throws SQLException{
        String sql = "INSERT INTO proveedor (nombre, , activo) VALUES (?, ?, ?)";

        try (Connection conexionBD = ConexionBD.getConexion();
             PreparedStatement statement = conexionBD.prepareStatement(sql)) {

            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getEmail());
            statement.setString(3, proveedor.getTelefono());

            String msg = (statement.executeUpdate() == 1) ?
                    "Proveedor Insertado Correctamente" : "Error al insertar al proveedor";
            System.out.println(msg);

        } catch (SQLException e) {
            throw new SQLException("Error en la conexion: " + e.getMessage());
        }
    }

    // Listar todos los proveedores
    public List<Proveedor> listarProveedores() throws SQLException{
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        try (Connection conexionBD = ConexionBD.getConexion();
             Statement statement = conexionBD.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Proveedor proveedor = new Proveedor(
                        result.getInt("id_proveedor"),
                        result.getString("nombre"),
                        result.getString("email"),
                        result.getString("telefono")
                );
                lista.add(proveedor);
            }
        } catch (SQLException e) {
            throw new SQLException("Error con la conexion: " + e.getMessage());
        }
        return lista;
    }

    // Metodo para buscar un proveedor por ID
    public Proveedor buscarProductoId(int proveedorId) throws SQLException {
        Proveedor proveedor = null;
        final String SQL = "SELECT * FROM proveedor WHERE id_proveedor = ?";

        try(Connection conexionBD = ConexionBD.getConexion();
            PreparedStatement statement = conexionBD.prepareStatement(SQL)){

            statement.setInt(1, proveedorId);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                proveedor = new Proveedor(
                        result.getInt("id_proveedor"),
                        result.getString("nombre"),
                        result.getString("email"),
                        result.getString("telefono")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("Error en la conexion: " + e.getMessage());
        }
        return proveedor;
    }

    // Metodo para eliminar proveedor por ID
    public void eliminarProducto(int proveedorId) throws SQLException{
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        try (Connection conexionBD = ConexionBD.getConexion();
             PreparedStatement stmt = conexionBD.prepareStatement(sql)) {

            stmt.setInt(1, proveedorId);
            int result = stmt.executeUpdate();
            String msg = (result == 1) ? "Proveedor eliminado correctamente" : "No se pudo eliminar el proveedor";
            System.out.printf(msg);
        } catch (SQLException e) {
            throw new SQLException("Error con la conexion: " + e.getMessage());
        }
    }
}
