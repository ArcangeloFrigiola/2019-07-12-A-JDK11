package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulator {

	//MMODELLO DEL MONDO
	private List<Stazione> stazioni;
	private List<Food> cibi;
	private Graph<Food, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	private Model model;
	
	// PARAMETRI DELLA SIMULAZIONE
	private PriorityQueue<Event> queue;
	private int K;

	// PARAMETRI DA CALCOLARE
	private Double tempoDiPreparazione;
	private int cibiPreparati;
		
	public Double getTempoDiPreparazione() {
		return tempoDiPreparazione;
	}
	
	public int getCibiPreparati() {
		return this.cibiPreparati;
	}

	public void setK(int k) {
		this.K = k;
	}

	//CODA DELGI EVENTI
	public Simulator(Graph<Food, DefaultWeightedEdge> g, Model model) {
		this.grafo = g;
		this.model = model;
	}
	
	public void initialize(Food partenza) {
		
		this.cibi = new ArrayList<>(this.grafo.vertexSet());
		for(Food c: this.cibi) {
			c.setPreparato(StatoPreparazione.DA_PREPARARE);
		}
		this.stazioni = new ArrayList<>();
		
		for(int i=0; i<this.K; i++) {
			this.stazioni.add(new Stazione(true, null));
		}
		
		this.tempoDiPreparazione=0.0;
		this.cibiPreparati = 0;
		
		this.queue = new PriorityQueue<>();
		List<FoodAndCalories> vicini = model.getElenco5Cibi(partenza);
		
		for(int i=0; i<this.K && i<vicini.size(); i++) {
			
			this.stazioni.get(i).setStazioneLibera(false);
			this.stazioni.get(i).setCiboInPreparazione(vicini.get(i).getCibo());
			
			vicini.get(i).getCibo().setPreparato(StatoPreparazione.IN_CORSO);
			Event e = new Event(EventType.FINE_PREPARAZIONE, vicini.get(i).getCalorieCongiunte(), this.stazioni.get(i), vicini.get(i).getCibo());
			this.queue.add(e);
			
		}
	}
	
	public void run() {
		
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}

	}

	private void processEvent(Event e) {

		switch (e.getTipoEvento()) {

		case INIZIO_PREPARAZIONE:
			
			List<FoodAndCalories> vicini = new ArrayList<>(this.model.getElenco5Cibi(e.getCiboSource()));
			FoodAndCalories prossimo = null;
			
			for(FoodAndCalories f: vicini) {
				if(f.getCibo().getPreprazione()==StatoPreparazione.DA_PREPARARE) {
					prossimo = f;
					break;
				}
			}
			
			if(prossimo!=null) {
				prossimo.getCibo().setPreparato(StatoPreparazione.IN_CORSO);
				e.getStazione().setStazioneLibera(false);
				e.getStazione().setCiboInPreparazione(prossimo.getCibo());
				
				Event nuovo = new Event(EventType.FINE_PREPARAZIONE, e.getTempoPreparazione()+prossimo.getCalorieCongiunte(), e.getStazione(), prossimo.getCibo());
				this.queue.add(nuovo);
			}
			break;

		case FINE_PREPARAZIONE:
			
			this.cibiPreparati++;
			this.tempoDiPreparazione = e.getTempoPreparazione();
			
			e.getStazione().setStazioneLibera(true);
			e.getCiboSource().setPreparato(StatoPreparazione.PREPARATO);
			
			Event nuovo = new Event(EventType.INIZIO_PREPARAZIONE, e.getTempoPreparazione(), e.getStazione(), e.getCiboSource());
			this.queue.add(nuovo);
			
			break;

		}
	}

}
