/*
 * JuegoGato.java
 *
 * Created on 2 de diciembre de 2008, 06:36 PM
 */

package proyecto2;

import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author  Alejandro
 */
public class JuegoGato extends JuegoGenerico {
    
    private File path = new File("Imagenes_Gato");
    private File[] allFiles = path.listFiles();
    
    /** Creates new form JuegoGato */
    public JuegoGato() throws IOException{
        try {
            // esto es parte del gato
            initComponents();
            jPanel1.setLayout(null);
            generarTablero();
            
            // Cra una cliente que es su coenxion al server
            cliente = new Cliente(this);
            System.out.println("Se hizo el cliente");
            cliente.conexion();
            
            // pide el status al server, el server le enviara
            // al cliente el numero jugador que es y el nombre
            // enemigo
            cliente.salida.writeInt(2);
            
        } catch (IOException ex) {
           
        }
    }
    
    
    //----------------------------------
    Cliente cliente;
    //----------------------------------
    
    
    // cambiar este valor para dimensiones
    public static int DIMENSIONES = 3;
    // Tablero con objetos JButton
    JButton[][] tableroLabels = new JButton[DIMENSIONES][DIMENSIONES];
    // tablero logico, indica el status del boton, si disparado o no
    int[][] tableroLogico = new int[DIMENSIONES][DIMENSIONES];
    
    ImageIcon iconoVacio = new ImageIcon(ImageIO.read(allFiles[2]));
    ImageIcon iconoEquiz = new ImageIcon(ImageIO.read(allFiles[1]));
    ImageIcon iconoCirculo = new ImageIcon(ImageIO.read(allFiles[0]));
    
    // crea imagen blanco
    //ImageIcon iconoVacio = new ImageIcon(getClass().getResource("imagenes/cvacio.gif"));
    // crea imagen X
    //ImageIcon iconoEquiz = new ImageIcon(getClass().getResource("imagenes/cequiz.gif"));
    // crea la imagen circulo
    //ImageIcon iconoCirculo = new ImageIcon(getClass().getResource("imagenes/ccirculo.gif"));
    
    int turnoJugador = 1;
    
