package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    private static final int CHESSBOARD_SIZE = 8;
    private static final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private static ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public List<List> steps = new ArrayList<>();
    private ChessGameFrame chessGameFrame;
    TestThread testThread = new TestThread();

    public Chessboard(int width, int height, ChessGameFrame chessGameFrame) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        this.chessGameFrame = chessGameFrame;
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
//        ChessGameFrame.changeCurrentPlayer();
    }
    public List<String> getLastStep(){
        if(steps.size()>=2){
            List<String> lastStep = steps.get(steps.size() - 2);
            steps.remove(steps.size() - 1);
            return lastStep;
        }
        else return null;
    }

    public void forUndo(){
        if(clickController.singleStep.size()>=1){
            clickController.singleStep.remove(clickController.singleStep.size()-1);
        }
    }

    public void storeStep(){
        ArrayList<String> storedChessboard = new ArrayList<>();
        String[] result = {"","","","","","","",""};
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 8;j++){
                System.out.println("chessComponents[i][j].getName1()" + chessComponents[i][j].getName1());
                switch(chessComponents[i][j].getName1()){
                    case 'R' :
                        result[i]+="R";
                        break;
                    case 'r' :
                        result[i]+="r";
                        break;
                    case 'N' :
                        result[i]+="N";
                        break;
                    case 'n' :
                        result[i]+="n";
                        break;
                    case 'B' :
                        result[i]+="B";
                        break;
                    case 'b' :
                        result[i]+="b";
                        break;
                    case 'K' :
                        result[i]+="K";
                        break;
                    case 'k' :
                        result[i]+="k";
                        break;
                    case 'Q' :
                        result[i]+="Q";
                        break;
                    case 'q' :
                        result[i]+="q";
                        break;
                    case 'P' :
                        result[i]+="P";
                        break;
                    case 'p' :
                        result[i]+="p";
                        break;
                    default :
                        result[i]+="_";
                }
            }
            System.out.println("result[i]" + result[i]);
            storedChessboard.add(result[i]);
        }
        if(getCurrentColor() == ChessColor.WHITE){
            storedChessboard.add("w");
        }else{
            storedChessboard.add("b");
        }
        steps.add(storedChessboard);
    }

    public String stepToString(){
        List<String> step = steps.get(steps.size() - 1);
        String result = "";
        for(int i = 0;i < step.size()-1;i++){
            result = result + step.get(i) + "\n";
        }
        if(getCurrentColor() == ChessColor.WHITE){
            result+="w";
        }else{
            result+="b";
        }
        return result;
    }

    public static ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
        chessComponent.repaint();
        //storeStep();
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) throws MalformedURLException {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            if(chess2.getName1() == 'K'){
                chessGameFrame.addWhiteWin();
                //chessGameFrame.startNewGame();
            }
            if(chess2.getName1() == 'k'){
                chessGameFrame.addBlackWin();
                //chessGameFrame.startNewGame();
            }
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE, '_'));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        //swapColor();
        ChessGameFrame.changeCurrentPlayer();
        chess1.repaint();
        chess2.repaint();
        storeStep();
        Music.playmovemusic();
        ChessGameFrame.newTime();
