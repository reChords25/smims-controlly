package controller;

import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ArduinoController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(ArduinoController.class);

    @Override
    protected void evalData() {

        int indexBegin = data.indexOf("#");
        if (indexBegin < 0) {
            data = "";
            return;
        }

        data = data.substring(indexBegin + 1);

        int indexEnd = data.indexOf("?");
        if (indexEnd < 0) {
            data = "";
            return;
        }

        data = data.substring(0, indexEnd);

        dataArray = data.split(",");

        for (String s : dataArray) {
            System.out.print(s + " ");
        }
    }

    @Override
    public void disconnect() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            log.error("Exception: ", e);
        }
    }

    @Override
    public double getLJoystickX() {
        return Double.parseDouble(dataArray[0]);
    }

    @Override
    public double getLJoystickY() {
        return Double.parseDouble(dataArray[1]);
    }

    @Override
    public double getRJoystickX() {
        return Double.parseDouble(dataArray[2]);
    }

    @Override
    public double getRJoystickY() {
        return Double.parseDouble(dataArray[3]);
    }

    @Override
    public boolean getBtn1Pressed() {
        return Integer.parseInt(dataArray[4]) == 1;
    }
}
