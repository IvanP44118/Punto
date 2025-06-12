
package conex;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/negocio"; // Cambia nombre
    private static final String USER = "root";
    private static final String PASSWORD = ""; // scribe tu contraseña

    public static Connection getConexion() {
         Connection conn = null;
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Línea clave
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver JDBC no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
         return conn;
    }
}

