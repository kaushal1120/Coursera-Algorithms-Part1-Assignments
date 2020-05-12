import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int noOfItems;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        noOfItems = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return noOfItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node x = new Node();
        x.item = item;
        if (first == null)
            last = x;
        else
            first.prev = x;
        x.next = first;
        first = x;
        noOfItems++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node x = new Node();
        x.item = item;
        if (last == null)
            first = x;
        else
            last.next = x;
        x.prev = last;
        last = x;
        noOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null)
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;
        noOfItems--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null)
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;
        noOfItems--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // TODO Code to be added.
    }
}
