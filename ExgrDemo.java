import java.util.concurrent.Exchanger;

class MakeString implements Runnable {
    Exchanger<String> ex;
    String str;
    MakeString(Exchanger<String> c) {
        ex = c;
        str = new String();
    }
    @Override
    public void run() {
        char ch = 'A';
        for (int i=0; i<3; i++) {
            //FILL BUFFER
            for (int j=0;j<5; j++) {
                str += ch++;
            }
            try {
                // EXCHANGE A full buffer for an empty one
                str = ex.exchange(str);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class UseString implements Runnable {
    Exchanger<String> ex;
    String str;
    UseString(Exchanger<String> c) {
        ex = c;
    }
    @Override
    public void run() {
        for (int i=0; i<3; i++) {
            try {
                // EXCHANGE an empty buffer for a full one.
                str = ex.exchange(new String());
                System.out.println("Got: "+str);
            } catch (InterruptedException exception) {
                System.out.println(exception);
            }
        }
    }
    
}

public class ExgrDemo {
    public static void main(String[] args) {
        Exchanger<String> exgr = new Exchanger<String>();
        new Thread(new UseString(exgr)).start();
        new Thread(new MakeString(exgr)).start();
    }
}
