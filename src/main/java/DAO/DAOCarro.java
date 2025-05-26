package DAO;

import models.Cliente;
import models.Producto;

import java.util.ArrayList;

public interface DAOCarro {

    ArrayList<Producto> readAll(DAOManager dao, Cliente cliente);

    boolean insert(DAOManager dao, Cliente cliente, Producto producto);

    boolean delete(DAOManager dao, Cliente cliente, Producto producto);

    boolean deleteAll(DAOManager dao, Cliente cliente);
}
