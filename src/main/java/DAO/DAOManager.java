package DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static DAOManager singlenton; // Singleton DAO
    private final String URL;
    private final String USER;
    private final String PASS;
    private transient Connection conn; // Marcar como transient para evitar errores de serialización

    public DAOManager() {
        this.conn = null;
        this.URL = "jdbc:mysql://localhost:3307/practicaOblT8?autoReconnect=true&useSSL=false";
        this.USER = "root";
        this.PASS = "root";
    }

    public static DAOManager getSinglentonInstance() {
        if (singlenton == null) singlenton = new DAOManager();
        return singlenton;
    }

    public Connection getConn() {
        return conn;
    }

    public void open() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (this.conn != null) this.conn.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
