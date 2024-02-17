import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server_thread {
    public static void main(String[] args) {
        int portNumber = 5000;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("The server is listening on port : " + portNumber);

            // Continue to connect clients as they try to connect
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected : " + clientSocket);

                Thread clientThread  = new Thread(new clientThread(clientSocket));
            }
        } catch (Exception e) {
            System.out.println("The Server could not listen on port : " + portNumber);
        }
    }

    private static class clientThread implements Runnable {
        private final Socket clientSocket;

        public clientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                ObjectOutputStream OutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream InputStream = new ObjectInputStream(clientSocket.getInputStream());
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
            } catch (Exception e) {
                System.out.println("Could not read in message from client");
            }
            
        }
    
    }
    
}

