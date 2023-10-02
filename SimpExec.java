import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CustomThreadTwo implements Runnable {
    String name;
    CountDownLatch latch;

    CustomThreadTwo(CountDownLatch c, String s) {
        name = s;
        latch = c;
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
            System.out.println(name+": " +i);
            latch.countDown();
        }
    }
    
}

public class SimpExec {
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(5);
        CountDownLatch cdl2 = new CountDownLatch(5);
        CountDownLatch cdl3 = new CountDownLatch(5);
        CountDownLatch cdl4 = new CountDownLatch(5);
        ExecutorService es = Executors.newFixedThreadPool(2);

        System.out.println("Starting ... ");
        //START THE THREADS
        es.execute(new CustomThreadTwo(cdl, "A"));
        es.execute(new CustomThreadTwo(cdl2, "B"));
        es.execute(new CustomThreadTwo(cdl3, "C"));
        es.execute(new CustomThreadTwo(cdl4, "D"));

        try {
            cdl.await();
            cdl2.await();
            cdl3.await();
            cdl4.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        es.shutdown();
        System.out.println("Done");
    }
}
