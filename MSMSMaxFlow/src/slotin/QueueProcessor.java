package slotin;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueProcessor<T> implements Runnable {

    private final ConcurrentLinkedQueue<T> producerQueue;
    private final Collection<ConcurrentLinkedQueue<T>>
    		consumerQueues;
    
    public QueueProcessor(ConcurrentLinkedQueue<T> producerQueue, 
	    Collection<ConcurrentLinkedQueue<T>> consumerQueues){
	this.producerQueue = producerQueue;
	this.consumerQueues = consumerQueues;
    }
    
    @Override
    public void run() {
	T newObject;
	while (true){
	    if (producerQueue.isEmpty()){
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    } else {
		newObject = producerQueue.poll();
		for (ConcurrentLinkedQueue<T> nextQueue : consumerQueues){
		    nextQueue.add(newObject);
		}
	    }
	}
    }

}
