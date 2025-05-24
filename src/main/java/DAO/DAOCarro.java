package DAO;

import models.Cliente;
import models.Producto;

import java.util.ArrayList;

public interface DAOCarro {

    public ArrayList<Producto> readAll(DAOManager dao, Cliente cliente);

    public boolean insert(DAOManager dao, Cliente cliente, Producto producto);

    public boolean delete(DAOManager dao, Cliente cliente, Producto producto);

    public boolean deleteAll(DAOManager dao, Cliente cliente);
}
