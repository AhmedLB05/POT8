package DAO;

import models.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOAdminSQL implements DAOAdmin {

    @Override
    public ArrayList<Admin> read(DAOManager dao) {
        ArrayList<Admin> admins = new ArrayList<>();
        String sentencia = "SELECT * FROM Admin";
        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    admins.add(new Admin(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("clave"),
                            rs.getString("email")
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
        return admins;
    }

    @Override
    public boolean insert(DAOManager dao, Admin admin) {
        String sql = "INSERT INTO Admin (id, nombre, clave, email) VALUES (?, ?, ?, ?)";
        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sql);
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
                throw new RuntimeException(e);
            }
        }
    }
}
