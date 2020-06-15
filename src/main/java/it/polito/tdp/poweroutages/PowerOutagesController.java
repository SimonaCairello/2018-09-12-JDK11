/**
 * Sample Skeleton for 'PowerOutages.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Adiacente;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxNerc"
    private ComboBox<Nerc> cmbBoxNerc; // Value injected by FXMLLoader

    @FXML // fx:id="btnVisualizzaVicini"
    private Button btnVisualizzaVicini; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	List<Nerc> nercList = this.model.buildGraph();
    	cmbBoxNerc.getItems().setAll(nercList);
    	
    	txtResult.appendText(String.format("Grafo creato!\n# Vertici: %d\n# Archi: %d\n", this.model.getNumVertex(), this.model.getNumEdges()));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer K = null;
    	try {
    		K = Integer.parseInt(txtK.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero");
    		return;
    	}
    	
    	if(K < 1) {
    		txtResult.appendText("Inserire un numero intero maggiore di 0");
    		return;
    	}
    	
    	this.model.simula(K);
    	txtResult.appendText(String.format("Si sono verifacate %d catastrofi\n\n", this.model.getCatastrofi()));
    	for(Nerc n : this.model.getNerc()) {
    		txtResult.appendText(n.toString()+" | "+n.getBonus()+"\n");
    	}
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	txtResult.clear();
    	Nerc nerc = this.cmbBoxNerc.getValue();
    	if(nerc == null) {
    		txtResult.appendText("Selezionare un nerc!");
    		return;
    	}
    	
    	List<Adiacente> vicini = this.model.getVicini(nerc);
    	txtResult.appendText(String.format("Il Nerc %s ha %d vicini: \n\n", nerc.toString(), vicini.size()));
    	for(Adiacente a : vicini) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;

	}
}
