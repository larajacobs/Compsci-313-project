import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
      public static void main(String[] args) throws IOException {

        try {
          int portNumber = 5000;
          
          ServerSocket serverSocket = new ServerSocket(portNumber);
          // server has been created, need to now wait for client
          System.out.println("Waiting on client");
          Socket socket = serverSocket.accept(); 
          // Client is now connected to server.
          System.out.println("Client connected.");

          // Create input and output streams
          
          ObjectOutputStream OutputStream = new ObjectOutputStream(socket.getOutputStream());
          ObjectInputStream InputStream = new ObjectInputStream(socket.getInputStream());
          BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

          // Communication loop
          String message;
      
          while (true) {
             // Read message from client and print to console
             message = (String) InputStream.readObject();
             if (message.equals("1")) {
                System.out.println("exits");
                break;
             }
             System.out.println("Client: " + message);

             // Send response to client
             System.out.print("Server: ");
             message = reader.readLine();
             OutputStream.writeObject(message);
             OutputStream.flush();
          }
          
          // Close streams and socket
          InputStream.close();
          OutputStream.close();
          socket.close();
          serverSocket.close();

        } catch (Exception e) {
          // TODO: handle exception
        }
      
      }
}