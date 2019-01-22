import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;
public class Node implements Comparable<Node>{
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
