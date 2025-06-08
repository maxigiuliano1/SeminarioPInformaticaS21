package org.universidadS21.view;

import org.universidadS21.controller.ProductoController;
import org.universidadS21.model.Producto;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AccionesProducto {
    private final Scanner scanner;
    private final ProductoController productoController;

    public AccionesProducto(Scanner scanner){
        this.scanner = scanner;
        this.productoController = new ProductoController();
    }

    public void agregarProducto() {
        try {
            System.out.println("\n-- Complete datos del producto --");
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Categoría: ");
            String categoria = scanner.nextLine();

            System.out.print("Precio costo: ");
            double precioCosto = Double.parseDouble(scanner.nextLine());

            System.out.print("Stock inicial: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Stock mínimo: ");
            int stockMinimo = Integer.parseInt(scanner.nextLine());

            Producto nuevoProducto = new Producto(nombre, categoria, precioCosto, 0.0, stock, stockMinimo);
            nuevoProducto.calcularPrecioVenta(); // Calcula el precio automaticamente

            productoController.insertarProducto(nuevoProducto);
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error. Intente nuevamente: " + e.getMessage());
        }
    }

    public void listarProductos() {
        System.out.println("\n-- Lista de Productos --");
        try{
            List<Producto> productos = productoController.listarProductos();

            if (!productos.isEmpty()) {
                productos.forEach(System.out::println);
            } else {
                System.out.println("No hay productos cargados.");
            }
        } catch (SQLException | NumberFormatException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void buscarProductoPorId(){
        System.out.println("\n-- Buscar Producto por Id o Nombre, ingrese el id: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        try{
            Producto producto = productoController.buscarProductoId(idProducto);
            if(producto != null){
                System.out.println("Producto encontrado: " + producto);
            } else {
                System.out.println("No se encontro el producto");
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarProducto(){
        System.out.println("\n-- Eliminar Producto, ingrese el id: ");
        int idProducto = Integer.parseInt(scanner.nextLine());
        try{
            productoController.eliminarProducto(idProducto);
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
