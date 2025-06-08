package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.model.Producto;
import org.universidadS21.utils.ImportadorExcel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ImportarController {
    private final ProductoController productoController = new ProductoController();

    public void insertarYActualizarProducto(String ruta) throws SQLException {
        // query para recuperar los nombres de todos los productos en la base de datos
        final String SQL = "Select nombre, precio_costo from producto";

        try(Connection conexionBD = ConexionBD.getConexion();
            Statement statement = conexionBD.prepareStatement(SQL);
            ResultSet result = statement.executeQuery(SQL)){

            // hago el llamado al metodo para recuperar los productos desde el archivo excel
            List<Producto> productos = ImportadorExcel.leerListaPrecios(ruta);

            //chequeo si tiene datos el resultado, si no tiene datos simplemente los inserto
            if(result.next()){
                while(result.next()){
                    String nombreProducto = result.getString("nombre");
                    Double precioCostoProducto = result.getDouble("precio_costo");

                    for(Producto p : productos){
                        if(!p.getNombre().equals(nombreProducto)){
                            Producto producto = new Producto(
                                    p.getNombre(),
                                    p.getCategoria(),
                                    p.getPrecioCosto(),
                                    p.getPrecioVenta(),
                                    0,
                                    0
                            );
                            System.out.println("producto que se insera: " + producto);
                            productoController.insertarProducto(producto);
                        }
                        else if(!p.getPrecioCosto().equals(precioCostoProducto)){
                            // aca iria el metodo para actualizar un producto en este caso el precio costo por ende se actualiza automaticamente el precio venta
                            System.out.println("Actualizo precio porque existe el producto.....");
                        }
                    }
                }
            } else{
                for(Producto p : productos){
                    System.out.println("producto que se insera: " + p);
                    productoController.insertarProducto(p);
                }
            }
        }catch (SQLException e){
            throw new SQLException("Error en la conexion: " + e.getMessage());
        }
    }
}
