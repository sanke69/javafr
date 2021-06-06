package fr.java.graph.impl.algorithms.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.search.GTPathFinder;
import fr.java.lang.properties.ID;

/**
 * Search for costless path
 * 
 * @author sanke
 *
 */
public abstract class PathFinderAdapter implements GTPathFinder {

	private GTGraph		 		graph;
    private PriorityQueue<ID> 	nextVertices;
    HashMap<ID, Double> 		costData;
    HashMap<ID, ID> 			predecessorData;

    public PathFinderAdapter(GTGraph _graph) {
        super();
        graph = _graph;

        nextVertices    = new PriorityQueue<ID>(graph.getNodes().size(), new CostComparator());
        costData        = new HashMap<ID, Double>();
        predecessorData = new HashMap<ID, ID>();
    }

    public List<ID> compute(ID _from, ID _to) {
        setCost(_from, 0);
        setPredecessor(_from, null);
        nextVertices.addAll( graph.getNodes()
        						  .stream()
        						  .map(n -> n.getId())
        						  .collect(Collectors.toList()) );

        while(!nextVertices.isEmpty()) {
        	ID v = nextVertices.remove();
            if(v == _to)
                break ;

            for (GTNode n : graph.getNeighbors(v)) {
            	ID w = n.getId();
            			
                if (getCost(v) + getWeight(v, w) < getCost(w)) {
                    double newWeight = getCost(v) + getWeight(v, w);
                    setCost(w, newWeight);
                    setPredecessor(w, v);
                    nextVertices.remove(w);
                    nextVertices.add(w);
                }
            }
        }

        return path(_from, _to);
    }
    public List<ID> 			path(ID _from, ID _to) {
        ArrayList<ID> result = new ArrayList<ID>();
        result.add(_to);

        ID temp = _to;
        while(_from.compareTo(temp) != 0) {
            temp = getPredecessor(temp);
            result.add(temp);
        }
        Collections.reverse(result);
        return result;
    }

    protected abstract double 	getWeight(ID u, ID v);
    protected abstract double 	estimatedCost(ID v);

    class CostComparator implements Comparator<ID> {
        @Override
        public int compare(ID o1, ID o2) {
            double firstValue  = getCost(o1) + estimatedCost(o1);
            double secondValue = getCost(o2) + estimatedCost(o2);

            if (firstValue  == secondValue)
                return 0;

            if (firstValue < secondValue)
                return -1;

            return 1;
        }
    }

    PriorityQueue<ID> getnextVertices() {
        return nextVertices;
    }

    
    
    
    public double getCost(ID v) {
        if(costData.containsKey(v))
            return costData.get(v);

        return Double.MAX_VALUE;
    }

    protected void setCost(ID v, double w) {
    	costData.put(v, w);
    }

    public ID getPredecessor(ID v) {
        if (predecessorData.containsKey(v))
            return predecessorData.get(v);

        return null;
    }

    protected void setPredecessor(ID v, ID u) {
        predecessorData.put(v, u);
    }

    
}
