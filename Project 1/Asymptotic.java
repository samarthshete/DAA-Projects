import java.util.Arrays;

/**
 * Asymptotic
 */
public class Asymptotic {
    public static void main(String[] args) {

        int[] testCase = {10, 100, 1000, 10000, 15000,20000,25000,30000,35000,40000,45000,50000,55000,60000,70000,80000,90000, 100000};  
         
        for (int n : testCase) {
            
            long startTime = System.nanoTime();
            // System.out.println("start time:" + startTime);
            // analysis(testCase);
            analysis(n);
            long endTime = System.nanoTime();
            // System.out.println("End Time:" + endTime);

            long experimentalDuration = (endTime - startTime);

            // System.out.println("n = " + n + );

            double theoreticalTime = theoreticalComplexity(n);
            
            System.out.println( n + "\t" + experimentalDuration + "\t" + theoreticalTime);
        }
    }

    public static void analysis(int n) {
       long[] a = new long[n];
        long[] b = new long[n];

        Arrays.fill(a, 1);
        Arrays.fill(b, 1);

        // for (int i = 0; i < a.length; i++) {
        //     System.out.println(a[i]);
        // }

        long sum = 0;
        int j = 2;

        while (j < n) {
            int k = j;

            while (k < n) {
                sum += a[j] * b[k];
                k = k * k;
            }
            j = 2 * j;
        }
    }
    
    public static double theoreticalComplexity(int n) {
        
        double logN = Math.log(n);
        double logLogN = Math.log(logN);
        
        return logN * logLogN;
         
    }
}