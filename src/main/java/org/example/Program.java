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
        System.out.println("Fulfilled order: " + Order.name() + " " + Order.amount());
        notifyAll();
    }
}


class Producer extends java.lang.Thread
{
    private final ShoesStorage OrderListExample;
    private final ShoesStorage Product;
    public Producer(ShoesStorage OrderListExample, ShoesStorage Product)
    {
        this.OrderListExample = OrderListExample;
        this.Product = Product;
    }
    @Override
    public void run(){

            for(int i = 1; i <= 10; i++){
                try {
                    Random RandomNum = new Random();
                    int RandomPositionInList = RandomNum.nextInt(12);
                    int AmountOfProduct = RandomNum.nextInt(19) + 1; //Pair of shoes
                    String Name = Product.Product.get(RandomPositionInList);
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
    public Consumer(ShoesStorage OrderListExample)
    {
        this.OrderListExample = OrderListExample;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            try {
                OrderListExample.fulfillOrder();
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
        ShoesStorage OrderListExample = new ShoesStorage();
        Thread producer1 = new Thread(new Producer(OrderListExample, OrderListExample));
        Thread consumer1 = new Thread(new Consumer(OrderListExample));
        producer1.start();
        consumer1.start();
        try{
            producer1.join();
            consumer1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}