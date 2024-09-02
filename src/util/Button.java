package util;

import java.awt.Color;
import sas.Rectangle;
import sas.Sprite;
import sas.Text;

public class Button {
    Buttontest bt;

    public Button(int xPos, int yPos, int width, int height, String label, Color col) {
        this.bt = new Buttontest(xPos, yPos, width, height, label, col);
        (new Thread(this.bt)).start();
    }

    public boolean clicked() {
        return this.bt.clicked();
    }

    public void setHidden(boolean pHidden) {
        this.bt.setHidden(pHidden);
    }

    public boolean getHidden() {
        return this.bt.isHidden();
    }

    public void setActivated(boolean activated) {
        this.bt.setActivated(activated);
    }

    public boolean getActivated() {
        return this.bt.isActivated();
    }

    public String getButtonText() {
        return this.bt.getText();
    }

    class Buttontest implements Runnable {
        private Sprite but = new Sprite();
        private Rectangle rect;
        private Rectangle shadow;
        private Text buttontext;
        private boolean gedrueckt = false;
        private boolean activated;

        public Buttontest(int xPos, int yPos, int width, int height, String label, Color col) {
            this.shadow = new Rectangle(xPos - 1, yPos - 1, width + 3, height + 3, new Color(150, 150, 150));
            this.but.add(this.shadow);
            this.rect = new Rectangle(xPos, yPos, width, height, col);
            this.but.add(this.rect);
            double offset = height * 0.15;
            int fontHeight = (int) Math.round(height * 0.6);
            this.buttontext = new Text(xPos, yPos, label);
            this.buttontext.setFontSansSerif(true, fontHeight);
            this.buttontext.moveTo(xPos + (width - this.buttontext.getShapeWidth()) / 2.0, yPos + offset);
            this.but.add(this.buttontext);
            this.activated = true;
        }

        public void run() {
            while(true) {
                if (this.but.mousePressed() && this.activated && !Button.this.getHidden()) {
                    this.shadow.move(-1.0, -1.0);
                }

                if (this.but.mouseReleased() && this.activated && !Button.this.getHidden()) {
                    this.gedrueckt = true;
                    this.shadow.move(1.0, 1.0);
                }

                this.wait(1);
            }
        }

        public boolean clicked() {
            boolean click = this.gedrueckt;
            this.gedrueckt = false;
            return click;
        }

        public void setActivated(boolean active) {
            this.activated = active;
            if (this.activated) {
                this.buttontext.setFontColor(Color.BLACK);
            } else {
                this.buttontext.setFontColor(Color.LIGHT_GRAY);
            }

        }

        public boolean isActivated() {
            return this.activated;
        }

        public String getText() {
            return this.buttontext.getText();
        }

        public void setHidden(boolean hidden) {
            this.but.setHidden(hidden);
        }

        public boolean isHidden() {
            return this.but.getHidden();
        }

        private void wait(int millSec) {
            try {
                Thread.sleep((long)millSec);
            } catch (InterruptedException var3) {
            }

        }
    }
}
