package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newFixedThreadPool;

class ShoesStorage {
    public ShoesStorage(int NumOfThreads)
    {
        Consumers = newFixedThreadPool(NumOfThreads/5);
    }
    ExecutorService Consumers;
    Queue<OrderClass> OrderList = new LinkedList<>();
    List<String> Product = Arrays.asList("sneakers", "boots", "dress shoes"
            , "sandals", "ankle boots"
            ,"ugg boots", "flip-flops", "moccasins",
            "high-tops", "oxfords", "wedges", "derby shoes");
    int capacity = 10;

    ExecutorService Producers = Executors.newSingleThreadExecutor();

    public synchronized void receiveOrder(OrderClass Order) throws InterruptedException {

        while (OrderList.size() == capacity) {
            System.out.println("Max capacity reached");
            wait();
        }
        OrderList.add(Order);
        System.out.println("Received " + Order.name()  + " " + Order.amount());
        notifyAll();
    }


    public synchronized void fulfillOrder() throws InterruptedException
    {
        while(OrderList.isEmpty())
        {
            System.out.println("Min capacity reached");
            wait();
        }
        OrderClass Order;
        Order = OrderList.poll();
        assert Order != null;
        System.out.print("Fulfilled order: " + Order.name() + " " + Order.amount());
        notifyAll();
    }
}