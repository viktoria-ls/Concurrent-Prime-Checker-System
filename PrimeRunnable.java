import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PrimeRunnable implements Runnable {
    Integer startInteger;
    Integer endInteger;
    List<Integer> primeList = new ArrayList<Integer>();
    Semaphore primeSemaphore;
    ReentrantLock primeLock;

    public PrimeRunnable(Integer start, Integer end, List<Integer> primeList, Semaphore primeSemaphore, ReentrantLock primeLock) {
        this.startInteger = start;
        this.endInteger = end;
        this.primeList = primeList;
        this.primeSemaphore = primeSemaphore;
        this.primeLock = primeLock;
    }

    public void run() {
        try {
            primeSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int current_num = startInteger; current_num <= endInteger; current_num++) {
            if(check_prime(current_num)) {
                primeLock.lock();
                primeList.add(current_num);
                primeLock.unlock();
            }
        }
        
        primeSemaphore.release();
    }

    public static boolean check_prime(int n) {
        for(int i = 2; i * i <= n; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
