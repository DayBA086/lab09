package graph;
import graph.GraphLink;

import java.util.ArrayList;
import java.util.Stack;

public class TestGraphLink {
    public static void main(String[] args) {
        GraphLink<String> graph = new GraphLink<>();

        // Insertar vértices
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        // Insertar aristas con o sin peso
        graph.insertEdgeWeight("A", "B", 2);
        graph.insertEdgeWeight("A", "C", 3);
        graph.insertEdgeWeight("B", "D", 4);
        graph.insertEdgeWeight("C", "D", 1);
        graph.insertEdgeWeight("D", "E", 5);

        // Mostrar DFS desde A
        System.out.println("Recorrido DFS desde A:");
        graph.dfs("A");

        // Mostrar BFS desde A
        System.out.println("\nRecorrido BFS desde A:");
        graph.bfs("A");

        // Camino BFS de A a E
        System.out.println("\nCamino más corto (BFS) de A a E:");
        ArrayList<String> bfsPath = graph.bfsPath("A", "E");
        System.out.println(bfsPath);

        // Camino más corto (Dijkstra) de A a E
        System.out.println("\nCamino más corto (Dijkstra) de A a E:");
        ArrayList<String> dijkstraPath = graph.shortPath("A", "E");
        System.out.println(dijkstraPath);

        // Camino más corto en pila (Dijkstra)
        System.out.println("\nCamino más corto como pila (Dijkstra):");
        Stack<String> pathStack = graph.dijkstraPath("A", "E");
        while (!pathStack.isEmpty()) {
            System.out.print(pathStack.pop());
            if (!pathStack.isEmpty()) System.out.print(" -> ");
        }

        // Conectividad
        System.out.println("\n\n¿El grafo es conexo? " + graph.isConexo());
    }
}
