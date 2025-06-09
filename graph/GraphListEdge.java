package graph;

import java.util.*;

public class GraphListEdge<V, E> {
    ArrayList<VertexObj<V, E>> secVertex;
    ArrayList<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ArrayList<>();
        this.secEdge = new ArrayList<>();
    }

    public void insertVertex(V info) {
        if (!searchVertex(info)) {
            secVertex.add(new VertexObj<>(info, secVertex.size()));
        }
    }

    public void insertEdge(V info1, V info2) {
        VertexObj<V, E> v1 = getVertex(info1);
        VertexObj<V, E> v2 = getVertex(info2);

        if (v1 != null && v2 != null && !edgeExists(v1, v2)) {
            secEdge.add(new EdgeObj<>(v1, v2, null, secEdge.size()));
        }
    }

    public boolean searchVertex(V info) {
        return getVertex(info) != null;
    }

    private VertexObj<V, E> getVertex(V info) {
        for (VertexObj<V, E> v : secVertex) {
            if (v.info.equals(info)) {
                return v;
            }
        }
        return null;
    }

    public boolean searchEdge(V info1, V info2) {
        VertexObj<V, E> v1 = getVertex(info1);
        VertexObj<V, E> v2 = getVertex(info2);
        return v1 != null && v2 != null && edgeExists(v1, v2);
    }

    private boolean edgeExists(VertexObj<V, E> v1, VertexObj<V, E> v2) {
        for (EdgeObj<V, E> e : secEdge) {
            if ((e.endVertex1.equals(v1) && e.endVertex2.equals(v2)) ||
                (e.endVertex1.equals(v2) && e.endVertex2.equals(v1))) {
                return true;
            }
        }
        return false;
    }

    public void bfs(V start) {
        VertexObj<V, E> startVertex = getVertex(start);
        if (startVertex == null) return;

        Set<VertexObj<V, E>> visited = new HashSet<>();
        Queue<VertexObj<V, E>> queue = new LinkedList<>();
        visited.add(startVertex);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            VertexObj<V, E> current = queue.poll();
            System.out.println(current.info);

            for (EdgeObj<V, E> e : secEdge) {
                VertexObj<V, E> neighbor = null;
                if (e.endVertex1.equals(current) && !visited.contains(e.endVertex2)) {
                    neighbor = e.endVertex2;
                } else if (e.endVertex2.equals(current) && !visited.contains(e.endVertex1)) {
                    neighbor = e.endVertex1;
                }
                if (neighbor != null) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    public int getDegree(V info) {
        VertexObj<V, E> vertex = getVertex(info);
        if (vertex == null) return -1;
        int degree = 0;
        for (EdgeObj<V, E> e : secEdge) {
            if (e.endVertex1.equals(vertex) || e.endVertex2.equals(vertex)) {
                degree++;
            }
        }
        return degree;
    }

    public String classifyGraph() {
        int n = secVertex.size();
        int edgeCount = secEdge.size();
        int[] degrees = new int[n];

        for (int i = 0; i < n; i++) {
            degrees[i] = getDegree(secVertex.get(i).info);
        }

        Arrays.sort(degrees);

        boolean isComplete = edgeCount == (n * (n - 1)) / 2;
        boolean isCycle = Arrays.stream(degrees).allMatch(d -> d == 2);
        boolean isPath = degrees[0] == 1 && degrees[n - 1] == 1 && Arrays.stream(degrees, 1, n - 1).allMatch(d -> d == 2);
        boolean isWheel = degrees[n - 1] == n - 1 && Arrays.stream(degrees, 0, n - 1).allMatch(d -> d == 3);

        if (isComplete) return "K" + n;
        if (isCycle) return "C" + n;
        if (isPath) return "P" + n;
        if (isWheel) return "W" + n;

        return "No clasificado";
    }

    public void printGraphFormal() {
        System.out.println("G = (V, E)");
        System.out.print("V = {");
        for (int i = 0; i < secVertex.size(); i++) {
            System.out.print(secVertex.get(i).info);
            if (i < secVertex.size() - 1) System.out.print(", ");
        }
        System.out.println("}");

        System.out.print("E = {");
        for (int i = 0; i < secEdge.size(); i++) {
            EdgeObj<V, E> e = secEdge.get(i);
            System.out.print("(" + e.endVertex1.info + ", " + e.endVertex2.info + ")");
            if (i < secEdge.size() - 1) System.out.print(", ");
        }
        System.out.println("}");
    }

    public void printAdjacencyList() {
        for (VertexObj<V, E> v : secVertex) {
            System.out.print(v.info + ": ");
            List<V> neighbors = new ArrayList<>();
            for (EdgeObj<V, E> e : secEdge) {
                if (e.endVertex1.equals(v)) {
                    neighbors.add(e.endVertex2.info);
                } else if (e.endVertex2.equals(v)) {
                    neighbors.add(e.endVertex1.info);
                }
            }
            System.out.println(neighbors);
        }
    }

    public void printAdjacencyMatrix() {
        int n = secVertex.size();
        int[][] matrix = new int[n][n];

        for (EdgeObj<V, E> e : secEdge) {
            int i = e.endVertex1.position;
            int j = e.endVertex2.position;
            matrix[i][j] = 1;
            matrix[j][i] = 1;
        }

        System.out.print("    ");
        for (VertexObj<V, E> v : secVertex) {
            System.out.print(v.info + " ");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.print(secVertex.get(i).info + " [");
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
                if (j < n - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    public void insertDirectedEdge(V info1, V info2) {
        VertexObj<V, E> v1 = getVertex(info1);
        VertexObj<V, E> v2 = getVertex(info2);

        if (v1 != null && v2 != null && !directedEdgeExists(v1, v2)) {
            secEdge.add(new EdgeObj<>(v1, v2, null, secEdge.size()));
        }
    }
    private boolean directedEdgeExists(VertexObj<V, E> v1, VertexObj<V, E> v2) {
        for (EdgeObj<V, E> e : secEdge) {
            if (e.endVertex1.equals(v1) && e.endVertex2.equals(v2)) {
                return true;
            }
        }
        return false;
    }

    public int inDegree(V info) {
        VertexObj<V, E> vertex = getVertex(info);
        if (vertex == null) return -1;
        int in = 0;
        for (EdgeObj<V, E> e : secEdge) {
            if (e.endVertex2.equals(vertex)) {
                in++;
            }
        }
        return in;
    }

    public int outDegree(V info) {
        VertexObj<V, E> vertex = getVertex(info);
        if (vertex == null) return -1;
        int out = 0;
        for (EdgeObj<V, E> e : secEdge) {
            if (e.endVertex1.equals(vertex)) {
                out++;
            }
        }
        return out;
    }

    public String classifyDirectedGraph() {
        int n = secVertex.size();
        int[] outDegrees = new int[n];
        int[] inDegrees = new int[n];

        for (int i = 0; i < n; i++) {
            V data = secVertex.get(i).info;
            inDegrees[i] = inDegree(data);
            outDegrees[i] = outDegree(data);
        }

        boolean isCycle = true;
        boolean isPath = true;
        boolean isWheel = false;

        int countIn1Out1 = 0;
        int countIn0Out1 = 0;
        int countIn1Out0 = 0;

        for (int i = 0; i < n; i++) {
            if (inDegrees[i] == 1 && outDegrees[i] == 1) countIn1Out1++;
            if (inDegrees[i] == 0 && outDegrees[i] == 1) countIn0Out1++;
            if (inDegrees[i] == 1 && outDegrees[i] == 0) countIn1Out0++;
            if (inDegrees[i] != 1 || outDegrees[i] != 1) isCycle = false;
            if ((inDegrees[i] > 1 || outDegrees[i] > 1)) isPath = false;
        }

        if (countIn1Out1 == n - 2 && countIn0Out1 == 1 && countIn1Out0 == 1) isPath = true;
        else isPath = false;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && directedEdgeExists(secVertex.get(i), secVertex.get(j))) {
                    count++;
                }
            }
            if (count == n - 1) isWheel = true;
        }

        if (isCycle) return "C" + n;
        if (isPath) return "P" + n;
        if (isWheel) return "W" + n;

        return "No clasificado";
    }
//Conexo, si un grafo tiene conectado sus vertices por un camino.
    public boolean esConexo() {
        if (secVertex.isEmpty()) return true;
        Set<VertexObj<V, E>> visited = new HashSet<>();
        dfs(secVertex.get(0), visited);
        return visited.size() == secVertex.size();
    }

    private void dfs(VertexObj<V, E> current, Set<VertexObj<V, E>> visited) {
        visited.add(current);
        for (EdgeObj<V, E> e : secEdge) {
            VertexObj<V, E> neighbor = null;
            if (e.endVertex1.equals(current)) neighbor = e.endVertex2;
            else if (e.endVertex2.equals(current)) neighbor = e.endVertex1;
            if (neighbor != null && !visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }
// Isomorfo, si un grafo con respecto a otro tiene la misma forma.
    public boolean esIsomorfo(GraphListEdge<V, E> other) {
        return this.secVertex.size() == other.secVertex.size() &&
               this.secEdge.size() == other.secEdge.size();
    }
//Autocomplementario, si un grafo resultante de eliminar las aris
    public boolean Autocomplementario() {
        int n = secVertex.size();
        GraphListEdge<V, E> complement = new GraphListEdge<>();
        for (VertexObj<V, E> v : secVertex) {
            complement.insertVertex(v.info);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    VertexObj<V, E> vi = secVertex.get(i);
                    VertexObj<V, E> vj = secVertex.get(j);
                    if (!directedEdgeExists(vi, vj)) {
                        complement.insertDirectedEdge(vi.info, vj.info);
                    }
                }
            }
        }
        return this.esIsomorfo(complement);
    }
//Plano, si un grafo y sus aristas no se cruzan.
    public boolean esPlano() {
        int v = secVertex.size();
        int e = secEdge.size();
        return e <= 3 * v - 6; 
    }
}


