public class Request {
    private String requestCode;

    public Request (String requestCode) {
        this.requestCode = requestCode;
    }

    public void receiveRequest () {
        switch (requestCode) {
            case "100" :
                System.out.println("100");
                break;
            case "120" :
                System.out.println("120");
                break;
        }
    }

    // send changed information to client and update it
    public void refreshRequest () {

    }
}
