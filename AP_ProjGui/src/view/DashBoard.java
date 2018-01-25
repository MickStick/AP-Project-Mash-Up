package view;

/**
 * Created by micks on 3/13/2017.
 */

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Screen;
import java.awt.Desktop;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class DashBoard implements Initializable {
    @FXML
    AnchorPane rootPane, personaBar;
    @FXML
    InternalPane querySection, chatSection, replySection;
    @FXML
    QueryPane queryPane;
    @FXML
    ChatPane chatPane;
    @FXML
    ComposePane composePane;
    @FXML
    GridPane pBarBtnCont;
    @FXML
    Button chkBtn, contBtn, chatBtn;
    @FXML
    Label personName;
    Socket con, chatCon;
    ObjectInputStream is;
    ObjectOutputStream os;
    Person emp;



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        try {
            //con = new Socket("127.0.0.5",3000);
            con = new Socket("127.0.0.5", 4000);
            System.out.println("TestClient Connected");
        } catch (IOException e) {
            System.out.println("Can't connect to Server!!!");
            e.printStackTrace();
        }
    }

    public void waitForRequest(){
        while (true) {
            try {
                while(true) {
                    DataInputStream din = new DataInputStream(con.getInputStream());
                    DataOutput dout = new DataOutputStream(con.getOutputStream());
                    String data = din.readUTF();
                    System.out.println("Received Message");
                    System.out.println(data);
                }
            } catch (IOException e) {
                System.out.println("Chat Problems");
                break;
            }
            /*try{

                //waitForRequest();
            }catch(IOException e){
                System.out.println("Can't Send");
            }*/

        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**This function sets the properties of the root Anchor Pane that holds the dashboard components**/
    public void initRootPane(){
        rootPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
        rootPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        rootPane.setLayoutY(Screen.getPrimary().getVisualBounds().getMinY());
        rootPane.setLayoutX(Screen.getPrimary().getVisualBounds().getMinX());
    }

    /**This function initializes and sets the properties of the pane that hold the query related functions**/
    public void initQueryPanes(){
        queryPane = new QueryPane(emp);
        //queryPane = (AnchorPane) FXMLLoader.load(getClass().getResource("../com/ARDS/view/AdminView.fxml"));
        //queryPane.setStyle("-fx-border-width: 0.5mm; -fx-border-color: #000");
        queryPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() - 90);
    }

    /**This function intializes and sets the propertis of the Internal Pane that holds the querypane**/
    public void initQuerySection(){
        querySection = new InternalPane();
        querySection.setSize(795,Screen.getPrimary().getVisualBounds().getHeight() - 90);
        querySection.setPosition(0,personaBar.getPrefHeight()+2);
        querySection.setTitle("Student Queries");
        querySection.setScrollable(false,false);/**Sets the internal pane's scrolling to false**/
        querySection.addToPane(0,0, queryPane);
    }

    /**This function initializes and sets the properties of the pane that hold the chat related components **/
    /**then initializes and sets the properties of the internal pane that holds the chat pane**/
    public void initChatSection(){
        chatPane = new ChatPane();
        chatPane.setSize(Screen.getPrimary().getVisualBounds().getWidth() - (querySection.getPrefWidth() + 6),399);
        chatSection = new InternalPane();
        chatSection.setSize(Screen.getPrimary().getVisualBounds().getWidth() - (querySection.getPrefWidth() + 6),400);
        chatSection.setPosition(querySection.getPrefWidth() + 2, Screen.getPrimary().getVisualBounds().getHeight() - 428);
        chatSection.setTitle("Chat");
        chatSection.setScrollable(true,false);/**Sets the internal pane's scrollable to horizontal scrolling only**/
        chatSection.addToPane(0,0,chatPane);
    }

    /**This function initializes and set properties of the pane that holds the components responsible for replying**/
    /**to queries then initializes and sets the properties if the internal pane that holds the pane for replying**/
    public void initReplySection(){

        composePane = new ComposePane(chatSection.getPrefWidth(),(querySection.getPrefHeight() - 400), (Stage) rootPane.getScene().getWindow());

        replySection = new InternalPane();
        replySection.addToPane(0,0, composePane);
        replySection.setSize(composePane.getPrefWidth(), composePane.getPrefHeight());
        replySection.setPosition(querySection.getPrefWidth() + 2, personaBar.getPrefHeight() + 1);
        replySection.setTitle("Compose");
        replySection.setScrollable(false,false);/**Sets the internal pane scrolling to false**/
    }

    /**This function adds the components that should be visible on start to the root AnchorPane**/
    public void addToRootPane(){
        rootPane.getChildren().addAll(querySection,chatSection);
    }

    /**This function sets the properties of the section that holds employee personal info and navigation buttons**/
    /**This also sets action listeners to the navigation buttons**/
    public void initPBarBottonContainer(){
        pBarBtnCont.setLayoutY(7);
        pBarBtnCont.setLayoutX(personaBar.getPrefWidth() - 250);
        chkBtn.setId("srchB");////Sets CSS id
        chkBtn.setOnAction(e->{/**Sets action listener to dashboard button**/
           try{
               if(!rootPane.getChildren().contains(querySection)){/**Checks if the section containing query functionality is in the root AnchorPane**/
                   rootPane.getChildren().add(querySection);/**Add the query section to the root AnchorPane**/
               }
           }catch(NullPointerException qne){/**if the query section hasn't been initialized then a NullPointerException is caught**/
               initQuerySection();/**Initializes the query section**/
               rootPane.getChildren().add(querySection); /**Adds query section to root AnchorPane**/
           }

        });
        contBtn.setId("srchB");////Sets CSS id
        contBtn.setOnAction(e->{/**Sets action listener to reply/compose button**/
            try{
                if(!rootPane.getChildren().contains(replySection)){/**Checks if the reply/compose section containing reply functionality is in the root AnchorPane**/
                    initReplySection();/**Initializes the reply/compose section**/
                    rootPane.getChildren().add(replySection);/**Add the reply/compose section to the root AnchorPane**/
                }

            }catch(NullPointerException ignored){/**if the reply/compose section hasn't been initialized then a NullPointerException is caught**/
                initReplySection();/**Initializes the reply/compose section**/
                rootPane.getChildren().add(replySection);/**Add the reply/compose section to the root AnchorPane**/
            }
        });
        chatBtn.setId("srchB");////Sets CSS id
        chatBtn.setOnAction(e-> {/**Sets action listener to chat navigation button**/
            try{
                if(!rootPane.getChildren().contains(chatSection)){/**Checks if the chat section containing reply functionality is in the root AnchorPane**/
                    rootPane.getChildren().add(chatSection);/**Add the chat section to the root AnchorPane**/
                }
            }catch(NullPointerException ne){/**if the reply/compose section hasn't been initialized then a NullPointerException is caught**/
                initChatSection();/**Initializes the chat section**/
                rootPane.getChildren().add(chatSection);/**Add the chat section to the root AnchorPane**/
            }
        });
    }

    /**This functions gets the employee info from file and initializes the employee info components**/
    public void initPersonaInfo(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("PersonData.bin"));
            emp = new Person((Person) in.readObject());

        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }catch(IOException e){
            System.out.println("Can't Read From File");
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found");
        }
        personName.setText(emp.getName());
    }

    /**This function is used to run specific functions on start**/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //getConnected();
        initPersonaInfo();
        initRootPane();
        initPBarBottonContainer();
        initQueryPanes();
        initQuerySection();
        initChatSection();
        //initReplySection();
        addToRootPane();

    }

}

