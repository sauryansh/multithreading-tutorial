package _2threadcordination;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadCoordinationJoin {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(111111110L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        List<FactorialThread> threads = new ArrayList<>();

        inputNumbers.forEach(in -> threads.add(new FactorialThread(in)));

        // Start all threads
        threads.forEach(Thread::start);

        for (FactorialThread thread : threads) {
            // Set a timeout of 2 seconds for joining each thread
            thread.join(2000);

            // If the thread is still alive after the timeout, interrupt it
            if (thread.isAlive()) {
                System.out.println("Factorial calculation for " + thread.getInputNumber() + " is taking too long. Interrupting...");
                thread.interrupt();
            }
        }

        for (FactorialThread thread : threads) {
            if (thread.isFinished()) {
                System.out.println("Factorial of " + thread.getInputNumber() + " is " + thread.getResult());
            } else {
                System.out.println("The calculation for " + thread.getInputNumber() + " was interrupted or is still in progress.");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private final long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            try {
                this.result = factorial(inputNumber);
                this.isFinished = true;
            } catch (InterruptedException e) {
                // Thread was interrupted, set isFinished to false
                this.isFinished = false;
            }
        }

        public BigInteger factorial(long n) throws InterruptedException {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                // Check for interruption and throw InterruptedException if interrupted
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }

        public long getInputNumber() {
            return inputNumber;
        }
    }
}

/**
 * Here, is an example where we want to calculate factorial for list of numbers, which is quite CPU intensive task, so we will be
 * delicate the task to thread which will compute the task in parallel
 *   Let see the code,
 *   In: Long number in the constructor and when computation complete it store the calculated value in "result" variable
 *   Also when computation is done it update the isFinished variable, so that when caller want to know if the calculation is done or not
 *   Main Thread: we want to capture all the result from all the factorial thread and print them all
 *
 *   List: for storing thread
 *   For each , in number we will create separate thread and assign value from the list to each thread.
 *   and then iterate over all the thread, and start the thread.
 *
 *   Factorial thread and main thread running concurrently
 *
 *
 * TBD:
 * import java.math.BigInteger;
 * import java.util.ArrayList;
 * import java.util.Arrays;
 * import java.util.List;
 * import java.util.concurrent.*;
 *
 * public class ThreadCoordinationFuture {
 *     public static void main(String[] args) throws InterruptedException, ExecutionException {
 *         List<Long> inputNumbers = Arrays.asList(111111110L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
 *         List<Future<BigInteger>> futures = new ArrayList<>();
 *
 *         ExecutorService executor = Executors.newFixedThreadPool(inputNumbers.size());
 *
 *         for (Long inputNumber : inputNumbers) {
 *             FactorialTask task = new FactorialTask(inputNumber);
 *             Future<BigInteger> future = executor.submit(task);
 *             futures.add(future);
 *         }
 *
 *         for (int i = 0; i < inputNumbers.size(); i++) {
 *             Long inputNumber = inputNumbers.get(i);
 *             Future<BigInteger> future = futures.get(i);
 *             try {
 *                 BigInteger result = future.get(2, TimeUnit.SECONDS);
 *                 System.out.println("Factorial of " + inputNumber + " is " + result);
 *             } catch (TimeoutException e) {
 *                 System.out.println("Factorial calculation for " + inputNumber + " is taking too long. Interrupting...");
 *                 future.cancel(true); // Interrupt the task
 *             }
 *         }
 *
 *         executor.shutdown();
 *     }
 *
 *     public static class FactorialTask implements Callable<BigInteger> {
 *         private final long inputNumber;
 *
 *         public FactorialTask(long inputNumber) {
 *             this.inputNumber = inputNumber;
 *         }
 *
 *         @Override
 *         public BigInteger call() throws InterruptedException {
 *             return factorial(inputNumber);
 *         }
 *
 *         private BigInteger factorial(long n) throws InterruptedException {
 *             BigInteger result = BigInteger.ONE;
 *             for (long i = n; i > 0; i--) {
 *                 if (Thread.currentThread().isInterrupted()) {
 *                     throw new InterruptedException();
 *                 }
 *                 result = result.multiply(BigInteger.valueOf(i));
 *             }
 *             return result;
 *         }
 *     }
 * }
 *
 *
 */
