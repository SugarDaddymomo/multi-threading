import java.util.concurrent.locks.ReentrantLock;

class Shared {
    static int count = 0;
}
class LockThread implements Runnable {
    String name;
    ReentrantLock lock;
    LockThread(ReentrantLock lk, String n) {
        lock = lk;
        name = n;
    }
    @Override
    public void run() {
        System.out.println("Starting: " + name);
        try {
            // First lock the count
            System.out.println(name+" is waiting to lock count.");
            lock.lock();
            System.out.println(name+" is locking count.");
            Shared.count++;
            System.out.println(name+": "+Shared.count);

            //Now allow a context switch
            System.out.println(name+" is sleeping");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            //UNLOCK
            System.out.println(name+" is unlocking count!");
            lock.unlock();
        }
    }
    
}



public class LockDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        new Thread(new LockThread(lock, "A")).start();
        new Thread(new LockThread(lock, "B")).start();
    }
}
