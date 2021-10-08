package classload;

import org.omg.CORBA.ByteHolder;

public class ClassLoaderTestMain {

    public static void main(String[] args) throws ClassNotFoundException {
        loadClass1();

//        loadClass2();
    }

    private static void loadClass2() throws ClassNotFoundException {
        System.out.println("---------------------------------------------------");
        Class<?> forName = Class.forName("classload.ClassLoaderTest");
        System.out.println(forName.getName());
        System.out.println("---------------------------------------------------");
    }

    private static void loadClass1() throws ClassNotFoundException {
        System.out.println("---------------------------------------------------");
        Class<?> loadClass = ClassLoaderTestMain.class.getClassLoader().loadClass("classload.ClassLoaderTest");
        System.out.println(loadClass.getName());
        System.out.println("---------------------------------------------------");
    }

}
