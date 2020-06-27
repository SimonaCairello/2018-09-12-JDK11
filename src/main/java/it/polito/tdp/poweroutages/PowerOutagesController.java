/**
 * Sample Skeleton for 'PowerOutages.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercNumero;
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
    	this.model.generateGraph();
    	this.txtResult.appendText("Il grafo è stato generato con successo\n");
    	this.txtResult.appendText("Il numero di vertici del grafo è: "+this.model.getNumVertici()+"\n");
    	this.txtResult.appendText("Il numero di archi del grafo è: "+this.model.getNumArchi()+"\n");
    	
    	this.cmbBoxNerc.getItems().setAll(this.model.getVertici());
    	this.btnVisualizzaVicini.setDisable(false);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Integer mesi = 0;
    	
    	try {
    		mesi = Integer.parseInt(this.txtK.getText());
    	} catch (NumberFormatException e) {
    		this.txtResult.appendText("Inserire un numero intero!\n");
    		return;
    	} 
    	
    	if(mesi<=0) {
    		this.txtResult.appendText("Inserire un numero di mesi maggiore di zero!\n");
    		return;
    	}
    	
    	this.model.simula(mesi);
    	this.txtResult.appendText("Il numero di catastrofi è: "+this.model.getCatastrofi()+"\n");
    	this.txtResult.appendText("I NERC che hanno ricevuto un bonus sono: \n");
    	
    	List<NercNumero> bonus = new ArrayList<>(this.model.getBonus());
    	for(NercNumero n : bonus) {
    		txtResult.appendText(n.toString());
    	}
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	this.txtResult.clear();
    	Nerc nerc = this.cmbBoxNerc.getValue();
    	if(nerc==null) {
    		this.txtResult.appendText("Selezionare un NERC dalla tendina!\n");
    		return;
    	}
    	
    	List<NercNumero> vicini = this.model.getVicini(nerc);
    	for(NercNumero n : vicini) {
    		this.txtResult.appendText(n.toString()+"\n");
    	}
    	
    	this.btnSimula.setDisable(false);
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
		this.btnSimula.setDisable(true);
		this.btnVisualizzaVicini.setDisable(true);
	}
}
