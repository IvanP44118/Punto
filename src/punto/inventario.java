

package punto;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.*;
import javax.swing.table.DefaultTableModel;



public class inventario extends javax.swing.JFrame {
     private static final Logger LOGGER = Logger.getLogger(inventario.class.getName());
    private static final String LOG_FILE = "inventario.log";

    static {
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            LOGGER.info("Logger de Inventario configurado correctamente");
        } catch (IOException e) {
            System.err.println("Error al configurar el logger de Inventario: " + e.getMessage());
        }
    }
    
      public inventario(String rolUsuario) {
        if (!"administrador".equalsIgnoreCase(rolUsuario)) {
            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo el administrador puede ver el inventario.");
            LOGGER.warning("Intento de acceso al inventario por usuario no administrador: " + rolUsuario);
            dispose();
            return;
        }
        inicializar();
        }
        public inventario() {
        inicializar();
    }
          private void inicializar() {
        try {
            LOGGER.info("Iniciando ventana de Inventario");
            initComponents();
            configurarEventos();
            LOGGER.info("Ventana de Inventario inicializada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error crítico al inicializar la ventana de Inventario", e);
            JOptionPane.showMessageDialog(this, "Error crítico al iniciar: " + e.getMessage());
            System.exit(1);
        }
    }
           private void configurarEventos() {
        btnagregar.addActionListener(e -> agregarProducto());

        btnsalir.addActionListener(e -> {
            LOGGER.info("Botón salir presionado. Regresando al menú principal.");
            dispose();
            new panel().setVisible(true); // asegúrate de que la clase 'panel' exista
        });
    }
    private void agregarProducto() {
        try {
 String idTexto = txtFilcodigo.getText().trim();
            String nombre = txtFilNombre.getText().trim();
            String cantidadTexto = txtFilStock.getText().trim();
            String precioCompraTexto = txtFilPrecioCompra.getText().trim();
            String precioVentaTexto = txtFilPrecioVenta.getText().trim();

            if (idTexto.isEmpty() || nombre.isEmpty() || cantidadTexto.isEmpty() ||
                precioCompraTexto.isEmpty() || precioVentaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return;
            }

      int id = Integer.parseInt(idTexto);
            int cantidad = Integer.parseInt(cantidadTexto);
            double precioCompra = Double.parseDouble(precioCompraTexto);
            double precioVenta = Double.parseDouble(precioVentaTexto);
            
            Connection con = conex.Conexion.getConexion();

            String verificarSQL = "SELECT COUNT(*) FROM productos WHERE id_producto = ?";
            PreparedStatement verificarStmt = con.prepareStatement(verificarSQL);
            verificarStmt.setInt(1, id);
            try (ResultSet rs = verificarStmt.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "El producto ya existe. Usa editar para modificarlo.");
                        return;
                    }
                }
            }
          
              String sqlInsertar = "INSERT INTO productos (id_producto, nombre, stock, categoria_id, precio_unitario, precio_venta) VALUES (?, ?, ?, ?, ?, ?)";
           try (PreparedStatement ps = con.prepareStatement(sqlInsertar)) {
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setInt(3, cantidad);
            ps.setInt(4, 1); // categoria_id fija
            ps.setDouble(5, precioCompra);
            ps.setDouble(6, precioVenta);
            ps.executeUpdate();
             }
             String sqlMov = "INSERT INTO movimientos_inventario (producto_id, tipo_movimiento, cantidad) VALUES (?, 'entrada', ?)";
            PreparedStatement psMov = con.prepareStatement(sqlMov);
            psMov.setInt(1, id);
            psMov.setInt(2, cantidad);
            psMov.executeUpdate();
             }
            
              DefaultTableModel modelo = (DefaultTableModel) jtable1.getModel();
            modelo.addRow(new Object[]{id, nombre, cantidad, precioCompra, precioVenta});
            
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
            limpiarCampos();
            LOGGER.info("Producto agregado: ID=" + id + ", Nombre=" + nombre + ", Cantidad=" + cantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID, Cantidad, Precio Compra y Venta deben ser numéricos.");
            LOGGER.warning("Error de formato numérico: " + e.getMessage());
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con ese ID.");
            LOGGER.warning("Violación de restricción única: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Error al agregar producto", e);
        }
    }
 private void editarProducto() {
        // Implementa la lógica para editar un producto
    }

 private void eliminarProducto() {
        // Implementa la lógica para eliminar un producto
    }

     private void limpiarCampos() {
        txtFilcodigo.setText("");
        txtFilNombre.setText("");
        txtFilStock.setText("");
        txtFilPrecioCompra.setText("");
        txtFilPrecioVenta.setText("");
    }

    // Resto del initComponents y main exactamente igual al tuyo,
    // no requiere cambios si ya compila y carga correctamente
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtable1 = new javax.swing.JTable();
        btnagregar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btneditar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        txtNombre = new javax.swing.JLabel();
        txtFilNombre = new javax.swing.JTextField();
        txtcodigo = new javax.swing.JLabel();
        txtFilcodigo = new javax.swing.JTextField();
        txtPrecioCompra = new java.awt.Label();
        txtFilPrecioCompra = new javax.swing.JTextField();
        btnsalir = new javax.swing.JButton();
        txtPrecioVenta = new javax.swing.JLabel();
        txtStock = new javax.swing.JLabel();
        txtFilStock = new javax.swing.JTextField();
        txtFilPrecioVenta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventario");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 670, -1));

        btnagregar.setBackground(new java.awt.Color(0, 255, 0));
        btnagregar.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        btnagregar.setText("Agregar");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnagregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        btneditar.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        btneditar.setText("Editar ");
        getContentPane().add(btneditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, -1, -1));

        btneliminar.setBackground(new java.awt.Color(255, 0, 0));
        btneliminar.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        btneliminar.setText("Eliminar");
        getContentPane().add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, -1, -1));

        txtNombre.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtNombre.setText("Nombre del producto");
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtFilNombre.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtFilNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilNombreActionPerformed(evt);
            }
        });
        getContentPane().add(txtFilNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 250, -1));

        txtcodigo.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtcodigo.setText("Codigo");
        getContentPane().add(txtcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        txtFilcodigo.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        getContentPane().add(txtFilcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 210, -1));

        txtPrecioCompra.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtPrecioCompra.setText("Precio Compra");
        getContentPane().add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 30));

        txtFilPrecioCompra.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtFilPrecioCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilPrecioCompraActionPerformed(evt);
            }
        });
        getContentPane().add(txtFilPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 180, -1));

        btnsalir.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        btnsalir.setText("Salir");
        getContentPane().add(btnsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

        txtPrecioVenta.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtPrecioVenta.setText("Precio Venta");
        getContentPane().add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        txtStock.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtStock.setText("Stock");
        getContentPane().add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        txtFilStock.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        getContentPane().add(txtFilStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 190, 30));

        txtFilPrecioVenta.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        getContentPane().add(txtFilPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 190, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFilNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFilNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFilNombreActionPerformed

    private void txtFilPrecioCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFilPrecioCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFilPrecioCompraActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
    btnagregar.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        agregarProducto();
    }
});


    }//GEN-LAST:event_btnagregarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
           try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inventario().setVisible(true);
            }
        });
    }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable1;
    private javax.swing.JTextField txtFilNombre;
    private javax.swing.JTextField txtFilPrecioCompra;
    private javax.swing.JTextField txtFilPrecioVenta;
    private javax.swing.JTextField txtFilStock;
    private javax.swing.JTextField txtFilcodigo;
    private javax.swing.JLabel txtNombre;
    private java.awt.Label txtPrecioCompra;
    private javax.swing.JLabel txtPrecioVenta;
    private javax.swing.JLabel txtStock;
    private javax.swing.JLabel txtcodigo;
    // End of variables declaration//GEN-END:variables
}
