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
		this.dao = new FoodDao();
	}
	
	public List<Food> getListaBoxCibi(int num) {
		
		this.listaCibi = new ArrayList<>(this.dao.listaCibi(num));
		this.generateGraph();
		
		return this.listaCibi;
	}
	
	public void generateGraph() {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.listaCibi);
	}
	
	public int getNvertex() {
		return this.grafo.vertexSet().size();
	}
	

}
