package org.universidadS21;

import org.universidadS21.controller.ImportarController;
import org.universidadS21.controller.ProductoController;
import org.universidadS21.model.Producto;
import org.universidadS21.utils.ImportadorExcel;
import org.universidadS21.view.MenuConsola;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        //String ruta = "C:\\Users\\maximiliano.giuliano\\Desktop\\lista_precios.xlsx";
        //List<Producto> productos = ImportadorExcel.leerListaPrecios(ruta); //no hace falta ya que puedo llamarlo directamente en insertar y actualizar productos
        //ImportarController importarController = new ImportarController();
        //importarController.insertarYActualizarProducto(ruta);

        MenuConsola menu = new MenuConsola();
        menu.iniciar();
    }
}