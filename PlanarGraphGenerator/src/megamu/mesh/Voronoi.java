package src.megamu.mesh;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import quickhull3d.QuickHull3D;

public class Voronoi {

	float[][] points;
	float[][] edges;
	MPolygon[] regions;

	// HashMap<String, ArrayList<PointReference>> crossPoints = null;

	public MPolygon getRegion(int i) {
		if (i >= regions.length || i < 0)
			return null;

		return regions[i];
	}

	
	public class CrossPoints extends HashMap<String, ArrayList<PointReference>> {

		private static final long serialVersionUID = -993575480908951571L;


		public PointReference get(float tmpx, float tmpy, int i, int k)
				throws Exception {

			String key = tmpx + " " + tmpy;

			MessageDigest m = null;
			try {
				m = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				Logger.getLogger(Void.class.getName()).log(Level.SEVERE, null,
						ex);
			}
			m.update(key.getBytes(), 0, key.length());
			key = new BigInteger(1, m.digest()).toString(16);

			if (!this.containsKey(key)) {
				throw new Exception("Non existing key (md5) : " + key);
			}

			for (PointReference L : this.get(key)) {

				if (L.x == tmpx && L.y == tmpy && L.regionNumber == i
						&& L.numberOfPointInRegion == k)
					return L;

			}
			throw new Exception("Could not find point # " + k + " in region "
					+ i + ", coordinates=(" + tmpx + "," + tmpy + "), md5 : "
					+ key);

		

	}

	
	public ArrayList<PointReference> get(float tmpx, float tmpy)
			throws Exception {

		String key = tmpx + " " + tmpy;

		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Void.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		m.update(key.getBytes(), 0, key.length());
		key = new BigInteger(1, m.digest()).toString(16);

		if (!this.containsKey(key)) {
			throw new Exception("Non existing key (md5) : " + key);
		}

		return  this.get(key);
	}

}
	
	
	
	public CrossPoints crossPoints = null;

	public class PointReference {

		public int regionNumber = -1;
		public int numberOfPointInRegion = -1;
		public float x = -Float.MAX_VALUE;
		public float y = -Float.MAX_VALUE;

		public float initial_x = -Float.MAX_VALUE;
		public float initial_y = -Float.MAX_VALUE;

		public final String md5;

		public PointReference(int regionNumber, int numberOfPointInRegion,
				float x, float y) {

			this.regionNumber = regionNumber; // Konwencja: w pętlach iterowane
												// zmienną i
			this.numberOfPointInRegion = numberOfPointInRegion;// Konwencja: w
																// pętlach
																// iterowane
																// zmienną k
			this.x = x;
			this.y = y;

			this.initial_x = x;
			this.initial_y = x;

			String s = x + " " + y;

			MessageDigest m = null;
			try {
				m = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				Logger.getLogger(Void.class.getName()).log(Level.SEVERE, null,
						ex);
			}
			m.update(s.getBytes(), 0, s.length());
			md5 = new BigInteger(1, m.digest()).toString(16);

		}

	}

