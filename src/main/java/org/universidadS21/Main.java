package org.universidadS21;

import org.universidadS21.model.Producto;
import org.universidadS21.utils.ImportadorExcel;
import org.universidadS21.view.MenuConsola;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //String ruta = "C:\\Users\\maximiliano.giuliano\\Desktop\\lista_precios.xlsx";
        //List<Producto> productos = ImportadorExcel.leerListaPrecios(ruta);

        MenuConsola menu = new MenuConsola();
        menu.iniciar();
    }
}