
public class Main {
    public static void main(String[] args) {

        /// we handle multiThread in this section
        Server server = new Server(5000);
        while (true) {
            server.receiveInformation();
        }

    }
}