	public Voronoi(float[][] points) {

		if (points.length < 1) {
			edges = new float[0][4];
			regions = new MPolygon[0];
			return;
		}

		this.points = points;

		// build points array for qhull
		double qPoints[] = new double[points.length * 3 + 9];
		for (int i = 0; i < points.length; i++) {
			qPoints[i * 3] = points[i][0];
			qPoints[i * 3 + 1] = points[i][1];
			qPoints[i * 3 + 2] = -(points[i][0] * points[i][0] + points[i][1]
					* points[i][1]); // standard half-squared eucledian distance
		}
		// 1
		qPoints[qPoints.length - 9] = -8000D;
		qPoints[qPoints.length - 8] = 0D;
		qPoints[qPoints.length - 7] = -64000000D;
		// 2
		qPoints[qPoints.length - 6] = 8000D;
		qPoints[qPoints.length - 5] = 8000D;
		qPoints[qPoints.length - 4] = -128000000D;
		// 3
		qPoints[qPoints.length - 3] = 8000D;
		qPoints[qPoints.length - 2] = -8000D;
		qPoints[qPoints.length - 1] = -128000000D;

		// prepare quickhull
		QuickHull3D quickHull = new QuickHull3D(qPoints);
		int[][] faces = quickHull.getFaces(QuickHull3D.POINT_RELATIVE
				+ QuickHull3D.CLOCKWISE);
		int artifact = 0;

		// compute dual points
		double dualPoints[][] = new double[faces.length][2];
		for (int i = 0; i < faces.length; i++) {

			// test if it's the artifact
			if (faces[i][0] >= points.length && faces[i][1] >= points.length
					&& faces[i][2] >= points.length)
				artifact = i;

			double x0 = qPoints[faces[i][0] * 3 + 0];
			double y0 = qPoints[faces[i][0] * 3 + 1];
			double x1 = qPoints[faces[i][1] * 3 + 0];
			double y1 = qPoints[faces[i][1] * 3 + 1];
			double x2 = qPoints[faces[i][2] * 3 + 0];
			double y2 = qPoints[faces[i][2] * 3 + 1];

			double v1x = 2 * (x1 - x0);
			double v1y = 2 * (y1 - y0);
			double v1z = x0 * x0 - x1 * x1 + y0 * y0 - y1 * y1;

			double v2x = 2 * (x2 - x0);
			double v2y = 2 * (y2 - y0);
			double v2z = x0 * x0 - x2 * x2 + y0 * y0 - y2 * y2;

			double tmpx = v1y * v2z - v1z * v2y;
			double tmpy = v1z * v2x - v1x * v2z;
			double tmpz = v1x * v2y - v1y * v2x;

			dualPoints[i][0] = tmpx / tmpz;
			dualPoints[i][1] = tmpy / tmpz;
		}

		// create edge/point/face network
		edges = new float[1][4];
		int edgeCount = 0;
		LinkedArray faceNet = new LinkedArray(faces.length);
		IntArray[] pointBuckets = new IntArray[points.length];
		for (int i = 0; i < points.length; i++)
			pointBuckets[i] = new IntArray();

		// discover edges
		for (int i = 0; i < faces.length; i++) {

			// bin faces to the points they belong with
			for (int f = 0; f < faces[i].length; f++)
				if (faces[i][f] < points.length)
					pointBuckets[faces[i][f]].add(i);

			for (int j = 0; j < i; j++) {
				if (i != artifact && j != artifact
						&& isEdgeShared(faces[i], faces[j])) {

					faceNet.link(i, j);

					if (edges.length <= edgeCount) {
						float[][] tmpedges = new float[edges.length * 2][4];
						System.arraycopy(edges, 0, tmpedges, 0, edges.length);
						edges = tmpedges;
					}

					edges[edgeCount][0] = (float) dualPoints[i][0];
					edges[edgeCount][1] = (float) dualPoints[i][1];
					edges[edgeCount][2] = (float) dualPoints[j][0];
					edges[edgeCount][3] = (float) dualPoints[j][1];
					edgeCount++;

				}
			}
		}

		// trim edges down
		float[][] tmpedges = new float[edgeCount][4];
		// for(int i=0; i<tmpedges.length; i++)
		// tmpedges[i] = edges[i];
		System.arraycopy(edges, 0, tmpedges, 0, tmpedges.length);
		edges = tmpedges;

		// calculate the region for each point
		regions = new MPolygon[points.length];

		for (int i = 0; i < points.length; i++) {

			IntArray faceOrder = new IntArray(pointBuckets[i].length);

			// add coords of the region in the order they touch, starting with
			// the convenient first
			int p = pointBuckets[i].get(0);

			while (p >= 0) {

				faceOrder.add(p);

				// find the next coordinate that is in this set that we haven't
				// used yet
				int newP = -1;
				for (int k = 0; k < faceNet.get(p).linkCount; k++) {
					int neighbor = faceNet.get(p).links[k];
					if (!faceOrder.contains(neighbor)
							&& pointBuckets[i].contains(neighbor)) {
						newP = neighbor;
						break;
					}
				}
				p = newP;

			}

			// turn the coordinates into a polygon
			regions[i] = new MPolygon(pointBuckets[i].length);
			for (int f = 0; f < faceOrder.length; f++) {
				int face = faceOrder.get(f);
				regions[i].add((float) dualPoints[face][0],
						(float) dualPoints[face][1]);
			}

		}

		crossPoints = findCrossPoints();

	}

	public HashMap<String, ArrayList<PointReference>> getCrossPoints() {
		return crossPoints;
	}

