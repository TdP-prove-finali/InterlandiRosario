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
	
	public void doRicerca(Integer kilometer,Integer price, String fuel, String color,String ownerType,String Marca ) {

		this.autoFiltrate = this.dao.applyFilter(kilometer, price, fuel, color,ownerType, Marca);
		
	}
	
	public List<Veicolo> getFilteredAuto(){
		List<Veicolo> result = new ArrayList<Veicolo>(this.autoFiltrate) ;
		Collections.sort(result);
		return result;
	}
	
	public List<TableModel> getTableEntries(){
		List<Veicolo> autoFiltrate = new ArrayList<>(this.autoFiltrate);
		List<TableModel> result = new ArrayList<>();
		for (Veicolo v : autoFiltrate ) {
			TableModel tableEntry = new TableModel(v.getId(), v.getMake(), v.getModel(), v.getEngine(), v.getYear(), v.getKilometer(), v.getPrice());
			result.add(tableEntry);
		}
		return result;
	}

	public List<Veicolo> getBestSolution(Veicolo root, Integer budget, String type) {
		this.bestPath = new ArrayList<>();
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
		
		if (this.actualWeight(parziale)> budget) {
			return;
		}
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
		List<Veicolo> successori = new ArrayList<>(autoFiltrate);
		
		for (Veicolo successor: successori ) {
			if (!parziale.contains(successor)) {
				parziale.add(successor);
				ricorsione(parziale,budget,type);
				parziale.remove(successor);
			}
				
		}
		
	}
	
	public Integer getValoreInsieme() {
		return this.actualWeight(bestPath);
	}


	private Double getScoreMigliore(List<Veicolo> parziale) {
		Double score = 0.0;
		for (Veicolo v: parziale) {
			score += v.getKilometer();
		}
		score = score/parziale.size();
		return score;
	}

	private Integer actualWeight(List<Veicolo> parziale) {
		int result = 0;
		for (Veicolo v : parziale) {
			result += v.getPrice();
		}		
		return result;
	}
	
}
