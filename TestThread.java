package view;

public class TestThread extends Thread {
    private ChessGameFrame chessGameFrame;

    public void run() {
        while (true) {
            try {
                sleep(1000);
                //这里可以写你自己要运行的逻辑代码
                ChessGameFrame.changeTime();
                System.out.println("一秒钟运行一次");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
