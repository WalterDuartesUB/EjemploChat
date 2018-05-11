import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import ar.edu.ub.p3.conexion.ConexionAlServerDeChat;

public class Aplicacion {

    public static void main(String args[]) throws IOException {
        //Pido algunos datos para hacer un login
        Scanner                in = new Scanner( System.in );
        ConexionAlServerDeChat cnxChat = new ConexionAlServerDeChat( "127.0.0.1", 12345 );

        // Pido la identificacion para el chat
        System.out.print("Nombre de usuario: ");
        String userName = in.nextLine();
        
        System.out.print("Contrasenia: ");
        String userPassword = in.nextLine();               
                
        //Si me pude conectar, le dejo mandar mensajes
        if( cnxChat.conectar( userName, userPassword ) )
        {
        	// Esto es para que el servidor pueda desconectarme con un mensaje
            while ( cnxChat.isEstoyConectado() ) {
            	String linea = in.nextLine();
            	
            	//Comando para salir
            	if( linea.compareTo("/quit") == 0 )
            		cnxChat.desconectar();
            	//Comando para pedir la lista de usuarios
            	else if( linea.compareTo("/users") == 0 )
            	{
            		Collection<String> usuarios = cnxChat.getUsuarios();
            		
            		for( String alias : usuarios )
            			System.out.println( alias );
            	}
            	else 	
            		//Texto normal
            		cnxChat.enviarMensajeAlChat( linea );
            }        	

        }
        
        /*
         // Ejemplo que estaba funcionando
         
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
        */
        in.close();
    }
}