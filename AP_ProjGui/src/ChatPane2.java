

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;


import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by micks on 3/20/2017.
 */
public class ChatPane2 extends Pane implements Serializable{
    @FXML
    private VBox chat,chatTo, chatContainer;
    @FXML
    private ScrollPane chatSC, chatToSC;
    @FXML
    private Label inMessage,outMessage, name, date;
    @FXML
    private Pane  ppl, sendContainer;
    @FXML
    AnchorPane messageIn,messageOut;
    @FXML
    private Button send;
    @FXML
    private TextField messages;
    /*@FXML
    private TextArea messages;*/
     @FXML
    SVGPath sendIcon;

     Socket con;
     DataInputStream din;
     DataOutputStream dout;
     String data;
     Task getIn;

    public void getConnected(){
        /*try{
            //con = new Socket("127.0.0.5",3000);
            con = new Socket("LAPTOP-1LV8BSRD",3001);
            System.out.println("DashBoard Connected");

            //initPersonaInfo(data);
        }catch(IOException e){
            System.out.println("Can't connect to Server!!!");
            e.printStackTrace();
        }*/
        new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        //con = new Socket("127.0.0.5",3000);
                        con = new Socket("127.0.0.5", 4000);
                        System.out.println("Chat Connected");
                    } catch (IOException e) {
                        System.out.println("Can't connect to Server!!!");
                        e.printStackTrace();
                    } finally {
                        if (con == null) {
                            try {
                                con.close();
                            } catch (IOException ignored) {

                            }
                        }
                    }
                }
        }).start();

    }

    public void receiveData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        din = new DataInputStream(con.getInputStream());
                        data = din.readUTF();
                        System.out.println(data);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                initGetChat(data);
                                addGetChatToContainer();
                            }
                        });
                    }catch(IOException ex){
                        System.out.println("Couldn't receive message");
                        break;
                    }
                }
            }
        }).start();

    }

     public void setPosition(double X, double Y){
        this.setLayoutY(Y);
        this.setLayoutX(X);
    }

    public double[] getPostion(){
        double pos[] = {this.getLayoutX(),this.getLayoutY()};

        return pos;
    }

    public void setSize(double width, double height){
        this.setPrefWidth(width);
        this.setMaxWidth(width);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        chatContainer.setPrefHeight(this.getPrefHeight() - (sendContainer.getPrefHeight() + 2));
    }

    public double[] getSize(){
        double size[] = {this.getPrefWidth(), this.getPrefHeight()};

        return size;
    }

    public void sendMessage(String msg){
        try{
            DataOutputStream dout = new DataOutputStream(con.getOutputStream());
            dout.writeUTF(msg);
            System.out.println("Sent");
        }catch(IOException ex){
            System.out.println("Can't send data");
        }
    }

    private void initComps(){
        name = new Label("");
        ppl = new Pane();
        chatContainer = new VBox();
        sendContainer = new Pane();
        chat = new VBox();
        chatTo = new VBox();
        chatSC = new ScrollPane();
        chatToSC = new ScrollPane();
        send = new Button();
        messages = new TextField();
        //messages = new TextArea();
        sendIcon = new SVGPath();
        send.setGraphic(sendIcon);

    }

    public void initGetChat(String m){
        inMessage = new Label(m);
        inMessage.setWrapText(true);
        inMessage.setTranslateX(20);
        inMessage.setMaxWidth(250);
        inMessage.setStyle("-fx-text-fill: #000; -fx-padding: 1.5mm 3mm 1.5mm 3mm; -fx-background-color: #e6e6e6; -fx-background-radius: 15px 15px 15px 0px; -fx-border-radius: 15px 15px 15px 0px");
        addGetChatToContainer();
    }

    private void initSendChat( String m){
        messageOut = new AnchorPane();
        outMessage = new Label(m);

        outMessage.setWrapText(true);
        //outMessage.setPrefWidth(messageOut.getPrefWidth() - 8);
        outMessage.setTranslateX((chat.getPrefWidth() * 0.2) + 5);
        outMessage.setMaxWidth(250);
        /*if(outMessage.getText().length() == 38){
            outMessage.setMaxWidth(250);
        }
        /*outMessage.setOnInputMethodTextChanged(e->{
            if(outMessage.getBoundsInParent().getHeight() == messageOut.getMaxWidth() - 8){
                outMessage.setMaxWidth(messageOut.getMaxWidth() - 8);
            }
        });*/

        //outMessage.setLayoutX(15);
        outMessage.setLayoutY(8);
        outMessage.setStyle("-fx-text-fill: #000; -fx-padding: 1.5mm 3mm 1.5mm 3mm; -fx-background-color: #80ccff; -fx-background-radius: 15px 15px 0px 15px; -fx-border-radius: 15px 15px 0px 15px");

        //messageOut.getChildren().add(outMessage);
    }
    private void addSendChatToContainer(){
        chat.getChildren().add(outMessage);
        //date.setLayoutY(messageOut.getPrefHeight() + 15);
    }

    public void addGetChatToContainer(){
        try{
            chat.getChildren().add(inMessage);
        }catch(IllegalArgumentException e){

        }catch(IllegalStateException e){

        }
    }

    private void initChat(){
        chat.setPrefWidth(chatSC.getPrefWidth());
        chat.setMaxWidth(chatSC.getPrefWidth());
        //chat.setPrefHeight(800);
        chat.setStyle("-fx-padding: 5px 5px 26px 0px;");
        chat.setSpacing(5);
    }

    private void initChatTo(){

    }

    private void initChatContainer(){
        chatContainer.setPrefHeight(this.getPrefHeight() - (sendContainer.getPrefHeight() + 2));
        chatContainer.setPrefWidth(this.getPrefWidth() * 0.60);
        chatContainer.setLayoutY(0);
        chatContainer.setLayoutX(0);
        chatContainer.setSpacing(0);
        chatContainer.setStyle("-fx-border-width: 0.5mm; -fx-border-color: #000;");
        chatContainer.getChildren().add(0,chatSC);
        chatContainer.getChildren().add(1,sendContainer);
    }

    private void initSendContainer(){
        sendContainer.setPrefHeight(35);
        sendContainer.setPrefWidth(chatContainer.getPrefWidth());


        messages.setPromptText("text");
        messages.setPrefWidth(chatContainer.getPrefWidth() - 42);
        messages.setPrefHeight(12);
        //messages.setWrapText(true);
        messages.setMaxHeight(messages.getPrefHeight());
        messages.setLayoutY(0);
        messages.setLayoutX(chatToSC.getPrefWidth() + 1);
        messages.setStyle("-fx-font-size: 13; -fx-border-width: 0.5mm; -fx-border-color: #000; -fx-outline: none;");
        messages.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    if(!messages.getText().equals("")){
                        initSendChat(messages.getText());
                        addSendChatToContainer();
                        sendMessage(messages.getText());
                        messages.setText("");
                    }
                }
            }
        });

        sendIcon.setContent("M2.01 21L23 12 2.01 3 2 10l15 2-15 2z");
        sendIcon.setFill(Paint.valueOf("#A9A9A9"));
        send.setPrefHeight(35);
        send.setCursor(Cursor.HAND);
        send.setPrefWidth(39);
        send.setLayoutY(0);
        send.setLayoutX(messages.getPrefWidth() - 0.5);
        send.setStyle("-fx-border-width: 0.75mm; -fx-border-color: #000; -fx-border-radius: 0; -fx-background-color: #fff; -fx-padding: 0; -fx-outline: none; -fx-padding: 2;");
        send.setAlignment(Pos.CENTER);
        send.setOnAction(e->{
            if(!messages.getText().equals("")){
                initSendChat(messages.getText());
                addSendChatToContainer();
                sendMessage(messages.getText());
                messages.setText("");
            }
        });

        sendContainer.getChildren().add(messages);
        sendContainer.getChildren().add(send);

    }

    private void initChatSC(){
        chatSC.setPrefHeight(this.getPrefHeight() - (sendContainer.getPrefHeight() + 34));
        chatSC.setPrefWidth(chatContainer.getPrefWidth());
        /*chatSC.setLayoutY(0);
        chatSC.setLayoutX(0);*/
        chatSC.setStyle("-fx-background: #fff;");
        //chatSC.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        chatSC.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        chatSC.vvalueProperty().bind(chat.heightProperty());
        chatSC.setContent(chat);
    }

    private void initChatToSC(){
        chatToSC.setPrefHeight(this.getPrefHeight());
        chatToSC.setPrefWidth(this.getPrefWidth() * 0.40);
        chatToSC.setLayoutX(chatContainer.getPrefWidth());
        chatToSC.setLayoutY(0);
        chatToSC.setStyle("-fx-background: #fff;");
        chatToSC.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        chatToSC.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        chatToSC.setContent(chatTo);
    }

    /**This sets the overall properties of the ChatPane**/
    private void initChatPane(){
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setPrefHeight(395);
        this.setPrefWidth(595);
        this.setStyle("-fx-padding: 0");
        this.getChildren().add(chatToSC);
        this.getChildren().add(chatContainer);
    }

    /**This constructs the ChatPAne class**/
    ChatPane2(){
        initComps();
        initChatPane();
        initChatContainer();
        initSendContainer();
        initChatToSC();
        initChatSC();
        initChat();
        initChatTo();
        //Thread chat = new Thread(this);
        getConnected();
        try{
            receiveData();
        }catch(NullPointerException e){

        }
        //run();
        //chat.start();
        //waitForRequest();
    }





    /*@Override
    public void run() {
        receiveData();
    }*/

}
