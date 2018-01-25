package MainView;

import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.stage.Screen;

import javax.security.auth.Subject;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable{

    @FXML
    public AnchorPane rootPane, secPane, searchPane, EnsearchPane, queryPane;
    @FXML
    public GridPane TopPane, updatePane, chatGrid;
    @FXML
    public VBox chatPane, viewQuery;
    @FXML
    public ScrollPane upScrollPane, vqScrollPane;
    /*@FXML
    public Pane queryPane;*/
    @FXML
    public Label idlbl, fnamelbl, lnamelbl, emaillbl, ballbl, tellbl, sender, subject, query, date, aFile;
    @FXML
    public TextField searchField, EnsearchField, idtxt, FNtxt, LNtxt, etxt, baltxt, teltxt;
    public int row = 0;
    @FXML
    public Button file, edit,settings,help, chatBtn, searchBtn, EnsearchBtn, upBtn, remBtn;

    public int in = 0;
    public Tooltip reply = new Tooltip("Alt + RMB to reply!");

    Socket con;
    ObjectInputStream is;
    ObjectOutputStream os;

    public void getConnected(){
            try{
                if(con.isConnected()){
                    closeConnection();
                    getConnected();
                }
            }catch (NullPointerException ex){
                try{
                    con = new Socket("127.0.0.5", 4000);
                    System.out.println("Admin Connected");
                }catch(IOException e){
                    System.out.println("Can't connect to Server!!!");
                    e.printStackTrace();
                }
            }

    }

    public void closeConnection(){
        try{
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void popUpdatePane(){ //this populates the section for updating student data
        idtxt.setText("1406944");
        idtxt.disableProperty().setValue(true);
        FNtxt.setText("Mikhail");
        FNtxt.disableProperty().setValue(false);
        LNtxt.setText("Shaw");
        LNtxt.disableProperty().setValue(false);
        etxt.setText("mikshaw01@gmail.com");
        etxt.disableProperty().setValue(false);
        baltxt.setText("US $5,000,000");
        baltxt.disableProperty().setValue(false);
        teltxt.setText("1 (876) 585-1482");
        teltxt.disableProperty().setValue(false);
        upBtn.disableProperty().setValue(false);
        remBtn.disableProperty().setValue(false);
    }

    public void InitUpdatePane(){ // this initializes the sectiion for updating student data
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

        upBtn = new Button("Update");
        upBtn.setId("upbtn");
        upBtn.disableProperty().setValue(true);
        remBtn = new Button("Remove");
        remBtn.setId("upbtn");
        remBtn.disableProperty().setValue(true);
        remBtn.layoutYProperty().set(90);

        updatePane.add(idlbl,0,0);
        updatePane.add(fnamelbl,0,1);
        updatePane.add(lnamelbl,0,2);
        updatePane.add(emaillbl,0,3);
        updatePane.add(ballbl,0,4);
        updatePane.add(tellbl,0,5);
        updatePane.add(idtxt,1,0);
        updatePane.add(FNtxt,1,1);
        updatePane.add(LNtxt,1,2);
        updatePane.add(etxt,1,3);
        updatePane.add(baltxt,1,4);
        updatePane.add(teltxt,1,5);
        updatePane.add(upBtn, 1, 6);
        updatePane.add(remBtn, 1 , 7);

    }

    boolean dont = false;
    public void InitEnquiryPane(){ // This creates and initializes: -the pane holding the query, -the query, -the query sender, the query date
        queryPane = new AnchorPane();
        queryPane.setPrefWidth(401.0);
        queryPane.setId("enqPane "  + String.valueOf(in));
        queryPane.accessibleRoleProperty().setValue(AccessibleRole.BUTTON);
        queryPane.cursorProperty().setValue(Cursor.HAND);
        queryPane.requestFocus();
        queryPane.getStyleClass().add("enqPane");


        sender = new Label("SENDER "  + String.valueOf(in));
        sender.setLayoutX(5);
        sender.setLayoutY(1);
        sender.setPrefWidth(305.0);
        sender.setStyle("-fx-font-weight: 800");

        subject = new Label("Subject");
        subject.setLayoutX(5);
        subject.setLayoutY(17);
        subject.setPrefWidth(160.0);
        subject.setStyle("-fx-font-weight: 800");

        query = new Label("This is a message containing the query from the student to the admin. This is a message containing the query from the student to the admin. This is a message containing the query from the student to the admin. This is a message containing the query from the student to the admin. This is a message containing the query from the student to the admin.  ");
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

        date = new Label("Date sent");
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
            dont = true;
            if(aFile.getLayoutX() == 5){
                aFile.setLayoutX(10);
                dont = false;
            }else{
                aFile.setLayoutX(5);
                dont = false;
            }
        });

        queryPane.getChildren().addAll(sender,subject,query,aFile,date);
        System.out.println(query.getText().length());
        queryPane.setOnMouseClicked(e->{ //Opens and closes a query by clicking said query
            if(dont){

            }else{
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
            }


        });

    }

    public void addToQueryView(){//this adds the query pane(created in InitEnquiryPane) to the view/receive query section
        in += 1;
        InitEnquiryPane();
        viewQuery.getChildren().add(0,queryPane);
    }

    public void popQueryView(){
        in += 1;
        InitEnquiryPane();

    }

/*////////////////////////////// Animation Section///////////////////////////////*/

/*////////////////////////////// Animation Section///////////////////////////////*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* TopPane.setId("topPane");
        file.setId("topBtn");
        edit.setId("topBtn");
        settings.setId("topBtn");
        help.setId("topBtn");*/

       //getConnected();
/*//////////////////////////////// Admin View Root Pane /////////////////////////////////////////*/
        rootPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() - 285);
/*//////////////////////////////// Admin View Root Pane /////////////////////////////////////////*/
        secPane.setId("secPane");
        searchPane.setId("srchP");
        EnsearchPane.setId("srchP");
        viewQuery.setId("vqPane");
        vqScrollPane.setId("vqscPane");
        //chatBtn.setId("chat-sect-btn");
/*///////////////////////////////// Update Section////////////////////////////////////*/
        searchBtn.setId("srchB");
        searchBtn.setCursor(Cursor.HAND);
        searchField.setId("srchF");
        searchField.setFocusTraversable(false);
        updatePane.setId("upPane");
        upScrollPane.setId("upscPane");
        upScrollPane.setPrefHeight(secPane.getPrefWidth() - 255);
        InitUpdatePane();
        searchBtn.setOnAction(m ->{
           popUpdatePane();
        });
        EnsearchBtn.setOnAction(m ->{
            addToQueryView();
        });
/*///////////////////////////////// Update Section////////////////////////////////////*/

/*///////////////////////////////// Enquiry Section///////////////////////////////////*/
        EnsearchBtn.setId("srchB");
        EnsearchBtn.setCursor(Cursor.HAND);
        EnsearchField.setId("srchF");
        addToQueryView();
        vqScrollPane.setPrefHeight(secPane.getPrefWidth() - 255);
/*///////////////////////////////// Enqury Section///////////////////////////////////*/
    }
}