/**This class is used to designed the Internal Pane**/
class InternalPane extends Pane{
    @FXML
    private Pane tBar;
    @FXML
    private ScrollPane body;
    @FXML
    private Button close;
    @FXML
    private Label title;

    public Boolean isMinimized, isClosed;

    /**This initializes the components of the internal pane**/
    private void InitComps(){
        body = new ScrollPane();
        tBar = new Pane();
        title = new Label("Internal Pane");
        close = new Button("X");
    }

    /**This sets the title and close button of the internal pane**/
    private void InitTBarComps(){
        title.setStyle("-fx-text-fill: #000; -fx-font-size: 14; -fx-font-weight: 800;");
        title.setLayoutY(2);
        title.setLayoutX(5);
        close.setStyle("-fx-background-color: #fff; -fx-border-width: 0.5mm; -fx-border-color: #000; -fx-border-radius: 3; -fx-font-size: 12; -fx-padding: 0; -fx-font-weight: 800;");
        close.setPrefHeight(3);
        close.setPrefWidth(18);
        close.setLayoutY(2);
        close.setLayoutX(tBar.prefWidthProperty().getValue() - (close.prefWidthProperty().getValue() + 3));
        close.setOnAction((ActionEvent e) ->{/**Sets action listerner to close button**/
            /**Closes the internal pane**/
            Pane pane = (Pane) this.getParent();
            pane.getChildren().remove(this);
        });
    }

    /**This sets the properties of the title bar of the internal pane**/
    /**It also adds the titl and close button to the title bar**/
    private void InitTBar(){
        tBar.setLayoutX(2);
        tBar.setLayoutY(2);
        tBar.setPrefHeight(30);
        tBar.setStyle("-fx-background-color: #fff; -fx-border-width: 0mm 0mm 0.5mm 0mm; -fx-border-color: #000;");
        tBar.setCursor(Cursor.HAND);
        tBar.setPrefWidth(this.getPrefWidth() - 4);
        tBar.getChildren().add(0,title);
        tBar.getChildren().add(1,close);
        tBar.setOnMouseClicked(m ->{/**Sets action listener to title bar**/
            /**Minimes the internal pane**/
            if(!isMinimized){
                body.setPrefHeight(0);
                this.setPrefHeight(tBar.getPrefHeight());
                this.setTranslateY(this.getMaxHeight() - tBar.getPrefHeight());
                body.visibleProperty().setValue(false);
                isMinimized = true;
            }else{
                this.setPrefHeight(this.getMaxHeight());
                this.setTranslateY(0);
                body.setPrefHeight(this.getMaxHeight() - (tBar.getPrefHeight() + 4));
                body.visibleProperty().setValue(true);
                isMinimized = false;
            }

        });
    }

