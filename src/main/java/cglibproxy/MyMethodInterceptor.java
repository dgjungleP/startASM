package cglibproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public interface MyMethodInterceptor {
    Object intercept(Object obj, Method method, String methodName, Object[] args) throws Throwable;
}
