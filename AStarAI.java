
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.Hashtable;
/// First AI h = 0.

public class AStarAI implements AIModule
{
    
    /// Creates the path to the goal.
    public List<Point> createPath(final TerrainMap map)
    {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Hashtable<String, Node> visited = new Hashtable<String, Node>();

        // Keep track of where we are and add the start point.
        final Point StartPoint = map.getStartPoint();
        final Point EndPoint = map.getEndPoint();
        
        ArrayList<Point> path = new ArrayList<Point>();
        path.add(new Point(StartPoint));

        queue.add(new Node(path, 0, getHeuristic(map, StartPoint, EndPoint)));
        

        Node currNode;
        Point currPoint;
        Point[] neighbors;

        while(true){
        	if(!findNextUnvisited(queue, visited)) return null;
            currNode = queue.remove();
            currPoint = currNode.getPoint();
            
            //Check if is the end.
            if(currPoint.equals(EndPoint)){
                return currNode.getPath();
            }
            
            //Check if already visited.
            if(visited.get(currPoint.toString()) == null){
                //add to hash
                visited.put(currPoint.toString(), currNode);

                neighbors = map.getNeighbors(currPoint);
                addNeighbors(map, queue, visited, neighbors, currNode, EndPoint);
            }
        }
    }

	private boolean findNextUnvisited(PriorityQueue<Node> queue, Hashtable<String, Node> visited) {
    	while(true) {
    		if(queue.peek() == null) return false;
    		else if(visited.get(queue.peek().getPoint().toString()) == null) return true;
    		else queue.remove();
    	}
    }
    
    private void addNeighbors(final TerrainMap map, PriorityQueue<Node> queue, Hashtable<String, Node> visited, Point[] neighbors, Node currNode, Point goal) {
    	for(Point p : neighbors){
        	if(visited.get(p.toString()) == null){
                queue.add(currNode.nextNode(p, map.getCost(currNode.getPoint(), p), getHeuristic(map, p, goal)));
        	}
        }
    	return;
	}
    
    private double getHeuristic(final TerrainMap map,final Point pt1, final Point pt2){
    	
    	Double chebyDist = Math.max(Math.abs(pt1.getX()-pt2.getX()), Math.abs(pt1.getY()-pt2.getY()));
    	Double h1 = map.getTile(pt1);
    	Double h2 = map.getTile(pt2);
    	
    	return chebyDist*Math.pow(2.0, (h2-h1-1)/chebyDist);
//    	double sum = 0.0;
//    	double deltaH = (h2 - h1)/chebyDist;
//    	for(int i = 0; i < chebyDist; i++) {
//    		sum+= (h1+deltaH*i)/(h1+deltaH*(i+1) + 1);
//    	}
//    	
//    	double downUp = h1 + chebyDist/2 - 1;
//    	
//    	return Math.min(downUp, sum);
    }
    
    
    
    
    
    
    
    
    private class Node implements Comparable<Node>{
    	private ArrayList<Point> path;
    	private double g;
    	private double h;

    	public Node(ArrayList<Point> p, double g, double h){
    		path = p;
    		this.g = g;
    		this.h = h;
    	}

    	public ArrayList<Point> getPath(){ return path; }
    	public Point getPoint(){ return path.get(path.size()-1); }
    	public double getG(){ return g; }
    	public double getTotal(){ return g+h; }

    	//public void setPath(ArrayList<Point> p){ path = p; }
    	// public void setG(double n){ g = n; }
    	// public void setH(double n){ h = n; }

    	public Node nextNode(Point p, double dist, double heur){
    		ArrayList<Point> nextAr = new ArrayList<Point>(path);
    		nextAr.add(p);
    		return new Node(nextAr, g+dist, heur);
    	}

    	public String toString(){
    		return path.get(path.size() - 1).toString();
    	}

    	@Override
    	public int compareTo(Node other){
    		double diff = getTotal() - other.getTotal();
    		if(diff > 0) return 1;
    		if(diff == 0) return 0;
    		else return -1;
    	}
    }

}