    //numero de jugador 1 o 2
    int numeroJugador = 0;
    
    
    void generarTablero()
    {
        for(int i=0;i<DIMENSIONES;i++)
        {
            for(int j=0;j<DIMENSIONES;j++)
            {
                // coloca imagen a todos vacio
                tableroLabels[i][j] = new JButton(iconoVacio);
                //añade al panel el boton;
                jPanel1.add(tableroLabels[i][j]);
                // coloca dimensiones y localidad
                tableroLabels[i][j].setBounds(100+50*i, 100+50*j, 50, 50);
                // coloca el comand como i , j 
                tableroLabels[i][j].setActionCommand(i+","+j);//i+","+j
                
                //aclickSobreTablero(evt);ñade el listener al boton
                tableroLabels[i][j].addMouseListener(new MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                        
                    clickSobreTablero(evt);
                    
                }
                });
                // en logico indica estado en disponible
                tableroLogico[i][j]=0;
            }
        }
    }
    
    // reiniciar el juego es poner todo como en un inicio
    public void reiniciarJuego()
    {
        turnoJugador=1;
        for(int i=0;i<DIMENSIONES;i++)
        {
            for(int j=0;j<DIMENSIONES;j++)
            {
                tableroLabels[i][j].setIcon(iconoVacio);
                tableroLogico[i][j]=0;
            }
        }
    }
    
    // este metodo es la respuesta del cliente al clic del enemigo
    public void marcar(int columna, int fila)
    {
        // marca el tablero con num de jugador
        tableroLogico[columna][fila]=turnoJugador;
        // si soy el 1, marco con o que es el 2, sino con X
        // pues es el turno del enemigo que estoy marcando
        if (numeroJugador == 1)
            tableroLabels[columna][fila].setIcon(iconoCirculo);
        else
            tableroLabels[columna][fila].setIcon(iconoEquiz);
            
        // pregunta si gano el enemigo
            if(haGanado())
            {
                JOptionPane.showMessageDialog(null, "Ha ganado el jugador "+turnoJugador);
                
                reiniciarJuego();
            }          
        // este fue el clic del enemigo, marco ahora mi turno
        turnoJugador = numeroJugador;
        jLabel1.setText("Turno del Jugador "+turnoJugador);
        
        

        
    }
    

    
    public void clickSobreTablero(java.awt.event.MouseEvent evt)
    {
        // obtiene el boton 
        JButton botonTemp = (JButton)evt.getComponent();
        // obtiene el i,j de action command del boton
        String identificadorBoton = botonTemp.getActionCommand();
        
        // separa el string del action comand para obtener columnas
        int columna = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int fila = 
          Integer.parseInt(identificadorBoton.substring(1+identificadorBoton.indexOf(",")));
        
        // si ya se disparo entonces nada
        if(tableroLogico[columna][fila]!=0)
            return;
        
        // si es mi turno continua, si no return
        if (numeroJugador != turnoJugador)
            return;
        
        // como es turno del cliente marca el logico con su numero
        tableroLogico[columna][fila]=turnoJugador;
        // si era el jugador 1 marca con x y cambia el turno a jugador 2
        if (numeroJugador == 1)
        {
            
            tableroLabels[columna][fila].setIcon(iconoEquiz);
            turnoJugador=2;
        }
        else
        {
            // si era jugador 3, marca circulo y turno jugador 1
            tableroLabels[columna][fila].setIcon(iconoCirculo);
            turnoJugador=1;
        }
        // muestra el turno del jugador
         jLabel1.setText("Turno del Jugador "+turnoJugador);
        
        try {
            // como el cliente dio clic debe enviar al servidor las coordenadas
            // el servidor se las pasara al thread cliente para que este
            // las muestre (haga el marcar)
            // envia las coordenadas
            cliente.salida.writeInt(1);
            cliente.salida.writeInt(columna);
            cliente.salida.writeInt(fila);
        } catch (IOException ex) {
            
        }
         
        // si gano el jugador 1 lo indica
        if(haGanado())
        {
            JOptionPane.showMessageDialog(null, "Ha ganado el jugador 1");
            reiniciarJuego();
        }      
    }
    
    
    boolean haGanado()
    {
        
        //Ganó en las filas
        for(int i=0;i<3;i++)
        {
        if ((tableroLogico[i][0]==tableroLogico[i][1])
                &&(tableroLogico[i][1]==tableroLogico[i][2])
                && !(tableroLogico[i][0]==0))
        {
            return true;
        }
        }
        
        //Gano en las columnas
        for(int i=0;i<3;i++)
        {
        if ((tableroLogico[0][i]==tableroLogico[1][i])
                &&(tableroLogico[1][i]==tableroLogico[2][i])
                && !(tableroLogico[0][i]==0))
        {
            return true;
        }
        }
        //Verificar diagonal 1
        if ((tableroLogico[0][0]==tableroLogico[1][1])
                &&(tableroLogico[1][1]==tableroLogico[2][2])
                && !(tableroLogico[0][0]==0))
            return true;
        
        //Verificar diagonal 2
        if ((tableroLogico[2][0]==tableroLogico[1][1])
                &&(tableroLogico[1][1]==tableroLogico[0][2])
                && !(tableroLogico[2][0]==0))
            return true;
        
        return false;
    }
    
    // set el nombre del enemigo
    @Override
    public void setEnemigo(String enem)
    {
        lblEnemigo.setText("vs. "+enem);
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblEnemigo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txfMensaje = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMensajes = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        txfColumna = new javax.swing.JTextField();
        txfFila = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Turno del Jugador: Jugador 1");

        lblEnemigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblEnemigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txaMensajes.setColumns(20);
        txaMensajes.setRows(5);
        jScrollPane1.setViewportView(txaMensajes);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        jButton2.setText("DADOS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEnemigo, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txfMensaje, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnEnviar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txfFila)
                    .addComponent(txfColumna, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEnemigo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txfMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEnviar)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txfColumna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfFila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    JOptionPane.showMessageDialog(this, numeroJugador+","+turnoJugador);
}//GEN-LAST:event_jButton1ActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JuegoGato().setVisible(true);
                } catch (IOException ex) {
                    //
                }
            }
        });
    }
    
    public void mostrar(String texto)
    {
        txaMensajes.append(texto+"\n");
    }
    
    
    public void cambiarEtiqueta(int dados){
        
        jButton2.setText("Sacó " + dados);
        
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEnemigo;
    private javax.swing.JTextArea txaMensajes;
    private javax.swing.JTextField txfColumna;
    private javax.swing.JTextField txfFila;
    private javax.swing.JTextField txfMensaje;
    // End of variables declaration//GEN-END:variables
   
}
