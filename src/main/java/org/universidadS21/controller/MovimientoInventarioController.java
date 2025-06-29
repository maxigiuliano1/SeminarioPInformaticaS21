package org.universidadS21.controller;

import org.universidadS21.config.ConexionBD;
import org.universidadS21.exceptions.BaseDatosException;
import org.universidadS21.model.MovimientoInventario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoInventarioController {

    public void registrarMovimientoInventario(MovimientoInventario movimiento) throws BaseDatosException{
        if (movimiento.getTipoMovimiento().equalsIgnoreCase("SALIDA")) {
            if (!validarStockDisponible(movimiento.getIdProducto(), movimiento.getCantidad())) {
                System.out.println("No hay suficiente stock para realizar la salida.");
                return;
            }
        }

        if (!insertarMovimientoInventario(movimiento)) {
            System.out.println("Error al registrar el movimiento de inventario.");
            return;
        }

        actualizarStock(movimiento.getIdProducto(), movimiento.getCantidad(), movimiento.getTipoMovimiento());
    }

    private boolean validarStockDisponible(int idProducto, int cantidadSolicitada) throws BaseDatosException {
        String sql = "SELECT stock FROM producto WHERE id_producto = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int stockActual = rs.getInt("stock");
                return stockActual >= cantidadSolicitada;
            }

        } catch (SQLException e) {
            System.out.println("Error al validar stock: " + e.getMessage());
        }

        return false;
    }

    private boolean insertarMovimientoInventario(MovimientoInventario movimiento) throws BaseDatosException{
        String sql = "INSERT INTO movimiento_inventario (tipo_movimiento, cantidad, fecha, id_usuario, id_producto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movimiento.getTipoMovimiento());
            stmt.setInt(2, movimiento.getCantidad());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setInt(4, movimiento.getIdUsuario());
            stmt.setInt(5, movimiento.getIdProducto());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar movimiento de inventario: " + e.getMessage());
            return false;
        }
    }

    private void actualizarStock(int idProducto, int cantidad, String tipoMovimiento) throws BaseDatosException{
        String operador = tipoMovimiento.equalsIgnoreCase("ENTRADA") ? "+" : "-";

        String sql = "UPDATE producto SET stock = stock " + operador + " ? WHERE id_producto = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
        }
    }

    public List<MovimientoInventario> listarMovimientoInventario() throws BaseDatosException{
        List<MovimientoInventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimiento_inventario";

        try (Connection connectionBD = ConexionBD.getConexion();
             Statement statement = connectionBD.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                MovimientoInventario movimientoInventario = new MovimientoInventario(
                        result.getInt("id_movimiento"),
                        result.getInt("id_producto"),
                        result.getString("tipo_movimiento"),
                        result.getInt("cantidad"),
                        result.getDate("fecha"),
                        result.getInt("id_usuario")
                );
                lista.add(movimientoInventario);
            }
            return lista;
        } catch (SQLException e) {
            throw new BaseDatosException("Error al listar movimiento de inventario: " + e.getMessage(), e);
        }
    }
}
