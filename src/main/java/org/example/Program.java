package org.example;

public class Program
{
    public static void main(String[] args)
    {
        int Num = 25;
        ShoesStorage OrderList = new ShoesStorage();
        Thread producer = new Thread(new Producer(OrderList, Num));
        producer.start();
        for(int i = 1; i <= Num/5; i++)
        {
            Thread consumer = new Thread(new Consumer(OrderList, Num, i));
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