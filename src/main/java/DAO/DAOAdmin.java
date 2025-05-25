package DAO;

import models.Admin;

import java.util.ArrayList;

public interface DAOAdmin {

    public ArrayList<Admin> read(DAOManager dao);

    public boolean insert(DAOManager dao, Admin admin);
}
