package org.universidadS21.view;

import org.universidadS21.controller.MovimientoInventarioController;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.MovimientoInventario;

import java.util.Scanner;
//TODO: REPASAR ESTA CLASE Y AVANZAR CON LA INTERFAZ
public class AccionesMovimientoInventario {
    private final Scanner scanner;
    private final MovimientoInventarioController controller;

    public AccionesMovimientoInventario(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new MovimientoInventarioController();
    }

    public void registrarMovimiento() throws BaseDatosException {
        System.out.println("\n--- Registrar Movimiento de Inventario ---");

        System.out.print("Ingrese ID del producto: ");
        int idProducto;
        try {
            idProducto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ ID inválido.");
            return;
        }

        System.out.print("Tipo de movimiento (ENTRADA / SALIDA): ");
        String tipo = scanner.nextLine().toUpperCase();

        if (!tipo.equals("ENTRADA") && !tipo.equals("SALIDA")) {
            System.out.println("⚠️ Tipo de movimiento inválido.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(scanner.nextLine());
            if (cantidad <= 0) {
                System.out.println("⚠️ La cantidad debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Cantidad inválida.");
            return;
        }

        System.out.print("Id del usuario (opcional): ");
        int idUsuario = scanner.nextInt();

        MovimientoInventario movimiento = new MovimientoInventario(idProducto, tipo, cantidad, idUsuario);
        boolean exito = controller.registrarMovimientoInventario(movimiento);

        if (exito) {
            System.out.println("Movimiento registrado y stock actualizado.");
        } else {
            System.out.println("No se pudo registrar el movimiento.");
        }
    }
}
