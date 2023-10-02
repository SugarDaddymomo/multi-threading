import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CustomThread implements Runnable {
    CyclicBarrier cbar;
    String name;
    CustomThread(CyclicBarrier c, String s) {
        cbar = c;
        name = s;
    }
    @Override
    public void run() {
        System.out.println(name);
        try {
            cbar.await();
        } catch (BrokenBarrierException exc) {
            System.out.println(exc);
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
    }
    
}

class BarAction implements Runnable {
    @Override
    public void run() {
        System.out.println("Barrier Reached!");
    }
    
}

public class BarDemo {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction());
        System.out.println("Starting...");
        new Thread(new CustomThread(cb, "A")).start();
        new Thread(new CustomThread(cb, "B")).start();
        new Thread(new CustomThread(cb, "C")).start();
        new Thread(new CustomThread(cb, "X")).start();
        new Thread(new CustomThread(cb, "Y")).start();
        new Thread(new CustomThread(cb, "Z")).start();
    }
}
