/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	String input = this.txtSoglia.getText();
    	if(input=="") {
    		this.txtResult.appendText("Inserire un valore soglia.");
    		return;
    	}
    	try {
    		double s = Double.parseDouble(input);
    		if(s>=model.getMin() && s<=model.getMax()) {
    			this.txtResult.appendText("Soglia: "+s+" --> Maggiori "+model.getSup(s)+", minori "+model.getInf(s)+"\n");
    		}
    		else {
    			this.txtResult.setText("Inserire un valore tra il minimo e il massimo.");
    			return;
    		}
    	} catch (NumberFormatException e) {
    		this.txtResult.setText("Il valore immesso non è valido.");
    		return;
    	}
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	String input = this.txtSoglia.getText();
    	if(input=="") {
    		this.txtResult.appendText("Inserire un valore soglia.");
    		return;
    	}
    	try {
    		double s = Double.parseDouble(input);
    		if(s>=model.getMin() && s<=model.getMax()) {
    			List<Integer> l = model.calcolaCammino(s);
				this.txtResult.appendText("Cammino creato.\n");
    			for(Integer i : l) {
    				this.txtResult.appendText(i.toString()+"\n");
    			}
    		}
    		else {
    			this.txtResult.setText("Inserire un valore tra il minimo e il massimo.");
    			return;
    		}
    	} catch (NumberFormatException e) {
    		this.txtResult.setText("Il valore immesso non è valido.");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		model.creaGrafo();
		this.txtResult.setText("Grafo creato: "+model.getVerticiSize()+" vertici, "+model.getArchiSize()+" archi\n");
		this.txtResult.appendText("Peso minimo = "+model.getMin()+", peso massimo = "+model.getMax()+"\n");
	}
}
