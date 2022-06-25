import java.util.concurrent.atomic.AtomicInteger;

public class App {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Server server = new Server(5000);
        int i = 1;

        while (true) {
            while (atomicInteger.get() >= 16) {
                Thread.sleep(10);
            }

            atomicInteger.getAndIncrement();
            User user = new User();
            WorkSpace workSpace = new WorkSpace();
            server.join();
            server.start();
            //server.run();
        }
    }

    public static void decreaseConcurrencyLevel () { atomicInteger.getAndDecrement(); }
}
