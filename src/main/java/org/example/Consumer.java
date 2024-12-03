package org.example;

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
                Thread.sleep(30 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}