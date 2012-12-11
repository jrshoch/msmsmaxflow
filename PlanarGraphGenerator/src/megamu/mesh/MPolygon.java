package src.megamu.mesh;

import java.awt.Polygon;

import processing.core.PApplet;
import processing.core.PGraphics;

public class MPolygon {

	float[][] coords;
	int count;
	
	
	public Polygon getPolygon() {
		
		int [] x = new int[count];
		int [] y = new int[count];
		for(int i=0; i<count; i++) {
			x[i] = (int) coords[i][0];
			y[i] = (int) coords[i][1];
		}
		
		return new Polygon(x,y,count);  
		
	}
	
	public MPolygon(){
		this(0);
	}

	public MPolygon(int points){
		coords = new float[points][2];
		count = 0;
	}

	public void add(float x, float y){
		
		coords[count][0] = x;
		coords[count++][1] = y;
	}

	public void draw(PApplet p){
		draw(p.g);
	}

	public void draw(PGraphics g){
		g.beginShape();
		for(int i=0; i<count; i++){
			g.vertex(coords[i][0], coords[i][1]);
		}
		g.endShape(PApplet.CLOSE);
	}

	public int count(){
		return count;
	}

	public float[][] getCoords(){
		return coords;
	}

   	// moja funkcja 
	
	public boolean setCoords(int i, float x, float y) {
		
		if (i>=count) return false;
		
		coords[i][0] = x;
		coords[i][1] = y;
		
		return true;
		
	}
}