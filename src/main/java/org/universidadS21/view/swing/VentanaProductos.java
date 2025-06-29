package org.universidadS21.view.swing;

import org.universidadS21.controller.ProductoController;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaProductos extends JFrame {
    private final ProductoController productoController;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public VentanaProductos() {
        productoController = new ProductoController();

        setTitle("Gestion de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        mostrarTablaProductos();
    }

    private void inicializarComponentes(){
        setLayout(new BorderLayout());

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio Costo", "Precio Venta", "Stock"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        add(scroll, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel();

        JButton btnAgregar = new JButton("Agregar Producto");
        JButton btnEliminar = new JButton("Eliminar Producto");
        JButton btnMostrarListaProductos = new JButton("Mostrar Lista Productos");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrarListaProductos);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnMostrarListaProductos.addActionListener(e -> mostrarTablaProductos());
    }

    private void agregarProducto(){
        String nombreProducto = JOptionPane.showInputDialog(this, "Ingrese nombre del producto:");
        if (nombreProducto == null || nombreProducto.trim().isEmpty()) return;

        String categoriaProducto = JOptionPane.showInputDialog(this, "Ingrese nombre de la categoria:");
        String precioCostoStr = JOptionPane.showInputDialog(this, "Ingrese precio de costo:");
        String stockStr = JOptionPane.showInputDialog(this, "Ingrese stock inicial:");

        try {
            double precioCosto = Double.parseDouble(precioCostoStr);
            int stock = Integer.parseInt(stockStr);
            int STOCK_MIN = 1;

            Producto producto = new Producto(nombreProducto, categoriaProducto, precioCosto, stock, STOCK_MIN);
            producto.calcularPrecioVenta();
            productoController.insertarProducto(producto);
            mostrarTablaProductos();
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Datos con error al querer agregar el producto.");
        }
    }

    private void eliminarProducto(){
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        int idProductoSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        try {
            productoController.eliminarProductoPorId(idProductoSeleccionado);
            mostrarTablaProductos();
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar producto por id: " + idProductoSeleccionado + e.getMessage());
        }
    }

    private void mostrarTablaProductos() {
        try {
            modeloTabla.setRowCount(0); // Limpia tabla
            List<Producto> productos = productoController.listarProductos();

            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                        producto.getIdProducto(), producto.getNombre(), producto.getPrecioCosto(), producto.getPrecioVenta(), producto.getStock()
                });
            }
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }
}
