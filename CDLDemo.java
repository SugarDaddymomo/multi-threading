import java.util.concurrent.CountDownLatch;

class MyThread implements Runnable {
    CountDownLatch latch;
    MyThread(CountDownLatch c) {
        latch = c;
    }
    @Override
    public void run() {
        for (int i=0; i<5; i++) {
            System.out.println(i);
            latch.countDown();  //decrement count
        }
    }
}

public class CDLDemo {
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(5);
        System.out.println("Starting..");
        new Thread(new MyThread(cdl)).start();
        
        try {
            cdl.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Done");
    }    
}
