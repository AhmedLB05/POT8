package DAO;

import models.Cliente;

import java.util.ArrayList;

public interface DAOCliente {

    ArrayList<Cliente> readAll(DAOManager dao);

    boolean insert(DAOManager dao, Cliente cliente);

    boolean update(DAOManager dao, Cliente cliente);

    boolean delete(DAOManager dao, Cliente cliente);
}
