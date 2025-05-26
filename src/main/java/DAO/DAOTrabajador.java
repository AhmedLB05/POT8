package DAO;

import models.Trabajador;

import java.util.ArrayList;

public interface DAOTrabajador {
    ArrayList<Trabajador> readAll(DAOManager dao);

    boolean insert(DAOManager dao, Trabajador trabajador);

    boolean update(DAOManager dao, Trabajador trabajador);

    boolean delete(DAOManager dao, Trabajador trabajador);

}
