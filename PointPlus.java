import java.awt.Point;

public class PointPlus extends Point implements Comparable<PointPlus>{
	private Point prev;
	private double g;
	private double h;
	private double elev;

	public PointPlus(Point p, double ge, double ach){
		super(p);
		prev = p;
		g = ge;
		h = ach;
	}

	public Point getPrev(){ return prev; }
	public double getG(){ return g; }
	public double getH(){ return h; }
	public double getTotal(){ return g+h; }

	public void setPrev(Point p){ prev = p; }
	public void setG(double n){ g = n; }
	public void setH(double n){ h = n; }

	@Override
	public int compareTo(PointPlus other){
		double diff = (g + h) - (other.getG() + other.getH());
		if(diff > 0) return 1;
		if(diff == 0) return 0;
		else return -1;
	}
}