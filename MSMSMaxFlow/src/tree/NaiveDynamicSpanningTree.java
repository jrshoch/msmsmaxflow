package tree;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class NaiveDynamicSpanningTree implements DynamicSpanningTree {

    private final Map<Vertex, Set<Edge>> tree;
    private final Graph graph;
    private final Map<Edge, Long> slacks;

    private Set<Edge> recentPath;
    private Vertex recentPathStart;
    private Vertex recentPathEnd;
    private Edge recentPathMinimumEdge;
    private boolean recentPathPossible;
    private boolean recentPathClean;

    private NaiveDynamicSpanningTree(Graph graph, Map<Edge, Long> dualSlacks) {
        this.tree = Maps.newHashMap();
        this.graph = graph;
        this.slacks = Maps.newHashMap();
        for (Vertex vertex : graph.getVertices()) {
            Set<Edge> treeNeighbors = Sets.newHashSet();
            for (Edge edge : graph.getNeighboringEdges(vertex)) {
                Long slack = dualSlacks.get(graph.getDualOf(edge));
                slacks.put(edge, slack);
                Long reverseSlack = dualSlacks.get(graph.getDualOf(graph.getReverseEdge(edge)));
                if ((slack.longValue() > 0) && (reverseSlack.longValue() > 0)) {
                    treeNeighbors.add(edge);
                }
            }
        }
        this.recentPath = null;
        this.recentPathStart = null;
        this.recentPathEnd = null;
        this.recentPathMinimumEdge = null;
        this.recentPathPossible = false;
        this.recentPathClean = false;
    }

    public static NaiveDynamicSpanningTree createFromDualSlacks(Graph graph,
            Map<Edge, Long> dualSlacks) {
        return new NaiveDynamicSpanningTree(graph, dualSlacks);
    }

    private void calculatePath(Vertex start, Vertex end) {
        if (recentPathClean && (recentPathStart == start) && (recentPathEnd == end)) {
            return;
        }
        Queue<Vertex> queue = Lists.newLinkedList();
        Map<Vertex, Edge> predecessors = Maps.newHashMap();
        recentPathPossible = false;
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            if (vertex == end) {
                recentPathPossible = true;
                break;
            }
            for (Edge edge : tree.get(vertex)) {
                Vertex newVertex = edge.getHead();
                if (predecessors.get(newVertex) == null) {
                    predecessors.put(newVertex, edge);
                    queue.add(newVertex);
                }
            }
        }
        recentPath = Sets.newHashSet();
        long minimumSlack = Long.MAX_VALUE;
        Vertex backTrace = end;
        while (backTrace != start) {
            Edge edge = predecessors.get(backTrace);
            long slack = slacks.get(edge).longValue();
            if (slack < minimumSlack) {
                minimumSlack = slack;
                recentPathMinimumEdge = edge;
            }
            recentPath.add(edge);
            backTrace = edge.getTail();
        }
        recentPathStart = start;
        recentPathEnd = end;
        recentPathClean = true;
    }

    @Override
    public boolean areInSameComponent(Vertex vertex1, Vertex vertex2) {
        calculatePath(vertex1, vertex2);
        return recentPathPossible;
    }

    @Override
    public Edge getMinimumEdgeOnPath(Vertex start, Vertex end) {
        calculatePath(start, end);
        return recentPathMinimumEdge;
    }

    @Override
    public void remove(Edge edge) {
        Edge reverseEdge = graph.getReverseEdge(edge);
        tree.get(edge.getTail()).remove(edge);
        tree.get(edge.getHead()).remove(reverseEdge);
        recentPathClean = false;
    }

    @Override
    public void insert(Edge edge) {
        Edge reverseEdge = graph.getReverseEdge(edge);
        tree.get(edge.getTail()).add(edge);
        tree.get(edge.getHead()).add(reverseEdge);
        recentPathClean = false;
    }

    @Override
    public long getSlack(Edge edge) {
        return slacks.get(edge).longValue();
    }

    @Override
    public void addSlackOnPath(Vertex start, Vertex end, long amount) {
        calculatePath(start, end);
        for (Edge edge : recentPath) {
            slacks.put(edge, new Long(slacks.get(edge).longValue() + amount));
        }
    }

    @Override
    public void addSlackOnReversePath(Vertex start, Vertex end, long amount) {
        calculatePath(start, end);
        for (Edge edge : recentPath) {
            Edge reverseEdge = graph.getReverseEdge(edge);
            slacks.put(reverseEdge, new Long(slacks.get(reverseEdge).longValue() + amount));
        }
    }

}
