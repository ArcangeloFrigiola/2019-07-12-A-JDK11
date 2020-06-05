package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		
		INIZIO_PREPARAZIONE,
		FINE_PREPARAZIONE
	}
	
	private EventType tipoEvento;
	private Double tempoPreparazione;
	private Stazione stazione;
	private Food ciboSource;
	
	/**
	 * @param tipoEvento
	 * @param tempoPreparazione
	 * @param stazione
	 */
	public Event(EventType tipoEvento, Double tempoPreparazione, Stazione stazione, Food ciboSource) {
		super();
		this.tipoEvento = tipoEvento;
		this.tempoPreparazione = tempoPreparazione;
		this.stazione = stazione;
		this.ciboSource = ciboSource;
	}

	public EventType getTipoEvento() {
		return tipoEvento;
	}

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}

	public Stazione getStazione() {
		return stazione;
	}
	
	public Food getCiboSource() {
		return this.ciboSource;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return (this.tempoPreparazione.compareTo(o.getTempoPreparazione()));
	}
	
	
	
}
