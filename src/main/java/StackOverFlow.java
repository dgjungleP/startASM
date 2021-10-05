import java.io.IOException;

public class StackOverFlow {

    public static int sigma(int n) {

        System.out.println("Curren 'n' value is " + n);
        return n + sigma(n + 1);
    }


    public static void main(String[] args) throws IOException {

//        new Thread(() -> sigma(1)).start();
//        System.in.read();
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
    }
}
