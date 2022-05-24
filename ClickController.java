package controller;


import model.*;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public List<String> singleStep = new ArrayList<String>();
//    private ChessGameFrame button;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void storeSingleStep(ChessComponent p1, ChessComponent p2) {
        StringBuilder string1 = new StringBuilder();
        string1.append(p1.getName1());
        string1.append(p2.getName1());
        string1.append(p1.getChessboardPoint().getX());
        string1.append(p1.getChessboardPoint().getY());
        string1.append(p2.getChessboardPoint().getX());
        string1.append(p2.getChessboardPoint().getY());
        singleStep.add(string1.toString());
    }

    public String getLastSingleStep() {
        return singleStep.get(singleStep.size() - 1);
    }

    public void onClick(ChessComponent chessComponent) throws MalformedURLException {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
                for(ChessboardPoint p : first.getCanMovePoints(Chessboard.getChessComponents())){
                    if(Chessboard.getChessComponents()[p.getX()][p.getY()].getChessColor() != first.getChessColor()){
                        Chessboard.getChessComponents()[p.getX()][p.getY()].setCanBeMoved(true);
                        Chessboard.getChessComponents()[p.getX()][p.getY()].repaint();
                    }
                }
                if(first.getChessboardPoint().getX()==3 || first.getChessboardPoint().getX()==4){
                    if(first.getChessboardPoint().getY()-1>=0&&first.getChessboardPoint().getY()+1<8){
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()-1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()-1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()-1].repaint();
                        }
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()+1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()+1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()+1].repaint();
                        }
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()-1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()-1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()-1].repaint();
                        }
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()+1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()+1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()+1].repaint();
                        }
                    }
                    else if(first.getChessboardPoint().getY()-1==-1){
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()+1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()+1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()+1].repaint();
                        }
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()+1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()+1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()+1].repaint();
                        }
                    }
                    else {
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()-1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()-1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()+1][first.getChessboardPoint().getY()-1].repaint();
                        }
                        if(pawnSpecialWay1(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()-1])){
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()-1].setCanBeMoved(true);
                            Chessboard.getChessComponents()[first.getChessboardPoint().getX()-1][first.getChessboardPoint().getY()-1].repaint();
                        }
                    }
                }
                if(first instanceof RookChessComponent){
                    if(KingSpecialWay(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()][4])){
                        Chessboard.getChessComponents()[first.getChessboardPoint().getX()][4].setCanBeMoved(true);
                        Chessboard.getChessComponents()[first.getChessboardPoint().getX()][4].repaint();
                    }
                }
                if(first instanceof KingChessComponent){
                    if(KingSpecialWay(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()][0])){
                        Chessboard.getChessComponents()[first.getChessboardPoint().getX()][0].setCanBeMoved(true);
                        Chessboard.getChessComponents()[first.getChessboardPoint().getX()][0].repaint();
                    }
                    if(KingSpecialWay(first,Chessboard.getChessComponents()[first.getChessboardPoint().getX()][7])){
                        Chessboard.getChessComponents()[first.getChessboardPoint().getX()][7].setCanBeMoved(true);
                        Chessboard.getChessComponents()[first.getChessboardPoint().getX()][7].repaint();
                    }
                }
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                            Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                            Chessboard.getChessComponents()[i][j].repaint();
                    }
                }
            }
            else {
                if (first instanceof PawnChessComponent) {
                    if (!handleSecond(chessComponent)) {
                        if (pawnSpecialWay1(first,chessComponent)) {
                            storeSingleStep(first, chessComponent);
                            chessboard.eatPassingPawn(first, chessComponent);

                            Chessboard.swapColor();
                            ChessGameFrame.changeCurrentPlayer();
                            first.setSelected(false);
                            first = null;
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                                    Chessboard.getChessComponents()[i][j].repaint();
                                }
                            }
                            if(whiteKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.WHITE){
                                JOptionPane.showMessageDialog(null,"白方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                            if(blackKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.BLACK){
                                JOptionPane.showMessageDialog(null,"黑方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                    else {
                        if(pawnSpecialWay2(first)){
                            storeSingleStep(first,chessComponent);
                            if(chessComponent.getName1() == 'k'){
                                JOptionPane.showMessageDialog(null, "黑方获胜");
                            }
                            else if(chessComponent.getName1() == 'K'){
                                JOptionPane.showMessageDialog(null, "白方获胜");
                            }
                            else {
                                chessboard.promote(first,chessComponent);
                                Chessboard.swapColor();
                                ChessGameFrame.changeCurrentPlayer();
                                first.setSelected(false);
                                first = null;
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                                        Chessboard.getChessComponents()[i][j].repaint();
                                    }
                                }
                                if(whiteKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.WHITE){
                                    JOptionPane.showMessageDialog(null,"白方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                                }
                                if(blackKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.BLACK){
                                    JOptionPane.showMessageDialog(null,"黑方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                        else {
                            storeSingleStep(first,chessComponent);
                            chessboard.swapChessComponents(first, chessComponent);
                            Chessboard.swapColor();
                            ChessGameFrame.changeCurrentPlayer();
                            first.setSelected(false);
                            first = null;
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                                    Chessboard.getChessComponents()[i][j].repaint();
                                }
                            }
                            if(whiteKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.WHITE){
                                JOptionPane.showMessageDialog(null,"白方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                            if(blackKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.BLACK){
                                JOptionPane.showMessageDialog(null,"黑方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }
                else if(first instanceof KingChessComponent || first instanceof RookChessComponent){
                    if(KingSpecialWay(first,chessComponent)){
                        storeSingleStep(first,chessComponent);
                        chessboard.swapRookKing(first,chessComponent);
                        Chessboard.swapColor();
                        ChessGameFrame.changeCurrentPlayer();
                        first.setSelected(false);
                        first = null;
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                                Chessboard.getChessComponents()[i][j].repaint();
                            }
                        }
                        if(whiteKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.WHITE){
                            JOptionPane.showMessageDialog(null,"白方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        if(blackKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.BLACK){
                            JOptionPane.showMessageDialog(null,"黑方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else if(handleSecond(chessComponent)){
                        storeSingleStep(first,chessComponent);
                        //repaint in swap chess method.
                        chessboard.swapChessComponents(first, chessComponent);
                        Chessboard.swapColor();
                        ChessGameFrame.changeCurrentPlayer();
                        //chessboard.storeStep();

                        first.setSelected(false);
                        first = null;
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                                Chessboard.getChessComponents()[i][j].repaint();
                            }
                        }
                        if(whiteKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.WHITE){
                            JOptionPane.showMessageDialog(null,"白方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        if(blackKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.BLACK){
                            JOptionPane.showMessageDialog(null,"黑方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                else {
                    if (handleSecond(chessComponent)) {
                        storeSingleStep(first,chessComponent);
                        //repaint in swap chess method.
                        chessboard.swapChessComponents(first, chessComponent);
                        Chessboard.swapColor();
                        ChessGameFrame.changeCurrentPlayer();
                        //chessboard.storeStep();

                        first.setSelected(false);
                        first = null;
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                Chessboard.getChessComponents()[i][j].setCanBeMoved(false);
                                Chessboard.getChessComponents()[i][j].repaint();
                            }
                        }
                        if(whiteKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.WHITE){
                            JOptionPane.showMessageDialog(null,"白方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        if(blackKingWarning(Chessboard.getChessComponents())&& chessboard.getCurrentColor() == ChessColor.BLACK){
                            JOptionPane.showMessageDialog(null,"黑方王正在被将军!","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
    }

    private boolean handleFirst (ChessComponent chessComponent){
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }
    private boolean handleSecond (ChessComponent chessComponent){
        return chessComponent.getChessColor() != chessboard.getCurrentColor() && first.canMoveTo(Chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
    private boolean pawnSpecialWay1(ChessComponent first,ChessComponent chessComponent){
        boolean ans = false;
        if (first.getChessColor() == ChessColor.WHITE && first.getChessboardPoint().getX() == 3&&first instanceof PawnChessComponent) {
            if (chessComponent.getChessboardPoint().getX() - first.getChessboardPoint().getX() == -1 && Math.abs(chessComponent.getChessboardPoint().getY() - first.getChessboardPoint().getY()) == 1) {
                if (getLastSingleStep().charAt(0) == 'P' && getLastSingleStep().charAt(2) == '1' && getLastSingleStep().charAt(4) == '3' && getLastSingleStep().charAt(3) == chessComponent.getChessboardPoint().getY()+48) {
                    ans = true;
                }
            }
        }
        else if(first.getChessColor() == ChessColor.BLACK && first.getChessboardPoint().getX() == 4&& first instanceof PawnChessComponent){
            if (chessComponent.getChessboardPoint().getX() - first.getChessboardPoint().getX() == 1 && Math.abs(chessComponent.getChessboardPoint().getY() - first.getChessboardPoint().getY()) == 1) {
                if (getLastSingleStep().charAt(0) == 'p' && getLastSingleStep().charAt(2) == '6' && getLastSingleStep().charAt(4) == '4' && getLastSingleStep().charAt(3) == chessComponent.getChessboardPoint().getY()+48) {
                    ans = true;
                }
            }
        }
        return ans;
    }
    private boolean pawnSpecialWay2(ChessComponent first){
        boolean ans = false;
        if(first.getChessColor()==ChessColor.WHITE&&first.getChessboardPoint().getX() == 1){
            ans = true;
        }
        else if(first.getChessColor()==ChessColor.BLACK&&first.getChessboardPoint().getX() == 6){
            ans = true;
        }
        return ans;
    }
    private boolean KingSpecialWay(ChessComponent first,ChessComponent chessComponent){
        boolean result = false;
        if((first instanceof RookChessComponent && chessComponent instanceof KingChessComponent && first.getChessColor()==chessComponent.getChessColor())||(first instanceof KingChessComponent && chessComponent instanceof RookChessComponent && first.getChessColor() == chessComponent.getChessColor() )){
            if(first.getChessColor() == ChessColor.WHITE && first.getChessboardPoint().getX() == 7 && chessComponent.getChessboardPoint().getX()==7 &&(first.getChessboardPoint().getY() == 0||first.getChessboardPoint().getY() == 7||first.getChessboardPoint().getY() == 4)&&(chessComponent.getChessboardPoint().getY() == 0||chessComponent.getChessboardPoint().getY() == 7||chessComponent.getChessboardPoint().getY() == 4)){
                if(!whiteKingWarning(Chessboard.getChessComponents())){
                    int end = Math.max(first.getChessboardPoint().getY(),chessComponent.getChessboardPoint().getY());
                    int start = Math.min(first.getChessboardPoint().getY(),chessComponent.getChessboardPoint().getY());
                    boolean a = true;
                    boolean b = true;
                    for(int i= start+1;i< end;i++){
                        if(!(Chessboard.getChessComponents()[7][i] instanceof EmptySlotComponent)){
                            a = false;
                            break;
                        }
                        for(int y=0;y<8;y++){
                            for(int x = 0;x<8;x++){
                                if(Chessboard.getChessComponents()[y][x].canMoveTo(Chessboard.getChessComponents(),new ChessboardPoint(7,i))&&Chessboard.getChessComponents()[y][x].getChessColor() != first.getChessColor()){
                                    b = false;
                                    break;
                                }
                            }
                            if(!b){break;}
                        }
                    }
                    if(a&&b){
                        boolean ans = true;
                        for(String step : singleStep){
                            if(step.charAt(2)=='7'&&(step.charAt(3)== first.getChessboardPoint().getY()+48 || step.charAt(3)== chessComponent.getChessboardPoint().getY()+48 )){
                                if(step.charAt(4)=='7'&&(step.charAt(5)== first.getChessboardPoint().getY()+48 || step.charAt(5)== chessComponent.getChessboardPoint().getY()+48 )){
                                    ans = false;
                                    break;
                                }
                            }
                        }
                        if(ans){
                            result = true;
                        }
                    }
                }
            }
            else if(first.getChessColor() == ChessColor.BLACK && first.getChessboardPoint().getX() == 0 && chessComponent.getChessboardPoint().getX()==0 &&(first.getChessboardPoint().getY() == 0||first.getChessboardPoint().getY() == 7||first.getChessboardPoint().getY() == 4)&&(chessComponent.getChessboardPoint().getY() == 0||chessComponent.getChessboardPoint().getY() == 7||chessComponent.getChessboardPoint().getY() == 4)){
                if(!blackKingWarning(Chessboard.getChessComponents())){
                    int end = Math.max(first.getChessboardPoint().getY(),chessComponent.getChessboardPoint().getY());
                    int start = Math.min(first.getChessboardPoint().getY(),chessComponent.getChessboardPoint().getY());
                    boolean a = true;
                    boolean b = true;
                    for(int i= start+1;i< end;i++){
                        if(!(Chessboard.getChessComponents()[7][i] instanceof EmptySlotComponent)){
                            a = false;
                            break;
                        }
                        for(int y=0;y<8;y++){
                            for(int x = 0;x<8;x++){
                                if(Chessboard.getChessComponents()[y][x].canMoveTo(Chessboard.getChessComponents(),new ChessboardPoint(7,i))&&Chessboard.getChessComponents()[y][x].getChessColor() != first.getChessColor()){
                                    b = false;
                                    break;
                                }
                            }
                            if(!b){break;}
                        }
                    }
                    if(a&&b){
                        boolean ans = true;
                        for(String step : singleStep){
                            if(step.charAt(2)=='0'&&(step.charAt(3)== first.getChessboardPoint().getY()+48 || step.charAt(3)== chessComponent.getChessboardPoint().getY()+48 )){
                                if(step.charAt(4)=='0'&&(step.charAt(5)== first.getChessboardPoint().getY()+48 || step.charAt(5)== chessComponent.getChessboardPoint().getY()+48 )){
                                    ans = false;
                                    break;
                                }
                            }
                        }
                        if(ans){
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }
    private boolean whiteKingWarning(ChessComponent[][] chessComponents){
        boolean ans = false;
        for (int i =0;i<chessComponents.length;i++){
            for(int y=0;y<chessComponents[i].length;y++){
                if(chessComponents[i][y].getChessColor() == ChessColor.BLACK){
                    for(ChessboardPoint p : chessComponents[i][y].getCanMovePoints(chessComponents)){
                        if(chessComponents[p.getX()][p.getY()] instanceof KingChessComponent && chessComponents[p.getX()][p.getY()].getChessColor() == ChessColor.WHITE){
                            ans = true;
                        }
                        if(ans) break;
                    }
                }
                if(ans) break;
            }
            if(ans) break;
        }
        return ans;
    }
    private boolean blackKingWarning(ChessComponent[][] chessComponents){
        boolean ans = false;
        for (int i =0;i<chessComponents.length;i++){
            for(int y=0;y<chessComponents[i].length;y++){
                if(chessComponents[i][y].getChessColor() == ChessColor.WHITE){
                    for(ChessboardPoint p : chessComponents[i][y].getCanMovePoints(chessComponents)){
                        if(chessComponents[p.getX()][p.getY()] instanceof KingChessComponent && chessComponents[p.getX()][p.getY()].getChessColor() == ChessColor.BLACK){
                            ans = true;
                        }
                        if(ans) break;
                    }
                }
                if(ans) break;
            }
            if(ans) break;
        }
        return ans;
    }
}
