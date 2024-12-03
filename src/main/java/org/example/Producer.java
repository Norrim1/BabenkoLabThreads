package org.example;

import java.util.Random;

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