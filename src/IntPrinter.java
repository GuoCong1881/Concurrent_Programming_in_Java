import java.util.ArrayList;
import java.util.List;



public final class IntPrinter {

    /**
     * Running Multi-threaded Programs
     * This exercise is going to be more an experiment in which you twiddle around with threads to help understand how they work.
     *
     * Humble, Sequential Beginnings
     * The starter code contains a simple for loop. The number of iterations, n, is set by the command-line argument.
     * Each iteration of the loop prints the iteration number, as well as the current thread number.
     * Try it out to make sure it works:
     * javac IntPrinter.java
     * java IntPrinter 3
     *
     * You should see some output like this:
     *
     * Thread #1 printed 0
     * Thread #1 printed 1
     * Thread #1 printed 2
     * A few important things you will notice about this execution:
     *
     * All the printing to standard output happens from the same thread, whose id is 1.
     * The iteration variable is always printed sequentially, in order. After all, it's just a regular for loop.
     */
    public static void seqIntPrinter(int n) throws Exception {

        // TODO: Create a list of n Threads and run them all in parallel with the System.out.println
        //       statement.
        for (int i = 0; i < n; i++) {
            System.out.println("Sequential Thread #" + Thread.currentThread().getId() + " printed " + i);
        }
    }

    /**
     * Making It Parallel
     * Your job is to modify this program to run in n threads. Each thread will print one of the iteration values to
     * standard output. To do this, you will use the Thread API. The Thread API is the lowest-level
     * threading API Java offers. Most of the concurrent programming you write will use abstractions built on
     * top of Threads. So while you usually won't be using Threads directly, it's useful to know what's going
     * on under the hood.
     *
     * First, you need to create a List of Threads. In a for loop, populate the list with threads, calling the
     * new Thread(Runnable) constructor. The easiest way to create Runnables is to use lambdas, but you can also
     * create a separate Runnable implementation if you'd like.
     * Each thread should be constructed with a Runnable that prints its thread id and iteration number to standard
     * output (just like the sequential version of the code).
     * Next, in a separate for loop, start() each of the threads. You now have code to execute n threads
     * executing in parallel — pretty cool!
     * Finally, in yet another for loop join() each thread. This is the code that waits for all the threads
     * to finish executing.
     * Note: In most real-world applications, you'll want to run your threads in an explicit thread pool.
     * You'll learn more about thread pools in a later topic. Running threads as you did in this example
     * uses the default thread pool, which is usually frowned upon.
     *
     * Running the Solution
     * Compile and run the program. What happens when you run the following?
     *
     * javac IntPrinter.java
     * java IntPrinter 10
     * On my PC, the terminal shows the following output:
     *
     * Thread #15 printed 3
     * Thread #20 printed 8
     * Thread #17 printed 5
     * Thread #21 printed 9
     * Thread #19 printed 7
     * Thread #14 printed 2
     * Thread #12 printed 0
     * Thread #13 printed 1
     * Thread #16 printed 4
     * Thread #18 printed 6
     * A couple interesting things to note are that:
     *
     * The thread id is different every time.
     * The numbers 1 through 10 are printed in seemingly random order.
     * That last point is due to the fact that the order of execution of the threads is not guaranteed.
     * What would we have to do to get them to print in order? That's what synchronization is all about,
     * which you will learn about in a later topic.
     */

    public static void palIntPrinter(int n) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        //each thread is constructed with a runnable that prints to standard output
        for (int i = 0; i < n; i++){
            threads.add(new Thread(new IntRunner(i)));
        }

        /**
         * non of the threads are actually run until you call the start method
         */

        //at the end of this for loop, we have multiple threads running in parallel at the same time
        for (Thread thread: threads){
            thread.start();
            //thread.run();
            /**if using thread.run(), the run method just executes the runnable without starting a new thread
             * everything runs in the main thread, and all the numbers print in order
             * When we run them in parallel, they printed in a completely different order.
             * This is because thread scheduling is managed by the operating system and
             * it can run threads in any order at once. If you want tasks to execute in a particular order
             * you have to just run them in one thread, or hyou have to employ some kind of synchronization
             */
        }

        //calling thread.join waits for each thread to finish running
        for (Thread thread: threads){
            thread.join();
        }
    }

    private static final class IntRunner implements Runnable {

        private final int value;

        IntRunner(int value){
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println("Thread #" + Thread.currentThread().getId() + " printed " + value);
        }
    }



    public static void main(String[] args) throws Exception{
        int n = 10;
        seqIntPrinter(n);
        palIntPrinter(n);
    }


}
