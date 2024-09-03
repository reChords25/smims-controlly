package controller;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractController implements SerialPortEventListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);
    protected String data = "";
    protected String[] dataArray;
    protected SerialPort serialPort;

    public AbstractController() {
        initSerialPort();
    }

    private void initSerialPort() {
        boolean isWorking = false;
        for (int i = 21; i > 1 && !isWorking; i--) {
            isWorking = true;
            try {
                serialPort = new SerialPort("COM" + i);
                serialPort.openPort();
                serialPort.setParams(9600, 8, 1, 0);
                serialPort.addEventListener(this);
            } catch (SerialPortException ex) {
                isWorking = false;
            }
        }
    }

    public void serialEvent(SerialPortEvent e) {
        try {
            data = data + serialPort.readString();
            data = data.replaceAll(" ", "");
            data = data.replaceAll("null", "");
            data = data.replaceAll("\n", "");
            data = data.replaceAll("\r", "");

            evalData();

        } catch (SerialPortException e2) {
            log.error("Exception: ", e2);
        }
    }

    protected abstract void evalData();

    //protected abstract void sendLights(int l1, int l2, int l3, int l4, int l5, int l6, int l7, int l8);

    //protected abstract void sendVibration(boolean on);

    public abstract void disconnect();

    /* Geters and Setters */
    public abstract double getLJoystickX();

    public abstract double getLJoystickY();

    public abstract double getRJoystickX();

    public abstract double getRJoystickY();

    public abstract boolean getBtn1Pressed();



}