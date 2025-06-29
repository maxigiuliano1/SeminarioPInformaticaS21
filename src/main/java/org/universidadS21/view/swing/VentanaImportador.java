package org.universidadS21.view.swing;

import org.universidadS21.controller.ImportadorController;
import org.universidadS21.exceptions.BaseDatosException;

import javax.swing.*;
import java.awt.*;

public class VentanaImportador extends JFrame {
    private final ImportadorController importadorController = new ImportadorController();

    public VentanaImportador(){
        setTitle("Importar o Actualizar Lista de Precios desde Excel");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JButton btnInsertarProductosDesdeExcel = new JButton("Insertar productos desde Archivo Excel");
        JButton btnActualizarPreciosDesdeExcel = new JButton("Actualizar precio de productos desde Archivo Excel");

        JPanel panelBotones = new JPanel(new GridLayout(2,1,10,10));

        panelBotones.add(btnInsertarProductosDesdeExcel);
        panelBotones.add(btnActualizarPreciosDesdeExcel);

        add(panelBotones, BorderLayout.CENTER);

        btnInsertarProductosDesdeExcel.addActionListener(e -> {
            try{
                importadorController.insertarProductosDesdeArchivoExcel();
                JOptionPane.showMessageDialog(this, "Productos insertados correctamente.");
            } catch (BaseDatosException exception){
                JOptionPane.showMessageDialog(this, "Error al insertar productos desde archivo excel.");
            }
        });

        btnActualizarPreciosDesdeExcel.addActionListener(e -> {
            try {
                importadorController.actualizarPreciosDesdeArchivoExcel();
                JOptionPane.showMessageDialog(this, "Productos actualizados correctamente.");
            } catch (BaseDatosException exception) {
                JOptionPane.showMessageDialog(this, "Error al actualizar precios de productos desde archivo excel.");
            }
        });
    }
}
