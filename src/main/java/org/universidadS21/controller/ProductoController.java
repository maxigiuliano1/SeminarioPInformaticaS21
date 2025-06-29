package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoController {

    public int insertarProducto(Producto producto) throws BaseDatosException{
        final String SQL = "INSERT INTO producto (nombre, categoria, precio_costo, precio_venta, stock, stock_min) VALUES (?,?,?,?,?,?)";
        // Hago uso del recurso try with resources, para evitar colocar el finally para cerrar las conexiones luego del catch
        try (Connection connectionBD = ConexionBD.getConexion();
             PreparedStatement statement = connectionBD.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getCategoria());
            statement.setDouble(3, producto.getPrecioCosto());
            statement.setDouble(4, producto.getPrecioVenta());
            statement.setInt(5, producto.getStock());
            statement.setInt(6, producto.getStockMin());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 0) {
                throw new BaseDatosException("No se pudo insertar el producto.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    throw new BaseDatosException("No se pudo obtener el ID generado del producto.");
                }
            }
        } catch (SQLException e){
            throw new BaseDatosException("Error al insertar el producto: " + e.getMessage(), e);
        }
    }

    public List<Producto> listarProductos() throws BaseDatosException{
        List<Producto> listaProductos = new ArrayList<>();
        final String SQL = "SELECT * FROM producto";

        // Hago uso del recurso try with resources, para evitar colocar el finally para cerrar las conexiones luego del catch
        try (Connection connectionBD = ConexionBD.getConexion();
             PreparedStatement statement = connectionBD.prepareStatement(SQL);
             ResultSet result = statement.executeQuery(SQL)) {

            while (result.next()) {
                Producto producto = mapearProducto(result);
                listaProductos.add(producto);
            }

            return listaProductos;
        } catch (SQLException e) {
            throw new BaseDatosException("Error al recuperar la lista de productos: " + e.getMessage(), e);
        }
    }

    public Optional<Producto> buscarProductoId(int productoId) throws BaseDatosException {
        final String SQL = "SELECT * FROM producto WHERE id_producto = ?";
        // Hago uso del recurso try with resources, para evitar colocar el finally para cerrar las conexiones luego del catch
        try(Connection connectionBD = ConexionBD.getConexion();
            PreparedStatement statement = connectionBD.prepareStatement(SQL)){

            statement.setInt(1, productoId);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return Optional.of(mapearProducto(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar producto por ID: " + productoId, e);
        }
    }

    private Producto mapearProducto(ResultSet resultSet) throws SQLException{
        Producto producto = new Producto();
        producto.setIdProducto(resultSet.getInt("id_producto"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setCategoria(resultSet.getString("categoria"));
        producto.setPrecioCosto(resultSet.getDouble("precio_costo"));
        producto.setStock(resultSet.getInt("stock"));
        producto.setStockMin(resultSet.getInt("stock_min"));
        return producto;
    }

    public void eliminarProductoPorId(int productoId) throws BaseDatosException{
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection connectionBD = ConexionBD.getConexion();
             PreparedStatement stmt = connectionBD.prepareStatement(sql)) {

            stmt.setInt(1, productoId);
            int result = stmt.executeUpdate();
            String msg = (result == 1) ? "Producto eliminado correctamente" : "No se pudo eliminar el producto";
            System.out.printf(msg);
        } catch (SQLException e) {
            throw new BaseDatosException("Error al eliminar el producto con id: " + productoId, e);
        }
    }

    public void actualizarPrecioCostoProducto(int productoId, Double precioCosto) throws BaseDatosException{
        String sql = "UPDATE producto SET precio_costo = ?, precio_venta = ? WHERE id_producto = ?";

        try(Connection connectionBD = ConexionBD.getConexion();
            PreparedStatement statement = connectionBD.prepareStatement(sql)){
            Optional<Producto> producto = buscarProductoId(productoId);

            if(producto.isPresent()){
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
            throw new BaseDatosException("Error al actualizar el precio de costo con productoId: " + productoId
                    + " y precioCosto: " + precioCosto, e);
        }
    }
}
