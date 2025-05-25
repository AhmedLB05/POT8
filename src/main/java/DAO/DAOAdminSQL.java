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
        String sentencia = "SELECT * FROM Admin";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Admin(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("clave"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer administradores", e);
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar conexión", e);
            }
        }

        return lista;
    }

    @Override
    public boolean insert(DAOManager dao, Admin admin) {
        String sentencia = "INSERT INTO Admin (id, nombre, clave, email) VALUES (?, ?, ?, ?)";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, admin.getId());
            ps.setString(2, admin.getNombre());
            ps.setString(3, admin.getClave());
            ps.setString(4, admin.getEmail());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar conexión", e);
            }
        }
    }
}
