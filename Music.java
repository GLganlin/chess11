package view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Music {
    public static void playmovemusic() throws MalformedURLException {
        File f= new File("C:\\Users\\甘琳\\Desktop\\ChessProject\\musics\\吃.wav");
        AudioClip ac = Applet.newAudioClip(f.toURL());
        ac.play();
    }

    public static void playBackgroundMusic() throws MalformedURLException {
        File f= new File("C:\\Users\\甘琳\\Desktop\\ChessProject\\musics\\背景1.wav");
        AudioClip ac = Applet.newAudioClip(f.toURL());
        ac.play();
    }
}
