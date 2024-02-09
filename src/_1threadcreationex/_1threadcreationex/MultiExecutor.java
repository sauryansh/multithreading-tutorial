package _1threadcreationex._1threadcreationex;

import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {
    private final List<Runnable> tasks;

    public static void main(String[] args) {
        Runnable task1= new Runnable() {
            @Override
            public void run() {
                System.out.println("Task 1 executed by thread : " + Thread.currentThread().getName());
            }
        };

        Runnable task2= () -> System.out.println("Task 2 executed by thread : " + Thread.currentThread().getName());
        Runnable task3= () -> System.out.println("Task 3 executed by thread : " + Thread.currentThread().getName());
        Runnable task4= () -> System.out.println("Task 4 executed by thread : " + Thread.currentThread().getName());
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        MultiExecutor multiExecutor = new MultiExecutor(tasks);
        multiExecutor.executeAll();
    }

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll(){
        List<Thread> threads = new ArrayList<>(tasks.size());

        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
        }
        threads.forEach(Thread::start);
    }
}
