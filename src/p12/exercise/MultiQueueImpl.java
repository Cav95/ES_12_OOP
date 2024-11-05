package p12.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    HashMap<Q, List<T>> multiQueue = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        Set<Q> queueAvaiable = new HashSet<>();
        for (Entry<Q, List<T>> elem : multiQueue.entrySet()) {
            queueAvaiable.add(elem.getKey());
        }
        return queueAvaiable;
    }

    @Override
    public void openNewQueue(Q queue) {

        if (!availableQueues().contains(queue)) {
            List<T> newQueue = new ArrayList<>();

            multiQueue.put(queue, newQueue);
        }

    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        return multiQueue.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {

        multiQueue.get(queue).addLast(elem);

    }

    @Override
    public T dequeue(Q queue) {
        if (!multiQueue.get(queue).isEmpty()) {
            return multiQueue.get(queue).removeFirst();
        } else {
            return null;
        }

    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        HashMap<Q, T> multiDequeueOne = new HashMap<>();

        for (Entry<Q, List<T>> elem : multiQueue.entrySet()) {
            multiDequeueOne.put(elem.getKey(), dequeue(elem.getKey()));
        }
        return multiDequeueOne;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> allEnqueuedElementsSet = new TreeSet<>();

        for (Entry<Q, List<T>> elemMap : multiQueue.entrySet()) {
            for (T elemT : elemMap.getValue()) {
                allEnqueuedElementsSet.add(elemT);
            }

        }
        return allEnqueuedElementsSet;

    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        List<T> listDequeue = new ArrayList<>();

        while (!multiQueue.get(queue).isEmpty()) {
            listDequeue.addLast(dequeue(queue));
        }
        return listDequeue;

    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        List<T> reallocatedListDequeue = dequeueAllFromQueue(queue);
        multiQueue.remove(queue);

        while (!reallocatedListDequeue.isEmpty()) {
            multiQueue.get(availableQueues().iterator().next()).add(reallocatedListDequeue.removeFirst());

        }

    }

}
