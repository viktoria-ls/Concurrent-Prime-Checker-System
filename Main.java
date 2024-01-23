import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static int LIMIT = 10000000;
    private static int NUM_THREADS = 1;
    private static Semaphore primeSemaphore;
    private static ReentrantLock primeLock = new ReentrantLock();

    public static void main(String[] args) {
        // Get input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter limit: ");
        LIMIT = sc.nextInt();
        System.out.print("Enter number of threads: ");
        NUM_THREADS = sc.nextInt();
        sc.close();

        primeSemaphore = new Semaphore(NUM_THREADS);



        // Get start time`
        long startTime = System.currentTimeMillis();

        // Goes through all numbers up to LIMIT and adds to primes List
        List<Integer> primes = new ArrayList<Integer>();
        List<Thread> primeThreads = new ArrayList<Thread>();

        Integer numPerThread = LIMIT/NUM_THREADS;
        Integer excess = LIMIT%NUM_THREADS;
        Integer tempStart = 0;
        Integer tempEnd = 0;

        for(int i = 2, counter = 1;i <= LIMIT; i++,counter++) {
            if(counter == 1)
                tempStart = i;

            if(counter == numPerThread) {
                counter = 1;
                tempEnd = i;
                PrimeRunnable tempRunnable = new PrimeRunnable(tempStart, tempEnd, primes, primeSemaphore, primeLock);
                
                // If thread dies after instantiating new one with same name. We make list of threads and add this thread there.
                Thread tempThread = new Thread(tempRunnable);
                primeThreads.add(tempThread);
                primeThreads.get(primeThreads.size() - 1).start();
            }
        }
        
        for(int current_num = 2; current_num <= LIMIT; current_num++) {
            if(check_prime(current_num)) {
                primes.add(current_num);
            }
        }

        try {
            primeSemaphore.acquire(NUM_THREADS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get stop time and total time
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.printf("%d primes were found.\n",primes.size());
        System.out.printf("LIMIT: %d, NUM_THREADS: %d\n", LIMIT, NUM_THREADS);
        System.out.print("TOTAL TIME: " + totalTime + " milliseconds");
    }    

    public void run(List<Integer> primeSet) {
        System.out.print("Test");
    }

    /*
    This function checks if an integer n is prime.

    Parameters:
    n : int - integer to check

    Returns true if n is prime, and false otherwise.
    */
    public static boolean check_prime(int n) {
        for(int i = 2; i * i <= n; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
