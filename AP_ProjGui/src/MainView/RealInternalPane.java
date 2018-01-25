package MainView;

/**
 * Created by micks on 3/18/2017.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.animation.*;

import java.awt.*;

public class RealInternalPane extends Pane{
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
        close.setOnAction((ActionEvent e) ->{
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
        tBar.setOnMouseClicked(m ->{
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

    public void setScrollable(boolean Hstate, boolean Vstate){
        if(!Vstate) {
            body.setVmax(0);
        }

        if(!Hstate){
            body.setHmax(0);
        }
    }

    public void setBodyStyle(String style){
        body.setStyle(style);
    }

    /**The constructor**/
    RealInternalPane(){
        InitComps();
        InitInternalPane();
        InitTBar();
        InitTBarComps();
        InitBody();
        isMinimized = false;
    }

}
