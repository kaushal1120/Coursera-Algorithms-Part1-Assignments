import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int head;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        head = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return head;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (head == s.length) resize(2 * s.length);
        s[head++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int randomIndex = StdRandom.uniform(0, head);
        Item item = s[randomIndex];
        s[randomIndex] = s[--head];
        s[head] = null;
        if (head > 0 && head == s.length / 4) resize(s.length / 2);
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < head; i++)
            copy[i] = s[i];
        s = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int randomIndex = StdRandom.uniform(0, head);
        return s[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator<Item>();
    }

    private class RandomQueueIterator<Item> implements Iterator<Item> {
        private int current;
        private Item[] x;

        public RandomQueueIterator() {
            current = 0;
            x = (Item[]) new Object[size()];
            for (int i = 0; i < head; i++)
                x[i] = (Item) s[i];
        }

        @Override
        public boolean hasNext() {
            return current != head;
        }

        @Override
        public Item next() {
            if (current == head)
                throw new java.util.NoSuchElementException();
            int randomIndex = StdRandom.uniform(current, head);
            Item item = x[randomIndex];
            x[randomIndex] = x[current];
            x[current++] = item;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // TODO Code to be added.
    }
}
