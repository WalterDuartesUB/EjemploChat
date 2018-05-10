package ar.edu.ub.p3.conexion;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import ar.edu.ub.p3.common.Message;

public class MessageListener implements Runnable {
    private Socket socket;
    
    public MessageListener(Socket clientSocket) {
        this.setSocket(clientSocket);
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream())) {
        	Message message = null;
            while (true) {
                try {
                    message = (Message) in.readObject();
                }
                catch (EOFException e) {
                    break;
                }
                System.out.println(message);
            }
        }
        catch (SocketException e) {
            System.out.println("Disconnected");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

	public Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket clientSocket) {
		this.socket = clientSocket;
	}
}