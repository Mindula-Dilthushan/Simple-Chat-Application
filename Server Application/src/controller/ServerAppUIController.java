package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerAppUIController implements Initializable {
    public TextArea txtMessageArea;
    public Button btnSend;
    public TextField txtMessageSent;

    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    String messageIn="";

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMessageSent.getText().trim());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(()->{
            try {

                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started");
                socket=serverSocket.accept();
                System.out.println("Client Accepted!");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while(!messageIn.equals("end")){
                    messageIn = dataInputStream.readUTF();
                    txtMessageArea.appendText("\n Client :"+messageIn.trim());
                }
            } catch (IOException e) {

            }
        }).start();
    }
}
