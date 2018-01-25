package MainView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by micks on 3/30/2017.
 */
public class ControllerCommunicator {
    private final StringProperty empId = new SimpleStringProperty();

    public StringProperty textProperty() {
        return empId ;
    }

    public final String getText() {
        return textProperty().get();
    }

    public final void setText(String text) {
        textProperty().set(text);
    }
}
