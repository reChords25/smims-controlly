package controller;

import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sas.View;

//import javax.swing.text.View;


public class ArduinoController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(ArduinoController.class);

    public ArduinoController() {
        super();
    }

    private boolean containsHash(String testString) {
        for (int i = 0; i < testString.length(); i++) {
            char c = testString.charAt(i);
            if (c == '#') {
                return true;
            }
        }
        return false;
    }

    private void toArray(String pString) {
        System.out.println(pString);
        if (pString != null) {
            dataArray = pString.split(",");
        }
    }

    private String tmpData;

    @Override
    protected void evalData() {
        //System.out.println(data);
        if (!data.isEmpty()) {
            //System.out.println(data);


            if (data.charAt(0) == '#' && data.length() == 1) {
                //System.out.println("Blurry");
                toArray(tmpData);
            } else if (containsHash(data)) {

                //System.out.println(data);
                String[] tmpSplit = data.split("#");
                if (!tmpSplit[0].isEmpty()) {
                    toArray(tmpData + tmpSplit[0]);
                } else if (tmpSplit[1] != null) {
                    if (tmpSplit.length > 2) {
                        toArray(tmpSplit[1]);
                    } else {
                        toArray(tmpData + tmpSplit[1]);
                        tmpData = tmpSplit[1];
                    }
                } else if (tmpSplit.length > 2) {
                    if (tmpSplit[3] != null) {
                        toArray(tmpData);
                        tmpData = tmpSplit[3];
                    }
                    //toArray(tmpData);
                }


            } else {
                tmpData = tmpData + data;
            }
        }
    }

        @Override
        public void disconnect () {
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                log.error("Exception: ", e);
            }
        }

        @Override
        public int getLJoystickX () {
            return Integer.parseInt(dataArray[0]);
        }

        @Override
        public int getLJoystickY () {
            return Integer.parseInt(dataArray[1]);
        }

        @Override
        public boolean getLJoystickButton () {
            return false;
        }

        @Override
        public boolean getLPad () {
            return false;
        }

        @Override
        public int getRJoystickX () {
            return Integer.parseInt(dataArray[2]);
        }

        @Override
        public int getRJoystickY () {
            return Integer.parseInt(dataArray[3]);
        }

        @Override
        public boolean getRJoystickButton () {
            return false;
        }

        @Override
        public boolean getRPad () {
            return false;
        }

        private double parsTester (String testString){
            for (int i = 0; i < testString.length(); i++) {
                char c = testString.charAt(i);
                if (c == '#') {
                    return 0;
                }
            }
            return Double.parseDouble(testString);
        }

        public static void main (String[]args){
            //ArduinoController c = new ArduinoController();
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
