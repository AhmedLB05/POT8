package DAO;

import models.Cliente;
import models.Pedido;
import models.Trabajador;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPedidoSQL implements DAOPedido, Serializable {
    private final DAOPedidoProductosSQL daoPedidoProductos = new DAOPedidoProductosSQL();

    @Override
    public ArrayList<Pedido> readAll(DAOManager dao) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sentencia = "SELECT * FROM Pedido";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            rs.getDate("fechaPedido").toLocalDate(),
                            rs.getDate("fechaEntregaEstimada").toLocalDate(),
                            rs.getInt("estado"),
                            rs.getString("comentario"),
                            daoPedidoProductos.readAll(dao, rs.getInt("id"))
                    ));
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

        return pedidos;
    }

    @Override
    public boolean insert(DAOManager dao, Pedido pedido, Cliente cliente) {
        String sentencia = "INSERT INTO Pedido (id, fechaPedido, fechaEntregaEstimada, estado, comentario, idCliente) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, pedido.getId());
            ps.setDate(2, java.sql.Date.valueOf(pedido.getFechaPedido()));
            ps.setDate(3, java.sql.Date.valueOf(pedido.getFechaEntregaEstimada()));
            ps.setInt(4, pedido.getEstado());
            ps.setString(5, pedido.getComentario());
            ps.setInt(6, cliente.getId());
            ps.executeUpdate();
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

    @Override
    public boolean update(DAOManager dao, Pedido pedido) {
        String sentencia = "UPDATE Pedido SET fechaPedido = ?, fechaEntregaEstimada = ?, estado = ?, comentario = ? WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setDate(1, java.sql.Date.valueOf(pedido.getFechaPedido()));
            ps.setDate(2, java.sql.Date.valueOf(pedido.getFechaEntregaEstimada()));
            ps.setInt(3, pedido.getEstado());
            ps.setString(4, pedido.getComentario());
            ps.setInt(5, pedido.getId());
            ps.executeUpdate();
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

    @Override
    public boolean updateTrabajador(DAOManager dao, Pedido pedido, Trabajador trabajador) {
        String sentencia = "UPDATE Pedido SET idTrabajador = ? WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, trabajador.getId());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
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

    @Override
    public ArrayList<Pedido> readPedidosByIdCliente(DAOManager dao, Cliente cliente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sentencia = "SELECT * FROM Pedido WHERE `idCliente` = '" + cliente.getId() + "'";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            rs.getDate("fechaPedido").toLocalDate(),
                            rs.getDate("fechaEntregaEstimada").toLocalDate(),
                            rs.getInt("estado"),
                            rs.getString("comentario"),
                            daoPedidoProductos.readAll(dao, rs.getInt("id"))
                    ));
                }
            }
            dao.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pedidos;
    }

    @Override
    public ArrayList<Pedido> readPedidosByIdTrabajador(DAOManager dao, Trabajador trabajador) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sentencia = "SELECT * FROM Pedido WHERE `idTrabajador` = '" + trabajador.getId() + "'";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            rs.getDate("fechaPedido").toLocalDate(),
                            rs.getDate("fechaEntregaEstimada").toLocalDate(),
                            rs.getInt("estado"),
                            rs.getString("comentario"),
                            daoPedidoProductos.readAll(dao, rs.getInt("id"))
                    ));
                }
            }
            dao.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pedidos;
    }
}
