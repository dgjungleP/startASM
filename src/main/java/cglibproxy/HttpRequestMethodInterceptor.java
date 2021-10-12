package cglibproxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HttpRequestMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return proxy.invokeSuper(obj, args);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("The method spend time:" + (end - start) + "ms");
        }
    }
}
