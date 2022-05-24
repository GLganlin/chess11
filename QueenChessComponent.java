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
 * 这个类表示国际象棋里面的后
 */

public class QueenChessComponent extends ChessComponent {
    /**
     * 黑后和白后的图片，static使得其可以被所有后对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    /**
     * 后棋子对象自身的图片，是上面两种中的一种
     */
    private Image queenImage;

    /**
     * 读取加载后棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File(("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\queen-white.png")));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File(("C:\\Users\\甘琳\\Desktop\\ChessProject\\images\\queen-black.png")));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定queenImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, char name) {
        super(chessboardPoint, location, color, listener, size, name);
        initiateQueenImage(color);
    }

    /**
     * 后棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 后棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        boolean ans = true;
        ChessboardPoint source = getChessboardPoint();
        int r1 = Math.min(source.getX(),destination.getX());
        int r2 = Math.max(source.getX(),destination.getX());
        int c1 = Math.min(source.getY(),destination.getY());
        int c2 = Math.max(source.getY(),destination.getY());
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
        }
        else if(sameDiagonal(source,destination)){
            for (int i = r1; i <=r2; i++) {
                for (int j = c1; j <=c2; j++) {
                    if (chessComponents[i][j] != chessComponents[source.getX()][source.getY()]) {
                        if(chessComponents[i][j] != chessComponents[destination.getX()][destination.getY()]&&sameDiagonal(chessComponents[i][j].getChessboardPoint(),destination)){
                            if(!(chessComponents[i][j] instanceof EmptySlotComponent)){
                                ans = false;
                                break;
                            }
                        }
                    }
                }
                if(!ans)break;
            }
        }
        else ans = false;
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
        int i,j;
        if(getChessColor() == ChessColor.BLACK) {
            i = sourceX + 1;
            j = sourceY + 1;
            while(i < 8&&j < 8){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i++;
                j++;
            }
            i = sourceX + 1;
            j = sourceY - 1;
            while(i < 8&&j >= 0){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i++;
                j--;
            }
            i = sourceX - 1;
            j = sourceY - 1;
            while(i >= 0&&j >= 0){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i--;
                j--;
            }
            i = sourceX - 1;
            j = sourceY + 1;
            while(i >= 0&&j < 8){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i--;
                j++;
            }
            for (int a = sourceY + 1; a < 8; a++) {
                if (Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, a));
                } else if(Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(sourceX, a));
                    break;
                }else {
                    break;
                }
            }
            for (int a = sourceY - 1; a >= 0; a--) {
                if (Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, a));
                } else if(Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(sourceX, a));
                    break;
                }else {
                    break;
                }
            }
            for (int a = sourceX + 1; a < 8; a++) {
                if (Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(a, sourceY));
                } else if(Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(a, sourceY));
                    break;
                }else {
                    break;
                }
            }
            for (int a = sourceX - 1; a >= 0; a--) {
                if (Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(a, sourceY));
                } else if(Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.WHITE){
                    result.add(new ChessboardPoint(a, sourceY));
                    break;
                }else {
                    break;
                }
            }
        }else{
            i = sourceX + 1;
            j = sourceY + 1;
            while(i < 8&&j < 8){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i++;
                j++;
            }
            i = sourceX + 1;
            j = sourceY - 1;
            while(i < 8&&j >= 0){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i++;
                j--;
            }
            i = sourceX - 1;
            j = sourceY - 1;
            while(i >= 0&&j >= 0){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i--;
                j--;
            }
            i = sourceX - 1;
            j = sourceY + 1;
            while(i >= 0&&j < 8){
                if (Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(i, j));
                } else if(Chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(i, j));
                    break;
                }else {
                    break;
                }
                i--;
                j++;
            }
            for (int a = sourceY + 1; a < 8; a++) {
                if (Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, a));
                } else if(Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(sourceX, a));
                    break;
                }else {
                    break;
                }
            }
            for (int a = sourceY - 1; a >= 0; a--) {
                if (Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(sourceX, a));
                } else if(Chessboard.getChessComponents()[sourceX][a].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(sourceX, a));
                    break;
                }else {
                    break;
                }
            }
            for (int a = sourceX + 1; a < 8; a++) {
                if (Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(a, sourceY));
                } else if(Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(a, sourceY));
                    break;
                }else {
                    break;
                }
            }
            for (int a = sourceX - 1; a >= 0; a--) {
                if (Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.NONE) {
                    result.add(new ChessboardPoint(a, sourceY));
                } else if(Chessboard.getChessComponents()[a][sourceY].getChessColor() == ChessColor.BLACK){
                    result.add(new ChessboardPoint(a, sourceY));
                    break;
                }else {
                    break;
                }
            }
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

        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
