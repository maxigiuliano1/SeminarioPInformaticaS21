package org.universidadS21.view.swing;

import org.universidadS21.controller.ProveedorController;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Proveedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaProveedores extends JFrame {
    private final ProveedorController proveedorController;
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;

    public VentanaProveedores() {
        proveedorController = new ProveedorController();

        setTitle("Gestion de Proveedores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        mostrarTablaProveedores();
    }

    private void inicializarComponentes(){
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Celular", "Email"}, 0);
        tablaProveedores = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProveedores);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton btnAgregar = new JButton("Agregar Proveedor");
        JButton btnEliminar = new JButton("Eliminar Proveedor");
        JButton btnActualizarLista = new JButton("Refrescar Listado");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizarLista);

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarProveedor());
        btnEliminar.addActionListener(e -> eliminarProveedor());
        btnActualizarLista.addActionListener(e -> mostrarTablaProveedores());
    }

    private void agregarProveedor(){
        String nombreProveedor = JOptionPane.showInputDialog(this, "Ingrese nombre del proveedor:");
        if (nombreProveedor == null || nombreProveedor.trim().isEmpty()) return;

        String telefono = JOptionPane.showInputDialog(this, "Ingrese telefono de contacto:");
        if (telefono == null) telefono = "";

        String email = JOptionPane.showInputDialog(this, "Ingrese email de contacto:");
        if (email == null) email = "";

        try {
            Proveedor proveedor = new Proveedor(nombreProveedor, email, telefono);
            proveedorController.insertarProveedor(proveedor);
            mostrarTablaProveedores();
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar proveedor: " + e.getMessage());
        }
    }

    private void eliminarProveedor(){
        int fila = tablaProveedores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor.");
            return;
        }

        int idProveedorSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        try {
            proveedorController.eliminarProveedor(idProveedorSeleccionado);
            mostrarTablaProveedores();
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar proveedor por id: " + idProveedorSeleccionado + e.getMessage());
        }
    }

    private void mostrarTablaProveedores(){
        try {
            modeloTabla.setRowCount(0);
            List<Proveedor> proveedores = proveedorController.listarProveedores();

            for (Proveedor proveedor : proveedores) {
                modeloTabla.addRow(new Object[]{
                        proveedor.getIdProveedor(), proveedor.getNombre(), proveedor.getTelefono(), proveedor.getEmail()
                });
            }
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage());
        }
    }
}
