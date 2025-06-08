package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {
    // Metodo para insertar un producto nuevo
    public void insertarProducto(Producto producto) throws SQLException{
        final String SQL = "INSERT INTO producto (nombre, categoria, precio_costo, precio_venta, stock, stock_min) VALUES (?,?,?,?,?,?)";
        // Hago uso del recurso try with resources, para evitar colocar el finally para cerrar las conexiones luego del catch
        try (Connection conexionBD = ConexionBD.getConexion();
             PreparedStatement statement = conexionBD.prepareStatement(SQL)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getCategoria());
            statement.setDouble(3, producto.getPrecioCosto());
            statement.setDouble(4, producto.getPrecioVenta());
            statement.setInt(5, producto.getStock());
            statement.setInt(6, producto.getStockMin());

            String msg = (statement.executeUpdate() == 1) ?
                    "Producto Insertado Correctamente" : "Error al insertar el producto";
            System.out.println(msg);
        } catch (SQLException e) {
            throw new SQLException("Error en la conexion: " + e.getMessage());
        }
    }

    // Metodo para listar los productos
    public List<Producto> listarProductos() throws SQLException{
        List<Producto> listaProductos = new ArrayList<>();
        final String SQL = "SELECT * FROM producto";
        // Hago uso del recurso try with resources, para evitar colocar el finally para cerrar las conexiones luego del catch
        try (Connection conexionBD = ConexionBD.getConexion();
             PreparedStatement statement = conexionBD.prepareStatement(SQL);
             ResultSet result = statement.executeQuery(SQL)) {

            while (result.next()) {
                Producto producto = new Producto(
                        result.getInt("id_producto"),
                        result.getString("nombre"),
                        result.getString("categoria"),
                        result.getDouble("precio_costo"),
                        result.getDouble("precio_venta"),
                        result.getInt("stock"),
                        result.getInt("stock_min")
                );
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            throw new SQLException("Error con la conexion: " + e.getMessage());
        }
        return listaProductos;
    }

    // Metodo para buscar un producto por ID
    public Producto buscarProductoId(int productoId) throws SQLException {
        Producto producto = null;
        final String SQL = "SELECT * FROM producto WHERE id_producto = ?";

        // Hago uso del recurso try with resources, para evitar colocar el finally para cerrar las conexiones luego del catch
        try(Connection conexionBD = ConexionBD.getConexion();
            PreparedStatement statement = conexionBD.prepareStatement(SQL)){

            statement.setInt(1, productoId);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                producto = new Producto(
                        result.getInt("id_producto"),
                        result.getString("nombre"),
                        result.getString("categoria"),
                        result.getDouble("precio_costo"),
                        result.getDouble("precio_venta"),
                        result.getInt("stock"),
                        result.getInt("stock_min")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("Error en la conexion: " + e.getMessage());
        }
        return producto;
    }

    // Metodo para eliminar producto por ID
    public void eliminarProducto(int productoId) throws SQLException{
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection conexionBD = ConexionBD.getConexion();
             PreparedStatement stmt = conexionBD.prepareStatement(sql)) {

            stmt.setInt(1, productoId);
            int result = stmt.executeUpdate();
            String msg = (result == 1) ? "Producto eliminado correctamente" : "No se pudo eliminar el producto";
            System.out.printf(msg);
        } catch (SQLException e) {
            throw new SQLException("Error con la conexion: " + e.getMessage());
        }
    }

    // Metodo para actualizar producto por ID
    public void actualizarPrecioCostoProducto(int productoId, Double precioCosto) throws SQLException{
        String sql = "UPDATE producto SET precio_costo = ?, precio_venta = ? WHERE id_producto = ?";

        try(Connection conexionBD = ConexionBD.getConexion();
            PreparedStatement statement = conexionBD.prepareStatement(sql)){
            Producto producto = buscarProductoId(productoId);

            if(producto != null){
                statement.setDouble(1,precioCosto);
                statement.setDouble(2, precioCosto * 1.3);
                statement.setInt(3, productoId);

                int result = statement.executeUpdate();
                String msg = result == 1 ? "Producto actualizado correctamente" : "No se pudo actualizar el producto";
                System.out.println(msg);
            }else{
                System.out.println("No existe producto con el id: " + productoId);
            }
        }catch (SQLException e){
            throw new SQLException("Error con la conexion: " + e.getMessage());
        }
    }
}
