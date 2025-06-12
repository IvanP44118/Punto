
package conex;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
<<<<<<< HEAD
    private static final String URL = "jdbc:mysql://localhost:3306/negocio"; // Cambia nombre
=======
    private static final String URL = "jdbc:mysql://localhost:3306/sales_point"; // Cambia nombre
>>>>>>> fa9b32070f945735e36da317914160460c528c1d
    private static final String USER = "root";
    private static final String PASSWORD = ""; // scribe tu contraseña

    public static Connection getConexion() {
<<<<<<< HEAD
         Connection conn = null;
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Línea clave
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa con la BD!");
            conn.close();
=======
        Connection conn = null;
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Línea clave
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("tConexion exitosa!");
>>>>>>> fa9b32070f945735e36da317914160460c528c1d
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver JDBC no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
         return conn;
    }
}

