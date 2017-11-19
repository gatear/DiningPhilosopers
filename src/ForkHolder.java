import java.util.concurrent.*;

public class ForkHolder {

    private static class Fork {}
    private Fork fork = new Fork();
    private BlockingQueue<Fork> holder = new ArrayBlockingQueue<>(1);

    public ForkHolder() { putDown(); }

    public void pickUp() {
        try {
            holder.take(); // The Queue blocks the thread if unavailable
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void putDown() {
        try {
            holder.put(fork);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
