package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {

	Model model;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxMese;
	
	

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		String sequenza; 
		Integer mese = boxMese.getValue();;
		if(mese==null) {
			txtResult.appendText("Nessun mese selezionato!\n");
		    return;
		   
		}
		
		else {
			
			sequenza = model.trovaSequenza(mese);
			txtResult.appendText(sequenza);
		}
		

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		String umiditaMedia;
		Integer mese = boxMese.getValue();;
		if(mese==null) {
			txtResult.appendText("Nessun mese selezionato!\n");
		    return;
		   
		}
		
		else {
			
			umiditaMedia = model.getUmiditaMedia(mese);
			txtResult.appendText(umiditaMedia);
		}
		
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
	}
	
	void setModel(Model m) {
		this.model = m;
		boxMese.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
		
	}

}
