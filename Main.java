import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static int LIMIT = 10000000;
    private static int NUM_THREADS = 1;
    private static ReentrantLock primeLock = new ReentrantLock();

    public static void main(String[] args) {
        // Get input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter limit: ");
        LIMIT = sc.nextInt();
        System.out.print("Enter number of threads: ");
        NUM_THREADS = sc.nextInt();
        sc.close();

        // Get start time
        long startTime = System.currentTimeMillis();

        // Gets [start, end] range for each thread
        List<Integer> primes = new ArrayList<Integer>();
        List<Thread> primeThreads = new ArrayList<Thread>();

        int numPerThread = (LIMIT - 1) / NUM_THREADS;
        int tempStart = 2;
        int tempEnd = tempStart + numPerThread - 1;

        for (int i = 0; i < NUM_THREADS; i++) {
            PrimeRunnable tempRunnable = new PrimeRunnable(tempStart, tempEnd, primes, primeLock);
                
            // Adds to list of Threads and starts newly created one
            Thread tempThread = new Thread(tempRunnable);
            primeThreads.add(tempThread);
            primeThreads.get(primeThreads.size() - 1).start();

            tempStart = tempEnd + 1;

            //Any excess goes to the final thread
            if (i == NUM_THREADS - 1)
                tempEnd = LIMIT; 
            else
                tempEnd += numPerThread;
        }

        // Checks if all threads are done
        for (Thread thread: primeThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Get stop time and total time
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.printf("%d primes were found.\n\n",primes.size());
        System.out.printf("LIMIT: %d, NUM_THREADS: %d\n", LIMIT, NUM_THREADS);
        System.out.print("TOTAL TIME: " + totalTime + " milliseconds");
    }
}
