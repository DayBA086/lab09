package graph;

import listas.ListLinked;

public class Vertex<E> {
    private E data;
    public ListLinked<Edge<E>> listAdj;

    public Vertex(E data) {
        this.data = data;
        listAdj = new ListLinked<>();
    }

    public E getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex<?>) {
            Vertex<E> v = (Vertex<E>) o;
            return this.data.equals(v.data);
        }
        return false;
    }

    @Override
    public String toString() {
        return data + " --> " + listAdj.toString() + "\n";
    }
}
