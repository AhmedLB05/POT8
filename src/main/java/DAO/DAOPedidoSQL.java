package DAO;

import models.Cliente;
import models.Pedido;
import models.Trabajador;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOPedidoSQL implements DAOPedido {
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
                    Timestamp fechaPedidoTS = rs.getTimestamp("fechaPedido");
                    LocalDate fechaPedido = fechaPedidoTS.toLocalDateTime().toLocalDate();

                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            fechaPedido,
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
        String sql = "INSERT INTO Pedido (id, fechaPedido, fechaEntregaEstimada, estado, comentario, idCliente) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, pedido.getId());
            ps.setDate(2, Date.valueOf(pedido.getFechaPedido()));
            if (pedido.getFechaEntregaEstimada() != null) {
                ps.setDate(3, Date.valueOf(pedido.getFechaEntregaEstimada()));
            } else {
                ps.setNull(3, Types.DATE);
            }
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
        String sql = "UPDATE Pedido SET fechaPedido = ?, fechaEntregaEstimada = ?, estado = ?, comentario = ? WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setDate(1, Date.valueOf(pedido.getFechaPedido()));
            if (pedido.getFechaEntregaEstimada() != null) {
                ps.setDate(2, Date.valueOf(pedido.getFechaEntregaEstimada()));
            } else {
                ps.setNull(2, Types.DATE);
            }
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
        String sql = "UPDATE Pedido SET idTrabajador = ? WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
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
        String sql = "SELECT * FROM Pedido WHERE idCliente = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp fechaPedidoTS = rs.getTimestamp("fechaPedido");
                    LocalDate fechaPedido = fechaPedidoTS.toLocalDateTime().toLocalDate();

                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            fechaPedido,
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
    public ArrayList<Pedido> readPedidosByIdTrabajador(DAOManager dao, Trabajador trabajador) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE idTrabajador = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, trabajador.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp fechaPedidoTS = rs.getTimestamp("fechaPedido");
                    LocalDate fechaPedido = fechaPedidoTS.toLocalDateTime().toLocalDate();

                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            fechaPedido,
                            rs.getString("comentario"),
                            daoPedidoProductos.readAll(dao, rs.getInt("id"))
                    ));
                }
            }
        } catch (Exception e) {
            return pedidos;
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                return pedidos;
            }
        }

        return pedidos;
    }
}
