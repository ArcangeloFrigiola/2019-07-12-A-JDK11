package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<Food, DefaultWeightedEdge> grafo;
	private List<Food> listaCibi;
	
	public Model() {
		
	}
	
	public List<Food> getListaBoxCibi(int num) {
		
		this.dao = new FoodDao();
		this.listaCibi = new ArrayList<>(this.dao.listaCibi(num));
		this.generateGraph();
		
		return this.listaCibi;
	}
	
	public void generateGraph() {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.listaCibi);
		
		for(Food food1: this.listaCibi) {
			for(Food food2: this.listaCibi) {
				
				if(!food1.equals(food2) && food1.getFood_code()<food2.getFood_code()) {
					Double peso = this.dao.getAdiacenza(food1.getFood_code(), food2.getFood_code());
					if(peso>0) {
						Graphs.addEdge(this.grafo, food1, food2, peso);
					}
				}
				
			}
		}
		
		
	}
	
	public String getElenco5Cibi(Food cibo) {
		
		List<Food> cibiVicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, cibo));
		List<FoodAndCalories> listaTemp = new ArrayList<>();
		
		for(Food f: cibiVicini) {
			
			listaTemp.add(new FoodAndCalories(f, this.grafo.getEdgeWeight(this.grafo.getEdge(cibo, f))));
		}
		
		Collections.sort(listaTemp, new ComparatoreCalorie());
		String result = "";
		
		if(listaTemp.size()>0) {
			
			if(listaTemp.size()>4) {
				result = "Top 5 cibi adiacenti a "+cibo.getDisplay_name()+":\n";
			}else{
				result = "Top "+(listaTemp.size()+1)+" cibi adiacenti a "+cibo.getDisplay_name()+":\n";
			}
			
			for(int i=0; i<listaTemp.size() && i<5; i++) {
				result+=listaTemp.get(i).getCibo().getDisplay_name()+", calorie: "+listaTemp.get(i).getCalorieCongiunte()+"\n";
			}
		}else {
			result+="Nessun cibo adiacente a quello selezionato!\n";
		}
		
		return result;
		
	}
	
	public int getNvertex() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNedges() {
		return this.grafo.edgeSet().size();
	}
}
