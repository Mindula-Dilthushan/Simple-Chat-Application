package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientAppUIController {

    public TextArea txtMessage;
    public Button btnSend;
    public TextField txtMessageSent;

    static Socket socket = null;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    public void initialize(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost",5000);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    String messageIn= "";

                    while (!messageIn.equals("end")){
                        messageIn=dataInputStream.readUTF();
                        txtMessage.appendText("\nServe : "+messageIn.trim());
                    }
                } catch (IOException e) {

                }
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        String reply="";
        reply=txtMessageSent.getText();
        dataOutputStream.writeUTF(reply);
    }
}
