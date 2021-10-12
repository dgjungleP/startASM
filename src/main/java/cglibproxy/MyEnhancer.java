package cglibproxy;

import com.sun.xml.internal.ws.server.provider.ProviderArgumentsBuilder;
import org.objectweb.asm.Type;
import startasm.util.ByteCodeUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class MyEnhancer {
    private Class<?> superClass;
    private MyMethodInterceptor methodInterceptor;

    public void setSuperClass(Class<?> superClass) {
        if (superClass.isInterface()) {
            throw new RuntimeException("SuperClass can not be interface");
        }
        if (superClass == Object.class) {
            throw new RuntimeException("Can not proxy with Object");
        }

        if (((superClass.getModifiers() & Modifier.FINAL) == Modifier.FINAL)) {
            throw new RuntimeException("Can not proxy with final class");
        }
        this.superClass = superClass;
    }

    public void setCallBack(MyMethodInterceptor interceptor) {
        this.methodInterceptor = interceptor;
    }

    public Object create() {
        if (this.superClass == null) {
            throw new RuntimeException("SuperClass can not be null");
        }
        try {
            if (this.methodInterceptor == null) {
                return superClass.newInstance();
            }
            String subClassName = Type.getInternalName(superClass) + "$Proxy";

            byte[] codeByte = SubclassProxyFactory.CreateProxyClass(subClassName, superClass);
            Class<?> proxyClass = ByteCodeUtils.loadClass(subClassName, codeByte);
            Constructor<?> constructor = proxyClass.getConstructor(MyMethodInterceptor.class);
            return constructor.newInstance(methodInterceptor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
