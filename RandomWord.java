import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String winner = null;
        while(!StdIn.isEmpty()){
            i++;
            String current = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)){
                winner = current;
            }
        }
        StdOut.print(winner);
    }
}
