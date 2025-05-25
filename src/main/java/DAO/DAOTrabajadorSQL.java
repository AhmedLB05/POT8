package DAO;

import models.Trabajador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("pass"),
                        rs.getString("email"),
                        rs.getInt("movil")
                ));
            }
            rs.close();
            ps.close();

            for (Trabajador t : lista) {
                t.setPedidosAsignados(daoPedidoSQL.readPedidosByIdTrabajador(dao, t));
            }
            dao.close();
            return lista;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
            throw new RuntimeException(e);
        }
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
            dao.close();
            return true;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
            return false;
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
            dao.close();
            return true;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
            return false;
        }
    }

    @Override
    public boolean delete(DAOManager dao, Trabajador trabajador) {
        try {
            dao.open();
            String sentencia = "DELETE FROM Trabajador WHERE `Trabajador`.`id` = '" + trabajador.getId() + "'";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
            return false;
        }
    }

    @Override
    public Trabajador buscaTrabajadorPrueba(DAOManager dao) {
        try {
            dao.open();
            Trabajador trabajador = null;
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
            dao.close();
            return trabajador;
        } catch (Exception e) {
            try {
                dao.close();
            } catch (Exception ignored) {
            }
            return null;
        }
    }
}
