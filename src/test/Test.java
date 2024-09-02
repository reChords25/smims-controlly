package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

        final ArduinoTestController controller = new ArduinoTestController();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Closing connection now ...");
                controller.disconnect();
            } catch (InterruptedException e) {
                log.error("Exception: ", e);
            }
        }).start();

    }
}
