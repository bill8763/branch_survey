package com.allianz.sd.core.cisl;

import com.allianz.sd.core.cisl.bean.CISLAPITask;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class CISLAPIQueue {
    private Queue queue = null;

    public CISLAPIQueue() {
        queue = new LinkedList();
    }

    public void add(CISLAPITask task)  {
        queue.offer(task);
    }

    public Object get() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
