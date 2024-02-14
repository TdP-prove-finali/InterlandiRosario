package it.polito.s284166.Tesi;

import it.polito.s284166.Tesi.model.*;



import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;



public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<TableModel, Integer> ID;

    @FXML
    private TableColumn<TableModel, String> Make;

    @FXML
    private TableColumn<TableModel, String> Model;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnRicorsione1;

    @FXML
    private ComboBox<Veicolo> cmbAuto1;

    @FXML
    private ComboBox<String> cmbColor;

    @FXML
    private ComboBox<String> cmbFuel;

    @FXML
    private TableColumn<TableModel, Integer> engine;

    @FXML
    private TableColumn<TableModel, Integer> kilometer;
    
    @FXML
    private RadioButton Migliore;
    
    @FXML
    private RadioButton Numero;

    @FXML
    private RadioButton Valore;
    
    @FXML
    private ComboBox<String> cmbMarca;
    
    @FXML
    private ToggleGroup ricerca;

    @FXML
    private ToggleGroup owner;
    
    @FXML
    private RadioButton firstOwner;
    
    @FXML
    private RadioButton secondOwner;
    
    @FXML
    private Label txtAvvisi2;

    @FXML
    private TableColumn<TableModel, Integer> price;

    @FXML
    private TableView<TableModel> table;

    @FXML
    private TextField txtBudget1;

    @FXML
    private TextField txtKilometer;
    
    @FXML
    private Label txtAvviso;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextArea txtResult1;

    @FXML
    private TableColumn<TableModel, Integer> year;
    
    private Integer prezzoDiRiferimento;

    @FXML
    void doRicerca(ActionEvent event) {
	
	// Controlli, conversione dei dati passati come input e creazione variabili da passare al modello per la ricerca
	// Se il valore non si esce dal metodo in quanto all'utente è permesso di selezionare i vincoli a sua scelta.
    	Integer kilometer;
    	try {
    		if (this.txtKilometer.getText()!="") {
    			kilometer = Integer.parseInt(this.txtKilometer.getText());
    		}else {
    			kilometer = null;
    		}	
    	}catch(Exception e) {
    		this.txtAvviso.setText("Errore nel format dei chilometri");
    		return;
    	}
	    
	    
    	Integer price;
    	try {
    		if (this.txtPrice.getText()!="") {
    			price = Integer.parseInt(this.txtPrice.getText());
    		}else {
    			price = null;
    		}		
    	}catch(Exception e) {
    		this.txtAvviso.setText("Errore nel format del prezzo");
    		return;
    	}
	    
    	this.owner = new ToggleGroup();
    	String fuel = this.cmbFuel.getValue();
    	String color = this.cmbColor.getValue();
    	String marca = this.cmbMarca.getValue();
    	String ownerType = null;
    	if (this.owner.getSelectedToggle() != null){
	    	if(this.owner.getSelectedToggle().equals(firstOwner)) {
	    		ownerType = "First";
	    	}
	    	if (this.owner.getSelectedToggle().equals(secondOwner)) {
	    		ownerType = "Second";
	    	}
    	}

    	this.model.doRicerca(kilometer,price, fuel, color, ownerType, marca);
    	if (this.model.getFilteredAuto().size()!= 0) {
		
		// Resetto label di avviso in caso di ricerca avvenuta con successo
    		if (this.txtAvviso.getText().compareTo("Avviso")!=0) {
    			txtAvviso.setText("Avviso");
    		}
		
    		List<TableModel> entries=this.model.getTableEntries();
    		ObservableList<TableModel> list = FXCollections.observableArrayList(entries);
    		table.setItems(list);
		
    		// In questo modo il prezzo di riferimento per il metodo doRicorsione() è quello dell'ultima ricerca fatta
    		prezzoDiRiferimento = price;
    	
    		List<Veicolo> auto = this.model.getFilteredAuto();
    		this.cmbAuto1.getItems().clear();
    		this.cmbAuto1.getItems().addAll(auto);
    		this.btnRicorsione1.setDisable(false);
    	}
    	
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	Integer budget;
    	try {
    		if (this.txtBudget1.getText()!="") {
    			budget = Integer.parseInt(this.txtBudget1.getText());

			// Se il budget inserito è minore del prezzo inserito per la ricerca non viene avviata la ricorisone, è stato scelto di utilizzare
			// questo limite inferiore per essere sicuri che almeno un elemento sia presente nella soluzione dell'algoritmo ricorsivo. Ovviamente 
			// questo avviene solamente se l'utente ha indicato il prezzo durante la ricerca.
    			if (prezzoDiRiferimento!= null) {
	    			if (budget<Integer.parseInt(this.txtPrice.getText())) {
	    				txtAvvisi2.setText("Il budget deve essere maggiore del prezzo della prima sezione");
	    				txtResult1.clear();
	    				return;
	    			}
    			}
    		}else {
    			txtAvvisi2.setText("Inserisci un budget");
    	        return;
    		}	
    	}catch(Exception e) {
    		this.txtAvvisi2.setText("Errore nel format del budget");
    		return;
    	}

	// Inizializzione stringa che rappresenta il tipo di parametro da ottimizzare nella ricerca
    	String Ricerca = null;
    	if (this.ricerca.getSelectedToggle() != null){
	    	if(this.ricerca.getSelectedToggle().equals(Migliore)) {
	    		Ricerca = "Migliore";
	    	}
	    	if (this.ricerca.getSelectedToggle().equals(Numero)) {
	    		Ricerca = "Numero";
	    	}
	    	if (this.ricerca.getSelectedToggle().equals(Valore)) {
	    		Ricerca = "Valore";
	    	}
    	}
    	Veicolo root = this.cmbAuto1.getValue();
    	if (root == null) {
    		txtAvvisi2.setText("Seleziona un auto da inserire");
    	}
    	List<Veicolo> result = this.model.getBestSolution(root, budget, Ricerca);

	// Arrivato a questo punto devo stampare solamente l'output ed eliminare i messaggi di errore resettando la label    
    	if (txtAvvisi2.getText().compareTo("Sezione avvisi")!=0) {
    		txtAvvisi2.setText("Sezione avvisi");
    	}
    	txtResult1.clear();
    	txtResult1.appendText("Prezzo dell'insieme delle auto: "+ this.model.getValoreInsieme());
    	for (Veicolo v: result) {
    		if(txtResult1.getText()!=null)
    			txtResult1.appendText("\n");
    		txtResult1.appendText(v.toString());
    	}
  		

    }
    
    

    @FXML
    void initialize() {
    	ID.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("Id"));
    	Make.setCellValueFactory(new PropertyValueFactory<TableModel,String>("Make"));
    	Model.setCellValueFactory(new PropertyValueFactory<TableModel,String>("Model"));
    	year.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("Year"));
    	engine.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("Engine"));
    	kilometer.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("Kilometer"));
    	price.setCellValueFactory(new PropertyValueFactory<TableModel,Integer>("Price"));
        this.btnRicorsione1.setDisable(true);
    	assert ID != null : "fx:id=\"ID\" was not injected: check your FXML file 'Scene.fxml'.";
        assert Make != null : "fx:id=\"Make\" was not injected: check your FXML file 'Scene.fxml'.";
        assert Migliore != null : "fx:id=\"Miglliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert Numero != null : "fx:id=\"Numero\" was not injected: check your FXML file 'Scene.fxml'.";
        assert Valore != null : "fx:id=\"Valore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert Model != null : "fx:id=\"Model\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMarca != null : "fx:id=\"cmbMarca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicorsione1 != null : "fx:id=\"btnRicorsione1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAuto1 != null : "fx:id=\"cmbAuto1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbColor != null : "fx:id=\"cmbColor\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbFuel != null : "fx:id=\"cmbFuel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert engine != null : "fx:id=\"engine\" was not injected: check your FXML file 'Scene.fxml'.";
        assert kilometer != null : "fx:id=\"kilometer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert owner != null : "fx:id=\"owner\" was not injected: check your FXML file 'Scene.fxml'.";
        assert firstOwner != null : "fx:id=\"firstOwner\" was not injected: check your FXML file 'Scene.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'Scene.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAvvisi2 != null : "fx:id=\"txtAvvisi2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAvviso != null : "fx:id=\"txtAvviso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtBudget1 != null : "fx:id=\"txtBudget1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtKilometer != null : "fx:id=\"txtKilometer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'Scene.fxml'.";
        assert secondOwner != null : "fx:id=\"secondOwner\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult1 != null : "fx:id=\"txtResult1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert year != null : "fx:id=\"year\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<String> color = this.model.getColor();
    	List<String> fuel = this.model.getFuel();
    	List<String> marca = this.model.getMarca();
    	this.cmbColor.getItems().clear();
    	this.cmbColor.getItems().addAll(color);
    	this.cmbFuel.getItems().clear();
    	this.cmbFuel.getItems().addAll(fuel);
    	this.cmbMarca.getItems().clear();
    	this.cmbMarca.getItems().addAll(marca);
    }

}

 
