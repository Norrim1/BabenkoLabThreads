package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class ShoesStorage {
    Queue<OrderClass> OrderListExample = new LinkedList<>();
    List<String> Product = Arrays.asList("sneakers", "boots", "dress shoes"
            , "sandals", "ankle boots"
            ,"ugg boots", "flip-flops", "moccasins",
            "high-tops", "oxfords", "wedges", "derby shoes");
    int capacity = 10;
    public synchronized void receiveOrder(OrderClass Order) throws InterruptedException {

        while (OrderListExample.size() == capacity) {
            System.out.println("Max capacity reached");
            wait();
        }
        OrderListExample.add(Order);
        System.out.println("Received " + Order.name()  + " " + Order.amount());
        notifyAll();
    }
    public synchronized void fulfillOrder() throws InterruptedException
    {
        while(OrderListExample.isEmpty())
        {
            System.out.println("Min capacity reached");
            wait();
        }
        OrderClass Order;
        Order = OrderListExample.poll();
        assert Order != null;
        System.out.print("Fulfilled order: " + Order.name() + " " + Order.amount());
        notifyAll();
    }
}