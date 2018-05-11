
import ar.edu.ub.p3.conexion.RecibidorDePedidosDeConexion;
import ar.edu.ub.p3.servidor.ChatRepository;

public class Aplicacion {

    public static void main(String args[]) throws InterruptedException {
        int serverPort = 12345;
        int cantidadMaximaMensajes = 3;
        
        Thread tx = new Thread( new RecibidorDePedidosDeConexion( serverPort, new ChatRepository( cantidadMaximaMensajes ) ) );
        
        tx.start();
        
        //Placeholder para el socket de administracion del chat
        
        //Espero a que terminen todos los hilos para bajar la app
        tx.join();
    }
}