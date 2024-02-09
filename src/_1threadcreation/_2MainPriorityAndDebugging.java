package _1threadcreation;

public class _2MainPriorityAndDebugging {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
           new Runnable(){
               @Override
               public void run() {
                   //Code that will run in a new thread.
                   System.out.println("We are now in a new thread : '" + Thread.currentThread().getName() + "'");
                   System.out.println("Current thread priority : " + Thread.currentThread().getPriority());
               }
           }
        ); //Thread object creation and it is empty by default
        thread.setName("my-first-child-thread"); //Set the name of the thread
        thread.setPriority(Thread.MAX_PRIORITY); //Set the priority of the thread
        System.out.println("We are in the main thread : '" + Thread.currentThread().getName() + "' before starting a new thread");
        thread.start(); //Start the thread
        System.out.println("We are in the main thread : '" + Thread.currentThread().getName() + "' after starting a new thread");

    }
}