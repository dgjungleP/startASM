package cglibproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyMethodInterceptorImpl implements MyMethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, String methodName, Object[] args) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            return method.invoke(obj, args);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("The method spend time:" + (end - start) + "ms");
        }
    }
}
