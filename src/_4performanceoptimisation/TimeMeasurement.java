package _4performanceoptimisation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimeMeasurement {
    public static void main(String[] args) throws InterruptedException {
        int[] numbers = generateArray(100000000);
        long singleThreadedDuration = measureSingleThreaded(numbers);
        long multiThreadedDuration = measureMultiThreaded(numbers);
        long timeImprovement = singleThreadedDuration - multiThreadedDuration;

        System.out.println("Single-threaded Duration: " + singleThreadedDuration + " ms");
        System.out.println("Multi-threaded Duration: " + multiThreadedDuration + " ms");
        System.out.println("Time Improvement: " + timeImprovement + " ms");
    }

    public static long measureSingleThreaded(int[] numbers) {
        long startTime = System.currentTimeMillis();
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static long measureMultiThreaded(int[] numbers) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        int chunkSize = numbers.length / numOfThreads;
        int startIndex = 0;
        int[] sums = new int[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            int endIndex = (i == numOfThreads - 1) ? numbers.length : startIndex + chunkSize;
            int[] chunk = new int[endIndex - startIndex];
            System.arraycopy(numbers, startIndex, chunk, 0, chunk.length);
            startIndex = endIndex;

            executorService.execute(new SumTask(chunk, i, sums));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        int totalSum = 0;
        for (int sum : sums) {
            totalSum += sum;
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    static class SumTask implements Runnable {
        private int[] numbers;
        private int index;
        private int[] sums;

        public SumTask(int[] numbers, int index, int[] sums) {
            this.numbers = numbers;
            this.index = index;
            this.sums = sums;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int number : numbers) {
                sum += number;
            }
            sums[index] = sum;
        }
    }

    public static int[] generateArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        return array;
    }
}
