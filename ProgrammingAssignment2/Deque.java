import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * This class provides implementation of Deque
 * The methods are:
 * addFirst: This method is used to add elements at the front of the queue
 * addLast: This method is used to add elements at the end of the queue
 * removeFirst: This method is used to remove elements at the front of the queue
 * removeLast: This method is ued to remove elements at the end of the queue
 * iterator: This method outputs the iterator of the Queue
 * size: This method returns the size of the array.
 * */
public class Deque<Item> implements Iterable<Item> {
    private static class Node<T> {
        public T val;
        public Node<T> next;
        public Node<T> prev;

        public Node(T val) {
            this.val = val;
            next = null;
            prev = null;
        }
    }

    private static class DequeIterator<T> implements Iterator<T> {
        Node<T> trav;

        public DequeIterator(Deque<T> obj) {
            trav = obj.head;
        }

        @Override
        public boolean hasNext() {
            return trav != null;
        }

        @Override
        public T next() {
            T ans = trav.val;
            trav = trav.next;
            return ans;
        }
    }

    private int size;
    private Node<Item> head;
    private Node<Item> tail;

    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    private void remove() {
        throw new UnsupportedOperationException("Specify the position of remove");
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null");
        }
        if (head == null) {
            head = new Node<>(item);
            tail = head;
        } else {
            Node<Item> temp = new Node<>(item);
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null");
        }
        if (head == null) {
            head = new Node<>(item);
            tail = head;
        } else {
            tail.next = new Node<>(item);
            tail.next.prev = tail;
            tail = tail.next;

        }
        size++;
    }

    public Item removeFirst() {
        if (this.size() == 0)
            throw new NoSuchElementException("Remove called on empty deque");
        Item ans = head.val;
        head = head.next;
        size--;
        if (size == 0)
            tail = null;
        else
            head.prev = null;
        return ans;

    }

    public Item removeLast() {
        if (this.size() == 0)
            throw new NoSuchElementException("Remove called on empty deque");
        Item ans = tail.val;
        tail = tail.prev;
        size--;
        if (size == 0)
            head = null;
        else {
            tail.next = null;
        }
        return ans;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(this);
    }

    public static void main(String[] args) {
        Deque<String> s = new Deque<>();
        s.addFirst("Hello");
        s.addLast("How");
        s.addFirst("are");
        s.addLast("You?");
        for (String word : s) {
            System.out.print(word + " ");
        }
        System.out.println();
        System.out.println(s.removeFirst());
        System.out.println(s.removeLast());
        System.out.println(s.removeFirst());
        System.out.println(s.removeLast());
        System.out.println(s.isEmpty());
        s.addLast("Hi");
        s.addFirst("Me");
        System.out.println(s.isEmpty());
        System.out.println(s.size());
        System.out.println(s.removeFirst());
        System.out.println(s.removeLast());
        /*
         * Queue becomes are->Hello->How->You?
         * When removeFirst is called Queue becomes Hello->How->You? and output is "are"
         * When removeLast is called Queue becomes Hello->How and the output is "You?"
         * When removeFirst is called Queue becomes How and the output is "Hello"
         * Finally output is How
         *  */
    }

}
