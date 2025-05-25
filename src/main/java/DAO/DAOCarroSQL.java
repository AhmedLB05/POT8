package DAO;

import models.Cliente;
import models.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCarroSQL implements DAOCarro {
    private final DAOProductoSQL daoProductoSQL = new DAOProductoSQL();

    @Override
    public ArrayList<Producto> readAll(DAOManager dao, Cliente cliente) {
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Integer> listaId = new ArrayList<>();
        String sentencia = "SELECT idProducto FROM Carro WHERE idCliente = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, cliente.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaId.add(rs.getInt("idProducto"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer productos del carro", e);
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }

        for (Producto p : daoProductoSQL.readAll(dao)) {
            if (listaId.contains(p.getId())) {
                productos.add(p);
            }
        }

        return productos;
    }

    @Override
    public boolean insert(DAOManager dao, Cliente cliente, Producto producto) {
        String sentencia = "INSERT INTO Carro (idCliente, idProducto) VALUES (?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, cliente.getId());
            ps.setInt(2, producto.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }
    }

    @Override
    public boolean delete(DAOManager dao, Cliente cliente, Producto producto) {
        String sentencia = "DELETE FROM Carro WHERE idCliente = ? AND idProducto = ? LIMIT 1";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, cliente.getId());
            ps.setInt(2, producto.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }
    }

    @Override
    public boolean deleteAll(DAOManager dao, Cliente cliente) {
        String sentencia = "DELETE FROM Carro WHERE idCliente = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, cliente.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }
    }
}
