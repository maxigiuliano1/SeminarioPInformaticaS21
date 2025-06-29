package org.universidadS21.view.swing;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal(){
        setTitle("Sistema de Gestion de Inventario - Globaltech Formosa");
        setSize(600,400);

        //Se setea que cuando esta ventana se cierre, finaliza la app por completo.
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Este metodo posiciona la ventana en la pantalla,
        //Con null esta se centra con respecto a la pantalla principal del sistema.
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        JPanel jPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnProductos = new JButton("Gestion de Productos.");
        JButton btnProveedores = new JButton("Gestion de Proveedores.");
        JButton btnImportarExcel = new JButton("Importar Lista de Precios desde Excel.");
        JButton btnMovimientos = new JButton("Movimiento de Inventario.");
        JButton btnSalir = new JButton("Salir.");

        btnProductos.addActionListener(e -> {
            VentanaProductos ventanaProductos = new VentanaProductos();
            ventanaProductos.setVisible(true);
        });
        btnProveedores.addActionListener(e -> {
            VentanaProveedores ventanaProveedores = new VentanaProveedores();
            ventanaProveedores.setVisible(true);
        });
        btnImportarExcel.addActionListener(e -> {
            VentanaImportador ventanaImportador = new VentanaImportador();
            ventanaImportador.setVisible(true);
        });
        btnMovimientos.addActionListener(e -> {
            VentanaMovimientoInventario ventanaMovimientoInventario = new VentanaMovimientoInventario();
            ventanaMovimientoInventario.setVisible(true);
        });
        btnSalir.addActionListener(e -> System.exit(0));

        jPanel.add(btnProductos);
        jPanel.add(btnProveedores);
        jPanel.add(btnImportarExcel);
        jPanel.add(btnMovimientos);
        jPanel.add(btnSalir);

        add(jPanel, BorderLayout.CENTER);
    }
}
