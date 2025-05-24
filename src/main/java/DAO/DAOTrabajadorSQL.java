package DAO;

import models.Trabajador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DAOTrabajadorSQL implements DAOTrabajador {

    private final DAOPedidoSQL daoPedidoSQL = new DAOPedidoSQL();

    @Override
    public ArrayList<Trabajador> readAll(DAOManager dao) {
        ArrayList<Trabajador> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM Trabajador";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Trabajador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("pass"),
                            rs.getString("email"),
                            rs.getInt("movil")
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
        for (Trabajador t : lista) {
            t.setPedidosAsignados(daoPedidoSQL.readPedidosByIdTrabajador(dao, t));
        }
        return lista;
    }

    @Override
    public boolean insert(DAOManager dao, Trabajador trabajador) {
        String sql = "INSERT INTO Trabajador (id, nombre, pass, email, movil) VALUES (?, ?, ?, ?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, trabajador.getId());
            ps.setString(2, trabajador.getNombre());
            ps.setString(3, trabajador.getPass());
            ps.setString(4, trabajador.getEmail());
            ps.setInt(5, trabajador.getMovil());

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
    public boolean update(DAOManager dao, Trabajador trabajador) {
        String sql = "UPDATE Trabajador SET nombre = ?, pass = ?, email = ?, movil = ? WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setString(1, trabajador.getNombre());
            ps.setString(2, trabajador.getPass());
            ps.setString(3, trabajador.getEmail());
            ps.setInt(4, trabajador.getMovil());
            ps.setInt(5, trabajador.getId());

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
    public boolean delete(DAOManager dao, Trabajador trabajador) {
        String sql = "DELETE FROM Trabajador WHERE id = ?";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, trabajador.getId());

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
