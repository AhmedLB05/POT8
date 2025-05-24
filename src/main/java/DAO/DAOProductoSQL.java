package DAO;

import models.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DAOProductoSQL implements DAOProducto {
    @Override
    public ArrayList<Producto> readAll(DAOManager dao) {
        ArrayList<Producto> lista = new ArrayList<>();

        String sentencia = "SELECT * FROM Producto";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"), rs.getString("descripcion"), rs.getFloat("precio"), rs.getInt("relevancia")));
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
        return lista;
    }

    @Override
    public boolean insert(DAOManager dao, Producto producto) {
        String sql = "INSERT INTO Producto (id, marca, modelo, descripcion, precio, relevancia) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, producto.getId());
            ps.setString(2, producto.getMarca());
            ps.setString(3, producto.getModelo());
            if (producto.getDescripcion() != null) {
                ps.setString(4, producto.getDescripcion());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
            ps.setFloat(5, producto.getPrecio());
            ps.setInt(6, producto.getRelevancia());

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
    public boolean update(DAOManager dao, Producto producto) {
        String sql = "UPDATE Producto SET marca = ?, modelo = ?, descripcion = ?, precio = ?, relevancia = ? WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setString(1, producto.getMarca());
            ps.setString(2, producto.getModelo());
            if (producto.getDescripcion() != null) {
                ps.setString(3, producto.getDescripcion());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            ps.setFloat(4, producto.getPrecio());
            ps.setInt(5, producto.getRelevancia());
            ps.setInt(6, producto.getId());

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
    public boolean delete(DAOManager dao, Producto producto) {
        String sql = "DELETE FROM Producto WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, producto.getId());

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
}
