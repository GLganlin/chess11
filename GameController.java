package controller;

import model.ChessColor;
import view.Chessboard;
import view.Music;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {
    private Chessboard chessboard;
    private static List<String> stringChessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            if(path.charAt(path.length() - 1) == 't'&&path.charAt(path.length() - 2) == 'x'&&path.charAt(path.length() - 3) == 't') {
                List<String> chessData = Files.readAllLines(Paths.get(path));
                int check = checkFile(chessData);
                if (check == 0) {
                    chessboard.initiateEmptyChessboard();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                    chessboard.loadGame(chessData);
                    stringChessboard = chessData;
                    return chessData;
                } else {
                    List<String> error = new ArrayList<>();
                    String errorType = String.valueOf(check);
                    error.add(errorType);
                    return error;
                }
            }else{
                List<String> error = new ArrayList<>();
                String errorType = "4";
                error.add(errorType);
                return error;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getStringChessboard(){
        return stringChessboard;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public void saveChessboardToFile(String title){
        String result = chessboard.stepToString();
        String address = "D:\\chessProjrct";
        try {
            // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 写入Txt文件 */
            File writename = new File(address);// 相对路径，如果没有则要建立一个新的output。txt文件
            if(!writename.exists()){
                writename.mkdirs();
            }
            writename = new File(address + "\\" + title + ".txt");// 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(result); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int checkFile(List<String> file){
        if(file.size() == 8&&!Objects.equals(file.get(file.size() - 1), "w") && !Objects.equals(file.get(file.size() - 1), "b")){
            return 3;
        }
        if(file.size() - 1 != 8&&(Objects.equals(file.get(file.size() - 1), "w") || Objects.equals(file.get(file.size() - 1), "b"))){
            return 1;
        }
        for (int i = 0; i < 8; i++) {
            if(file.get(i).length() != 8){
                return 1;
            }
            for (int j = 0; j < 8; j++) {
                switch(file.get(i).charAt(j)){
                    case 'P' :
                    case 'p' :
                    case 'R' :
                    case 'r' :
                    case 'N' :
                    case 'n' :
                    case 'B' :
                    case 'b' :
                    case 'Q' :
                    case 'q' :
                    case 'K' :
                    case 'k' :
                    case '_' :
                        continue;
                    default :
                        return 2;
                }
            }
        }
        if(file.get(8).charAt(0) != 'w' && file.get(8).charAt(0) != 'b') {
            return 2;
        }
        else{
            return 0;
        }
    }
}
