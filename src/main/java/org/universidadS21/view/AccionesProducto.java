package org.universidadS21.view;

import org.universidadS21.controller.ProductoController;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Producto;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AccionesProducto {
    private final Scanner scanner;
    private final ProductoController productoController;

    public AccionesProducto(Scanner scanner){
        this.scanner = scanner;
        this.productoController = new ProductoController();
    }

    public void agregarProducto() throws BaseDatosException{
        Producto nuevoProducto = mapearProducto();
        productoController.insertarProducto(nuevoProducto);
    }

    private Producto mapearProducto(){
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

        Producto nuevoProducto = new Producto(nombre, categoria, precioCosto, stock, stockMinimo);
        nuevoProducto.calcularPrecioVenta();
        return nuevoProducto;
    }

    public void listarProductos() throws BaseDatosException{
        System.out.println("\n-- Lista de Productos --");
        List<Producto> productos = productoController.listarProductos();

        if (!productos.isEmpty()) {
            productos.forEach(System.out::println);
        } else {
            System.out.println("No hay productos cargados.");
        }
    }

    public void buscarProductoPorId() throws BaseDatosException{
        System.out.println("\n-- Buscar Producto por Id o Nombre, ingrese el id: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        Optional<Producto> producto = productoController.buscarProductoId(idProducto);
        if(producto.isPresent()){
            System.out.println("Producto encontrado: " + producto.get());
        } else {
            System.out.println("No existe el producto.");
        }
    }

    public void eliminarProducto() throws BaseDatosException{
        System.out.println("\n-- Eliminar Producto, ingrese el id: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        productoController.eliminarProductoPorId(idProducto);
    }
}
