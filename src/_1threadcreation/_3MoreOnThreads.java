package _1threadcreation;

public class _3MoreOnThreads {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
           new Runnable(){
               @Override
               public void run() {
                    throw new RuntimeException("Intentional Exception");
               }
           }
        );

        thread.setName("my-first-child-with-uncaught-exception-handler");
         thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
             @Override
             public void uncaughtException(Thread t, Throwable e) {
                 System.out.println("A critical error happened in thread '" + t.getName() + "' the error is : " + e.getMessage());
             }
         });
         thread.start(); //Start the thread
    }
}