import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;

public class SoundPlayer {
    private AdvancedPlayer player;

    public SoundPlayer(String soundFilePath) {
        try {
            InputStream soundStream = getClass().getResourceAsStream(soundFilePath);
            player = new AdvancedPlayer(soundStream);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // Release resources after playback is finished
                    player.close();
                }
            });
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (player != null) {
            new Thread(() -> {
                try {
                    player.play(1); // Start playback
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}