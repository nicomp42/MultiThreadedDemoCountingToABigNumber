package main;

/**
 * Count from one long int up to another long int. Really simple
 *
 */
public class CountingThread extends Thread {

    private long start, end;
    public CountingThread(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for(long i = start; i <= end; i++) {

        }
//      System.out.println("Thread counted from " + start + " to " + end);
    }
}