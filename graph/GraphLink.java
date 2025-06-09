package graph;

import listas.ListLinked;
import java.util.*;

public class GraphLink<E> {
    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<>();
    }

    public void insertVertex(E data) {
        if (!searchVertex(data)) {
            Vertex<E> newVertex = new Vertex<>(data);
            listVertex.insertLast(newVertex);
        }
    }

    public void insertEdge(E verOri, E verDes) {
        insertEdgeWeight(verOri, verDes, -1);
    }

    public void insertEdgeWeight(E verOri, E verDes, int weight) {
        Vertex<E> vOri = listVertex.searchVertexByData(verOri);
        Vertex<E> vDes = listVertex.searchVertexByData(verDes);

        if (vOri != null && vDes != null) {
            Edge<E> edgeToDes = new Edge<>(vDes, weight);
            Edge<E> edgeToOri = new Edge<>(vOri, weight);

            if (!vOri.listAdj.search(edgeToDes)) {
                vOri.listAdj.insertLast(edgeToDes);
            }
            if (!vDes.listAdj.search(edgeToOri)) {
                vDes.listAdj.insertLast(edgeToOri);
            }
        }
    }

    public boolean searchVertex(E data) {
        return listVertex.search(new Vertex<>(data));
    }

    public void removeVertex(E data) {
        Vertex<E> toRemove = listVertex.searchVertexByData(data);
        if (toRemove != null) {
            for (Vertex<E> v : listVertex) {
                v.listAdj.remove(new Edge<>(toRemove));
            }
            listVertex.remove(toRemove);
        }
    }

    public void removeEdge(E v, E z) {
        Vertex<E> v1 = listVertex.searchVertexByData(v);
        Vertex<E> v2 = listVertex.searchVertexByData(z);
        if (v1 != null && v2 != null) {
            v1.listAdj.remove(new Edge<>(v2));
            v2.listAdj.remove(new Edge<>(v1));
        }
    }

    public void dfs(E start) {
        Vertex<E> startVertex = listVertex.searchVertexByData(start);
        if (startVertex != null) {
            ListLinked<Vertex<E>> visited = new ListLinked<>();
            dfsVisit(startVertex, visited);
        }
    }

    private void dfsVisit(Vertex<E> v, ListLinked<Vertex<E>> visited) {
        visited.insertLast(v);
        System.out.println(v.getData());
        for (Edge<E> e : v.listAdj) {
            Vertex<E> neighbor = e.refDest;
            if (!visited.search(neighbor)) {
                dfsVisit(neighbor, visited);
            }
        }
    }

    public void bfs(E start) {
        Vertex<E> startVertex = listVertex.searchVertexByData(start);
        if (startVertex != null) {
            ListLinked<Vertex<E>> visited = new ListLinked<>();
            Queue<Vertex<E>> queue = new LinkedList<>();
            visited.insertLast(startVertex);
            queue.add(startVertex);
            while (!queue.isEmpty()) {
                Vertex<E> current = queue.poll();
                System.out.println(current.getData());
                for (Edge<E> e : current.listAdj) {
                    Vertex<E> neighbor = e.refDest;
                    if (!visited.search(neighbor)) {
                        visited.insertLast(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    public ArrayList<E> bfsPath(E start, E end) {
        Vertex<E> startVertex = listVertex.searchVertexByData(start);
        Vertex<E> endVertex = listVertex.searchVertexByData(end);
        ArrayList<E> path = new ArrayList<>();
        if (startVertex == null || endVertex == null) return path;
        ListLinked<Vertex<E>> visited = new ListLinked<>();
        Queue<Vertex<E>> queue = new LinkedList<>();
        Map<Vertex<E>, Vertex<E>> parentMap = new HashMap<>();
        visited.insertLast(startVertex);
        queue.add(startVertex);
        parentMap.put(startVertex, null);
        while (!queue.isEmpty()) {
            Vertex<E> current = queue.poll();
            if (current.equals(endVertex)) break;
            for (Edge<E> e : current.listAdj) {
                Vertex<E> neighbor = e.refDest;
                if (!visited.search(neighbor)) {
                    visited.insertLast(neighbor);
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        if (!parentMap.containsKey(endVertex)) return path;

        for (Vertex<E> at = endVertex; at != null; at = parentMap.get(at)) {
            path.add(0, at.getData());
        }
        return path;
    }

    public ArrayList<E> shortPath(E start, E end) {
        return dijkstra(start, end);
    }

    public boolean isConexo() {
        if (listVertex.isEmpty()) return true;
        ListLinked<Vertex<E>> visited = new ListLinked<>();
        dfsVisit(listVertex.getFirst(), visited);
        return visited.size() == listVertex.size();
    }

    public ArrayList<E> dijkstra(E start, E end) {
        Vertex<E> startVertex = listVertex.searchVertexByData(start);
        Vertex<E> endVertex = listVertex.searchVertexByData(end);
        Map<Vertex<E>, Integer> distances = new HashMap<>();
        Map<Vertex<E>, Vertex<E>> previous = new HashMap<>();
        PriorityQueue<Vertex<E>> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        for (Vertex<E> v : listVertex) {
            distances.put(v, v.equals(startVertex) ? 0 : Integer.MAX_VALUE);
            pq.add(v);
        }
        while (!pq.isEmpty()) {
            Vertex<E> current = pq.poll();
            if (current.equals(endVertex)) break;
            for (Edge<E> edge : current.listAdj) {
                Vertex<E> neighbor = edge.refDest;
                int alt = distances.get(current) + (edge.weight > 0 ? edge.weight : 1);
                if (alt < distances.get(neighbor)) {
                    pq.remove(neighbor);
                    distances.put(neighbor, alt);
                    previous.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        ArrayList<E> path = new ArrayList<>();
        for (Vertex<E> at = endVertex; at != null; at = previous.get(at)) {
            path.add(0, at.getData());
        }
        return path;
    }

    public Stack<E> dijkstraPath(E start, E end) {
        ArrayList<E> shortestPath = dijkstra(start, end);
        Stack<E> stack = new Stack<>();
        for (E node : shortestPath) {
            stack.push(node);
        }
        return stack;
    }

    @Override
    public String toString() {
        return listVertex.toString();
    }
}
