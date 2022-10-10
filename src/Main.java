public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()-> System.out.println("world!"));
        System.out.print("Hello, ");
        thread.start();
        thread.join();
    }
}