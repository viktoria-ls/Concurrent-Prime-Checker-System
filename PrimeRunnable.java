import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PrimeRunnable implements Runnable {
    int startInteger;
    int endInteger;
    List<Integer> primeList = new ArrayList<Integer>();
    Semaphore primeSemaphore;
    ReentrantLock primeLock;

    public PrimeRunnable(int start, int end, List<Integer> primeList, Semaphore primeSemaphore, ReentrantLock primeLock) {
        this.startInteger = start;
        this.endInteger = end;
        this.primeList = primeList;
        this.primeSemaphore = primeSemaphore;
        this.primeLock = primeLock;
    }

    public void run() {
        // Acquires permit from semaphore if available
        try {
            primeSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Checks if each integer in range is a prime
        // Locks the prime list if it is, adds to it, releases the lock
        for(int current_num = startInteger; current_num <= endInteger; current_num++) {
            if(check_prime(current_num)) {
                primeLock.lock();
                primeList.add(current_num);
                primeLock.unlock();
            }
        }
        
        // Returns permit meaning thread is done
        primeSemaphore.release();
    }


    // Checks if integer is a prime number
    public static boolean check_prime(int n) {
        for(int i = 2; i * i <= n; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
