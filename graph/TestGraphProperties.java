package graph;
import graph.GraphListEdge;

public class TestGraphProperties {
    public static void main(String[] args) {
        GraphListEdge<String, Integer> g1 = new GraphListEdge<>();
        g1.insertVertex("A");
        g1.insertVertex("B");
        g1.insertVertex("C");
        g1.insertDirectedEdge("A", "B");
        g1.insertDirectedEdge("B", "C");

        System.out.println("Grafo 1:");
        System.out.println("Conexo: " + g1.esConexo());
        System.out.println("Plano: " + g1.esPlano());
        System.out.println("Autocomplementario: " + g1.Autocomplementario());

        GraphListEdge<String, Integer> g2 = new GraphListEdge<>();
        g2.insertVertex("X");
        g2.insertVertex("Y");
        g2.insertVertex("Z");
        g2.insertDirectedEdge("X", "Y");
        g2.insertDirectedEdge("Y", "Z");

        System.out.println("\nGrafo 2:");
        System.out.println("Isomorfo con Grafo 1: " + g1.esIsomorfo(g2));
        System.out.println("Conexo: " + g2.esConexo());
    }
}
