package controller;
import sas.*;

import jssc.SerialPortException;

//import javax.swing.text.View;


public class Controller extends AbstractController {

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

        dataArray = null;

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
            System.err.println(e);
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
        if (Integer.parseInt(dataArray[4]) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        /*
        for (int i = 0; i < 100; i++) {
            try {
                c.sendVibration(true);
                Thread.sleep(10);
                c.sendVibration(false);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

       new Thread(() -> {
           try {

               Thread.sleep(2000);
           } catch (InterruptedException e) {
               System.err.println(e);
           }
           System.out.println("finished!");
       }).start();
        c.disconnect();
    */
    }
}
