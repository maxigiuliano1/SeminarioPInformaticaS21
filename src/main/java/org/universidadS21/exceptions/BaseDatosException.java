package org.universidadS21.exceptions;

public class BaseDatosException extends Exception{
    public BaseDatosException(String msg){
        super(msg);
    }

    public BaseDatosException(String msg, Throwable e){
        super(msg, e);
    }
}
