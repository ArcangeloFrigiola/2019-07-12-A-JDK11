/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodAndCalories;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int porzioni;
    	try {
    		porzioni = Integer.parseInt(this.txtPorzioni.getText());
    		if(porzioni>0) {
    			
    			this.boxFood.getItems().clear();
    			this.boxFood.getItems().addAll(this.model.getListaBoxCibi(porzioni));
    			this.txtResult.appendText("Grafo creato!\nNumero vertici del grafo: "+this.model.getNvertex()+
    					"\nNumero archi nel grafo: "+this.model.getNedges());
    		}else {
    			txtResult.appendText("Inserire solo numeri interi maggiori di zero!");
    		}
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire solo numeri interi maggiori di zero!");
    		return;
    	}
  
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	Food cibo = this.boxFood.getValue();
    	
    	
    	if(cibo!=null) {
    		
    		String result = "";
    		List<FoodAndCalories> listaTemp = new ArrayList<>(this.model.getElenco5Cibi(cibo));
    		if(listaTemp.size()>0) {
    			
    			if(listaTemp.size()>4) {
    				result = "Top 5 cibi adiacenti a "+cibo.getDisplay_name()+":\n";
    			}else{
    				result = "Top "+(listaTemp.size()+1)+" cibi adiacenti a "+cibo.getDisplay_name()+":\n";
    			}
    			
    			for(int i=0; i<listaTemp.size() && i<5; i++) {
    				result+=listaTemp.get(i).getCibo().getDisplay_name()+", calorie: "+listaTemp.get(i).getCalorieCongiunte()+"\n";
    			}
    			
    			txtResult.appendText(result);
    		}else {
    			result+="Nessun cibo adiacente a quello selezionato!\n";
    		}
    		
    	}else {
    		txtResult.appendText("Inserire un cibo dal menù a tendina!");
    		return;
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	
    	Food cibo = this.boxFood.getValue();
    	int k = 0;
    	
    	if(cibo==null) {
    		txtResult.appendText("Inserire un cibo dal menù a tendina!");
    		return;
    	}
    	
    	try {
    		
    		k = Integer.parseInt(this.txtK.getText());
    		if(k<0 || k>10) {
    			txtResult.appendText("Inserire un numero K maggiore di zero e minore di 10!");
        		return;
    		}
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero K!");
    		return;
    	}
    	
    	this.txtResult.appendText(this.model.simula(cibo, k));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
