package org.universidadS21.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.universidadS21.model.Producto;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportadorExcel {
    /**
     * Lee productos desde un archivo Excel (.xlsx)
     * @param rutaArchivo Ruta absoluta del archivo Excel
     * @return Lista de productos importados (nombre + precioCosto + precioVenta + categoria)
     */
    public static List<Producto> leerListaPrecios(String rutaArchivo) {
        List<Producto> productos = new ArrayList<>();
        String categoriaActual = "Sin categoria";

        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0); // Primera hoja
            for (Row row : hoja) {

                Cell celdaNombre = row.getCell(0); //Columna A
                Cell celdaPrecioUsd = row.getCell(1); //Columna B precios en dolares

                // Si la celda de nombre esta vacia, saltar
                if (celdaNombre == null || celdaNombre.getCellType() == CellType.BLANK) continue;

                String nombre = celdaNombre.getStringCellValue().trim();
                // Si la fila es una categoría (sin precio, y con nombre en mayusculas), actualizar categoríaActual
                boolean esCategoria = (celdaPrecioUsd == null || celdaPrecioUsd.getCellType() == CellType.BLANK) &&
                        (!nombre.isEmpty() || nombre.equals(nombre.toUpperCase()));

                if (esCategoria) {
                    categoriaActual = nombre;
                    continue; // no lo tratamos como producto y salteamos
                }

                // Validar precio
                if (celdaPrecioUsd == null || celdaPrecioUsd.getCellType() != CellType.NUMERIC) continue;

                double precioCosto = celdaPrecioUsd.getNumericCellValue();

                Producto p = new Producto();
                p.setNombre(nombre);
                p.setCategoria(categoriaActual);
                p.setPrecioCosto(precioCosto);
                p.calcularPrecioVenta(); // calcula automaticamente

                productos.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Excel: " + e.getMessage());
        }
        return productos;
    }
}
