package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthToggleButtonUI;

@SuppressWarnings("serial")

public class FileChooser extends JFrame {
    //JButton open=null;
    String fileName = "";

    //@Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFileChooser jfc=new JFileChooser("D:\\chessProjrct");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        fileName = file.getPath();
//        if(file.isDirectory()){
//            System.out.println("文件夹:"+file.getAbsolutePath());
//        }else if(file.isFile()){
//            System.out.println("文件:"+file.getAbsolutePath());
//        }
        //System.out.println(jfc.getSelectedFile().getName());
    }

    public String getFileName(){
        System.out.println(fileName);
        return fileName;
    }
}
