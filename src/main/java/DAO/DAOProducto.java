package DAO;

import models.Producto;

import java.util.ArrayList;

public interface DAOProducto {

    ArrayList<Producto> readAll(DAOManager dao);

    boolean insert(DAOManager dao, Producto producto);

    boolean update(DAOManager dao, Producto producto);

    boolean delete(DAOManager dao, Producto producto);
}
