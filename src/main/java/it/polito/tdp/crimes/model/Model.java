package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	SimpleWeightedGraph<String, DefaultWeightedEdge> graph;
	EventsDao db;
	List<Coppia> coppie;
	int pesoMassimo;
	List<String> bestPath;
	int pesoMinimo;
	int dimensioni;
	
	public Model() {
		this.db = new EventsDao();
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.pesoMassimo = 0;
	}
	
	public List<String> getAllCategories(){
		return this.db.listAllCategories();
	}
	
	public List<Integer> getAllYeart(){
		return this.db.listAllYears();
	}
	
	public void setGrafo(String categoria, int anno) {
		this.pesoMassimo = 0;
		this.coppie = new ArrayList<>(this.db.getCoppie(categoria, anno));
		for (Coppia c : this.coppie) {
			if (!this.graph.containsVertex(c.getTipologia1()))
				this.graph.addVertex(c.getTipologia1());
			if (!this.graph.containsVertex(c.getTipologia2()))
				this.graph.addVertex(c.getTipologia2());
			if (!this.graph.containsEdge(c.getTipologia1(), c.getTipologia2()))
				Graphs.addEdgeWithVertices(this.graph, c.getTipologia1(), c.getTipologia2(), c.getPeso());
			if (c.getPeso()>this.pesoMassimo)
				this.pesoMassimo = c.getPeso();
		}
	/*	System.out.println(String.format("Grafo creato con %d archi e %d vertici", this.graph.edgeSet().size(), 		this.graph.vertexSet().size())); */
	}
	
	public List<Coppia> getAllCoppie(){
		List<Coppia> daRitornare = new ArrayList<Coppia>();
		for (DefaultWeightedEdge e : this.graph.edgeSet()) {
			Coppia cTemp = new Coppia(graph.getEdgeSource(e), graph.getEdgeTarget(e), (int) graph.getEdgeWeight(e));
			daRitornare.add(cTemp);
		}
		return daRitornare;
	}
	
	public List<Coppia> getCoppiePesoMasismo(){
		List<Coppia> daRitornare = new ArrayList<Coppia>();
		for (DefaultWeightedEdge e : this.graph.edgeSet()) {
			if(graph.getEdgeWeight(e)== this.pesoMassimo) {
				Coppia cTemp = new Coppia(graph.getEdgeSource(e), graph.getEdgeTarget(e), (int) graph.getEdgeWeight(e));
				daRitornare.add(cTemp);
				}
		}
		return daRitornare;
	}
	
	
	
	public List<String> calcolaPercorso (Coppia c){
		bestPath = new ArrayList<>();
		pesoMinimo = 0;
		String partenza = c.getTipologia1();
		String destinazione = c.getTipologia2();
		List<String> parziale = new ArrayList<String>();
		parziale.add(partenza);
		dimensioni = this.graph.vertexSet().size();
		
		this.ricorsione(parziale, 1, destinazione, 0);
		
		return bestPath;
	}
	

	private void ricorsione(List<String> parziale, int i, String destinazione, int peso) {
		
		// condizioni terminali
		// verifico che tocchi tutti i vertici
		if (parziale.size() == dimensioni) {
			//verifico che termini con la destinazione
			if (parziale.get(dimensioni-1).compareTo(destinazione)==0) {
				//verifico che sia la migliore
				
				if (pesoMinimo == 0) {
					this.bestPath = new ArrayList<>(parziale);
					this.pesoMinimo = peso;
					return;
				}else if (peso<pesoMinimo) {
					this.bestPath = new ArrayList<>(parziale);
					this.pesoMinimo = peso;
					return;
				}
				return;
			}
			return;
		}
		
		//procedo con la ricorsione
		for (String s : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			
			// caso particolare: aggiungo la destinazione
			if (s.compareTo(destinazione)==0) {
				if (i!=dimensioni-1)
					return;
			}
			
			parziale.add(s);
			int nuovoPeso = (int) (peso + graph.getEdgeWeight(graph.getEdge(s, parziale.get(parziale.size()-2))));
			this.ricorsione(parziale, i ++, destinazione, nuovoPeso);
			parziale.remove(parziale.size()-1);
		}
	}

}
