import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic data type for a deque (which is a double ended queue). It's kinda like a queue + stack mishmash where you can
 * add something to the start or the end.
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * Private class which acts as individual nodes in our linked-list implementation.
     */
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    /**
     * Private iterator class to be used within the `Dequeue.iterator` function. Supports both the `hasNext` and `next`
     * functions, but will throw an erorr on calling `remove`.
     *
     * Navigates through the Deque by moving from the first to the last node.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = first;

        /**
         * Can the iterator continue iterating?
         *
         * @return
         */
        public boolean hasNext() {
            return currentNode != null;
        }

        /**
         * Iterates to the next node in the Deque and returns the current one.
         *
         * @return
         */
        public Item next() {
            if (!hasNext()) {
               throw new NoSuchElementException("There isn't a next item. Boo");
            }

            // Return the current item and then go to the next one
            Item currentItem = currentNode.item;
            currentNode = currentNode.next;
            return currentItem;
        }

        /**
         * Nope.
         */
        public void remove() {
            throw new UnsupportedOperationException("Sorry bro you can't use remove from the iterator");
        }
    }

    private int N;
    private Node first;
    private Node last;

    /**
     * Creates a empty deque with both the first and last nodes set to null
     */
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    /**
     * Is this deque empty or not?
     *
     * @return
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * How many nodes are currently in this deque?
     *
     * @return
     */
    public int size() {
        return N;
    }

    /**
     * Will add a new item to the start of the deque. Will throw an error if an empty item is passed through. If this is
     * the first item that has been added to the deque, then this will also set the last item in the deque to be the
     * first.
     *
     * @param item The item to add to the start of the deque
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Woah, you can't add nothing");
        }

        Node previousFirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;

        if (previousFirst == null) {
            // This is the first time something has been added to the deque
            first.next = null;
        } else {
            first.next = previousFirst;
            previousFirst.previous = first;
        }

        // Is this the only node in the deque?
        if (last == null) {
            last = first;
        }

        N++;
    }

    /**
     * Will add a new item to the end of the deque. Will throw an error if an empty item is passed through. If this is
     * the first item that has been added to the deque, then this will also set the first item in the deque to be the
     * last.
     *
     * @param item The item to add to the end of the deque
     */
    public void addLast(Item item) {
        if (isEmpty()) {
            throw new NullPointerException("Woah, you can't add nothing");
        }

        Node previousLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if (previousLast == null) {
            // This is the first time something has been added to the deque
            last.previous = null;
        } else {
            last.previous = previousLast;
            previousLast.next = last;
        }

        // Is this the only node in the deque?
        if (first == null) {
            first = last;
        }

        N++;
    }

    /**
     * Removes the item from the start of the deque. Will throw an exception if the deque is empty. After the successful
     * removal, this function will return the item that just got removed.
     *
     * @return The item that just got removed
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item itemToReturn = first.item;
        first = first.next;

        // Clear whatever previous value we might have
        if (first != null && first.previous != null) {
            first.previous = null;
        }

        // Have we just emptied out the deque?
        if (first == null) {
            last = null;
        }

        N--;

        return itemToReturn;
    }
    /**
     * Removes the item from the end of the deque. Will throw an exception if the deque is empty. After the successful
     * removal, this function will return the item that just got removed.
     *
     * @return The item that just got removed
     */
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Item itemToReturn = last.item;
        last = last.previous;

        // Clear whatever next value we might have
        if (last != null && last.previous != null) {
            last.next = null;
        }

        // Have we just emptied out the deque?
        if (last == null) {
            first = null;
        }

        N--;

        return itemToReturn;
    }

    /**
     * An iterator to iterate through the instance of the deque.
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deq = new Deque<String>();

        deq.addFirst("1");
        deq.addFirst("2");
        deq.addFirst("3");
        deq.addFirst("4");
        deq.addFirst("5");
        deq.addLast("6");
        deq.addLast("7");

        Iterator<String> iterate = deq.iterator();

        while(iterate.hasNext()) {
            System.out.println(iterate.next());
        }

        System.out.println(deq.size());
    }

}
