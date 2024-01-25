import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PrimeRunnable implements Runnable {
    int startInteger;
    int endInteger;
    List<Integer> primeList = new ArrayList<Integer>();
    ReadWriteLock primeLock;

    public PrimeRunnable(int start, int end, List<Integer> primeList, ReadWriteLock primeLock) {
        this.startInteger = start;
        this.endInteger = end;
        this.primeList = primeList;
        this.primeLock = primeLock;
    }

    public void run() {
        // Checks if each integer in range is a prime
        // Locks the prime list if it is, adds to it, releases the lock
        for(int current_num = startInteger; current_num <= endInteger; current_num++) {
            if(check_prime(current_num)) {
                primeLock.writeLock().lock();
                primeList.add(current_num);
                primeLock.writeLock().unlock();
            }
        }
        
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
