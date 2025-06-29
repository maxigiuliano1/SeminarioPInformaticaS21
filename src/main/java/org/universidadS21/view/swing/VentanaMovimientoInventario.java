package org.universidadS21.view.swing;

import org.universidadS21.controller.MovimientoInventarioController;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.MovimientoInventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaMovimientoInventario extends JFrame{
    private final MovimientoInventarioController movimientoInventarioController;
    private JTable tablaMovimientos;
    private DefaultTableModel modeloTabla;

    public VentanaMovimientoInventario() {
        movimientoInventarioController = new MovimientoInventarioController();

        setTitle("Movimientos de Inventario");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        mostrarTablaMovimientoInventario();
    }

    private void inicializarComponentes(){
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Producto ID", "Tipo Movimiento", "Cantidad", "Usuario ID", "Fecha"}, 0);
        tablaMovimientos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaMovimientos);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton btnRegistrar = new JButton("Registrar Movimiento");
        JButton btnActualizarTablaMovimientoInventario = new JButton("Refrescar tabla");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizarTablaMovimientoInventario);

        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarMovimiento());
        btnActualizarTablaMovimientoInventario.addActionListener(e -> mostrarTablaMovimientoInventario());
    }

    private void registrarMovimiento(){
        String idProductoStr = JOptionPane.showInputDialog(this, "Ingrese ID del producto:");
        if (idProductoStr == null || idProductoStr.trim().isEmpty()) return;

        String tipo = JOptionPane.showInputDialog(this, "Tipo de movimiento (ENTRADA / SALIDA):");
        if (tipo == null || tipo.trim().isEmpty()) return;
        tipo = tipo.toUpperCase();

        if (!tipo.equals("ENTRADA") && !tipo.equals("SALIDA")) {
            JOptionPane.showMessageDialog(this, "Tipo invalido.");
            return;
        }

        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese cantidad:");
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) return;

        String idUsuarioStr = JOptionPane.showInputDialog(this, "Ingrese Id de Usuario:");
        if (idUsuarioStr == null || idUsuarioStr.trim().isEmpty()) return;

        try {
            int idProducto = Integer.parseInt(idProductoStr);
            int cantidad = Integer.parseInt(cantidadStr);
            int idUsuario = Integer.parseInt(idUsuarioStr);

            MovimientoInventario movimientoInventario = new MovimientoInventario(idProducto, tipo, cantidad, idUsuario);
            movimientoInventarioController.registrarMovimientoInventario(movimientoInventario);
            mostrarTablaMovimientoInventario();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID o cantidad invalidos.");
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar movimiento: " + e.getMessage());
        }
    }

    private void mostrarTablaMovimientoInventario() {
        try {
            modeloTabla.setRowCount(0);
            List<MovimientoInventario> listaMovimientoInventario = movimientoInventarioController.listarMovimientoInventario();

            for (MovimientoInventario movimientoInventario : listaMovimientoInventario){
                modeloTabla.addRow(new Object[]{
                        movimientoInventario.getIdMovimiento(), movimientoInventario.getIdProducto(), movimientoInventario.getTipoMovimiento(),
                        movimientoInventario.getCantidad(), movimientoInventario.getIdUsuario(), movimientoInventario.getFecha()
                });
            }
        } catch (BaseDatosException e){
            JOptionPane.showInputDialog(this, "Error al mostrar la lista de movimiento inventario");
        }
    }
}
