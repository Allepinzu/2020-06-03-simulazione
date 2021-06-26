package it.polito.tdp.PremierLeague.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	

	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	PremierLeagueDAO dao;

	public Graph<Player, DefaultWeightedEdge> createGraph(double media) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao = new PremierLeagueDAO();
	idMap= new LinkedHashMap<>();
		
		for(Player b: dao.listAllPlayers(media)) {
			//Aggiungo i vertici
			if(!this.grafo.containsVertex(b)) {
				this.grafo.addVertex(b);
			}
		}
		
		for(Player p:dao.listAllPlayers(media)) {
			this.idMap.put(p.playerID, p);
			
		}
		
		for(Player p1:idMap.values()) {
			for(Player p2: idMap.values()) {
				if(!p1.equals(p2)&&dao.archi(p1, p2)!=null) {
					if(!grafo.containsEdge(grafo.getEdge(p2, p1))&&!grafo.containsEdge(grafo.getEdge(p1, p2))){
						if(dao.archi(p1, p2).getSomma1()>dao.archi(p1, p2).getSomma2()) {
							Graphs.addEdge(grafo, p1, p2, dao.archi(p1, p2).getPeso());
						}else if(dao.archi(p1, p2).getSomma1()<dao.archi(p1, p2).getSomma2()) {
							Graphs.addEdge(grafo, p2, p1, dao.archi(p1, p2).getPeso());
						}
						
					}
					
				}
				
			}
			
		}


		System.out.println("Grafo creato!");
		System.out.println("# VERTICI: " + this.grafo.vertexSet().size());
		System.out.println("# ARCHI: " + this.grafo.edgeSet().size());

		return grafo;	
	}


}