	public MPolygon[] getRegions() {
		return regions;
	}

	public float[][] getEdges() {
		return edges;
	}

	protected boolean isEdgeShared(int face1[], int face2[]) {
		for (int i = 0; i < face1.length; i++) {
			int cur = face1[i];
			int next = face1[(i + 1) % face1.length];
			for (int j = 0; j < face2.length; j++) {
				int from = face2[j];
				int to = face2[(j + 1) % face2.length];
				if (cur == from && next == to || cur == to && next == from)
					return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return HashMap mapping of md5 sum of a given vertex to an array of its
	 *         occurences in Voronoi diagram regions
	 */
	private CrossPoints findCrossPoints() {

		CrossPoints ret = new CrossPoints();

		
		for (int i = 0; i < regions.length; i++) {

			for (int k = 0; k < regions[i].count; k++) {

				float x = regions[i].coords[k][0];
				float y = regions[i].coords[k][1];
				PointReference tmp = new PointReference(i, k, x, y);

				String key = tmp.md5;

				
				
				if (ret.containsKey(key)) {

					
					ret.get(key).add(tmp);

				} else {


					ret.put(key, new ArrayList<PointReference>());
					ret.get(key).add(tmp);
				}

			}

		}


		return ret;
	}

	public void stretchRegions(float factor) {

		for (int i = 0; i < regions.length; i++) {

			for (int k = 0; k < regions[i].getCoords().length; k++) {

				float tmpx = regions[i].getCoords()[k][0];
				float tmpy = regions[i].getCoords()[k][1];

				float x0 = points[i][0];
				float y0 = points[i][1];

				float _x = x0 + factor * (regions[i].getCoords()[k][0] - x0);
				float _y = y0 + factor * (regions[i].getCoords()[k][1] - y0);

				regions[i].getCoords()[k][0] = x0 + factor
						* (regions[i].getCoords()[k][0] - x0);
				regions[i].getCoords()[k][1] = y0 + factor
						* (regions[i].getCoords()[k][1] - y0);

				PointReference p = null;
				try {
					p = crossPoints.get(tmpx, tmpy, i, k);
				} catch (Exception e) {

					e.printStackTrace();
				}

				p.x = _x;
				p.y = _y;

			}
		}

	}

	/**
	 * Establishes mapping between adjacent region and md5 sums of related crosspoints
	 * shared by i-th and neighboring region(s) 
	 * 	  
	 * @param i - number of region
	 * @return
	 */
	public HashMap<Integer, ArrayList<String>> getAdjacentRegions(int i) {

		HashMap<Integer, ArrayList<String>> ret = new HashMap<>();
		HashSet<Integer> tmp = new HashSet<>();
		boolean found = false;

		for (ArrayList<PointReference> prl : crossPoints.values()) {

			found = false;
			tmp.clear();

			for (PointReference pr : prl) {

				if (pr.regionNumber == i) //prl list describes crosspoint shared by i-th region
					found = true;

				tmp.add(pr.regionNumber);
			}

			if (found) {

				for (Integer I : tmp) {

					if (I==i) continue;
					
					if (ret.containsKey(I)) {
						
						ret.get(I).add(prl.get(0).md5);
						
					} else {						
						ArrayList<String> md5s = new ArrayList<>();
						md5s.add(prl.get(0).md5);
						ret.put(I,md5s);
					}
				}

			}
		}

		return ret;
	}

	class FloatPoint {
		float x;
		float y;
		public FloatPoint(float x, float y) {
			
			this.x = x;
			this.y = y;
		}
		
		
	}
	public float[][] getLinkingPoints(int i, int j, ArrayList<String> arrayList) {
		
		ArrayList<FloatPoint> tmp = new ArrayList<>();
		
		for(String MD5 : arrayList) {
			
			for(PointReference pr : crossPoints.get(MD5)) {
				
				if (pr.regionNumber == i || pr.regionNumber == j) tmp.add(new FloatPoint(pr.x, pr.y));
				
				
			}
			
		}
	
		float [][] ret = new float[ tmp.size() ][2];
		int count = 0;
		
		for(FloatPoint fp : tmp) {
			
			ret[count][0]   = fp.x;
			ret[count++][1] = fp.y;
			
		}
		
		
		return ret;
	}

}
