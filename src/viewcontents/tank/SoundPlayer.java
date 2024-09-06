package viewcontents.tank;

import viewcontents.AbstractViewContent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    private Clip clip;
    private FloatControl volumeControl;

    public SoundPlayer(String soundFileName) {
        try {
            // Lade die Audiodatei
            File soundFile = new File(AbstractViewContent.PATH_TO_RESOURCES + soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Clip erstellen und öffnen
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Zugriff auf die Lautstärkeregelung
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Zurücksetzen zum Anfang
            clip.start(); // Abspielen
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Methode, um die Lautstärke anzupassen (-80.0f bis 6.0f)
    public void setVolume(float volume) {
        if (volumeControl != null) {
            // Lautstärke muss zwischen dem minimalen und maximalen Wert liegen
            volumeControl.setValue(volume);
        }
    }
}
