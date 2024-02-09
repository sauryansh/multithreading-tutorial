package _1threadcreationex;

public class Vault {
    private final int password;
    public Vault(int password){
        this.password=password;
    }

    public boolean isCorrectPassword(int guess){
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.password==guess;
    }
}
