package model;

import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的王
 */

public class KingChessComponent extends ChessComponent {
    /**
     * 黑王和白王的图片，static使得其可以被所有王对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KING_WHITE;
    private static Image KING_BLACK;

    /**
     * 王棋子对象自身的图片，是上面两种中的一种
     */
    private Image kingImage;

    /**
     * 读取加载王棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\king-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定kingImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, char name) {
        super(chessboardPoint, location, color, listener, size, name);
        initiateKingImage(color);
    }


    /**
     * 王棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 王棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        ChessColor p1 = chessComponents[source.getX()][source.getY()].getChessColor();
        ChessColor p2 = chessComponents[destination.getX()][destination.getY()].getChessColor();
        int r1 = Math.min(source.getX(),destination.getX());
        int r2 = Math.max(source.getX(),destination.getX());
        int c1 = Math.min(source.getY(),destination.getY());
        int c2 = Math.max(source.getY(),destination.getY());
        if(sameDiagonal(source,destination)){
            if(r2 - r1 > 1){
                return false;
            }
            else {
                return p1 != p2;
            }
        }else if(source.getX() == destination.getX()){
            if(c2 - c1 > 1){
                return false;
            }
            else return p1 != p2;
        }else if(source.getY() == destination.getY()){
            if(r2 - r1 > 1){
                return false;
            }
            else return p1 != p2;
        } else {
            return false;
        }
    }

    @Override
    public List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponents) {
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> ans = new ArrayList<>();
        for(int i = 0;i<chessComponents.length;i++){
            for(int y = 0;y<chessComponents[i].length;y++){
                if(chessComponents[source.getX()][source.getY()].canMoveTo(chessComponents, chessComponents[i][y].getChessboardPoint())){
                    ans.add(new ChessboardPoint(i,y));
                }
            }
        }
        return ans;
    }

    @Override
    public ArrayList<ChessboardPoint> canMoveTo1() {
        ArrayList<ChessboardPoint> result = new ArrayList<>();
        int sourceX = getSource().getX();
        int sourceY = getSource().getY();
        if(sourceY + 1 < 8&&sourceY + 1 >= 0&& Chessboard.getChessComponents()[sourceX][sourceY + 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX,sourceY + 1));
        }
        if(sourceY - 1 < 8&&sourceY - 1 >= 0&&Chessboard.getChessComponents()[sourceX][sourceY - 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX,sourceY - 1));
        }
        if(sourceX + 1 < 8&&sourceX + 1 >= 0&&Chessboard.getChessComponents()[sourceX + 1][sourceY].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 1,sourceY));
        }
        if(sourceX - 1 < 8&&sourceX - 1 >= 0&&Chessboard.getChessComponents()[sourceX - 1][sourceY].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 1,sourceY));
        }
        if(sourceX + 1 < 8&&sourceX + 1 >= 0&&sourceY + 1 < 8&&sourceY + 1 >= 0&&Chessboard.getChessComponents()[sourceX + 1][sourceY + 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 1,sourceY + 1));
        }
        if(sourceX - 1 < 8&&sourceX - 1 >= 0&&sourceY + 1 < 8&&sourceY + 1 >= 0&&Chessboard.getChessComponents()[sourceX - 1][sourceY + 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 1,sourceY + 1));
        }
        if(sourceX - 1 < 8&&sourceX - 1 >= 0&&sourceY - 1 < 8&&sourceY - 1 >= 0&&Chessboard.getChessComponents()[sourceX - 1][sourceY - 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 1,sourceY - 1));
        }
        if(sourceX + 1 < 8&&sourceX + 1 >= 0&&sourceY - 1 < 8&&sourceY - 1 >= 0&&Chessboard.getChessComponents()[sourceX + 1][sourceY - 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 1,sourceY - 1));
        }
        return result;
    }

    public boolean sameDiagonal (ChessboardPoint p1 , ChessboardPoint p2){
        int r = Math.abs(p1.getX() - p2.getX());
        int c = Math.abs(p1.getY() - p2.getY());
        return r==c;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.PINK);
            g.fillRect(0, 0, getWidth() , getHeight());

        }
        if(isCanBeMoved()){
            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, getWidth() , getHeight());
        }
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