//        for (int i = 0; i < ; i++) {
//
//        }
    }
    public void eatPassingPawn(ChessComponent chess1, ChessComponent chess2) throws MalformedURLException {
        remove(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()]);
        add(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()] = new EmptySlotComponent(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getChessboardPoint(),chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getLocation(),clickController, CHESS_SIZE, '_'));
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        chess1.repaint();
        chess2.repaint();
        storeStep();
        Music.playmovemusic();
        ChessGameFrame.newTime();
    }

    public void swapRookKing(ChessComponent chess1, ChessComponent chess2) throws MalformedURLException {
        if(Math.abs(chess1.getChessboardPoint().getY()-chess2.getChessboardPoint().getY())==3){
            if(chess1 instanceof KingChessComponent){
                ChessComponent kingTo = chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY()+2];
                ChessComponent rookTo = chessComponents[chess2.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()-2];
                chess1.swapLocation(kingTo);
                int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess1;
                int row2 = kingTo.getChessboardPoint().getX(), col2 = kingTo.getChessboardPoint().getY();
                chessComponents[row2][col2] = kingTo;
                chess2.swapLocation(rookTo);
                int row11 = chess2.getChessboardPoint().getX(), col11 = chess2.getChessboardPoint().getY();
                chessComponents[row11][col11] = chess2;
                int row21 = rookTo.getChessboardPoint().getX(), col21 = rookTo.getChessboardPoint().getY();
                chessComponents[row21][col21] = rookTo;
                chess1.repaint();
                chess2.repaint();
                storeStep();
                Music.playmovemusic();
                ChessGameFrame.newTime();
            }
            else if(chess2 instanceof KingChessComponent){
                ChessComponent kingTo = chessComponents[chess2.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()+2];
                ChessComponent rookTo = chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY()-2];
                chess2.swapLocation(kingTo);
                int row1 = chess2.getChessboardPoint().getX(), col1 = chess2.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess2;
                int row2 = kingTo.getChessboardPoint().getX(), col2 = kingTo.getChessboardPoint().getY();
                chessComponents[row2][col2] = kingTo;
                chess1.swapLocation(rookTo);
                int row11 = chess1.getChessboardPoint().getX(), col11 = chess1.getChessboardPoint().getY();
                chessComponents[row11][col11] = chess1;
                int row21 = rookTo.getChessboardPoint().getX(), col21 = rookTo.getChessboardPoint().getY();
                chessComponents[row21][col21] = rookTo;
                chess1.repaint();
                chess2.repaint();
                storeStep();
                Music.playmovemusic();
                ChessGameFrame.newTime();
            }
        }
        else if(Math.abs(chess1.getChessboardPoint().getY()-chess2.getChessboardPoint().getY())==4){
            if(chess1 instanceof KingChessComponent){
                ChessComponent kingTo = chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY()-2];
                ChessComponent rookTo = chessComponents[chess2.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()+3];
                chess1.swapLocation(kingTo);
                int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess1;
                int row2 = kingTo.getChessboardPoint().getX(), col2 = kingTo.getChessboardPoint().getY();
                chessComponents[row2][col2] = kingTo;
                chess2.swapLocation(rookTo);
                int row11 = chess2.getChessboardPoint().getX(), col11 = chess2.getChessboardPoint().getY();
                chessComponents[row11][col11] = chess2;
                int row21 = rookTo.getChessboardPoint().getX(), col21 = rookTo.getChessboardPoint().getY();
                chessComponents[row21][col21] = rookTo;
                chess1.repaint();
                chess2.repaint();
                storeStep();
                Music.playmovemusic();
                ChessGameFrame.newTime();
            }
            else if(chess2 instanceof KingChessComponent){
                ChessComponent kingTo = chessComponents[chess2.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()-2];
                ChessComponent rookTo = chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY()+3];
                chess2.swapLocation(kingTo);
                int row1 = chess2.getChessboardPoint().getX(), col1 = chess2.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess2;
                int row2 = kingTo.getChessboardPoint().getX(), col2 = kingTo.getChessboardPoint().getY();
                chessComponents[row2][col2] = kingTo;
                chess1.swapLocation(rookTo);
                int row11 = chess1.getChessboardPoint().getX(), col11 = chess1.getChessboardPoint().getY();
                chessComponents[row11][col11] = chess1;
                int row21 = rookTo.getChessboardPoint().getX(), col21 = rookTo.getChessboardPoint().getY();
                chessComponents[row21][col21] = rookTo;
                chess1.repaint();
                chess2.repaint();
                storeStep();
                Music.playmovemusic();
                ChessGameFrame.newTime();
            }
        }
    }

    public void promote(ChessComponent p1,ChessComponent p2){
        if (!(p2 instanceof EmptySlotComponent)) {
            remove(p2);
            add(p2 = new EmptySlotComponent(p2.getChessboardPoint(), p2.getLocation(), clickController, CHESS_SIZE, '_'));
        }
        p1.swapLocation(p2);
        int row1 = p1.getChessboardPoint().getX(), col1 = p1.getChessboardPoint().getY();
        chessComponents[row1][col1] = p1;
        int row2 = p2.getChessboardPoint().getX(), col2 = p2.getChessboardPoint().getY();
        chessComponents[row2][col2] = p2;
        //
        JFrame f = new JFrame();
        f.setLayout(new FlowLayout());
        f.setSize(300,300);
        Dimension a = new Dimension(100,100);
        JButton queen = new JButton();
        JButton bishop = new JButton();
        JButton knight = new JButton();
        JButton rook = new JButton();
        ImageIcon blackBishop = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\bishop-black.png");
        ImageIcon whiteBishop = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\bishop-white.png");
        ImageIcon blackKnight = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\knight-black.png");
        ImageIcon whiteKnight = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\knight-white.png");
        ImageIcon blackQueen = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\queen-black.png");
        ImageIcon whiteQueen = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\queen-white.png");
        ImageIcon whiteRook = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\rook-white.png");
        ImageIcon blackRook = new ImageIcon("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\rook-black.png");


        if(p1.getChessColor() == ChessColor.WHITE){
            queen.setIcon(whiteQueen);
            bishop.setIcon(whiteBishop);
            knight.setIcon(whiteKnight);
            rook.setIcon(whiteRook);
        }
        if(p1.getChessColor() == ChessColor.BLACK){
            queen.setIcon(blackQueen);
            bishop.setIcon(blackBishop);
            knight.setIcon(blackKnight);
            rook.setIcon(blackRook);
        }

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(queen);
        buttons.add(bishop);
        buttons.add(knight);
        buttons.add(rook);
        for (int i=0;i<4;i++){
            int x = i;
            buttons.get(x).setPreferredSize(a);
            buttons.get(x).setBorder(BorderFactory.createLineBorder(Color.BLACK));
            buttons.get(x).addActionListener(e -> {
                if(e.getSource() == queen){
                    if(queen.getIcon() == whiteQueen){
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initQueenOnBoard(row1,col1,ChessColor.WHITE,'q');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                    else{
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initQueenOnBoard(row1,col1,ChessColor.BLACK,'q');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                }
                else if(e.getSource() == bishop){
                    if(bishop.getIcon() == whiteBishop){
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initBishopOnBoard(row1,col1,ChessColor.WHITE,'b');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                    else{
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initBishopOnBoard(row1,col1,ChessColor.BLACK,'b');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                }
                else if(e.getSource() == knight){
                    if(knight.getIcon() == whiteKnight){
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initKnightOnBoard(row1,col1,ChessColor.WHITE,'n');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                    else{
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initKnightOnBoard(row1,col1,ChessColor.BLACK,'N');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                }
                else {
                    if(rook.getIcon() == whiteRook){
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initRookOnBoard(row1,col1,ChessColor.WHITE,'r');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                    else{
                        int userOption = JOptionPane.showConfirmDialog(null,"Are you sure ?","NOTICE", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(userOption == JOptionPane.YES_NO_OPTION){
                            initRookOnBoard(row1,col1,ChessColor.BLACK,'R');
                            f.dispose();
                        }
                        else {
                            System.out.println("重新选择");
                        }
                    }
                }
            });
        }
        f.add(queen);
        f.add(bishop);
        f.add(knight);
        f.add(rook);
        f.setVisible(true);
        storeStep();
    }

    public ChessboardPoint getKingPoint(ChessColor color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(color == ChessColor.WHITE) {
                    if (chessComponents[i][j].getName1() == 'k'){
                        return new ChessboardPoint(i,j);
                    }
                }else{
                    if (chessComponents[i][j].getName1() == 'K'){
                        return new ChessboardPoint(i,j);
                    }
                }
            }
        }
        return null;
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE, '_'));
            }
        }
    }

    public static void swapColor() {
        currentColor = currentColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }



    private void initRookOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initKingOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initQueenOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initKnightOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initBishopOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initPawnOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chessData.get(i).charAt(j)) {
                    case 'R':
                        initRookOnBoard(i, j, ChessColor.BLACK, 'R');
                        break;
                    case 'r':
                        initRookOnBoard(i, j, ChessColor.WHITE, 'r');
                        break;
                    case 'N':
                        initKnightOnBoard(i, j, ChessColor.BLACK, 'N');
                        break;
                    case 'n':
                        initKnightOnBoard(i, j, ChessColor.WHITE, 'n');
                        break;
                    case 'B':
                        initBishopOnBoard(i, j, ChessColor.BLACK, 'B');
                        break;
                    case 'b':
                        initBishopOnBoard(i, j, ChessColor.WHITE, 'b');
                        break;
                    case 'Q':
                        initQueenOnBoard(i, j, ChessColor.BLACK, 'Q');
                        break;
                    case 'q':
                        initQueenOnBoard(i, j, ChessColor.WHITE, 'q');
                        break;
                    case 'K':
                        initKingOnBoard(i, j, ChessColor.BLACK, 'K');
                        break;
                    case 'k':
                        initKingOnBoard(i, j, ChessColor.WHITE, 'k');
                        break;
                    case 'P':
                        initPawnOnBoard(i, j, ChessColor.BLACK, 'P');
                        break;
                    case 'p':
                        initPawnOnBoard(i, j, ChessColor.WHITE, 'p');
                        break;
                }
            }
        }
        if (chessData.get(8).charAt(0) == 'w') {
            currentColor = ChessColor.WHITE;
        } else {
            currentColor = ChessColor.BLACK;
        }
        ChessGameFrame.changeCurrentPlayer();
        storeStep();
        ChessGameFrame.second = 60;
        testThread.start();
    }

    public void loadUndoGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chessData.get(i).charAt(j)) {
                    case 'R':
                        initRookOnBoard(i, j, ChessColor.BLACK, 'R');
                        break;
                    case 'r':
                        initRookOnBoard(i, j, ChessColor.WHITE, 'r');
                        break;
                    case 'N':
                        initKnightOnBoard(i, j, ChessColor.BLACK, 'N');
                        break;
                    case 'n':
                        initKnightOnBoard(i, j, ChessColor.WHITE, 'n');
                        break;
                    case 'B':
                        initBishopOnBoard(i, j, ChessColor.BLACK, 'B');
                        break;
                    case 'b':
                        initBishopOnBoard(i, j, ChessColor.WHITE, 'b');
                        break;
                    case 'Q':
                        initQueenOnBoard(i, j, ChessColor.BLACK, 'Q');
                        break;
                    case 'q':
                        initQueenOnBoard(i, j, ChessColor.WHITE, 'q');
                        break;
                    case 'K':
                        initKingOnBoard(i, j, ChessColor.BLACK, 'K');
                        break;
                    case 'k':
                        initKingOnBoard(i, j, ChessColor.WHITE, 'k');
                        break;
                    case 'P':
                        initPawnOnBoard(i, j, ChessColor.BLACK, 'P');
                        break;
                    case 'p':
                        initPawnOnBoard(i, j, ChessColor.WHITE, 'p');
                        break;
                }
            }
        }
        swapColor();
        ChessGameFrame.changeCurrentPlayer();
        ChessGameFrame.second = 60;
        testThread.start();
    }
}
