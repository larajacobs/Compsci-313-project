import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
      try {
        String hostName = "localhost";
        int portNumber = 5000;
        System.out.println("Trying to connect to server");
        Socket clientSocket = new Socket(hostName, portNumber); // Connect to localhost on port 5000
        System.out.println("Connected to server.");

        // Need to create a variable to read in data from command line
        // Create a variable that will write to standard out

      
        // Open an input stream and output stream to the socket.
        ObjectOutputStream OutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream InputStream = new ObjectInputStream(clientSocket.getInputStream());
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Read from and write to the stream according to the server's protocol.
        String userInputLine;
        System.out.println("Before while loop");
        
        while(true) {
          System.out.print("Client: ");
          userInputLine = userInput.readLine();
          OutputStream.writeObject(userInputLine);
          OutputStream.flush(); // check
          
          // Receive response from server
          userInputLine = (String) InputStream.readObject();
          System.out.println("Server: " + userInputLine);

          if (userInputLine.equals("1")){
            System.out.println("exits");
            break;
          } 
        }

        // Close streams and socket when user disconnects
        OutputStream.close();
        InputStream.close();
        userInput.close();
        clientSocket.close();
        
      } catch (Exception e) {
        // TODO: handle exception
      }

       
    }
  }

