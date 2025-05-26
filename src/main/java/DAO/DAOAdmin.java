package DAO;

import models.Admin;

import java.util.ArrayList;

public interface DAOAdmin {

    public ArrayList<Admin> readAll(DAOManager dao);

    public boolean insert(DAOManager dao, Admin admin);

    public boolean delete(DAOManager dao, Admin admin);
}
