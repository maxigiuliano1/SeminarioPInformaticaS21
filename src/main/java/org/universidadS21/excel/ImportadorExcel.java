package org.universidadS21.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.universidadS21.model.Producto;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImportadorExcel {

    /**
     * Lee productos desde un archivo Excel (.xlsx)
     */
    public static Map<String, Producto> mapearProductosDesdeArchivoExcel(String rutaArchivo) {
        HashMap<String, Producto> productos = new HashMap<>();
        String nombreCategoria = "Sin categoria";

        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0); // Primera hoja
            for (Row fila : hoja) {
                Cell celdaNombreProducto = fila.getCell(0); //Columna A
                Cell celdaPrecioUsdProducto = fila.getCell(1); //Columna B

                if (esVacioNombreProducto(celdaNombreProducto)) continue;

                if (esCategoriaElNombre(celdaPrecioUsdProducto, celdaNombreProducto.getStringCellValue().trim())) {
                    nombreCategoria = celdaNombreProducto.getStringCellValue().trim();
                    continue; // no lo tratamos como producto y salteamos
                }

                if (esPrecioNoValido(celdaPrecioUsdProducto)) continue;

                double precioCosto = celdaPrecioUsdProducto.getNumericCellValue();

                Producto producto = new Producto(celdaNombreProducto.getStringCellValue().trim(), nombreCategoria, precioCosto, 0,0);
                producto.calcularPrecioVenta();

                productos.put(producto.getNombre(), producto);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Excel: " + e.getMessage());
        }
        return productos;
    }

    private static boolean esVacioNombreProducto(Cell celdaNombreProducto){
        return celdaNombreProducto == null || celdaNombreProducto.getCellType() == CellType.BLANK
                || celdaNombreProducto.getStringCellValue().trim().isEmpty();
    }

    private static boolean esCategoriaElNombre(Cell celdaPrecioUsdProducto, String nombre){
        return (celdaPrecioUsdProducto == null || celdaPrecioUsdProducto.getCellType() == CellType.BLANK) &&
                nombre.equals(nombre.toUpperCase());
    }

    private static boolean esPrecioNoValido(Cell celdaPrecioUsdProducto){
        return celdaPrecioUsdProducto == null || celdaPrecioUsdProducto.getCellType() != CellType.NUMERIC;
    }
}
