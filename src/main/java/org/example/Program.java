package org.example;

public class Program
{
    public static void main(String[] args)
    {
        int Num = 25;
        ShoesStorage OrderListExample = new ShoesStorage(Num);
        OrderListExample.Producers.submit(new Producer(OrderListExample, Num));
        for(int i = 1; i <= Num/5; i++)
        {
            OrderListExample.Consumers.submit(new Consumer(OrderListExample, Num, i));
            }
        OrderListExample.Producers.shutdown();
        OrderListExample.Consumers.shutdown();
        }
    }
