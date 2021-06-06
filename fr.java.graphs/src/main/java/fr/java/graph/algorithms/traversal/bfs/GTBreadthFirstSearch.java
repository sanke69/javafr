package fr.java.graph.algorithms.traversal.bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.java.graph.GTGraph;
import fr.java.graph.GTNode;
import fr.java.graph.algorithms.traversal.GTGraphTraversal;
import fr.java.lang.properties.ID;

/**
 *  Breadth First Search
 *  
 *  see. https://www.geeksforgeeks.org/applications-of-breadth-first-traversal/
 *  
 *  @author sanke
 */
@FunctionalInterface
public interface GTBreadthFirstSearch extends GTGraphTraversal {

	default void visit(GTGraph _graph, ID _nodeId) {
        LinkedList<ID> queue       = new LinkedList<ID>();
        List<ID>       visited     = new ArrayList<ID>();
        ID             currentId   = _nodeId;

    	visited . add(currentId);
        queue   . add(currentId); 

        while(queue.size() != 0)  { 
        	currentId = queue.poll();

        	if(visit(currentId) != VisitReturn.CONTINUE)
        		return ;
  
            for(GTNode next : _graph.getNeighbors(currentId)) {
                if(!visited.contains(next.getId())) {
                	visited . add(next.getId());
                    queue   . add(next.getId());
                } 
            }
        } 
    }

	VisitReturn visit(ID _nodeId);

}
