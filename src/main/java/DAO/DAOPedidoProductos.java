package DAO;

import models.Pedido;
import models.Producto;

import java.util.ArrayList;

public interface DAOPedidoProductos {

    ArrayList<Producto> readAll(DAOManager dao, int idPedido);

    boolean insert(DAOManager dao, Pedido pedido);
}
