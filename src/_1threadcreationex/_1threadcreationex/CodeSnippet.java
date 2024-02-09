package _1threadcreationex._1threadcreationex;

public class CodeSnippet {
    public static void main(String[] args) {
        Thread thread = new TaskThread1();
        thread.start();

        Thread thread1= new Thread(new Task2());
        thread1.start();
    }

    private static class TaskThread1 extends Thread {
        @Override
        public void run(){
            System.out.println("Hello from new thread");
        }
    }

    private static class Task2 implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello from new thread");
        }

    }
}
/**
 * Both are indeed correct, and is a matter of how you'd like to organize your code.
 * Some developers prefer to decouple the code and the threading functionality,
 * and prefer to implement a Runnable.
 * In other scenarios, developers prefer to encapsulate the threading functionality in one class
 * and therefore prefer to extend Thread. There is no right or wrong here
 */
