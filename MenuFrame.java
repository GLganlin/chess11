package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;


public class MenuFrame extends JFrame implements ActionListener {
    private final int WIDTH;
    private final int HEIGTH;
    JPanel contentPane;
    JButton player1Button, player2Button, helpButton, exitButton;
    ImageIcon img = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\background11.jpg");
    private static ChessGameFrame chessGameFrame;
    private Music backgroundMusic;

    public MenuFrame(int width, int height) throws MalformedURLException {
        setTitle("Play Now !"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        contentPane = new JPanel();
        contentPane.setLayout(null);

        player1Button = new JButton("Player 1");
        player2Button = new JButton("Player 2");
        helpButton = new JButton("Help");
        exitButton = new JButton("Exit");

        addButton(player1Button, 449, 150, 180, 50);
        addButton(player2Button, 449, 250, 180, 50);
        addButton(helpButton, 449, 350, 180, 50);
        addButton(exitButton, 449, 450, 180, 50);
        setContentPane(contentPane);
        setSize(img.getIconWidth(), img.getIconHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setBackground();
        //Music.playmovemusic();
    }

    public void addButton(JButton b, int x, int y, int width, int hight) {
        b.setBounds(x, y, width, hight);
        b.setFont(new Font("Rockwell", Font.BOLD, 20));
        b.addActionListener(this);
        contentPane.add(b);
    }

    public void setBackground(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
        background.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == player2Button) {
            setVisible(false);
            try {
                chessGameFrame = new ChessGameFrame(1000,760);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chessGameFrame.setVisible(true);
            try {
                Music.playBackgroundMusic();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == player1Button) {
            JOptionPane.showMessageDialog(contentPane, "还没写捏");
        } else if (e.getSource() == helpButton) {
            JOptionPane.showMessageDialog(contentPane, "等我把规则复制一下");
        } else System.exit(0);
    }
}