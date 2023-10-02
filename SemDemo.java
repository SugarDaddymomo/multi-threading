import java.util.concurrent.Semaphore;

public class SemDemo {
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);

        new Thread(new IncThread(sem, "A")).start();
        new Thread(new DecThread(sem, "B")).start();
    }
}

class Shared {
    static int count = 0;
}

class IncThread implements Runnable {
    String name;
    Semaphore sem;

    IncThread(Semaphore s, String n) {
        sem = s;
        name = n;
    }

    @Override
    public void run() {
        System.out.println("Starting "+name);
        try {
            // GET PERMIT
            System.out.println(name+" is waiting for a permit");
            sem.acquire();
            System.out.println(name+" gets a permit");

            // ACCESS SHARED RESOURCE
            for (int i=0; i < 5; i++) {
                Shared.count++;
                System.out.println(name+": "+Shared.count);

                //context switch
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // RELEASE PERMIT
        System.out.println(name+" releases the permit");
        sem.release();
    }
}

class DecThread implements Runnable {
    String name;
    Semaphore sem;

    DecThread(Semaphore s, String n) {
        sem = s;
        name = n;
    }

    @Override
    public void run() {
        System.out.println("Starting "+name);
        try {
            // GET PERMIT
            System.out.println(name+" is waiting for a permit");
            sem.acquire();
            System.out.println(name+" gets a permit");

            // ACCESS SHARED RESOURCE
            for (int i=0; i < 5; i++) {
                Shared.count--;
                System.out.println(name+": "+Shared.count);

                //context switch
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // RELEASE PERMIT
        System.out.println(name+" releases the permit");
        sem.release();
    }
}