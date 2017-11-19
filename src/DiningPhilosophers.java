import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DiningPhilosophers {
    private static final int runSeconds = 15;
    private ForkHolder[] forks;
    private Philosopher[] philosophers;

    public DiningPhilosophers(int n) {
        forks = new ForkHolder[n];
        Arrays.setAll(forks, i -> new ForkHolder());

        philosophers = new Philosopher[n];
        Arrays.setAll(philosophers, i -> new Philosopher(i, forks[i], forks[(i + 1) % n]));


        // prevent dead-lock by reversing fork sequence

        philosophers[0] =    new Philosopher(0, forks[1], forks[0]);
        Arrays.stream(philosophers).forEach(CompletableFuture::runAsync);
    }

    public static void main(String[] args) {
        // Returns right away:

        DiningPhilosophers diningPhilosophers =new DiningPhilosophers(15);

        System.out.println(diningPhilosophers.philosophers.length);
        // Keeps main() from exiting:
        Arrays.stream(diningPhilosophers.philosophers).forEach(p -> System.out.println(p));

        ScheduledExecutorService sched = Executors.newScheduledThreadPool(1);
        sched.schedule(() -> {
            System.out.println("It's over ... no more eating!");
            sched.shutdown();
        }, runSeconds, SECONDS);


    }
}