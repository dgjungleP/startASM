package bytecheck;

public class HelloWorld {
    public static void main(String[] args) {
//        System.out.println("Hello World");
        int a = 10, b = 20;
        int c = b;
        b = a;
    }

    public int ifFunction(int type) {
        if (type == 1) {
            return 100;
        } else if (type == 2) {
            return 1000;
        } else {
            return 0;
        }

    }
}
