package org.universidadS21.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class MovimientoInventario {
    private int idMovimiento;
    private int idProducto;
    private String tipoMovimiento; // ENTRADA o SALIDA
    private int cantidad;
    private Date fecha;
    private int idUsuario;

    public MovimientoInventario(int idProducto, String tipoMovimiento, int cantidad, int idUsuario) {
        this.idProducto = idProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.idUsuario = idUsuario;
    }

    public MovimientoInventario(int idMovimiento, int idProducto, String tipoMovimiento, int cantidad, Date fecha, int idUsuario) {
        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}

