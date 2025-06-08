package org.universidadS21.model;

public class Proveedor {
    private int iDproveedor;
    private String nombre;
    private String email;
    private String telefono;

    public Proveedor(){}

    public Proveedor(int iDproveedor, String nombre, String email, String telefono) {
        this.iDproveedor = iDproveedor;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Proveedor(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public int getiDproveedor() {
        return iDproveedor;
    }

    public void setiDproveedor(int iDproveedor) {
        this.iDproveedor = iDproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "iDproveedor=" + iDproveedor +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
