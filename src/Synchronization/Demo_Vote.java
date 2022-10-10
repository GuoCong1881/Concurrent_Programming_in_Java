package Synchronization;

import java.util.*;
import java.util.concurrent.*;

public class Demo_Vote {
    /**
     * Synchronization Demo: voting app
     */



    //Single-Threaded version
    public static void singleThreadedVote() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Map<String, Integer> votes = new HashMap<>();
        List<Future<?>> futures = new ArrayList<>(10_000);
        for (int i = 0; i < 10_000; i++){
            futures.add(
                    executor.submit(()->{
                        votes.compute("Larry", (k,v) -> (v == null) ? 1 : v+1);
                    }));
        }
        for (Future<?> future: futures){
            future.get();
        }
        executor.shutdown();
        System.out.println(votes);
    }

    //Wrong: hashmap, we skipped 13% votes
    public static void multiThreadedVoteWrong1() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(12);
        Map<String, Integer> votes = new HashMap<>();
        List<Future<?>> futures = new ArrayList<>(10_000);
        for (int i = 0; i < 10_000; i++) {
            futures.add(
                    executor.submit(() -> {
                        Integer count = votes.get("Larry");
                        if (count == null){
                            votes.put("Larry", 1);
                        } else {
                            votes.put("Larry", count + 1);
                        }
                    }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executor.shutdown();

        System.out.println(votes);
    }

    //Wrong: concurrenthashmap (have an atomic method for reading and updating an entry in a map
    // but it is wrong here because we are still using two separate operations
    // so we should update the code with atomic operation
    public static void multiThreadedVoteWrong2() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(12);
        Map<String, Integer> votes = new ConcurrentHashMap<>();
        List<Future<?>> futures = new ArrayList<>(10_000);
        for (int i = 0; i < 10_000; i++) {
            futures.add(
                    executor.submit(() -> {
                        Integer count = votes.get("Larry");
                        if (count == null){
                            votes.put("Larry", 1);
                        } else {
                            votes.put("Larry", count + 1);
                        }
                    }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executor.shutdown();

        System.out.println(votes);
    }

    //Multi-Threaded Version using concurrentHashMap
    public static void multiThreadedVote() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(12);
        Map<String, Integer> votes = new ConcurrentHashMap<>();
        List<Future<?>> futures = new ArrayList<>(10_000);
        for (int i = 0; i < 10_000; i++) {
            futures.add(
                    executor.submit(() -> {
                        votes.compute("Larry", (k, v) -> (v == null) ? 1 : v + 1);
                    }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executor.shutdown();

        System.out.println(votes);
    }

    //Multi-threaded version using Collections.synchronizedMap()
    public static void multiThreadedSynVote() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(12);
        Map<String, Integer> votes = Collections.synchronizedMap(new HashMap<>());
        List<Future<?>> futures = new ArrayList<>(10_000);
        for (int i = 0; i < 10_000; i++) {
            futures.add(
                    executor.submit(() -> {
                        votes.compute("Larry", (k, v) -> (v == null) ? 1 : v + 1);
                    }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executor.shutdown();

        System.out.println(votes);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        singleThreadedVote();
        multiThreadedVoteWrong1();
        multiThreadedVoteWrong2();
        multiThreadedVote();
        multiThreadedSynVote();

    }

}
