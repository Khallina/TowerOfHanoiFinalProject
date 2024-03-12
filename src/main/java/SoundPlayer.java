import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;

public class SoundPlayer {
    private String soundFilePath;

    public SoundPlayer(String soundFilePath) {
        this.soundFilePath = soundFilePath;
    }

    public void play() {
        new Thread(() -> {
            try {
                InputStream soundStream = getClass().getResourceAsStream(soundFilePath);
                if (soundStream == null) {
                    throw new RuntimeException("Resource not found: " + soundFilePath);
                }
                // Create a new AdvancedPlayer instance for each playback, otherwise it plays one sound and closes
                AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
                AdvancedPlayer player = new AdvancedPlayer(soundStream, audioDevice);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(javazoom.jl.player.advanced.PlaybackEvent evt) {
                        player.close();
                    }
                });
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
