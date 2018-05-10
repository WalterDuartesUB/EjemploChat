import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import ar.edu.ub.p3.common.Credentials;
import ar.edu.ub.p3.common.Message;
import ar.edu.ub.p3.conexion.MessageListener;

public class Aplicacion {

    public static void main(String args[]) throws IOException {
        
    	// TODO Todo este bloque puede refactorizarse, como ejemplo alcanza por el momento
    	
        //Pido algunos datos para hacer un login
        Scanner in = new Scanner( System.in );
        
        System.out.print("Nombre de usuario del aeropuerto: ");
        String userName = in.nextLine();
        
        System.out.print("Contrasenia del usuario del aeropuerto: ");
        String userPassword = in.nextLine();

        //Creo la conexion con el servidor
        try (Socket socket = new Socket("127.0.0.1", 12345)) {
        	
        	//Creo un thread para poder escuchar los mensajes que llegan        	
            new Thread( new MessageListener(socket) ).start();

            //Creo el oos para enviar mi login y mensajes de chat
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            	
            	//Envio mis credenciales de login
                out.writeObject( new Credentials(userName, userPassword) );
                
                //Empiezo a enviar mensajes de chat
                String message;
                
                while ((message = in.nextLine()) != null) {
                    out.writeObject( new Message(userName, message) );
                }
            }
        }
        
        in.close();
    }
}