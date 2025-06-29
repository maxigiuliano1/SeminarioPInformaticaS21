package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorController {
    public void insertarProveedor(Proveedor proveedor) throws BaseDatosException {
        String sql = "INSERT INTO proveedor (nombre, email, telefono) VALUES (?, ?, ?)";

        try (Connection connectionBD = ConexionBD.getConexion();
             PreparedStatement statement = connectionBD.prepareStatement(sql)) {

            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getEmail());
            statement.setString(3, proveedor.getTelefono());

            String msg = (statement.executeUpdate() == 1) ?
                    "Proveedor Insertado Correctamente" : "Error al insertar al proveedor";
            System.out.println(msg);

        } catch (SQLException e) {
            throw new BaseDatosException("Error al insertar un proveedor: " + e.getMessage(), e);
        }
    }

    public List<Proveedor> listarProveedores() throws BaseDatosException{
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        try (Connection connectionBD = ConexionBD.getConexion();
             Statement statement = connectionBD.createStatement();
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

            return lista;
        } catch (SQLException e) {
            throw new BaseDatosException("Error al listar proveedores: " + e.getMessage(), e);
        }
    }

    public Proveedor buscarProductoId(int proveedorId) throws BaseDatosException {
        Proveedor proveedor = null;
        final String SQL = "SELECT * FROM proveedor WHERE id_proveedor = ?";

        try(Connection connectionBD = ConexionBD.getConexion();
            PreparedStatement statement = connectionBD.prepareStatement(SQL)){

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
            throw new BaseDatosException("Error al buscar proveedor por id: " + e.getMessage(), e);
        }
        return proveedor;
    }

    public void eliminarProveedor(int proveedorId) throws BaseDatosException{
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        try (Connection conexionBD = ConexionBD.getConexion();
             PreparedStatement stmt = conexionBD.prepareStatement(sql)) {

            stmt.setInt(1, proveedorId);
            int result = stmt.executeUpdate();
            String msg = (result == 1) ? "Proveedor eliminado correctamente" : "No se pudo eliminar el proveedor";
            System.out.printf(msg);
        } catch (SQLException e) {
            throw new BaseDatosException("Error al eliminar proveedor por id: " + e.getMessage(), e);
        }
    }
}
