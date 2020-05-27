/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Coppia;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Coppia> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if (this.boxCategoria.getValue()==null || this.boxAnno.getValue() == null) {
    		this.txtResult.setText("Devi selezionare dei valori a sinistra!");
    		return;
    	}
    	this.model.setGrafo(this.boxCategoria.getValue(), this.boxAnno.getValue());
    	List<Coppia> cTemp = new ArrayList<>(this.model.getCoppiePesoMasismo());
    	if (cTemp.size() == 0) {
    		this.txtResult.setText("Non esistono archi corrispondenti ai requisiti");
    	}else {
    	this.txtResult.setText("Archi che soddisfano i requisiti:\n");
    		for (Coppia c : cTemp)
    		this.txtResult.appendText(c.toString()+"\n");
    	}
    	this.boxArco.getItems().addAll(this.model.getAllCoppie());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	if (this.boxArco.getValue()== null){
    		this.txtResult.setText("Devi prima creare il grafo e selezionare un arco!");
    		return;
    	}else {
    		
    		Coppia arcoSelezionato = this.boxArco.getValue();
    		for (String s :this.model.calcolaPercorso(arcoSelezionato))
    			this.txtResult.appendText(s+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(this.model.getAllCategories());
    	this.boxAnno.getItems().addAll(this.model.getAllYeart());
    }
}
