package controller;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public abstract class AbstractController implements SerialPortEventListener {

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */
    protected String data = "";
    protected SerialPort serialPort;

    /* Constructors */
    public AbstractController() {
        initSerialPort();
    }

    /* Object Methods */
    private void initSerialPort() {
        boolean funktioniert = false;
        for (int i = 21; i > 1 && !funktioniert; i--) {
            funktioniert = true;
            try {
                serialPort = new SerialPort("COM" + i);
                serialPort.openPort();
                serialPort.setParams(9600, 8, 1, 0);
                serialPort.addEventListener(this);
            } catch (SerialPortException ex) {
                funktioniert = false;
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

            werteDatenAus();

        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    protected abstract void werteDatenAus();

    public abstract void disconnect();


    /* Getters and Setters */
    public abstract double getJoystickX();

    public abstract double getJoystickY();

    public abstract boolean getA();

    public abstract boolean getB();

    // TODO: Mehr Methoden hinzufügen, z.B. für Buttons o.ä.

    /* Inner Classes */
}