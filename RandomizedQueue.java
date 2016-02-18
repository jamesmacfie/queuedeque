import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic datatype for a queue that grabs items out of it at uniformly random intervals.
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int N;

    public RandomizedQueue() {
        // Create a new queue with a capacity of 1
        queue = (Item[]) new Object[1];
    }

    /**
     * Is this queue empty or not?
     *
     * @return
     */
    public boolean isEmpty() { return N == 0; }

    /**
     * How many nodes are currently in this queue?
     *
     * @return
     */
    public int size() { return N; }

    /**
     * Add a new item to the end of the queue. If we are running out of roon in our internal array, double the amount of
     * available space.
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Woah, you can't add nothing");
        }

        queue[N] = item;
        N++;

        if (N == queue.length) {
            // We've run out of room!
            resize(N * 2);
        }
    }

    /**
     * Remove an item from a uniformly random place in the queue. If we then find, after removing it, that we are a quarter
     * "full", resize the available space.
     *
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int idx = StdRandom.uniform(N);

        /**
         * Interesting implementation here (well, I think so). We don't care about the total size of the array (ie, queue.length)
         * but rather our internal reference to int N. Instead of totally removing the item, simply swap the random item for the
         * item at the end of the array and then dereference that item.
         */
        Item itemToReturn = queue[idx];
        queue[idx] = queue[N - 1]; // Swap out the random item with one at the end of the array.
        queue[N - 1]= null; // And then remove any reference to that item
        N--;

        if (N > 0 && N <= queue.length / 4) {
            // Resize!
            resize(N);
        }

        return itemToReturn;
    }

    /**
     * Grab a random item from the queue but don't remove it
     *
     * @return
     */
    public Item sample() {
       if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int idx= StdRandom.uniform(N);
        return queue[idx];
    }

    /**
     * An iterator to iterate through the instance of the queue.
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    /**
     * Private function to resize our internal queue array.
     *
     * @param newSize
     */
    private void resize(int newSize) {
        Item[] newQueue = (Item[]) new Object[newSize];

        for (int i = 0; i < N; i++) {
            newQueue[i] = queue[i];
        }

        queue = newQueue;
    }

    /**
     * Private iterator class to be used within the `RandomizedQueue.iterator` function. Supports both the `hasNext` and `next`
     * functions, but will throw an erorr on calling `remove`.
     *
     * Navigates randomly through the queue
     */
    private class QueueIterator implements Iterator<Item> {
        private Item[] iteratorQueue;
        private int iteratorIdx;

        public QueueIterator() {
            iteratorQueue = (Item[]) new Object[N];
            iteratorIdx = 0;

            /**
             * In order to randomly move through the queue array without duplication, we create an array of random non-repeating
             * indexes and then each time we move to the next item we simply grab the item from the queue at the given index.
             */
            for(int i = 0; i < iteratorQueue.length; i++) {
                iteratorQueue[i] = queue[i];
            }

            // Knuth shuffle :)
            for(int i = 1; i < iteratorQueue.length; i++) {
                int swapIdx = StdRandom.uniform(i + 1);

                Item tempItem = iteratorQueue[i];
                iteratorQueue[i] = iteratorQueue[swapIdx];
                iteratorQueue[swapIdx] = tempItem;
            }
        }

        /**
         * Can the iterator continue iterating?
         *
         * @return
         */
        public boolean hasNext() {
            return iteratorIdx < N;
        }

        /**
         * Randomly iterates to the next item in the queue and returns the current one.
         *
         * @return
         */
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There isn't a next item. Boo");
            }

            Item item = iteratorQueue[iteratorIdx];
            iteratorIdx++;
            return item;
        }

        /**
         * Nope.
         */
        public void remove() {
            throw new UnsupportedOperationException("Sorry bro you can't use remove from the iterator");
        }
    }

    public static void main (String[] args) {

    }
}
