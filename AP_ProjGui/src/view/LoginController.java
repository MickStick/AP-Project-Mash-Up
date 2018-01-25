package view;

import models.Employee;
import models.Person;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.Main;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by micks on 3/3/2017.
 */
public class LoginController implements Initializable{


    @FXML
    Label topImg = new Label(), errMsg, ConfMsg;
    @FXML
    TextField user;
    @FXML
    PasswordField pass;
    @FXML
    Button log;
    @FXML
    SVGPath logIcon;

    Socket con;
    ObjectInputStream is;
    ObjectOutputStream os;
    Person emp;
    Employee eobj;

    public void getConnected(){
        try{
            //con = new Socket("127.0.0.5",3000);
            con = new Socket("127.0.0.5",4000);
            System.out.println("Login Connected");

           /* try {
                DataInputStream din = new DataInputStream(con.getInputStream());
                String data = din.readUTF();
                System.out.println("Received Message");
                System.out.println(data);
            }catch (IOException e){
                System.out.println("Didn't Receive Message");
            }*/
        }catch(IOException e){
            System.out.println("Can't connect to Server!!!");
            e.printStackTrace();
        }
    }

    public void sendData(Person p){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("PersonData.bin"));
            out.writeObject(p);
            out.close();
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }catch(IOException e){
            System.out.println("Can't Write To File");
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //getConnected();
        eobj = new Employee();
        logIcon = new SVGPath();
        logIcon.setScaleX(5);
        logIcon.setScaleY(5);
        logIcon.setContent("M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        topImg.setGraphic(logIcon);
        topImg.setPrefWidth(100);
        topImg.setPrefHeight(100);
        topImg.setTextFill(Color.web("#000000"));
        errMsg.setVisible(false);
        ConfMsg.setVisible(false);
        user.focusTraversableProperty().setValue(true);
        user.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    errMsg.setVisible(false);
                    eobj = Employee.empLogin("Login", Integer.parseInt(user.getText()), pass.getText());
                    if(eobj != null){
                        Platform.runLater(new Runnable() { /**Adds message to the scene**/
                        @Override
                        public void run() {
                            try {

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("DashBoard.fxml"));
                                emp = new Person(eobj.getFirstName() + " " + eobj.getLastName(), eobj.getEmpID());
                                sendData(emp);
                                Scene scene;
                                scene = new Scene((Parent) loader.load(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 29);
                                scene.getStylesheets().add(Main.class.getResource("../../../AdminV.css").toExternalForm());
                                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                                primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                                primaryStage.setScene(scene);



                            } catch (IOException ex) {
                                System.out.println("Can't create parent!");
                                ex.printStackTrace();
                            }
                        }
                        });
                        }else{
                            errMsg.setVisible(true);
                        }

                    //if(user.getText().equals("SuperUser") && pass.getText().equals("LetMeIn")) {
                        /*Platform.runLater(new Runnable() { /**Adds message to the scene**/
                        /*@Override
                        public void run() {
                            try {

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("DashBoard.fxml"));
                                emp = new Person(user.getText());
                                sendData(emp);
                                Scene scene;
                                scene = new Scene((Parent) loader.load(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 29);
                                scene.getStylesheets().add(Main.class.getResource("../../../AdminV.css").toExternalForm());
                                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                                primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                                primaryStage.setScene(scene);



                            } catch (IOException ex) {
                                System.out.println("Can't create parent!");
                                ex.printStackTrace();
                            }
                        }
                        });*/
                    /*}else{
                        errMsg.setVisible(true);
                    }*/
                }
            }
        });
        pass.focusTraversableProperty().setValue(true);
        pass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    errMsg.setVisible(false);
                    eobj = Employee.empLogin("Login", Integer.parseInt(user.getText()), pass.getText());
                    if(eobj != null){
                        Platform.runLater(new Runnable() { /**Adds message to the scene**/
                        @Override
                        public void run() {
                            try {

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("DashBoard.fxml"));
                                emp = new Person(eobj.getFirstName() + " " + eobj.getLastName(), eobj.getEmpID());
                                sendData(emp);
                                Scene scene;
                                scene = new Scene((Parent) loader.load(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 29);
                                scene.getStylesheets().add(Main.class.getResource("../AdminV.css").toExternalForm());
                                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                                primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                                primaryStage.setScene(scene);



                            } catch (IOException ex) {
                                System.out.println("Can't create parent!");
                                ex.printStackTrace();
                            }
                        }
                        });
                    }else{
                        errMsg.setVisible(true);
                    }
                    /*if(user.getText().equals("SuperUser") && pass.getText().equals("LetMeIn")) {
                        Platform.runLater(new Runnable() { *//**Adds message to the scene**//*
                        @Override
                        public void run() {
                            try {

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("DashBoard.fxml"));
                                emp = new Person(user.getText());
                                sendData(emp);

                                Scene scene;
                                scene = new Scene((Parent) loader.load(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 29);
                                scene.getStylesheets().add(Main.class.getResource("../../../AdminV.css").toExternalForm());
                                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                                primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                                primaryStage.setScene(scene);



                            } catch (IOException ex) {
                                System.out.println("Can't create parent!");
                                ex.printStackTrace();
                            }
                        }
                        });
                    }else{
                        errMsg.setVisible(true);
                    }*/
                }
            }
        });

        log.focusTraversableProperty().setValue(true);
        log.setOnAction(e -> {
            errMsg.setVisible(false);
            eobj = Employee.empLogin("Login", Integer.parseInt(user.getText()), pass.getText());
            if(eobj != null){
                Platform.runLater(new Runnable() { /**Adds message to the scene**/
                @Override
                public void run() {
                    try {

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("DashBoard.fxml"));
                        emp = new Person(eobj.getFirstName() + " " + eobj.getLastName(), eobj.getEmpID());
                        sendData(emp);
                        Scene scene;
                        scene = new Scene((Parent) loader.load(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 29);
                        scene.getStylesheets().add(Main.class.getResource("../AdminV.css").toExternalForm());
                        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                        primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                        primaryStage.setScene(scene);



                    } catch (IOException ex) {
                        System.out.println("Can't create parent!");
                        ex.printStackTrace();
                    }
                }
                });
            }else{
                errMsg.setVisible(true);
            }

            /*if(user.getText().equals("SuperUser") && pass.getText().equals("LetMeIn")) {
                Platform.runLater(new Runnable() { *//**Adds message to the scene**//*
                @Override
                public void run() {
                    try {

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("DashBoard.fxml"));
                        emp = new Person(user.getText());
                        sendData(emp);
                        Scene scene = new Scene((Parent) loader.load(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() - 29);
                        scene.getStylesheets().add(Main.class.getResource("../AdminV.css").toExternalForm());
                        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                        primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                        primaryStage.setScene(scene);
                        primaryStage.setResizable(true);
                    } catch (IOException ex) {
                        System.out.println("Can't create parent!");
                        ex.printStackTrace();
                    }
                }
                });

            }else{
                errMsg.setVisible(true);
            }*/

        });
    }
}
