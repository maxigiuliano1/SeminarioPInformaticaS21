package org.universidadS21.model;

public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String email;
    private String telefono;

    public Proveedor(int idProveedor, String nombre, String email, String telefono) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Proveedor(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
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
                "iDproveedor=" + idProveedor +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
