package DAO;

import models.Cliente;
import models.Pedido;
import models.Trabajador;

import java.util.ArrayList;

public interface DAOPedido {

    ArrayList<Pedido> readAll(DAOManager dao);

    ArrayList<Pedido> readPedidosByIdTrabajador(DAOManager dao, Trabajador trabajador);

    boolean insert(DAOManager dao, Pedido pedido, Cliente cliente);

    boolean update(DAOManager dao, Pedido pedido);

    boolean updateTrabajador(DAOManager dao, Pedido pedido, Trabajador trabajador);

    ArrayList<Pedido> readPedidosByIdCliente(DAOManager dao, Cliente c);
}
