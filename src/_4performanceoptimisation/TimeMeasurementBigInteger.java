package _4performanceoptimisation;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimeMeasurementBigInteger {
    public static void main(String[] args) throws InterruptedException {
        BigInteger[] numbers = generateArray(100000000);
        long singleThreadedDuration = measureSingleThreaded(numbers);
        long multiThreadedDuration = measureMultiThreaded(numbers);
        long timeImprovement = singleThreadedDuration - multiThreadedDuration;

        System.out.println("Single-threaded Duration: " + singleThreadedDuration + " ms");
        System.out.println("Multi-threaded Duration: " + multiThreadedDuration + " ms");
        System.out.println("Time Improvement: " + timeImprovement + " ms");
    }

    public static long measureSingleThreaded(BigInteger[] numbers) {
        long startTime = System.currentTimeMillis();
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger number : numbers) {
            sum = sum.add(number);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static long measureMultiThreaded(BigInteger[] numbers) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        int chunkSize = numbers.length / numOfThreads;
        int startIndex = 0;
        BigInteger[] sums = new BigInteger[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            int endIndex = (i == numOfThreads - 1) ? numbers.length : startIndex + chunkSize;
            BigInteger[] chunk = new BigInteger[endIndex - startIndex];
            System.arraycopy(numbers, startIndex, chunk, 0, chunk.length);
            startIndex = endIndex;

            executorService.execute(new SumTask(chunk, i, sums));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        BigInteger totalSum = BigInteger.ZERO;
        for (BigInteger sum : sums) {
            totalSum = totalSum.add(sum);
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    static class SumTask implements Runnable {
        private BigInteger[] numbers;
        private int index;
        private BigInteger[] sums;

        public SumTask(BigInteger[] numbers, int index, BigInteger[] sums) {
            this.numbers = numbers;
            this.index = index;
            this.sums = sums;
        }

        @Override
        public void run() {
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger number : numbers) {
                sum = sum.add(number);
            }
            sums[index] = sum;
        }
    }

    public static BigInteger[] generateArray(int size) {
        BigInteger[] array = new BigInteger[size];
        for (int i = 0; i < size; i++) {
            array[i] = BigInteger.valueOf(i + 1);
        }
        return array;
    }
}
