package org.universidadS21;

import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.view.swing.VentanaPrincipal;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, BaseDatosException {
        //MenuConsola menu = new MenuConsola();
        //menu.iniciar();
        javax.swing.SwingUtilities.invokeLater(()->{
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setVisible(true);
        });
    }
}