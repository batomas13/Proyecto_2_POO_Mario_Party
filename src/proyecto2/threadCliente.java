package proyecto2;
import java.io.*;

class threadCliente extends Thread{
   //solo de lectura
    DataInputStream entrada;
   JuegoGenerico vcli; //referencia acliente
   public threadCliente (DataInputStream entrada, JuegoGenerico vcli) throws IOException
   {
      this.entrada=entrada;
      this.vcli=vcli;
   }
   public void run() {
       //VARIABLES
      String menser="",amigo="";
      int opcion;
      
      // solamente lee lo que el servidor threadServidor le envia
      while(true)
      {         
         try{
            // esta leyendo siempre la instruccion, un int
            opcion=entrada.readInt();
            System.out.println("Thread cliente recibe opcion: " + opcion);
            switch(opcion)
            {
               case 1://mensaje enviado
                  int col = entrada.readInt();//lee columna
                  int fila = entrada.readInt();//lee fila
                  // llama a marcar, que es lo que hace el cliente cuando
                  // el enemigo marco la caasilla
                  //((JuegoGato) vcli).marcar(col,fila);
                  System.out.println("Op1: lee col y fila: "+col+" , "+fila);
                  break;
               case 2://se lee el nombre del user  
                  menser=entrada.readUTF();
                  // coloca el nombre del enemigo
                  vcli.setEnemigo(menser);
                  System.out.println("Op2: lee nombre enemigo: "+menser);
                  break;
                case 3:
                    //lee el numero del jugador
                    System.out.println("Entré a caso 3 para recibir el numero de jugador");
                    vcli.numeroJugador = entrada.readInt();
                    // lee el nomnre del enemigo vuando pide el status y lo coloca
                    // en la pantalla cliente
                    vcli.setEnemigo(entrada.readUTF());
                    System.out.println("OP3, lee numero de jugador "+vcli.numeroJugador);
                  break;
                case 4:
                  // lee el mensaje de la carta para el juego de cartas
                  menser = entrada.readUTF();
                  // muestra el mensjae en pantalla.
                  System.out.println("OP4, lee mensaje de carta: " + menser);
                  // envia la carta al cliente
                  ((Juego_Cards) vcli).recibeCarta(Integer.parseInt(menser));
                    break;
                case 5:
                    //lee el numero del jugador
                    System.out.println("Entré a caso 5 para recibir el numero de jugador");
                    vcli.numeroJugador = entrada.readInt();
                    // lee el nomnre del enemigo vuando pide el status y lo coloca
                    // en la pantalla cliente
                    vcli.setEnemigo(entrada.readUTF());
                    System.out.println("OP5, lee numero de jugador "+vcli.numeroJugador);
                    break;
                case 6: 
                    int valorDados = entrada.readInt();
                    //((JuegoGato) vcli).mostrar("Enemigo sacó un " + valorDados);
                    //((JuegoGato) vcli).cambiarEtiqueta(valorDados);
                    break;
                case 10:
                    String msg = entrada.readUTF();
                    System.out.println(msg);
                    System.out.println("Recibido caso 10 de cards");
                    break;
                default:
                    System.out.println("No sé en qué caso llegué :(");
                    break;
            }
         } catch (ClassCastException e) {
             System.out.println("Aqui se supone hago handling del casteo");
             break;
         }
         catch (IOException e){
            System.out.println("Error en la comunicacion");
            break;
         }
      }
      System.out.println("se desconecto el servidor");
   }
}