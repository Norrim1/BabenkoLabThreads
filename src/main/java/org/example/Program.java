package org.example;


import java.util.*;

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


class Producer extends java.lang.Thread
{
    private final ShoesStorage OrderListExample;
    int n;
    public Producer(ShoesStorage OrderListExample, int n)
    {
        this.OrderListExample = OrderListExample;
        this.n = n;
    }
    @Override
    public void run(){

            for(int i = 1; i <= n; i++){
                try {
                    Random RandomNum = new Random();
                    int RandomPositionInList = RandomNum.nextInt(12);
                    int AmountOfProduct = RandomNum.nextInt(19) + 1; //Pair of shoes
                    String Name = OrderListExample.Product.get(RandomPositionInList);
                    OrderClass Order = new OrderClass(Name, AmountOfProduct);
                    OrderListExample.receiveOrder(Order);
                    Thread.sleep(5);
        } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }
}

class Consumer extends java.lang.Thread
{
    private final ShoesStorage OrderListExample;
    int n, TrackNum;
    public Consumer(ShoesStorage OrderListExample, int n, int TrackNum)
    {
        this.OrderListExample = OrderListExample;
        this.n = n/5;
        this.TrackNum = TrackNum;
    }
    @Override
    public void run() {
        for (int i = 1; i <= n; i++) {
            try {
                OrderListExample.fulfillOrder();
                System.out.println(" by thread "+TrackNum);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Program
{
    public static void main(String[] args)
    {
        int Num = 25;
        ShoesStorage OrderListExample = new ShoesStorage();
        Thread producer = new Thread(new Producer(OrderListExample, Num));
        producer.start();
        for(int i = 1; i <= Num/5; i++)
        {
            Thread consumer = new Thread(new Consumer(OrderListExample, Num, i));
            consumer.start();
            try {
                consumer.join(26);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}