package org.universidadS21.exceptions;

public class ConexionBdException extends Exception{
    // voy a renombrar esta clase para usarla como otra excepcion
    public ConexionBdException(String msg){
        super(msg);
    }
}