    /**This sets the properties the the scrollpane and sets is as the body of the internal pane**/
    private void InitBody(){
        body.setPrefHeight(this.getPrefHeight() - (tBar.getPrefHeight() + 4));
        body.setPrefWidth(this.getPrefWidth() - 4);
        body.setLayoutX(2);
        body.setLayoutY(tBar.getPrefHeight() + 2);
        body.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        body.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**This sets the internal panes properties and adds the title bar and body**/
    private void InitInternalPane(){
        this.setLayoutY(0);
        this.setLayoutX(0);
        this.setStyle("-fx-background-color: #fff; -fx-border-width: 0.5mm; -fx-border-color: #000;");
        this.setPrefHeight(300);
        this.setMaxHeight(300);
        this.setPrefWidth(200);
        this.getChildren().add(tBar);
        this.getChildren().add(body);
    }

    /**This sets allows for the title to be changed**/
    public void setTitle(String title){
        this.title.setText(title);
    }

    /**This allows for children to be added to the body **/
    /**and to set their coordinates in the body**/
    public void addToPane(double X, double Y, Node node){
        node.setLayoutY(Y);
        node.setLayoutX(X);
        body.setContent(node);
    }

    /**This allows for children to be added to the body **/
    /**without setting their coordinates in the body**/
    public void addToPane(Node node){
        body.setContent(node);
    }

    /**This sets the position of the internal pane**/
    public void setPosition(double X, double Y){
        this.setLayoutX(X);
        this.setLayoutY(Y);
    }

    /**This gets the position of the internal pane**/
    public double[] getPosition(){
        double pos[] = {this.getLayoutX(), this.getLayoutY()};
        return pos;
    }

    /**This sets whether the internal pane is closed or not**/
    /**utilizing the visibility property of pane**/
    public void setClose(boolean close){
        if(close){
            isClosed = false;
        }else{
            isClosed = true;
        }
        this.visibleProperty().setValue(isClosed);
    }

    /**This sets the size of the internal pane**/
    public void setSize(double width, double height){
        this.setPrefWidth(width);
        this.setMaxWidth(width);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        tBar.setPrefWidth(this.getPrefWidth() - 4);
        close.setLayoutX(tBar.prefWidthProperty().getValue() - (close.prefWidthProperty().getValue() + 3));
        body.setPrefHeight(this.getPrefHeight() - (tBar.getPrefHeight() + 4));
        body.setPrefWidth(this.getPrefWidth() - 4);
    }

    /**This gets the size of the internal pane**/
    public double[] getSize(){
        double size[] = {this.getPrefWidth(),this.getPrefHeight()};
        return size;
    }

    /**This sets how the internal pane should scroll**/
    public void setScrollable(boolean Hstate, boolean Vstate){
        if(!Vstate) {
            body.setVmax(0);
        }

        if(!Hstate){
            body.setHmax(0);
        }
    }

    /**This sets the CSS style of the scrollpane/body of the internal pane**/
    public void setBodyStyle(String style){
        body.setStyle(style);
    }

    /**The constructor**/
    InternalPane(){
        InitComps();
        InitInternalPane();
        InitTBar();
        InitTBarComps();
        InitBody();
        isMinimized = false;
    }

}

/**This class is used to design the pane containing the chat related functionality**/
class ChatPane extends Pane implements Serializable{
    @FXML
    private VBox chat,chatTo, chatContainer;
    @FXML
    private ScrollPane chatSC;
    @FXML
    private Label inMessage,outMessage, name, date, status, messenger;
    @FXML
    private Pane  ppl, sendContainer, chatToSC, statContainer;
    @FXML
    AnchorPane messageIn,messageOut;
    @FXML
    private Button send, statusbtn;
    @FXML
    private TextField messages;
    /*@FXML
    private TextArea messages;*/
    @FXML
    SVGPath sendIcon, messengerPath;

    Socket con;
    DataInputStream din;
    DataOutputStream dout;
    String data;
    Task getIn;

