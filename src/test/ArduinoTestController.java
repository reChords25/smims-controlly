package test;

import controller.AbstractController;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArduinoTestController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ArduinoTestController.class);

    @Override
    protected void evalData() {
        System.out.println(data);
        data = "";
    }

    @Override
    public void disconnect() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            log.error("e: ", e);
        }
    }


    @Override
    public double getJoystickX() {
        return 0;
    }

    @Override
    public double getJoystickY() {
        return 0;
    }

    @Override
    public boolean getA() {
        return false;
    }

    @Override
    public boolean getB() {
        return false;
    }

}
