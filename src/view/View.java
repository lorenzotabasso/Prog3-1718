package view;

// VIEW

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class View extends BorderPane {

    public View(){
        super();

    }

    public void setBorders(){
        setTopBorder();

    }

    private void setTopBorder(){
        // Inizializzo la Navbar e i rispettivi bottoni dentro di essa.
        HBox topNavBar = new HBox();
        Button inviaRicevi = new Button("Invia / Ricevi");
        Button elimina = new Button("Elimina");
        Button indesiderata = new Button("Indesiderata");
        Button rispondi = new Button("Rispondi");
        Button inoltra = new Button("Inoltra");

        // Impostazioni della Navbar
        topNavBar.getChildren().addAll(inviaRicevi, elimina, indesiderata, rispondi, inoltra); // Inserisco i bottoni dentro la Navbar
        topNavBar.setAlignment(Pos.TOP_LEFT); // Imposto l'allineamento della Navbar
        setTop(topNavBar); // Imposto la Navbar al top del BorderPane
    }

    private void setWestBorder(){
        VBox westNavBar = new VBox();
        ListView cartelle = new ListView();


        // Impostazioni della Navbar
        topNavBar.getChildren().addAll(inviaRicevi, elimina, indesiderata, rispondi, inoltra); // Inserisco i bottoni dentro la Navbar
        topNavBar.setAlignment(Pos.TOP_LEFT); // Imposto l'allineamento della Navbar
        setTop(topNavBar); // Imposto la Navbar al top del BorderPane
    }
}
