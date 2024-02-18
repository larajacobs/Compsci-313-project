import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class server_thread {
    private static final List<clientThread> client_threads = new ArrayList<>();

    public static void main(String[] args) {
        int portNumber = 5000;
        
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("The server is listening on port : " + portNumber);

            // Continue to connect clients while the server socket is open
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected : " + clientSocket);
                
                // Create a thread for each client that connects to server
                clientThread clientThread = new clientThread(clientSocket);
                Thread thread  = new Thread(clientThread);
                client_threads.add(clientThread);
                thread.start();
            }

            serverSocket.close();
        } catch (Exception e) {
            System.out.println("The Server could not listen on port : " + portNumber);
        }
    }

    private static class clientThread implements Runnable {
        private final Socket clientSocket;
        private final ObjectOutputStream outputStream;

        public clientThread(Socket clientSocket) throws IOException{
            this.clientSocket = clientSocket;
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        
        // Send message to client
        private void sendMessage(String message) throws IOException {
            System.out.println("Attempting to send message");
            outputStream.writeObject(message);
            outputStream.flush();
            System.out.println("Message sent");
        }
        
        // Should disconnect the client
        private void disconnect() {
            try {
                client_threads.remove(this);
                outputStream.close();
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Could not disconnect");
            }
        }

        public void run() {
            try {

                // Open an input stream and output stream to the socket.
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                
                // Communication loop
                String message;
            
                while (true) {
                    // Read message from client and print to console
                    message = (String) inputStream.readObject();
                    if (message.equals("1")) {
                        System.out.println("exits");
                        disconnect();
                        break;
                    }
                    System.out.println("Client: " + message);

                    // Send response to client
                    synchronized (client_threads) {
                        for (clientThread otherClient: client_threads) {
                            // If not the input thread send message from server
                            if (otherClient != this) {
                                otherClient.sendMessage(message);
                            }
                        }
                    }
                }
                
                // Close streams and socket
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                System.out.println("Could not read in message from client");
            }
            
        }
    
    }
    
}

