package DAO;

import models.Cliente;
import models.Pedido;
import models.Producto;
import models.Trabajador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOPedidoSQL implements DAOPedido {

    private final DAOProductoSQL daoProductoSQL = new DAOProductoSQL();

    @Override
    public ArrayList<Pedido> readAll(DAOManager dao) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate fechaPedido = rs.getTimestamp("fechaPedido").toLocalDateTime().toLocalDate();

                LocalDate fechaEntregaEstimada = null;
                if (rs.getTimestamp("fechaEntregaEstimada") != null) {
                    fechaEntregaEstimada = rs.getTimestamp("fechaEntregaEstimada").toLocalDateTime().toLocalDate();
                } else {
                    fechaEntregaEstimada = fechaPedido.plusDays(5);
                }

                int estado = rs.getInt("estado");
                String comentario = rs.getString("comentario");

                // Cargar productos
                ArrayList<Producto> productos = new ArrayList<>();
                String sqlProd = "SELECT idProducto FROM PedidoProducto WHERE idPedido = ?";
                PreparedStatement psProd = dao.getConn().prepareStatement(sqlProd);
                psProd.setInt(1, id);
                ResultSet rsProd = psProd.executeQuery();
                ArrayList<Integer> productosIds = new ArrayList<>();
                while (rsProd.next()) {
                    productosIds.add(rsProd.getInt("idProducto"));
                }
                rsProd.close();
                psProd.close();

                for (Producto p : daoProductoSQL.readAll(dao)) {
                    if (productosIds.contains(p.getId())) productos.add(p);
                }

                Pedido pedido = new Pedido(id, fechaPedido, comentario, productos);
                pedido.setEstado(estado);
                pedido.setFechaEntregaEstimada(fechaEntregaEstimada);

                pedidos.add(pedido);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
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
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate fechaPedido = rs.getTimestamp("fechaPedido").toLocalDateTime().toLocalDate();

                LocalDate fechaEntregaEstimada = null;
                if (rs.getTimestamp("fechaEntregaEstimada") != null) {
                    fechaEntregaEstimada = rs.getTimestamp("fechaEntregaEstimada").toLocalDateTime().toLocalDate();
                } else {
                    fechaEntregaEstimada = fechaPedido.plusDays(5);
                }

                int estado = rs.getInt("estado");
                String comentario = rs.getString("comentario");

                ArrayList<Producto> productos = new ArrayList<>();
                String sqlProd = "SELECT idProducto FROM PedidoProducto WHERE idPedido = ?";
                PreparedStatement psProd = dao.getConn().prepareStatement(sqlProd);
                psProd.setInt(1, id);
                ResultSet rsProd = psProd.executeQuery();

                ArrayList<Integer> productosIds = new ArrayList<>();
                while (rsProd.next()) {
                    productosIds.add(rsProd.getInt("idProducto"));
                }
                rsProd.close();
                psProd.close();

                for (Producto p : daoProductoSQL.readAll(dao)) {
                    if (productosIds.contains(p.getId())) productos.add(p);
                }

                Pedido pedido = new Pedido(id, fechaPedido, comentario, productos);
                pedido.setEstado(estado);
                pedido.setFechaEntregaEstimada(fechaEntregaEstimada);

                pedidos.add(pedido);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            // Aquí puedes manejar el error o dejarlo vacío para ignorar
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
        return pedidos;
    }

    @Override
    public boolean insert(DAOManager dao, Pedido pedido, Cliente cliente) {
        try {
            dao.open();
            String sqlPedido = "INSERT INTO Pedido (fechaPedido, fechaEntregaEstimada, estado, comentario, idCliente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = dao.getConn().prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(pedido.getFechaPedido().atStartOfDay()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(pedido.getFechaEntregaEstimada().atStartOfDay()));
            ps.setInt(3, pedido.getEstado());
            ps.setString(4, pedido.getComentario());
            ps.setInt(5, cliente.getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int idPedido = -1;
            if (rs.next()) {
                idPedido = rs.getInt(1);
            } else {
                throw new RuntimeException("No se pudo obtener ID generado del pedido");
            }

            for (Producto producto : pedido.getProductos()) {
                String sqlProd = "INSERT INTO PedidoProducto (idPedido, idProducto) VALUES (?, ?)";
                PreparedStatement psProd = dao.getConn().prepareStatement(sqlProd);
                psProd.setInt(1, idPedido);
                psProd.setInt(2, producto.getId());
                psProd.executeUpdate();
                psProd.close();
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean update(DAOManager dao, Pedido pedido) {
        try {
            dao.open();
            String sql = "UPDATE Pedido SET fechaPedido=?, fechaEntregaEstimada=?, estado=?, comentario=? WHERE id=?";
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(pedido.getFechaPedido().atStartOfDay()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(pedido.getFechaEntregaEstimada().atStartOfDay()));
            ps.setInt(3, pedido.getEstado());
            ps.setString(4, pedido.getComentario());
            ps.setInt(5, pedido.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean updateTrabajador(DAOManager dao, Pedido pedido, Trabajador trabajador) {
        try {
            dao.open();
            String sql = "UPDATE Pedido SET idTrabajador=? WHERE id=?";
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, trabajador.getId());
            ps.setInt(2, pedido.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public ArrayList<Pedido> readPedidosByIdCliente(DAOManager dao, Cliente c) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE idCliente = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate fechaPedido = rs.getTimestamp("fechaPedido").toLocalDateTime().toLocalDate();

                LocalDate fechaEntregaEstimada = null;
                if (rs.getTimestamp("fechaEntregaEstimada") != null) {
                    fechaEntregaEstimada = rs.getTimestamp("fechaEntregaEstimada").toLocalDateTime().toLocalDate();
                } else {
                    fechaEntregaEstimada = fechaPedido.plusDays(5);
                }

                int estado = rs.getInt("estado");
                String comentario = rs.getString("comentario");

                // Cargar productos asociados al pedido
                ArrayList<Producto> productos = new ArrayList<>();
                String sqlProd = "SELECT idProducto FROM PedidoProducto WHERE idPedido = ?";
                PreparedStatement psProd = dao.getConn().prepareStatement(sqlProd);
                psProd.setInt(1, id);
                ResultSet rsProd = psProd.executeQuery();
                ArrayList<Integer> productosIds = new ArrayList<>();
                while (rsProd.next()) {
                    productosIds.add(rsProd.getInt("idProducto"));
                }
                rsProd.close();
                psProd.close();

                // Obtener todos los productos (ya que DAOProductoSQL.readAll no abre ni cierra conexión)
                for (Producto p : daoProductoSQL.readAll(dao)) {
                    if (productosIds.contains(p.getId())) productos.add(p);
                }

                Pedido pedido = new Pedido(id, fechaPedido, comentario, productos);
                pedido.setEstado(estado);
                pedido.setFechaEntregaEstimada(fechaEntregaEstimada);

                pedidos.add(pedido);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
        return pedidos;
    }
    //Recio
}
