/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import punto.panel;
package punto;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

/**
 *
 * @author chiqu
 */
public class cortedecaja extends javax.swing.JFrame {
    
       private static final Logger LOGGER = Logger.getLogger(cortedecaja.class.getName());
    private static final String LOG_FILE = "corte_caja.log";
    private static final double MAX_DINERO = 999999999.99;
    private static final int MAX_LENGTH = 15;
    
    // Configurar el logger
    static {
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Error al configurar el logger: " + e.getMessage());
        }
    }

    /** Creates new form cortedecaja */
    public cortedecaja() {
        try {
            LOGGER.info("Iniciando aplicación de Corte de Caja");
            initComponents();
            configurarManejoErrores();
            LOGGER.info("Aplicación de Corte de Caja inicializada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error crítico al inicializar la ventana", e);
            mostrarError("Error crítico al inicializar la aplicación", e);
            System.exit(1);
        }
    }

     private void configurarManejoErrores() {
        try {
            LOGGER.info("Configurando manejo de errores para componentes");
            
            // Configurar el botón guardar
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        LOGGER.info("Botón guardar presionado");
                        guardarCorteCaja();
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error al guardar el corte de caja", e);
                        mostrarError("Error al guardar el corte de caja", e);
                    }
                }
            });

            // Configurar validación en el campo de texto
            jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    try {
                        validarEntradaNumerica(evt);
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "Error en la validación de entrada", e);
                        mostrarError("Error en la validación de entrada", e);
                    }
                }
            });
            
            // Configurar evento de cierre de ventana
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    try {
                        LOGGER.info("Cerrando aplicación de Corte de Caja");
                        System.exit(0);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error al cerrar la aplicación", e);
                        System.exit(1);
                    }
                }
            });

            LOGGER.info("Manejo de errores configurado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al configurar los componentes", e);
            mostrarError("Error al configurar los componentes", e);
            throw new RuntimeException("Error fatal en la configuración", e);
        }
    }
 private void validarEntradaNumerica(java.awt.event.KeyEvent evt) {
        try {
            char c = evt.getKeyChar();
            String textoActual = jTextField1.getText();
            
            LOGGER.fine("Validando entrada: '" + c + "' en texto actual: '" + textoActual + "'");
            
            // Permitir solo números, punto decimal y teclas de control
            if (!Character.isDigit(c) && c != '.' && c != '\b' && c != '\u007F') {
                evt.consume();
                LOGGER.warning("Carácter no válido ingresado: " + c);
                mostrarAdvertencia("Solo se permiten números y punto decimal");
                return;
            }
            
            // Evitar múltiples puntos decimales
            if (c == '.' && textoActual.contains(".")) {
                evt.consume();
                LOGGER.warning("Intento de agregar múltiples puntos decimales");
                mostrarAdvertencia("Ya existe un punto decimal");
                return;
            }
            
            // Limitar la longitud del texto
            if (textoActual.length() >= MAX_LENGTH && c != '\b' && c != '\u007F') {
                evt.consume();
                LOGGER.warning("Texto demasiado largo: " + textoActual.length());
                mostrarAdvertencia("El valor es demasiado largo (máximo " + MAX_LENGTH + " caracteres)");
                return;
            }
            
            // Validar que no empiece con punto decimal
            if (c == '.' && textoActual.isEmpty()) {
                evt.consume();
                LOGGER.warning("Intento de empezar con punto decimal");
                mostrarAdvertencia("No puede empezar con punto decimal");
                return;
            }
            
            LOGGER.fine("Entrada válida aceptada");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado en la validación de entrada", e);
            mostrarError("Error inesperado en la validación de entrada", e);
        }
    }

    /**
     * Procesa el guardado del corte de caja
     */
    private void guardarCorteCaja() {
        try {
            String textoDinero = jTextField1.getText().trim();
            LOGGER.info("Procesando corte de caja con valor: '" + textoDinero + "'");
            
            // Validar que el campo no esté vacío
            if (textoDinero.isEmpty()) {
                LOGGER.warning("Campo de dinero vacío");
                mostrarError("El campo de dinero no puede estar vacío");
                jTextField1.requestFocus();
                return;
            }
            
            // Validar que sea un número válido
            double dinero;
            try {
                dinero = Double.parseDouble(textoDinero);
                LOGGER.info("Valor numérico parseado correctamente: " + dinero);
            } catch (NumberFormatException e) {
                LOGGER.warning("Error al parsear número: " + textoDinero);
                mostrarError("El valor ingresado no es un número válido\nFormato esperado: 123.45");
                jTextField1.requestFocus();
                return;
            }
            
            // Validar que sea un valor positivo
            if (dinero < 0) {
                LOGGER.warning("Valor negativo ingresado: " + dinero);
                mostrarError("El dinero en caja no puede ser negativo");
                jTextField1.requestFocus();
                return;
            }
            
            // Validar que no sea un valor excesivamente grande
            if (dinero > MAX_DINERO) {
                LOGGER.warning("Valor demasiado grande: " + dinero);
                mostrarError("El valor ingresado es demasiado grande\nMáximo permitido: $" + String.format("%,.2f", MAX_DINERO));
                jTextField1.requestFocus();
                return;
            }
            
            // Validar que no sea cero
            if (dinero == 0) {
                LOGGER.warning("Valor cero ingresado");
                int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que el dinero en caja es $0.00?",
                    "Confirmar valor cero",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (respuesta != JOptionPane.YES_OPTION) {
                    LOGGER.info("Usuario canceló el valor cero");
                    return;
                }
            }
            
            // Formatear el número para mostrar
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            String dineroFormateado = formato.format(dinero);
            
            // Actualizar la etiqueta con el valor formateado
            jLabel4.setText("$" + dineroFormateado);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Corte de caja guardado exitosamente\nDinero en caja: $" + dineroFormateado,
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            LOGGER.info("Corte de caja guardado exitosamente: $" + dineroFormateado);
                
        } catch (OutOfMemoryError e) {
            LOGGER.log(Level.SEVERE, "Error de memoria al procesar el corte de caja", e);
            mostrarError("Error de memoria del sistema", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al procesar el corte de caja", e);
            mostrarError("Error inesperado al procesar el corte de caja", e);
        }
    }

      /**
     * Muestra un mensaje de error con detalles de la excepción
     */
    private void mostrarError(String mensaje, Exception e) {
        try {
            String mensajeCompleto = mensaje + "\n\nDetalles del error:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, 
                mensajeCompleto,
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error mostrado al usuario: " + mensaje, e);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al mostrar mensaje de error", ex);
            System.err.println("Error crítico: " + mensaje);
            e.printStackTrace();
        }
    }

    /**
     * Muestra un mensaje de error con detalles de la excepción
     */
        private void mostrarError(String mensaje, Throwable e) {
        try {
            String mensajeCompleto = mensaje + "\n\nDetalles del error:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, 
                mensajeCompleto,
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error mostrado al usuario: " + mensaje, e);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al mostrar mensaje de error", ex);
            System.err.println("Error crítico: " + mensaje);
            e.printStackTrace();
        }
    }

         private void mostrarError(String mensaje) {
        try {
            JOptionPane.showMessageDialog(this, 
                mensaje,
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            LOGGER.warning("Error mostrado al usuario: " + mensaje);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al mostrar mensaje de error simple", e);
            System.err.println("Error: " + mensaje);
        }
    }
          private void mostrarAdvertencia(String mensaje) {
        try {
            JOptionPane.showMessageDialog(this, 
                mensaje,
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            LOGGER.info("Advertencia mostrada al usuario: " + mensaje);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al mostrar mensaje de advertencia", e);
            System.err.println("Advertencia: " + mensaje);
        }
    }


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDineroCaja = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        txtCorteCaja = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Dinero = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JLabel();
        Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtDineroCaja.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        txtDineroCaja.setText("Dinero en Caja");
        getContentPane().add(txtDineroCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jTextField1.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 200, -1));

        txtCorteCaja.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCorteCaja.setText("Corte De Caja");
        getContentPane().add(txtCorteCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jButton1.setBackground(new java.awt.Color(51, 204, 0));
        jButton1.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        jButton1.setText("Guardar");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, -1, 40));

        Dinero.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        Dinero.setText("dinero :");
        getContentPane().add(Dinero, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        txtSaldo.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        getContentPane().add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 180, 30));

        Salir.setBackground(new java.awt.Color(204, 0, 0));
        Salir.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        Salir.setText("Regresar");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        getContentPane().add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
         this.dispose(); // Cierra solo la ventana actual (jfventas)
    new panel().setVisible(true); // Abre la ventana principal (panel)
    }//GEN-LAST:event_SalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(cortedecaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(cortedecaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(cortedecaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(cortedecaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new cortedecaja().setVisible(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, 
                            "Error al abrir la aplicación:\n" + e.getMessage(),
                            "Error de Inicialización", 
                            JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al crear la ventana: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error crítico al iniciar la aplicación:\n" + e.getMessage(),
                "Error Crítico", 
                JOptionPane.ERROR_MESSAGE);
            System.err.println("Error crítico: " + e.getMessage());
            e.printStackTrace();
        }
    }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cortedecaja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dinero;
    private javax.swing.JButton Salir;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel txtCorteCaja;
    private javax.swing.JLabel txtDineroCaja;
    private javax.swing.JLabel txtSaldo;
    // End of variables declaration//GEN-END:variables


