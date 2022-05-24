package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;//游戏的窗体有哪些性质
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private static GameController gameController;
    private static JLabel statusLabel;
    private static JLabel timeLabel;
    private ClickController controller;
    private int counter = 2;
    public static int second = 60;
    private Timer time;
    TestThread testThread = new TestThread();
    ImageIcon img1 = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\3.JPEG");
    ImageIcon img2 = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\4.JPEG");
    ImageIcon img = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\3.JPEG");
    private JLabel background;
    Chessboard chessboard;

    public ChessGameFrame(int width, int height) throws IOException {
        setTitle("Play Now !"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);//决定窗口的大小
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setBackground();
//        addChessboardImage();

        addChessboard();
//        addLabel();
//        addStringCurrentPlayer();
        addCurrentPlayerLabel();
        addHelloButton();
        addLoadButton();
        addSaveButton();
        addRestartButton();
        addUndoButton();
        addChangeBackgroundButton();
        addTimeLabel();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() throws IOException {
        //List<String> stringChessboard = Files.readAllLines(Paths.get("./testcase/move/game2.txt"));
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, this);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        chessboard.setOpaque(false);
        add(chessboard);

    }

    //    public void addChessboardImage(){
//        JLabel chessboardImage = new JLabel(img2);
//        this.getLayeredPane().add(chessboardImage,new Integer(Integer.MIN_VALUE));
//    }
//
    public void setBackground(){((JPanel)this.getContentPane()).setOpaque(false);
        background = new JLabel(img1);
        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
        background.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        //background.setOpaque(true);
    }
    //
//    public void changeBackground(){((JPanel)this.getContentPane()).setOpaque(false);
//        if(counter == 1){
////            this.getContentPane().remove(background);
//            background = new JLabel(img1);
//            this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
//            background.setBounds(0,0,img1.getIconWidth(),img1.getIconHeight());
//        }else if(counter == 2){
//            this.getContentPane().remove(background);
//            background = new JLabel(img2);
//            this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
//            background.setBounds(0,0,img2.getIconWidth(),img2.getIconHeight());
//        }else if(counter == 3){
//            this.getContentPane().remove(background);
//            background = new JLabel(img3);
//            this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
//            background.setBounds(0,0,img3.getIconWidth(),img3.getIconHeight());
//        }else{
//            this.getContentPane().remove(background);
//        }
//    }
//
    public void changeBackgroundTo1(){((JPanel)this.getContentPane()).setOpaque(false);
        background.setIcon(img1);
//        background = new JLabel(img1);
//        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
//        background.setBounds(0,0,img1.getIconWidth(),img1.getIconHeight());
    }
    public void changeBackgroundTo2(){((JPanel)this.getContentPane()).setOpaque(false);
        background.setIcon(img2);
//        background = null;
//        background = new JLabel(img2);
//        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
//        background.setBounds(0,0,img2.getIconWidth(),img2.getIconHeight());
    }

    /**
     * 在游戏面板中添加标签
     */
//    private void addLabel() {
//        JLabel statusLabel = new JLabel("Sample label");
//        statusLabel.setLocation(HEIGTH, HEIGTH / 50);
//        statusLabel.setSize(200, 60);
//        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));//设置字体
//        add(statusLabel);//创建文本、添加窗体都需要加add
//    }

    private void addTimeLabel() {
        timeLabel = new JLabel("");
        timeLabel.setLocation(HEIGTH, HEIGTH / 50);
        timeLabel.setSize(200, 60);
        timeLabel.setFont(new Font("Rockwell", Font.BOLD, 20));//设置字体
        add(timeLabel);//创建文本、添加窗体都需要加add
    }

    public static void changeTime(){
        second--;
        if(second <= 0){
            second = 60;
            Chessboard.swapColor();
            changeCurrentPlayer();
        }
        timeLabel.setText(String.valueOf(second));
    }

    public static void newTime(){
        second = 60;
    }

//    private void

    private void addStringCurrentPlayer() {
        JLabel statusLabel = new JLabel("CurrentPlayer: ");
        statusLabel.setLocation(HEIGTH, HEIGTH / 50 + 60);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));//设置字体
        add(statusLabel);//创建文本、添加窗体都需要加add
    }

    private void addCurrentPlayerLabel(){
        statusLabel = new JLabel("");
        //gameController.getChessboard().swapColor();
        statusLabel.setLocation(HEIGTH, HEIGTH / 50 + 120);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));//设置字体
        add(statusLabel);//创建文本、添加窗体都需要加add
    }

    public static void changeCurrentPlayer(){
        if(gameController.getChessboard().getCurrentColor() == ChessColor.WHITE) {
            statusLabel.setText("WHITE");
        }else{
            statusLabel.setText("BLACK");
        }
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Hello, world!");//展示消息框
        });
        button.setLocation(HEIGTH, HEIGTH / 50 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setOpaque(false);
        //button.setOpaque(false);
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 50 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = "";

            JFrame fileChooser = new FileChooser();
            ((FileChooser) fileChooser).actionPerformed(e);
            path = ((FileChooser) fileChooser).getFileName();
            //System.out.print(path);
//            path = JOptionPane.showInputDialog(this,"Input Path here");//另一种消息框
            if(Objects.equals(gameController.loadGameFromFile(path).get(0), "1")){
                JOptionPane.showMessageDialog(this, "101");//展示消息框
            }else if(Objects.equals(gameController.loadGameFromFile(path).get(0), "2")){
                JOptionPane.showMessageDialog(this, "102");//展示消息框
            }else if(Objects.equals(gameController.loadGameFromFile(path).get(0), "3")){
                JOptionPane.showMessageDialog(this, "103");//展示消息框
            }else if(Objects.equals(gameController.loadGameFromFile(path).get(0), "4")){
                JOptionPane.showMessageDialog(this, "104");//展示消息框
            }
        });
    }

    private void addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 50 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String name = JOptionPane.showInputDialog(this,"Input Name here");//另一种消息框
            gameController.saveChessboardToFile(name);
        });
    }

    private void addRestartButton() {
        JButton button = new JButton("StartNewGame");
        button.setLocation(HEIGTH, HEIGTH / 50 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("StartNewGame");
            String path = "D:\\chessProjrct\\new.txt";
            gameController.loadGameFromFile(path);
        });
    }

    public void startNewGame(){
        System.out.println("StartNewGame");
        String path = "D:\\chessProjrct\\new.txt";
        gameController.loadGameFromFile(path);
    }

    private void addUndoButton(){
        JButton button = new JButton("Undo");
        button.setLocation(HEIGTH, HEIGTH / 50 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click undo");
            if(gameController.getChessboard().steps.size() > 0) {
                chessboard.forUndo();
                gameController.getChessboard().loadUndoGame(gameController.getChessboard().getLastStep());
            }
        });
    }

    private void addChangeBackgroundButton(){
        JButton button = new JButton("Style");
        button.setLocation(HEIGTH, HEIGTH / 50 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click style");
            counter++;
            if(counter > 2){
                counter = 1;
            }
            if(counter == 1){
                changeBackgroundTo1();
            }
            if(counter == 2){
                changeBackgroundTo2();
            }
            System.out.println(counter);
            //changeBackground();
        });
    }

    public void addBlackWin(){
        JOptionPane.showMessageDialog(this, "黑方获胜");//展示消息框
    }
    public void addWhiteWin(){
        JOptionPane.showMessageDialog(this, "白方获胜");//展示消息框
    }
}
