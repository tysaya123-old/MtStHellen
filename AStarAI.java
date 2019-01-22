
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
        Hashtable<String, Point> visited = new Hashtable<String, Point>();

        // Keep track of where we are and add the start point.
        final Point StartPoint = map.getStartPoint();
        final Point EndPoint = map.getEndPoint();
        final Double EndHeight = map.getTile(EndPoint);
        
        ArrayList<Point> path = new ArrayList<Point>();
        path.add(new Point(StartPoint));

        queue.add(new Node(path, 0, getHeuristic(map, StartPoint, EndPoint, map.getTile(StartPoint), EndHeight), map.getTile(StartPoint)));
        

        Node currNode;
        Point currPoint;
        Point[] neighbors;

        while(queue.peek() != null){
            currNode = queue.remove();
            currPoint = currNode.getPoint();
            
            //Check if is the end.
            if(currPoint.equals(EndPoint)){
                return currNode.getPath();
            }
            
            //Check if already visited.
            //TODO: Be sure we know at shortest with our Heruistic
            if(visited.get(currPoint.toString()) == null){
                //add to hash
                visited.put(currPoint.toString(), currPoint);
                
                neighbors = map.getNeighbors(currPoint);
                for(Point p : neighbors){
                	if(visited.get(p.toString()) == null){
	                    double height = map.getTile(p);
	                    //double cost = getCost(currNode.getElev(),height);
	                    double cost = map.getCost(currPoint, p);
	                    //TODO: Add heruistic
	                    queue.add(currNode.nextNode(p, cost, getHeuristic(map, p, EndPoint, height, EndHeight), height));
                	}
                }
            }
        }

        System.out.println("uhoh");
        return null;
    }


    private double getHeuristic(TerrainMap map, Point pt1, Point pt2, double h1, double h2){
    	Double maxSteps =(Double) Math.max(Math.abs(pt1.getX()-pt2.getX()), Math.abs(pt1.getY()-pt2.getY()));
    	if(h2>h1) {
    		return maxSteps*Math.pow(2, (h2-h1)/maxSteps);
    	}
    	else return 0;
    }

    private double getCost(double h1, double h2){
        return Math.pow(2.0,h1 - h2);
    }


}
