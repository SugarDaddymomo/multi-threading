package sem;
import java.util.concurrent.Semaphore;

class Q {
    int n;
    // start with consumer semaphore unavailable
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    void get() {
        try {
            semCon.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted caught!");
        }

        System.out.println("Got: "+n);
        semProd.release();
    }

    void put(int n) {
        try {
            semProd.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted caught!");
        }

        this.n = n;
        System.out.println("Put: "+n);
        semCon.release();
    }
}

class Producer implements Runnable {
    Q q;

    Producer(Q q) {
        this.q = q;
    }

    @Override
    public void run() {
        for (int i=0; i<20; i++) {
            q.put(i);
        }
    }
}

class Consumer implements Runnable {
    Q q;

    Consumer(Q q) {
        this.q = q;
    }

    @Override
    public void run() {
        for (int i=0; i<20; i++) {
            q.get();
        }
    }
    
}




public class ProdCon {
    public static void main(String[] args) {
        Q q = new Q();
        new Thread(new Consumer(q), "Consumer").start();
        new Thread(new Producer(q), "Producer").start();
    }
}
