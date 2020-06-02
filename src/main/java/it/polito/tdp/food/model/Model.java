package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;


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
					if(peso!=null) {
						Graphs.addEdge(this.grafo, food1, food2, peso);
					}
				}
				
			}
		}
		
		
	}
	
	public void getElenco5Cibi(Food cibo) {
		
		List<Food> cinqueCibi = new ArrayList<>(Graphs.neighborListOf(this.grafo, cibo));
		
	}
	
	public int getNvertex() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNedges() {
		return this.grafo.edgeSet().size();
	}
}
