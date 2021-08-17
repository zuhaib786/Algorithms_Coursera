import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String ans = StdIn.readString();
        double i = 1.0;
        while (!StdIn.isEmpty()) {
            i++;
            String temp = StdIn.readString();
            boolean b = StdRandom.bernoulli(1 / i);
            if (b) {
                ans = temp;
            }
        }
        System.out.println(ans);
    }
}
