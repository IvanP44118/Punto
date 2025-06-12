package conex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private String usuario;
    private String password;
    private String rol;

    public Usuario(String usuario, String password, String rol) {
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public static boolean validarUsuario(String usuario, String password, String rol) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean valido = false;
        
       /* System.out.println("usua"+usuario);
        System.out.println("pass"+password);
        System.out.println(rol);*/

        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ? AND rol = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            stmt.setString(3, rol);
            rs = stmt.executeQuery();
            

            if (rs.next()) {
                valido = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return valido;
    }
} 