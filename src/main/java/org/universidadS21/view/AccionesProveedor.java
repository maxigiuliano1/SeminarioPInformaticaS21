package org.universidadS21.view;

import org.universidadS21.controller.ProveedorController;
import org.universidadS21.model.Proveedor;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AccionesProveedor {
    private Scanner scanner;
    private ProveedorController proveedorController;

    public AccionesProveedor(Scanner scanner){
        this.scanner = scanner;
        this.proveedorController = new ProveedorController();
    }

    public void insertarProveedor(){
        try{
            System.out.println("\n***** Insertar proveedor *****");
            System.out.println("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Email: ");
            String email = scanner.nextLine();

            System.out.println("Telefono: ");
            String telefono = scanner.nextLine();

            Proveedor proveedor = new Proveedor(nombre,email,telefono);
            proveedorController.insertarProveedor(proveedor);
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarProveedores(){
        System.out.println("\n***** Lista de proveedores *****");
        try{
            List<Proveedor> proveedores = proveedorController.listarProveedores();

            if(!proveedores.isEmpty()){
                proveedores.forEach(System.out::println);
            } else{
                System.out.println("No se encontraron proveedores cargados.");
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void buscarProveedorPorId(){
        System.out.println("\n***** Buscar proveedor por ID *****");
        try{
            System.out.println("Ingrese ID: ");
            int idProveedor = Integer.parseInt(scanner.nextLine());

            Proveedor proveedor = proveedorController.buscarProductoId(idProveedor);
            if(proveedor!=null){
                System.out.println("Proveedor encontrado: " + proveedor);
            }else {
                System.out.println("No se encontro proveedor con el siguiente id: " + idProveedor);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarProveedor(){
        System.out.println("\n***** Eliminar proveedor por ID *****");
        try{
            System.out.println("Ingrese Id del proveedor a eliminar: ");
            int idProveedor = Integer.parseInt(scanner.nextLine());

            proveedorController.eliminarProducto(idProveedor);
        } catch (NumberFormatException | SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
