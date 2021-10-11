package jdkproxy;

import startasm.util.ByteCodeUtils;

import javax.print.DocFlavor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

public class MyProxy {

    protected InvocationHandler h;
    private static final AtomicInteger PROXY_CNT = new AtomicInteger(0);


    public MyProxy() {
    }

    public MyProxy(InvocationHandler h) {
        this.h = h;
    }

    public static Object newProxyInstance(Object proxy, Class<?>[] interfaces, InvocationHandler h) throws Exception {
        String proxyClassName = "com/sum/proxy/$Proxy" + PROXY_CNT.getAndIncrement();
        byte[] codeBytes = MyProxyFactory.createProxyClass(proxyClassName, interfaces);
        Class<?> proxyClass = ByteCodeUtils.loadClass(proxyClassName, codeBytes);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        return constructor.newInstance(h);
    }
}
