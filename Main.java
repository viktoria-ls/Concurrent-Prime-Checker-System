import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int LIMIT = 10000000;
    private static int NUM_THREADS = 1;

    public static void main(String[] args) {
        // Get input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter limit: ");
        LIMIT = sc.nextInt();
        System.out.print("Enter number of threads: ");
        NUM_THREADS = sc.nextInt();
        sc.close();

        // Get start time`
        long startTime = System.currentTimeMillis();

        // Goes through all numbers up to LIMIT and adds to primes List
        List<Integer> primes = new ArrayList<Integer>();
        
        for(int current_num = 2; current_num <= LIMIT; current_num++) {
            if(check_prime(current_num)) {
                primes.add(current_num);
            }
        }

        // Get stop time and total time
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.printf("%d primes were found.\n",primes.size());
        System.out.printf("LIMIT: %d, NUM_THREADS: %d\n", LIMIT, NUM_THREADS);
        System.out.print("TOTAL TIME: " + totalTime + " milliseconds");
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