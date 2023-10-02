public class MultiThreadDemo {
    public static void main(String[] args) {
        NewThread newThread1 = new NewThread("One");
        NewThread newThread2 = new NewThread("Two");
        NewThread newThread3 = new NewThread("Three");

        newThread1.t.start();
        newThread2.t.start();
        newThread3.t.start();

        System.out.println("Thread One is Alive: "+newThread1.t.isAlive());
        System.out.println("Thread Two is Alive: "+newThread2.t.isAlive());
        System.out.println("Thread Three is Alive: "+newThread3.t.isAlive());

        try {
            System.out.println("Waiting for threads to finish.");
            newThread1.t.join();
            newThread2.t.join();
            newThread3.t.join();

        } catch (InterruptedException e) {
            System.out.println("Main thread intrupted");
        }

        System.out.println("Thread One is Alive: "+newThread1.t.isAlive());
        System.out.println("Thread Two is Alive: "+newThread2.t.isAlive());
        System.out.println("Thread Three is Alive: "+newThread3.t.isAlive());

        System.out.println("Main thread exiting.");
    }
}
