package org.universidadS21.excel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Optional;

public class SelectorArchivo {

    public static Optional<File> seleccionarArchivoExcel() {
        JFileChooser fileChooser = configurarSelector();
        int resultado = mostrarDialogo(fileChooser);
        return obtenerArchivoSeleccionado(fileChooser, resultado);
    }

    private static JFileChooser configurarSelector() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo Excel");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filtroExcel = new FileNameExtensionFilter("Archivos Excel (*.xls, *.xlsx)", "xls", "xlsx");
        fileChooser.addChoosableFileFilter(filtroExcel);
        return fileChooser;
    }

    private static int mostrarDialogo(JFileChooser fileChooser) {
        return fileChooser.showOpenDialog(null);
    }

    private static Optional<File> obtenerArchivoSeleccionado(JFileChooser fileChooser, int resultado) {
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (esArchivoExcel(archivo)) {
                return Optional.of(archivo);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un archivo .xls o .xlsx", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
            }
        }
        return Optional.empty();
    }

    private static boolean esArchivoExcel(File archivo) {
        String nombre = archivo.getName().toLowerCase();
        return nombre.endsWith(".xls") || nombre.endsWith(".xlsx");
    }
}
