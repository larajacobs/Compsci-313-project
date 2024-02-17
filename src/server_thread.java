import java.io.BufferedReader;
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
                Thread clientThread  = new Thread(new clientThread(clientSocket));
                client_threads.add(new clientThread(clientSocket));
                clientThread.start();
            }

            serverSocket.close();
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

                // Open an input stream and output stream to the socket.
                
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

                    synchronized (client_threads) {
                        for (clientThread otherClient: client_threads) {
                            // If not the input thread send message from server
                            if (otherClient != this) {
                                System.out.print("Server: ");
                                message = reader.readLine();
                                OutputStream.writeObject(message);
                                OutputStream.flush();
                            }
                        }
                    }
                    
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

