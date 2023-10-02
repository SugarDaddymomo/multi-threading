class NewThread implements Runnable {
    String name;
    Thread t;
    boolean suspendFlag;

    NewThread(String threadName) {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("New Thread: " + t);
        suspendFlag = false;
    }

    @Override
    public void run() {
        try {
            for (int i = 15; i > 0; i--) {
                System.out.println(name+": " + i);
                Thread.sleep(200);
                synchronized(this) {
                    while(suspendFlag) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(name+" interrupted.");
        }
        System.out.println(name+" exiting.");
    }

    synchronized void mysuspend() {
        suspendFlag = true;
    }

    synchronized void myresume() {
        suspendFlag = false;
        notify();
    }

    
}


public class SuspendResume {
    public static void main(String[] args) {
        NewThread obj1 = new NewThread("One");
        NewThread obj2 = new NewThread("Two");

        obj1.t.start();
        obj2.t.start();

        try {
            Thread.sleep(1000);
            obj1.mysuspend();
            System.out.println("Suspending Thread ONE");
            Thread.sleep(1000);
            obj1.myresume();
            System.out.println("Resuming Thread ONE");
            obj2.mysuspend();
            System.out.println("Suspending Thread TWO");
            Thread.sleep(1000);
            obj2.myresume();
            System.out.println("Resuming Thread TWO");
        } catch (InterruptedException e) {
            System.out.println("main thread interrupted");
        }

        try {
            System.out.println("Waiting for thread to finish");
            obj1.t.join();
            obj2.t.join();
        } catch (InterruptedException e) {
            System.out.println("main thread interrupted");
        }

        System.out.println("Main thread exiting");
    }
}
