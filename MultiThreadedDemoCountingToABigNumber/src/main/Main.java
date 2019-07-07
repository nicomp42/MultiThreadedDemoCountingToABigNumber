/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		demo();
	}
	public static void demo() {
		System.out.println("This machine has " + getNumberOfCores() + " cores.");

		final long limit = 100_000_000_000L;	// 10_000_000_000L takes about 3-4 seconds.
		long startTime = System.currentTimeMillis();
		for (long i = 0; i < limit; i++) {
			// Nothing to see here, just counting
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Single threaded: Total execution time: " + (endTime - startTime) + " milliseconds.");

		// Now try it in two threads. Each thread will perform 1/2 of the counting
		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
				for (long i = 0; i < limit/2; i++) {
					// Nothing to see here, just counting
				}
		    }
		});  
		Thread t2 = new Thread(new Runnable() {
		    @Override
		    public void run() {
				for (long i = limit/2; i < limit; i++) {
					// Nothing to see here, just counting
				}
		    }
		});  
		startTime = System.currentTimeMillis();
		t1.start();
		t2.start();
		// Join t1 until it ends, then join t2 until it ends. Note that t1 and t2 are running in parallel with this thread.
		try {t1.join();} catch (InterruptedException e) {}
		try {t2.join();} catch (InterruptedException e) {}
		endTime = System.currentTimeMillis();
		System.out.println("2 threaded: Total execution time: " + (endTime - startTime) + " milliseconds.");

		// Now try it with however many cores are available.
		ArrayList<CountingThread> countingThreads = new ArrayList<CountingThread>();
		int numberOfCores = getNumberOfCores() - 2;
		long increment = limit / numberOfCores;
		for (int i = 0; i < numberOfCores; i++) {
			long start, end;
			start = i * increment;
			end = start + increment;
			countingThreads.add(new CountingThread(start, end));
		}
		// Launch all the threads to run in parallel
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numberOfCores; i++) {
			countingThreads.get(i).start();
		}

		// Wait for all the threads to finish
		for (int i = 0; i < numberOfCores; i++) {
			try {countingThreads.get(i).join();} catch(InterruptedException ex) {}
		}
		endTime = System.currentTimeMillis();
		System.out.println(numberOfCores + " threaded: Total execution time: " + (endTime - startTime) + " milliseconds.");
	}
	public static int getNumberOfCores() {
		int processors = Runtime.getRuntime().availableProcessors();
//	      System.out.println("CPU cores: " + processors);	
	      return processors;
	}

}
