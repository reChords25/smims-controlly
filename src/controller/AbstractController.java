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


    public abstract void disconnect();

    /* Getters and Setters */
    public abstract int getRJoystickX();
    public abstract int getRJoystickY();
    public abstract boolean getRJoystickButton();
    public abstract boolean getRPad();

    public abstract int getLJoystickX();
    public abstract int getLJoystickY();
    public abstract boolean getLJoystickButton();
    public abstract boolean getLPad();

    public abstract boolean getBtn1Pressed();




}