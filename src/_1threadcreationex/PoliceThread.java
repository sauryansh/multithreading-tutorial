package _1threadcreationex;

public class PoliceThread extends Thread{
    @Override
    public void run() {
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Game over for you hackers!");
        System.exit(0);
    }
}
