import java.util.concurrent.atomic.AtomicInteger;

public class App {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Server server = new Server(5000);

        while (true) {
            while (atomicInteger.get() >= 16) {
                Thread.sleep(10);
            }

            atomicInteger.getAndIncrement();
            Thread thread = new Thread(server);
            thread.start();
        }
    }

    public static void decreaseConcurrencyLevel () { atomicInteger.getAndDecrement(); }
}
