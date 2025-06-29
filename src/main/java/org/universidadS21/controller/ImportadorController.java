package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.exceptions.ExcelException;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Producto;
import org.universidadS21.excel.ImportadorExcel;
import org.universidadS21.excel.SelectorArchivo;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

public class ImportadorController {
    private final ProductoController productoController = new ProductoController();
    private final HistorialListaPreciosController historialListaPreciosController = new HistorialListaPreciosController();
    private final DetalleListaController detalleListaController = new DetalleListaController();

    public void insertarProductosDesdeArchivoExcel() throws BaseDatosException{
        File archivo = obtenerArchivoExcelSeleccionado();
        String rutaArchivo = archivo.getAbsolutePath();
        Map<String, Producto> productosImportados = ImportadorExcel.mapearProductosDesdeArchivoExcel(rutaArchivo);

        if (productosImportados.isEmpty()) {
            throw new ExcelException("No se encontraron productos en el archivo Excel.");
        }

        String nombreArchivo = archivo.getName();
        int idLista = historialListaPreciosController.crearHistorialListaPrecios(nombreArchivo, 1);

        for (Producto p : productosImportados.values()) {
            int idProducto = productoController.insertarProducto(p);
            detalleListaController.insertarDetalleLista(idLista, idProducto, p.getPrecioCosto(), p.getPrecioVenta());
        }
    }

    public void actualizarPreciosDesdeArchivoExcel() throws BaseDatosException {
        final String SQL = "Select * from producto";

        try (Connection connectionBD = ConexionBD.getConexion();
            Statement statement = connectionBD.prepareStatement(SQL);
            ResultSet result = statement.executeQuery(SQL)) {

            if(!result.next()){
                throw new BaseDatosException("No existen productos en la base de datos, inserte productos para luego poder actualizarlos.");
            }

            File archivo = obtenerArchivoExcelSeleccionado();

            Map<String, Producto> productos = mapearProductosDesdeArchivo(archivo);
            if (productos.isEmpty()) {
                throw new ExcelException("No se encontraron productos en el archivo Excel.");
            }

            int idHistorialListaPrecio = crearHistorialListaPrecios(archivo.getName());

            do {
                int idProducto = result.getInt("id_producto");
                Double precioCostoProducto = result.getDouble("precio_costo");
                String nombreProducto = result.getString("nombre");
                Producto producto = productos.get(nombreProducto);

                if (hayQueActualizarPrecioProducto(producto, precioCostoProducto)) {
                    productoController.actualizarPrecioCostoProducto(idProducto, producto.getPrecioCosto());
                    detalleListaController.insertarDetalleLista(idHistorialListaPrecio, idProducto, producto.getPrecioCosto(), producto.getPrecioVenta());
                    System.out.println("Se actualizo el producto: " + producto);
                }

            } while (result.next());

        } catch (SQLException e){
            throw new ExcelException("Error al actualizar precios desde archivo excel: " + e.getMessage());
        }
    }

    private File obtenerArchivoExcelSeleccionado() {
        Optional<File> archivo = SelectorArchivo.seleccionarArchivoExcel();
        return archivo.orElseThrow(()-> new ExcelException("No se selecciono o no se encontro la ruta del archivo Excel."));
    }

    private Map<String, Producto> mapearProductosDesdeArchivo(File archivo){
        String rutaArchivo = archivo.getAbsolutePath();
        return ImportadorExcel.mapearProductosDesdeArchivoExcel(rutaArchivo);
    }

    private int crearHistorialListaPrecios(String nombreArchivo) throws BaseDatosException {
        return historialListaPreciosController.crearHistorialListaPrecios(nombreArchivo, 1);
    }

    private boolean hayQueActualizarPrecioProducto(Producto producto, Double precioCostoProducto) {
        return producto != null && !producto.getPrecioCosto().equals(precioCostoProducto);
    }
}
