package _1threadcreation;

public class _4ThreadInheritance {
    public static void main(String[] args){
        MyThread myThread = new MyThread();
        myThread.setName("my-first-child-thread-using-inheritance");
        myThread.start();
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("We are now in a new thread : '" + this.getName() + "'");
        }
    }
}