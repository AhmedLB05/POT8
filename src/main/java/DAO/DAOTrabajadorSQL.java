package DAO;

import models.Trabajador;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOTrabajadorSQL implements DAOTrabajador, Serializable {
    private final DAOPedidoSQL daoPedidoSQL = new DAOPedidoSQL();

    @Override
    public ArrayList<Trabajador> readAll(DAOManager dao) {
        ArrayList<Trabajador> trabajadors = new ArrayList<>();
        String sentencia = "SELECT * FROM Trabajador";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                trabajadors.add(new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("pass"),
                        rs.getString("email"),
                        rs.getInt("movil")
                ));
            }
            rs.close();
            ps.close();

            for (Trabajador t : trabajadors) {
                t.setPedidosAsignados(daoPedidoSQL.readPedidosByIdTrabajador(dao, t));
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
        return trabajadors;
    }

    @Override
    public boolean insert(DAOManager dao, Trabajador trabajador) {
        try {
            dao.open();
            String sentencia = "INSERT INTO `Trabajador` (`id`, `nombre`, `pass`, `email`, `movil`) VALUES ('" +
                    trabajador.getId() + "', '" + trabajador.getNombre() + "', '" + trabajador.getPass() + "', '" +
                    trabajador.getEmail() + "', '" + trabajador.getMovil() + "')";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            stmt.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean update(DAOManager dao, Trabajador trabajador) {
        try {
            dao.open();
            String sentencia = "UPDATE Trabajador SET `nombre` = '" + trabajador.getNombre() + "', `pass` = '" +
                    trabajador.getPass() + "', `email` = '" + trabajador.getEmail() + "', `movil` = '" + trabajador.getMovil()
                    + "' WHERE `Trabajador`.`id` = " + trabajador.getId();
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            stmt.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean delete(DAOManager dao, Trabajador trabajador) {
        try {
            dao.open();
            String sentencia = "DELETE FROM Trabajador WHERE `Trabajador`.`id` = '" + trabajador.getId() + "'";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            stmt.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public Trabajador buscaTrabajadorPrueba(DAOManager dao) {
        Trabajador trabajador = null;
        try {
            dao.open();
            String sentencia = "SELECT * FROM `Trabajador` WHERE `Trabajador`.`id` = '" + 100000 + "'";
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                trabajador = new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("pass"),
                        rs.getString("email"),
                        rs.getInt("movil")
                );
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
        }
        return trabajador;
    }
}
