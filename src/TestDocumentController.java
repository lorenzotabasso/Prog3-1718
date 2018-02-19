import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TestDocumentController implements Initializable {

    // Java annotations per inizializzare le variabili in automatico – si basano sugli
    // ID dati alle componenti grafiche oppure solo sul loro tipo, se le componenti
    // sono uniche nella GUI, come in questo caso

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // qui eventuale codice di inizializzazione del controller
    }

}
