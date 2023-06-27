package it.polito.tdp.genes.model;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private List<Integer> vertici;
	private List<Coppia> archi;
	
	private List<Integer> migliore;
	private double bestPeso;
	
	public Model() {
		dao=new GenesDao();
	}
	
	public List<Integer> calcolaCammino(double s){
		this.migliore=new ArrayList<>();
		this.bestPeso=0;
		List<Integer> parziale = new ArrayList<>();
		ricorsione(parziale, this.vertici,s);
		return migliore;
	}
	
	private void ricorsione(List<Integer> parziale, List<Integer> adiacenti, double s) {
		if(this.calcolaPeso(parziale)>bestPeso) {
			this.migliore=new ArrayList<>(parziale);
			bestPeso=this.calcolaPeso(parziale);
		}
		for(Integer i : adiacenti) {
			if(parziale.size()==0) {
				parziale.add(i);
				ricorsione(parziale,this.getAdiacenti(i),s);
				parziale.remove(i);
			}
			else if(parziale.size()>0 && !parziale.contains(i)) {
				Integer momentaneo = parziale.get(parziale.size()-1);
				for(Coppia c : this.archi) {
					if((c.getC2().equals(momentaneo) && c.getC1().equals(i))) {
						if(!parziale.contains(i)) {
							if(c.getN()>=s) {
								parziale.add(i);
								ricorsione(parziale,this.getAdiacenti(i),s);
								parziale.remove(i);
							}
						}
					}
				}
//				DefaultWeightedEdge e = this.grafo.getEdge(momentaneo,i);
//				if(!parziale.contains(i)) {
//					if(grafo.getEdgeWeight(e)>=s) {
//						parziale.add(i);
//						ricorsione(parziale,this.getAdiacenti(i),s);
//						parziale.remove(i);
//					}
//				}
			}
		}
	}

	private double calcolaPeso(List<Integer> parziale) {
		double peso = 0;
		if(parziale.size()<=1) {
			return peso;
		}
		for(int i=1;i<parziale.size();i++) {
			DefaultWeightedEdge d = this.grafo.getEdge(parziale.get(i), parziale.get(i-1));
			peso+=this.grafo.getEdgeWeight(d);
		}
		return peso;
	}

	public List<Integer> getAdiacenti(Integer i){
		List<Integer> successori = Graphs.successorListOf(this.grafo, i);
		return successori;
	}
	
	public void creaGrafo() {
		this.grafo=new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.vertici=new ArrayList<>(dao.getVertici());
		Graphs.addAllVertices(this.grafo, this.vertici);
//		System.out.println(grafo.vertexSet().size());
		
		this.archi=new ArrayList<>(dao.getArchi());
		for(Coppia c : this.archi) {
			Graphs.addEdgeWithVertices(this.grafo, c.getC1(), c.getC2(), c.getN());
		}
//		System.out.println(grafo.edgeSet().size());
	}
	
	public int getVerticiSize() {
		return grafo.vertexSet().size();
	}
	
	public int getArchiSize() {
		return grafo.edgeSet().size();
	}
	
	public double getMax() {
		double max = 0;
		for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
			double grado = grafo.getEdgeWeight(d);
			if(grado>max) {
				max=grado;
			}
		}
		return max;
	}
	
	public double getMin() {
		double min = Double.MAX_VALUE;
		for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
			double grado = grafo.getEdgeWeight(d);
			if(grado<min) {
				min=grado;
			}
		}
		return min;
	}
	
	public int getSup(double s) {
		int sup = 0;
		for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
			double grado = grafo.getEdgeWeight(d);
			if(grado>s) {
				sup++;
			}
		}
		return sup;
	}
	
	public int getInf(double s) {
		int inf = 0;
		for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
			double grado = grafo.getEdgeWeight(d);
			if(grado<s) {
				inf++;
			}
		}
		return inf;
	}
}