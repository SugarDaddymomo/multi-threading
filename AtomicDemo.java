import java.util.concurrent.atomic.AtomicInteger;

class Shared {
    static AtomicInteger ai = new AtomicInteger(0);
}
class AtomThread implements Runnable {
    String name;
    AtomThread(String n) {
        name = n;
    }
    @Override
    public void run() {
        System.out.println("Starting "+name+"...");
        for (int i=1; i<=3; i++) {
            System.out.println(name+" got: "+Shared.ai.getAndSet(i));
        }
    }
    
}

public class AtomicDemo {
    public static void main(String[] args) {
        new Thread(new AtomThread("A")).start();
        new Thread(new AtomThread("B")).start();
        new Thread(new AtomThread("C")).start();
    }
}
