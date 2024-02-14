package it.polito.s284166.Tesi.model;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;


import it.polito.s284166.Tesi.db.macchineDAO;


public class Model {
	
	private macchineDAO dao;
	private List<Veicolo> autoFiltrate;
	private List<Veicolo> bestPath;
	private Double bestScore;
	private Veicolo root;
	
	public Model() {
		this.dao = new macchineDAO();
	}
	
	// Metodi per popolare le combobox del controller
	public List<String> getColor(){
		List<String> result = this.dao.getColor();
		Collections.sort(result);
		return result;
	}
	
	public List<String> getFuel(){
		List<String> result = this.dao.getFuelType();
		Collections.sort(result);
		return result;
	}
	public List<String> getMarca(){
		List<String> result = this.dao.getMarcaType();
		Collections.sort(result);
		return result;
	}
	
	// Nei seguenti due metodi sono stati applicati i filtri imposti dall'utente e restituito il risultato del filtro. E' stato deciso
	// di non restituire direttamente la lista delle auto filtrate in doRicerca() ma con un secondo metodo per poter richiedere il risultato
	// in un secondo momento senza rifare la ricerca, nel caso qualcuno volesse implementare altri algoritmi su tale lista.
	public doRicerca(Integer kilometer,Integer price, String fuel, String color,String ownerType,String Marca ) {

		this.autoFiltrate = this.dao.applyFilter(kilometer, price, fuel, color,ownerType, Marca);
		
	}
	
	public List<Veicolo> getFilteredAuto(){
		List<Veicolo> result = new ArrayList<Veicolo>(this.autoFiltrate) ;
		Collections.sort(result);
		return result;
	}
	
	// Il solo scopo di questo metodo è quello di creare oggetti per popolare la tabella di output, prendendo i dati dalla lista delle auto filtrate
	public List<TableModel> getTableEntries(){
		List<Veicolo> autoFiltrate = new ArrayList<>(this.autoFiltrate);
		List<TableModel> result = new ArrayList<>();
		for (Veicolo v : autoFiltrate ) {
			TableModel tableEntry = new TableModel(v.getId(), v.getMake(), v.getModel(), v.getEngine(), v.getYear(), v.getKilometer(), v.getPrice());
			result.add(tableEntry);
		}
		return result;
	}
	// Metodo con cui si interfaccia il controller per avviare l'algoritmo ricorsivo
	public List<Veicolo> getBestSolution(Veicolo root, Integer budget, String type) {
		this.bestPath = new ArrayList<>();

		// In base al parametro scelto il punteggio da ottimizzare dalla ricorsione, il punteggio viene inizializzato
		// con zero per i parametri numero e valore, e con un valore alto casuale per migliore (in quanto qui bisogna cercare il punteggio minimo
		// e non il massimo)
		if (type.compareTo("Migliore")==0) {
			this.bestScore = 1000000000.0;
		}else if (type.compareTo("Numero")==0) {
			this.bestScore = 0.0;
		}else if (type.compareTo("Valore")==0) {
			this.bestScore = 0.0;
		}
		this.root = root;
		List<Veicolo> parziale = new ArrayList<>();
		parziale.add(this.root);
		ricorsione(parziale,budget,type);
		return this.bestPath;
	}

	private void ricorsione(List<Veicolo> parziale, Integer budget,String type) {
		// Condizione di uscita
		if (this.actualWeight(parziale)> budget) {
			return;
		}

		// Condizioni per aggiornare la soluzione migliore
		if (type.compareTo("Migliore")==0) {
			if (getScoreMigliore(parziale)< this.bestScore) {
				this.bestScore = getScoreMigliore(parziale);
				this.bestPath = new ArrayList<>(parziale);
			}
		}else if (type.compareTo("Numero")==0) {
			if (parziale.size()>this.bestScore) {
				this.bestScore = Double.valueOf(parziale.size());
				this.bestPath = new ArrayList<>(parziale);
			}
		}else if (type.compareTo("Valore")==0) {
			if (actualWeight(parziale) > this.bestScore) {
				this.bestScore = Double.valueOf(this.actualWeight(parziale));
				this.bestPath = new ArrayList<>(parziale);
			}
		}

		// In questa parte di codice si prova a trovare soluzioni migliori di quella corrente andando ad aggiungere e rimuovere (backtracking)
		// i veicoli che non sono contenuti nella lista parziale
		List<Veicolo> successori = new ArrayList<>(autoFiltrate);
		
		for (Veicolo successor: successori ) {
			if (!parziale.contains(successor)) {
				parziale.add(successor);
				ricorsione(parziale,budget,type);
				parziale.remove(successor);
			}
				
		}
		
	}
	

	// Metodo che restituisce la media dei chilometri percorsi dei veicoli presenti nella lista passata come input
	private Double getScoreMigliore(List<Veicolo> parziale) {
		Double score = 0.0;
		for (Veicolo v: parziale) {
			score += v.getKilometer();
		}
		score = score/parziale.size();
		return score;
	}

	// Metodo che restituisce il prezzo totale dei veicoli presenti nella lista passata come input
	private Integer actualWeight(List<Veicolo> parziale) {
		int result = 0;
		for (Veicolo v : parziale) {
			result += v.getPrice();
		}		
		return result;
	}
	
	// Metodo per trovare il prezzo totale della migliore lista trovata dall'algoritmo ricorsivo e resituirlo al controller
	// per essere stampato nell'output.
	public Integer getValoreInsieme() {
		return this.actualWeight(bestPath);
	}
	
}
