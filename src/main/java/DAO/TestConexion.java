package DAO;

public class TestConexion {
    public static void main(String[] args) {
        DAOManager dao = DAOManager.getSinglentonInstance();

        try {
            dao.open();
            if (dao.getConn() != null && !dao.getConn().isClosed()) {
                System.out.println("Conexión a MySQL establecida correctamente.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                dao.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (Exception e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
