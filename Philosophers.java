
/*
 *PROBLEM STATEMENT: Implementation of Threading (Dyning Philosopher Problem) 
 */

import static java.lang.Thread.sleep;


class Table
{
    private boolean[] usedFork;
    public Table(int numberForks)
    {
        usedFork=new boolean[numberForks];
        for(int i=0;i<usedFork.length;i++)
            usedFork[i]=false;
    }
    private int left(int i)
    {
        return i;
    }
    private int right(int i)
    {
        return ((i+1)%usedFork.length);
    }
    
    public synchronized void takeForks(int place)
    {
        while(usedFork[left(place)]||usedFork[right(place)])
        {
            try{
                wait();
            }catch(InterruptedException e){}
        }
        usedFork[left(place)]=true;
        usedFork[right(place)]=true;
    }
    public synchronized void putBackForks(int place)
    {
        usedFork[left(place)]=false;
        usedFork[right(place)]=false;
        notifyAll();
    }
}
class Philosoph extends Thread {
    private Table Table;
    private int place;
    public Philosoph(Table Table,int place)
    {
        this.Table=Table;
        this.place=place;
        start();
    }
    public void run()
    {
        while(true)
        {
            thinking(place);
            Table.takeForks(place);
            eating(place);
            Table.putBackForks(place);            
        }
    }
    private void thinking(int place){
        System.out.println("Philosoph "+place+" Thinking.");
        try{
            sleep((int)(Math.random()*2000));
        }catch(InterruptedException e){}
    }
    
    private void eating(int place){
        System.out.println("Philosoph "+place+" Starts Eating.");
        try{
            sleep((int)(Math.random()*2000));
        }catch(InterruptedException e){}
        System.out.println("Philosoph "+place+" Finished Eating.");
    }
}
public class Philosophers{
    private static final int numberPhilosophers=5;
    private static final int numberForks=5;
    public static void main(String args[])
    {
        Table Table=new Table(numberForks);
        for(int i=0;i<numberPhilosophers;i++)
            new Philosoph(Table,i);
    }
}
