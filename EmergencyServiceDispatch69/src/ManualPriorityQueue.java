


public class ManualPriorityQueue {

    private Incident[] queue;
    private int size;

    public ManualPriorityQueue(int capacity) {
        queue = new Incident[capacity];
        size = 0;
    }

    public void enqueue(Incident incident) {

        if (size == queue.length) {
            resize();
        }

        int i = size - 1;

        while (i >= 0 &&
                queue[i].getPriority() > incident.getPriority()) {

            queue[i + 1] = queue[i];
            i--;
        }

        queue[i + 1] = incident;
        size++;
    }

    public Incident dequeue() {

        if (isEmpty()) {
            return null;
        }

        Incident highest = queue[0];

        for (int i = 0; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }

        queue[size - 1] = null;
        size--;

        return highest;
    }

    public Incident peek() {
        if (isEmpty()) {
            return null;
        }

        return queue[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public Incident getAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return queue[index];
    }

    private void resize() {
        Incident[] newQueue = new Incident[queue.length * 2];

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }

        queue = newQueue;
    }
}