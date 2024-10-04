import java.math.BigInteger;
import java.util.Arrays;

public class AsymptoticBigInteger {
    public static void main(String[] args) {

        // Test cases for increasing values of n
        int[] testCase = {10, 100, 1000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 55000, 60000, 70000, 80000, 90000, 100000};

        // Initialize arrays outside the loop to avoid frequent memory allocation
        BigInteger[] a = new BigInteger[100000];
        BigInteger[] b = new BigInteger[100000];

        // Fill arrays with BigInteger values
        Arrays.fill(a, BigInteger.ONE);
        Arrays.fill(b, BigInteger.ONE);

        // Loop through each test case
        for (int n : testCase) {
            long startTime = System.nanoTime();
            analysis(n, a, b);
            long endTime = System.nanoTime();

            long experimentalDuration = (endTime - startTime);
            double theoreticalTime = theoreticalComplexity(n);

            System.out.println(n + "\t" + experimentalDuration + "\t" + theoreticalTime);
        }
    }

    // Modified analysis function with BigInteger
    public static void analysis(int n, BigInteger[] a, BigInteger[] b) {
        BigInteger sum = BigInteger.ZERO;
        int j = 2;
        while (j < n) {
            int k = j;

            while (k < n) {
               sum = sum.add(a[k].multiply(b[k]));
                k = k * k;
            }
            j = 2 * j;
        }
         

         
    }

    // Function to calculate theoretical complexity
    public static double theoreticalComplexity(int n) {
        double logN = Math.log(n);
        double logLogN = Math.log(logN);
        return logN * logLogN;
    }
}