    /**This function is used to connect the chat section to the server**/
    public void getConnected(){
        new Thread(new Runnable() {/**Starts a new thread that tries to connect to the server**/
            @Override
            public void run() {

                try {
                    //con = new Socket("127.0.0.5",3000);
                    //con = new Socket("LAPTOP-1LV8BSRD",3001);
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

    /**This function is used to receive String messages from the server**/
    public void receiveData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        din = new DataInputStream(con.getInputStream()); /**initializes new data streams for receiving messages**/
                        data = din.readUTF();
                        System.out.println(data);
                        Platform.runLater(new Runnable() { /**Adds message to the scene**/
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

    /**This function is used to set the chat pane's position in the scene**/
    public void setPosition(double X, double Y){
        this.setLayoutY(Y);
        this.setLayoutX(X);
    }

    /**This functions return the position of the chat pane in the scene**/
    public double[] getPostion(){
        double pos[] = {this.getLayoutX(),this.getLayoutY()};

        return pos;
    }

    /**This sets the size of the chat pane**/
    public void setSize(double width, double height){
        this.setPrefWidth(width);
        this.setMaxWidth(width);
        this.setPrefHeight(height);
        this.setMaxHeight(height);
        chatContainer.setPrefHeight(this.getPrefHeight() - (sendContainer.getPrefHeight() + 2));
        chatToSC.setPrefHeight(this.getPrefHeight());
        chatTo.setPrefSize(chatToSC.getPrefWidth(),(chatToSC.getPrefHeight() - 70));
        statContainer.setLayoutY(chatTo.getPrefHeight() - 1);
        statusbtn.setPrefSize(chatTo.getPrefWidth()/2,31);
        status.setPrefSize((chatTo.getPrefWidth()/2) - 10,34);
        status.setLayoutX(statusbtn.getPrefWidth() + 10);
    }

    /**This function returns the size of the chat pane**/
    public double[] getSize(){
        double size[] = {this.getPrefWidth(), this.getPrefHeight()};

        return size;
    }

    public void initMessenger(){
        messenger = new Label();
        messenger.setPrefSize(200,22);
        messenger.setStyle("-fx-border-width: 0.5mm; -fx-border-color: #000");
        messenger.setText("Someone");
        messenger.setTranslateX(1);

        messengerPath = new SVGPath();
        messengerPath.setContent("M19 2H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h4l3 3 3-3h4c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm-7 3.3c1.49 0 2.7 1.21 2.7 2.7 0 1.49-1.21 2.7-2.7 2.7-1.49 0-2.7-1.21-2.7-2.7 0-1.49 1.21-2.7 2.7-2.7zM18 16H6v-.9c0-2 4-3.1 6-3.1s6 1.1 6 3.1v.9z");
        messengerPath.setScaleX(0.8);
        messengerPath.setScaleY(0.8);
        messenger.setGraphic(messengerPath);

        chatTo.getChildren().add(0,messenger);

    }
    /**This function is used to send String messages to the server**/
    public void sendMessage(String msg){
        try{
            DataOutputStream dout = new DataOutputStream(con.getOutputStream()); /**intializes new data stream for sending message*/
            dout.writeUTF(msg);
            System.out.println("Sent");
        }catch(IOException ex){
            System.out.println("Can't send data");
        }
    }

    /**This function initializes the companents of the chat pane**/
    private void initComps(){
        name = new Label("");
        ppl = new Pane();
        chatContainer = new VBox();
        sendContainer = new Pane();
        chat = new VBox();
        chatTo = new VBox();
        chatSC = new ScrollPane();
        chatToSC = new Pane();
        send = new Button();
        messages = new TextField();
        //messages = new TextArea();
        sendIcon = new SVGPath();
        send.setGraphic(sendIcon);

    }

    /**This function is used to set the component that contains received messages**/
    public void initGetChat(String m){
        inMessage = new Label(m);
        inMessage.setWrapText(true);
        inMessage.setTranslateX(20);
        inMessage.setMaxWidth(250);
        inMessage.setStyle("-fx-text-fill: #000; -fx-padding: 1.5mm 3mm 1.5mm 3mm; -fx-background-color: #e6e6e6; -fx-background-radius: 15px 15px 15px 0px; -fx-border-radius: 15px 15px 15px 0px");
        addGetChatToContainer();/**Adds received message to scene**/
    }

    /**This function is used to initializes and styles the sending messages **/
    private void initSendChat( String m){
        outMessage = new Label(m);

        outMessage.setWrapText(true);
        outMessage.setTranslateX((chat.getPrefWidth() * 0.2) + 5);
        outMessage.setMaxWidth(250);
        outMessage.setLayoutY(8);
        outMessage.setStyle("-fx-text-fill: #000; -fx-padding: 1.5mm 3mm 1.5mm 3mm; -fx-background-color: #80ccff; -fx-background-radius: 15px 15px 0px 15px; -fx-border-radius: 15px 15px 0px 15px");

    }

    /****/
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

    private void initChatToSection(){
        chatTo = new VBox();
        chatTo.setLayoutX(0);
        chatTo.setLayoutY(0);
        chatTo.setPrefSize(chatToSC.getPrefWidth(),(chatToSC.getPrefHeight() - 70));
        chatTo.setSpacing(2);


        statContainer = new Pane();
        statContainer.setPrefWidth(chatTo.getPrefWidth());
        statContainer.setLayoutX(0);
        statContainer.setLayoutY(chatTo.getPrefHeight() - 1);
        statContainer.setStyle("-fx-border-width: 0.5mm 0mm 0mm 0mm; -fx-border-color: #000");

        statusbtn = new Button();
        statusbtn.setText("Go Online");
        statusbtn.setPrefSize(chatTo.getPrefWidth()/2,27);
        statusbtn.setAlignment(Pos.CENTER);
        statusbtn.setLayoutY(3);
        statusbtn.setLayoutX(2);
        statusbtn.setId("srchB");
        statusbtn.setOnAction(e ->{
            //Employee.goOnline();
            statusbtn.setText("Go Offline");
            status.setText("Online");
            status.setStyle("-fx-text-fill: green");
            initMessenger();
        });

        status = new Label();
        status.setText("offline");
        status.setStyle("-fx-font-weight: 800; -fx-text-indent: 10; -fx-text-alignment: center; -fx-text-fill: red");
        status.setPrefSize((chatTo.getPrefWidth()/2) - 10,34);
        status.setLayoutX(statusbtn.getPrefWidth() + 10);
        status.setLayoutY(0);

        statContainer.getChildren().add(statusbtn);
        statContainer.getChildren().add(status);

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
        //chatToSC.getChildren().addAll(chatTo,statContainer);
        chatToSC.getChildren().add(chatTo);
        chatToSC.getChildren().add(statContainer);
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
    ChatPane(){
        initComps();
        initChatPane();
        initChatContainer();
        initSendContainer();
        initChatToSection();
        initChatToSC();
        initChatSC();
        initChat();
        //Thread chat = new Thread(this);
        /*getConnected();
        try{
            receiveData();
        }catch(NullPointerException e){

        }*/
        //run();
        //chat.start();
        //waitForRequest();
    }



}

class ComposePane extends Pane {
    @FXML
    TextField To,Subject;
    @FXML
    TextArea Message;
    @FXML
    Button Send, Attach, Discard;
    @FXML
    SVGPath send, attach,disard;
    @FXML
    FileChooser fileChooser;

    Stage stage = new Stage();
    private Desktop desktop = Desktop.getDesktop();

    private void initComps(){
        To = new TextField();
        Subject = new TextField();
        Message = new TextArea();
        Send = new Button();
        Attach = new Button();
        Discard = new Button();
        fileChooser = new FileChooser();

    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    private void initComposeArea(){
        Send.setPrefHeight(33);
        Send.setPrefWidth(80);
        //Send.setText("Send");
        Send.setLayoutX(this.getPrefWidth() - (Send.getPrefWidth() + 10));
        Send.setLayoutY(5);
        Send.setId("srchB");

        send = new SVGPath();
        send.setContent("M2.01 21L23 12 2.01 3 2 10l15 2-15 2z");
        Send.setGraphic(send);

        Attach.setPrefHeight(33);
        Attach.setPrefWidth(80);
       // Attach.setText("Attach");
        Attach.setLayoutX(this.getPrefWidth() - ((Send.getPrefWidth()  + 10) * 2));
        Attach.setLayoutY(5);
        Attach.setId("srchB");
        Attach.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                /*if (file != null) {
                    openFile(file);
                }*/
            }
        });

        attach = new SVGPath();
        attach.setContent("M16.5 6v11.5c0 2.21-1.79 4-4 4s-4-1.79-4-4V5c0-1.38 1.12-2.5 2.5-2.5s2.5 1.12 2.5 2.5v10.5c0 .55-.45 1-1 1s-1-.45-1-1V6H10v9.5c0 1.38 1.12 2.5 2.5 2.5s2.5-1.12 2.5-2.5V5c0-2.21-1.79-4-4-4S7 2.79 7 5v12.5c0 3.04 2.46 5.5 5.5 5.5s5.5-2.46 5.5-5.5V6h-1.5z");
        Attach.setGraphic(attach);

        Discard.setPrefHeight(33);
        Discard.setPrefWidth(80);
        //Discard.setText("Discard");
        Discard.setLayoutX(this.getPrefWidth() - ((Send.getPrefWidth()  + 10) * 3));
        Discard.setLayoutY(5);
        Discard.setId("srchB");
        Discard.setOnAction(e->{
            InternalPane parent = (InternalPane) this.getParent().getParent().getParent().getParent();
            Pane grandParent = (Pane) parent.getParent();
            grandParent.getChildren().remove(parent);

        });

        disard = new SVGPath();
        disard.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
        Discard.setGraphic(disard);

        To.setPrefHeight(28);
        To.setPrefWidth(this.getPrefWidth() - 15);
        To.setId("srchF");
        To.setPromptText("To: ");
        To.setLayoutY(41);
        To.setLayoutX(5);

        Subject.setPrefHeight(28);
        Subject.setPrefWidth(this.getPrefWidth() - 15);
        Subject.setId("srchF");
        Subject.setPromptText("Subject: ");
        Subject.setLayoutY(To.getLayoutY() + (To.getPrefHeight() + 5));
        Subject.setLayoutX(5);

        Message.setPrefWidth(this.getPrefWidth() - 15);
        Message.setPrefHeight(this.getPrefHeight() * 0.4);
        Message.setId("srchF");
        Message.setLayoutY(Subject.getLayoutY() + (Subject.getPrefHeight()+ 5));
        Message.setLayoutX(5);
        Message.setPromptText("Message: ");
        Message.setWrapText(true);

    }

    private void addCompsToPane(){
        this.getChildren().add(Send);
        this.getChildren().add(Attach);
        this.getChildren().add(Discard);
        this.getChildren().add(To);
        this.getChildren().add(Subject);
        this.getChildren().add(Message);
    }

    private void initComposePane(double width, double height){
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setLayoutY(0);
        this.setLayoutX(0);
        this.getStylesheets().add(Main.class.getResource("../AdminV.css").toExternalForm());
    }
    ComposePane(double width, double height,Stage s){
        initComposePane(width, height);
        initComps();
        initComposeArea();
        addCompsToPane();
        try{
            stage = (Stage) this.getScene().getWindow();
        }catch(NullPointerException ne){
        }
    }



}

class QueryPane extends AnchorPane{

    @FXML
    AnchorPane searchPane, EnsearchPane;
    @FXML
    ScrollPane upScrollpane, vqScrollpane;
    @FXML
    GridPane updatePane;
    @FXML
    VBox viewQuery;
    @FXML
    Pane queryPane, queryBtnHolder;
    @FXML
    TextField searchField, EnsearchField, idtxt,FNtxt, LNtxt, etxt, baltxt, teltxt, cleartxt;
    @FXML
    ComboBox services;
    @FXML
    Label searchLabel, enSearchLabel, idlbl, fnamelbl, lnamelbl, emaillbl, ballbl, tellbl, clearlbl, sender, subject, query, date, aFile;
    @FXML
    Button searchbtn, Ensearchbtn,upbtn, rembtn, replybtn, openAbtn;
    @FXML
    SVGPath searchPath, runPath, replyPath, openAPath;

    public int in = 0;
    public Tooltip reply = new Tooltip("Alt + RMB to reply!");
    public Employee emp;
    public Enquiry enq;
    public boolean qOpen = false;
    private Desktop desktop = Desktop.getDesktop();
    public void initSrcPane(){
        searchPane = new AnchorPane();
        searchPane.setId("srchP");
        searchPane.setPrefSize(397,65);
        searchPane.setLayoutX(2);
        searchPane.setLayoutY(1);
        searchPane.setStyle("-fx-border-width: 0mm 0mm 0.5mm 0mm;");

        searchPath = new SVGPath();
        searchPath.setContent("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");

        searchLabel = new Label("Student Info");
        searchLabel.setLayoutX(5);
        searchLabel.setLayoutY(20);
        searchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 800");

        searchField = new TextField();
        searchField.setId("srchF");
        searchField.setPrefSize(180,31);
        searchField.setLayoutX(123.5);
        searchField.setLayoutY(20);

        searchbtn = new Button();
        searchbtn.setId("srchB");
        searchbtn.setPrefSize(45,33);
        searchbtn.setLayoutX(303);
        searchbtn.setLayoutY(20);
        searchbtn.setGraphic(searchPath);
        searchbtn.setCursor(Cursor.HAND);
        searchbtn.setOnAction(m ->{
            emp = new Employee();
            Response res = new Response();
            emp.setStudent(Employee.studentLoad(Integer.parseInt(searchField.getText())));
            if(res.getStudentObject() != null){
                popUpdatePane(String.valueOf(emp.getStudent().getStudentID()),emp.getStudent().getFirstName(),emp.getStudent().getLastName(),emp.getStudent().getEmail(),String.valueOf(emp.getStudent().getAccountBalance()),emp.getStudent().getPhoneNumber(),emp.getStudent().getClearanceStatus());
            }

        });


        searchPane.getChildren().add(searchLabel);
        searchPane.getChildren().add(searchField);
        searchPane.getChildren().add(searchbtn);
    }

    public void initESrchPane(){
        EnsearchPane = new AnchorPane();
        EnsearchPane.setId("srchP");
        EnsearchPane.setPrefSize(401,65);
        EnsearchPane.setLayoutX(399);
        EnsearchPane.setLayoutY(0);
        EnsearchPane.setStyle("-fx-border-width: 0mm 0mm 0mm 0.5mm;");

        runPath = new SVGPath();
        runPath.setContent("M8 5v14l11-7z");

        services = new ComboBox();
        services.setPrefSize(210,31);
        services.setLayoutX(93.5);
        services.setLayoutY(16);
        services.setStyle("-fx-font-size: 14px; -fx-font-weight: 800");
        services.setId("srchF");
        services.getItems().addAll(
                "Refunds",
                "Financial Clearance",
                "Semester Fee Statement",
                "Monies Owed",
                "Account Balance"
        );
        services.setOnAction(e->{
            Ensearchbtn.disableProperty().setValue(false);
        });

        enSearchLabel = new Label("Services");
        enSearchLabel.setLayoutX(10);
        enSearchLabel.setLayoutY(20);
        enSearchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 800");

        EnsearchField = new TextField();
        EnsearchField.setId("srchF");
        EnsearchField.setPrefSize(205,31);
        EnsearchField.setLayoutX(98.5);
        EnsearchField.setLayoutY(20);

        Ensearchbtn = new Button();
        Ensearchbtn.setId("srchB");
        Ensearchbtn.setPrefSize(45,33);
        Ensearchbtn.setLayoutX(303);
        Ensearchbtn.setLayoutY(16.5);
        Ensearchbtn.setGraphic(runPath);
        Ensearchbtn.setCursor(Cursor.HAND);
        Ensearchbtn.disableProperty().setValue(true);
        Ensearchbtn.setOnAction(m ->{
            /*System.out.println("Pressed");
            addToQueryView();*/
            Ensearchbtn.disableProperty().setValue(true);
        });

        EnsearchPane.getChildren().add(enSearchLabel);
        EnsearchPane.getChildren().add(services);
        /*EnsearchPane.getChildren().add(EnsearchField);*/
        EnsearchPane.getChildren().add(Ensearchbtn);
    }

    public void initUpdatePane(){
        upScrollpane = new ScrollPane();
        upScrollpane.setId("upscPane");
        upScrollpane.setPrefSize(399,475);
        upScrollpane.setLayoutX(1);
        upScrollpane.setLayoutY(66);
        upScrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        upScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        upScrollpane.setPrefHeight(this.getPrefWidth() - 255);

        updatePane = new GridPane();
        upScrollpane.setId("upPane");
        updatePane.setPrefSize(399,575);
        updatePane.setLayoutX(0);
        updatePane.setLayoutY(120);
        updatePane.setVgap(15);
        upScrollpane.setContent(updatePane);

        idlbl = new Label("Student ID: ");
        idlbl.setId("upLabel");
        fnamelbl = new Label("Firstname: ");
        fnamelbl.setId("upLabel");
        lnamelbl = new Label("Lastname: ");
        lnamelbl.setId("upLabel");
        emaillbl = new Label("Email: ");
        emaillbl.setId("upLabel");
        ballbl = new Label("Balance: ");
        ballbl.setId("upLabel");
        tellbl = new Label("Telephone: ");
        tellbl.setId("upLabel");
        clearlbl = new Label("Clearance");
        clearlbl.setId("upLabel");

        idtxt = new TextField("No Data To Be Shown");
        idtxt.setId("upTextField");
        idtxt.disableProperty().setValue(true);
        FNtxt = new TextField("No Data To Be Shown");
        FNtxt.setId("upTextField");
        FNtxt.disableProperty().setValue(true);
        LNtxt = new TextField("No Data To Be Shown");
        LNtxt.setId("upTextField");
        LNtxt.disableProperty().setValue(true);
        etxt = new TextField("No Data To Be Shown");
        etxt.setId("upTextField");
        etxt.disableProperty().setValue(true);
        baltxt = new TextField("No Data To Be Shown");
        baltxt.setId("upTextField");
        baltxt.disableProperty().setValue(true);
        teltxt = new TextField("No Data To Be Shown");
        teltxt.setId("upTextField");
        teltxt.disableProperty().setValue(true);
        cleartxt = new TextField("No Data To Be Shown");
        cleartxt.setId("upTextField");
        cleartxt.disableProperty().setValue(true);

        upbtn = new Button("Update");
        upbtn.setId("upbtn");
        upbtn.disableProperty().setValue(true);
        rembtn = new Button("Remove");
        rembtn.setId("upbtn");
        rembtn.disableProperty().setValue(true);
        rembtn.layoutYProperty().set(90);

        updatePane.add(idlbl,0,0);
        updatePane.add(fnamelbl,0,1);
        updatePane.add(lnamelbl,0,2);
        updatePane.add(emaillbl,0,3);
        updatePane.add(ballbl,0,4);
        updatePane.add(tellbl,0,5);
        updatePane.add(clearlbl,0,6);
        updatePane.add(idtxt,1,0);
        updatePane.add(FNtxt,1,1);
        updatePane.add(LNtxt,1,2);
        updatePane.add(etxt,1,3);
        updatePane.add(baltxt,1,4);
        updatePane.add(teltxt,1,5);
        updatePane.add(cleartxt,1,6);
        updatePane.add(upbtn, 1, 7);
        updatePane.add(rembtn, 1 , 8);


    }

    public void popUpdatePane(String id, String fn, String ln, String em, String bal, String tel, String clr){ //this populates the section for updating student data
        idtxt.setText(id);
        idtxt.disableProperty().setValue(true);
        FNtxt.setText(fn);
        FNtxt.disableProperty().setValue(false);
        LNtxt.setText(ln);
        LNtxt.disableProperty().setValue(false);
        etxt.setText(em);
        etxt.disableProperty().setValue(false);
        baltxt.setText("$" + bal);
        baltxt.disableProperty().setValue(false);
        teltxt.setText(tel);
        teltxt.disableProperty().setValue(false);
        cleartxt.setText(tel);
        cleartxt.disableProperty().setValue(false);
        upbtn.disableProperty().setValue(false);
        rembtn.disableProperty().setValue(false);
    }

    public void initQueryButtonHolder(){
        queryBtnHolder = new Pane();
        queryBtnHolder.setPrefSize(400,50);
        queryBtnHolder.setLayoutY(vqScrollpane.getPrefHeight());
        queryBtnHolder.setLayoutX(399);

        replybtn = new Button();
        replybtn.setPrefSize(33,33);
        replybtn.setLayoutX(343);
        replybtn.setLayoutY(8.5);
        replybtn.setId("srchB");

        replyPath = new SVGPath();
        replyPath.setContent("M10 9V5l-7 7 7 7v-4.1c5 0 8.5 1.6 11 5.1-1-5-4-10-11-11z");
        replybtn.setGraphic(replyPath);

        openAbtn = new Button();
        openAbtn.setPrefSize(33,33);
        openAbtn.setLayoutX(293);
        openAbtn.setLayoutY(8.5);
        openAbtn.setId("srchB");

        openAPath = new SVGPath();
        openAPath.setContent("M2 12.5C2 9.46 4.46 7 7.5 7H18c2.21 0 4 1.79 4 4s-1.79 4-4 4H9.5C8.12 15 7 13.88 7 12.5S8.12 10 9.5 10H17v2H9.41c-.55 0-.55 1 0 1H18c1.1 0 2-.9 2-2s-.9-2-2-2H7.5C5.57 9 4 10.57 4 12.5S5.57 16 7.5 16H17v2H7.5C4.46 18 2 15.54 2 12.5z");
        openAbtn.setGraphic(openAPath);

        queryBtnHolder.getChildren().add(replybtn);
        queryBtnHolder.getChildren().add(openAbtn);
    }

    public void initViewQuery(){
        vqScrollpane = new ScrollPane();
        vqScrollpane.setId("vqscPane");
        vqScrollpane.setPrefSize(399,Screen.getPrimary().getVisualBounds().getHeight() - 170);
        vqScrollpane.setLayoutX(399);
        vqScrollpane.setLayoutY(65);
        vqScrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vqScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vqScrollpane.setPrefHeight(this.getPrefWidth() - 255);


        viewQuery = new VBox();
        viewQuery.setId("vqPane");
        viewQuery.setPrefWidth(401);
        viewQuery.setLayoutX(399);
        viewQuery.setLayoutY(60);
        viewQuery.setSpacing(0);
        vqScrollpane.setContent(viewQuery);
    }
    public void initQuerySection(String sendertxt, String subtxt, String querytxt, String datetxt){

        queryPane = new AnchorPane();
        queryPane.setPrefWidth(401.0);
        queryPane.setId("enqPane "  + String.valueOf(in));
        queryPane.accessibleRoleProperty().setValue(AccessibleRole.BUTTON);
        queryPane.cursorProperty().setValue(Cursor.HAND);
        queryPane.requestFocus();
        queryPane.getStyleClass().add("enqPane");


        sender = new Label(sendertxt);
        sender.setLayoutX(5);
        sender.setLayoutY(1);
        sender.setPrefWidth(305.0);
        sender.setStyle("-fx-font-weight: 800");

        subject = new Label(subtxt);
        subject.setLayoutX(5);
        subject.setLayoutY(17);
        subject.setPrefWidth(160.0);
        subject.setStyle("-fx-font-weight: 800");

        query = new Label(querytxt);
        query.setLayoutX(5);
        query.setLayoutY(50);
        query.setPrefWidth(305.0);
        //query.setWrapText(true);
        //query.setPrefHeight(18);//query.getFont().getSize() * ( query.getPrefWidth() / query.getText().length()));
        query.setTextOverrun(OverrunStyle.CLIP);
        query.setMinHeight(query.getPrefHeight());
        query.setStyle("-fx-font-weight: 800");
        query.setTooltip(reply);
        query.toFront();

        date = new Label(datetxt);
        date.setLayoutX(320.0);
        date.setLayoutY(17);
        date.prefWidth(80);
        date.setStyle("-fx-font-weight: 800");


        aFile = new Label();
        aFile.setText("A file is attached");
        aFile.setLayoutX(5);
        aFile.setLayoutY(32);
        aFile.setStyle("-fx-font-weight: 800");
        aFile.setOnMouseClicked(m ->{

        });

        queryPane.getChildren().addAll(sender,subject,query,aFile,date);
        queryPane.setOnMouseClicked(e->{ //Opens and closes a query by clicking said query

            for(int x = 0; x < viewQuery.getChildren().size(); x++){
                if(viewQuery.getChildren().get(x).hoverProperty().get()){
                    Pane here = (Pane) viewQuery.getChildren().get(x);
                    Label text = (Label) here.getChildren().get(2);
                /*Transition TDown = new Transition() {
                    {
                        setCycleDuration(Duration.millis(200));
                    }
                    @Override
                    protected void interpolate(double fraction) {

                        text.setPrefHeight(150 * (fraction));

                    }
                };
                Transition TUp = new Transition() {
                    {
                        setCycleDuration(Duration.millis(200));
                    }
                    @Override
                    protected void interpolate(double fraction) {
                        text.setPrefHeight(text.getMinHeight() * ( -1.0 * fraction));

                    }
                };
                if(text.getPrefHeight() != 200) {
                    TDown.play();
                }else{
                    TUp.play();
                }*/
                    if(!text.isWrapText()){
                        text.setWrapText(true);
                    }else{
                        here.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
                            @Override
                            public void handle(javafx.scene.input.KeyEvent event) {
                                if (event.getCode() == KeyCode.ALT) {
                                }
                            }
                        });
                        text.setWrapText(false);
                    }
                }
            }


        });
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addToQueryView(){//this adds the query pane(created in InitEnquiryPane) to the view/receive query section
        in += 1;
        initQuerySection(String.valueOf(enq.getEnqID()),"Subject",enq.getMessage(),enq.getsDate());
        viewQuery.getChildren().add(0,queryPane);
    }

    public void addToRoot(){

        initSrcPane();
        initESrchPane();
        initUpdatePane();
        initViewQuery();
        addToQueryView();
        initQueryButtonHolder();
        this.getChildren().add(searchPane);
        this.getChildren().add(upScrollpane);
        this.getChildren().add(EnsearchPane);
        this.getChildren().add(vqScrollpane);
        this.getChildren().add(queryBtnHolder);
    }
    public void initRoot(){
        this.setPrefSize(800,540);
        this.setId("secPane");
    }
    QueryPane(Person per){
        enq = new Enquiry();
        enq = Employee.getEnquiries("Enquiries", per.getId());
        initRoot();
        addToRoot();
    }
}




