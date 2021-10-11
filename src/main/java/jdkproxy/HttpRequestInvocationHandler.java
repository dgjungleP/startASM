package jdkproxy;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

public class HttpRequestInvocationHandler implements InvocationHandler {
    private HttpRequestTemplate target;

    public HttpRequestInvocationHandler(HttpRequestTemplate target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return method.invoke(target, args);

        } finally {
            long end = System.currentTimeMillis();
            System.out.println("The method is spend: " + (end - start) + " ms");
        }
    }

    static {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

    }

    public static void main(String[] args) throws Exception {
        HttpRequestTemplate template = new HttpRequestTemplateImpl();
        HttpRequestTemplate requestTemplate = (HttpRequestTemplate) MyProxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{HttpRequestTemplate.class},
                new HttpRequestInvocationHandler(template));

    }
}
