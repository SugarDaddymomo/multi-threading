import java.util.concurrent.*;

class Sum implements Callable<Integer> {
    int stop;
    Sum(int value) {
        stop = value;
    }
    @Override
    public Integer call() throws Exception {
        int sum=0;
        for (int i=1; i<=stop; i++) {
            sum+=i;
        }
        return sum;
    }
}
class Hypot implements Callable<Double> {
    double side1, side2;
    Hypot(double s1, double s2) {
        side1=s1;
        side2=s2;
    }
    @Override
    public Double call() throws Exception {
        return Math.sqrt((side1*side1) + (side2*side2));
    }
}
class Factorial implements Callable<Integer> {
    int stop;
    Factorial(int s) {
        stop=s;
    }
    @Override
    public Integer call() throws Exception {
        int fact=1;
        for (int i=2; i<=stop; i++) {
            fact*=i;
        }
        return fact;
    }
}

public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer> f;
        Future<Double> f2;
        Future<Integer> f3;
        System.out.println("Starting...");

        f = es.submit(new Sum(10));
        f2 = es.submit(new Hypot(3, 4));
        f3 = es.submit(new Factorial(5));

        try {
            System.out.println(f.get(10, TimeUnit.MILLISECONDS));
            System.out.println(f2.get(10, TimeUnit.MILLISECONDS));
            System.out.println(f3.get(10, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (ExecutionException e) {
            System.out.println(e);
        } catch (TimeoutException e) {
            System.out.println(e);
        }

        es.shutdown();
        System.out.println("Done");
    }
}
