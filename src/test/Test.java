package test;

public class Test {

    public static void main(String[] args) {

        final ArduinoTestController controller = new ArduinoTestController();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Closing connection now ...");
                controller.disconnect();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }).start();

    }

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */

    /* Constructors */

    /* Object Methods */

    /* Getters and Setters */

    /* Inner Classes */

}
