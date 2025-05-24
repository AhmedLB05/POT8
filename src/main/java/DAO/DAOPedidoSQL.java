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
        try {
            dao.open();
            String sentencia = "INSERT INTO `Pedido` (`id`, `fechaPedido`, `fechaEntregaEstimada`, `estado`, `comentario`, " +
                    "`idCliente`) VALUES ('" + pedido.getId() + "', '" + pedido.getFechaPedido() + "', '" +
                    pedido.getFechaEntregaEstimada() + "', '" + pedido.getEstado() + "', '" + pedido.getComentario() +
                    "', '" + cliente.getId() + "')";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
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
        try {
            dao.open();
            String sentencia = "UPDATE `Pedido` SET `fechaPedido` = '" + pedido.getFechaPedido() + "', `fechaEntregaEstimada` = '"
                    + pedido.getFechaEntregaEstimada() + "', `estado` = '" + pedido.getEstado() + "', `comentario` = '" +
                    pedido.getComentario() + "' WHERE `Pedido`.`id` = " + pedido.getId();
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
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
        try {
            dao.open();
            String sentencia = "UPDATE `Pedido` SET `idTrabajador` = '" + trabajador.getId() +
                    "' WHERE `Pedido`.`id` = " + pedido.getId();
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
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
        String sentencia = "SELECT * FROM Pedido WHERE `idTrabajador` = '" + trabajador.getId() + "'";

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
