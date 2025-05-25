package DAO;

import models.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOAdminSQL implements DAOAdmin {

    @Override
    public ArrayList<Admin> read(DAOManager dao) {
        ArrayList<Admin> lista = new ArrayList<>();
        String sentencia = "SELECT id, nombre, clave, email FROM Admin"; // Más claro y explícito

        try {
            dao.open();
            try (PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new Admin(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("clave"),
                            rs.getString("email")
                    ));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer administradores", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }

        return lista;
    }

    @Override
    public boolean insert(DAOManager dao, Admin admin) {
        String sentencia = "INSERT INTO Admin (id, nombre, clave, email) VALUES (?, ?, ?, ?)";

        try {
            dao.open();
            try (PreparedStatement ps = dao.getConn().prepareStatement(sentencia)) {
                ps.setInt(1, admin.getId());
                ps.setString(2, admin.getNombre());
                ps.setString(3, admin.getClave());
                ps.setString(4, admin.getEmail());

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar administrador", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                return false;
            }
        }
    }
}
