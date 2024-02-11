package _1threadcreation;

public class _1Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread( // Thread object creation, and it is empty by default, so we need to pass a Runnable object to it.
           new Runnable(){
               @Override
               public void run() {
                   //Code that will run in a new thread.
                   System.out.println("We are now in a new thread : '" + Thread.currentThread().getName() + "'");
               }
           }
        );

        System.out.println("We are in the main thread : '" + Thread.currentThread().getName() + "' before starting a new thread");
        thread.start(); //Only creation of a thread object does not start the thread, we need to call start() method to start the thread.
        //Start the thread, when we call start() method, it will call the run() method of the Runnable object that we passed to the Thread object.
        System.out.println("We are in the main thread : '" + Thread.currentThread().getName() + "' after starting a new thread");

        Thread.sleep(1000); //Sleep the main thread for 1 second
    }
}