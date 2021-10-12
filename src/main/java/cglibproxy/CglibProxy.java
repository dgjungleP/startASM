package cglibproxy;

import jdkproxy.HttpRequestTemplateImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxy {
    static {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./temp");
    }
//
//    public static void main(String[] args) {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(HttpRequestTemplateImpl.class);
//        enhancer.setCallback(new HttpRequestMethodInterceptor());
//        HttpRequestTemplateImpl template = (HttpRequestTemplateImpl) enhancer.create();
//
//    }

    public static void main(String[] args) {
        MyEnhancer enhancer = new MyEnhancer();
        enhancer.setSuperClass(HttpRequestTemplateImpl.class);
        enhancer.setCallBack(new MyMethodInterceptorImpl());
        HttpRequestTemplateImpl template = (HttpRequestTemplateImpl) enhancer.create();

    }
}
