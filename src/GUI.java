package src;

// The many many imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Separator;

/**
 * GUI and it's functions
 */
public class GUI extends Application {

    // Temp data - array of online Clients
    static ArrayList<Client> onlineClients = new ArrayList<Client>();

    @Override
    public void start(Stage primaryStage) {

        /* List of all clients */
        VBox col1 = new VBox();
        for (Client onlineClient : onlineClients) {
            HBox clientSec = new HBox();
            VBox rightCol = new VBox();

            // Private message icon
            Image image = new Image(getClass().getResourceAsStream("assets/msg_black.png"));
            Image hoverImage = new Image(getClass().getResourceAsStream("assets/msg_grey.png"));

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            //imageView.setOnMouseClicked(event -> );

            // Hover effects
            Tooltip tooltip = new Tooltip("Private Message");
            Tooltip.install(imageView, tooltip);
            imageView.setOnMouseEntered(event -> imageView.setImage(hoverImage));
            imageView.setOnMouseExited(event -> imageView.setImage(image));

            // Username
            Text text = new Text(onlineClient.getUsername());
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", FontWeight.BOLD, 13));

            // Online
            Text isOnline = new Text("Offline");
            isOnline.setFill(Color.DARKGREY);
            isOnline.setFont(Font.font("Arial", 11));
            if (onlineClient.isOnline()) {
                isOnline = new Text("Online");
                isOnline.setFill(Color.GREEN);
                isOnline.setFont(Font.font("Arial", 11));
            }

            Separator divider = new Separator();
            divider.setOrientation(Orientation.HORIZONTAL);

            rightCol.getChildren().addAll(text, isOnline);
            clientSec.getChildren().addAll(rightCol, imageView);
            clientSec.setMargin(imageView, new Insets(-5, 0, 0, 30));
            col1.getChildren().addAll(clientSec, divider);
            col1.setMargin(clientSec, new Insets(20, 30, 20, 30));
        }

        Separator divider = new Separator();
        divider.setOrientation(Orientation.VERTICAL);

        /* Messaging Section */

        // Display Inputted Msg
        Text msgPrint = new Text();
        msgPrint.setFill(Color.BLACK);

        // Text Input Box
        TextField msgInput = new TextField();
        msgInput.setPromptText("Enter your message here");
        msgInput.setPrefWidth(600);

        // Send Button
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            String msgSent = msgInput.getText();
            msgPrint.setText(msgSent);
        });

        VBox col2 = new VBox();
        col2.setPadding(new Insets(10));
        col2.setMargin(sendButton, new javafx.geometry.Insets(10, 0, 0, 0));
        col2.getChildren().addAll(msgPrint, msgInput, sendButton);

        HBox hbox = new HBox(col1, divider, col2);

        Scene scene = new Scene(hbox, 800, 500);
        primaryStage.setTitle("Happy Chatty Appy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Test Data - Load online client list
     */
    public static void loadClients() {
        Client cl0 = new Client("username0", "0000", true);
        onlineClients.add(cl0);
        Client cl1 = new Client("username1", "0001", true);
        onlineClients.add(cl1);
        Client cl2 = new Client("username2", "0002", false);
        onlineClients.add(cl2);
        Client cl3 = new Client("username3", "0003", true);
        onlineClients.add(cl3);
    }

    /**
     * Main function
     * @param args Terminal arguments
     */
    public static void main(String[] args) {
        loadClients();
        launch(args);
    }
}
