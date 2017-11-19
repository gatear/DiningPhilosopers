import java.util.Random;

public class Philosopher implements Runnable {

    static final int maxWaitMs = 100;
    static Random rand = new Random();

    private final int position;
    private final ForkHolder left, right;



    int timesEaten = 0;
    public Philosopher(int position, ForkHolder left, ForkHolder right) {

        this.position = position;
        this.left = left;
        this.right = right;
    }

    private void sleep() {
        try { Thread.sleep( rand.nextInt(maxWaitMs) ); }
            catch (InterruptedException ex) {}
    }


    @Override
    public String toString() {
        return "P" + position;
    }

    @Override
    public void run() {
        while(true) {

            System.out.println("Thinking");   // [1]
            sleep();

            right.pickUp();
            left.pickUp();
            timesEaten ++;
            System.out.println(this + " eating " +timesEaten +" times now ...");

            right.putDown();
            left.putDown();
            System.out.println(this + " done eating!");
        }
    }
}