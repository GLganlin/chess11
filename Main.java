import view.MenuFrame;
import view.Music;

import javax.swing.*;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        SwingUtilities.invokeLater(() -> {//先不用管，表示我们会运行这两个程序
            MenuFrame mainFrame = null;
            try {
                mainFrame = new MenuFrame(1000, 760);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mainFrame.setVisible(true);
        });
    }
}
