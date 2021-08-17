import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    private static class RandomQueueIterator<T> implements Iterator<T> {
        int j;
        T[] queue;

        public RandomQueueIterator(RandomizedQueue<T> rq) {
            this.queue = rq.queue;
            this.j = rq.size - 1;
        }

        @Override
        public boolean hasNext() {
            return this.j >= 0;
        }

        @Override
        public T next() {
            int x = StdRandom.uniform(this.j + 1);
            T temp = queue[x];
            queue[x] = queue[j];
            queue[j] = temp;
            j--;
            return temp;
        }
    }

    public RandomizedQueue() {
        queue = (Item[]) new Object[10];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return queue.length == size;
    }

    public int size() {
        return size;
    }

    private void resize() {
        Item[] temp = (Item[]) new Object[2 * queue.length];
        if (size >= 0) System.arraycopy(queue, 0, temp, 0, size);
        queue = temp;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item cannot be inserted");
        }
        if (isFull()) {
            resize();
        }
        queue[size] = item;
        size++;
    }

    public Item dequeue() {
        int j = StdRandom.uniform(size);
        Item ans = queue[j];
        queue[j] = queue[size - 1];
        queue[size - 1] = null;
        size--;
        return ans;
    }

    public Item sample() {
        int j = StdRandom.uniform(size);
        return queue[j];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomQueueIterator<>(this);
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("Hello");
        rq.enqueue("How");
        rq.enqueue("are");
        rq.enqueue("You");
        rq.enqueue("I");
        rq.enqueue("Am");
        rq.enqueue("fine");
        for (String s : rq) {
            System.out.print(s + ' ');
        }
        System.out.println();
        System.out.println(rq.sample());
        System.out.println(rq.dequeue());
        while (rq.size() > 0) {
            System.out.println(rq.dequeue());
        }
    }

}
