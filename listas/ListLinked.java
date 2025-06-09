package listas;

import graph.Vertex;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListLinked<E> implements Iterable<E> {
    private Node<E> first;
    private int size;

    public ListLinked() {
        first = null;
        size = 0;
    }

    public void insertLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (first == null) {
            first = newNode;
        } else {
            Node<E> current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public boolean search(E data) {
        Node<E> current = first;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public Vertex<E> searchVertexByData(E data) {
        Node<E> current = first;
        while (current != null) {
            Vertex<E> v = (Vertex<E>) current.data;
            if (v.getData().equals(data)) {
                return v;
            }
            current = current.next;
        }
        return null;
    }

    public void remove(E data) {
        if (first == null) return;

        if (first.data.equals(data)) {
            first = first.next;
            size--;
            return;
        }

        Node<E> current = first;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    public E getFirst() {
        if (first != null) {
            return first.data;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = first;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // Soporte para foreach (recorridos)
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = first;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    // Clase interna del nodo
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
}
