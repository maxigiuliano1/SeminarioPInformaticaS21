package org.universidadS21.view;

import java.util.Scanner;

public class MenuConsola {
    private final Scanner scanner = new Scanner(System.in);
    private AccionesProducto accionesProducto = new AccionesProducto(scanner);
    private AccionesProveedor accionesProveedor = new AccionesProveedor(scanner);

    private void mostrarMenu() {
        System.out.println("\n***** MENÚ DE GESTIÓN DE PRODUCTOS *****");
        System.out.println("1. Agregar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Agregar proveedor");
        System.out.println("6. Listar proveedores");
        System.out.println("7. Buscar proveedor por ID");
        System.out.println("8. Eliminar proveedor");
        System.out.println("0. Salir");
    }

    public void iniciar(){
        int opcion = -1;
        while (opcion != 0){
            try {
                mostrarMenu();
                System.out.println("Seleccione una opcion: ");
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    //acciones producto
                    case 1 -> accionesProducto.agregarProducto();
                    case 2 -> accionesProducto.listarProductos();
                    case 3 -> accionesProducto.buscarProductoPorId();
                    case 4 -> accionesProducto.eliminarProducto();
                    //acciones proveedor
                    case 5 -> accionesProveedor.insertarProveedor();
                    case 6 -> accionesProveedor.listarProveedores();
                    case 7 -> accionesProveedor.buscarProveedorPorId();
                    case 8 -> accionesProveedor.eliminarProveedor();
                    case 0 -> System.out.println("Sistema finalizado.");
                    default -> System.out.println("Opcion invalida. Intente nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero.");
                opcion = -1;
            }
        }
        scanner.close();
    }
}
