package org.example;

class EvenNumber extends java.lang.Thread
{
    @Override
    public void run(){
        for(int i = 1; i <= 5; i++ ){
            System.out.println(i*2);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class OddNumber implements Runnable
{
       public void run(){
           for(int i = 1; i <= 5; i++)
           {
               System.out.println(i*2-1);
           }
       }
}

public class Program
{
    static EvenNumber ThreadOne;
    static OddNumber ThreadTwo;
    public static void main(String[] args)
    {
        ThreadOne = new EvenNumber();
        ThreadTwo = new OddNumber();
        Thread One = new Thread(ThreadOne);
        Thread Two = new Thread(ThreadTwo);
        One.start();
        Two.start();
    }
}
