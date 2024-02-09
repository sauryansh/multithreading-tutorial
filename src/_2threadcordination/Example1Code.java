package _2threadcordination;

import java.io.IOException;

public class Example1Code {
    public static void main(String[] args) {
        Thread thread = new Thread(new WaitingForUserInput());
       // thread.setDaemon(true);
        thread.setName("InputWaitingThread");
        thread.start();
    }

    private static class WaitingForUserInput implements Runnable{
        @Override
        public void run(){
            try{
                while(true){
                    char input = (char) System.in.read();
                    if(input=='q'){
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("An exception was caught "+e);
            }
        }
    }
}
