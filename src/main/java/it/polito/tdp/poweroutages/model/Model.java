package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private Map<Integer, Nerc> nercs;
	private Simulator sim;
	
	public Model() {
		this.dao = new PowerOutagesDAO();
		this.nercs = new HashMap<>();
		this.sim = new Simulator();
	}
	
	public void generateGraph() {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.dao.loadAllNercs(this.nercs).values());
		
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(this.nercs);
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.graph, a.getN1(), a.getN2(), a.getPeso());
		}
	}
	
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<Nerc> getVertici() {
		List<Nerc> vertici = new ArrayList<>(this.nercs.values());
		return vertici;
	}
	
	public List<NercNumero> getVicini(Nerc nerc) {
		List<Nerc> vicini = Graphs.neighborListOf(this.graph, nerc);
		List<NercNumero> nercPeso = new ArrayList<>();
		
		for(Nerc n : vicini) {
			NercNumero nn = new NercNumero(n, (int) this.graph.getEdgeWeight(this.graph.getEdge(nerc, n)));
			nercPeso.add(nn);
		}
		Collections.sort(nercPeso);
		return nercPeso;
	}
	
	public void simula(Integer k) {
		List<PowerOutage> po = this.dao.getAllPowerOutages();
		this.sim.init(k, po, this);
		this.sim.run();
	}
	
	public List<PowerOutage> getPowerOutages() {
		return this.dao.getAllPowerOutages();
	}
	
	public Map<Integer, Nerc> getNerc() {
		return this.nercs;
	}
	
	public Integer getCatastrofi() {
		return this.sim.getCatastrofi();
	}
	
	public List<NercNumero> getBonus() {
		return this.sim.getBonus();
	}

}
