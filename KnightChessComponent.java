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
import java.util.Objects;

/**
 * 这个类表示国际象棋里面的马
 */

public class KnightChessComponent extends ChessComponent {
    /**
     * 黑马和白马的图片，static使得其可以被所有马对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    /**
     * 马棋子对象自身的图片，是上面两种中的一种
     */
    private Image knightImage;

    /**
     * 读取加载马棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\knight-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定knightImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, char name) {
        super(chessboardPoint, location, color, listener, size, name);
        initiateKnightImage(color);
    }

    /**
     * 马棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 马棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int r = Math.abs(source.getX() - destination.getX());
        int c = Math.abs(source.getY() - destination.getY());
        if((r==1&&c==2)||(r==2&&c==1)){
            if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                return true;
            }
            else if(chessComponents[destination.getX()][destination.getY()].getChessColor()!=chessComponents[source.getX()][source.getY()].getChessColor()){
                return true;
            }
            else return false;
        }
        else return false;
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
        int sourceX = getSource().getX();
        int sourceY = getSource().getY();
        ArrayList<ChessboardPoint> result = new ArrayList<>();
        if(sourceX + 2 < 8&&sourceX + 2 >= 0&&sourceY + 1 < 8&&sourceY + 1 >= 0&& Chessboard.getChessComponents()[sourceX + 2][sourceY + 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 2,sourceY + 1));
        }
        if(sourceX + 2 < 8&&sourceX + 2 >= 0&&sourceY - 1 < 8&&sourceY - 1 >= 0&&Chessboard.getChessComponents()[sourceX + 2][sourceY - 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 2,sourceY - 1));
        }
        if(sourceX - 2 < 8&&sourceX - 2 >= 0&&sourceY + 1 < 8&&sourceY + 1 >= 0&&Chessboard.getChessComponents()[sourceX - 2][sourceY + 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 2,sourceY + 1));
        }
        if(sourceX - 2 < 8&&sourceX - 2 >= 0&&sourceY - 1 < 8&&sourceY - 1 >= 0&&Chessboard.getChessComponents()[sourceX - 2][sourceY - 1].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 2,sourceY - 1));
        }
        if(sourceX + 1 < 8&&sourceX + 1 >= 0&&sourceY + 2 < 8&&sourceY + 2 >= 0&&Chessboard.getChessComponents()[sourceX + 1][sourceY + 2].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 1,sourceY + 2));
        }
        if(sourceX + 1 < 8&&sourceX + 1 >= 0&&sourceY - 2 < 8&&sourceY - 2 >= 0&&Chessboard.getChessComponents()[sourceX + 1][sourceY - 2].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX + 1,sourceY - 2));
        }
        if(sourceX - 1 < 8&&sourceX - 1 >= 0&&sourceY + 2 < 8&&sourceY + 2 >= 0&&Chessboard.getChessComponents()[sourceX - 1][sourceY + 2].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 1,sourceY + 2));
        }
        if(sourceX - 1 < 8&&sourceX - 1 >= 0&&sourceY - 2 < 8&&sourceY - 2 >= 0&&Chessboard.getChessComponents()[sourceX - 1][sourceY - 2].getChessColor() != getChessColor()){
            result.add(new ChessboardPoint(sourceX - 1,sourceY - 2));
        }
        return result;
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

        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
