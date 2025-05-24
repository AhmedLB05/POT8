package DAO;

import models.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOClienteSQL implements DAOCliente {

    private final DAOPedidoSQL daoPedidoSQL = new DAOPedidoSQL();
    private final DAOCarroSQL daoCarroSQL = new DAOCarroSQL();

    @Override
    public ArrayList<Cliente> readAll(DAOManager dao) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            dao.open();
            String sentencia = "SELECT * FROM Cliente";
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Cliente(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("clave"),
                            rs.getString("nombre"),
                            rs.getString("localidad"),
                            rs.getString("provincia"),
                            rs.getString("direccion"),
                            rs.getInt("movil")
                    ));
                }
            }
            dao.close();

            // Para cada cliente leemos pedidos y carrito usando DAOs
            for (Cliente c : clientes) {
                c.setPedidos(daoPedidoSQL.readPedidosByIdCliente(dao, c));
                c.setCarro(daoCarroSQL.readAll(dao, c));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    @Override
    public boolean insert(DAOManager dao, Cliente cliente) {
        try {
            dao.open();
            // Para evitar errores por comillas, usar PreparedStatement mejor:
            String sql = "INSERT INTO Cliente (id, email, clave, nombre, localidad, provincia, direccion, movil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getClave());
            ps.setString(4, cliente.getNombre());
            ps.setString(5, cliente.getLocalidad());
            ps.setString(6, cliente.getProvincia());
            ps.setString(7, cliente.getDireccion());
            ps.setInt(8, cliente.getMovil());
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
    public boolean update(DAOManager dao, Cliente cliente) {
        try {
            dao.open();
            String sql = "UPDATE Cliente SET email = ?, clave = ?, nombre = ?, localidad = ?, provincia = ?, direccion = ?, movil = ? WHERE id = ?";
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setString(1, cliente.getEmail());
            ps.setString(2, cliente.getClave());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getLocalidad());
            ps.setString(5, cliente.getProvincia());
            ps.setString(6, cliente.getDireccion());
            ps.setInt(7, cliente.getMovil());
            ps.setInt(8, cliente.getId());
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
    public boolean delete(DAOManager dao, Cliente cliente) {
        try {
            dao.open();
            String sql = "DELETE FROM Cliente WHERE id = ?";
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
            ps.setInt(1, cliente.getId());
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
