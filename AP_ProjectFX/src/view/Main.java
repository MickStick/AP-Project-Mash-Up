package view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("Login.fxml"));
        //Scene scene = new Scene(root, 790, 590);
        Scene scene2 = new Scene(root2, 320, 500);
        primaryStage.setTitle("Accounts Receiveble Distance Service");
        primaryStage.setResizable(false);
        //scene.getStylesheets().add(view.Main.class.getResource("../AdminV.css").toExternalForm());
        scene2.getStylesheets().add(Main.class.getResource("../AdminV.css").toExternalForm());
        /*primaryStage.setX(Screen.getPrimary().getVisualBounds().getMinX());
        primaryStage.setY(Screen.getPrimary().getVisualBounds().getMinY());
        primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setFullScreen(true);*/
        primaryStage.setScene(scene2);
        primaryStage.show();
    }


    public static void main(String [] args) {

        try{
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
