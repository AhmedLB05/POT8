package DAO;

import models.Admin;

import java.util.ArrayList;

public interface DAOAdmin {

    ArrayList<Admin> readAll(DAOManager dao);

    boolean insert(DAOManager dao, Admin admin);

    boolean delete(DAOManager dao, Admin admin);
}
