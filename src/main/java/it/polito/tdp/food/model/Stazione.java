package it.polito.tdp.food.model;

public class Stazione {
	
	private Food ciboInPreparazione;
	private boolean stazioneLibera;
	/**
	 * @param ciboInPreparazione
	 * @param stazioneLibera
	 */
	public Stazione(boolean stazioneLibera, Food ciboInPreparazione) {
		super();
		this.ciboInPreparazione = ciboInPreparazione;
		this.stazioneLibera = stazioneLibera;
	}
	public Food getCiboInPreparazione() {
		return ciboInPreparazione;
	}
	public void setCiboInPreparazione(Food ciboInPreparazione) {
		this.ciboInPreparazione = ciboInPreparazione;
	}
	public boolean isStazioneLibera() {
		return stazioneLibera;
	}
	public void setStazioneLibera(boolean stazioneLibera) {
		this.stazioneLibera = stazioneLibera;
	}
	
	
	
}
