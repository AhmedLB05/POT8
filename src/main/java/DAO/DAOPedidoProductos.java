package DAO;

import models.Pedido;
import models.Producto;

import java.util.ArrayList;

public interface DAOPedidoProductos {

    public ArrayList<Producto> readAll(DAOManager dao, int idPedido);

    public boolean insert(DAOManager dao, Pedido pedido);
}
