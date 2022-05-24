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
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image rookImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {
            ROOK_WHITE = ImageIO.read(new File(("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\rook-white.png")));
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = ImageIO.read(new File(("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\rook-black.png")));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,char name) {
        super(chessboardPoint, location, color, listener, size, name);
        initiateRookImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        boolean ans = true;
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {//只能直着走
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    ans = false;
                    break;
                }
            }
        }
        else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    ans = false;
                    break;
                }
            }
        } else { // Not on the same row or the same column.
            ans = false;
        }
        return ans;
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
        if(getChessColor() == ChessColor.BLACK) {
            for (int i = sourceY + 1; i < 8; i++) {
                if (Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, i));
                } else if(Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(sourceX, i));
                    break;
                }else {
                    break;
                }
            }
            for (int i = sourceY - 1; i >= 0; i--) {
                if (Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, i));
                } else if(Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(sourceX, i));
                    break;
                }else {
                    break;
                }
            }
            for (int i = sourceX + 1; i < 8; i++) {
                if (Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, sourceY));
                } else if(Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(i, sourceY));
                    break;
                }else {
                    break;
                }
            }
            for (int i = sourceX - 1; i >= 0; i--) {
                if (Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, sourceY));
                } else if(Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(i, sourceY));
                    break;
                }else {
                    break;
                }
            }
        }else{
            for (int i = sourceY + 1; i < 8; i++) {
                if (Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, i));
                } else if(Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(sourceX, i));
                    break;
                }else {
                    break;
                }
            }
            for (int i = sourceY - 1; i >= 0; i--) {
                if (Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, i));
                } else if(Chessboard.getChessComponents()[sourceX][i].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(sourceX, i));
                    break;
                }else {
                    break;
                }
            }
            for (int i = sourceX + 1; i < 8; i++) {
                if (Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, sourceY));
                } else if(Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(i, sourceY));
                    break;
                }else {
                    break;
                }
            }
            for (int i = sourceX - 1; i >= 0; i--) {
                if (Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, sourceY));
                } else if(Chessboard.getChessComponents()[i][sourceY].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(i, sourceY));
                    break;
                }else {
                    break;
                }
            }
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
        g.drawImage(rookImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
