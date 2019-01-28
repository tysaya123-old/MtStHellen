
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
        final Double StartHeight = map.getTile(StartPoint);
        final Double EndHeight = map.getTile(EndPoint);
        
        ArrayList<Point> path = new ArrayList<Point>();
        path.add(new Point(StartPoint));

        queue.add(new Node(path, 0, getHeuristic(map, StartPoint, EndPoint, StartHeight, EndHeight), StartHeight));
        

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
    	Node currNode;
    	while(true) {
    		if(queue.peek() == null) return false;
    		else if(visited.get(queue.peek().getPoint().toString()) == null) return true;
    		else queue.remove();
    	}
    }
    
    private void addNeighbors(final TerrainMap map, PriorityQueue<Node> queue, Hashtable<String, Node> visited, Point[] neighbors, Node currNode, Point goal) {
    	for(Point p : neighbors){
        	if(visited.get(p.toString()) == null){
                double height = map.getTile(p);
                double cost = map.getCost(currNode.getPoint(), p);
                queue.add(currNode.nextNode(p, cost, getHeuristic(map, p, goal, height, map.getTile(goal)), height));
        	}
        }
    	return;
	}
    
    private double getHeuristic(TerrainMap map, Point pt1, Point pt2, double h1, double h2){

    	Double chebyDist =Math.max(Math.abs(pt1.getX()-pt2.getX()), Math.abs(pt1.getY()-pt2.getY()));
    	Double maxH = 255.0;
    	Double maxW = 500.0;

    	return chebyDist*Math.pow(2.0, (h2-h1-1)/chebyDist);
    }
    
    
    
    
    
    
    
    
    private class Node implements Comparable<Node>{
    	private ArrayList<Point> path;
    	private double g;
    	private double h;
    	private double currElev;

    	public Node(ArrayList<Point> p, double g, double h, double e){
    		path = p;
    		this.g = g;
    		this.h = h;
    		currElev = e;
    	}

    	public ArrayList<Point> getPath(){ return path; }
    	public Point getPoint(){ return path.get(path.size()-1); }
    	public double getG(){ return g; }
    	public double getH(){ return h; }
    	public double getElev() { return currElev; }
    	public double getTotal(){ return g+h; }

    	//public void setPath(ArrayList<Point> p){ path = p; }
    	// public void setG(double n){ g = n; }
    	// public void setH(double n){ h = n; }

    	public Node nextNode(Point p, double dist, double heur, double elev){
    		ArrayList<Point> nextAr = new ArrayList<Point>(path);
    		nextAr.add(p);
    		return new Node(nextAr, g+dist, heur, elev);
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



