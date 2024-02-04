package ldts.g0907.diplomacyfailed.game.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.Objects;

public class MusicPlayer {
    private Clip Music;

    public MusicPlayer() {
        this.Music = loadMusic();
    }

    private Clip loadMusic() throws NullPointerException{
        try {
            File musicFile = new File(Objects.requireNonNull(MusicPlayer.class.getResource("/sound/boom4.wav")).getFile());
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            Clip musicClip = AudioSystem.getClip();
            musicClip.open(audioInput);

            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            return musicClip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startMusic() {
        Music.setMicrosecondPosition(0);
        Music.start();
    }
}
