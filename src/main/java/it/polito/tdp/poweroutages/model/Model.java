package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private Graph<Nerc, DefaultWeightedEdge> graph;
	private PowerOutagesDAO dao;
	private Map<Integer, Nerc> idMap;
	
	private Simulator sim;
	
	
	public Model() {
		this.dao = new PowerOutagesDAO();
		this.sim = new Simulator();
	}
	
	public List<Nerc> buildGraph() {
		this.graph = new SimpleWeightedGraph<Nerc, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.idMap = new HashMap<Integer, Nerc>();
		
		List<Nerc> nercList = this.dao.loadAllNercs(idMap);
		Graphs.addAllVertices(this.graph, nercList);
		
		for(Relation r : this.dao.getRelations(idMap)) {
			Graphs.addEdge(this.graph, r.getNerc1(), r.getNerc2(), r.getPeso());
		}
		
		return nercList;
	}
	
	public List<Adiacente> getVicini(Nerc source) {
		List<Adiacente> result = new ArrayList<>();
		
		for(Nerc vicino : Graphs.neighborListOf(this.graph, source)) {
			result.add(new Adiacente(vicino, (int) this.graph.getEdgeWeight(this.graph.getEdge(source, vicino))));
		}
		result.sort(null);
		return result;
	}

	public Integer getNumVertex() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumEdges() {
		return this.graph.edgeSet().size();
	}
	
	public List<PowerOutage> getPowerOutage() {
		return this.dao.getPowerOutage(idMap);
	}
	
	public void simula(Integer K) {
		this.sim.init(K, this, idMap);
		this.sim.run();
	}
	
	public Integer getCatastrofi() {
		return this.sim.getCatastrofi();
	}
	
	public List<Nerc> getNerc() {
		return new ArrayList<Nerc>(this.idMap.values());
	}

}
