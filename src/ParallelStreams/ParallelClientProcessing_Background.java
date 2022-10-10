package ParallelStreams;

public class ParallelClientProcessing_Background {
    /**
     * Udacisearch has really taken off! You now have lots of clients — so many clients, in fact, that you've started
     * to notice that old SummarizeClients utility is taking longer and longer to process them all and generate
     * its summary.
     *
     * Your starter code for this project will be the solution to the Streams API exercise from Lesson 1. The goal
     * is to transform this code to do its computations in parallel, using a thread pool, parallel Streams, and
     * Futures. Here's what you need to do:
     *
     * Create a ForkJoinPool that uses multiple threads. The default pool detects and uses the number of available
     * processors. If you want, you can use Runtime.getRuntime().availableProcessors() to see how many processors the system has.
     * For each of the computed metrics, create a Callable that uses a parallel stream to do the computation
     * (Hint: Lambdas are the easiest way to create a Callable). Submit the Callable to the thread pool and
     * keep the returned Future in a variable for later. Recall that, by default, running a parallel stream
     * without an explicit thread pool is generally a bad idea. Wrapping the parallel stream in a Callable and
     * submitting it to the thread pool like this forces the parallel stream to run in the given thread pool.
     * To get you started, here's the first metric, totalQuarterlySpend:
     * Future<Integer> totalQuarterlySpend =
     *    pool.submit(() ->
     *        clients
     *            .parallelStream()
     *            .mapToInt(UdacisearchClient::getQuarterlyBudget)
     *            .sum());
     * Shut down the thread pool ForkJoinPool#shutdown() and update the System.out.printlns to print out the
     * computed metrics (use Future#get()).
     * Running the solution
     * Try it out!
     *
     * javac SummarizeClients.java
     * java SummarizeClients
     * You should notice there are 10,000 clients now. If you want, take a peek in ClientStore.java to see
     * how they are randomly generated using the Stream API.
     */
}

