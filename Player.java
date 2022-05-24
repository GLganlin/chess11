package view;

public class Player {
    private String name;
    private String account;
    private String password;
    private int winGame;
    private int wholeGame;
    private double winRate;

    public Player(String name, String account, String password){
        this.name = name;
        this.account = account;
        this.password = password;
    }

    public boolean checkIdentity(String account, String password){
        return this.account.equals(account)&&this.password.equals(password);
    }

    public void changeWinGame(){
        winGame++;
    }

    public void changeWholeGame(){
        wholeGame++;
    }

    public void evaluateWinRate(){
        winRate = (double)winGame / wholeGame;
    }

    public double getWinRate(){
        return winRate;
    }

    public String getName(){
        return name;
    }

    public int getWinGame(){
        return winGame;
    }
}

