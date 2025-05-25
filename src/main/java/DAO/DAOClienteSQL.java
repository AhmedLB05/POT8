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
        String sentencia = "SELECT * FROM Cliente";

        try {
            dao.open();
            try (PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("clave"),
                            rs.getString("nombre"),
                            rs.getString("localidad"),
                            rs.getString("provincia"),
                            rs.getString("direccion"),
                            rs.getInt("movil")
                    );
                    clientes.add(cliente);
                }

                // Cargar pedidos y carro usando la misma conexión abierta
                for (Cliente c : clientes) {
                    c.setPedidos(daoPedidoSQL.readPedidosByIdCliente(dao, c));
                    c.setCarro(daoCarroSQL.readAll(dao, c));
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer los clientes", e);
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }

        return clientes;
    }

    @Override
    public boolean insert(DAOManager dao, Cliente cliente) {
        String sentencia = "INSERT INTO Cliente (id, email, clave, nombre, localidad, provincia, direccion, movil) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            dao.open();
            try (PreparedStatement ps = dao.getConn().prepareStatement(sentencia)) {
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
            }
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
    public boolean update(DAOManager dao, Cliente cliente) {
        String sentencia = "UPDATE Cliente SET email = ?, clave = ?, nombre = ?, localidad = ?, provincia = ?, " +
                "direccion = ?, movil = ? WHERE id = ?";

        try {
            dao.open();
            try (PreparedStatement ps = dao.getConn().prepareStatement(sentencia)) {
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
            }
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
    public boolean delete(DAOManager dao, Cliente cliente) {
        String sentencia = "DELETE FROM Cliente WHERE id = ?";

        try {
            dao.open();
            try (PreparedStatement ps = dao.getConn().prepareStatement(sentencia)) {
                ps.setInt(1, cliente.getId());
                ps.executeUpdate();
                return true;
            }
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
