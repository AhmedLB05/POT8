package DAO;

import models.Pedido;
import models.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPedidoProductosSQL implements DAOPedidoProductos {
    private final DAOProductoSQL daoProducto = new DAOProductoSQL();

    @Override
    public ArrayList<Producto> readAll(DAOManager dao, int idPedido) {
        ArrayList<Integer> lista = new ArrayList<>();
        ArrayList<Producto> productos = new ArrayList<>();
        String sentencia = "SELECT id_producto FROM Pedido_Productos WHERE id_pedido = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("id_producto"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        for (Producto p : daoProducto.readAll(dao)) {
            if (lista.contains(p.getId())) {
                productos.add(p);
            }
        }

        return productos;
    }

    @Override
    public boolean insert(DAOManager dao, Pedido pedido) {
        String sentencia = "INSERT INTO Pedido_Productos (id_pedido, id_producto) VALUES (?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            for (Producto producto : pedido.getProductos()) {
                ps.setInt(1, pedido.getId());
                ps.setInt(2, producto.getId());
                ps.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
