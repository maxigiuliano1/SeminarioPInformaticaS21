package org.universidadS21.model;

public class Producto {
    private int idProducto;
    private String nombre;
    private String categoria;
    private Double precioCosto;
    private Double precioVenta;
    private int stock;
    private int stockMin;

    public Producto(){};

    public Producto(String nombre, String categoria, Double precioCosto, int stock, int stockMin) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCosto = precioCosto;
        this.stock = stock;
        this.stockMin = stockMin;
    }

    public Producto(int idProducto, String nombre, String categoria, Double precioCosto, int stock, int stockMin) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCosto = precioCosto;
        this.stock = stock;
        this.stockMin = stockMin;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(Double precioCosto) {
        this.precioCosto = precioCosto;
        calcularPrecioVenta();
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public void calcularPrecioVenta(){
        this.precioVenta = this.precioCosto * 1.3;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precioCosto=" + precioCosto +
                ", precioVenta=" + precioVenta +
                ", stock=" + stock +
                ", stockMin=" + stockMin +
                '}';
    }
}
