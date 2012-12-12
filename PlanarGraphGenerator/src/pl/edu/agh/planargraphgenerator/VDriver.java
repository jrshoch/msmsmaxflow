package src.pl.edu.agh.planargraphgenerator;
import java.util.Random;

import megamu.mesh.Voronoi;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * megamu.mesh packege was downloaded from http://www.leebyron.com/else/mesh/
 * (and slightly modified, mainly by adding some moethods)
 * 
 * @author Adam Sędziwy
 */
public class VDriver {

	public Voronoi myVoronoi = null;

	public UndirectedSparseGraph<Pair<Float>, String> planarGraph = null;

	public VDriver(int W, int H) {
		int X = W / 10;
		int Y = H / 10;
		Random r = new Random(System.currentTimeMillis());
		float[][] points = new float[X * Y][2];

		int i = 0;

		for (int ix = 0; ix < X; ix++) {

			for (int iy = 0; iy < Y; iy++) {

				points[i][0] = (100F * ix + r.nextInt(100)) / 10;
				points[i][1] = (100F * iy + r.nextInt(200)) / 10;
				i++;

			}

		}

		myVoronoi = new Voronoi(points);

		planarGraph = new UndirectedSparseGraph<>();

		float[][] edges = myVoronoi.getEdges();

		for (int j = 0; j < edges.length; j++) {

			// Keep all vertices within the window W x H
			if (edges[j][0] > W || edges[j][1] > H || edges[j][2] > W
					|| edges[j][3] > H || edges[j][0] < 0 || edges[j][1] < 0
					|| edges[j][2] < 0 || edges[j][3] < 0)
				continue;

			Pair<Float> v1 = new Pair<>(edges[j][0], edges[j][1]);
			Pair<Float> v2 = new Pair<>(edges[j][2], edges[j][3]);

			planarGraph.addEdge(v1.toString() + "-" + v2.toString(), v1, v2);

		}

	}

}
