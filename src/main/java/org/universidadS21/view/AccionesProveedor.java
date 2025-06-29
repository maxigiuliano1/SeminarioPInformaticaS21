package org.universidadS21.view;

import org.universidadS21.controller.ProveedorController;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Proveedor;

import java.util.List;
import java.util.Scanner;

public class AccionesProveedor {
    private final Scanner scanner;
    private final ProveedorController proveedorController;

    public AccionesProveedor(Scanner scanner){
        this.scanner = scanner;
        this.proveedorController = new ProveedorController();
    }

    public void insertarProveedor() throws BaseDatosException {
        System.out.println("\n***** Insertar proveedor *****");
        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Telefono: ");
        String telefono = scanner.nextLine();

        Proveedor proveedor = new Proveedor(nombre,email,telefono);
        proveedorController.insertarProveedor(proveedor);
    }

    public void listarProveedores() throws BaseDatosException{
        System.out.println("\n***** Lista de proveedores *****");

        List<Proveedor> proveedores = proveedorController.listarProveedores();

        if(!proveedores.isEmpty()){
            proveedores.forEach(System.out::println);
        } else{
            System.out.println("No se encontraron proveedores cargados.");
        }
    }

    public void buscarProveedorPorId() throws BaseDatosException{
        System.out.println("\n***** Buscar proveedor por ID *****");

        System.out.println("Ingrese ID: ");
        int idProveedor = Integer.parseInt(scanner.nextLine());

        Proveedor proveedor = proveedorController.buscarProductoId(idProveedor);
        if(proveedor!=null){
            System.out.println("Proveedor encontrado: " + proveedor);
        }else {
            System.out.println("No se encontro proveedor con el siguiente id: " + idProveedor);
        }
    }

    public void eliminarProveedor() throws BaseDatosException{
        System.out.println("\n***** Eliminar proveedor por ID *****");

        System.out.println("Ingrese Id del proveedor a eliminar: ");
        int idProveedor = Integer.parseInt(scanner.nextLine());

        proveedorController.eliminarProveedor(idProveedor);
    }
}
